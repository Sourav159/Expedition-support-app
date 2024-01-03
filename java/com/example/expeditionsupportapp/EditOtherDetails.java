package com.example.expeditionsupportapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditOtherDetails extends AppCompatActivity {

    EditText OldPassword, NewPassword, ConfirmNewPassword;
    FirebaseAuth fireAuth;
    FirebaseUser user;
    Button Button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_other_details);

        fireAuth = FirebaseAuth.getInstance();
        user = fireAuth.getCurrentUser();

        OldPassword = findViewById(R.id.editTextOldPassword);
        NewPassword = findViewById(R.id.editTextNewPassword);
        ConfirmNewPassword = findViewById(R.id.editTextConfirmNewPassword);
        Button_save = findViewById(R.id.saveButton);

        Button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = OldPassword.getText().toString().trim();
                String newPassword = NewPassword.getText().toString().trim();
                String confirmNewPassword = ConfirmNewPassword.getText().toString().trim();
                AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(),oldPassword);
                user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditOtherDetails.this, "The password has been updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomePage.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditOtherDetails.this, "Failed to change the password", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditOtherDetails.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}