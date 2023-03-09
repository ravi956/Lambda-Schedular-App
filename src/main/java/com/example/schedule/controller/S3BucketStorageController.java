package com.example.schedule.controller;

import com.example.schedule.dao.LogDataAccessService;
import com.example.schedule.model.ExecutionLog;
import com.example.schedule.service.S3BucketStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class S3BucketStorageController {

    @Autowired
    private S3BucketStorageService s3BucketStorageService;

    @Autowired
    private LogDataAccessService logDataAccessService;

    @PostMapping("/storage")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        List<Object> response = s3BucketStorageService.uploadFile(file);
        logDataAccessService.save((ExecutionLog) response.get(0));
        return "File uploaded : " + response.get(1);
    }
}
