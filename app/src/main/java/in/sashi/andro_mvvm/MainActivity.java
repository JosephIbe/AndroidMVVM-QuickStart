package in.sashi.andro_mvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import in.sashi.andro_mvvm.adapter.NotesAdapter;
import in.sashi.andro_mvvm.entities.Note;
import in.sashi.andro_mvvm.viewmodel.NoteViewModel;

public class MainActivity extends AppCompatActivity implements NotesAdapter.onNoteItemClickListener {

    private NoteViewModel noteViewModel;
    private RecyclerView notesRV;
    private NotesAdapter adapter;

    private FloatingActionButton addFAB;

    private static final int ADD_NOTE_REQ_CODE = 101;
    private static final int EDIT_NOTE_REQ_CODE = 202;

    private NotesAdapter.onNoteItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        adapter = new NotesAdapter();

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                notesRV.setAdapter(adapter);
                adapter.setItems(notes);
            }
        });

        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQ_CODE);
            }
        });

        itemClickListener = (NotesAdapter.onNoteItemClickListener) this;

    }

    private void init() {
        notesRV = findViewById(R.id.notesRV);
        addFAB = findViewById(R.id.addFAB);

        notesRV.setHasFixedSize(true);
        notesRV.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView rv, @NonNull RecyclerView.ViewHolder vh, @NonNull RecyclerView.ViewHolder vh1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder vh, int position) {
                noteViewModel.deleteNote(adapter.getNoteAt(vh.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted at Position:\t" + position, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(notesRV);

        adapter.setClickListener(this);
//        adapter.setClickListener(new NotesAdapter.onNoteItemClickListener() {
//            @Override
//            public void onNoteClicked(Note note) {
//                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
//                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
//                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
//                intent.putExtra(AddEditNoteActivity.EXTRA_DESC, note.getDesc());
//                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
//                startActivityForResult(intent, EDIT_NOTE_REQ_CODE);
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADD_NOTE_REQ_CODE:
                    Note note = new Note(data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE),
                            data.getStringExtra(AddEditNoteActivity.EXTRA_DESC),
                            data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0));
                    noteViewModel.addNote(note);
                    Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();
                    break;
                case EDIT_NOTE_REQ_CODE:
                    int note_id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
                    if (note_id == -1){
                        return;
                    }

                    Note editedNote = new Note(data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE),
                            data.getStringExtra(AddEditNoteActivity.EXTRA_DESC),
                            data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0));

                    editedNote.setId(note_id);
                    noteViewModel.upDateNote(editedNote);

                    Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();

                    break;
            }
        } else {
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_all_notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNote();
                Toast.makeText(this, "Notes Database Deleted...Add New Notes", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onNoteClicked(Note note) {
        Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
        intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
        intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
        intent.putExtra(AddEditNoteActivity.EXTRA_DESC, note.getDesc());
        intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
        startActivityForResult(intent, EDIT_NOTE_REQ_CODE);
    }
}
