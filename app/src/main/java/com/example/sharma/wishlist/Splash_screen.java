package com.example.sharma.wishlist;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash_screen extends AppCompatActivity {
    private static int splashInterval = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO Auto-generated methods stub
                Intent i = new Intent(Splash_screen.this, userSignIn.class);
                startActivity(i);
                this.finish();

            }

            private void finish() {
                //TODO Auto-generated methods stub

            }
        }, splashInterval);

    }
}

