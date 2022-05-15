package com.example.foodorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.ResolutionAnchor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

    }
    public void signUp(View v)
    {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    public void forgotPwd(View v)
    {
        Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }
    public void userLogin(View v) {
        EditText phone=findViewById(R.id.phone);
        EditText password=findViewById(R.id.password);
        //first getting the values
        final String phones = phone.getText().toString().trim();
        final String passwords = password.getText().toString().trim();

        //validating inputs
        if (TextUtils.isEmpty(phones)) {
            phone.setError("Please enter your mobile number");
            phone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passwords)) {
            password.setError("Please enter your password");
            password.requestFocus();
            return;
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url="http://13.235.250.119/v2/login/fetch_result/";
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("mobile_number",phones);
            jsonBody.put("password", passwords);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //if everything is fine
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,jsonBody,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("VOLLEY", String.valueOf(response));

                        try {
                            //converting response to json object
                            JSONObject data=response.getJSONObject("data");
                            boolean success=data.getBoolean("success");
                            //if no error in response
                            if (success) {
                                Toast.makeText(getApplicationContext(),"logged in successfully", Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = data.getJSONObject("data");

                                //creating a new user object
                                User user = new User(
                                        userJson.getString("user_id"),
                                        userJson.getString("name"),
                                        userJson.getString("email"),
                                        userJson.getString("mobile_number"),
                                        userJson.getString("address")
                                );

                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),data.getString("errorMessage"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            public Map getHeaders() {
                HashMap headers = new HashMap();
                ((Map) headers).put("Content-type", "application/json");
                ((Map) headers).put("token", "9bf534118365f1");
                return (Map) headers;
            }
        };
        queue.add(stringRequest);

    }

}