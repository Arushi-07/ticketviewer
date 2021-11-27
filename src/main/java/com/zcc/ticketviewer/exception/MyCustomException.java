package com.zcc.ticketviewer.exception;

import lombok.Getter;

@Getter
public class MyCustomException extends Exception{
    String message;
    int statusCode;

    /**
     * @param errorMessage message for the exception
     * @param statusCode status code of the exception
     */
    public MyCustomException(final String errorMessage, final int statusCode){
        super(errorMessage);
        this.message = errorMessage;
        this.statusCode = statusCode;

    }
}
