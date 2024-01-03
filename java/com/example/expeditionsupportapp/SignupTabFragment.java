package com.example.expeditionsupportapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SignupTabFragment extends Fragment {

    // Variables
    EditText email_editText, fullName_editText, password_editText, username;
    Button signUp_btn;
    TextView view1, view2;
    FirebaseAuth fAuth;
    FirebaseFirestore firestore;
    String ID_user;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        email_editText = root.findViewById(R.id.email_signup);
        fullName_editText = root.findViewById(R.id.fullName);
        username = root.findViewById(R.id.emailTxt);
        password_editText = root.findViewById(R.id.password_signup);
        signUp_btn = root.findViewById(R.id.signup_btn);
        view1 = root.findViewById(R.id.text_view1);
        view2 = root.findViewById(R.id.text_view2);

        // Displaying the fragment elements in main screen.
        email_editText.setTranslationX(0);
        fullName_editText.setTranslationX(0);
        password_editText.setTranslationX(0);
        signUp_btn.setTranslationX(0);
        view1.setTranslationX(0);
        view2.setTranslationX(0);

        fAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullName_editText.getText().toString();
                String userName = username.getText().toString();
                String email = email_editText.getText().toString().trim();
                String password = password_editText.getText().toString().trim();

                if(TextUtils.isEmpty(fullName))
                {
                    fullName_editText.setError("Name is Required");
                    return;
                }

                if(TextUtils.isEmpty(userName))
                {
                    fullName_editText.setError("Username is Required");
                    return;
                }
                if(TextUtils.isEmpty(email))
                {
                    email_editText.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    password_editText.setError("Password is Required");
                    return;
                }
                if(password.length()>=1 && password.length()<6)
                {
                    password_editText.setError("Password must be greater than 5 characters");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            ID_user = fAuth.getCurrentUser().getUid();
                            Toast.makeText(getActivity(), "User is Created", Toast.LENGTH_LONG).show();
                            DocumentReference documentReference = firestore.collection("users").document(ID_user);
                            Map<String,Object> user_map = new HashMap<>();
                            user_map.put("fullName",fullName);
                            user_map.put("username",userName);
                            user_map.put("email", email);
                            documentReference.set(user_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: Profile Created ");
                                }
                            });

                            startActivity(new Intent(getContext(), HomePage.class));
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error: "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
        return root;
    }
}