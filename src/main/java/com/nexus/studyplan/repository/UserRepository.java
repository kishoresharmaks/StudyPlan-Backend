package com.nexus.studyplan.repository;

import com.nexus.studyplan.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByMailid(String mailid);
    boolean existsByMailid(String mailid);

}
