package com.devices.api.controller;

import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enums.StateEnums;
import com.devices.api.rest.controller.DeviceController;
import com.devices.api.rest.dto.DeviceDTO;
import com.devices.api.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeviceService deviceService;

    @Test
    void createDevice() throws Exception {
        DeviceDTO requestDTO = new DeviceDTO();
        requestDTO.setName("Device01");
        requestDTO.setBrand("Brand01");
       requestDTO.setCreationTime(Time.valueOf(LocalTime.now()));
       // requestDTO.setCreationTime(fixedTime);
        requestDTO.setState(StateEnums.AVAILABLE);

        Device response = new Device(1L, "Device01", "Brand01", Time.valueOf(LocalTime.now()), StateEnums.AVAILABLE);

        when(deviceService.create(any(DeviceDTO.class))).thenReturn(response);

        mockMvc.perform(post("/v1/api/device")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Device01"))
                .andExpect(jsonPath("$.brand").value("Brand01"))
                .andExpect(jsonPath("$.creationTime").value(Time.valueOf(LocalTime.now()).toString()))
                .andExpect(jsonPath("$.state").value(StateEnums.AVAILABLE.name()));
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        DeviceService deviceService() {
            return Mockito.mock(DeviceService.class);
        }
    }
}
