package com.example.quantumsocialapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quantumsocialapp.Model.Users;
import com.example.quantumsocialapp.databinding.ActivitySignUpBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're Creating your Account");

        binding.tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etname.getText().toString().isEmpty() || binding.etemail.getText().toString().isEmpty()
                        || binding.etpassword.getText().toString().isEmpty())
                {
                    Toast.makeText(SignUpActivity.this,"Please fill all the text",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.show();
                    auth.createUserWithEmailAndPassword(binding.etemail.getText().toString(),
                            binding.etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                String name =binding.etname.getText().toString();
                                String email = binding.etemail.getText().toString();
                                String password = binding.etpassword.getText().toString();
                                String phone_number = binding.etphone.getText().toString();

                                String id = task.getResult().getUser().getUid();

                                Users user = new Users();
                                user.setUsername(name);
                                user.setEmail(email);
                                user.setPassword(password);
                                user.setUserphone(phone_number);

                                database.getReference().child("User").child(id).setValue(user);
                                Toast.makeText(SignUpActivity.this,"User Created Successfully",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);

                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        binding.tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}