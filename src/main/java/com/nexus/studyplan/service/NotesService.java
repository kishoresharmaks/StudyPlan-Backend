package com.nexus.studyplan.service;

import com.nexus.studyplan.model.NotesModel;
import com.nexus.studyplan.model.UserModel;
import com.nexus.studyplan.repository.NotesRepository;
import com.nexus.studyplan.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Service
public class NotesService {

    private final NotesRepository notesrepo;
    private final UserRepository userRepository;

    public NotesService(NotesRepository notesrepo, UserRepository userRepository) {
        this.notesrepo = notesrepo;
        this.userRepository = userRepository;
    }

    public List<NotesModel> getAllNotes() {
        return notesrepo.findAll();
    }

    public List<NotesModel> getNotesByUserId(Long userId) {
        return notesrepo.findByUserId(userId);
    }

    public NotesModel createNote(NotesModel notes) {
        // Validate user exists
        if (!userRepository.existsById(notes.getUserId())) {
            throw new RuntimeException("User with ID " + notes.getUserId() + " not found");
        }

        return notesrepo.save(notes);
    }

    public NotesModel updateNote(Long id, NotesModel notes) {
        if (!userRepository.existsById(notes.getUserId()) && !notesrepo.existsById(id)) {
            throw new RuntimeException("Notes with ID " + id + " not found.");
        }
        notes.setId(id); // Ensure correct ID
        return notesrepo.save(notes);
    }

    public String deleteNote(Long id) {
        if (!notesrepo.existsById(id)) {
            throw new RuntimeException("Notes with ID Not Found");
        }
        notesrepo.deleteById(id);
        return "Notes With ID" + id + "Deleted!";
    }

    public NotesModel getNoteById(Long id) {
        return notesrepo.findById(id).orElse(null);
    }
}
