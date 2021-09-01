package com.notes.notesapp.models.user_models;

import com.notes.notesapp.models.notes_models.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private List<Note> notes = new ArrayList<>();
}
