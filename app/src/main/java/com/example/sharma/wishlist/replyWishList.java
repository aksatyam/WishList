package com.example.sharma.wishlist;


import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class replyWishList extends AppCompatActivity {
    final String REGISTER_URL = "https://loveneet30.000webhostapp.com/UserReply.php";
    EditText et_message;
    Button reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_wishList);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        et_message = (EditText) findViewById(R.id.reply_message);
        reply = (Button) findViewById(R.id.btn_Reply);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void connection() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("shashi_message",response);
                        if (response.equals("success")) {
                            Toast.makeText(getApplicationContext(), "Reply Inserted:" + response, Toast.LENGTH_LONG).show();
                           /* Intent intent = new Intent(getApplicationContext(), display_Wish.xml.class);
                            startActivity(intent);*/

                        } else {
                            Toast.makeText(getApplicationContext(), "Reply Insertion Failed", Toast.LENGTH_LONG).show();

                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("wish_reply", wishReply);
                params.put("wish_reply", et_message.getText().toString());

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menusecond, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
