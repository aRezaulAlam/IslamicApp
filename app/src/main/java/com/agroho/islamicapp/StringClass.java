package com.agroho.islamicapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Rezaul on 2015-09-29.
 */
public class StringClass {

    public static void t(Context context, String message){
        Toast.makeText(context,message+"",Toast.LENGTH_LONG).show();
    }
}
