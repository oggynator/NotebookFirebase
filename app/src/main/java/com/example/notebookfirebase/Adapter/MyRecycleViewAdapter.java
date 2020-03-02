package com.example.notebookfirebase.Adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebookfirebase.Model.Note;
import com.example.notebookfirebase.R;
import com.example.notebookfirebase.ViewHolder.ViewHolder;

import java.util.List;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Note> notes;

    public MyRecycleViewAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, parent, false), notes, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.initViewHolder(position);
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }
}
