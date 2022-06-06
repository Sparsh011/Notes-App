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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
    private EditText mSignupEmail, mSignupPassword;
    private Button SignUp, goToLogin;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mSignupEmail = findViewById(R.id.signupemail);
        mSignupPassword = findViewById(R.id.signuppassword);
        SignUp = findViewById(R.id.buttonsignup);
        goToLogin = findViewById(R.id.taketologinscreen);

//        Initialising firebaseAuth -
        firebaseAuth = FirebaseAuth.getInstance();
//        ------------------------------------------------------------------------------------------------------------------
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, MainActivity.class));
            }
        });
//   -------------------------------------------------------------------------------------------------------------------
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mSignupEmail.getText().toString();
                String password = mSignupPassword.getText().toString();

                if (email.trim().isEmpty() || password.trim().isEmpty()){
                    Toast.makeText(Signup.this, "All fields are required to Sign Up", Toast.LENGTH_SHORT).show();
                }
                else if (password.trim().length()<7){
                    Toast.makeText(Signup.this, "Password length should be greater than 7", Toast.LENGTH_SHORT).show();
                }

//                ---------------------------------------------------------------------------------------------------

                else{
//                    register user to firebase -

                    firebaseAuth.createUserWithEmailAndPassword(email.trim(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            This will register the user on firebase
                            if (task.isSuccessful()){
//                                This means ki user is successfully registered.
                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

//                                To verify the user (whether the user has entered correct email or not, if the details are correct only then the user will be able to login)-
                                sendEmailVerification();
                            }
                            
                            else{
                                Toast.makeText(getApplicationContext(), "Failed to register!" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });



    }

//    Sending email verification -
    private void sendEmailVerification(){
//        Taking instance of current user -
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
//            Means ki user exists and details were authentic
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Verification Email is sent, verify it and then login.", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
//                    Doing this because sign up is complete and now we have to take the user to the login page
                    startActivity(new Intent(Signup.this, MainActivity.class));
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "Failed to send verification email!", Toast.LENGTH_SHORT).show();
        }
    }
}