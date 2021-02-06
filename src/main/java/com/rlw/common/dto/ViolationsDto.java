package com.rlw.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ViolationsDto implements Serializable {

    @JsonProperty(value = "violationsId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long violationsId;

    @JsonProperty(value = "orderId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    @JsonProperty(value = "userId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @JsonProperty(value = "carNo")
    private String carNo;

    @JsonProperty(value = "violationsIf")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long violationsIf;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "violationsTime")
    private LocalDateTime violationsTime;

    @JsonProperty(value = "violationsSite")
    private String violationsSite;

    @JsonProperty(value = "violationsDetail")
    private String violationsDetail;

    @JsonProperty(value = "violationsCost")
    private BigDecimal violationsCost;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "violationsCreate")
    private LocalDateTime violationsCreate;

    @JsonProperty(value = "violationsRecorder")
    private String violationsRecorder;
}
