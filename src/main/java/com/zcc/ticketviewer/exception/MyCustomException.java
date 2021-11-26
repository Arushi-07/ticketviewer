package com.zcc.ticketviewer.exception;

import lombok.Getter;

@Getter
public class MyCustomException extends Exception{
    String message;
    int statusCode;
    public MyCustomException(String errorMessage, int statusCode){
        super();
        this.message = errorMessage;
        this.statusCode = statusCode;

    }


}
