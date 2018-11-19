package com.example.mmmalthaf.momsmenu.AddRecipe;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mmmalthaf.momsmenu.RecipeFeed.PostTabActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.mmmalthaf.momsmenu.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddPost extends AppCompatActivity {
    private final String TAG = this.getClass().getName();

    private int imageNum = 0;

    private EditText title;

    private RadioButton veg,nonVeg;


    private EditText ingName;
    private EditText ingAmnt;
    private EditText introText;
    private TextView ingTextName;
    private TextView ingAmpuntText;

    private ProgressDialog progressDialog;

    private EditText utName;
    private EditText utAmnt;
    private TextView utTextName;
    private TextView utAmpuntText;
    private Boolean isVeg=false;

    private EditText stpName;
    private TextView stpTextName;
    private EditText stpDetail;
    private TextView stpTextDetail;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference dbRefPost,dbRefUser,dbRefPostAll;
    private StorageReference postFinPicsRef,postIngPicsRef;

    Button postRecipe;
    HashMap<String,Object> IngImages = new HashMap<>();
    HashMap<String,Object> utImages = new HashMap<>();
    HashMap<String,Object> stpImages = new HashMap<>();
    HashMap<String,Object> ftImages = new HashMap<>();

    SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();

    private static final int Gallery_Pick_Fin = 1;
    private static final int Gallery_Pick_Ing = 2;
    private static final int Gallery_Pick_Ut = 3;
    private static final int Gallery_Pick_Stp = 4;
    private Uri ImageUriFin,ImageUriIng,ImageUriUt,ImageUriStp;
    ImageView addFinImg;
    private String downloadUrlFin,downloadUrlIng,downloadUrlUt;

    private String ingredientsNames = "",ingredientsAmount = "",ingredientsPics = "",utensilsNames= "",
            utensilsAmount = "",utensilsPics = "",steps= "",stepsDetail= "",name = "";

    private String ingDispName="",ingDispAmnt = "",utDispName="",utDispAmnt = "",stpDispName="",stpDispAmnt = "";

    String proUrl;
    String aa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        progressDialog = new ProgressDialog(this);

        imageNum = 0;
        veg = (RadioButton)findViewById(R.id.vegID) ;
        nonVeg = (RadioButton)findViewById(R.id.nonVegID) ;

        veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veg.setActivated(true);
                nonVeg.setActivated(false);
                veg.setChecked(true);
                nonVeg.setChecked(false);
                isVeg = true;
            }
        });
        nonVeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonVeg.setActivated(true);
                veg.setActivated(false);
                veg.setChecked(false);
                nonVeg.setChecked(true);
                isVeg=false;
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        postFinPicsRef = FirebaseStorage.getInstance().getReference();
        postIngPicsRef = FirebaseStorage.getInstance().getReference();

        dbRefPostAll = FirebaseDatabase.getInstance().getReference().child("PostsAll");
        dbRefPost = FirebaseDatabase.getInstance().getReference().child("Posts");

        aa = dbRefPost.push().getKey();

        //title
        title = (EditText)findViewById(R.id.recipeTitleID);
        introText = (EditText)findViewById(R.id.introID);

        //INGREDIENTS
        Button ingSaveBtn = (Button)findViewById(R.id.ing_btn_save);
        Button ingSaveClear = (Button)findViewById(R.id.ing_btn_clear);
        ingName = (EditText)findViewById(R.id.ingNameID);
        ingAmnt = (EditText)findViewById(R.id.ingAmntID);
        ingTextName = (TextView)findViewById(R.id.ingTxt);
        ingAmpuntText = (TextView)findViewById(R.id.ingTxtAmnt);
        //UTENSILS
        Button utSaveBtn = (Button)findViewById(R.id.ut_btn_save);
        Button utSaveClear = (Button)findViewById(R.id.ut_btn_clear);
        utName = (EditText)findViewById(R.id.utNameID);
        utAmnt = (EditText)findViewById(R.id.utAmntID);
        utTextName = (TextView)findViewById(R.id.utTxt);
        utAmpuntText = (TextView)findViewById(R.id.utTxtAmnt);
        //STEPS
        Button stpSaveBtn = (Button)findViewById(R.id.stp_btn_save);
        Button stpSaveClear = (Button)findViewById(R.id.stp_btn_clear);
        stpName = (EditText)findViewById(R.id.stpNameID);
        stpTextName = (TextView)findViewById(R.id.stpTxt);
        stpDetail = (EditText)findViewById(R.id.stpDetailID);
        stpTextDetail = (TextView)findViewById(R.id.stpDtl);
        //COMMENTS
         addFinImg = (ImageView)findViewById(R.id.add_cmnt_img);

        postRecipe = (Button)findViewById(R.id.postRecipeBtn);


        ingSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ingName.getText().toString().equals("") && !ingAmnt.getText().toString().equals("")){
                    ingredientsNames+=ingName.getText().toString();
                    ingDispName+=ingName.getText().toString();
                    ingDispName+=",";
                    ingredientsNames+="~";
                    ingredientsAmount+=ingAmnt.getText().toString();
                    ingDispAmnt+=ingAmnt.getText().toString();
                    ingDispAmnt+=",";
                    ingredientsAmount+="~";
                    ingAmnt.setText("");
                    ingName.setText("");
                    ingTextName.setText(ingDispName);
                    ingAmpuntText.setText(ingDispAmnt);
                    Toast.makeText(getApplicationContext(),"Ingredient Saved",Toast.LENGTH_SHORT).show();
//                    openGalleryIng();
                }else{
                    Toast.makeText(getApplicationContext(),"Type",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ingSaveClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientsNames="";
                ingredientsAmount = "";
                ingTextName.setText("");
                ingAmpuntText.setText("");
                ingDispName="";
                ingDispAmnt="";
            }
        });



        utSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!utName.getText().toString().equals("") && !utAmnt.getText().toString().equals("")){
                    utensilsNames+=utName.getText().toString();
                    utDispName+=utName.getText().toString();
                    utDispName+=",";
                    utensilsNames+="~";
                    utensilsAmount+=utAmnt.getText().toString();
                    utDispAmnt+=utAmnt.getText().toString();
                    utDispAmnt+=",";
                    utensilsAmount+="~";
                    utAmnt.setText("");
                    utName.setText("");
                    utTextName.setText(utDispName);
                    utAmpuntText.setText(utDispAmnt);
                    Toast.makeText(getApplicationContext(),"Utensil Saved",Toast.LENGTH_SHORT).show();
//                    openGalleryUt();
                }else{
                    Toast.makeText(getApplicationContext(),"Type",Toast.LENGTH_SHORT).show();
                }
            }
        });
        utSaveClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utensilsNames="";
                utensilsAmount = "";
               utTextName.setText("");
                utAmpuntText.setText("");
                utDispName="";
                utDispAmnt="";
            }
        });

        stpSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!stpName.getText().toString().equals("") && !stpDetail.getText().toString().equals("")){
                    steps+=stpName.getText().toString();
                    stpDispName+=stpName.getText().toString();
                    stpDispName+=",";
                    steps+="~";
                    stepsDetail+=stpDetail.getText().toString();
                    stpDispAmnt+=stpDetail.getText().toString();
                    stpDispAmnt+=",";
                    stepsDetail+="~";
                    stpName.setText("");
                    stpDetail.setText("");
                    stpTextName.setText(stpDispName);
                    stpTextDetail.setText(stpDispAmnt);
                    Toast.makeText(getApplicationContext(),"Step Saved",Toast.LENGTH_SHORT).show();
//                    openGalleryStp();
                }else{
                    Toast.makeText(getApplicationContext(),"Type",Toast.LENGTH_SHORT).show();
                }
            }
        });
        stpSaveClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                steps="";
                stepsDetail = "";
                stpTextName.setText("");
                stpDetail.setText("");
                stpDispName="";
                stpDispAmnt="";
            }
        });


        addFinImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalleryFin();
            }
        });

        postRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Posting... \n Please Wait.. :)");
                progressDialog.show();
                    final StorageReference filePath = postFinPicsRef.child("postFinPics").child(ImageUriFin.getLastPathSegment() + user.getUid());
                    filePath.putFile(ImageUriFin).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                downloadUrlFin = task.getResult().getDownloadUrl().toString();
                                dbRefUser = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

                                dbRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        name = dataSnapshot.child("name").getValue(String.class);
                                        proUrl = dataSnapshot.child("proPic").getValue(String.class);

                                        HashMap<String, Object> userPosts = new HashMap<>();
                                        userPosts.put("Rating", "0");
                                        userPosts.put("isVeg",isVeg);
                                        userPosts.put("UserId", user.getUid());
                                        userPosts.put("Name", name);
                                        userPosts.put("proPic", proUrl);
                                        userPosts.put("Title", title.getText().toString());
                                        userPosts.put("Introduction", introText.getText().toString());
                                        userPosts.put("Time", dtf.format(date));
                                        userPosts.put("Ingredients", ingredientsNames);
                                        userPosts.put("IngredientsAmount", ingredientsAmount);
                                        userPosts.put("Utensils", utensilsNames);
                                        userPosts.put("UtensilsAmount", utensilsAmount);
                                        userPosts.put("StepTitle", steps);
                                        userPosts.put("StepDetail", stepsDetail);
                                        userPosts.put("FinPic", downloadUrlFin);


                                        dbRefPost.child(user.getUid()).child("" + aa).updateChildren(userPosts);
                                        if(isVeg==true)
                                            dbRefPostAll.child("Veg").child("" + aa).updateChildren(userPosts);
                                        else if(isVeg==false)
                                            dbRefPostAll.child("NonVeg").child("" + aa).updateChildren(userPosts);
                                        startActivity(new Intent(getApplicationContext(), PostTabActivity.class));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }
                        }
                    });

            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),PostTabActivity.class));
    }
    private void openGalleryFin() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,Gallery_Pick_Fin);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Gallery_Pick_Fin && resultCode == RESULT_OK && data!=null){
            ImageUriFin = data.getData();
            addFinImg.setImageURI(ImageUriFin);
        }
    }


}
