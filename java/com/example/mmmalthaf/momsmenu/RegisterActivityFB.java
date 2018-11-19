package com.example.mmmalthaf.momsmenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RegisterActivityFB extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private ImageView proPic;
    private EditText editTextEmail,editTextPassword;
    private TextView textViewSingIn;
    private EditText etName;
    private EditText etAge;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private DatabaseReference dbRef;
    FirebaseUser user;
    private StorageReference proPicRef;

    private static final int Gallery_Pick = 1;
    private String downloadUrl;

    private Uri ImageUri;

    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        proPicRef = FirebaseStorage.getInstance().getReference();


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            //start profile acitivty
            finish();
            startActivity(new Intent(getApplicationContext(),WelcomeSplashActivity.class));
        }
        etAge = (EditText)findViewById(R.id.ageID);
        etName = (EditText)findViewById(R.id.nameID);
        buttonRegister = (Button)findViewById(R.id.btRegister);
        editTextEmail = (EditText)findViewById(R.id.usernameID);
        editTextPassword = (EditText)findViewById(R.id.passwordID);
        textViewSingIn = (TextView)findViewById(R.id.sgnIn);

        buttonRegister.setOnClickListener(this);
        textViewSingIn.setOnClickListener(this);

        proPic = (ImageView)findViewById(R.id.selectPostImg);
        proPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }



    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Gallery_Pick && resultCode == RESULT_OK && data!=null){
            ImageUri = data.getData();
            proPic.setImageURI(ImageUri);
        }
    }

    private void registerUser(){

        ValidatePostInfo();

        //all okay
        progressDialog.setMessage("Registering user...\nAnd login in...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            if(firebaseAuth.getCurrentUser()!=null){
                                //start profile acitivty
                                user = firebaseAuth.getCurrentUser();

                                //StoringProPicToFirebase();
                                StorageReference filePath = proPicRef.child("ProPics").child(ImageUri.getLastPathSegment() + user.getUid());
                                filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if(task.isSuccessful()){
                                            HashMap<String,Object> userData = new HashMap<>();
                                            downloadUrl = task.getResult().getDownloadUrl().toString();
                                            userData.put("name",etName.getText().toString());
                                            userData.put("email",editTextEmail.getText().toString());
                                            userData.put("age",Integer.parseInt(etAge.getText().toString()));
                                            userData.put("proPic",downloadUrl);
                                            dbRef = FirebaseDatabase.getInstance().getReference().child("users");
                                            dbRef.child(user.getUid()).setValue(userData);
                                        }
                                        else {
                                        }
                                    }
                                });
                                finish();
                                startActivity(new Intent(getApplicationContext(),WelcomeSplashActivity.class));
                            }
                        }else{
                            Toast.makeText(RegisterActivityFB.this,"not done registering",Toast.LENGTH_SHORT).show();

                            return;
                        }

                    }
                });

    }

    private void ValidatePostInfo() {
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            //is empty
            Toast.makeText(this,"Type your email",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty((password))){
            //isempty
            Toast.makeText(this,"Type your password",Toast.LENGTH_SHORT).show();
            return;//stop from exectution furher
        }
        else if(ImageUri == null){
            Toast.makeText(getApplicationContext(),"Please Select Post Image..",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(etAge.getText().toString().trim())){
            //isempty
            Toast.makeText(this,"Type your age",Toast.LENGTH_SHORT).show();
            return;//stop from exectution furher
        }
    }


    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }
        if(view == textViewSingIn){
            startActivity(new Intent(this,LoginActivityFB.class));
        }
    }
}
