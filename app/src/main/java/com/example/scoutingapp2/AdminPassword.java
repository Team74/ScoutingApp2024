package com.example.scoutingapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//This is the screen for the admin password

public class AdminPassword extends AppCompatActivity {

    EditText passwordInput;
    Button check;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_password);

        passwordInput = findViewById(R.id.passwordInput_et);
        check = findViewById(R.id.checkPassword_btn);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UPDATE to a new password
                if(passwordInput.getText().toString().equals("ch33z")) {
                    //Launch the Admin Screen
                    Intent intent = new Intent(AdminPassword.this, Admin.class);
                    startActivity(intent);
                }
                else{
                    //If it is wrong, tell the user
                    Toast.makeText(context, "Not Correct", Toast.LENGTH_SHORT).show();
                    passwordInput.getText().clear();
                }

        }

    });

}
}