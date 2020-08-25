package com.messieyawo.bookapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btRegister;
    private TextView tvLogin;
    private EditText e_email,e_user_password;
    private ProgressBar loading;
    private Button e_login;
    public static final String PREFS = "prefs";
    SharedPreferences prefs;
    private String URL_LOGIN = "http://192.168.8.100/myProjects/sellBook/login.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //sharepreference to save userdetals
        prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);


        sessionManager = new SessionManager(this);
        e_email = findViewById(R.id.user_email);
        loading = findViewById(R.id.loading_login);
        e_login = findViewById(R.id.btn_login);
        e_user_password = findViewById(R.id.user_password);
        btRegister  = findViewById(R.id.btRegister);
        tvLogin     = findViewById(R.id.tvLogin);
        btRegister.setOnClickListener(LoginActivity.this);
        e_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               LoginUser();
            }
        });




    }



    @Override
    protected void onResume() {
        super.onResume();
        //loginRemenber();
        LoginUser();

    }


    private void loginRemenber(){
       String email = prefs.getString("email", null);
       String password = prefs.getString("password", null);

        if (email != null && password !=null ){
            Intent login= new Intent(LoginActivity.this,ProfileActivity.class);
            startActivity(login);
        }else {
            LoginUser();

        }
    }


    private void LoginUser() {

        String email = e_email.getText().toString().trim();
        String password = e_user_password.getText().toString().trim();

        if (!email.isEmpty() || !password.isEmpty()){
            login(email,password);
        }else {
           e_email.setError("Please insert a valid email");
           e_user_password.setError("Please enter your password");
        }
    }

    private void login(final String e_email, final String e_password) {
        loading.setVisibility(View.VISIBLE);
        e_login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")){
                                for (int i=0; i< jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    String id = object.getString("id").trim();

                                    sessionManager.createSession(name,email,id);
//                                    Toast.makeText(LoginActivity.this,"Success login. \n Your name is "+
//                                            name+"\nYour email is "+ email,Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("email",email);
                                    startActivity(intent);

                                    loading.setVisibility(View.GONE);
                                    e_login.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            e_login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this,"Error "+e.toString(),Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        e_login.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this,"Error "+error.toString(),Toast.LENGTH_LONG).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",e_email);
                params.put("password",e_password);


               // SharedPreferences.Editor editor = prefs.edit();
              //  editor.putString("email", e_email);
              //  editor.putString("password", e_password);
               // editor.apply();
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v==btRegister){
            Intent intent   = new Intent(LoginActivity.this,RegisterActivty.class);
            Pair[] pairs    = new Pair[1];
            pairs[0] = new Pair<View,String>(tvLogin,"tvLogin");
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(intent,activityOptions.toBundle());
        }
    }
}
