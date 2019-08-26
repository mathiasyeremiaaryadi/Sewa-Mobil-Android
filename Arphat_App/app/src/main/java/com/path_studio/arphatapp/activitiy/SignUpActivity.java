package com.path_studio.arphatapp.activitiy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.path_studio.arphatapp.R;
import com.path_studio.arphatapp.check_internet_connection;

public class SignUpActivity extends AppCompatActivity {

    private Button register;
    private TextView login;
    private EditText mEmail, mPassword, mUsername;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //check Internet Connection
        check_internet_connection cic = new check_internet_connection();
        if(cic.check_internet(getApplicationContext()) == false){
            //direct ke halaman Disconnect
            Intent i = new Intent(SignUpActivity.this, DisconnectActivity.class);
            startActivity(i);
            finish();
        }

        //------------------------------------------------------------------------------------------

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user != null){
                    Intent i = new Intent(SignUpActivity.this, HalamanUtamaActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }
            }
        };

        mUsername = (EditText) findViewById(R.id.username);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        login = (TextView) findViewById(R.id.login_btn);
        register = (Button) findViewById(R.id.signup_btn);

        //Direct ke halaman login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        //proses Sign Up
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = mUsername.getText().toString();
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                //check apakah semua sudah terisi
                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(username)){
                    Toast.makeText(SignUpActivity.this, "Please Fill All Form before Submit", Toast.LENGTH_SHORT).show();
                }else if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)){
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "Sign up Error", Toast.LENGTH_SHORT).show();
                            }else{
                                String user_id = mAuth.getCurrentUser().getUid();

                                DatabaseReference current_user_db = mDatabase.child("Users").child("Customers").child(user_id);
                                current_user_db.child("Username").setValue(username);
                                current_user_db.child("Email").setValue(email);
                                current_user_db.child("Password").setValue(password);
                                current_user_db.child("SignUp Method").setValue("Email&Password");
                            }
                        }
                    });
                }else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)){
                    Toast.makeText(SignUpActivity.this, "Username, Email, or Password is Still Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

}