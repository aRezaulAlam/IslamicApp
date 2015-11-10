package com.agroho.islamicapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import android.content.SharedPreferences;
import java.util.ArrayList;
import android.preference.PreferenceManager;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MostRecentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MostRecentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    public RecyclerView MostRecent;
    private QAAdapter adapterQA;
    private ProgressDialog progressDialog;
    public SharedPreferences preferenceSettings;

    String userName;
    String Contact;



    private ArrayList<QAInfo> listQA = new ArrayList<>();


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MostRecentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MostRecentFragment newInstance(String param1, String param2) {
        MostRecentFragment fragment = new MostRecentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MostRecentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getsInstance();
        requestQueue=volleySingleton.getRequestQueue();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("প্রশ্ন ও উত্তর লোড হচ্ছে...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);


        sendJsonRequest();

    }

    private void sendJsonRequest(){

        preferenceSettings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String name = preferenceSettings.getString("username", userName);
        String con = preferenceSettings.getString("contact", Contact);

        //Toast.makeText(getActivity(), name +" "+ con, Toast.LENGTH_SHORT).show();



        if (name==null && con==null) {
            con = "0";
            name = "0";
        }

        final String URL = "http://api.agroho.com/islam/json/qa_recent.php?username="+con+"&pin="+name;

       //Toast.makeText(getActivity(), URL, Toast.LENGTH_SHORT).show();


        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();

                listQA =  parseJsonResponse(response);

                adapterQA.setQAList(listQA);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                VolleyErrorNotice(error);

            }
        });

        requestQueue.add(req);



    }

    private void VolleyErrorNotice(VolleyError error) {

        if (error instanceof TimeoutError || error instanceof NoConnectionError){

            startActivity(new Intent(getActivity(), InternetDisconnected.class));

            Toast.makeText(getActivity(), "No Internet Connection Please Connect to internet", Toast.LENGTH_SHORT).show();

        } else if (error instanceof AuthFailureError){

            Toast.makeText(getActivity(), "Authentication Error Please Report" + error.getMessage(), Toast.LENGTH_SHORT).show();


        } else if(error instanceof ServerError){

            Toast.makeText(getActivity(), "Server Error please contact Admin" + error.getMessage(), Toast.LENGTH_SHORT).show();


        } else if(error instanceof NetworkError){

            Toast.makeText(getActivity(), "Network Error Please Try Again" + error.getMessage(), Toast.LENGTH_SHORT).show();


        } else if (error instanceof ParseError) {
            Toast.makeText(getActivity(), "Parse Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<QAInfo> parseJsonResponse(JSONArray response) {
        ArrayList<QAInfo> listQA = new ArrayList<>();
        if (response == null || response.length()==0){
            Toast.makeText(getActivity(), "Nothing Found", Toast.LENGTH_SHORT).show();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_most_recent, container, false);
        MostRecent = (RecyclerView) view.findViewById(R.id.recent_fragment_id_recycler);
        MostRecent.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterQA = new QAAdapter(getActivity());
        MostRecent.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        MostRecent.setAdapter(adapterQA);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Write here anything that you wish to do on click of FAB
                // Code to Add an item with default animation
                startActivity(new Intent(getActivity(), WriteQuestion_Activity.class));
            }
        });
        sendJsonRequest();
        return view;
    }




}