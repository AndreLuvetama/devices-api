package com.devices.api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DeviceNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -6588856562733498763L;
    public DeviceNotFoundException(String message){
        super(message);
    }

    public DeviceNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
