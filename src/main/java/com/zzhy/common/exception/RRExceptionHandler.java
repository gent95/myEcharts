package com.zzhy.common.exception;

import com.google.gson.JsonParseException;
import com.zzhy.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

/**
 * 异常处理器
 */
@RestControllerAdvice
@Slf4j
public class RRExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e) {
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }

    @ExceptionHandler(SQLException.class)
    public R handlerSQLException(SQLException e) {
        log.error(e.getMessage(), e);
        return R.error("SQL语法有误");
    }

    @ExceptionHandler(JsonParseException.class)
    public R handlerJsonParseException(JsonParseException e) {
        log.error(e.getMessage(), e);
        return R.error("配置项不符合Echarts规则或JSON格式有误");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error();
    }

}
