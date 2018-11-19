package com.example.mmmalthaf.momsmenu.RecipeFeed;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mmmalthaf.momsmenu.AddRecipe.AddPost;
import com.example.mmmalthaf.momsmenu.LoginActivityFB;
import com.example.mmmalthaf.momsmenu.R;
import com.example.mmmalthaf.momsmenu.WelcomeSplashActivity;
import com.google.firebase.auth.FirebaseAuth;

public class PostTabActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    Boolean isVegg=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        firebaseAuth=FirebaseAuth.getInstance();

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null) {
            isVegg=(Boolean)b.get("veg?");
        }else{
            isVegg=false;
        }
        //setting tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layoutt);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText("Recipe Feed"));
        tabLayout.addTab(tabLayout.newTab().setText("My Recipes"));
        tabLayout.addTab(tabLayout.newTab().setText("My Profile"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pagerr);
        final MainAdaptor a = new MainAdaptor(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(a);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                firebaseAuth.signOut();
                Toast.makeText(getApplicationContext(),"Logging out...",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(),LoginActivityFB.class));
                return true;
            case R.id.action_veg:
                finish();
                startActivity(new Intent(getApplicationContext(),vegOrNonVeg.class));
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    public void newPost(View view) {
        finish();
        startActivity(new Intent(getApplicationContext(), AddPost.class));
    }

}
