package com.example.mmmalthaf.momsmenu;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivityFB extends AppCompatActivity implements View.OnClickListener{

    //Layout
    private Button buttonSignIn;
    private EditText editTextEmail,editTextPassword;
    private TextView textViewSignUp;
    //Firebase
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){//check if logged in
            //start profile acitivty
            finish();
            startActivity(new Intent(getApplicationContext(),WelcomeSplashActivity.class));
        }

        buttonSignIn = (Button)findViewById(R.id.btLogin);
        editTextEmail = (EditText)findViewById(R.id.usernameID);
        editTextPassword = (EditText)findViewById(R.id.passwordID);
        textViewSignUp = (TextView)findViewById(R.id.tvRegister);

        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);


    }

    @Override

    public void onClick(View view) {
        if(view == buttonSignIn){
            loginUser();
        }
        if(view == textViewSignUp){
            startActivity(new Intent(this,RegisterActivityFB.class));
        }

    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //is empty
            Toast.makeText(this,"Type an email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty((password))){
            //isempty
            Toast.makeText(this,"Type a password",Toast.LENGTH_SHORT).show();
            return;//stop from exectution furher
        }
        //all okay
        progressDialog.setMessage("Login in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //success
                            finish();
                            startActivity(new Intent(getApplicationContext(),WelcomeSplashActivity.class));
                        }else{
                            Toast.makeText(LoginActivityFB.this,"Wrong Username or Password",Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                            return;
                        }
                    }
                });

    }
}
