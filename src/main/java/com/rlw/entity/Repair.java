package com.rlw.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 饶立玮
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_repair")
public class Repair implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "repairId")
    @TableId(value = "repair_id", type = IdType.AUTO)
    private Long repairId;

    @JsonProperty(value = "orderId")
    @TableField(value = "order_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    @JsonProperty(value = "carId")
    @TableField(value = "car_id")
    private Long carId;

    @JsonProperty(value = "repairIf")
    @TableField(value = "repair_if")
    private Long repairIf;

    @JsonProperty(value = "repairPart")
    @TableField(value = "repair_part")
    private String repairPart;

    @JsonProperty(value = "repairDetail")
    @TableField(value = "repair_detail")
    private String repairDetail;

    @JsonProperty(value = "repairCost")
    @TableField(value = "repair_cost")
    private BigDecimal repairCost;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "repairCreate")
    @TableField(value = "repair_create")
    private LocalDateTime repairCreate;

    @JsonProperty(value = "repairRecorder")
    @TableField(value = "repair_recorder")
    private String repairRecorder;


}
