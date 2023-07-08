package com.blog.server.exceptions;

public class ServiceException extends RuntimeException{

    public ServiceException(String message) {
        super(message);
    }
}
