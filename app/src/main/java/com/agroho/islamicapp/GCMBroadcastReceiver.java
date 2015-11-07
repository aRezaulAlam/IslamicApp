package com.agroho.islamicapp;

/**
 * Created by Rezaul on 2015-11-03.
 */

        import android.app.Activity;
        import android.content.BroadcastReceiver;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.content.WakefulBroadcastReceiver;
        import android.util.Log;

        import java.util.Iterator;
        import java.util.Set;


public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
                GCMIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}


