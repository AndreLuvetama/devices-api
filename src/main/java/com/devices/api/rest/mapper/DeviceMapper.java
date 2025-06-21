package com.devices.api.rest.mapper;

import com.devices.api.domain.entity.Device;
import com.devices.api.rest.dto.DeviceDTO;

public class DeviceMapper {


    public static Device putDeviceFromDto(Device device, DeviceDTO dto){
        device.setName(dto.getName());
        device.setBrand(dto.getBrand());
        device.setState(dto.getState());
        return device;
    }

    public static Device patchDeviceFromDto(Device device, DeviceDTO dto){
        if(dto.getName() != null){
            device.setName(dto.getName());
        }
        if(dto.getBrand() != null){
            device.setBrand(dto.getBrand());
        }
        if(dto.getState() != null){
            device.setState(dto.getState());
        }
        return device;
    }

    public static DeviceDTO mapDeviceToDTO(Device device){
            return new DeviceDTO(
                    device.getName(),
                    device.getBrand(),
                    device.getCreationTime(),
                    device.getState()
            );
    }

}
