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
 * @since 2021-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    @TableField("user_account")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @TableField("user_password")
    private String password;

    /**
     * 姓名
     */
    @TableField("user_name")
    private String name;

    /**
     * 性别
     */
    @TableField("user_gender")
    private String gender;

    /**
     * 年龄
     */
    @TableField("user_age")
    private Long age;

    /**
     * 手机号码
     */
    @TableField("user_mobile")
    private String mobile;

    /**
     * 邮箱
     */
    @TableField("user_email")
    private String email;

    /**
     * 住址
     */
    @TableField("user_address")
    private String address;

    /**
     * 身份证号
     */
    @TableField("user_number")
    private String number;

    /**
     * 驾照编号
     */
    @TableField("user_license")
    private String license;

    /**
     * 帐号状态：是否封禁
     */
    @TableField("user_state")
    private Long state;

    /**
     * 随机盐
     */
    @TableField("user_salt")
    private String salt;


}
