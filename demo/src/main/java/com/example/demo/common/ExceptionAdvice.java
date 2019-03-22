package com.example.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by whydd on 2017-07-13.
 */
@ControllerAdvice
public class ExceptionAdvice {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    
    private ResponseMap reponseMap;
    @Autowired
    public ExceptionAdvice(ResponseMap reponseMap) {
    	this.reponseMap = reponseMap;
	}
    
    
    
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public Map<String, Object> handleException(Exception e) throws Exception{
        LOGGER.error(e.getMessage(), e, e.getStackTrace());
        return reponseMap.internalServerError();
    }
    
    
    @ExceptionHandler(ApiException.class)
    public Map<String, Object> handleApiException(ApiException ae) throws Exception{
        LOGGER.error(ae.getMessage(), ae, ae.getStackTrace());
        return reponseMap.internalServerError(ae.getErrorCode(), ae.getMessage());
    }
    
    
    
}
