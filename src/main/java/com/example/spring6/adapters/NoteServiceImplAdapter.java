package com.example.spring6.adapters;

import com.example.spring6.aspect.TrackUserAction;
import com.example.spring6.domain.Note;
import com.example.spring6.service.NoteServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Service
@AllArgsConstructor
public class NoteServiceImplAdapter implements NoteServiceImplAdapterInterface {

    private NoteServiceImpl noteServiceImpl;

    //Стоит добавить вывод ошибки при неправильном предоставлении id записи
    @Override
    @TrackUserAction
    public Optional<Note> deleteNote(Long id) {
        Optional<Note> noteById = noteServiceImpl.getNoteById(id);
        if (noteById.isPresent()) {
            noteById.ifPresent(noteServiceImpl.getNoteRepository()::delete);
        }
        return noteById;
    }
}
