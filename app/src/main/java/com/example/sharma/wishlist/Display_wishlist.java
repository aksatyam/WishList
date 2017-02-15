package com.example.sharma.wishlist;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Display_wishlist extends AppCompatActivity {
    final String Wish_URL = "https://loveneet30.000webhostapp.com/wishlist.php";
    ListView var_listview;
    Button btnClose;
    ArrayAdapter adapter;
    public static final String TAG_NAME = "wish_description";
    private ArrayList<String> wish_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_wishlist);
        btnClose = (Button) findViewById(R.id.app_close);
        var_listview = (ListView) findViewById(R.id.listViewWish);
        wish_description = new ArrayList<>();
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...", "Fetching data...", false, false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Wish_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing the progressdialog on response
                        loading.dismiss();

                        //Displaying our grid
                        listview(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding our request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    private void listview(JSONArray jsonArray) {
        //Looping through all the elements of json array
        for (int i = 0; i < jsonArray.length(); i++) {
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);
                Log.d("Vishal message", "" + obj);
                //getting image url and title from json object
                wish_description.add(obj.getString(TAG_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /**
         * To attach  wish in listview
         * But we have to create own CustomGrid java class
         */
        adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, wish_description);

        /**
         * setting adapter in listview
         */

        var_listview.setAdapter(adapter);
        var_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment ff=new Reply();
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.activity_display_wishlist,ff);
                fragmentTransaction.commit();
                btnClose.setVisibility(View.INVISIBLE);
            }
        });
    }
}

