package com.example.schedule.dao;

import com.example.schedule.model.ExecutionLog;

import java.util.List;

public interface ExecutionLogDao {

    int save(ExecutionLog executionLog);

    int deleteByFunctionName(String functionName);

    List<ExecutionLog> getAll();

    ExecutionLog getBySno(long sno);

    List<ExecutionLog> getByFunctionName(String functionName);
}
