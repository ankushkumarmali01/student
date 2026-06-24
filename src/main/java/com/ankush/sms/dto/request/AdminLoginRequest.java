package com.ankush.sms.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
