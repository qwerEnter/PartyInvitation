package com.example.partyinvitation.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.partyinvitation.Model.LoginModel;
import com.example.partyinvitation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {


    EditText email,password;
    Button btnsignup;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnsignup = findViewById(R.id.btnsignup);

        //nak register
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser();

            }
        });
    }

    //create user
    private void createUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(Register.this, "Enter Your Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(Register.this, "Enter Your Password", Toast.LENGTH_LONG).show();
            return;
        }

        if (userPassword.length()<6) {
            Toast.makeText(Register.this, "Password Must Be More Than 6", Toast.LENGTH_LONG).show();
            return;
        }

        //create user
        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    LoginModel userModel = new LoginModel(userEmail,userPassword);
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(userModel);

                    Toast.makeText(Register.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Register.this, "Welcome", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
