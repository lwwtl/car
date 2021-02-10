package com.rlw.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatHotStaticsDto implements Serializable {
    private String name;
    private Integer total;
}
