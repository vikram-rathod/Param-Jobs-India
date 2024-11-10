package com.app.paramjobsindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.DomainName.DomainName;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SingleJobDetailsActivity extends AppCompatActivity {

    ProgressDialog progress;
    Toolbar toolbar;
    TextView title,cmpname,location,salary,interviewdate,jobopenings,jobdescription,jobtype;
    ImageView img,shareimg;
    String jobid;
    Button apply_job,cmp_back_hm;
    String companyid,cndid,candtype,cmptype,type,name,cmpanyname;

    SharedPreferences sharedPreferences_candidate,sharedPreferences_company;
    private static final String SHARED_PREF_NAME_CANDIDATE="myprefcandidate";
    private static final String Key_CANDIDATEID="candidateid";
    private static final String KEY_CANDIDATE_NAME="candidateName";
    private static final String Key_CANDIDATE_PROFILETYPE="candidate_profile_type";

    private static final String SHARED_PREF_NAME_COMPANY="myprefCOMPANY";
    private static final String KEY_COMPANY_NAME="COMPANYName";
    private static final String Key_COMPANY_PROFILETYPE="COMPANY_profile_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_job_details);

        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait");
        progress.show();
        //giving delay of 3 sec to progress dialogue
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.setCanceledOnTouchOutside(true);
                progress.dismiss();

            }
        }, 3000);

        title = findViewById(R.id.t);
        cmpname = findViewById(R.id.cn);
        location = findViewById(R.id.a);
        salary = findViewById(R.id.s);
        interviewdate = findViewById(R.id.idate);
        jobopenings = findViewById(R.id.jobopening);
        jobdescription = findViewById(R.id.desc);
        img = findViewById(R.id.img);
        apply_job = findViewById(R.id.appjob);
        jobtype = findViewById(R.id.jobt);
        shareimg = findViewById(R.id.sharejob);
        cmp_back_hm = findViewById(R.id.cmpbacktohm);

        sharedPreferences_candidate = getSharedPreferences(SHARED_PREF_NAME_CANDIDATE, Context.MODE_PRIVATE);
        cndid = sharedPreferences_candidate.getString(Key_CANDIDATEID, null);
        candtype = sharedPreferences_candidate.getString(Key_CANDIDATE_PROFILETYPE, null);


//        Log.d("candtype",candtype);

        //company shareprefference
        sharedPreferences_company = getSharedPreferences(SHARED_PREF_NAME_COMPANY, Context.MODE_PRIVATE);
        cmptype = sharedPreferences_company.getString(Key_COMPANY_PROFILETYPE, null);


        //if there is no account of candidate or company then go to main activity for creating account
        name = sharedPreferences_candidate.getString(KEY_CANDIDATE_NAME, null);
        cmpanyname = sharedPreferences_company.getString(KEY_COMPANY_NAME, null);

        System.out.println(cmpanyname);

        /*

        if(((name==null))&&((cmpanyname==null))){

            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }


 */
        cmp_back_hm.setVisibility(View.GONE);


        if (candtype != null) {

            type = candtype;
            Log.d("type",type);

            if (type.equals("2")) {
                if (cndid != null) {

                    cmp_back_hm.setVisibility(View.GONE);

                }
            } else if ((type.equals("1"))) {

                cmp_back_hm.setVisibility(View.VISIBLE);
                apply_job.setVisibility(View.GONE);


            }
            else {
                cmp_back_hm.setVisibility(View.GONE);


            }
        }

        if (cmptype != null) {

            type = cmptype;

            if (type.equals("2")) {
                if (cndid != null) {

                    cmp_back_hm.setVisibility(View.GONE);

                }
            } else if ((type.equals("1"))) {

                cmp_back_hm.setVisibility(View.VISIBLE);
                apply_job.setVisibility(View.GONE);


            }
            else {
                cmp_back_hm.setVisibility(View.GONE);


            }
        }



        getData();


        cmp_back_hm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(getApplicationContext(), "Company can't apply to job", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CompanyMainActivity.class);
                startActivity(intent);

            }
        });

        apply_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(SingleJobDetailsActivity.this, name, Toast.LENGTH_SHORT).show();
//                Toast.makeText(SingleJobDetailsActivity.this, cmpanyname, Toast.LENGTH_SHORT).show();

                if(!(name!=null) && !(cmpanyname!=null)){

                    Toast.makeText(SingleJobDetailsActivity.this, "please Create Account first", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);

                }

                else{

                    if(type.equals("2")) {
                        if (cndid != null) {

                            applyJob();
                        }
                    }else if ((type.equals("1"))){
                        Toast.makeText(getApplicationContext(), "Company can't apply to job", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),CompanyMainActivity.class);
                        startActivity(intent);
                    }


                }


            }
        });

        shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = title.getText().toString()+
                cmpname.getText().toString()+
                location.getText().toString()+
                salary.getText().toString()+
                jobtype.getText().toString()+
                jobdescription.getText().toString()+
                jobopenings.getText().toString();

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });


        //toolbar with back button

        toolbar=findViewById(R.id.toolbarsingledetailjob);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void getData() {

        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1=d+"candidate_info/get_job_details.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("ss",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
//                    String dd=respObj.getString("message");
//                    Log.d("dd",dd);

//                    if(!(dd.equals("No Records Found...!"))) {

                    JSONArray jsonArray = respObj.getJSONArray("job_post");

//                    Log.d("class details", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String job_id = jsonObject.getString("job_id");
                        String job_title = jsonObject.getString("job_title");
                        String bu_cat_id = jsonObject.getString("bu_cat_id");
                        String get_compname = jsonObject.getString("get_compname");
                        String company_id = jsonObject.getString("company_id");
                        String qualification_id = jsonObject.getString("qualification_id");
                        String job_type = jsonObject.getString("job_type");
                        String job_salary = jsonObject.getString("job_salary");
                        String job_education = jsonObject.getString("job_education");
                        String job_location = jsonObject.getString("job_location");
                        String job_note = jsonObject.getString("job_note");
                        String job_interview_date = jsonObject.getString("job_interview_date");
                        String job_interview_time = jsonObject.getString("job_interview_time");
                        String job_opening = jsonObject.getString("job_opening");
                        String pro_img = jsonObject.getString("pro_img");

                        companyid=company_id;
                        Log.d("companyid",companyid);

                        title.setText(job_title);
                        cmpname.setText(get_compname);
                        location.setText(job_location);
                        salary.setText("₹"+job_salary+"/-");
                        jobtype.setText(job_type);
                        jobdescription.setText(job_note);
                        jobopenings.setText(job_opening);

                        String dt=job_interview_date;//seting textview with data coming from newsadapter
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//date format of date fetching from api
                        SimpleDateFormat formatterOut = new SimpleDateFormat("dd-MM-yyyy");//we wand to diplay date in this format


                        //formating date into desire one
                        try {

                            Date date = formatter.parse(dt);
                            System.out.println(date);
                            interviewdate.setText(formatterOut.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        Glide.with(getApplication())
                                .load(pro_img)
                                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                                .error(R.drawable.jobc)
                                .into(img);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                jobid=getIntent().getExtras().getString("job_id").toString();

                Log.d("jobid",jobid);
                Map<String, String> params = new HashMap<String, String>();

                params.put("job_id",jobid);



                return params;
            }
        };

        queue.add(request);


    }

    private void applyJob() {

        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url=d+"candidate_info/candidate_apply_for_jobs.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");

                    Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),JobAppliedListActivity.class);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("candidate_id",cndid);
                parms.put("job_id",jobid);
                parms.put("company_id",companyid);

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