package com.nexus.studyplan.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class NotesModel {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private  String content;
    private Long userId;

    public NotesModel(long id, String title, String description, String content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
    }
    public NotesModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
