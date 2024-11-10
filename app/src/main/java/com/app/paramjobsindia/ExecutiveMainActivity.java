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
import com.app.paramjobsindia.databinding.ActivityExecutiveMainBinding;
import com.app.paramjobsindia.service.NetworkBroadcast;
import com.google.android.datatransport.BuildConfig;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class ExecutiveMainActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;

    ProgressDialog progress;
    SharedPreferences sharedPreferences_executive;
    private static final String SHARED_PREF_NAME_EXECUTIVE="myprefexecutive";
    private static final String KEY_EXECUTIVE_NAME="executiveName";
    private static final String KEY_EXECUTIVE_MOBILE="executivemobile";

    ActivityExecutiveMainBinding executiveMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executiveMainBinding=ActivityExecutiveMainBinding.inflate(getLayoutInflater());
        setContentView(executiveMainBinding.getRoot());


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
        sharedPreferences_executive = getSharedPreferences(SHARED_PREF_NAME_EXECUTIVE, Context.MODE_PRIVATE);
        String execname=sharedPreferences_executive.getString(KEY_EXECUTIVE_NAME,null);

        @SuppressLint(value = {"MissingInflatedId", "LocalSuppress"}) NavigationView navigationView=findViewById(R.id.nav_view);

        // profile name in navigation drawer
        View headerView=navigationView.getHeaderView(0);
        ImageView frwd=headerView.findViewById(R.id.exec_view_profile);
        TextView Name=headerView.findViewById(R.id.execname);
        TextView MobNo=headerView.findViewById(R.id.execphn);

        if(execname!=null){

            Name.setText(sharedPreferences_executive.getString(KEY_EXECUTIVE_NAME,"Name"));
            MobNo.setText(sharedPreferences_executive.getString(KEY_EXECUTIVE_MOBILE,"Mobile Number"));

        }


        //go to profile
        frwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                executiveMainBinding.drawerlayout.closeDrawer(GravityCompat.START);
                Fragment selectedfragment=null;
                selectedfragment=new ProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();

//                Intent intent=new Intent(getApplicationContext(),ViewProfileActivity.class);
//                startActivity(intent);
            }
        });
        //bottom navigation

        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,new HomeFragment()).commit();

        executiveMainBinding.bottomNav.setSelectedItemId(R.id.homebottom);

        executiveMainBinding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfragment=null;

                switch (item.getItemId()){

                    case R.id.exechmbottom:

                        selectedfragment=new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.execjobsbottom:
                        selectedfragment=new JobsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.execcatagorybottom:
                        selectedfragment=new CatagoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.execprofilebottom:
                        selectedfragment=new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;


                }
                return true;
            }
        });

        //top navigation

        executiveMainBinding.toolBar.setNavigationOnClickListener(v -> {
            executiveMainBinding.drawerlayout.openDrawer(GravityCompat.START);

        });

        executiveMainBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                executiveMainBinding.drawerlayout.closeDrawer(GravityCompat.START);

                Fragment selectedfragment=null;


                switch (id){

                    case R.id.exechmeside:

                        selectedfragment=new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.execjobsside:
                        selectedfragment=new JobsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.execcatagoryside:
                        selectedfragment=new CatagoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;


                    case R.id.execappliedjoblistside:

                        Intent intent=new Intent(getApplicationContext(),JobAppliedListActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.execlogoutside:
                        SharedPreferences.Editor editor=sharedPreferences_executive.edit();
                        editor.clear();
                        editor.apply();

                        SharedPreferences.Editor editorcmp=sharedPreferences_executive.edit();
                        editorcmp.clear();
                        editorcmp.apply();
                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.execshareside:

                        //share option for sharing app
                        try {

                            Intent r=new Intent(Intent.ACTION_SEND);
                            r.setType("text/plain");
                            r.putExtra(Intent.EXTRA_SUBJECT,"Demo Share");
                            String sharemsg="https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID+"\n\n";
                            r.putExtra(Intent.EXTRA_TEXT,sharemsg);
                            startActivity(Intent.createChooser(r,"share by"));

                        }catch (Exception e){
                            Toast.makeText(ExecutiveMainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();

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
        item.getItemId();
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