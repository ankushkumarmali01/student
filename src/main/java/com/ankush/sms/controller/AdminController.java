package com.ankush.sms.controller;

import com.ankush.sms.dto.request.AdminLoginRequest;
import com.ankush.sms.dto.request.CourseRequest;
import com.ankush.sms.dto.request.StudentRequest;
import com.ankush.sms.dto.response.CourseResponse;
import com.ankush.sms.dto.response.LoginResponse;
import com.ankush.sms.dto.response.StudentResponse;
import com.ankush.sms.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin APIs", description = "Operations performed by Admin")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Admin Login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody AdminLoginRequest request) {

        return ResponseEntity.ok(adminService.login(request));
    }

    @Operation(summary = "Create Student")
    @PostMapping("/students")
    public ResponseEntity<StudentResponse> createStudent(
            @Valid @RequestBody StudentRequest request) {

        return new ResponseEntity<>(
                adminService.createStudent(request),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Update Student")
    @PutMapping("/students/{id}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {

        return ResponseEntity.ok(
                adminService.updateStudent(id, request)
        );
    }

    @Operation(summary = "Get Student by ID")
    @GetMapping("/students/{id}")
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.getStudentById(id)
        );
    }

    @Operation(summary = "Get All Students")
    @GetMapping("/students")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {

        return ResponseEntity.ok(
                adminService.getAllStudents()
        );
    }

    @Operation(summary = "Search Students by Name")
    @GetMapping("/students/search")
    public ResponseEntity<List<StudentResponse>> searchStudents(
            @RequestParam String name) {

        return ResponseEntity.ok(
                adminService.searchStudentsByName(name)
        );
    }

    @Operation(summary = "Delete Student")
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id) {

        adminService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create New Course")
    @PostMapping("/courses")
    public ResponseEntity<CourseResponse> createCourse(
            @Valid @RequestBody CourseRequest request) {

        return new ResponseEntity<>(
                adminService.createCourse(request),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Update Course")
    @PutMapping("/courses/{id}")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request) {

        return ResponseEntity.ok(
                adminService.updateCourse(id, request)
        );
    }

    @Operation(summary = "Get Course by ID")
    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseResponse> getCourseById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.getCourseById(id)
        );
    }

    @Operation(summary = "Get All Courses")
    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {

        return ResponseEntity.ok(
                adminService.getAllCourses()
        );
    }

    @Operation(summary = "Delete Course")
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable Long id) {

        adminService.deleteCourse(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign Course to Student")
    @PostMapping("/courses/{courseId}/assign/{studentId}")
    public ResponseEntity<String> assignCourse(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {

        adminService.assignCourseToStudent(studentId, courseId);

        return ResponseEntity.ok("Course assigned successfully.");
    }

    @Operation(summary = "Get Students Assigned to a Course")
    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<StudentResponse>> getStudentsByCourse(
            @PathVariable Long courseId) {

        return ResponseEntity.ok(
                adminService.getStudentsByCourse(courseId)
        );
    }

}