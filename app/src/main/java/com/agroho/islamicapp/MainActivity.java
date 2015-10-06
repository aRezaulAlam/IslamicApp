package com.agroho.islamicapp;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    public static final int MOST_DISCUSSED = 0;
    public static final int MOST_RECENT = 1;
    public static final int CATEGORIES = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentNavDrawer drawerFragment = (FragmentNavDrawer)
                getSupportFragmentManager().findFragmentById(R.id.navdrawer);
        drawerFragment.setup(R.id.navdrawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout)findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabView(R.layout.custom_tab_view,R.id.tabtext);
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        mTabs.setViewPager(mPager);
       // mTabs.setViewPager();
    }

    void  onDrawerItemCLicked(int position){
        mPager.setCurrentItem(position);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if (id == R.id.new_msg){
            startActivity(new Intent(this,WriteQuestion_Activity.class));
        }

        if (id == R.id.sync){
            startActivity(new Intent(this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        int icons[] = {R.drawable.whatshot,R.drawable.assesment,R.drawable.shif};
        String[] tabtext = getResources().getStringArray(R.array.tabs);

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            tabtext=getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case MOST_DISCUSSED:
                    fragment = new DiscussedFragment();
                    break;
                case MOST_RECENT:
                    fragment = new MostRecentFragment();
                    break;
                case CATEGORIES:
                    fragment = new CategoriesFragment();
                    break;

            }
            //MyFragment myFragment = MyFragment.getInstance(position);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            //Drawable drawable = getResources().getDrawable(icons[position]);
            return tabtext[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    }

