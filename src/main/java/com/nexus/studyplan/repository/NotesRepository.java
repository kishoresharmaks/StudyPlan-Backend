package com.nexus.studyplan.repository;

import com.nexus.studyplan.model.NotesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<NotesModel, Long> {
}
