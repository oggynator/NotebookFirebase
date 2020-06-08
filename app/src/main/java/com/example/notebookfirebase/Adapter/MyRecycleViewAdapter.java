package com.example.notebookfirebase.Adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebookfirebase.Model.Note;
import com.example.notebookfirebase.R;
import com.example.notebookfirebase.ViewHolder.ViewHolder;

import java.util.List;

//The adapter is the piece that will connect our data to our RecyclerView and determine the ViewHolder(s) which will need to be used to display that data.
//we always want the adapter to be as simple or "dumb" as possible.

public class MyRecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Note> notes;

    public MyRecycleViewAdapter(List<Note> notes) {
        this.notes = notes;
    }

    //Inflates the layout with the notes
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, parent, false), notes, this);
    }

    //calls the initViewHolder for all the notes
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.initViewHolder(position);
    }


    //return count of the notes list
    @Override
    public int getItemCount() {
        return notes.size();
    }
}
