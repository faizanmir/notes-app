package com.notes.notesapp.models.notes_models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddOrUpdateNoteRequest {
    String jwt;
    Note note;
}
