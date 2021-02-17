package com.rlw.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CarTypeDto implements Serializable {
    private String city;
    private String type;
    private String brand;
    private String start;
    private String end;
}
