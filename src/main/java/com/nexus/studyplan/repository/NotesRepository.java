package com.nexus.studyplan.repository;

import com.nexus.studyplan.model.NotesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotesRepository extends JpaRepository<NotesModel, Long> {
    List<NotesModel> findByUserId(Long userId);
}
