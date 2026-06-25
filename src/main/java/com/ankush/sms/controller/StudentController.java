package com.ankush.sms.controller;

import com.ankush.sms.dto.request.StudentLoginRequest;
import com.ankush.sms.dto.request.StudentProfileUpdateRequest;
import com.ankush.sms.dto.response.CourseResponse;
import com.ankush.sms.dto.response.LoginResponse;
import com.ankush.sms.dto.response.StudentResponse;
import com.ankush.sms.dto.response.TopicResponse;
import com.ankush.sms.service.StudentService;
import com.ankush.sms.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Student APIs", description = "Operations performed by Students")
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Student Login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody StudentLoginRequest request) {

        return ResponseEntity.ok(studentService.login(request));
    }

    @Operation(summary = "Get Logged In Student Profile")
    @GetMapping("/profile")
    public ResponseEntity<StudentResponse> getProfile() {

        return ResponseEntity.ok(studentService.getStudentProfile());
    }

    @Operation(summary = "Update Student Profile")
    @PutMapping("/profile")
    public ResponseEntity<StudentResponse> updateProfile(
            @Valid @RequestBody StudentProfileUpdateRequest request) {

        return ResponseEntity.ok(studentService.updateProfile(request));
    }

    @Operation(summary = "View Assigned Courses")
    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> getAssignedCourses() {

        return ResponseEntity.ok(studentService.getAssignedCourses());
    }

    @Operation(summary = "View Assigned Topics")
    @GetMapping("/topics")
    public ResponseEntity<List<TopicResponse>> getAssignedCourseTopics() {

        return ResponseEntity.ok(studentService.getAssignedCourseTopics());
    }

    @Operation(summary = "Leave Assigned Course")
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<ApiResponse> leaveCourse(
            @PathVariable Long courseId) {

        studentService.leaveCourse(courseId);

        ApiResponse response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Course left successfully.")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(Authentication authentication) {

        return ResponseEntity.ok(
                "Welcome " + authentication.getName()
        );
    }
}
