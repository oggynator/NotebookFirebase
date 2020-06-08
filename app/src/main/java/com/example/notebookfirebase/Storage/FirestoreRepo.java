package com.example.notebookfirebase.Storage;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebookfirebase.Model.Note;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreRepo {

    private FirebaseFirestore firestore;
    private final String notesToDatabase = "notes";
    private static FirestoreRepo instance = new FirestoreRepo();


    //gets the instance of firestore belonging to this project, to be used in this class
    private FirestoreRepo() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    //Get the the instance of firestore
    public static FirestoreRepo getInstance() {
        return instance;
    }

    public void setChangeListener(final List<Note> notes, final RecyclerView.Adapter adapter) {
        firestore.collection(notesToDatabase).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                notes.clear();
                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                    notes.add(FirestoreRepo.parseNote(doc));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void addOrUpdateNote(Note note) {
        DocumentReference doc;
        if (note.isNew()) {
            doc = firestore.collection(notesToDatabase).document();
        } else {
            doc = firestore.collection(notesToDatabase).document(note.getId());
        }

        Map<String, String> map = new HashMap<>();
        map.put("title", note.getTitle());
        map.put("content", note.getContent());

        doc.set(map);
    }

    public void deleteNote(Note note) {
        DocumentReference doc = firestore.collection(notesToDatabase).document(note.getId());
        doc.delete();
    }

    public static Note parseNote(DocumentSnapshot doc) {
        Note result = new Note();
        result.setId(doc.getId());
        result.setTitle(doc.get("title").toString());
        result.setContent(doc.get("content").toString());
        return result;
    }
}
