package com.rlw.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderStatisticsDto implements Serializable {
    private String days;
    private Integer quantity;
}
