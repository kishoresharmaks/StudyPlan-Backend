package com.nexus.studyplan.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserModel {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    @Column(unique = true, nullable = false)
    private String mailid;
    private  String password;

    public UserModel(){

    }

    public UserModel(long id, String username, String mailid, String password) {
        this.id = id;
        this.username = username;
        this.mailid = mailid;
        this.password = password;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
