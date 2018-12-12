package in.sashi.andro_mvvm.repos;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import in.sashi.andro_mvvm.db.NoteDatabase;
import in.sashi.andro_mvvm.entities.Note;
import in.sashi.andro_mvvm.interfaces.NoteDAO;

public class NoteRepository {

    public NoteDAO noteDAO;
    public LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDAO = database.noteDAO();
        allNotes = noteDAO.notesList();
    }

    public void addNote(Note note){
        new AddNoteTask(noteDAO).execute(note);
    }

    public void updateNote(Note note){
        new UpDateNoteTask(noteDAO).execute(note);
    }

    public void deleteNote(Note note){
        new DeleteNoteTask(noteDAO).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNotesTask(noteDAO).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class AddNoteTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        public AddNoteTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.addNote(notes[0]);
            return null;
        }
    }

    private static class UpDateNoteTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        public UpDateNoteTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.upDateNote(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        public DeleteNoteTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.deleteNote(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesTask extends AsyncTask<Void, Void, Void> {

        private NoteDAO noteDAO;

        public DeleteAllNotesTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.deleteAllNotes();
            return null;
        }
    }

}
