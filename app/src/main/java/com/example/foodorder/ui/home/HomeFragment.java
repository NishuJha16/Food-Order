package com.example.foodorder.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodorder.R;
import com.example.foodorder.RestaurantMenuList;
import com.example.foodorder.Restaurants;
import com.example.foodorder.ui.FavouriteDatabase;
import com.example.foodorder.ui.FavouriteEntity;
import com.example.foodorder.ui.RestaurantAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener{

    private HomeViewModel homeViewModel;
    RecyclerView.Adapter mAdapter;
    RecyclerView listView;
    Context mContext;
    RelativeLayout progressLayout;
    SearchView editsearch;
    RecyclerView.LayoutManager layoutManager;
    List<Restaurants> filterDataList;
    ArrayList<Restaurants> arrayList=new ArrayList();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        progressLayout = root.findViewById(R.id.progressLayout);
        ProgressBar progressBar = root.findViewById(R.id.progressBar);
        progressLayout.setVisibility(View.VISIBLE);
        listView=root.findViewById(R.id.listView);
        editsearch = (SearchView) root.findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);


        return root;
    }
     public void fetchRestaurant()
     {

         listView.setHasFixedSize(true);
         layoutManager=new LinearLayoutManager(getContext());
         RequestQueue queue = Volley.newRequestQueue(getContext());
         final String url="http://13.235.250.119/v2/restaurants/fetch_result/";
         final JSONObject jsonBody = new JSONObject();



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
                                 JSONArray restaurants=data.getJSONArray("data");
                                 for(int i=0;i<restaurants.length();i++)
                                 {
                                     JSONObject individual=restaurants.getJSONObject(i);
                                     Restaurants RestaurantData= new Restaurants(
                                             individual.getString("id"),
                                             individual.getString("name"),
                                             individual.getString("rating"),
                                             individual.getString("cost_for_one"),
                                             individual.getString("image_url")
                                     );
                                     arrayList.add(RestaurantData);

                                     if(getView()!=null && getView().isShown()) {
                                         mAdapter = new RestaurantAdapter(getActivity(), arrayList);
                                         listView.setLayoutManager(layoutManager);
                                         listView.setAdapter(mAdapter);
                                     }

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
        fetchRestaurant();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterDataList=filter(arrayList,newText);
        mAdapter=new RestaurantAdapter(getActivity(),filterDataList);
        listView.setAdapter(mAdapter);
        listView.invalidate();
        return true;
    }
    private List<Restaurants> filter(List<Restaurants> arrayList,String newText)
    {
        newText=newText.toLowerCase();
        String text;
        filterDataList=new ArrayList<>();
        for(Restaurants dataFromDataList:arrayList)
        {
            text=dataFromDataList.getName().toLowerCase();
            if(text.contains(newText))
            {
                filterDataList.add(dataFromDataList);
            }
        }
        return filterDataList;
    }

}

