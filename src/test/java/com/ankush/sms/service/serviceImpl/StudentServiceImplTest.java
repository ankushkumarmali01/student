package com.ankush.sms.service.serviceImpl;

import com.ankush.sms.dto.request.StudentLoginRequest;
import com.ankush.sms.dto.request.StudentProfileUpdateRequest;
import com.ankush.sms.dto.response.CourseResponse;
import com.ankush.sms.dto.response.LoginResponse;
import com.ankush.sms.dto.response.StudentResponse;
import com.ankush.sms.entity.Course;
import com.ankush.sms.entity.Student;
import com.ankush.sms.exception.BadRequestException;
import com.ankush.sms.exception.ResourceNotFoundException;
import com.ankush.sms.mapper.AddressMapper;
import com.ankush.sms.mapper.CourseMapper;
import com.ankush.sms.mapper.StudentMapper;
import com.ankush.sms.mapper.TopicMapper;
import com.ankush.sms.repository.CourseRepository;
import com.ankush.sms.repository.StudentRepository;
import com.ankush.sms.security.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private TopicMapper topicMapper;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private JwtService jwtService;

    // =====================================================
    // Helper Method
    // =====================================================

    private void mockLoggedInStudent() {

        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("STU101");

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    // =====================================================
    // Student Login Success
    // =====================================================

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {

        StudentLoginRequest request = StudentLoginRequest.builder()
                .studentCode("STU101")
                .dob(LocalDate.of(2000, 1, 1))
                .build();

        Student student = Student.builder()
                .studentCode("STU101")
                .dob(LocalDate.of(2000, 1, 1))
                .build();

        when(studentRepository.findByStudentCode("STU101"))
                .thenReturn(Optional.of(student));

        when(jwtService.generateToken("STU101", "ROLE_STUDENT"))
                .thenReturn("jwt-token");

        LoginResponse response = studentService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("STU101", response.getUsername());
        assertEquals("ROLE_STUDENT", response.getRole());

        verify(studentRepository).findByStudentCode("STU101");
        verify(jwtService).generateToken("STU101", "ROLE_STUDENT");
    }

    // =====================================================
    // Student Not Found
    // =====================================================

    @Test
    void login_ShouldThrowException_WhenStudentNotFound() {

        StudentLoginRequest request = StudentLoginRequest.builder()
                .studentCode("ABC")
                .dob(LocalDate.now())
                .build();

        when(studentRepository.findByStudentCode("ABC"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> studentService.login(request));

        verify(studentRepository).findByStudentCode("ABC");
    }

    // =====================================================
    // Invalid DOB
    // =====================================================

    @Test
    void login_ShouldThrowException_WhenDobIsWrong() {

        StudentLoginRequest request = StudentLoginRequest.builder()
                .studentCode("STU101")
                .dob(LocalDate.of(1999, 1, 1))
                .build();

        Student student = Student.builder()
                .studentCode("STU101")
                .dob(LocalDate.of(2000, 1, 1))
                .build();

        when(studentRepository.findByStudentCode("STU101"))
                .thenReturn(Optional.of(student));

        assertThrows(BadRequestException.class,
                () -> studentService.login(request));
    }

    // =====================================================
    // Get Student Profile
    // =====================================================

    @Test
    void getStudentProfile_ShouldReturnStudentResponse() {

        mockLoggedInStudent();

        Student student = Student.builder()
                .studentCode("STU101")
                .build();

        StudentResponse response = StudentResponse.builder()
                .studentCode("STU101")
                .build();

        when(studentRepository.findByStudentCode("STU101"))
                .thenReturn(Optional.of(student));

        when(studentMapper.toResponse(student))
                .thenReturn(response);

        StudentResponse result = studentService.getStudentProfile();

        assertEquals("STU101", result.getStudentCode());

        verify(studentMapper).toResponse(student);
    }

    // =====================================================
    // Update Profile
    // =====================================================

    @Test
    void updateProfile_ShouldUpdateSuccessfully() {

        mockLoggedInStudent();

        Student student = Student.builder()
                .studentCode("STU101")
                .email("old@gmail.com")
                .build();

        StudentProfileUpdateRequest request =
                StudentProfileUpdateRequest.builder()
                        .email("new@gmail.com")
                        .mobile("9999999999")
                        .parentName("Father")
                        .build();

        StudentResponse response = StudentResponse.builder()
                .email("new@gmail.com")
                .build();

        when(studentRepository.findByStudentCode("STU101"))
                .thenReturn(Optional.of(student));

        when(studentRepository.save(any(Student.class)))
                .thenReturn(student);

        when(studentMapper.toResponse(student))
                .thenReturn(response);

        StudentResponse result = studentService.updateProfile(request);

        assertEquals("new@gmail.com", result.getEmail());

        verify(studentRepository).save(student);
    }

    // =====================================================
    // Get Assigned Courses
    // =====================================================

    @Test
    void getAssignedCourses_ShouldReturnCourses() {

        mockLoggedInStudent();

        Course course = Course.builder()
                .courseName("Spring Boot")
                .build();

        Student student = Student.builder().build();

        student.addCourse(course);

        when(studentRepository.findByStudentCode("STU101"))
                .thenReturn(Optional.of(student));

        when(courseMapper.toResponse(course))
                .thenReturn(new CourseResponse());

        List<CourseResponse> result = studentService.getAssignedCourses();

        assertEquals(1, result.size());

        verify(courseMapper).toResponse(course);
    }
}