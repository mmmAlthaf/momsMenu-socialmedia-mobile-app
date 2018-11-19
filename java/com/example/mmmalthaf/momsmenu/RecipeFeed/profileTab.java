package com.example.mmmalthaf.momsmenu.RecipeFeed;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mmmalthaf.momsmenu.LoginActivityFB;
import com.example.mmmalthaf.momsmenu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by M.M.M Althaf on 4/3/2018.
 */

public class profileTab extends Fragment{
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference dbUserRef;
    TextView name,age,numPosts,ratings;
    ImageView proPic;
    Button lgtBtn;
    private FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        lgtBtn = (Button)rootView.findViewById(R.id.btnlg);
        name = (TextView)rootView.findViewById(R.id.nameID);
        age = (TextView)rootView.findViewById(R.id.ageID);
        proPic = (ImageView) rootView.findViewById(R.id.profile_image);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        dbUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        dbUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue(String.class));
                age.setText(dataSnapshot.child("age").getValue(Integer.class)+"");
                dataSnapshot.child("proPic").getValue(String.class);
                Glide.with(getActivity()).load(dataSnapshot.child("proPic").getValue(String.class)).into(proPic);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Toast.makeText(getActivity(),"Logging out...",Toast.LENGTH_SHORT).show();
                getActivity().finish();
                startActivity(new Intent(getActivity(),LoginActivityFB.class));
            }
        });


        return rootView;

    }

}
