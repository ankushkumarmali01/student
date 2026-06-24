package com.ankush.sms.mapper;

import com.ankush.sms.dto.request.CourseRequest;
import com.ankush.sms.dto.response.CourseResponse;
import com.ankush.sms.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseMapper {

    private final TopicMapper topicMapper;

    public Course toEntity(CourseRequest request) {

        if (request == null) {
            return null;
        }

        Course course = Course.builder()
                .courseName(request.getCourseName())
                .description(request.getDescription())
                .courseType(request.getCourseType())
                .duration(request.getDuration())
                .build();

        course.getTopics().addAll(
                request.getTopics()
                        .stream()
                        .map(topicMapper::toEntity)
                        .toList()
        );

        course.getTopics().forEach(topic -> topic.setCourse(course));

        return course;
    }

    public CourseResponse toResponse(Course course) {

        if (course == null) {
            return null;
        }

        return CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .courseType(course.getCourseType())
                .duration(course.getDuration())
                .topics(course.getTopics()
                        .stream()
                        .map(topicMapper::toResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
