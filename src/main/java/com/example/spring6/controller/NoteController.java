package com.example.spring6.controller;

import com.example.spring6.adapters.NoteServiceImplAdapter;
import com.example.spring6.domain.Note;
import com.example.spring6.service.FileGateWay;
import com.example.spring6.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Контроллер для работы с задачами.
 */
@RestController
@RequestMapping("/Notes")
@RequiredArgsConstructor
public class NoteController {

    private final FileGateWay fileGateWay;


    private final NoteServiceImplAdapter noteServiceImplAdapter;

    /**
     * Обработчик запроса на вывод всех задач.
     * @return список задач.
     */
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return new ResponseEntity<>(noteServiceImplAdapter.getNoteServiceImpl().getAllNotes(), HttpStatus.OK);
    }

    /**
     * Обработчик запроса на создание и запись в репозиторий задачи.
     * @param note Задача.
     * @return Задачу.
     */
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        ResponseEntity<Note> finalNote = new ResponseEntity<>(noteServiceImplAdapter.getNoteServiceImpl().createNote(note), HttpStatus.CREATED);
        fileGateWay.writeToFile(finalNote.getBody().getHeading() + ".txt", finalNote.getBody().toString());
        return finalNote;
    }

    /**
     * Обработчик запроса на вывод задачи с указанным id.
     * @param id
     * @return опционально, либо задача, либо статус не найдено.
     */
    @GetMapping("{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") Long id) {
        Optional<Note> note = noteServiceImplAdapter.getNoteServiceImpl().getNoteById(id);
        return note.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Обработчик запроса на обновление задачи.
     * @param note Задача.
     * @return опционально, либо задача, либо статус плохой запрос.
     */
    @PutMapping
    public ResponseEntity<Note> updateNote(@RequestBody Note note) {
        Optional<Note> noteUpdate = noteServiceImplAdapter.getNoteServiceImpl().updateNote(note);
        return noteUpdate.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }


    /**
     * Обработчик запроса на удаление задачи.
     * @param id
     * @return опционально, либо задача, либо статус не найдено.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable("id") Long id) {
        Optional<Note> noteDelete = noteServiceImplAdapter.deleteNote(id);
        return noteDelete.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
