package in.sashi.andro_mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import in.sashi.andro_mvvm.entities.Note;
import in.sashi.andro_mvvm.repos.NoteRepository;

public class NoteViewModel extends AndroidViewModel {

    public NoteRepository repository;
    public LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();

    }

    public void addNote(Note note){ repository.addNote(note); }
    public void upDateNote(Note note){ repository.updateNote(note); }
    public void deleteNote(Note note){ repository.deleteNote(note); }
    public void deleteAllNote(){ repository.deleteAllNotes(); }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
