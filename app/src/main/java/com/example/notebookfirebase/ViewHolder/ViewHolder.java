package com.example.notebookfirebase.ViewHolder;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.notebookfirebase.Model.Note;
import com.example.notebookfirebase.NoteEditActivity;
import com.example.notebookfirebase.R;
import com.example.notebookfirebase.Storage.FirestoreRepo;

import java.util.List;
//Viewholder is the very object that represents each item in our collection and will be used to display it.
public class ViewHolder extends RecyclerView.ViewHolder {

    private List<Note> notes;
    private ConstraintLayout rowLayout;
    private TextView noteTitle;
    private TextView deleteButton;
    private RecyclerView.Adapter adapter;

    public ViewHolder(@NonNull View itemView, List<Note> notes, RecyclerView.Adapter adapter) {
        super(itemView);

        this.adapter = adapter;
        this.notes = notes;
        rowLayout = (ConstraintLayout) itemView;
        noteTitle = rowLayout.findViewById(R.id.noteTitle);
        deleteButton = rowLayout.findViewById(R.id.deleteButton);
    }

    //When the viewholder is initialised it will set the title text for the notes, and add onclick and onlongclick events to them.
    public void initViewHolder(final int position) {
        noteTitle.setText(notes.get(position).getTitle());

        //Only works if there has been a long click on a note
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirestoreRepo.getInstance().deleteNote(notes.get(position));
                notes.remove(position);
                deleteButton.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });

        //Open the noteedit activity and send the note_key as an extra, so the Noteedit activity knows which note to open
        rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoteEditActivity.class);
                intent.putExtra(NoteEditActivity.NOTE_KEY, notes.get(position));
                v.getContext().startActivity(intent);
            }
        });

        //Makes the delete button visible
        rowLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (deleteButton.getVisibility() == View.GONE) {
                    deleteButton.setVisibility(View.VISIBLE);
                } else {
                    deleteButton.setVisibility(View.GONE);
                }
                return true;
            }
        });
    }
}
