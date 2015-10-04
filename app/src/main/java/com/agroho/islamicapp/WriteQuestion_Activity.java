package com.agroho.islamicapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
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
import java.util.ArrayList;
import java.util.List;
import android.preference.PreferenceManager;



public class WriteQuestion_Activity extends AppCompatActivity {
    private Toolbar toolbar;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private ProgressDialog pDialog;
    EditText input1;
    EditText input2;
    String userName;
    String Contact;
    String name;
    String con;
    private static String JsonUrl = "http://api.agroho.com/islam/islamicapp/create_user.php";


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

            Toast.makeText(getApplicationContext(), "SHaredPref without Dialog: " + name + " " + con, Toast.LENGTH_LONG).show();
        }

    }


    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View promptView = inflater.inflate(R.layout.dialog_signin, null);
        builder.setView(promptView);
        input1 = (EditText)promptView.findViewById(R.id.username);
        input2 = (EditText)promptView.findViewById(R.id.contact);
                // Add action buttons
        builder.setPositiveButton("Signin", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                if (input1.getText() != null && input2.getText() != null) {

                    // result.setText(userInput.getText());
                    userName = input1.getText().toString();
                    Contact = input2.getText().toString();
                    hello();
                    insertToDatabase(userName, Contact);



                    preferenceSettings =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    //preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
                        preferenceEditor = preferenceSettings.edit();

                        preferenceEditor.putString("username", userName);
                        preferenceEditor.putString("contact", Contact);
                        preferenceEditor.commit();

                    preferenceSettings =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    name = preferenceSettings.getString("username", userName);
                    con = preferenceSettings.getString("contact", Contact);


                    Toast.makeText(getApplicationContext(), "SHaredPref: "+ name +" "+ con, Toast.LENGTH_LONG).show();


                }

            }
        })
                .setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void insertToDatabase( final String userName, final String contact) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramUsername = params[0];
                String paramAddress = params[1];



                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", userName));
                nameValuePairs.add(new BasicNameValuePair("contact", contact));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://api.agroho.com/islam/islamicapp/create_user.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                //TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                //textViewResult.setText("Inserted");
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(userName, contact);
    }

    private void hello() {

        Toast.makeText(this,  userName+" "+Contact , Toast.LENGTH_SHORT).show();
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

        return super.onOptionsItemSelected(item);
    }


    /*
    private void insertToDatabase(String name, String add){

    }
     */


}
