package com.ankush.sms.dto.response;
import com.ankush.sms.enums.CourseType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponse {
    private Long id;
    private String courseName;
    private String description;
    private CourseType courseType;
    private Integer duration;
    private List<TopicResponse> topics;
}
