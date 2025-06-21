package com.devices.api.rest.controller;


import com.devices.api.domain.entity.Device;
import com.devices.api.rest.dto.DeviceDTO;
import com.devices.api.service.impl.DeviceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/device")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceServiceImpl service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Device createDevice(@RequestBody DeviceDTO dto){
        return service.create(dto);
    }

    //Fully update an existinng device.
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceDTO updateFullyDevice(@RequestBody DeviceDTO dto, @PathVariable("id") Long deviceId){
        return service.updateFullyDevice(dto,deviceId);
    }
    //Partial update an existinng device.

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceDTO updatePartialDevice(@RequestBody DeviceDTO dto, @PathVariable("id") Long deviceId){
          return service.updatePartialDevice(dto,deviceId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceDTO findSingleDevice(@PathVariable("id") Long deviceId){
        return service.fetchSingleDevice(deviceId);
    }
    //Getting all devices.
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Device> fetchAllDevice(){
        return service.fetchAllDevice();
    }
    //Fetch devices by brand.
    @GetMapping("/brand/{brand}")
    @ResponseStatus(HttpStatus.OK)
    public List<DeviceDTO> fetchDeviceByBrand(@PathVariable("brand") String brand ){
        return service.fetchDevicesByBrand(brand);
    }
    //Fetch devices by state.
    @GetMapping("/state/{state}")
    @ResponseStatus(HttpStatus.OK)
    public List<DeviceDTO> fetchDeviceByState(@PathVariable("state") String state ){
        return service.fetchDevicesByState(state);
    }

    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable("id") Long deviceId){
        service.deleteDevice(deviceId);
    }
}
