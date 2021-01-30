package com.rlw.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePasswordDto  implements Serializable {
    @JSONField(name = "userId")
    private Long userId;

    @JSONField(name = "oldPassword")
    private String oldPassword;

    @JSONField(name = "newPassword")
    private String newPassword;

    @JSONField(name = "checkPassword")
    private String checkPassword;
}
