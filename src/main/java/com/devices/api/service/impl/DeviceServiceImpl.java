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
import org.springframework.stereotype.Service;


import java.sql.Time;
import java.time.LocalTime;
import java.util.List;


import static org.springframework.util.ObjectUtils.isEmpty;

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
        if(dto.getCreationTime() != null){
            throw new IllegalArgumentException("Creation time can not be updated");
        }
        Device existingDevice = repository.findById(deviceId)
                .orElseThrow(()->new DeviceNotFoundException("Device not Found"));

        //Valid if state is in-use
        if (existingDevice.getState() == StateEnums.INUSE) {
            if (!existingDevice.getName().equals(dto.getName()) ||
                    !existingDevice.getBrand().equals(dto.getBrand())) {
                throw new IllegalStateException("Name and brand properties cannot be updated if the device is in use.");
            }
        }

        Device updatedDevice = DeviceMapper.putDeviceFromDto(existingDevice, dto);
                        repository.save(updatedDevice);
        return  updatedDevice;
    }

    //Update Partial Device
    @Override
    @Transactional
    public Device updatePartialDevice(DeviceDTO dto, Long deviceId) {
        if(dto.getCreationTime() != null){
            throw new IllegalArgumentException("Creation time can not be updated");
        }
        Device existingDevice = repository.findById(deviceId)
                .orElseThrow(()->new DeviceNotFoundException("Device not Found"));
        //Valid if the state is in use and the field that will be updated
        if(existingDevice.getState() == StateEnums.INUSE){
            if(dto.getName() != null || dto.getBrand() != null){
                throw new IllegalArgumentException("Device in USE can not updated");
            }
        }
        Device updatedDevice = DeviceMapper.patchDeviceFromDto(existingDevice, dto);
        return repository.save(updatedDevice);
    }
    @Override
    public Device fetchSingleDevice(Long deviceId) {
        if(isEmpty(deviceId)){
            throw new DeviceNotFoundException("Enter the ID");
        }
        return repository.findById(deviceId)
                .orElseThrow(()->new DeviceNotFoundException("Device not Found"));
    }

    @Override
    public List<Device> fetchAllDevice() {
        return repository.findAll();
    }

    @Override
    public List<Device> fetchDevicesByBrand(String brand) {
        if(brand == null || brand.trim().isEmpty()){
            throw new IllegalArgumentException("Brand must be provided");
        }
        List<Device> devices = repository.findByBrand(brand);
        if(devices.isEmpty()){
            throw new DeviceNotFoundException("Device not Found");
        }
        return devices;
    }

    @Override
    public List<Device> fetchDevicesByState(String state) {
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("State must be provided");
        }

        StateEnums stateEnum;
        try {
            stateEnum = StateEnums.valueOf(state.toUpperCase()); // Convert the String for Enum
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid state value: " + state);
        }
        List<Device> devices = repository.findByState(stateEnum);

        if (devices.isEmpty()) {
            throw new DeviceNotFoundException("No devices found with state: " + state);
        }

        return devices;
    }


    @Override
    public void deleteDevice(Long deviceId) {
        if(isEmpty(deviceId)){
            throw new DeviceNotFoundException("Device must be provided");
        }
        Device existingDevice = repository.findById(deviceId)
                .orElseThrow(()-> new DeviceNotFoundException("Device not Found"));

        if(existingDevice.getState() == StateEnums.INUSE){
            throw new IllegalArgumentException("Device in USE can not DELETED");
        }

        repository.delete(existingDevice);
    }

}
