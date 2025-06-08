package com.devices.api.domain.entity;

import com.devices.api.domain.enums.StateEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "brand", length = 50)
    private String brand;
    @Column(name = "creationTime")
    private Time creationTime;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StateEnums state;

}
