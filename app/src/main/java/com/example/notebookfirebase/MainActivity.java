package com.example.notebookfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebookfirebase.Adapter.MyRecycleViewAdapter;
import com.example.notebookfirebase.Model.Note;
import com.example.notebookfirebase.Storage.FirestoreRepo;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView notesList;
    private MyRecycleViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private FirestoreRepo firestoreRepo;
    private List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firestoreRepo = FirestoreRepo.getInstance();

        notesList = findViewById(R.id.notesList);
        layoutManager = new LinearLayoutManager(this);
        notesList.setLayoutManager(layoutManager);
        adapter = new MyRecycleViewAdapter(notes);
        notesList.setAdapter(adapter);
        firestoreRepo.setChangeListener(notes, adapter); //fills the adapter with the notes that are in the database
    }

    //Creates the options menu with the option to add a new note
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //Gets called on the new note click
    public void newNote(MenuItem menuItem) {
        startActivity(new Intent(this, NoteEditActivity.class));
    }
}