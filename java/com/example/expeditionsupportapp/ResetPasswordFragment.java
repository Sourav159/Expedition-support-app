package com.example.expeditionsupportapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordFragment extends Fragment {

    Button resetBtn;
    EditText emailTxt;
    FirebaseAuth fAuth;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.reset_password, container, false);
        resetBtn = root.findViewById(R.id.reset_btn);
        emailTxt = root.findViewById(R.id.emailTxt);
        fAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailID = emailTxt.getText().toString();
                EditText resetEmail = new EditText(v.getContext());
                fAuth.sendPasswordResetEmail(emailID).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        final AlertDialog.Builder passwordReset = new AlertDialog.Builder(v.getContext());
                        passwordReset.setTitle("Password Reset");
                        passwordReset.setMessage("A password reset link has been sent to your email id.");
                        passwordReset.setView(resetEmail);
                        passwordReset.create().show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        final AlertDialog.Builder passwordReset = new AlertDialog.Builder(v.getContext());
                        passwordReset.setTitle("Failed Password Reset");
                        passwordReset.setMessage(e.getMessage().toString());
                        passwordReset.setView(resetEmail);
                        passwordReset.create().show();

                    }
                });
            }
        });
        return root;
    }
}
