package com.example.foodorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.android.volley.Request.*;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

}

    public void register2(View v) throws JSONException {
        EditText name=findViewById(R.id.user);
        EditText phone=findViewById(R.id.phone);
        EditText password=findViewById(R.id.password);
        EditText password2=findViewById(R.id.confirmPassword);
        EditText address=findViewById(R.id.address);
        EditText email=findViewById(R.id.email);
        final String phones = phone.getText().toString().trim();
        final String emails = email.getText().toString().trim();
        final String passwords = password.getText().toString().trim();
        final String passwords2 = password2.getText().toString().trim();
        final String addresses = address.getText().toString().trim();
        final String names = name.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(names)) {
            name.setError("Please enter name");
            name.requestFocus();
            return;
        }

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

        if (TextUtils.isEmpty(passwords)) {
            password.setError("Enter a password");
            password.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(addresses)) {
            address.setError("Enter a delivery location");
            address.requestFocus();
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
  if(passwords.equals(passwords2)) {
     RequestQueue queue = Volley.newRequestQueue(this);
      final String url="http://13.235.250.119/v2/register/fetch_result/";
      final JSONObject jsonBody = new JSONObject();
      jsonBody.put("name", names);
      jsonBody.put("mobile_number", phones);
      jsonBody.put("password", passwords);
      jsonBody.put("address", address);
      jsonBody.put("email", emails);


         JsonObjectRequest stringRequest = new JsonObjectRequest(Method.POST, url,jsonBody,
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
                            Toast.makeText(getApplicationContext(),"registered successfully", Toast.LENGTH_SHORT).show();

                            //getting the user from the response
                            JSONObject userJson = data.getJSONObject("data");

                            //creating a new user object
                            User user = new User(
                                    userJson.getString("user_id"),
                                    userJson.getString("name"),
                                    userJson.getString("email"),
                                    userJson.getString("mobile_number"),
                                    addresses
                            );

                            //storing the user in shared preferences
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                            //starting the profile activity

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
else
{
    password2.setError("Both Passwords should match");
    password2.requestFocus();
    return;
}
    }


    public void loginPage(View v)
    {
        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}