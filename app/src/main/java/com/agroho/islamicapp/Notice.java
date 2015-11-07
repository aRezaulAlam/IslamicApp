package com.agroho.islamicapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Notice extends AppCompatActivity {
    private ViewPager mPager;


    TextView notificationDetails;
    Button not_ok;
    BroadcastReceiver recieve_chat;
    //String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        notificationDetails = (TextView)findViewById(R.id.notification_info);



        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("message");
        notificationDetails.setText(msg);
        //LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("message_recieved"));
        OKNotification();



}

    private void OKNotification() {

        not_ok = (Button)findViewById(R.id.not_ok);




        not_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               // Fragment fragment = new MostRecentFragment();

               // MainActivity.sendFragment();
                startActivity(new Intent(Notice.this, MainActivity.class));



            }
        });
    }

   /*
    private BroadcastReceiver onNotice= new BroadcastReceiver() {
      @Override
        public void onReceive(Context context, Intent intent) {

            String text = intent.getStringExtra("message");


            notificationDetails.setText(text);




        }
    };
     */




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
