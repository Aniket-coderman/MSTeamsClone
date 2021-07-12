package com.example.msteams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    FirebaseFirestore database;
    EditText name,email,password;
    Button createAccount, AlreadyAccount;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        name = findViewById(R.id.NameText);
        email = findViewById(R.id.emailBox1);
        password = findViewById(R.id.passwordBox1);

        createAccount = findViewById(R.id.createBtn1);
        AlreadyAccount = findViewById(R.id.loginBtn1);

        AlreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smail,spass,sname;
                smail = email.getText().toString();
                spass = password.getText().toString();
                sname = name.getText().toString();
                final User user = new User();
                user.setEmail(smail);
                user.setPassword(spass);
                user.setName(sname);

                auth.createUserWithEmailAndPassword(smail,spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                }
                            });
                            Toast.makeText(SignupActivity.this, "Account Successfully Created!!", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(SignupActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}