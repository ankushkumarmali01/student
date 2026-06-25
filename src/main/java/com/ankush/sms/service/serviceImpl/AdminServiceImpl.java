package com.ankush.sms.service.serviceImpl;

import com.ankush.sms.dto.request.AdminLoginRequest;
import com.ankush.sms.dto.request.CourseRequest;
import com.ankush.sms.dto.request.StudentRequest;
import com.ankush.sms.dto.response.CourseResponse;
import com.ankush.sms.dto.response.LoginResponse;
import com.ankush.sms.dto.response.StudentResponse;
import com.ankush.sms.entity.Course;
import com.ankush.sms.entity.Student;
import com.ankush.sms.exception.DuplicateResourceException;
import com.ankush.sms.exception.ResourceNotFoundException;
import com.ankush.sms.mapper.AddressMapper;
import com.ankush.sms.mapper.CourseMapper;
import com.ankush.sms.mapper.StudentMapper;
import com.ankush.sms.mapper.TopicMapper;
import com.ankush.sms.repository.CourseRepository;
import com.ankush.sms.repository.StudentRepository;
import com.ankush.sms.security.JwtService;
import com.ankush.sms.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


    private final StudentMapper studentMapper;
    private final AddressMapper addressMapper;
    private final CourseMapper courseMapper;
    private final TopicMapper topicMapper;

    /**
     * -------------------------
     * ADMIN LOGIN
     * -------------------------
     */
    @Override
    public LoginResponse login(AdminLoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        log.info("Admin '{}' logged in successfully.", userDetails.getUsername());

        return LoginResponse.builder()
                .token(token)
                .username(userDetails.getUsername())
                .role(userDetails.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority())
                .build();
    }

    @Override
    @Transactional
    public StudentResponse createStudent(StudentRequest request) {

        log.info("Creating student with student code : {}", request.getStudentCode());

        if (studentRepository.existsByStudentCode(request.getStudentCode())) {
            throw new DuplicateResourceException(
                    "Student code already exists : " + request.getStudentCode()
            );
        }

        if (request.getEmail() != null
                && !request.getEmail().isBlank()
                && studentRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already exists : " + request.getEmail()
            );
        }

        Student student = studentMapper.toEntity(request);

        Student savedStudent = studentRepository.save(student);

        log.info("Student created successfully with id : {}", savedStudent.getId());

        return studentMapper.toResponse(savedStudent);
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequest request) {

        log.info("Updating student with id : {}", id);

        Student student = getStudent(id);

        if (!student.getStudentCode().equals(request.getStudentCode())
                && studentRepository.existsByStudentCode(request.getStudentCode())) {

            throw new DuplicateResourceException(
                    "Student code already exists : " + request.getStudentCode()
            );
        }

        if (request.getEmail() != null
                && !request.getEmail().equalsIgnoreCase(student.getEmail())
                && studentRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already exists : " + request.getEmail()
            );
        }

        studentMapper.updateEntity(student, request);

        // Remove existing addresses
        student.getAddresses().clear();

        // Add new addresses
        request.getAddresses()
                .stream()
                .map(addressMapper::toEntity)
                .forEach(student::addAddress);

        Student updatedStudent = studentRepository.save(student);

        log.info("Student updated successfully : {}", updatedStudent.getId());

        return studentMapper.toResponse(updatedStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {

        log.info("Deleting student with id : {}", id);

        Student student = getStudent(id);

        studentRepository.delete(student);

        log.info("Student deleted successfully.");
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {

        log.info("Fetching student with id : {}", id);

        Student student = getStudent(id);

        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {

        log.info("Fetching all students.");

        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> searchStudentsByName(String name) {

        log.info("Searching students with name : {}", name);

        return studentRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CourseResponse createCourse(CourseRequest request) {

        log.info("Creating course : {}", request.getCourseName());

        if (courseRepository.existsByCourseName(request.getCourseName())) {

            throw new DuplicateResourceException(
                    "Course already exists : " + request.getCourseName()
            );
        }

        Course course = courseMapper.toEntity(request);

        Course savedCourse = courseRepository.save(course);

        log.info("Course created successfully with id : {}", savedCourse.getId());

        return courseMapper.toResponse(savedCourse);
    }

    @Override
    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest request) {

        log.info("Updating course : {}", id);

        Course course = getCourse(id);

        if (!course.getCourseName().equalsIgnoreCase(request.getCourseName())
                && courseRepository.existsByCourseName(request.getCourseName())) {

            throw new DuplicateResourceException(
                    "Course already exists : " + request.getCourseName()
            );
        }

        courseMapper.updateEntity(course, request);

        course.getTopics().clear();

        request.getTopics()
                .stream()
                .map(topicMapper::toEntity)
                .forEach(topic -> {

                    topic.setCourse(course);

                    course.getTopics().add(topic);

                });

        Course updatedCourse = courseRepository.save(course);

        log.info("Course updated successfully.");

        return courseMapper.toResponse(updatedCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long courseId) {

        log.info("Fetching course : {}", courseId);

        return courseMapper.toResponse(getCourse(courseId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {

        log.info("Fetching all courses.");

        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {

        log.info("Deleting course : {}", id);

        Course course = getCourse(id);

        courseRepository.delete(course);

        log.info("Course deleted successfully.");
    }

    @Override
    @Transactional
    public void assignCourseToStudent(Long studentId, Long courseId) {

        log.info("Assigning course {} to student {}", courseId, studentId);

        Student student = getStudent(studentId);
        Course course = getCourse(courseId);

        if (student.getCourses().contains(course)) {
            throw new DuplicateResourceException(
                    "Course is already assigned to this student."
            );
        }

        student.addCourse(course);

        studentRepository.save(student);

        log.info("Course assigned successfully.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsByCourse(Long courseId) {

        log.info("Fetching students for course {}", courseId);

        Course course = getCourse(courseId);

        return course.getStudents()
                .stream()
                .map(studentMapper::toResponse)
                .toList();
    }




    private Student getStudent(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with id : " + id
                        ));
    }

    private Course getCourse(Long id) {

        return courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found with id : " + id
                        ));
    }

}