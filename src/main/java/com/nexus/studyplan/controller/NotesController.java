package com.nexus.studyplan.controller;

import com.nexus.studyplan.model.NotesModel;
import com.nexus.studyplan.service.NotesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.http.ResponseEntity;
import com.nexus.studyplan.repository.UserRepository;
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
    public ResponseEntity<?> getAllNotes(HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login to get Notes");
        }
        List<NotesModel> notes = notesService.getNotesByUserId(userId);
        return ResponseEntity.ok(notes);
    }
    @PostMapping("/new")
    public ResponseEntity<?> createNote(@RequestBody NotesModel notes, HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login to create Notes");
        }
        
        // Validate input lengths
        if (notes.getTitle() != null && notes.getTitle().length() > 500) {
            return ResponseEntity.badRequest().body("Title is too long (max 500 characters)");
        }
        if (notes.getDescription() != null && notes.getDescription().length() > 1000) {
            return ResponseEntity.badRequest().body("Description is too long (max 1000 characters)");
        }
        if (notes.getContent() != null && notes.getContent().length() > 10000) {
            return ResponseEntity.badRequest().body("Content is too long (max 10000 characters)");
        }
        
        // Set the userId from session
        notes.setUserId(userId);
        
        // Validate and create note
        try {
            NotesModel createdNote = notesService.createNote(notes);
            return ResponseEntity.ok(createdNote);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody NotesModel notes, HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login to update Notes");
        }
        
        try {
            // ✅ SECURITY CHECK: Verify note exists and belongs to user
            NotesModel existingNote = notesService.getNoteById(id);
            if (existingNote == null) {
                return ResponseEntity.badRequest().body("Note not found");
            }
            if (!existingNote.getUserId().equals(userId)) {
                return ResponseEntity.badRequest().body("You can only update your own notes");
            }
            
            // Validate input lengths
            if (notes.getTitle() != null && notes.getTitle().length() > 500) {
                return ResponseEntity.badRequest().body("Title is too long (max 500 characters)");
            }
            if (notes.getDescription() != null && notes.getDescription().length() > 1000) {
                return ResponseEntity.badRequest().body("Description is too long (max 1000 characters)");
            }
            if (notes.getContent() != null && notes.getContent().length() > 10000) {
                return ResponseEntity.badRequest().body("Content is too long (max 10000 characters)");
            }
            
            // Set the userId from session
            notes.setUserId(userId);
            NotesModel updatedNote = notesService.updateNote(id, notes);
            return ResponseEntity.ok(updatedNote);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login to delete Notes");
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
