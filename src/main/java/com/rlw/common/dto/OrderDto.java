package com.rlw.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto implements Serializable {
    /*车牌*/
    private String cno;
    /*用户账号*/
    private String account;
    /*订单编号*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String state;
}
