package com.devices.api.rest.dto;

import com.devices.api.domain.enums.StateEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {
    private String name;
    private String brand;
    private Time creationTime;
    private StateEnums state;
}
