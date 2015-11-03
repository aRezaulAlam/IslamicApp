package com.agroho.islamicapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNavDrawer extends Fragment implements DataAdapter.ClickListener {

    private RecyclerView recyclerView;

    //private RecyclerView recyclerView;
    public static final String PREF_FILE_NAME = "testpref";
    private static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private View containerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    // private DataAdapter adapter;
    private DataAdapter adapter;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    public FragmentNavDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromSharedPreference(getActivity(), KEY_USER_LEARNED_DRAWER, "true"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_nav_drawer, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new DataAdapter(getActivity(), getData());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;
    }

    public static List<Information> getData() {

        List<Information> data = new ArrayList<>();
        int[] icons = {R.drawable.qnaw, R.drawable.askqwhite, R.drawable.msgboxw, R.drawable.writeqw,R.drawable.info};
        String[] titles = {"প্রশ্ন ও উত্তর", "প্রশ্ন করুন", "আমার প্রশ্ন", "যোগাযোগ","অ্যাপ বিষয়ক তথ্য"};

        for (int i = 0; i < titles.length & i < icons.length; i++) {
            Information current = new Information();
            current.iconId = icons[i];
            current.title = titles[i];
            data.add(current);
        }

        return data;
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {


            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToSharedPreference(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToSharedPreference(Context context, String preferenceName, String preferenceValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromSharedPreference(Context context, String preferenceName, String DefaultValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, DefaultValue);
    }


    @Override
    public void itemClicked(View view, int position) {

        if (position == 0) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            ((MainActivity) getActivity()).onDrawerItemCLicked(position);
        } else if(position == 1){
            startActivity(new Intent(getActivity(), WriteQuestion_Activity.class));
        } else if (position == 2) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            ((MainActivity) getActivity()).onDrawerItemCLicked(1);
        }
        else if (position == 3){
            startActivity(new Intent(getActivity(), WriteQuestion_Activity.class));
        }
        else if (position == 4){
            startActivity(new Intent(getActivity(), About.class));
        }
/*
        if (position==1){
            startActivity(new Intent(getActivity(), WriteQuestion_Activity.class));
        } else{
            Toast.makeText(getActivity(), "Item Clicked on" + position, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), MainActivity.class));
}
        //startActivity(new Intent(getActivity(), WriteQuestion_Activity.class));


    */


    }

}
