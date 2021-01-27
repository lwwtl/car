package com.rlw.entity;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JSONField(name="orderId")
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    @JSONField(name = "userId")
    private Long userId;

    @JSONField(name = "carId")
    private Long carId;

    @JSONField(name = "orderStart")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "orderEnd")
    private LocalDateTime orderEnd;

    @JSONField(name = "orderSource")
    private String orderSource;

    @JSONField(name = "orderDestination")
    private String orderDestination;

    @JSONField(name = "orderRentDays")
    private Long orderRentDays;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "orderCreate")
    private LocalDateTime orderCreate;

    @JSONField(name = "orderState")
    private String orderState;

    @JSONField(name = "orderTotal")
    private BigDecimal orderTotal;


}
