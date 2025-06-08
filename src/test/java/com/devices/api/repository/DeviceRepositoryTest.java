package com.devices.api.repository;

import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enums.StateEnums;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DeviceRepositoryTest {

    @Autowired
    DeviceRepository repository;

    //Creation device teste
    @Test
    public void saveDevice(){
        Device device = new Device();
        device.setName("device002");
        device.setBrand("Brand03");
        device.setCreationTime(Time.valueOf(LocalTime.now()));
        device.setState(StateEnums.AVAILABLE);
        var deviceSaved = repository.save(device);

        System.out.println("Device saved successfully "+ deviceSaved);
    }

    //Update a device test
    @Test
    public void update(){
        Long id = 204L;
        Optional<Device> device = repository.findById(id);
        if(device.isPresent()){
            Device existingDevice = device.get();
            existingDevice.setBrand("new brand01");
            existingDevice.setName("Brand012");
            existingDevice.setState(StateEnums.INUSE);
            repository.save(existingDevice);
        }
    }

    @Test
    public void listDevice(){
        List<Device> list = repository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    public void deleteSingleDevice(){
        Long id = 202L;
        repository.deleteById(id);
    }


}
