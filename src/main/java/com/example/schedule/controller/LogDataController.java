package com.example.schedule.controller;

import com.example.schedule.dao.LogDataAccessService;
import com.example.schedule.model.ExecutionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LogDataController {

    @Autowired
    private LogDataAccessService logDataAccessService;

    @GetMapping("/log")
    public List<ExecutionLog> getExecutionLogs() {
        return logDataAccessService.getAll();
    }

    @GetMapping("/log/{sno}")
    public ExecutionLog getExecutionLogBySno(@PathVariable("sno") long sNo) {
        return logDataAccessService.getBySno(sNo);
    }

    @GetMapping("/log/function")
    public List<ExecutionLog> getExecutionLogsByFunctionName(@RequestParam(value = "function_name") String functionName) {
        return logDataAccessService.getByFunctionName(functionName);
    }

    @DeleteMapping("/log")
    public String deleteLogsByFunctionName(@RequestParam("function_name") String functionName) {
        return "No. of rows deleted : " + logDataAccessService.deleteByFunctionName(functionName);
    }
}
