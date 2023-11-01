package com.giriraj.usermanegementservice.repository;

import com.giriraj.usermanegementservice.model.Session;
import com.giriraj.usermanegementservice.model.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findByUsersEmailAndSessionStatus(String email, SessionStatus sessionStatus);

    Optional<Session> findByToken(String token);
}
