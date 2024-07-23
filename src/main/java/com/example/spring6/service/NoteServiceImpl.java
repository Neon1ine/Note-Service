package com.example.spring6.service;

import com.example.spring6.aspect.TrackUserAction;
import com.example.spring6.domain.Note;
import com.example.spring6.repository.NoteRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Getter
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    @TrackUserAction
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    @TrackUserAction
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    @TrackUserAction
    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    @Override
    @TrackUserAction
    public Optional<Note> updateNote(Note note) {
        Optional<Note> noteById = getNoteById(note.getId());
        if (noteById.isPresent()) {
            Note noteChange = noteById.get();
            noteChange.setHeading(note.getHeading());
            noteChange.setContent(note.getContent());
            return Optional.of(noteRepository.save(noteChange));
        }
        return noteById;
    }

    //Данный код стоит удалить, он уже используется в NoteServiceImplAdapter классе
//    @Override
//    @TrackUserAction
//    public Optional<Note> deleteNote(Long id) {
//        Optional<Note> noteById = getNoteById(id);
//        if (noteById.isPresent()) {
//            noteById.ifPresent(noteRepository::delete);
//        }
//        return noteById;
//    }
}
