package com.example.sharma.wishlist;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class Profile extends AppCompatActivity {
    ImageView profPic;
    TextView profName,profEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profPic=(ImageView)findViewById(R.id.user_profile_photo);
        profName=(TextView)findViewById(R.id.user_profile_name);
        profEmail=(TextView)findViewById(R.id.user_profile_short_bio);


        //final String proName,proEmail,proPic;

        profName.setText(SharedPrefFacebook.getInstance(this).getFBinfo().get(1));
        Toast.makeText(this, "Name", Toast.LENGTH_SHORT).show();
        profEmail.setText(SharedPrefFacebook.getInstance(this).getFBinfo().get(2));

        String imgUrl = "https://graph.facebook.com/" +
                SharedPrefFacebook.getInstance(this).getFBinfo().get(0) +
                "/picture?type=large";
        Glide.with(this).load(imgUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(profPic) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                profPic.setImageDrawable(circularBitmapDrawable);
            }
        });

    }
}
