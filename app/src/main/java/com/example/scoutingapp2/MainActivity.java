package com.example.scoutingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button toInputView_btn, toAdminPassword_btn;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toInputView_btn = findViewById(R.id.toInputView_btn);
        toAdminPassword_btn = findViewById(R.id.toAdminPassword_btn);

        toInputView_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Input_View.class);
                startActivity(intent);
            }
        });

        toAdminPassword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdminPassword.class);
                startActivity(intent);
            }
        });

        picture = findViewById(R.id.teamPicture);
        //Glide.with(this).load("https://www.thebluealliance.com/team/254/2019").into(picture);
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}