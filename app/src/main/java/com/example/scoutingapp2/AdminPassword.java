package com.example.scoutingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
                if(passwordInput.getText().toString().equals("ch33z")) {
                    Intent intent = new Intent(AdminPassword.this, Admin.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(context, "Not Correct", Toast.LENGTH_SHORT).show();
                    passwordInput.getText().clear();
                }

        }

    });

}
}