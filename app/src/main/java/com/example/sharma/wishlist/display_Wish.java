package com.example.sharma.wishlist;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class display_Wish extends Fragment {
    final String Wish_URL = "https://loveneet30.000webhostapp.com/wishlist.php";
    ListView var_listview;
    public static final String TAG_NAME = "wish_description";
    private ArrayList<String> list;
    ArrayAdapter adapter;
    Bundle bundle;


    public display_Wish() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.display_Wish, container, false);
      bundle = new Bundle();
       var_listview = (ListView)view.findViewById(R.id.wish_list);
        list = new ArrayList<>();
        function1();

        return view;
    }

    private void function1() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching data...", false, false);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //Adding our request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    private void listview(JSONArray response) {

        //Looping through all the elements of json array
        for (int i = 0; i < response.length(); i++) {
            JSONObject obj = null;
            //Creating a json object of the current index
            try {
                //getting json object from current index
                obj = response.getJSONObject(i);
                Log.d("Vishal message", "" + obj);
                //getting image url and title from json object
                list.add(obj.getString(TAG_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
        var_listview.setAdapter(adapter);

        var_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = list.get(position);
                bundle.putString("wish",data);
                /*Fragment ff=new Reply();
                FragmentManager fragmentManager=getFragmentManager();
                ff.setArguments(bundle);
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frag_wishListHolder,ff);
                fragmentTransaction.commit();*/
                Intent i = new Intent(getActivity(),replyWishList.class);
                i.putExtra("message", data);
                startActivity(i);
            }
        });
    }

}
