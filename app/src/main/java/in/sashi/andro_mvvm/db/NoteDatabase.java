package in.sashi.andro_mvvm.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import in.sashi.andro_mvvm.entities.Note;
import in.sashi.andro_mvvm.interfaces.NoteDAO;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;
    public abstract NoteDAO noteDAO();

    public synchronized NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
