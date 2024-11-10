package com.app.paramjobsindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.DomainName.DomainName;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    ProgressDialog progress;
    Toolbar toolbar;
    SharedPreferences sharedPreferences_candidate;
    EditText candidate_name,candidate_email_id,candidate_contact,candidate_local,candidate_address,candidate_location
            ,candidate_district,candidate_education,candidate_exprince;

    Button updatebtn;
    private static final String SHARED_PREF_NAME_CANDIDATE="myprefcandidate";
    private static final String KEY_NAME_CANDIDATE="candidateName";
    private static final String Key_CANDIDATEID="candidateid";
    private static final String Key_CANDIDATE_JOBCARDNO="jobcardno";
    private static final String Key_CANDIDATE_EMAILID="candidate_email_id";
    private static final String Key_CANDIDATE_STATUS="candidate_status";
    private static final String Key_CANDIDATE_PROFILETYPE="candidate_profile_type";
    private static final String KEY_CANDIDATE_NAME="candidateName";
    private static final String KEY_CANDIDATE_MOBILE="candidatemobile";
    private static final String KEY_CANDIDATE_LOCAL="candidate_local";
    private static final String KEY_CANDIDATE_ADDRESS="candidate_address";
    private static final String KEY_CANDIDATE_LOCATION="candidate_location";
    private static final String Key_CANDIDATE_DISTRICT="candidate_district";
    private static final String Key_CANDIDATE_EDUCATION="candidate_education";
    private static final String Key_CANDIDATE_EXPERIENCE="candidate_exprince";
    private static final String Key_CANDIDATE_REFERID="refer_id";
    private static final String Key_CANDIDATE_PASSWORD="candidate_password";
    private static final String Key_CANDIDATE_DATE="candidate_cdate";
   String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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

        sharedPreferences_candidate =getSharedPreferences(SHARED_PREF_NAME_CANDIDATE, Context.MODE_PRIVATE);
        id=sharedPreferences_candidate.getString(Key_CANDIDATEID,null);
        candidate_name=findViewById(R.id.cndname);
        candidate_email_id=findViewById(R.id.emailid);
        candidate_contact=findViewById(R.id.edcndmobno);
        candidate_local=findViewById(R.id.local);
        candidate_address=findViewById(R.id.address);
        candidate_location=findViewById(R.id.city);
        candidate_district=findViewById(R.id.district);
        candidate_education=findViewById(R.id.education);
        candidate_exprince=findViewById(R.id.experience);
        updatebtn=findViewById(R.id.updatebtn);


        //updating candidate data

        candidate_name.setText(sharedPreferences_candidate.getString(KEY_NAME_CANDIDATE,null));
        candidate_email_id.setText(sharedPreferences_candidate.getString(Key_CANDIDATE_EMAILID,null));
        candidate_contact.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_MOBILE,null));
        candidate_local.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_LOCAL,null));
        candidate_address.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_ADDRESS,null));
        candidate_location.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_LOCATION,null));
        candidate_district.setText(sharedPreferences_candidate.getString(Key_CANDIDATE_DISTRICT,null));
        candidate_education.setText(sharedPreferences_candidate.getString(Key_CANDIDATE_EDUCATION,null));
        candidate_exprince.setText(sharedPreferences_candidate.getString(Key_CANDIDATE_EXPERIENCE,null));

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfile();

            }
        });
        //toolbar with back button

        toolbar=findViewById(R.id.toolbareditprofile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void updateProfile() {
        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();
        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url=d+"candidate_info/candidate_update_profile.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");


                    Log.d("message",msg);

                    SharedPreferences.Editor editoru = sharedPreferences_candidate.edit();
                    editoru.putString(Key_CANDIDATEID, id);
                    editoru.putString(KEY_CANDIDATE_NAME, candidate_name.getText().toString());
                    editoru.putString(KEY_CANDIDATE_LOCAL, candidate_local.getText().toString());
                    editoru.putString(Key_CANDIDATE_EMAILID, candidate_email_id.getText().toString());
                    editoru.putString(KEY_CANDIDATE_ADDRESS, candidate_address.getText().toString());
                    editoru.putString(KEY_CANDIDATE_LOCATION, candidate_location.getText().toString());
                    editoru.putString(Key_CANDIDATE_DISTRICT, candidate_district.getText().toString());
                    editoru.putString(Key_CANDIDATE_EDUCATION, candidate_education.getText().toString());
                    editoru.putString(Key_CANDIDATE_EXPERIENCE, candidate_exprince.getText().toString());


//                        Saving values to editor
                    editoru.commit();

//                    finish();
                    Toast.makeText(EditProfile.this, "Account Created Successfully!\n" +
                            "Please Login Now", Toast.LENGTH_SHORT).show();
                            Intent j=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(j);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("candidate_id",id);
                parms.put("jobcardno","cardno");
                parms.put("candidate_name",candidate_name.getText().toString());
                parms.put("candidate_email_id",candidate_email_id.getText().toString());
                parms.put("candidate_local",candidate_local.getText().toString());
                parms.put("candidate_address",candidate_address.getText().toString());
                parms.put("candidate_location",candidate_location.getText().toString());
                parms.put("candidate_district",candidate_district.getText().toString());
                parms.put("candidate_education",candidate_education.getText().toString());
                parms.put("candidate_exprince",candidate_exprince.getText().toString());


                return parms;
            }
        };

        queue.add(request);

    }

    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);


    }
}