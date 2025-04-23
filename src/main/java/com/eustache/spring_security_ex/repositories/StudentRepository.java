package com.eustache.spring_security_ex.repositories;

import com.eustache.spring_security_ex.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    //select * from student;
    List<Student> findAll();

    //select * from student where username like = '%eustache%'
    Student findByUsername(String username);



}
