package com.example.mmmalthaf.momsmenu.RecipeFeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mmmalthaf.momsmenu.R;
import com.example.mmmalthaf.momsmenu.AddRecipe.AddPost;
import com.example.mmmalthaf.momsmenu.ViewRecipe.RecipeDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class recipeFeedTabAll extends Fragment {


    ListView lv;
    Boolean isVeggg=false;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference dbRefPost,dbUserRef,dbRefPostAll;
    ImageView proPic;
//    String proUrl,chefName;
    PostsArrayAdaptor adp;
    public static Boolean isVEG = false;
    public static ArrayList<String> recipeNamee = new ArrayList();
    public static ArrayList<String> introText = new ArrayList();
    //public static ArrayList<String> proUrl = new ArrayList();
    public static ArrayList<String> chefName = new ArrayList();
    public static ArrayList<String> ingredients = new ArrayList();
    public static ArrayList<String> ingredientsAmnt = new ArrayList();
    public static ArrayList<String> utensils = new ArrayList();
    public static ArrayList<String> utensilsAmnt = new ArrayList();
    public static ArrayList<String> steps = new ArrayList();
    public static ArrayList<String> stepsDetail = new ArrayList();
    //public static ArrayList<String> dispPic = new ArrayList();
    public static ArrayList<String> rating = new ArrayList<>();
    public static ArrayList<String> postID = new ArrayList();
    public static ArrayList<String> postUserID = new ArrayList();
    public static Integer [] proUrl={R.drawable.heart,R.drawable.samosa,R.drawable.heart,R.drawable.samosa};
    public static Integer [] dispPic={R.drawable.chefvirender,R.drawable.ndtv,R.drawable.chefvirender,R.drawable.ndtv};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_recipesfeed_view, container, false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        dbUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        dbRefPostAll = FirebaseDatabase.getInstance().getReference().child("PostsAll");
        lv=(ListView) rootView.findViewById(R.id.list_Ing);




        isVeggg = ((PostTabActivity)getActivity()).isVegg;
        Log.e("veggggg", String.valueOf(isVeggg));
        if(isVeggg==true) {
            dbRefPostAll.child("Veg").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> list = dataSnapshot.getChildren();
                    recipeNamee.clear();
                    ingredients.clear();
                    postID.clear();
                    postUserID.clear();
                    rating.clear();
                    introText.clear();
                    ingredientsAmnt.clear();
                    utensilsAmnt.clear();
                    utensils.clear();
                    stepsDetail.clear();
                    steps.clear();
                    //dispPic.clear();
                    chefName.clear();
                    //proUrl.clear();
                    for (DataSnapshot d : list) {
                        postID.add(d.getKey());
                        isVEG = (d.child("isVeg").getValue(Boolean.class));
                        postUserID.add(d.child("UserId").getValue(String.class));
                        recipeNamee.add(d.child("Title").getValue(String.class));
                        rating.add(d.child("Rating").getValue(String.class));
                        introText.add(d.child("Introduction").getValue(String.class));
                        ingredients.add(d.child("Ingredients").getValue(String.class));
                        ingredientsAmnt.add(d.child("IngredientsAmount").getValue(String.class));
                        utensils.add(d.child("Utensils").getValue(String.class));
                        utensilsAmnt.add(d.child("UtensilsAmount").getValue(String.class));
                        steps.add(d.child("StepTitle").getValue(String.class));
                        stepsDetail.add(d.child("StepDetail").getValue(String.class));
                        //dispPic.add(d.child("FinPic").getValue(String.class));
                        chefName.add(d.child("Name").getValue(String.class));
                        //proUrl.add(d.child("proPic").getValue(String.class));

                        if (isAdded()) {
                            adp = new PostsArrayAdaptor(getActivity(), recipeNamee, chefName, proUrl, dispPic, rating);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lv.setAdapter(adp);
                                }
                            });
                        }
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else if(isVeggg==false){
            dbRefPostAll.child("NonVeg").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> list = dataSnapshot.getChildren();
                    recipeNamee.clear();
                    ingredients.clear();
                    postID.clear();
                    postUserID.clear();
                    rating.clear();
                    introText.clear();
                    ingredientsAmnt.clear();
                    utensilsAmnt.clear();
                    utensils.clear();
                    stepsDetail.clear();
                    steps.clear();
                    //dispPic.clear();
                    chefName.clear();
                    //proUrl.clear();
                    for (DataSnapshot d : list) {
                        postID.add(d.getKey());
                        postUserID.add(d.child("UserId").getValue(String.class));
                        recipeNamee.add(d.child("Title").getValue(String.class));
                        rating.add(d.child("Rating").getValue(String.class));
                        isVEG = (d.child("isVeg").getValue(Boolean.class));
                        introText.add(d.child("Introduction").getValue(String.class));
                        ingredients.add(d.child("Ingredients").getValue(String.class));
                        ingredientsAmnt.add(d.child("IngredientsAmount").getValue(String.class));
                        utensils.add(d.child("Utensils").getValue(String.class));
                        utensilsAmnt.add(d.child("UtensilsAmount").getValue(String.class));
                        steps.add(d.child("StepTitle").getValue(String.class));
                        stepsDetail.add(d.child("StepDetail").getValue(String.class));
                        //dispPic.add(d.child("FinPic").getValue(String.class));
                        chefName.add(d.child("Name").getValue(String.class));
                        //proUrl.add(d.child("proPic").getValue(String.class));

                        if (isAdded()) {
                            adp = new PostsArrayAdaptor(getActivity(), recipeNamee, chefName, proUrl, dispPic, rating);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lv.setAdapter(adp);
                                }
                            });
                        }
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //do stuff
                Intent intent=new Intent(getActivity(),RecipeDetailActivity.class);
                intent.putExtra("CfName",chefName.get(position));
                intent.putExtra("VEGCHECK",isVEG);
                //intent.putExtra("proPic",proUrl.get(position));
                intent.putExtra("proPic",proUrl[position]);
                intent.putExtra("postUserId",postUserID.get(position));
                intent.putExtra("ID",postID.get(position));
                intent.putExtra("Rating",rating.get(position));
                intent.putExtra("Title",recipeNamee.get(position));
                intent.putExtra("Introduction",introText.get(position));
                intent.putExtra("Ings",ingredients.get(position));
                intent.putExtra("IngsAmnt",ingredientsAmnt.get(position));
                intent.putExtra("Uts",utensils.get(position));
                intent.putExtra("UtsAmnt",utensilsAmnt.get(position));
                intent.putExtra("Steps",steps.get(position));
                intent.putExtra("StepsDtl",stepsDetail.get(position));
                //intent.putExtra("IntroPic",dispPic.get(position));
                intent.putExtra("IntroPic",dispPic[position]);
                getActivity().finish();
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getActivity(),"Long Clicked "+i,Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        return rootView;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
