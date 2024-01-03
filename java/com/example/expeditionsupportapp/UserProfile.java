package com.example.expeditionsupportapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserProfile extends AppCompatActivity {

    Button account_settings_button;
    TextView fullname;
    TextView email;
    TextView username;
    FirebaseAuth fireAuth;
    FirebaseFirestore fireStore;
    StorageReference fireStorage;
    String userID;
    Button Account_settings, log_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);

        fullname = findViewById(R.id.FullName);

        email = findViewById(R.id.Email);
        username = findViewById(R.id.UserName);
        Account_settings = findViewById(R.id.Account_setting);
        log_out = (Button) findViewById(R.id.logOut);


        fireAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        fireStorage = FirebaseStorage.getInstance().getReference();

        userID = fireAuth.getCurrentUser().getUid();


        DocumentReference documentReference = fireStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                fullname.setText(documentSnapshot.getString("fullName"));
                email.setText(documentSnapshot.getString("email"));
                username.setText(documentSnapshot.getString("username"));

            }
        });
        Account_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });


    }

    public void openActivity()
    {
        Intent intent = new Intent(this, Account_settings.class);
        intent.putExtra("fullname", fullname.getText().toString());
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("username", username.getText().toString());
        startActivity(intent);
    }

}