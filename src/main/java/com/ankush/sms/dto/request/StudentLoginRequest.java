package com.ankush.sms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentLoginRequest {
    @NotBlank
    private String studentCode;

    @NotNull
    private LocalDate dob;

}
