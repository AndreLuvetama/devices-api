package com.devices.api.service.impl;

import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enums.StateEnums;
import com.devices.api.exception.DeviceIllegalStateException;
import com.devices.api.exception.DeviceNotFoundException;
import com.devices.api.repository.DeviceRepository;
import com.devices.api.rest.dto.DeviceDTO;
import com.devices.api.rest.mapper.DeviceMapper;
import com.devices.api.service.DeviceService;
import com.devices.api.util.ValidationData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository repository;
    @Autowired
    private ValidationData validationData;



    @Override
    public Device create(DeviceDTO dto) {
        Device device = new Device();

        device.setBrand(dto.getBrand());
        device.setName(dto.getName());
        device.setCreationTime(Time.valueOf(LocalTime.now())); //get the current system time (just hour, minute and second, without the date).
        device.setState(StateEnums.AVAILABLE);

        return repository.save(device);
    }

    //Fully update an existing device.
    @Override
    @Transactional
    public DeviceDTO updateFullyDevice(DeviceDTO dto, Long deviceId) {
        //Validate if the deviceId field is filled in
        validationData.validateDeviceId(deviceId);

        if(dto.getCreationTime() != null){
            throw new DeviceIllegalStateException("Creation time can not be updated");
        }
        Device existingDevice = repository.findById(deviceId)
                .orElseThrow(()->new DeviceNotFoundException("Device not Found"));

        //Brand and name can not be updated if state is INUSE
        if (existingDevice.getState() == StateEnums.INUSE) {
            if (!existingDevice.getName().equals(dto.getName()) ||
                    !existingDevice.getBrand().equals(dto.getBrand())) {
                throw new DeviceIllegalStateException("Name and brand properties cannot be updated if the device is in use.");
            }
        }

        Device updatedDevice = DeviceMapper.putDeviceFromDto(existingDevice, dto);
                        repository.save(updatedDevice);
        return DeviceMapper.mapDeviceToDTO(updatedDevice);
    }

    //Update Partial Device
    @Override
    @Transactional
    public DeviceDTO updatePartialDevice(DeviceDTO dto, Long deviceId) {
        //Validate if the deviceId field is filled in
        validationData.validateDeviceId(deviceId);

        if(dto.getCreationTime() != null){
            throw new DeviceIllegalStateException("Creation time can not be updated");
        }
        Device existingDevice = repository.findById(deviceId)
                .orElseThrow(()->new DeviceNotFoundException("Device not Found"));
        //Valid if the state is in use and the field that will be updated
        if(existingDevice.getState() == StateEnums.INUSE){
            if(dto.getName() != null || dto.getBrand() != null){
                throw new DeviceIllegalStateException("Device in USE can not updated");
            }
        }
        Device updatedDevice = DeviceMapper.patchDeviceFromDto(existingDevice, dto);
         repository.save(updatedDevice);
        return DeviceMapper.mapDeviceToDTO(updatedDevice);
    }
    //Getting a single device
    @Override
    public DeviceDTO fetchSingleDevice(Long deviceId) {
        //Validate if the deviceId field is filled in
         validationData.validateDeviceId(deviceId);

        Device device = repository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device not Found!"));
        return DeviceMapper.mapDeviceToDTO(device);

    }

    @Override
    public List<Device> fetchAllDevice() {
        return repository.findAll();
    }

    //Getting a list of devices by brand
    @Override
    public List<DeviceDTO> fetchDevicesByBrand(String brand) {
        if(brand == null || brand.trim().isEmpty()){
            throw new DeviceIllegalStateException("Brand must be provided");
        }
        List<Device> devices = repository.findByBrand(brand);
        if(devices.isEmpty()){
            throw new DeviceNotFoundException("Device not Found");
        }
        //Performs a bulk conversion of a list of entities (Device) to a list of DTOs
         return devices.stream().map(DeviceMapper::mapDeviceToDTO).collect(Collectors.toList());
    }

    // Getting devices with the same state
    @Override
    public List<DeviceDTO> fetchDevicesByState(String state) {
        if (state == null || state.trim().isEmpty()) {
            throw new DeviceIllegalStateException("State must be provided");
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

        //Performs a bulk conversion of a list of entities (Device) to a list of DTOs
        return devices.stream().map(DeviceMapper::mapDeviceToDTO).collect(Collectors.toList());
    }

   // Deleting a single device
    @Override
    public void deleteDevice(Long deviceId) {
       validationData.validateDeviceId(deviceId);

        Device existingDevice = repository.findById(deviceId)
                .orElseThrow(()-> new DeviceNotFoundException("Device not Found"));

        if(existingDevice.getState() == StateEnums.INUSE){
            throw new DeviceIllegalStateException("Device in USE can not DELETED");
        }

        repository.delete(existingDevice);
    }

}
