package com.example.schedule.dao;

import com.example.schedule.model.ExecutionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LogDataAccessService implements ExecutionLogDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${table.name}")
    private String tableName;

    @Override
    public int save(ExecutionLog executionLog) {
        String sqlStatement = "INSERT INTO " + tableName +  " (function_name, execution_time) " +
                "VALUES (?, ?)";
        return jdbcTemplate.update(sqlStatement, executionLog.getFunctionName(), executionLog.getExecutionTime());
    }

    @Override
    public int deleteByFunctionName(String functionName) {
        String sqlStatement = "DELETE FROM " + tableName + " WHERE function_name = ?";
        return jdbcTemplate.update(sqlStatement, functionName);
    }

    @Override
    public List<ExecutionLog> getAll() {
        String sqlStatement = "SELECT * FROM " + tableName;
        return jdbcTemplate.query(sqlStatement, rowMapper());
    }

    @Override
    public ExecutionLog getBySno(long sno) {
        String sqlStatement = "SELECT * FROM " + tableName + " WHERE sno = ?";
        return jdbcTemplate.queryForObject(sqlStatement, rowMapper(), sno);
    }

    @Override
    public List<ExecutionLog> getByFunctionName(String functionName) {
        String sqlStatement = "SELECT * FROM " + tableName + " WHERE function_name = ?";
        return jdbcTemplate.query(sqlStatement, rowMapper(), functionName);
    }

    private RowMapper<ExecutionLog> rowMapper() {
        return new RowMapper<ExecutionLog>() {
            @Override
            public ExecutionLog mapRow(ResultSet rs, int rowNum) throws SQLException {
                ExecutionLog executionLog = new ExecutionLog(
                        rs.getLong("sno"),
                        rs.getTimestamp("function_invoke_time"),
                        rs.getString("function_name"),
                        rs.getLong("execution_time"));
                return executionLog;
            }
        };
    }
}
