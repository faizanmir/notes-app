package com.notes.notesapp.models.user_models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class NotesUser {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    private String username;
    @Id
    private int id;
    private  String password;
    private String email;
    private  String role;
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public NotesUser() {
    }


    public NotesUser(String username, int id, String password, String email, String role, String jwt) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.email = email;
        this.role = role;
        this.jwt = jwt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "NotesUser{" +
                "username='" + username + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", jwt='" + jwt + '\'' +
                '}';
    }
}
