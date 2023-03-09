package com.example.schedule.model;

import java.sql.Timestamp;

public class ExecutionLog {

    private long sNo;
    private Timestamp functionInvokeTime;
    private String functionName;
    private long executionTime;

    public ExecutionLog(long sNo, Timestamp functionInvokeTime, String functionName, long executionTime) {
        this.sNo = sNo;
        this.functionInvokeTime = functionInvokeTime;
        this.functionName = functionName;
        this.executionTime = executionTime;
    }

    public ExecutionLog(String functionName, long executionTime) {
        this.functionName = functionName;
        this.executionTime = executionTime;
    }

    public ExecutionLog() {
    }

    public long getsNo() {
        return sNo;
    }

    public void setsNo(long sNo) {
        this.sNo = sNo;
    }

    public Timestamp getFunctionInvokeTime() {
        return functionInvokeTime;
    }

    public void setFunctionInvokeTime(Timestamp functionInvokeTime) {
        this.functionInvokeTime = functionInvokeTime;
    }


    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
