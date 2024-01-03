package com.example.expeditionsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class AnimationPage extends AppCompatActivity {

    ImageView background;
    LottieAnimationView lottieAnimationView;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.animation_page);
        fAuth = FirebaseAuth.getInstance();
        background = findViewById(R.id.background);
        lottieAnimationView = findViewById(R.id.cycle);

        background.animate().translationY(-2100).setDuration(1000).setStartDelay(4000);

        lottieAnimationView.animate().translationY(-2100).setDuration(1000).setStartDelay(4000);


        // Display the next activity screen after timer
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(fAuth.getCurrentUser() != null) {
                            Log.d("TAG ", " "+fAuth.getCurrentUser().getEmail());
                            Intent intent = new Intent(AnimationPage.this, HomePage.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(AnimationPage.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        }, 4600);




    }
}