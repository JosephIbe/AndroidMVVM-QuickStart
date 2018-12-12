package in.sashi.andro_mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText titleET, descET;
    private NumberPicker numPicker;

    public static final String EXTRA_TITLE = "in.sashi.andro_mvvm.EXTRA_TITLE";
    public static final String EXTRA_DESC = "in.sashi.andro_mvvm.EXTRA_DESC";
    public static final String EXTRA_PRIORITY = "in.sashi.andro_mvvm.EXTRA_PRIORITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        init();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add New Note");

    }

    private void init() {
        titleET = findViewById(R.id.titleET);
        descET = findViewById(R.id.descET);
        numPicker = findViewById(R.id.numPicker);

        numPicker.setMaxValue(10);
        numPicker.setMinValue(1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String title = titleET.getText().toString().trim();
        String desc = descET.getText().toString().trim();

        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Title and Description Are Required", Toast.LENGTH_SHORT).show();
        } else if (numPicker.getValue() <= 0) {
            Toast.makeText(this, "Please Select Priority", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
            intent.putExtra(EXTRA_TITLE, title);
            intent.putExtra(EXTRA_DESC, desc);
            intent.putExtra(EXTRA_PRIORITY, numPicker.getValue());
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}
