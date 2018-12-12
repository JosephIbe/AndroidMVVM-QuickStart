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

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private RecyclerView notesRV;
    private NotesAdapter adapter;

    private FloatingActionButton addFAB;
    private static final int NOTE_REQ_CODE = 101;

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
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, NOTE_REQ_CODE);
            }
        });

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case NOTE_REQ_CODE:
                    Note note = new Note(data.getStringExtra(AddNoteActivity.EXTRA_TITLE),
                            data.getStringExtra(AddNoteActivity.EXTRA_DESC),
                            data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 0));
                    noteViewModel.addNote(note);
                    Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();
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
}
