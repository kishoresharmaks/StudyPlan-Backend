package com.nexus.studyplan.controller;

import com.nexus.studyplan.model.NotesModel;
import com.nexus.studyplan.model.UserModel;
import com.nexus.studyplan.service.NotesService;
import org.aspectj.weaver.ast.Not;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.nexus.studyplan.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.nexus.studyplan.repository.NotesRepository;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin("*")
public class NotesController {
    private final NotesService notesService;
    private final UserRepository userRepository;

    public NotesController(NotesService service, UserRepository userRepository) {
        this.notesService = service;
        this.userRepository = userRepository;
    }
    @GetMapping
    public List<NotesModel> getAllNotes(){
        return notesService.getAllNotes();
    }
    @PostMapping("/new")
    public ResponseEntity<?> createNote(@RequestBody NotesModel notes) {
        // Check if userId is provided
        if (notes.getUserId() == null) {
            return ResponseEntity.badRequest()
                .body("User ID is required to create a note");
        }
        
        // Validate and create note
        try {
            NotesModel createdNote = notesService.createNote(notes);
            return ResponseEntity.ok(createdNote);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody NotesModel notes) {
        if (notes.getUserId() == null) {
            return ResponseEntity.badRequest()
                    .body("User ID is required to update a note");
        }
        
        try {
            // ✅ SECURITY CHECK: Verify note exists and belongs to user
            NotesModel existingNote = notesService.getNoteById(id);
            if (existingNote == null) {
                return ResponseEntity.badRequest().body("Note not found");
            }
            if (!existingNote.getUserId().equals(notes.getUserId())) {
                return ResponseEntity.badRequest().body("You can only update your own notes");
            }
            
            NotesModel updatedNote = notesService.updateNote(id, notes);
            return ResponseEntity.ok(updatedNote);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        
        if (userId == null) {
            return ResponseEntity.badRequest().body("User ID is required to delete a note");
        }
        
        try {
            // Check if note exists
            NotesModel existingNote = notesService.getNoteById(id);
            if (existingNote == null) {
                return ResponseEntity.badRequest().body("Note not found");
            }
            
            // Check if note belongs to the user
            if (!existingNote.getUserId().equals(userId)) {
                return ResponseEntity.badRequest().body("You can only delete your own notes");
            }
            
            String result = notesService.deleteNote(id);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
