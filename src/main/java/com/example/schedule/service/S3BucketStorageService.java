package com.example.schedule.service;

import com.example.schedule.model.ExecutionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class S3BucketStorageService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${table.name}")
    private String tableName;

    public List<Object> uploadFile(MultipartFile multipartFile) {

        Long startTime = System.currentTimeMillis();
        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        uploadFileToS3Bucket(fileName, file);
        file.delete();
        Long endTime = System.currentTimeMillis();

        ExecutionLog executionLog = new ExecutionLog(
                new Object() {}.getClass().getEnclosingMethod().getName(),
                endTime-startTime);

        return Arrays.asList(executionLog, fileName);
    }

    private File convertMultiPartToFile(MultipartFile multipartFile) {

        File convertedFile = new File(multipartFile.getOriginalFilename());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }

    private String generateFileName(MultipartFile multipartFile) {
        return System.currentTimeMillis() + "_"
                + multipartFile.getOriginalFilename()
                .replace(" ", "_");
    }

    private void uploadFileToS3Bucket(String fileName, File file) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentLength(file.length())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));
    }
}
