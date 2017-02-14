package com.example.sharma.wishlist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class registerUser extends AppCompatActivity {
    EditText mfullName, muserName, mPassword, mEmail, mcontactNo, mAddress;
    Button btnSignUp;
    TextView tvwLogIn;

    final String REGISTER_URL = "https://loveneet30.000webhostapp.com/Signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mfullName = (EditText) findViewById(R.id.edtFullName);
        muserName = (EditText) findViewById(R.id.edtUserName1);
        mPassword = (EditText) findViewById(R.id.edtPassword);
        mEmail = (EditText) findViewById(R.id.edtEmail);
        mcontactNo = (EditText) findViewById(R.id.edtContactNo);
        mAddress = (EditText) findViewById(R.id.edtAddress);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        tvwLogIn = (TextView) findViewById(R.id.userSignInP);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

        tvwLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerUser.this, userSignIn.class);
                startActivity(intent);

            }
        });
    }

    public void registerNewUser() {
        final String FullName = mfullName.getText().toString().trim();
        final String UserName = muserName.getText().toString().trim();
        final String uPassword = mPassword.getText().toString().trim();
        final String uEmail = mEmail.getText().toString().trim();
        final String uContactNo = mcontactNo.getText().toString().trim();
        final String uAddress = mAddress.getText().toString().trim();
        final ProgressDialog loading = ProgressDialog.show(this, "Please Wait.....", "SignUp.......", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DataBase Response", response);
                        if (response.equals("success")) {
                            loading.dismiss();
                            Toast.makeText(registerUser.this, "User Registration :" + response, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(registerUser.this, userSignIn.class);
                            startActivity(intent);
                        } else {
                            loading.dismiss();
                            Toast.makeText(registerUser.this, "User Registration :" + response, Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(registerUser.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("full_name", FullName);
                params.put("user_name", UserName);
                params.put("password", uPassword);
                params.put("email", uEmail);
                params.put("contact_no", uContactNo);
                params.put("address", uAddress);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
