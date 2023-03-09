package com.example.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudwatch.model.CloudWatchException;
import software.amazon.awssdk.services.cloudwatchevents.CloudWatchEventsClient;
import software.amazon.awssdk.services.cloudwatchevents.model.*;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.AddPermissionRequest;

import java.util.UUID;

@Service
public class ScheduleService {

    @Autowired
    private CloudWatchEventsClient cweClient;

    @Autowired
    private LambdaClient lambdaClient;

    @Value("${cweRule.ruleName}")
    private String ruleName;

    @Value("${cweRule.scheduleExpression}")
    private String scheduleExpression;

    @Value("${aws.lambda.functionArn}")
    private String functionArn;

    @Value("${aws.lambda.functionName}")
    private String functionName;

    @Value("${aws.lambda.policy.action}")
    private String action;

    @Value("${aws.lambda.policy.principal}")
    private String principal;

    public PutRuleResponse putCWRule() {

        PutRuleResponse response = null;
        try {
            PutRuleRequest request = PutRuleRequest.builder()
                    .name(ruleName)
                    .scheduleExpression(scheduleExpression)
                    .state(RuleState.ENABLED)
                    .build();

            response = cweClient.putRule(request);
            System.out.printf("Successfully created CloudWatch events rule with arn = %s\n",
                    response.ruleArn());
        } catch (CloudWatchException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
        return response;
    }

    public void putCWTargets() {

        String targetId = UUID.randomUUID().toString();
        try {
            Target target = Target.builder()
                    .arn(functionArn)
                    .id(targetId)
                    .build();

            PutTargetsRequest request = PutTargetsRequest.builder()
                    .targets(target)
                    .rule(ruleName)
                    .build();

            cweClient.putTargets(request);
            System.out.printf("Successfully created CloudWatch events target for rule : %s\n", ruleName);
        } catch (CloudWatchException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    public void addResourcePolicy(String ruleArn) {

        String statementId = UUID.randomUUID().toString();

        AddPermissionRequest addPermissionRequest = AddPermissionRequest.builder()
                                                        .statementId(statementId)
                                                        .sourceArn(ruleArn)
                                                        .action(action)
                                                        .principal(principal)
                                                        .functionName(functionName)
                                                        .build();

        lambdaClient.addPermission(addPermissionRequest);
        System.out.println("Resource Policy added with statement id : " + statementId);
    }
}