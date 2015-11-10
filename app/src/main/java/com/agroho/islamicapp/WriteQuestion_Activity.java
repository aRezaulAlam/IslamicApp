package com.agroho.islamicapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;



public class WriteQuestion_Activity extends AppCompatActivity {

    // Resgistration Id from GCM
    private static final String PREF_GCM_REG_ID = "PREF_GCM_REG_ID";
    private SharedPreferences prefs;
    // Your project number and web server url.
    private static final String GCM_SENDER_ID = "173650430784";
    //private static final String WEB_SERVER_URL = "http://api.agroho.com/GCM/register.php";
    private String gcmRegId;
    GoogleCloudMessaging gcm;
    private Toolbar toolbar;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private ProgressDialog pDialog;
    EditText input1;
    EditText input2;
    EditText FullNameInput;
    EditText QuestionInput;
    Button sendQuestion;
    String userName;
    String Contact;
    String name;
    String con;
    String FullName;
    String Question;
    String user_name;

    private static final int ACTION_PLAY_SERVICES_DIALOG = 100;
    protected static final int MSG_REGISTER_WITH_GCM = 101;
    protected static final int MSG_REGISTER_WEB_SERVER = 102;
    protected static final int MSG_REGISTER_WEB_SERVER_SUCCESS = 103;
    protected static final int MSG_REGISTER_WEB_SERVER_FAILURE = 104;

