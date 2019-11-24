package com.project.mytaskslist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> mAllNotes;

    public NoteRepository(Application application) {
        NoteDatabase noteDataBase = NoteDatabase.getInstance(application);
        noteDao = noteDataBase.noteDao();
        mAllNotes = noteDao.getAllNotes();
    }

    public void insert(final Note note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDao.insert(note);
                return null;
            }
        }.execute();
    }

    public void update(final Note note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDao.update(note);
                return null;
            }
        }.execute();
    }

    public void delete(final Note note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDao.delete(note);
                return null;
            }
        }.execute();
    }

    public void deleteAllNotes() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDao.delete();
                return null;
            }
        }.execute();
    }

    // Room DB executes LiveData Types in the back ground so we don't need to wrap this fun in AsyncTask....
    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }
}