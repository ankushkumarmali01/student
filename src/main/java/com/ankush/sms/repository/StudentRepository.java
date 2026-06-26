package com.ankush.sms.repository;

import com.ankush.sms.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface StudentRepository  extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentCode(String studentCode);

    Optional<Student> findByEmail(String email);

    boolean existsByStudentCode(String studentCode);

    boolean existsByEmail(String email);

    List<Student> findByNameContainingIgnoreCase(String name);

    Page<Student> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable);
}
