package com.app.paramjobsindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.Adapter.AppliedJobAdapter;
import com.app.paramjobsindia.Adapter.AppliedJobCmpnyAdapter;
import com.app.paramjobsindia.DomainName.DomainName;
import com.app.paramjobsindia.Model.AppliedJobModelList;
import com.app.paramjobsindia.Model.AppliedJobcmpnyModelList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobAppliedListActivity extends AppCompatActivity {

    ProgressDialog progress;
    SharedPreferences sharedPreferences_candidate,sharedPreferences_company;

    Toolbar toolbar;
    private static final String SHARED_PREF_NAME_CANDIDATE="myprefcandidate";
    private static final String Key_CANDIDATEID="candidateid";

    private static final String SHARED_PREF_NAME_COMPANY="myprefCOMPANY";
    private static final String Key_COMPANY_ID="COMPANYid";

    AppliedJobAdapter appliedJobAdapter;
    List<AppliedJobModelList> list=new ArrayList<>();

    AppliedJobCmpnyAdapter appliedJobCmpnyAdapter;
    List<AppliedJobcmpnyModelList> cmplist=new ArrayList<>();

    String candid,cmpid;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_applied_list);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);


        // Refresh  the layout
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // Your code goes here
                        // In this code, we are just 
                        // changing the text in the textbox
                        Toast.makeText(JobAppliedListActivity.this, "Refreshed!", Toast.LENGTH_SHORT).show();

                        // This line is important as it explicitly 
                        // refreshes only once
                        // If "true" it implicitly refreshes forever
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
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

        recyclerView=findViewById(R.id.rvjobapplied);

        sharedPreferences_candidate =getSharedPreferences(SHARED_PREF_NAME_CANDIDATE, Context.MODE_PRIVATE);
//        sharedPreferences_company =getSharedPreferences(SHARED_PREF_NAME_COMPANY, Context.MODE_PRIVATE);
        sharedPreferences_company = getSharedPreferences(SHARED_PREF_NAME_COMPANY, Context.MODE_PRIVATE);

        cmpid=sharedPreferences_company.getString(Key_COMPANY_ID,null);
        candid=sharedPreferences_candidate.getString(Key_CANDIDATEID,null);

        Log.d("cmpid",cmpid);


        if(candid!=null){

            Log.d("candid",candid);
            LinearLayoutManager layoutManagerbank=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManagerbank);
            appliedJobAdapter=new AppliedJobAdapter(getApplicationContext(),list);
            recyclerView.setAdapter(appliedJobAdapter);
            getJobListData();//function used for volley json data fetch

        }



        if((cmpid!=null)){

            LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            appliedJobCmpnyAdapter=new AppliedJobCmpnyAdapter(getApplicationContext(),cmplist);
            recyclerView.setAdapter(appliedJobCmpnyAdapter);
            getcmpJobListData();//function used for volley json data fetch

        }


