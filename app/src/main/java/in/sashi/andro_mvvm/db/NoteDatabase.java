package in.sashi.andro_mvvm.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import in.sashi.andro_mvvm.entities.Note;
import in.sashi.andro_mvvm.interfaces.NoteDAO;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;
    public abstract NoteDAO noteDAO();

    public static synchronized NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            new PopulateDBAsyncTask(instance).execute();
                        }
                    })
                    .build();
        }
        return instance;
    }

    public static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteDAO noteDAO;

        public PopulateDBAsyncTask(NoteDatabase db) {
            noteDAO = db.noteDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.addNote(new Note("Note 1", "Desc 1", 1));
            noteDAO.addNote(new Note("Note 2", "Desc 2", 2));
            noteDAO.addNote(new Note("Note 3", "Desc 2", 3));
            return null;
        }
    }

}
