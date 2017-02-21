package com.example.sharma.wishlist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class wish_List extends AppCompatActivity {
    EditText edtWish;
    Bundle bundle;
    Button btnSave, btnMake,btnWishlist;
    String myValue;
    private UserDetail userDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish__list);

        final String REGISTER_URL = "https://loveneet30.000webhostapp.com/postWish.php";

        btnWishlist=(Button)findViewById(R.id.btnViewList);

        userDetail=new UserDetail();
        bundle=userDetail.getName();
        myValue=bundle.getString("uName");

        TextView showText = (TextView) findViewById(R.id.textViewUser);
        showText.setText(myValue);

        edtWish = (EditText) findViewById(R.id.edtMakeWish);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.layout_makewish);
        layout.setVisibility(View.INVISIBLE);

        final LinearLayout linear1 = (LinearLayout) findViewById(R.id.layout_Clear);
        linear1.setVisibility(View.VISIBLE);

        btnMake = (Button) findViewById(R.id.btn_MakeWish);
        btnMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
                linear1.setVisibility(View.GONE);

            }
        });
        btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(wish_List.this,frag_wishlistholder.class));
            }
        });

        btnSave = (Button) findViewById(R.id.btnSaveDATA);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inValid()) {
                    return;
                } else {
                    final String wishData = edtWish.getText().toString().trim();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                Toast.makeText(getApplicationContext(), "Wishlist Inserted:" + response, Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "Wishlist Insertion Failed", Toast.LENGTH_LONG).show();

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
                            params.put("username", myValue);
                            params.put("wish", wishData);
                            return params;
                        }

                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            }
        });

    }

    public boolean inValid() {
        String getData = edtWish.getText().toString();
        if (getData.isEmpty()) {
            edtWish.setError("enter the wish");
            return true;
        }
        return false;
    }

}
