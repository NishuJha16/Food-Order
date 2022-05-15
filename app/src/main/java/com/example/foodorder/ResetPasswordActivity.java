package com.example.foodorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Button submit=findViewById(R.id.resetBtn);
        final EditText password1=findViewById(R.id.password);
        final EditText otp=findViewById(R.id.otp);
        final EditText password2=findViewById(R.id.confirmPassword);
        Bundle bundle=getIntent().getExtras();
        final String phones=bundle.getString("mobile_number");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String otps = otp.getText().toString().trim();
                final String passwords1 = password1.getText().toString().trim();
                final String passwords2 = password2.getText().toString().trim();

                if (TextUtils.isEmpty(otps)) {
                    otp.setError("Please enter your otp received in email");
                    otp.requestFocus();
                    return;
                }


                if (TextUtils.isEmpty(passwords1)) {
                    password1.setError("Enter a valid password");
                    password1.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(passwords2)) {
                    password2.setError("Enter the password again");
                    password2.requestFocus();
                    return;
                }
                if(!passwords1.equals(passwords2))
                {
                    password2.setError("Both passwords should match");
                    password2.requestFocus();
                    return;
                }
                Toast.makeText(getApplicationContext(),phones+" "+otps+" "+passwords1, Toast.LENGTH_SHORT).show();
                RequestQueue queue = Volley.newRequestQueue(ResetPasswordActivity.this);
                final String url="http://13.235.250.119/v2/reset_password/fetch_result/";
                final JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("mobile_number", phones);
                    jsonBody.put("password", passwords1);
                    jsonBody.put("otp", otps);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                                        Toast.makeText(getApplicationContext(),data.getString("successMessage"), Toast.LENGTH_SHORT).show();


                                        //starting the profile activity
                                        Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                                        SharedPreferences sharedPreferences = getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.clear();
                                        editor.commit();
                                        startActivity(intent);
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
        });

    }


}