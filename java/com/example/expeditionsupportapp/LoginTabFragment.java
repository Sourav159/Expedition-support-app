package com.example.expeditionsupportapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {

    // Variables
    EditText email_editText, password_editText;
    Button login_btn;
    TextView view1, view2;
    FirebaseAuth fAuth;
    TextView forgetPassword;
    float v = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        email_editText = root.findViewById(R.id.email_login);
        password_editText = root.findViewById(R.id.password_login);
        login_btn = root.findViewById(R.id.button_login);
        view1 = root.findViewById(R.id.text_view1);
        view2 = root.findViewById(R.id.text_view2);
        forgetPassword = root.findViewById(R.id.resetPassword_textView);
        // Displaying the fragment elements on the main screen.
        forgetPassword.setTranslationX(800);
        email_editText.setTranslationX(800);
        password_editText.setTranslationX(800);
        login_btn.setTranslationX(800);
        view1.setTranslationX(800);
        view2.setTranslationX(800);

        email_editText.setAlpha(v);
        forgetPassword.setAlpha(v);
        password_editText.setAlpha(v);
        login_btn.setAlpha(v);
        view1.setAlpha(v);
        view2.setAlpha(v);

        // Animations for the elements.
        email_editText.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password_editText.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        view1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        view2.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        login_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        forgetPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();

        fAuth = FirebaseAuth.getInstance();
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment resetPassword = new ResetPasswordFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_login, resetPassword ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_editText.getText().toString().trim();
                String password = password_editText.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {
                    email_editText.setError("Email Cannot Be Empty");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    password_editText.setError("Password Cannot Be Empty");
                    return;
                }
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "Logged in Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getContext(), HomePage.class));
                        }
                        else
                        {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });



        return root;
    }
}