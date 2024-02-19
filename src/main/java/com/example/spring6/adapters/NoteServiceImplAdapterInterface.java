package com.example.spring6.adapters;

import com.example.spring6.domain.Note;

import java.util.Optional;

public interface NoteServiceImplAdapterInterface {
    Optional<Note> deleteNote(Long id);
}
