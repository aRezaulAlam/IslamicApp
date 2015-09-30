package com.agroho.islamicapp;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by rezaul on 9/19/15.
 */
public class VolleySingleton{

    private RequestQueue mRequestQueue;

    private static VolleySingleton sInstance=null;
    private VolleySingleton(){
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public static VolleySingleton getsInstance(){
        if (sInstance==null){
            sInstance=new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

}
