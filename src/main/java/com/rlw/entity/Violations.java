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
@TableName("t_violations")
public class Violations implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "violationsId")
    @TableId(value = "violations_id", type = IdType.AUTO)
    private Long violationsId;

    @JsonProperty(value = "orderId")
    @TableField(value = "order_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    @JsonProperty(value = "userId")
    @TableField(value = "user_id")
    private Long userId;

    @JsonProperty(value = "carId")
    @TableField(value = "car_id")
    private Long carId;

    @JsonProperty(value = "violationsIf")
    @TableField(value = "violations_if")
    private Long violationsIf;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "violationsTime")
    @TableField(value = "violations_time")
    private LocalDateTime violationsTime;

    @JsonProperty(value = "violationsSite")
    @TableField(value = "violations_site")
    private String violationsSite;

    @JsonProperty(value = "violationsDetail")
    @TableField(value = "violations_detail")
    private String violationsDetail;

    @JsonProperty(value = "violationsCost")
    @TableField(value = "violations_cost")
    private BigDecimal violationsCost;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "violationsCreate")
    @TableField(value = "violations_create")
    private LocalDateTime violationsCreate;

    @JsonProperty(value = "violationsRecorder")
    @TableField(value = "violations_recorder")
    private String violationsRecorder;


}
