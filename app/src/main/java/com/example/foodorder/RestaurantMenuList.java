package com.example.foodorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.foodorder.ui.RestaurantAdapter;
import com.example.foodorder.ui.RestaurantMenuAdapter;
import com.example.foodorder.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.example.foodorder.ui.RestaurantMenuAdapter.a;

public class RestaurantMenuList extends AppCompatActivity {
    RecyclerView.Adapter mAdapter;
    RecyclerView listView;
    TextView restname;
    Button shareBtn;
    Button addCart;
    Button location;
    String resId;
    TextView offers;
     String res_name;
    String res_address;
    String res_offers;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addCart=findViewById(R.id.addCart);
        location=findViewById(R.id.location);
        offers=findViewById(R.id.offers);
        shareBtn=findViewById(R.id.share);
        final RelativeLayout progressLayout = findViewById(R.id.progressLayout);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressLayout.setVisibility(View.VISIBLE);
        listView=findViewById(R.id.listView);
        listView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        restname=findViewById(R.id.restname);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        resId=getIntent().getStringExtra("res_id");
        res_name=getIntent().getStringExtra("name");
        res_address=getIntent().getStringExtra("address");
        res_offers=getIntent().getStringExtra("offers");
        restname.setText(res_name);
        final String url="http://13.235.250.119/v2/restaurants/fetch_result/"+resId+"/";
        final JSONObject jsonBody = new JSONObject();
        final ArrayList<RestaurantMenuItems> arrayList=new ArrayList();


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null,
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
                                //Toast.makeText(getApplicationContext(),"Hurray!!", Toast.LENGTH_SHORT).show();
                                offers.setText(res_offers);
                                location.setText(res_address);

                                progressLayout.setVisibility(View.GONE);
                                location.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Uri mapUri=Uri.parse("geo:0,0?q="+Uri.encode(res_address));
                                        Intent mapIntent=new Intent(Intent.ACTION_VIEW,mapUri);
                                        mapIntent.setPackage("com.google.android.apps.maps");
                                        startActivity(mapIntent);
                                    }
                                });

                                shareBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent();
                                        intent.setAction(Intent.ACTION_SEND);
                                        intent.setType("text/plain");
                                        intent.putExtra(Intent.EXTRA_TEXT,"playstore.foodorder.com/download/");
                                        startActivity(Intent.createChooser(intent,"Share"));
                                    }
                                });

                                Toast.makeText(getApplicationContext(),res_address+" "+res_offers, Toast.LENGTH_SHORT).show();
                                JSONArray restaurants=data.getJSONArray("data");
                                for(int i=0;i<restaurants.length();i++)
                                {
                                    JSONObject individual=restaurants.getJSONObject(i);
                                    RestaurantMenuItems RestaurantData= new RestaurantMenuItems(
                                            individual.getString("id"),
                                            individual.getString("name"),
                                            individual.getString("cost_for_one"),
                                            individual.getString("restaurant_id"),
                                            Integer.toString(i+1)
                                    );
                                    arrayList.add(RestaurantData);
                                    mAdapter=new RestaurantMenuAdapter(RestaurantMenuList.this,arrayList);
                                    listView.setAdapter(mAdapter);
                                    listView.setLayoutManager(layoutManager);
                                }
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

            addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(RestaurantMenuList.this,CartActivity.class);
                    intent.putExtra("res_id",resId);
                    intent.putExtra("res_name",res_name);
                    startActivity(intent);
                    finish();
                }
            });

    }
    public void updateCart()
    {
        if(a>0)
        {
            addCart.setVisibility(View.VISIBLE);
           // Toast.makeText(getApplicationContext(), "Visible", Toast.LENGTH_SHORT).show();
        }
        else{
            addCart.setVisibility(View.GONE);
           // Toast.makeText(getApplicationContext(), "InVisible", Toast.LENGTH_SHORT).show();

        }
    }
    public void allrest(View v)
    {
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }


}