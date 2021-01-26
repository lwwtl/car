package com.rlw.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto implements Serializable {
    /*车牌*/
    private String cno;
    /*用户账号*/
    private String account;
    /*订单编号*/
    private Long id;
}
