package com.devices.api.util;

import com.devices.api.domain.entity.Device;
import com.devices.api.exception.DeviceIllegalStateException;
import com.devices.api.exception.DeviceNotFoundException;
import com.devices.api.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class ValidationData {
    @Autowired
    private DeviceRepository repository;

    public void validateDeviceId(Long deviceId){
        if(isEmpty(deviceId) || deviceId <= 0){
            throw new DeviceIllegalStateException("The field cannot be empty, Enter the ID");
        }
        Device device = repository.findById(deviceId).orElseThrow(
                ()-> new DeviceNotFoundException("Device not Found")
        );
    }
}
