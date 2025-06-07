package com.devices.api.rest.controller;


import com.devices.api.domain.entity.Device;
import com.devices.api.rest.dto.DeviceDTO;
import com.devices.api.service.impl.DeviceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/device")
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
    public Device updateFullyDevice(@RequestBody DeviceDTO dto, @PathVariable("id") Long deviceId){
        return service.updateFullyDevice(dto,deviceId);
    }
    //Partial update an existinng device.

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Device updatePartialDevice(@RequestBody DeviceDTO dto, @PathVariable("id") Long deviceId){
          return service.updateFullyDevice(dto,deviceId);
    }

}
