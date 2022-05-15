package com.example.foodorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodorder.ui.CartDatabase;
import com.example.foodorder.ui.CartEntity;
import com.example.foodorder.ui.FavouriteDatabase;
import com.example.foodorder.ui.FavouriteEntity;
import com.example.foodorder.ui.RestaurantAdapter;
import com.example.foodorder.ui.favourite.FavouriteAdapter;
import com.example.foodorder.ui.favourite.favouriteFragment;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class CartActivity extends AppCompatActivity {
    RecyclerView.Adapter mAdapter;
    RecyclerView listView;
    TextView restname;
    Button proceedCart;
    RecyclerView.LayoutManager layoutManager;
    String res_id;
    String user_id;
    JSONArray food_id;
    JSONObject mainObject;
    String res_name;
    RelativeLayout progressLayout;
    RelativeLayout successLayout;
    List<CartEntity> dbCart;
    int s=0;

    public static List<String> foodId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        res_id=getIntent().getStringExtra("res_id");
        user_id=this.getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keyid",null);
        res_name=getIntent().getStringExtra("res_name");
        restname=findViewById(R.id.restName);
        restname.setText("Ordering From:  "+" "+res_name);
        proceedCart=findViewById(R.id.proceedCart);
        progressLayout = findViewById(R.id.progressLayout);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressLayout.setVisibility(View.VISIBLE);
        listView=findViewById(R.id.listView);
        listView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        successLayout=findViewById(R.id.success);


        try {
            dbCart= new RetriveCart(this).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (getApplicationContext() != null) {
            progressLayout.setVisibility(View.GONE);
            mAdapter = new CartAdapter(this, dbCart);
            for(int i=0;i<dbCart.size();i++)
            {
                s+=Integer.parseInt(dbCart.get(i).getCost_for_one());
            }
            listView.setAdapter(mAdapter);
            listView.setLayoutManager(layoutManager);
        }

        //getting all food id's
        DBAsyncTask async = (DBAsyncTask) new DBAsyncTask(this).execute();
        Boolean result = null;
        try {
            result = async.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (result){
            mainObject=new JSONObject();
            food_id=new JSONArray();
            try {
            for(String id:foodId)
            {
                JSONObject idsJsonObject=new JSONObject();

                    idsJsonObject.put("food_id",id);
                    food_id.put(idsJsonObject);
            }
             mainObject.put("food",food_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(this, "Some error occurred!", Toast.LENGTH_SHORT).show();
        }

        final String totalCost=Integer.toString(total());

        proceedCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(CartActivity.this);
                final String url="http://13.235.250.119/v2/place_order/fetch_result/";
                final JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("user_id",user_id);
                    jsonBody.put("restaurant_id", res_id);
                    jsonBody.put("total_cost",totalCost);
                    jsonBody.put("food",food_id);
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
                                        Toast.makeText(getApplicationContext(),"Order Placed successfully", Toast.LENGTH_SHORT).show();
                                        successLayout.setVisibility(View.VISIBLE);
                                        proceedCart.setVisibility(View.INVISIBLE);

                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                                                CartActivity.this.startActivity(intent);
                                                CartActivity.this.finish();
                                            }
                                        },5000);
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
    private static class DBAsyncTask extends AsyncTask<Void, Void, Boolean> {
        Context context;

        CartDatabase db;

        public DBAsyncTask(Context context) {
            this.context = context;
            db = Room.databaseBuilder(context, CartDatabase.class, "cart-db").build();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            foodId = db.cartDao().getAllFoodId();
            db.close();
            return true;
        }
    }
    public int total()
    {
        proceedCart.setText("Place Order(Total Rs."+Integer.toString(s)+")");
       return s;

    }

    public static class RetriveCart extends AsyncTask<Void,Void,List<CartEntity>>{
        Context context;
        CartDatabase db;
        public RetriveCart(Context context)
        {
            this.context=context;
        }

        @Override
        protected List<CartEntity> doInBackground(Void... voids) {
            db = Room.databaseBuilder(context, CartDatabase.class, "cart-db").build();
            return db.cartDao().getAllCart();
        }
    }
}