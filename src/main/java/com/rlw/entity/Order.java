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
 * @since 2021-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 将long类型转化为string类型传值给前端
     * 因为java的long类型是18位，js的long类型是16位，会导致精度丢失
     * 返回String给前端
     * */
    @JsonProperty(value = "orderId")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "order_id")
    private Long orderId;

    @JsonProperty(value = "userId")
    @TableField(value = "user_id")
    private Long userId;

    @JsonProperty(value = "carId")
    @TableField(value = "car_id")
    private Long carId;

    @JsonProperty(value = "orderStart")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "order_start")
    private LocalDateTime orderStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "orderEnd")
    @TableField(value = "order_end")
    private LocalDateTime orderEnd;

    @JsonProperty(value = "orderSource")
    @TableField(value = "order_source")
    private String orderSource;

    @JsonProperty(value = "orderDestination")
    @TableField(value = "order_destination")
    private String orderDestination;

    @JsonProperty(value = "orderRentDays")
    @TableField(value = "order_rent_days")
    private Long orderRentDays;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "orderCreate")
    @TableField(value = "order_create")
    private LocalDateTime orderCreate;

    @JsonProperty(value = "orderState")
    @TableField(value = "order_state")
    private String orderState;

    @JsonProperty(value = "orderTotal")
    @TableField(value = "order_total")
    private BigDecimal orderTotal;

    @JsonProperty(value = "orderExtra")
    @TableField(value = "order_extra")
    private BigDecimal orderExtra;


}
