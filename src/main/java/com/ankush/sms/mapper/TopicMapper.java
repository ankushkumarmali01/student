package com.ankush.sms.mapper;

import com.ankush.sms.dto.request.TopicRequest;
import com.ankush.sms.dto.response.TopicResponse;
import com.ankush.sms.entity.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {

    public Topic toEntity(TopicRequest request){

        if(request == null){
            return null;
        }

        return Topic.builder().topicName(request.getTopicName())
                .build();
    }

    public TopicResponse toResponse(Topic topic){
        if(topic == null){
            return null;
        }
        return TopicResponse.builder().id(topic.getId())
                .topicName(topic.getTopicName())
                .build();
    }
}
