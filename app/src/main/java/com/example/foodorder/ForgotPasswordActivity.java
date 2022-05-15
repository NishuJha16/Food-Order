package com.example.foodorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            Intent intent=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

    }
    public void forgotPassword(View v) throws JSONException {
        EditText email=findViewById(R.id.email);
        EditText phone=findViewById(R.id.phone);
        final String phones = phone.getText().toString().trim();
        final String emails = email.getText().toString().trim();

        if (TextUtils.isEmpty(emails)) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phones)) {
            phone.setError("Enter a mobile number");
            phone.requestFocus();
            return;
        }
        if(phones.length()!=10)
        {
            phone.setError("Enter a valid mobile number");
            phone.requestFocus();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url="http://13.235.250.119/v2/forgot_password/fetch_result/";
        final JSONObject jsonBody = new JSONObject();
        jsonBody.put("mobile_number", phones);
        jsonBody.put("email", emails);

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
                                Toast.makeText(getApplicationContext(),"Please Check you email for Otp!!", Toast.LENGTH_SHORT).show();


                                boolean first_try=data.getBoolean("first_try");


                                //starting the profile activity
                                Intent intent=new Intent(getApplicationContext(), ResetPasswordActivity.class);
                                intent.putExtra("mobile_number",phones);
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
}