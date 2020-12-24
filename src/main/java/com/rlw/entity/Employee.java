package com.rlw.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author 饶立玮
 * @since 2020-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @TableId(value = "emp_id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "账号不能为空")
    @TableField("emp_account")
    private String account;

    @NotBlank(message = "密码不能为空")
    @TableField("emp_password")
    private String password;

    @NotBlank(message = "姓名不能为空")
    @TableField("emp_name")
    private String name;

    @NotBlank(message = "性别不能为空")
    @TableField("emp_gender")
    private String gender;

    @NotNull(message = "年龄不能为空")
    @TableField("emp_age")
    private Long age;

    @NotBlank(message = "手机号码不能为空")
    @TableField("emp_mobile")
    private String mobile;

    @NotBlank(message = "地址不能为空")
    @TableField("emp_address")
    private String address;

    @TableField("emp_last_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastTime;

    @NotBlank(message = "角色不能为空")
    @TableField("emp_role")
    private String role;

    @TableField("emp_salt")
    private String salt;

}
