package com.app.paramjobsindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.paramjobsindia.Fragments.CatagoryFragment;
import com.app.paramjobsindia.Fragments.HomeFragment;
import com.app.paramjobsindia.Fragments.JobsFragment;
import com.app.paramjobsindia.Fragments.ProfileFragment;
import com.app.paramjobsindia.service.NetworkBroadcast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class CandidateMainActivity extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver;

    ProgressDialog progress;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    DrawerLayout drawer;
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
        setContentView(R.layout.activity_candidate_main);

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
        sharedPreferences_company = getSharedPreferences(SHARED_PREF_NAME_COMPANY, Context.MODE_PRIVATE);

        String cndname=sharedPreferences_candidate.getString(KEY_CANDIDATE_NAME,null);
        String cmpname=sharedPreferences_company.getString(KEY_COMPANY_NAME,null);

        NavigationView navigationView=findViewById(R.id.nav_view);

        // profile name in navigation drawer
        View headerView=navigationView.getHeaderView(0);
        ImageView frwd=headerView.findViewById(R.id.view_profile);
        TextView Name=headerView.findViewById(R.id.candidatename);
        TextView MobNo=headerView.findViewById(R.id.candidatephn);

        if(cndname!=null){

            Name.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_NAME,"Name"));
            MobNo.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_MOBILE,"Mobile Number"));

        }
        if(cmpname!=null){

            Name.setText(sharedPreferences_company.getString(KEY_COMPANY_NAME,"Name"));
            MobNo.setText(sharedPreferences_company.getString(Key_COMPANY_CONTACT,"Mobile Number"));

        }

        //go to profile
        frwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawer(GravityCompat.START);
                Fragment selectedfragment=null;
                selectedfragment=new ProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();

//                Intent intent=new Intent(getApplicationContext(),ViewProfileActivity.class);
//                startActivity(intent);
            }
        });
        //bottom navigation

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,new HomeFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.homebottom);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfragment=null;

                switch (item.getItemId()){

                    case R.id.chomebottom:

                        selectedfragment=new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.cjobsbottom:
                        selectedfragment=new JobsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.ccatagorybottom:
                        selectedfragment=new CatagoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.cprofilebottom:
                        selectedfragment=new ProfileFragment();
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
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                drawer.closeDrawer(GravityCompat.START);

                Fragment selectedfragment=null;


                switch (id){

                    case R.id.chomeside:

                        selectedfragment=new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.cjobsside:
                        selectedfragment=new JobsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.ccatagoryside:
                        selectedfragment=new CatagoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;


                    case R.id.cappliedjoblistside:

                       Intent intent=new Intent(getApplicationContext(),JobAppliedListActivity.class);
                       startActivity(intent);
                       break;

                    case R.id.clogoutside:
                        SharedPreferences.Editor editor=sharedPreferences_candidate.edit();
                        editor.clear();
                        editor.commit();

                        SharedPreferences.Editor editorcmp=sharedPreferences_company.edit();
                        editorcmp.clear();
                        editorcmp.commit();
                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.cshreside:

                        //share option for sharing app
                        try {

                            Intent r=new Intent(Intent.ACTION_SEND);
                            r.setType("text/plain");
                            r.putExtra(Intent.EXTRA_SUBJECT,"Demo Share");
                            String sharemsg="https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID+"\n\n";
                            r.putExtra(Intent.EXTRA_TEXT,sharemsg);
                            startActivity(Intent.createChooser(r,"share by"));

                        }catch (Exception e){
                            Toast.makeText(CandidateMainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();

                        }
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

    //cart menu showing
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cndnotice_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //go to cart
        if(item.getItemId()==R.id.candnotice){

        }
        return super.onOptionsItemSelected(item);


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