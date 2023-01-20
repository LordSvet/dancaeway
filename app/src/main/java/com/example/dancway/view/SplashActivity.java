package com.example.dancway.view;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dancway.R;
import com.example.dancway.controller.SongsListController;

/**
 * This is the splash activity of the logo screen
 */
public class SplashActivity extends AppCompatActivity {

    Animation rotateAnimation;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = (ImageView)findViewById(R.id.logo_rotation);
        rotateAnimation();

        // start activity after 2500 millisec and goes from splash to register activity
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    SongsListController.populateSongs();
                    sleep(2500);
                }catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }
        };
        thread.start();
            }

    /**
     * Method that rotates the imageView of logo
     */
    private void rotateAnimation() {

        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation);
        logo.startAnimation (rotateAnimation);
    }
}
