package com.example.foodorder.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodorder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class historyFragment extends Fragment {

    private historyViewModel historyViewModel;
    RecyclerView.Adapter restAdapter;
    RecyclerView listView1;
    Context mContext;
    RelativeLayout progressLayout;
    RecyclerView.LayoutManager layoutManager1;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(historyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        progressLayout = root.findViewById(R.id.progressLayout);
        ProgressBar progressBar = root.findViewById(R.id.progressBar);
        progressLayout.setVisibility(View.VISIBLE);
        listView1=root.findViewById(R.id.listView1);
        return root;
    }
    public void fetchOrder()
    {
        listView1.setHasFixedSize(true);
        layoutManager1=new LinearLayoutManager(getContext());
        String userId=getContext().getSharedPreferences("simplifiedcodingsharedpref", Context.MODE_PRIVATE).getString("keyid",null);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final String url="http://13.235.250.119/v2/orders/fetch_result/"+userId+"/";
        final JSONObject jsonBody = new JSONObject();
        final ArrayList<HistoryRestaurantName> restName=new ArrayList();


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
                                Toast.makeText(getActivity(),"Hurray!!", Toast.LENGTH_SHORT).show();
                                progressLayout.setVisibility(View.GONE);
                                JSONArray orders=data.getJSONArray("data");
                                for(int i=0;i<orders.length();i++)
                                {
                                    JSONObject individual=orders.getJSONObject(i);
                                    HistoryRestaurantName historyRestaurantName= new HistoryRestaurantName(
                                            individual.getString("order_id"),
                                            individual.getString("restaurant_name"),
                                            individual.getString("total_cost"),
                                            individual.getString("order_placed_at")
                                    );
                                    restName.add(historyRestaurantName);
                                    JSONArray foods=individual.getJSONArray("food_items");
                                    final ArrayList<ArrayList<HistoryFood>> foodName= new ArrayList<>();
                                    for(int j=0;j<foods.length();j++)
                                    {
                                        JSONObject foodIndividual=foods.getJSONObject(j);
                                        HistoryFood historyFood=new HistoryFood(
                                                foodIndividual.getString("food_item_id"),
                                                foodIndividual.getString("name"),
                                                foodIndividual.getString("cost")
                                        );
                                        ArrayList<HistoryFood> foodindi=new ArrayList<>();
                                        foodindi.add(historyFood);
                                        foodName.add(foodindi);
                                    }
                                    restAdapter= new HistoryRestaurantNameAdapter(getActivity(), restName,foodName);
                                    listView1.setAdapter(restAdapter);
                                    listView1.setLayoutManager(layoutManager1);
                                }


                            } else {
                                Toast.makeText(getContext(),data.getString("errorMessage"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchOrder();
    }
}