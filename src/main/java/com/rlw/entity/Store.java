package com.rlw.entity;

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
 * @since 2021-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_store")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "store_id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "名称不能为空")
    @TableField("store_name")
    private String name;

    @TableField("store_address")
    private String address;

    @TableField("store_admin")
    private String admin;

    @TableField("store_tel")
    private String tel;

    @TableField("store_detail")
    private String detail;


}
