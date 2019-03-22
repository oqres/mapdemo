package com.example.demo.common;

/**
 * Created by whydd on 2017-07-13.
 */
public class ApiException extends Exception{
    private int errorCode;
    private String errorMsg;

    public int getErrorCode(){
        return this.errorCode;
    }

    public String getErrorMsg(){
        return this.errorMsg;
    }

    public ApiException(int errorCode, String errorMsg){
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
