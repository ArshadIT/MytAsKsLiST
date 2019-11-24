package com.project.mytaskslist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNote extends AppCompatActivity {
    public static String EXTRA_TITLE = "com.project.mytaskslist.EXTRA_TITLE";
    public static String EXTRA_DESC = "com.project.mytaskslist.EXTRA_DESC";
    public static String EXTRA_PRIORITY = "com.project.mytaskslist.EXTRA_PRIORITY";
    public static String EXTRA_ID = "com.project.mytaskslist.EXTRA_ID";

    @BindView(R.id.addnotetitleedittext)
    EditText editText;
    @BindView(R.id.adddescedittext)
    EditText addNoteDescriptionEdtTxt;
    @BindView(R.id.prioritynrpricker)
    NumberPicker numberPicker;
    @BindView(R.id.add_notpriorityedittext)
    TextView addNotePriorityEdtTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);
        ButterKnife.bind(this);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");

            editText.setText(intent.getStringExtra(EXTRA_TITLE));
            addNoteDescriptionEdtTxt.setText(intent.getStringExtra(EXTRA_DESC));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));

        } else {
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note: {
                saveNote();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void saveNote() {
        String title = editText.getText().toString();
        String desc = addNoteDescriptionEdtTxt.getText().toString();
        int priority = numberPicker.getValue();

        if (title.trim().isEmpty() | desc.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description !", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DESC, desc);
        intent.putExtra(EXTRA_PRIORITY, priority);


        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1) {
            intent.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();

    }

}
