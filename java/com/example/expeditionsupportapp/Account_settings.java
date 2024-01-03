package com.example.expeditionsupportapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Account_settings extends AppCompatActivity {

    EditText profileFullName, profileEmail, profileUsername;
    ImageView profileImage;
    Button changeImage, edit_other_details;
    StorageReference storageReference;
    FirebaseFirestore fireStore;
    FirebaseAuth fireAuth;
    Button saveButton;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_account_settings);

        fireAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        user = fireAuth.getCurrentUser();

        Intent data = getIntent();

        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference refProfile = storageReference.child("users/"+fireAuth.getCurrentUser().getUid()+"profile.jpg");

        refProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        String fullname = data.getStringExtra("fullname");
        String email = data.getStringExtra("email");
        String username = data.getStringExtra("username");

        profileFullName = findViewById(R.id.editTextTextFullName);
        profileEmail = findViewById(R.id.editTextTextEmail);
        profileUsername = findViewById(R.id.editTextUsername);
        profileImage = findViewById(R.id.profileImage2);
        changeImage = findViewById(R.id.changeImageButton);
        saveButton = findViewById(R.id.saveButton);
        edit_other_details = (Button) findViewById(R.id.edit_details_button);
        edit_other_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_other_details();
            }
        });

        profileFullName.setText(fullname);
        profileEmail.setText(email);
        profileUsername.setText(username);



        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email_string = profileEmail.getText().toString();
                if(profileFullName.getText().toString().isEmpty() ||  profileEmail.getText().toString().isEmpty() || profileUsername.getText().toString().isEmpty())
                {
                    Toast.makeText(Account_settings.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }


                user.updateEmail(email_string).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference refDoc = fireStore.collection("users").document(user.getUid());
                        Map<String, Object> edit = new HashMap<>();
                        edit.put("email",email_string);
                        edit.put("fullName", profileFullName.getText().toString());
                        edit.put("username", profileUsername.getText().toString());
                        refDoc.update(edit).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Account_settings.this, "The Data is changed", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),UserProfile.class));
                                finish();
                            }
                        });

                        Toast.makeText(Account_settings.this, "Email is successfully updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Account_settings.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri ImageUri = data.getData();
//                profileImage.setImageURI(ImageUri);
                uploadImageFirebase(ImageUri);
            }
        }
    }

    private void uploadImageFirebase(Uri ImageUri) {
        final StorageReference ref = storageReference.child("users/"+fireAuth.getCurrentUser().getUid()+"profile.jpg");
        ref.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Account_settings.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void open_other_details()
    {
        Intent intent = new Intent(this,EditOtherDetails.class);
        startActivity(intent);
    }

}