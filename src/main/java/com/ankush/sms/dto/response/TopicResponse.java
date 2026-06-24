package com.ankush.sms.dto.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicResponse {

    private Long id;
    private String topicName;
}
