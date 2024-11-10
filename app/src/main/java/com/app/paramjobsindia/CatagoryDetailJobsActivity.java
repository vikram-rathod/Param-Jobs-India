package com.app.paramjobsindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.Adapter.JobCatagoryDetailAdapter;
import com.app.paramjobsindia.DomainName.DomainName;
import com.app.paramjobsindia.Model.JobCatagoryDetailModelList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatagoryDetailJobsActivity extends AppCompatActivity {
   ProgressDialog progress;
    JobCatagoryDetailAdapter adapter;//defining adpater
    RequestQueue requestQueue;//defining request for volley get
    List<JobCatagoryDetailModelList> itemlist=new ArrayList<>();//initializing model arraylist
    RecyclerView recyclerv;//defining recyclerview
    String id,name;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_detail_jobs);
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
        //getting data from JobCatagoryAdapter

        id=getIntent().getExtras().get("id").toString();
        name=getIntent().getExtras().get("name").toString();


        recyclerv=findViewById(R.id.recyclerViewcatdetail);
//gridlayout manager used for dispaying data in grid view
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerv.setLayoutManager(gridLayoutManager);
//        adapter=new JobCatagoryAdapter(getContext(),Myitemlist);
//        recyclerv.setAdapter(adapter);
        adapter=new JobCatagoryDetailAdapter(getApplicationContext(),itemlist);
        recyclerv.setAdapter(adapter);
        getData();//function used for volley json data fetch

        //toolbar with back button

        toolbar=findViewById(R.id.toolbardetailjobcat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
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

    private void getData() {

        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1=d+"candidate_info/get_jobs_category_wise.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ss",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
//                    String dd=respObj.getString("message");
//                    Log.d("dd",dd);

//                    if(!(dd.equals("No Records Found...!"))) {

                    JSONArray jsonArray = respObj.getJSONArray("job_post");

                    Log.d("class details", jsonArray.toString());
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

                        JobCatagoryDetailModelList listModel = new JobCatagoryDetailModelList(job_id, job_title,
                                bu_cat_id, get_compname, company_id, qualification_id,job_type,job_salary
                        ,job_education,job_location,job_note,job_interview_date,job_interview_time
                        ,job_opening,pro_img);
                        itemlist.add(listModel);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter=new JobCatagoryDetailAdapter(getApplicationContext(),itemlist);
                recyclerv.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();


                params.put("bu_cat_id",id);



                return params;
            }
        };

        queue.add(request);


    }

}