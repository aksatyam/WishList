package com.example.sharma.wishlist;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Reply extends Fragment {


    public Reply() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reply, container, false);

        final String REGISTER_URL = "https://loveneet30.000webhostapp.com/UserReply.php";
        final EditText reply_message = (EditText) view.findViewById(R.id.reply_text2);

        Button reply = (Button) view.findViewById(R.id.btn_Reply);


        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String wishReply = reply_message.getText().toString().trim();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Reply Inserted:" + response, Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(getActivity().getApplicationContext(),MainPage.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Reply Insertion Failed", Toast.LENGTH_LONG).show();

                                }
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("wish_reply", wishReply);
                        return params;
                    }

                };
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(stringRequest);

            }


        });
    return view;
}

}
