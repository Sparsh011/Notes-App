package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText forgotPasswordEmail;
    private Button passwordRecoverButton, goBackToLogin;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgotPasswordEmail = findViewById(R.id.forgotpasswordemail);
        passwordRecoverButton = findViewById(R.id.passwordrecoverbutton);
        goBackToLogin = findViewById(R.id.gobacktologin);

        firebaseAuth = FirebaseAuth.getInstance();
        goBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
            }
        });

        passwordRecoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = forgotPasswordEmail.getText().toString();
                if (mail.trim().isEmpty()){
                    Toast.makeText(ForgotPassword.this, "Enter Email ID", Toast.LENGTH_SHORT).show();
                }
                else{
//                    We have to send mail to recover password


                    firebaseAuth.sendPasswordResetEmail(mail.trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
//                                Means mail is sent to the user
                                Toast.makeText(getApplicationContext(), "Recovery Mail is sent, Recover your Password using that Mail.", Toast.LENGTH_SHORT).show();
                                finish();
//                                This will finish this activity because work here is done.
                                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Incorrect email or account does not exist!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}