package com.devices.api.service;

import com.devices.api.domain.entity.Device;
import com.devices.api.rest.dto.DeviceDTO;

import java.util.List;

public interface DeviceService {
    Device create(DeviceDTO dto);
    DeviceDTO updateFullyDevice(DeviceDTO dto, Long id);
    DeviceDTO updatePartialDevice(DeviceDTO dto, Long id);
    DeviceDTO fetchSingleDevice(Long deviceId);
    List<Device> fetchAllDevice();
    List<DeviceDTO> fetchDevicesByBrand(String brand);
    List<DeviceDTO> fetchDevicesByState(String state);
    void deleteDevice(Long deviceId);
}
