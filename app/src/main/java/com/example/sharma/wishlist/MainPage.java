package com.example.sharma.wishlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Bundle bundleSendUsername;
    ImageView getProfPic;
    TextView getProfName;
    TextView getProfEmail;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        bundleSendUsername = new Bundle();

        //Nav Header references

        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hview = navigationView.getHeaderView(0);

        getProfPic = (ImageView) hview.findViewById(R.id.iV_profPic);
        getProfEmail = (TextView) hview.findViewById(R.id.tv_Email);
        getProfName = (TextView) hview.findViewById(R.id.tv_profName);




        // Setting profile from Google Data
//        getProfName.setText(sharedPreferences.getString("Name", "new user"));
//        getProfEmail.setText(sharedPreferences.getString("Email", "sign In For More"));
//
//
//        Glide.with(this).load(sharedPreferences.getString("Img_Url", "Profile Pic"))
//                .asBitmap().centerCrop().into(new BitmapImageViewTarget(getProfPic) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                getProfPic.setImageDrawable(circularBitmapDrawable);
//            }
//        });


        // Setting profile from FaceBook Data
        getProfName.setText(SharedPrefFacebook.getInstance(this).getFBinfo().get(1));
        Toast.makeText(this, "Name" + getProfName.getText(), Toast.LENGTH_SHORT).show();
        getProfEmail.setText(SharedPrefFacebook.getInstance(this).getFBinfo().get(2));

        String imgUrl = "https://graph.facebook.com/" +
                SharedPrefFacebook.getInstance(this).getFBinfo().get(0) +
                "/picture?type=large";
        Glide.with(this).load(imgUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(getProfPic) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                getProfPic.setImageDrawable(circularBitmapDrawable);
            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_settings1) {
            return true;
        }
        if (id == R.id.action_settings2) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(MainPage.this, registerUser.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainPage.this, frag_wishlistholder.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(MainPage.this, Profile.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainPage.this, wish_List.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(MainPage.this, FriendList.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
