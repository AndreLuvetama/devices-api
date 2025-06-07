package com.devices.api.service;

import com.devices.api.domain.entity.Device;
import com.devices.api.rest.dto.DeviceDTO;

import java.util.List;

public interface DeviceService {
    Device create(DeviceDTO dto);
    Device updateFullyDevice(DeviceDTO dto, Long id);
    Device updatePartialDevice(DeviceDTO dto, Long id);
    Device fetchDevice(String deviceName);
    List<Device> fetchAllDevice();
    List<Device> fetchDeviceByBrand(String brandName);
    List<Device> fetchDeviceByState(String state);
    void deleteDevice(String deviceName);
}
