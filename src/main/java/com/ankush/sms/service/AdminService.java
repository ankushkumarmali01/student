package com.ankush.sms.service;

import com.ankush.sms.dto.request.AdminLoginRequest;
import com.ankush.sms.dto.request.CourseRequest;
import com.ankush.sms.dto.request.StudentRequest;
import com.ankush.sms.dto.response.CourseResponse;
import com.ankush.sms.dto.response.LoginResponse;
import com.ankush.sms.dto.response.StudentResponse;

import java.util.List;

public interface AdminService {

    LoginResponse login(AdminLoginRequest request);

    StudentResponse createStudent(StudentRequest request);

    StudentResponse updateStudent(Long id, StudentRequest request);

    void deleteStudent(Long id);

    StudentResponse getStudentById(Long id);

    List<StudentResponse> getAllStudents();

    List<StudentResponse> searchStudentsByName(String name);

    CourseResponse createCourse(CourseRequest request);

    CourseResponse updateCourse(Long id, CourseRequest request);

    CourseResponse getCourseById(Long courseId);

    void deleteCourse(Long id);

    List<CourseResponse> getAllCourses();

    void assignCourseToStudent(Long studentId, Long courseId);

    List<StudentResponse> getStudentsByCourse(Long courseId);
}
