package com.zcc.ticketviewer.exception;

import lombok.Getter;

@Getter
public class ApiException extends Exception {
    String message;
    int statusCode;

    /**
     * @param errorMessage message for the exception
     * @param statusCode status code of the exception
     */
    public ApiException(final String errorMessage, final int statusCode){
        super(errorMessage);
        this.message = errorMessage;
        this.statusCode = statusCode;
    }
}