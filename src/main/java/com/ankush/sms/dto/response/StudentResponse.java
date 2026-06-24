package com.ankush.sms.dto.response;
import com.ankush.sms.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
    private Long id;
    private String name;
    private LocalDate dob;
    private Gender gender;
    private String email;
    private String mobile;
    private String parentName;

    private List<AddressResponse> addresses;
    private List<CourseResponse> courses;
}
