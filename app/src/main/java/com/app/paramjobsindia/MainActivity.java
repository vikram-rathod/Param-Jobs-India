package com.app.paramjobsindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.app.paramjobsindia.Fragments.CatagoryFragment;
import com.app.paramjobsindia.Fragments.HomeFragment;
import com.app.paramjobsindia.Fragments.JobsFragment;
import com.app.paramjobsindia.Fragments.LoginFragment;
import com.app.paramjobsindia.Fragments.ProfileFragment;
import com.app.paramjobsindia.service.NetworkBroadcast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    DrawerLayout drawer;
    ProgressDialog progress;
    SharedPreferences sharedPreferences_candidate,sharedPreferences_company;
    private static final String SHARED_PREF_NAME_CANDIDATE="myprefcandidate";
    private static final String KEY_CANDIDATE_NAME="candidateName";
    private static final String KEY_CANDIDATE_MOBILE="candidatemobile";

    private static final String SHARED_PREF_NAME_COMPANY="myprefCOMPANY";
    private static final String KEY_COMPANY_NAME="COMPANYName";
    private static final String Key_COMPANY_CONTACT="COMPANYCONTACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disable screenshot
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen

        setContentView(R.layout.activity_main);

        progress=new ProgressDialog(this);
        progress.setMessage("Please Wait");
        progress.show();
        //giving delay of 3 sec to progress dialogue
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.setCanceledOnTouchOutside(true);
                progress.dismiss();

            }
        },3000);


        sharedPreferences_candidate = getSharedPreferences(SHARED_PREF_NAME_CANDIDATE, Context.MODE_PRIVATE);
        String name=sharedPreferences_candidate.getString(KEY_CANDIDATE_NAME,null);
        if(name!=null){

            Intent i=new Intent(getApplicationContext(),CandidateMainActivity.class);
            startActivity(i);
        }

        sharedPreferences_company = getSharedPreferences(SHARED_PREF_NAME_COMPANY, Context.MODE_PRIVATE);
        String cmpname=sharedPreferences_company.getString(KEY_COMPANY_NAME,null);
        if(cmpname!=null){

            Intent i=new Intent(getApplicationContext(),CompanyMainActivity.class);
            startActivity(i);
        }

        NavigationView navigationView=findViewById(R.id.nav_view);
        // profile name in navigation drawer
        View headerView=navigationView.getHeaderView(0);
        TextView Login=headerView.findViewById(R.id.loginheader);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        //bottom navigation

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,new HomeFragment()).commit();



        bottomNavigationView.setSelectedItemId(R.id.homebottom);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfragment=null;

                if(item.getItemId()==R.id.signinbottom){
                    toolbar.setVisibility(View.GONE);
                }else{
                    toolbar.setVisibility(View.VISIBLE);
                }
                switch (item.getItemId()){

                    case R.id.homebottom:
                        selectedfragment=new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.jobsbottom:
                        selectedfragment=new JobsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.catagorybottom:
                        selectedfragment=new CatagoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.signinbottom:
                        selectedfragment=new LoginFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;


                }
                return true;
            }
        });

        //top navigation

        toolbar = findViewById(R.id.toolBar);
        drawer = findViewById(R.id.drawerlayout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                drawer.closeDrawer(GravityCompat.START);

                Fragment selectedfragment=null;


                switch (id){

                    case R.id.homeside:

                        selectedfragment=new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.jobsside:
                        selectedfragment=new JobsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.catagoryside:
                        selectedfragment=new CatagoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.profileside:

                        selectedfragment=new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;


                    default:
                        return true;
                }
                return true;
            }
        });
        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

        @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


    protected void onDestroy() {//no internet connection
        super.onDestroy();//no internet connection
        unregisterReceiver(broadcastReceiver);//no internet connection
    }

}
