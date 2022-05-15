package com.example.foodorder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class ConnectionManager {

    static Boolean checkConnectivity(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork= connectivityManager.getActiveNetworkInfo();

        if (activeNetwork!= null){
            return activeNetwork.isConnected();
        } else {
            return false;
        }
    }
}
