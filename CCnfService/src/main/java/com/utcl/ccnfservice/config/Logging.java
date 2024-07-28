package com.utcl.ccnfservice.config;

import java.io.IOException;
import java.sql.SQLException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.utcl.dto.ccnf.CustomException;

@Aspect
@Controller
public class Logging {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Before("execution(* com.utcl.ccnfservice.cement.controller.transaction.*.* (..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments: {} ,Class Name:{}", joinPoint.getSignature().getName(),joinPoint.getArgs(), joinPoint.getSignature().getDeclaringTypeName());
    }

    @AfterReturning(pointcut = "execution(* com.utcl.ccnfservice.cement.controller.transaction.*.* (..))", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method: {} with result: {} ,Class Name:{}", joinPoint.getSignature().getName(), result, joinPoint.getSignature().getDeclaringTypeName());
    }
    @AfterThrowing(pointcut = "execution(* com.utcl.ccnfservice.cement.controller.transaction.*.* (..))", throwing = "ex")
    public void handleExceptions(Exception ex) {
        logErrorBasedOnExceptionType(ex);
    	logger.error(ex.getMessage());
    }
    private void logErrorBasedOnExceptionType(Exception ex) {
        if (ex instanceof IOException ioEx) {
        	logger.error("IOException occurred: {}", ioEx.getMessage());
        } else if (ex instanceof SQLException sqlEx) {
        	logger.error("SQLException occurred: {}", sqlEx.getMessage());
        }
        else if (ex instanceof CustomException cx ) {
        	logger.error("CustomException occurred: {}", cx.getMessage());
        }
        else {
        	logger.error("Exception occurred: {}", ex.getMessage());
        }
        
    }
}