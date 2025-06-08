package com.devices.api.service;

import com.devices.api.domain.entity.Device;
import com.devices.api.rest.dto.DeviceDTO;

import java.util.List;

public interface DeviceService {
    Device create(DeviceDTO dto);
    Device updateFullyDevice(DeviceDTO dto, Long id);
    Device updatePartialDevice(DeviceDTO dto, Long id);
    Device fetchSingleDevice(Long deviceId);
    List<Device> fetchAllDevice();
    List<Device> fetchDevicesByBrand(String brand);
    List<Device> fetchDevicesByState(String state);
    void deleteDevice(Long deviceId);
}
