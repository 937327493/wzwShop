package com.wzw.exceptions;

import com.wzw.templates.R;
import com.wzw.util.RUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UnifiedExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R handlerException(Exception e){
        log.info("服务器内部异常，{}", e.getMessage());
        return RUtil.fail(e.getMessage());
    }

}
