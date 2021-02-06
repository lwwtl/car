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
public class RepairDto implements Serializable {
    @JsonProperty(value = "repairId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long repairId;

    @JsonProperty(value = "orderId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    @JsonProperty(value = "carNo")
    @JsonSerialize(using = ToStringSerializer.class)
    private String carNo;

    @JsonProperty(value = "repairIf")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long repairIf;

    @JsonProperty(value = "repairPart")
    private String repairPart;

    @JsonProperty(value = "repairDetail")
    private String repairDetail;

    @JsonProperty(value = "repairCost")
    private BigDecimal repairCost;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "repairCreate")
    private LocalDateTime repairCreate;

    @JsonProperty(value = "repairRecorder")
    private String repairRecorder;
}
