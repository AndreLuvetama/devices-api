package com.devices.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeviceIllegalStateException extends RuntimeException{
    public DeviceIllegalStateException(String message){
        super(message);
    }
    public DeviceIllegalStateException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

