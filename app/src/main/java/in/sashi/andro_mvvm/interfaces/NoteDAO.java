package in.sashi.andro_mvvm.interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import in.sashi.andro_mvvm.entities.Note;

@Dao
public interface NoteDAO {

    @Insert
    void addNote(Note note);

    @Update
    void upDateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("delete from notes_table")
    void deleteAllNotes();

    @Query("select * from notes_table order by priority desc")
    LiveData<List<Note>> notesList();

}
