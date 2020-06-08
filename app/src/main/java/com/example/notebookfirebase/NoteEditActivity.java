package com.example.notebookfirebase;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notebookfirebase.Model.Note;
import com.example.notebookfirebase.Storage.FirestoreRepo;
import com.google.firebase.firestore.FirebaseFirestore;


public class NoteEditActivity extends AppCompatActivity {

    public static final String NOTE_KEY = "NOTE";
    private Note noteBeingEdited;
    private TextView titleInput;
    private TextView contentInput;
    private FirestoreRepo firestoreRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        this.titleInput = findViewById(R.id.titleInput);
        this.contentInput = findViewById(R.id.contentInput);
        this.firestoreRepo = FirestoreRepo.getInstance();

        //Gets the NOTE_KEY
        //Sets title and the content from the note that was clicked
        Intent intent = getIntent();
        if (intent.hasExtra(NOTE_KEY)) {
            this.noteBeingEdited = (Note) intent.getSerializableExtra(NOTE_KEY);
            titleInput.setText(this.noteBeingEdited.getTitle());
            contentInput.setText(this.noteBeingEdited.getContent());
        } else {
            this.noteBeingEdited = new Note();
        }

        //Provides us with the back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Saves the note when back is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.noteBeingEdited.setTitle(titleInput.getText().toString());
                this.noteBeingEdited.setContent(contentInput.getText().toString());
                if (this.noteBeingEdited.isNew()) {
                    if (!(noteBeingEdited.getTitle().isEmpty() && noteBeingEdited.getContent().isEmpty())) {
                        if (noteBeingEdited.getTitle().isEmpty()) {
                            noteBeingEdited.setTitle("Untitled note"); //If no title is put in
                        }
                        firestoreRepo.addOrUpdateNote(this.noteBeingEdited); //Save the note via the firestorerepo method addOrUpdateNote()
                    }
                } else {
                    firestoreRepo.addOrUpdateNote(this.noteBeingEdited); //Save the updated note
                }
                finish(); //<-- finish the activity, we dont want to return back to this activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //adds the options menu, adds the delete button if the note is not new
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_edit_menu, menu);
        if (noteBeingEdited.isNew()) {
            MenuItem deleteOption = menu.findItem(R.id.deleteMenu);
            if (deleteOption != null) {
                deleteOption.setVisible(false);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    //Deletes note via the deleteNote() method and finish the activity, called in the menu with an onclick.
    public void deleteNote(MenuItem menuItem) {
        firestoreRepo.deleteNote(noteBeingEdited);
        finish();
    }


    //Finish activity without doing anything, called in the menu with an onclick.
    public void cancel(MenuItem menuItem) {
        finish();
    }
}
