package com.ankush.sms.service;

import com.ankush.sms.dto.request.StudentLoginRequest;
import com.ankush.sms.dto.request.StudentProfileUpdateRequest;
import com.ankush.sms.dto.response.CourseResponse;
import com.ankush.sms.dto.response.LoginResponse;
import com.ankush.sms.dto.response.StudentResponse;
import com.ankush.sms.dto.response.TopicResponse;

import java.util.List;

public interface StudentService {

        LoginResponse login(StudentLoginRequest request);

        StudentResponse getStudentProfile();

        StudentResponse updateProfile(StudentProfileUpdateRequest request);

        List<CourseResponse> getAssignedCourses();

        List<TopicResponse> getAssignedCourseTopics();

        void leaveCourse(Long courseId);
}