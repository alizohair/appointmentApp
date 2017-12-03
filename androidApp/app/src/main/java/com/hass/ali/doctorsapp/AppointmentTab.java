package com.hass.ali.doctorsapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentTab extends AppCompatActivity {



    private TabLayout tabLayout;
    private ViewPager viewPager;
   // private View V1,V2,V3,V4;
  //  View view;



    public AppointmentTab() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.container);
      viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//Fragment fragment = ((ViewPagerAdapter)viewPager.getAdapter()).get
                Fragment fragment = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
                fragment.onResume();

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PendingFragmentList(), "Pending");
        adapter.addFragment(new TokenedFragmentList(), "Tokened");
        adapter.addFragment(new AvailedFragmentList(), "Availed");
        //adapter.addFragment(new ProInboxFragment(), "Inbox");

        viewPager.setAdapter(adapter);


    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
private Map<Integer,String> integerStringMap ;
        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);

            integerStringMap = new HashMap<>();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public void refreshTabs(){



        viewPager.getAdapter().notifyDataSetChanged();
    }


}
