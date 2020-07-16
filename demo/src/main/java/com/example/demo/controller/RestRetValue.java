package com.example.demo.controller;


public class RestRetValue<T> {

    private String errorCode;
    private String errorMsg;
    private T data;

    public RestRetValue(){
        this.errorCode = "0";
        this.errorMsg = "";
        this.data = null;
    }

    public RestRetValue(String errorCode, String errorMsg, T data){
        setReturnValue(errorCode,  errorMsg,  data);
    }

    public String getErrorCode(){
        return errorCode;
    }

    public void setErrorCode(String errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorMsg(){
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg){
        this.errorMsg = errorMsg;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }

    public void setReturnValue(String errorCode, String errorMsg, T data) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

}
