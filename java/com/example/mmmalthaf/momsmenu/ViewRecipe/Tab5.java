package com.example.mmmalthaf.momsmenu.ViewRecipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.content.SharedPreferences;

import com.bumptech.glide.Glide;
import com.example.mmmalthaf.momsmenu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class Tab5 extends Fragment {



    String chefName= "",proUrl = "";


    ListView lv;
    ImageView heart,proPic;
    TextView likeTxt,UsrNamee;
    FirebaseUser user;
    FirebaseAuth auth;
    int check=0;
    EditText cmntEt;
    frndComentArrayAdaptor adp;
    int rating = 0,r=0;
    boolean liked = false;
    Button sayBtn;
    public static ArrayList<String> userNamee = new ArrayList();
    public static ArrayList<String> userProPic = new ArrayList();
    public static ArrayList<String> userComment = new ArrayList();

    DatabaseReference dbRefPost,dbRefPostAll,dbRefPostOwn,dbRefPostt,dbUserRef,dbCmntUserRef,dbRefPostAlll,dbRefPostOwnn;
    public static String [] name ={"Virender","Samosa Chaat","Virender","Samosa Chaat"};
    public static String [] comment ={"Wow its soo tasty. I need to meet you some day :D","Wow its soo tasty. I need to meet you some day :D Chaat","Wow its soo tasty. I need to meet you some day :D","Wow its soo tasty. I need to meet you some day :Dt"};

    public static Integer [] propic={R.drawable.chefvirender,R.drawable.ndtv,R.drawable.chefvirender,R.drawable.ndtv};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab6, container, false);
        lv=(ListView) rootView.findViewById(R.id.list_fr);

        SharedPreferences sharedPreferencess = getActivity().getSharedPreferences("liked?",MODE_PRIVATE);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user.getUid().equals(sharedPreferencess.getString("userID","notSameUser"))){
            if(((RecipeDetailActivity)getActivity()).ID.equals(sharedPreferencess.getString("PostID","notSameUser"))){
                liked =  sharedPreferencess.getBoolean("liked?",false);
            }

        }else{
            liked =  false;
        }



        dbUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        dbRefPost = FirebaseDatabase.getInstance().getReference().child("PostsAll").child(((RecipeDetailActivity)getActivity()).vg)
                .child(((RecipeDetailActivity)getActivity()).ID)
                .child("Rating");
        dbRefPostt = FirebaseDatabase.getInstance().getReference().child("PostsAll").child(((RecipeDetailActivity)getActivity()).vg)
                .child(((RecipeDetailActivity)getActivity()).ID);

        dbRefPostt.child("Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> list = dataSnapshot.getChildren();
                userComment.clear();
                userProPic.clear();
                userNamee.clear();
                for(DataSnapshot d:list){
                    userComment.add(d.child("Comment").getValue(String.class));
                    dbCmntUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(d.child("userID").getValue(String.class));
                    dbCmntUserRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            userNamee.add(dataSnapshot.child("name").getValue(String.class));
                            userProPic.add(dataSnapshot.child("proPic").getValue(String.class));
                            if(isAdded()){
                                adp = new frndComentArrayAdaptor(getActivity(), userNamee, userComment,userProPic);

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lv.setAdapter(adp);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chefName = dataSnapshot.child("name").getValue(String.class);
                proUrl = dataSnapshot.child("proPic").getValue(String.class);
                Glide.with(getContext()).load(proUrl).into(proPic);
                UsrNamee.setText(chefName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        heart = (ImageView)rootView.findViewById(R.id.like);

        if(liked==false)
            heart.setImageResource(R.drawable.newunliked);
        else if(liked == true)
            heart.setImageResource(R.drawable.newliked);

        cmntEt = (EditText)rootView.findViewById(R.id.editTextCmnt);
        sayBtn = (Button)rootView.findViewById(R.id.sayID);
        proPic = (ImageView)rootView.findViewById(R.id.profile_image);
        likeTxt = (TextView)rootView.findViewById(R.id.likeTxt);
        UsrNamee = (TextView)rootView.findViewById(R.id.namee);

        likeTxt.setText(((RecipeDetailActivity)getActivity()).rating);



        sayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat dtf = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                dbRefPostAll = FirebaseDatabase.getInstance().getReference().child("PostsAll").child(((RecipeDetailActivity)getActivity()).vg)
                        .child(((RecipeDetailActivity)getActivity()).ID).child("Comments").child("Comments:"+dtf.format(date));
                dbRefPostOwn = FirebaseDatabase.getInstance().getReference().child("Posts").child(((RecipeDetailActivity)getActivity()).postUserID)
                        .child(((RecipeDetailActivity)getActivity()).ID).child("Comments").child("Comments:"+dtf.format(date));
                HashMap<String,Object> userPosts = new HashMap<>();
                userPosts.put("Comment",cmntEt.getText().toString().trim());
                userPosts.put("userID",user.getUid());
                dbRefPostAll.updateChildren(userPosts);
                dbRefPostOwn.updateChildren(userPosts);
                cmntEt.setText("");
            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(liked==false) {
                    updateRating(liked);
                    heart.setImageResource(R.drawable.newliked);
                    liked = true;
                }
                else if(liked==true){
                    updateRating(liked);
                    heart.setImageResource(R.drawable.newunliked);
                    liked=false;
                }



            }
        });


        return rootView;

    }

    private void updateRating(final boolean l) {
        dbRefPost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rating =  Integer.parseInt(dataSnapshot.getValue(String.class));
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("liked?", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("liked?",liked);
                editor.putString("PostID",((RecipeDetailActivity)getActivity()).ID);
                editor.putString("userID",user.getUid());
                editor.apply();
                if(l == false) {
                    r = rating + 1;
                }
                else if(l == true) {
                    r = rating - 1;
                }

                HashMap<String,Object> userPosts = new HashMap<>();
                userPosts.put("Rating",r+"");
                dbRefPostt.updateChildren(userPosts);

                likeTxt.setText(r+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}



