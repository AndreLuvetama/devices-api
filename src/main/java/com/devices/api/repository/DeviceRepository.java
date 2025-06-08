
package com.devices.api.repository;

import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enums.StateEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByBrand(String brand);
    List<Device> findByState(StateEnums state);
}
