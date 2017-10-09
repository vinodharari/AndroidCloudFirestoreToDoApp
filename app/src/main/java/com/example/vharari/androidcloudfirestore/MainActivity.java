package com.example.vharari.androidcloudfirestore;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    TextView txtDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();

        txtDisplay = findViewById(R.id.txtDisplay);

//        addNewContact();
//        readSingleContact();
//        readSingleContactCustomObject();
//        updateData();
//        deleteData();
        addRealTimeUpdate();
    }

    private void addRealTimeUpdate() {
        DocumentReference contactRefernce = db.collection("AddressBook").document("1");
        contactRefernce.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(e != null){
                    Log.e("ERROR", e.getMessage());
                    return;
                }
                if(documentSnapshot != null && documentSnapshot.exists()){
                    Toast.makeText(MainActivity.this, "Current data : "+documentSnapshot.getData(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void deleteData() {
        db.collection("AddressBook").document("1")
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "Deleted !!!", Toast.LENGTH_LONG).show();
            }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateData() {
        DocumentReference contact = db.collection("AddressBook").document("1");
        contact.update("name", "VinsSup")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Updated !!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void readSingleContactCustomObject() {
        DocumentReference contact = db.collection("AddressBook").document("1");
        contact.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                AddressBook readContact = documentSnapshot.toObject(AddressBook.class);
                StringBuilder data = new StringBuilder("");
                data.append("Name : ").append(readContact.getName());
                data.append("\nEmail : ").append(readContact.getEmail());
                data.append("\nMobile : ").append(readContact.getMobile());
                txtDisplay.setText(data);
            }
        });
    }

    private void readSingleContact() {

        DocumentReference contact = db.collection("AddressBook").document("1");
        contact.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    StringBuilder data = new StringBuilder("");
                    data.append("Name : ").append(doc.getString("name"));
                    data.append("\nEmail : ").append(doc.getString("email"));
                    data.append("\nMobile : ").append(doc.getString("mobile"));
                    txtDisplay.setText(data);
                }
            }
        });
    }

    public void addNewContact() {
        db = FirebaseFirestore.getInstance();

        Map<String, Object> newContact = new HashMap<>();
        newContact.put("name", "jain1");
        newContact.put("email", "vins@jain.com");
        newContact.put("mobile", "8951387273");

        db.collection("AddressBook").document("1")
                .set(newContact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Added new contact", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ERROR", e.getMessage());
                    }
                });
    }
}
