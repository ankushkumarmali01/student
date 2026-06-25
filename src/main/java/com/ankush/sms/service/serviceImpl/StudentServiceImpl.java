package com.ankush.sms.service.serviceImpl;

import com.ankush.sms.dto.request.StudentLoginRequest;
import com.ankush.sms.dto.request.StudentProfileUpdateRequest;
import com.ankush.sms.dto.response.CourseResponse;
import com.ankush.sms.dto.response.LoginResponse;
import com.ankush.sms.dto.response.StudentResponse;
import com.ankush.sms.dto.response.TopicResponse;
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
import com.ankush.sms.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final TopicMapper topicMapper;
    private final AddressMapper addressMapper;

    private final JwtService jwtService;

    // =====================================================
    // Student Login
    // =====================================================

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(StudentLoginRequest request) {

        log.info("Student login request for student code: {}", request.getStudentCode());

        Student student = studentRepository.findByStudentCode(request.getStudentCode())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with code : " + request.getStudentCode()));

        if (!student.getDob().equals(request.getDob())) {
            throw new BadRequestException("Invalid student code or date of birth.");
        }

        String token = jwtService.generateToken(
                student.getStudentCode(),
                "ROLE_STUDENT"
        );

        log.info("Student {} logged in successfully.", student.getStudentCode());

        return LoginResponse.builder()
                .token(token)
                .username(student.getStudentCode())
                .role("ROLE_STUDENT")
                .build();
    }

    // =====================================================
    // Helper Method
    // =====================================================

    private Student getLoggedInStudent() {

        String studentCode = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        log.info("Fetching logged-in student: {}", studentCode);

        return studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with code : " + studentCode));
    }

    // =====================================================
    // Get Logged-in Student Profile
    // =====================================================

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentProfile() {

        log.info("Fetching logged-in student profile.");

        Student student = getLoggedInStudent();

        return studentMapper.toResponse(student);
    }

    // =====================================================
    // Update Student Profile
    // =====================================================

    @Override
    public StudentResponse updateProfile(StudentProfileUpdateRequest request) {

        log.info("Updating profile for logged-in student.");

        Student student = getLoggedInStudent();

        // Check duplicate email
        if (request.getEmail() != null
                && !request.getEmail().equals(student.getEmail())
                && studentRepository.existsByEmail(request.getEmail())) {

            throw new BadRequestException(
                    "Email already exists : " + request.getEmail()
            );
        }

        // Update basic details
        student.setEmail(request.getEmail());
        student.setMobile(request.getMobile());
        student.setParentName(request.getParentName());

        // Update addresses
        if (request.getAddresses() != null) {

            student.getAddresses().clear();

            request.getAddresses()
                    .stream()
                    .map(addressMapper::toEntity)
                    .forEach(student::addAddress);
        }

        Student updatedStudent = studentRepository.save(student);

        log.info("Student profile updated successfully.");

        return studentMapper.toResponse(updatedStudent);
    }

    // =====================================================
    // Get Assigned Courses
    // =====================================================

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getAssignedCourses() {

        log.info("Fetching assigned courses for logged-in student.");

        Student student = getLoggedInStudent();

        return student.getCourses()
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    // =====================================================
    // Get Topics of Assigned Courses
    // =====================================================

    @Override
    @Transactional(readOnly = true)
    public List<TopicResponse> getAssignedCourseTopics() {

        log.info("Fetching topics of assigned courses for logged-in student.");

        Student student = getLoggedInStudent();

        return student.getCourses()
                .stream()
                .flatMap(course -> course.getTopics().stream())
                .distinct() // Avoid duplicate topics if present
                .map(topicMapper::toResponse)
                .toList();
    }

    // =====================================================
    // Leave Course
    // =====================================================

    @Override
    public void leaveCourse(Long courseId) {

        Student student = getLoggedInStudent();

        log.info("Student {} requested to leave course {}",
                student.getStudentCode(), courseId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found with id : " + courseId));

        if (!student.getCourses().contains(course)) {
            throw new BadRequestException(
                    "You are not enrolled in this course."
            );
        }

        student.removeCourse(course);

        studentRepository.save(student);

        log.info("Student {} left course {} successfully.",
                student.getStudentCode(), courseId);
    }

}
