package com.rlw.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author 饶立玮
 * @since 2020-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "car_id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "车牌号不能为空")
    @TableField("car_no")
    private String no;

    @NotBlank(message = "名称不能为空")
    @TableField("car_name")
    private String name;

    @TableField("car_model")
    private String model;

    @TableField("car_type")
    private String type;

    @TableField("car_box")
    private String box;

    @TableField("car_output")
    private String output;

    @TableField("car_seat")
    private Long seat;

    @TableField("car_brand")
    private String brand;

    @TableField("car_price")
    private BigDecimal price;

    @TableField("car_rent")
    private BigDecimal rent;

    @TableField("car_img")
    private String img;

    @TableField("car_state")
    private String state;

    @TableField("car_store")
    private String store;

    @TableField("car_region")
    private String region;


}
