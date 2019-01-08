package com.bigpowerlifting.bigsoftware.bigpowerlifting.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by shanesepac on 6/2/18.
 */

public class Networker {

    private RequestQueue mRequestQueue;
    private Context mContext;
    public Networker(Context context){
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static final String TAG = "Networker";
    //RetryPolicy contains the information such as how long the timeout should be.
    //This is particularly necessary for getting the names for the USAPL autofilltextview
    //since the operation is time consuming.
    public void getPageContent(String address, Response.Listener<String> listener,
                                      Response.ErrorListener errorListener, RetryPolicy policy){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, address,
                listener, errorListener);
        stringRequest.setTag(TAG);
        stringRequest.setRetryPolicy(policy);
        mRequestQueue.add(stringRequest);
    }

    public void cancelAllRequests(){
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
            Log.d(this.TAG, "cancelAllRequests: Cancelled all networking tasks on " + mContext.getPackageName());
        }
    }
}
