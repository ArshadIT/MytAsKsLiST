package com.project.mytaskslist;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.mytaskslist.Note;
import com.project.mytaskslist.R;


import java.util.ArrayList;
import java.util.List;



public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    private List<Note> mAllNotes = new ArrayList<>();
    private OnItemClicked mOnItemClicked;

    public NotesAdapter(OnItemClicked mOnItemClicked) {
        this.mOnItemClicked = mOnItemClicked;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rooView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_single_items, parent, false);
        return new NotesViewHolder(rooView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, final int position) {
        holder.bind(mAllNotes.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClicked.onClick(mAllNotes.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAllNotes.size();
    }

    public Note getNoteAtPosition(int positon) {
        return mAllNotes.get(positon);
    }

    public void updateNotes(List<Note> notes) {
        this.mAllNotes = notes;
        notifyDataSetChanged();
    }
}
