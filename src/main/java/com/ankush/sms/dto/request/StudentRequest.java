package com.ankush.sms.dto.request;

import com.ankush.sms.enums.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Date of birth is required")
    private LocalDate dob;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Student code is required")
    private String studentCode;

    @Email(message = "Invalid email")
    private String email;

    private String mobile;

    private String parentName;

    @Valid
    @NotEmpty(message = "At least one address is required")
    private List<AddressRequest> addresses;
}
