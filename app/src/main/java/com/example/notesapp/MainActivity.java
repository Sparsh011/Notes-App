package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button signup, forgotPassword, login;
    private EditText mSignInEmail, mSignInPassword;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup = findViewById(R.id.newusersignup);
        forgotPassword = findViewById(R.id.buttonforgotpassword);
        login = findViewById(R.id.buttonlogin);
        mSignInEmail = findViewById(R.id.loginemail);
        mSignInPassword = findViewById(R.id.loginpassword);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
//            If user is already logged in, then no need to login that user again
            finish();
            startActivity(new Intent(MainActivity.this, NotesActivity.class));
        }
//        ---------------------------------------------------------------------------------------------------
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Signup.class));
            }
        });
//         -----------------------------------------------------------------------------------------------------------------------------------
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            }
        });
//        -----------------------------------------------------------------------------------------------------------------
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mSignInEmail.getText().toString();
                String password = mSignInPassword.getText().toString();
                if (email.trim().isEmpty() || password.trim().isEmpty()){
                    Toast.makeText(MainActivity.this, "All fields are required to login", Toast.LENGTH_SHORT).show();
                }
                else{
//                    login the user

                    firebaseAuth.signInWithEmailAndPassword(email.trim(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
//                                Means everything is fine

                                checkMailVerification();
//                                This method will check whether the email is already verified or not.
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Account doesn't exist!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void checkMailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser.isEmailVerified()){
            Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this, NotesActivity.class));
        }
        else{
            Toast.makeText(getApplicationContext(), "Verify your mail first!", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}