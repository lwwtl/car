package com.rlw.common.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePasswordDto  implements Serializable {
    @JsonProperty(value = "userId")
    private Long userId;

    @JsonProperty(value = "oldPassword")
    private String oldPassword;

    @JsonProperty(value = "newPassword")
    private String newPassword;

    @JsonProperty(value = "checkPassword")
    private String checkPassword;
}
