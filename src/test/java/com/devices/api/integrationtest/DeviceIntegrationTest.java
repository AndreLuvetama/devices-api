package com.devices.api.integrationtest;


import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enums.StateEnums;
import com.devices.api.rest.dto.DeviceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")  // Loading the application-test file
public class DeviceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createDevice() throws Exception{
        DeviceDTO requestDTO = new DeviceDTO();
        requestDTO.setName("Device01");
        requestDTO.setBrand("Brand01");
        requestDTO.setCreationTime(Time.valueOf(LocalTime.now()));
        requestDTO.setState(StateEnums.AVAILABLE);

      String response =  mockMvc.perform(post("/v1/api/device")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
                Long deviceId = 1L;

        mockMvc.perform(post("/v1/api/device")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Device01"))
                .andExpect(jsonPath("$.brand").value("Brand01"))
                .andExpect(jsonPath("$.creationTime").value(Time.valueOf(LocalTime.now()).toString()))
                .andExpect(jsonPath("$.state").value(StateEnums.AVAILABLE.name()));
    }


}
