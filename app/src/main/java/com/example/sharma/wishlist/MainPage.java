package com.example.sharma.wishlist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
/*import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;*/
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.Map;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tx_ques1, tx_ques2, tx_ques3, tx_ques4;
    private EditText edt_description, edtUser, edtMobNo;
    private Button btn;
    RadioGroup rdbtnGroup1, rdbtnGroup2, rdbtnGroup3;
    RadioButton radioButton1, radioButton2, radioButton3;
    Bundle bundleSendUsername;
    ImageView getProfPic;
    TextView getProfName;
    TextView getProfEmail;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    View view;

    private static final String REGISTER_URL = "https://loveneet30.000webhostapp.com/user_answer.php";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        bundleSendUsername = new Bundle();
        tx_ques1 = (TextView) findViewById(R.id.ques1);
        tx_ques2 = (TextView) findViewById(R.id.ques2);
        tx_ques3 = (TextView) findViewById(R.id.ques3);
        tx_ques4 = (TextView) findViewById(R.id.ques4);

        rdbtnGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        rdbtnGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        rdbtnGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);

        //Nav Header references

        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();


        btn = (Button) findViewById(R.id.btn_submit);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserMain();
            }
        });

        edt_description = (EditText) findViewById(R.id.ed_discription);
        edtUser = (EditText) findViewById(R.id.edtUser1);
        edtMobNo = (EditText) findViewById(R.id.edtMobNo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

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
        getProfPic = (ImageView) hview.findViewById(R.id.iV_profPic);

        getProfName.setText(sharedPreferences.getString("Name", "new user"));
        getProfEmail.setText(sharedPreferences.getString("Email", "sign In For More"));


        Glide.with(this).load(sharedPreferences.getString("Img_Url", "Profile Pic")).asBitmap().centerCrop().into(new BitmapImageViewTarget(getProfPic) {
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
            Intent intent = new Intent(MainPage.this, frag_wishListHolder.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(MainPage.this, Profile.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainPage.this, wishlist.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void registerUserMain() {
        if (invalid()) {
            return;
        }
        final String uName;
        final String uMob;
        final String uDesc;

        final String radiobtn1;
        final String radiobtn2;
        final String radiobtn3;

        rdbtnGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        rdbtnGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        rdbtnGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);

        radioButton1 = (RadioButton) findViewById(rdbtnGroup1.getCheckedRadioButtonId());
        radioButton2 = (RadioButton) findViewById(rdbtnGroup2.getCheckedRadioButtonId());
        radioButton3 = (RadioButton) findViewById(rdbtnGroup3.getCheckedRadioButtonId());


        uName = edtUser.getText().toString().trim();
        uMob = edtMobNo.getText().toString().trim();
        uDesc = edt_description.getText().toString().trim();

        radiobtn1 = radioButton1.getText().toString();
        radiobtn2 = radioButton2.getText().toString();
        radiobtn3 = radioButton3.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Database_message", response);

                        if (response.equals("success")) {
                           // Toast.makeText(getApplicationContext(), "Good Successfully inserted", Toast.LENGTH_LONG).show();

                            Snackbar.make(view, "Good Successfully inserted", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            bundleSendUsername.putString("passusername", uName);
                            startActivity(new Intent(MainPage.this,wishlist.class));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(MainPage.this, error.toString(), Toast.LENGTH_LONG).show();
                        Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", uName);
                params.put("mobile_number", uMob);
                params.put("Q1answer", radiobtn1);
                params.put("Q2answer", radiobtn2);
                params.put("Q3answer", radiobtn3);
                params.put("Q4answer", uDesc);
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public boolean invalid() {
        String radiobtn1 = radioButton1.getText().toString();
        String radiobtn2 = radioButton2.getText().toString();
        String radiobtn3 = radioButton3.getText().toString();
        if (radiobtn1.matches("")) {
            radioButton1.setError("Please Select one");
            return true;
        } else {
            radioButton1.setError("");
        }
        if (radiobtn2.matches("")) {
            radioButton2.setError("Please Select one");
            return true;
        } else {
            radioButton2.setError("");
        }
        if (radiobtn3.matches("")) {
            radioButton3.setError("Please Select one");
            return true;
        } else {
            radioButton3.setError("");
        }
        return false;
    }


}
