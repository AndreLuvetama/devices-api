package com.devices.api.service.impl;

import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enums.StateEnums;
import com.devices.api.repository.DeviceRepository;
import com.devices.api.rest.dto.DeviceDTO;
import com.devices.api.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository repository;

    @Override
    public Device create(DeviceDTO dto) {
        Device device = new Device();

        device.setBrand(dto.getBrand());
        device.setName(dto.getName());
        device.setCreationTime(Time.valueOf(LocalTime.now())); //get the current system time (just hour, minute and second, without the date).
        device.setStatus(StateEnums.AVAILABLE);
        Device deviceSave =  repository.save(device);

        return deviceSave;
    }

    @Override
    public void updateDevice(Long id, DeviceDTO dto) {

    }

    @Override
    public Device fetchDevice(String deviceName) {
        return null;
    }

    @Override
    public List<Device> fetchAllDevice() {
        return List.of();
    }

    @Override
    public List<Device> fetchDeviceByBrand(String brandName) {
        return List.of();
    }

    @Override
    public List<Device> fetchDeviceByState(String state) {
        return List.of();
    }

    @Override
    public void deleteDevice(String deviceName) {

    }
}
