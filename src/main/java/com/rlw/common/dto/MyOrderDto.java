package com.rlw.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MyOrderDto implements Serializable {
    /**
     * 将long类型转化为string类型传值给前端
     * 因为java的long类型是18位，js的long类型是16位，会导致精度丢失
     * */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    private String state;
}
