package com.agroho.islamicapp;


import android.app.ProgressDialog;
import android.os.Bundle;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    public RecyclerView Category;
    private CategoryAdapter adapterCategory;
    private ProgressDialog progressDialog;

    private ArrayList<CategoryPOJO> listCategory = new ArrayList<>();



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoriesFragment() {
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
        progressDialog.setMessage("Loading Categories...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        sendJsonRequest();
    }

    private void sendJsonRequest(){

        final String URL = "http://api.agroho.com/islam/json/category.php";
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();

                listCategory =  parseJsonResponse(response);
                adapterCategory.setCategoryList(listCategory);

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

    private ArrayList<CategoryPOJO> parseJsonResponse(JSONArray response) {
        ArrayList<CategoryPOJO> listCategory = new ArrayList<>();
        if (response == null || response.length()==0){

        }

        //StringBuilder sb = new StringBuilder();
        for (int i=0; i<response.length();i++){
            try {
                JSONObject obj = response.getJSONObject(i);
                String CatId = obj.getString("cat_id");
                String CatTitle = obj.getString("cat_name");
                //String QAQuestion = obj.getString("qa_question");
                //String QAAnswer = obj.getString("qa_answer");
                //String QACategory = obj.getString("qa_category");
                CategoryPOJO catObj = new CategoryPOJO();

                catObj.setCategoryId(CatId);
                catObj.setGetCategoryName(CatTitle);
                //qaObj.setCategory(QAQuestion);
                //qaObj.setDetailsUrl(QAAnswer);
                //qaObj.setCategory(QACategory);

                //listQAInfo.add(qaObj);
                listCategory.add(catObj);

                //sb.append(UniName+"\n");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //StringClass.t(getActivity(),listQA.toString());
        return listCategory;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_categories, container, false);

        Category = (RecyclerView) view.findViewById(R.id.category_fragment_id_recycler);
        Category.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterCategory = new CategoryAdapter(getActivity());
        Category.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        Category.setAdapter(adapterCategory);
        sendJsonRequest();
        return view;
    }


}
