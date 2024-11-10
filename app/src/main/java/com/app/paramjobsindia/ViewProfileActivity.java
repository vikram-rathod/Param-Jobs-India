package com.app.paramjobsindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewProfileActivity extends AppCompatActivity {
    ProgressDialog progress;
    Toolbar toolbar;
    SharedPreferences sharedPreferences_candidate;
    TextView name,education,email,contact,address,experience,hometown,district;

    //create a share preferences name and keys name
    //shareprefference of parammitra registration
    private static final String SHARED_PREF_NAME_CANDIDATE="myprefcandidate";
    private static final String Key_CANDIDATEID="candidateid";
    private static final String Key_CANDIDATE_JOBCARDNO="jobcardno";
    private static final String Key_CANDIDATE_PROFILETYPE="candidate_profile_type";
    private static final String KEY_CANDIDATE_NAME="candidateName";
    private static final String KEY_CANDIDATE_MOBILE="candidatemobile";
    private static final String Key_CANDIDATE_EMAILID="candidate_email_id";
    private static final String KEY_CANDIDATE_LOCAL="candidate_local";
    private static final String KEY_CANDIDATE_ADDRESS="candidate_address";
    private static final String KEY_CANDIDATE_LOCATION="candidate_location";
    private static final String Key_CANDIDATE_DISTRICT="candidate_district";
    private static final String Key_CANDIDATE_EDUCATION="candidate_education";
    private static final String Key_CANDIDATE_EXPERIENCE="candidate_exprince";
    private static final String Key_CANDIDATE_REFERID="refer_id";
    private static final String Key_CANDIDATE_PASSWORD="candidate_password";
    private static final String Key_CANDIDATE_STATUS="candidate_status";
    private static final String Key_CANDIDATE_DATE="candidate_cdate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

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

        name=findViewById(R.id.cname);
        education=findViewById(R.id.ceducation);
        email=findViewById(R.id.cemailid);
        contact=findViewById(R.id.ccontact);
        address=findViewById(R.id.caddress);
        experience=findViewById(R.id.cexperience);
        hometown=findViewById(R.id.chome);
        district=findViewById(R.id.cdistrict);


        sharedPreferences_candidate = getSharedPreferences(SHARED_PREF_NAME_CANDIDATE, Context.MODE_PRIVATE);

        name.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_NAME,null));
        education.setText(sharedPreferences_candidate.getString(Key_CANDIDATE_EDUCATION,null));
        email.setText(sharedPreferences_candidate.getString(Key_CANDIDATE_EMAILID,null));
        contact.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_MOBILE,null));
        address.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_ADDRESS,null));
        experience.setText(sharedPreferences_candidate.getString(Key_CANDIDATE_EXPERIENCE,null));
        hometown.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_LOCAL,null));
        district.setText(sharedPreferences_candidate.getString(Key_CANDIDATE_DISTRICT,null));

        //toolbar with back button

        toolbar=findViewById(R.id.toolbarprofile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);



    }
}