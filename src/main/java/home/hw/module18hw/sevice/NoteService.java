package home.hw.module18hw.sevice;

import home.hw.module18hw.mapper.NoteMapper;
import home.hw.module18hw.model.Note;
import home.hw.module18hw.model.request.NoteRequest;
import home.hw.module18hw.model.response.NoteResponse;
import home.hw.module18hw.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;
    private final NoteMapper noteMapper;

    public List<NoteResponse> findAll() {
        return noteMapper.notesToNoteResponses(noteRepository.findAllByUser(userService.getCurrentUser()));
    }

    public void deleteById(long id) {
        noteRepository.deleteByIdAndUser(id, userService.getCurrentUser());
    }

    public NoteResponse save(NoteRequest noteRequest) {
        Note note = noteMapper.noteRequestToNote(noteRequest);
        note.setUser(userService.getCurrentUser());
        noteRepository.save(note);
        return noteMapper.noteToNoteResponse(note);
    }

    public NoteResponse getById(Long id) {
        return noteMapper.noteToNoteResponse(noteRepository.findByIdAndUser(id, userService.getCurrentUser()));
    }


}
