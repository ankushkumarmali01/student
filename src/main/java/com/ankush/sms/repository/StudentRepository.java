package com.ankush.sms.repository;

import com.ankush.sms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository  extends JpaRepository<Student, Long> {

}