    //private static String JsonUrl = "http://api.agroho.com/islam/islamicapp/create_user.php";
    public static final String SharedDataRegister = "Registration Data";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_question_);
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //SharedPreferences.Editor editor = preferences.edit();
        //editor.putString("Name","Harneet");
        //editor.apply();
        preferenceSettings =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        name = preferenceSettings.getString("username", userName);
        con = preferenceSettings.getString("contact", Contact);

        if (name==null && con==null) {

            showDialog();

        } else {

            //Toast.makeText(getApplicationContext(), "SHaredPref without Dialog: " + name + " " + con, Toast.LENGTH_LONG).show();
        }




        processQuestion();

    }


    private boolean isGoogelPlayInstalled() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        ACTION_PLAY_SERVICES_DIALOG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Google Play Service is not installed",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;

    }

    private SharedPreferences getSharedPreferences() {
        if (prefs == null) {
            prefs = getApplicationContext().getSharedPreferences(
                    "AndroidSRCDemo", Context.MODE_PRIVATE);
        }
        return prefs;
    }

    public void saveInSharedPref(String result) {
        // TODO Auto-generated method stub
        Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_GCM_REG_ID, result);
        editor.commit();
    }


    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_REGISTER_WITH_GCM:
                    new GCMRegistrationTask().execute();
                    break;
                case MSG_REGISTER_WEB_SERVER:
                    new WebServerRegistrationTask().execute();

                    break;
                case MSG_REGISTER_WEB_SERVER_SUCCESS:
                    Toast.makeText(getApplicationContext(),
                            "registered with web server", Toast.LENGTH_LONG).show();
                    break;
                case MSG_REGISTER_WEB_SERVER_FAILURE:
                    Toast.makeText(getApplicationContext(),
                            "registration with web server failed",
                            Toast.LENGTH_LONG).show();
                    break;
            }
        };
    };


    private class WebServerRegistrationTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            SharedPreferences prefs = getSharedPreferences(SharedDataRegister, MODE_PRIVATE);
            String restoredText = prefs.getString("text", null);

            preferenceSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            name = preferenceSettings.getString("username", userName);
            con = preferenceSettings.getString("contact", Contact);


            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username", name));
            nameValuePairs.add(new BasicNameValuePair("contact", con));
            nameValuePairs.add(new BasicNameValuePair("reg_id", gcmRegId));

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(
                        "http://api.agroho.com/IslamicApp/push/register.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();


            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(),userName+" "+contact, Toast.LENGTH_LONG).show();


            Toast.makeText(getApplicationContext(), "আপনার একাউন্ট তৈরি  হয়েছে ", Toast.LENGTH_SHORT).show();
            //TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
            //textViewResult.setText("Inserted");
        }
    }

    private class GCMRegistrationTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            if (gcm == null && isGoogelPlayInstalled()) {
                gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            }
            try {
                gcmRegId = gcm.register(GCM_SENDER_ID);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return gcmRegId;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(getApplicationContext(), "অ্যাকাউন্ট সেটআপ হয়েছে",
                        Toast.LENGTH_LONG).show();
               // regIdView.setText(result);
                saveInSharedPref(result);
                handler.sendEmptyMessage(MSG_REGISTER_WEB_SERVER);
            }
        }

    }


    private void processQuestion() {

        FullNameInput = (EditText)findViewById(R.id.name);
        QuestionInput = (EditText)findViewById(R.id.question);
        sendQuestion = (Button)findViewById(R.id.btnSend);

        sendQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             clickForSendMessage();


            }
        });
    }


    private void clickForSendMessage() {

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        Boolean isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent == true) {

            if ((FullNameInput.getText().toString().trim().length() > 2) && QuestionInput.getText().toString().trim().length() > 10) {

                FullName = FullNameInput.getText().toString();
                Question = QuestionInput.getText().toString();


                sendQuestionToDatabase(FullName, Question);
                startActivity(new Intent(WriteQuestion_Activity.this, MainActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "সঠিকভাবে প্রশ্ন করা  হয়নি। নুন্যতম ৩ অক্ষরের নাম ও ১১ অক্ষরের প্রশ্ন করতে হবে।  আবার চেষ্টা করুন", Toast.LENGTH_LONG).show();
                processQuestion();
            }

        } else {
            Toast.makeText(getApplicationContext(), "ইন্টারনেট কানেকশন নেই। আপনার মোবাইলটি ইন্টারনেটের সাথে সংযুক্ত করুন", Toast.LENGTH_LONG).show();
            processQuestion();
        }
    }
    public void sendQuestionToDatabase(final String fullName, final String question) {

        class SendPostQuestionAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {




                    String paramUsername = params[0];
                    String paramAddress = params[1];
                    String paramwas = params[2];

                    preferenceSettings =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    user_name = preferenceSettings.getString("contact", Contact);





                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("username", user_name));
                    nameValuePairs.add(new BasicNameValuePair("fullname", fullName));
                    nameValuePairs.add(new BasicNameValuePair("qa_question", question));




                    try {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(
                                "http://api.agroho.com/islam/islamicapp/ask_question.php");


                        UrlEncodedFormEntity urlEncodedFormEntity = null;
                        try {
                            urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }


                        httpPost.setEntity(urlEncodedFormEntity);

                        HttpResponse response = httpClient.execute(httpPost);

                        HttpEntity entity = response.getEntity();


                    } catch (ClientProtocolException e) {

                    } catch (IOException e) {

                    }
                    return "success";






            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(getApplicationContext(), "আপনার প্রশ্নটি পাঠানো সম্পন্ন হয়েছে।", Toast.LENGTH_SHORT).show();

                //TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                //textViewResult.setText("Inserted");
            }
        }
        SendPostQuestionAsyncTask sendPostReqAsyncTask = new SendPostQuestionAsyncTask();
        sendPostReqAsyncTask.execute(user_name, fullName, question);
    }


    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                View promptView = inflater.inflate(R.layout.dialog_signin, null);
                builder.setCancelable(false);
                builder.setView(promptView);
                input1 = (EditText)promptView.findViewById(R.id.username);
                input2 = (EditText)promptView.findViewById(R.id.contact);
                // Add action buttons
                builder.setPositiveButton("রেজিস্ট্রেশন করুন", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

                Boolean isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent == true) {

                    if (input1.getText() != null && input2.getText() != null) {

                        // result.setText(userInput.getText());
                        if ((input1.getText().toString().trim().length() > 3) && input2.getText().toString().trim().length() > 5) {
                            userName = input1.getText().toString();
                            Contact = input2.getText().toString();

                            preferenceSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                            //preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
                            preferenceEditor = preferenceSettings.edit();

                            preferenceEditor.putString("username", userName);
                            preferenceEditor.putString("contact", Contact);
                            preferenceEditor.commit();

                            hello();

                            // Check device for Play Services APK.
                            if (isGoogelPlayInstalled()) {
                                gcm = GoogleCloudMessaging.getInstance(getApplicationContext());

                                // Read saved registration id from shared preferences.
                                gcmRegId = getSharedPreferences().getString(PREF_GCM_REG_ID, "");

                                if (TextUtils.isEmpty(gcmRegId)) {
                                    handler.sendEmptyMessage(MSG_REGISTER_WITH_GCM);
                                }else{
                                    //regIdView.setText(gcmRegId);
                                    Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_SHORT).show();
                                }
                            }





                            preferenceSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                            name = preferenceSettings.getString("username", userName);
                            con = preferenceSettings.getString("contact", Contact);

                        } else {
                            Toast.makeText(getApplicationContext(), "সঠিকভাবে রেজিষ্ট্রেশন হয়নি। আবার চেষ্টা করুন", Toast.LENGTH_LONG).show();
                            showDialog();
                        }

                    }



                }

                else {

                    Toast.makeText(getApplicationContext(), "ইন্টারনেট কানেকশন নেই। আপনার মোবাইলটি ইন্টারনেটের সাথে সংযুক্ত করুন", Toast.LENGTH_LONG).show();
                    showDialog();
                }


            }
            }

            )
                    .

            setNegativeButton("বাতিল", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int Button) {
                            startActivity(new Intent(WriteQuestion_Activity.this, MainActivity.class));
                            dialog.cancel();
                        }
                    }

            );
            builder.create().

            show();
        }


    private void hello() {

        //Toast.makeText(this,  userName+" "+Contact , Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write_question_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        if (id == R.id.send){
            clickForSendMessage();
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    private void insertToDatabase(String name, String add){

    }
     */


}
