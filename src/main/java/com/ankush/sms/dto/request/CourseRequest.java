package com.ankush.sms.dto.request;

import com.ankush.sms.enums.CourseType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequest {

    @NotBlank
    private String courseName;

    @NotBlank
    private String description;

    @NotNull
    private CourseType courseType;

    @NotNull
    private Integer duration;

    @Valid
    @NotEmpty
    private List<TopicRequest> topics;
}
