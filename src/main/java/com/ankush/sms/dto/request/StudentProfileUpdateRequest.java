package com.ankush.sms.dto.request;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileUpdateRequest {

    private String email;

    private String mobile;

    private String parentName;

    @Valid
    private List<AddressRequest> addresses;
}
