package com.devices.api.service.impl;

import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enums.StateEnums;
import com.devices.api.exception.DeviceNotFoundException;
import com.devices.api.repository.DeviceRepository;
import com.devices.api.rest.dto.DeviceDTO;
import com.devices.api.rest.mapper.DeviceMapper;
import com.devices.api.service.DeviceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.internal.Nullability;
import org.springframework.data.util.NullableUtils;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

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
        device.setState(StateEnums.AVAILABLE);
        Device deviceSave =  repository.save(device);

        return deviceSave;
    }

    //Fully update an existing device.
    @Override
    @Transactional
    public Device updateFullyDevice(DeviceDTO dto, Long deviceId) {
        Device device = repository.findById(deviceId)
                .orElseThrow(DeviceNotFoundException::new);
        Device updatedDevice = DeviceMapper.putDeviceFromDto(device, dto);
                        repository.save(updatedDevice);
        return  updatedDevice;
    }

    /*@Override
    @Transactional
    public Device updatePartialDevice(DeviceDTO dto, Long deviceId) {
        Device existingDevice = repository.findById(deviceId)
                .orElseThrow(DeviceNotFoundException::new);

        if (dto.getName() != null) {
            existingDevice.setName(dto.getName());
        }
        if (dto.getBrand() != null) {
            existingDevice.setBrand(dto.getBrand());
        }
        if (dto.getState() != null) {
            existingDevice.setState(dto.getState());
        }

        return repository.save(existingDevice);
    }*/

    @Override
    @Transactional
    public Device updatePartialDevice(Map<String, Object> request, Long deviceId) {
        Device existingDevice = repository.findById(deviceId)
                .orElseThrow(DeviceNotFoundException::new);


            request.forEach((k,v) -> {

            Field field = ReflectionUtils.findField(Device.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, existingDevice, v);

        });
        return  repository.save(existingDevice);
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
