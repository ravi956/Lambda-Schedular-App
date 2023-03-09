CREATE TABLE IF NOT EXISTS execution_log (
    sno BIGINT AUTO_INCREMENT PRIMARY KEY,
    function_invoke_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    function_name VARCHAR(100),
    execution_time BIGINT
);