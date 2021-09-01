package com.notes.notesapp.models.notes_models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetNotesRequest {
    private String jwt;
}
