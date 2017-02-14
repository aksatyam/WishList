package com.example.sharma.wishlist;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class wish_list extends Fragment {
    EditText edtWish;

    public wish_list() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);

        final String REGISTER_URL = "https://loveneet30.000webhostapp.com/postWish.php";


        Bundle bundle = this.getArguments();
        final String myValue = bundle.getString("passusername");

        TextView showText = (TextView) view.findViewById(R.id.text1);
        showText.setText(myValue);
        edtWish = (EditText) view.findViewById(R.id.edtMakeWish);
        final LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_makewish);
        layout.setVisibility(View.INVISIBLE);

        final LinearLayout linear1 = (LinearLayout) view.findViewById(R.id.layout_Clear);
        linear1.setVisibility(View.VISIBLE);

        Button btnMake = (Button) view.findViewById(R.id.btn_MakeWish);
        btnMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
                linear1.setVisibility(View.GONE);

            }
        });

        Button btnBack = (Button) view.findViewById(R.id.btnBack);
        Button btnSave = (Button) view.findViewById(R.id.btnSaveDATA);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setVisibility(View.VISIBLE);
                layout.setVisibility(View.INVISIBLE);
                edtWish.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inValid()) {
                    edtWish.setError("wish filed can not be empty.");
                } else {


                    final String wishData = edtWish.getText().toString().trim();
                   // final ProgressDialog dialog = ProgressDialog.show(getActivity().getApplicationContext(), "Please Wait.....", "Wish sending....", false, false);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")) {
                                       // dialog.dismiss();
                                        Toast.makeText(getActivity().getApplicationContext(), "Wishlist Inserted:" + response, Toast.LENGTH_LONG).show();

                                    } else {
                                       // dialog.dismiss();
                                        Toast.makeText(getActivity().getApplicationContext(), "Wishlist Insertion Failed", Toast.LENGTH_LONG).show();

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
                            params.put("username", myValue);
                            params.put("wish", wishData);
                            return params;
                        }

                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    requestQueue.add(stringRequest);

                }


            }
        });

        return view;
    }

    public boolean inValid() {
        String getData = edtWish.getText().toString();
        if (getData.isEmpty()) {
            return true;
        }
        return false;
    }

}
