package com.example.dancway.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dancway.R;

/**
 * This is user profile activity
 */
public class UserProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Button btn = findViewById(R.id.profilePicButtor);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            ImageView imageView = findViewById(R.id.profilePicture);
                int width = imageView.getDrawable().getIntrinsicWidth();
                int height = imageView.getDrawable().getIntrinsicHeight();
                imageView.getLayoutParams().height = height; // OR
                imageView.getLayoutParams().width = width;
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setImageURI(imageUri);

            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("imageUri", imageUri.toString());
            editor.apply();


        }
    }

}

