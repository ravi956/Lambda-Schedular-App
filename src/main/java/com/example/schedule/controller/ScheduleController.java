package com.example.schedule.controller;

import com.example.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.cloudwatchevents.model.PutRuleResponse;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/schedule")
    public String createCloudWatchRule() {
        PutRuleResponse response = scheduleService.putCWRule();
		scheduleService.putCWTargets();
		scheduleService.addResourcePolicy(response.ruleArn());
        return "Successfully scheduled the lambda function";
    }
}
