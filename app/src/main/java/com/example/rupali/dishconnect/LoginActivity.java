package com.example.rupali.dishconnect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    EditText email_edittext;
    EditText password_edittext;
    Button signIn;
    Button signUp;
    String email_text;
    String password_text, adhar_no;
    EditText  adhar;
    private FirebaseAuth mAuth;
    TextView resetPassword;
    FirebaseDatabase database;
    FirebaseUser currentUser;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email_edittext=findViewById(R.id.login_name);
        database = FirebaseDatabase.getInstance();
        password_edittext=findViewById(R.id.login_password);
        adhar = findViewById(R.id.adhar);
        resetPassword=findViewById(R.id.reset_password_text);
        signIn=findViewById(R.id.signin_button);
        signUp=findViewById(R.id.signup_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInToYourAccount();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpToYourAccount();
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });


    }

    private void resetPassword() {
        String emailAddress = email_edittext.getText().toString();

        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,"Email sent",Toast.LENGTH_SHORT).show();
                            Log.d("Authentication", "Email sent.");
                        }
                    }
                });
    }

    private void signUpToYourAccount() {
        email_text=email_edittext.getText().toString();
        password_text=password_edittext.getText().toString();
        adhar_no = adhar.getText().toString();

        final UserData userData = new UserData(email_text ,password_text ,adhar_no);
        if(!email_text.isEmpty()&&!password_text.isEmpty()){
            mAuth.createUserWithEmailAndPassword(email_text, password_text)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LoginActivity.this , "successfull signup", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                mRef = database.getReference(user.getUid());
                                mRef.setValue(userData);


//                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Authentication", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        else if(email_text.isEmpty()&&password_text.isEmpty()){
            Toast.makeText(LoginActivity.this,"Email and password are required",Toast.LENGTH_SHORT).show();
        }
        else if(email_text.isEmpty()){
            Toast.makeText(LoginActivity.this,"Email is required",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(LoginActivity.this,"password is required",Toast.LENGTH_SHORT).show();
        }
    }

    private void signInToYourAccount() {
        email_text=email_edittext.getText().toString();
        password_text=password_edittext.getText().toString();
        if(!email_text.isEmpty()&&!password_text.isEmpty()){
            mAuth.signInWithEmailAndPassword(email_text, password_text)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this , "login successfull" , Toast.LENGTH_SHORT).show();
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Authentication", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent=new Intent();
                                setResult(Constants.LOGIN_ACTIVITY_RESULT_CODE);
                                finish();
//                            updateUI(user);
                            } else {
                                Toast.makeText(LoginActivity.this , "user is already registered " , Toast.LENGTH_SHORT).show();
                                // If sign in fails, display a message to the user.
                                Log.w("Authentication", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

//                            updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        else if(email_text.isEmpty()&&password_text.isEmpty()){
            Toast.makeText(LoginActivity.this,"Email and password are required",Toast.LENGTH_SHORT).show();
        }
        else if(email_text.isEmpty()){
            Toast.makeText(LoginActivity.this,"Email is required",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(LoginActivity.this,"password is required",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }


//    private void updateUI(FirebaseUser currentUser) {
//    }
}
