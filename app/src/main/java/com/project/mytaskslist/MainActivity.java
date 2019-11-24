package com.project.mytaskslist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements OnItemClicked {
    public static String EXTRA_TITLE = "com.project.mytaskslist.EXTRA_TITLE";
    public static String EXTRA_DESC = "com.project.mytaskslist.EXTRA_DESC";
    public static String EXTRA_PRIORITY = "com.project.mytaskslist.EXTRA_PRIORITY";
    public static String EXTRA_ID = "com.project.mytaskslist.EXTRA_ID";

    private static final int RC_SIGN_IN = 42;
    private static final int ADDRESULT_CODE = 565;
    private static final int EDITRESULT_CODE = 172;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton addActionButton;

    private NoteViewModel noteViewModel;
    private NotesAdapter notesAdapter;

    @BindView(R.id.notesrecyclerview)
    RecyclerView mNotesRecyclerView;


    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
        mNotesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNotesRecyclerView.setHasFixedSize(true);
        notesAdapter = new NotesAdapter(this);
        mNotesRecyclerView.setAdapter(notesAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                notesAdapter.updateNotes(notes);

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note note = notesAdapter.getNoteAtPosition(viewHolder.getAdapterPosition());
                noteViewModel.delete(note);
                Toast.makeText(MainActivity.this, "Note Deleted!", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(mNotesRecyclerView);


        addActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MainActivity.this, "HHHHH", Toast.LENGTH_SHORT).show();

                Intent note = new Intent(MainActivity.this, AddNote.class);
                startActivity(note);

               /* intent.putExtra(EXTRA_TITLE, note.getTitle());
                intent.putExtra(EXTRA_DESC, note.getDescription());
                intent.putExtra(EXTRA_PRIORITY, note.getPriority());
                intent.putExtra(EXTRA_ID, note.getId());

                startActivityForResult(intent, ADD_RESULT_CODE);
                MainActivity.this.onClick();

                */
                finish();
            }
        });
    }

    public void onClick(Note note) {


        Intent intent = new Intent(MainActivity.this ,AddNote.class);

        intent.putExtra(EXTRA_TITLE, note.getTitle());
        intent.putExtra(EXTRA_DESC, note.getDescription());
        intent.putExtra(EXTRA_PRIORITY, note.getPriority());
        intent.putExtra(EXTRA_ID, note.getId());

              startActivityForResult(intent, ADDRESULT_CODE);
        Toast.makeText(this, "H note add", Toast.LENGTH_LONG).show();
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN && resultCode==RESULT_OK){

        }
        if (requestCode == ADDRESULT_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(EXTRA_TITLE);
            String desc = data.getStringExtra(EXTRA_DESC);
            int priority = data.getIntExtra(EXTRA_PRIORITY, 1);
            noteViewModel.insert(new Note(title, desc, priority));
            Toast.makeText(this, "Note Saved !! ", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == EDITRESULT_CODE && resultCode == RESULT_OK) {

            int id = data.getIntExtra(EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "can't update Note !", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(EXTRA_TITLE);
            String desc = data.getStringExtra(EXTRA_DESC);
            int priority = data.getIntExtra(EXTRA_PRIORITY, 1);

            noteViewModel.update(new Note(id, title, desc, priority));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllNote: {
                noteViewModel.deleteAllNotes();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void startLoginActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    public void signOut(View v) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startLoginActivity();
                    }
                });
    }

    }


