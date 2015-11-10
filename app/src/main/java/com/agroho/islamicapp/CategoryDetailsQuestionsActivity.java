package com.agroho.islamicapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryDetailsQuestionsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    TextView textViewQAQuestion, textViewQAAnswer;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    public RecyclerView CategoryDetails;
    private QAAdapter adapterCD;
    private ProgressDialog progressDialog;


    private ArrayList<QAInfo> listQA = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details_questions);

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        volleySingleton = VolleySingleton.getsInstance();
        requestQueue=volleySingleton.getRequestQueue();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("বিষয়ভিত্তিক প্রশ্ন লোড হচ্ছে...");
        progressDialog.show();


        CategoryDetails = (RecyclerView)findViewById(R.id.category_detail_id_recycler);
        CategoryDetails.setLayoutManager(new LinearLayoutManager(this));
        adapterCD = new QAAdapter(this);
        CategoryDetails.addItemDecoration(new SimpleDividerItemDecoration(this));
        CategoryDetails.setAdapter(adapterCD);
        sendJsonRequest();

    }


    private void sendJsonRequest(){
        String id = "0";
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            id = (String) b.get("CAT_ID");

        }

        if (id==null){
            id = "5";
        }

        final String URL = "http://api.agroho.com/islam/json/category_details.php?cat_id="+id;
        Log.d("URL", URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                listQA =  parseJsonResponse(response);
                adapterCD.setQAList(listQA);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyErrorNotice(error);
                progressDialog.dismiss();


            }
        });

        requestQueue.add(req);



    }

    private void VolleyErrorNotice(VolleyError error) {

        if (error instanceof TimeoutError || error instanceof NoConnectionError){
            startActivity(new Intent(this, InternetDisconnected.class));


            Toast.makeText(this, "No Internet Connection Please Connect to internet", Toast.LENGTH_SHORT).show();

        } else if (error instanceof AuthFailureError){

            Toast.makeText(this, "Authentication Error Please Report" + error.getMessage(), Toast.LENGTH_SHORT).show();


        } else if(error instanceof ServerError){

            Toast.makeText(this, "Server Error please contact Admin" + error.getMessage(), Toast.LENGTH_SHORT).show();


        } else if(error instanceof NetworkError){

            Toast.makeText(this, "Network Error Please Try Again" + error.getMessage(), Toast.LENGTH_SHORT).show();


        } else if (error instanceof ParseError) {
            Toast.makeText(this, "Parse Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<QAInfo> parseJsonResponse(JSONArray response) {
        ArrayList<QAInfo> listQA = new ArrayList<>();
        if (response == null || response.length()==0){

        }

        //StringBuilder sb = new StringBuilder();
        for (int i=0; i<response.length();i++){
            try {
                JSONObject obj = response.getJSONObject(i);
                String QAId = obj.getString("qa_id");
                String QATitle = obj.getString("qa_title");
                //String QAQuestion = obj.getString("qa_question");
                //String QAAnswer = obj.getString("qa_answer");
                String QACategory = obj.getString("qa_category");
                QAInfo qaObj = new QAInfo();

                qaObj.setId(QAId);
                qaObj.setqaTitle(QATitle);
                //qaObj.setCategory(QAQuestion);
                //qaObj.setDetailsUrl(QAAnswer);
                qaObj.setCategory(QACategory);

                //listQAInfo.add(qaObj);
                listQA.add(qaObj);

                //sb.append(UniName+"\n");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //StringClass.t(getActivity(),listQA.toString());
        return listQA;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category_details_questions, menu);
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
}
