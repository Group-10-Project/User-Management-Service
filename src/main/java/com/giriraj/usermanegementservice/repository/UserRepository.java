package com.giriraj.usermanegementservice.repository;

import com.giriraj.usermanegementservice.model.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {
    List<Users> findByEmail(String email);
}
