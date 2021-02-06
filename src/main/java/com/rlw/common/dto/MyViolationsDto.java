package com.rlw.common.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MyViolationsDto  implements Serializable {

    @JsonProperty(value = "orderId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    @JsonProperty(value = "userId")
    private Long userId;

    @JsonProperty(value = "carName")
    private String carName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "violationsTime")
    private LocalDateTime violationsTime;

    @JsonProperty(value = "violationsSite")
    private String violationsSite;

    @JsonProperty(value = "violationsDetail")
    private String violationsDetail;

    @JsonProperty(value = "violationsCost")
    private BigDecimal violationsCost;


}
