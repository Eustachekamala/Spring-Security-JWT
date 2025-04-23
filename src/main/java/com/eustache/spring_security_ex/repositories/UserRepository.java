package com.eustache.spring_security_ex.repositories;

import com.eustache.spring_security_ex.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    //select * from student;
    List<Users> findAll();

    //select * from student where username like = '%eustache%'
    Users findByUsername(String username);



}
