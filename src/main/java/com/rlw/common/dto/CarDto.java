package com.rlw.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@Data
public class CarDto implements Serializable {
    private String no;
    private String name;
    private String region;

    private String start;

    private String end;
    private String source;
    private String destination;
}
