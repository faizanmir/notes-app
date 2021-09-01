package com.notes.notesapp.models.notes_models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Note {
    private String id;
    private String title;
    private String body;
    private String color;
    private List<String> images = new ArrayList<>();
    private boolean markAsDone;
}