//toolbar with back button

        toolbar=findViewById(R.id.toolbarappliedjobs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

//job applied candidate list in candidate profile

    private void getJobListData() {

        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1=d+"candidate_info/applied_job_list_for_candidate.php";

        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ss",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    String msg = respObj.getString("todayhwmessage");
//                    Log.d("dd",dd);

                    if ((msg.equals("Success"))) {

                        JSONArray jsonArray = respObj.getJSONArray("can_jobs");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String job_id = jsonObject.getString("job_id");
                            String job_title = jsonObject.getString("job_title");
                            String bu_cat_id = jsonObject.getString("bu_cat_id");
                            String get_compname = jsonObject.getString("get_compname");
                            String company_id = jsonObject.getString("company_id");
                            String qualification_namw = jsonObject.getString("qualification_namw");
                            String job_type = jsonObject.getString("job_type");
                            String job_salary = jsonObject.getString("job_salary");
                            String job_education = jsonObject.getString("job_education");
                            String job_location = jsonObject.getString("job_location");
                            String job_note = jsonObject.getString("job_note");
                            String job_interview_date = jsonObject.getString("job_interview_date");
                            String job_interview_time = jsonObject.getString("job_interview_time");
                            String job_opening = jsonObject.getString("job_opening");
                            String candidate_post_applied_status = jsonObject.getString("candidate_post_applied_status");
                            String pro_img = jsonObject.getString("pro_img");

                            AppliedJobModelList listModel = new AppliedJobModelList(job_id, job_title,
                                    bu_cat_id, get_compname, company_id, qualification_namw, job_type, job_salary
                                    , job_education, job_location, job_note, job_interview_date, job_interview_time
                                    , job_opening, candidate_post_applied_status, pro_img);

                            list.add(listModel);

                        }
                    }else{
                        Toast.makeText(JobAppliedListActivity.this, "No Record Available", Toast.LENGTH_SHORT).show();
                    }

                    } catch(JSONException e){
                        e.printStackTrace();
                    }

                    appliedJobAdapter = new AppliedJobAdapter(getApplicationContext(), list);
                    recyclerView.setAdapter(appliedJobAdapter);
                    appliedJobAdapter.notifyDataSetChanged();

                

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("candidate_id",candid);
                return parms;
            }
        };

        queue.add(request);


    }

//job applied candidate list in company profile

    private void getcmpJobListData() {

        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1="https://paramjobsindia.com/dash/apis/candidate_info/applied_job_list_for_company.php";

        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    Log.d("response",response);
                    String msg = respObj.getString("todayhwmessage");
//                    Log.d("dd",dd);

                    if ((msg.equals("Success"))) {


                        JSONArray jsonArray = respObj.getJSONArray("applied_can_list");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String candidate_id = jsonObject.getString("candidate_id");
                            String job_id = jsonObject.getString("job_id");
                            String candidate_name = jsonObject.getString("candidate_name");
                            String jobcardno = jsonObject.getString("jobcardno");
                            String candidate_number = jsonObject.getString("candidate_number");
                            String candidate_email_id = jsonObject.getString("candidate_email_id");
                            String candidate_address = jsonObject.getString("candidate_address");
                            String candidate_education = jsonObject.getString("candidate_education");
                            String candidate_exprince = jsonObject.getString("candidate_exprince");
                            String bu_cat_id = jsonObject.getString("bu_cat_id");
                            String get_compname = jsonObject.getString("get_compname");
                            String company_id = jsonObject.getString("company_id");
                            String qualification_namw = jsonObject.getString("qualification_namw");
                            String job_title=jsonObject.getString("job_title");
                            String job_type = jsonObject.getString("job_type");
                            String job_salary = jsonObject.getString("job_salary");
                            String job_education = jsonObject.getString("job_education");
                            String job_location = jsonObject.getString("job_location");
                            String job_note = jsonObject.getString("job_note");
                            String job_interview_date = jsonObject.getString("job_interview_date");
                            String job_interview_time = jsonObject.getString("job_interview_time");
                            String job_opening = jsonObject.getString("job_opening");
                            String candidate_post_applied_status = jsonObject.getString("candidate_post_applied_status");
                            String pro_img = jsonObject.getString("pro_img");

                            Log.d("jbbbbid",job_id);

                            AppliedJobcmpnyModelList listModel = new AppliedJobcmpnyModelList(candidate_id,job_id,candidate_name, jobcardno, candidate_number, candidate_email_id, candidate_address, candidate_education, candidate_exprince,
                                    bu_cat_id, get_compname, company_id, qualification_namw,job_title, job_type, job_salary
                                    , job_education, job_location, job_note, job_interview_date, job_interview_time
                                    , job_opening,candidate_post_applied_status, pro_img);
                            cmplist.add(listModel);

                        }
                    }else{
                        Toast.makeText(JobAppliedListActivity.this, "No record Found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                appliedJobCmpnyAdapter=new AppliedJobCmpnyAdapter(getApplicationContext(),cmplist);
                recyclerView.setAdapter(appliedJobCmpnyAdapter);
                appliedJobCmpnyAdapter.notifyDataSetChanged();



            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("company_id",cmpid);
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