package com.project.mytaskslist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.notes_singleitem_title)
    TextView titleTxtView;
    @BindView(R.id.notes_single_item_desc_tv)
    TextView descTxtView;
    @BindView(R.id.notes_singleitem_priority)
    TextView priorityTxtView;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(Note note) {
        titleTxtView.setText(note.getTitle());
        descTxtView.setText(note.getDescription());
        priorityTxtView.setText("" + note.getPriority());
    }
}
