package com.nexus.studyplan.repository;

import com.nexus.studyplan.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByMailid(String mailid);
}
