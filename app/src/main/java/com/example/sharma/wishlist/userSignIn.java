package com.example.sharma.wishlist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class userSignIn extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private EditText edtUsernameL, edtPasswordL;
    private Button btnSignInD;
    private LoginButton btnLogInF;
    private SignInButton btnSignInG;
    private UserDetail userDetail;
    TextView redirectSignup;
    Bundle bundle;
    AccessTokenTracker accessTokenTracker;

    private static final String Login_URL = "https://loveneet30.000webhostapp.com/login.php";

    CallbackManager callbackManager;

    //Content Display
    private LinearLayout Prof_section;
    private Button SignOut;
    private TextView Name, Email;
    private ImageView Prof_pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_user_sign_in);

        bundle=new Bundle();

        userDetail=new UserDetail();

        edtUsernameL = (EditText) findViewById(R.id.edtUserName1);
        edtPasswordL = (EditText) findViewById(R.id.edtPassword1);

        redirectSignup = (TextView) findViewById(R.id.redSignUp);

        btnSignInD = (Button) findViewById(R.id.btnSignIn);
        btnLogInF = (LoginButton) findViewById(R.id.btnFB_SignIn);
        btnSignInG = (SignInButton) findViewById(R.id.btnGoogle_SignIn);
        SignOut = (Button) findViewById(R.id.btn_logout);


        Prof_section = (LinearLayout) findViewById(R.id.prof_section);
        Name = (TextView) findViewById(R.id.name);
        Email = (TextView) findViewById(R.id.email);
        Prof_pic = (ImageView) findViewById(R.id.prof_pic);

        btnSignInD.setOnClickListener(this);
        btnLogInF.setOnClickListener(this);
        btnSignInG.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        redirectSignup.setOnClickListener(this);

        Prof_section.setVisibility(View.GONE);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                callWS(v);
                break;
            case R.id.btnFB_SignIn:
                callFB();
                break;
            case R.id.btnGoogle_SignIn:
                callGoogle();
                break;
            case R.id.btn_logout:
                signOut();
            case R.id.redSignUp:
                Intent inte = new Intent(userSignIn.this, registerUser.class);
                startActivity(inte);
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void callWS(final View view) {
        if(inValid()){
            return;
        }else {
            final String Username = edtUsernameL.getText().toString();
            final String password = edtPasswordL.getText().toString();
            final ProgressDialog loading = ProgressDialog.show(this, "Please Wait....", "loggin In....", false, false);
            StringRequest request = new StringRequest(Request.Method.POST, Login_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Vishal", "response" + response);
                    if (response.equals("success")) {
                        loading.dismiss();
                        Snackbar.make(getCurrentFocus(), "Login successfull", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        bundle.putString("uName",Username);
                        userDetail.putName(bundle);
                        Intent i = new Intent(userSignIn.this, MainPage.class);
                        startActivity(i);
                    } else {
                        loading.dismiss();
                        Snackbar.make(view, "Fail to Login!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            ) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("username", Username);
                    map.put("password", password);
                    return map;
                }

            };
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);

        }
    }
    private void callFB() {
        callbackManager = CallbackManager.Factory.create();
        Toast.makeText(userSignIn.this, "Facebook1", Toast.LENGTH_SHORT).show();
        btnLogInF.setReadPermissions(Arrays.asList("public_profile, email, user_friends,read_custom_friendlists"));

        btnLogInF.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(userSignIn.this, "Facebook Login Success", Toast.LENGTH_SHORT).show();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String uId= object.getString("id");
                            String uName= object.getString("name");
                            String uEmail= object.getString("email");

                            ArrayList fId= new ArrayList();
                            ArrayList fName= new ArrayList();
                            Toast.makeText(userSignIn.this, uName, Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject=object.getJSONObject("friends");
                            JSONArray jsonArray= jsonObject.getJSONArray("data");
                             for (int i=0; i<jsonArray.length();i++)
                             {
                                 JSONObject object1=jsonArray.getJSONObject(i);
                                 fId.add(object1.getString("id"));
                                 fName.add(object1.getString("name"));
                             }
                            SharedPrefFacebook.getInstance(userSignIn.this).saveFBInfo(uId,uName,uEmail);
                            SharedPrefFacebook.getInstance(userSignIn.this).saveFacebookData(fId.toString(),fName.toString());
                            startActivity(new Intent(userSignIn.this,MainPage.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle bundle=new Bundle();
                bundle.putString("fields","id,name,email,friends");
                request.setParameters(bundle);
                request.executeAsync();
                //Access Token To Manage Logout
                accessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                        if (currentAccessToken == null) {
                            SharedPrefFacebook.getInstance(userSignIn.this).deleteFB();
                        }
                    }
                };



            }

            @Override
            public void onCancel() {
                Toast.makeText(userSignIn.this, "Facebook Login Cancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(userSignIn.this, "Facebook Login Error", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void callGoogle() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
    }

    private void signOut() {

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    private void handleRequest(GoogleSignInResult result) {
        Toast.makeText(this, "Google1", Toast.LENGTH_SHORT).show();
        if (result.isSuccess()) {
            Toast.makeText(this, "Welcome To Homepage", Toast.LENGTH_SHORT).show();
            GoogleSignInAccount signInAccount = result.getSignInAccount();
            String name = signInAccount.getDisplayName();
            String email = signInAccount.getEmail();
            String img_url = signInAccount.getPhotoUrl().toString();

            SharedPreferences sharedPreferences=getSharedPreferences("UserProfile",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("Name",name);
            editor.putString("Email",email);
            editor.putString("Img_Url",img_url);
            editor.apply();
            Toast.makeText(this, "Home Page", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this,MainPage.class));
        }
        else {

        }

    }

    private void updateUI(boolean isLogin) {
        if (isLogin) {
            Prof_section.setVisibility(View.VISIBLE);
            btnSignInG.setVisibility(View.GONE);
        } else {
            Prof_section.setVisibility(View.GONE);
            btnSignInG.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleRequest(result);
        }
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    public boolean inValid() {
        String getusername = edtUsernameL.getText().toString();
        String getpassword = edtPasswordL.getText().toString();
        if (getusername.isEmpty()) {
            edtUsernameL.setError("enter username");
            return true;
        }
        if (getpassword.isEmpty()) {
            edtPasswordL.setError("enter password");
            return true;
        }
        return false;
    }


}
