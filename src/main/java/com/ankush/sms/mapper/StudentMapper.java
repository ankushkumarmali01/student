package com.ankush.sms.mapper;

import com.ankush.sms.dto.request.StudentProfileUpdateRequest;
import com.ankush.sms.dto.request.StudentRequest;
import com.ankush.sms.dto.response.StudentResponse;
import com.ankush.sms.entity.Address;
import com.ankush.sms.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StudentMapper {

    private final AddressMapper addressMapper;
    private final CourseMapper courseMapper;

    public Student toEntity(StudentRequest request) {

        if (request == null) {
            return null;
        }

        Student student = Student.builder()
                .name(request.getName())
                .dob(request.getDob())
                .gender(request.getGender())
                .studentCode(request.getStudentCode())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .parentName(request.getParentName())
                .build();

        for (Address address : request.getAddresses()
                .stream()
                .map(addressMapper::toEntity)
                .toList()) {

            student.addAddress(address);
        }

        return student;
    }

    public StudentResponse toResponse(Student student) {

        if (student == null) {
            return null;
        }

        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .dob(student.getDob())
                .gender(student.getGender())
                .studentCode(student.getStudentCode())
                .email(student.getEmail())
                .mobile(student.getMobile())
                .parentName(student.getParentName())
                .addresses(student.getAddresses()
                        .stream()
                        .map(addressMapper::toResponse)
                        .collect(Collectors.toList()))
                .courses(student.getCourses()
                        .stream()
                        .map(courseMapper::toResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public void updateEntity(Student student, StudentRequest request) {

        student.setName(request.getName());
        student.setDob(request.getDob());
        student.setGender(request.getGender());
        student.setStudentCode(request.getStudentCode());
        student.setEmail(request.getEmail());
        student.setMobile(request.getMobile());
        student.setParentName(request.getParentName());
    }

    public void updateProfile(
            Student student,
            StudentProfileUpdateRequest request) {

        student.setEmail(request.getEmail());
        student.setMobile(request.getMobile());
        student.setParentName(request.getParentName());

        if (request.getAddresses() != null) {

            student.getAddresses().clear();

            request.getAddresses()
                    .stream()
                    .map(addressMapper::toEntity)
                    .forEach(student::addAddress);
        }
    }

}
