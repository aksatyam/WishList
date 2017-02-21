package com.example.sharma.wishlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class questions extends AppCompatActivity {
    TextView tx_ques1, tx_ques2, tx_ques3, tx_ques4;
    private EditText edt_description;
    private Button btn;
    RadioGroup rdbtnGroup1, rdbtnGroup2, rdbtnGroup3;
    RadioButton radioButton1, radioButton2, radioButton3;
    /*Bundle bundleSendUsername;
    ImageView getProfPic;
    TextView getProfName;
    TextView getProfEmail;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;*/
    View view;
    private static final String REGISTER_URL = "https://loveneet30.000webhostapp.com/user_answer.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        tx_ques1 = (TextView) findViewById(R.id.ques1);
        tx_ques2 = (TextView) findViewById(R.id.ques2);
        tx_ques3 = (TextView) findViewById(R.id.ques3);
        tx_ques4 = (TextView) findViewById(R.id.ques4);

        rdbtnGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        rdbtnGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        rdbtnGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);

        btn = (Button) findViewById(R.id.btn_submit);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserMain();
            }
        });
        edt_description = (EditText) findViewById(R.id.ed_discription);

    }
    public void registerUserMain() {
        if (invalid()) {
            return;
        }
        else {
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
                                startActivity(new Intent(questions.this, wish_List.class));
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
