package com.eustache.spring_security_ex.repositories;

import com.eustache.spring_security_ex.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
   //select * from student;
    List<Student> findAll();
}
