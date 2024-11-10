package com.app.paramjobsindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.Adapter.PostedJbListAdapter;
import com.app.paramjobsindia.DomainName.DomainName;
import com.app.paramjobsindia.Model.PostedJbListModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PstedJobListCmpActivity extends AppCompatActivity {
    Toolbar toolbar;
    PostedJbListAdapter postedJbListAdapter;
    List<PostedJbListModel> list=new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    SearchView searchView;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME_COMPANY="myprefCOMPANY";
    private static final String Key_COMPANY_ID="COMPANYid";

    String cmp_id,us_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psted_job_list_cmp);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME_COMPANY, Context.MODE_PRIVATE);
        cmp_id = sharedPreferences.getString(Key_COMPANY_ID, null);


        //VIEWING DATA IN RECYCLER VIEW
        recyclerView = findViewById(R.id.rvpostedjblist);//initializing recycler view
        LinearLayoutManager layoutManagerbank = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagerbank);

        postedJbListAdapter = new PostedJbListAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(postedJbListAdapter);


        getCustData();


        //searchbar//
        searchView = findViewById(R.id.searchpostedjblst);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newTextS) {
                filterlistS(newTextS);
                return true;
            }


        });///search view//



        //toolbar with back button
        toolbar=findViewById(R.id.toolbarpostedjblst);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);



    }

    ///search tab///
    private void filterlistS(String newTextS) {

        List<PostedJbListModel> filteredListS=new ArrayList<>();

        for(PostedJbListModel listS: list){
            if(listS.getJob_title().toLowerCase().contains(newTextS.toLowerCase())){
                filteredListS.add(listS);
            }
        }
        if (filteredListS.isEmpty()){
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else{

            postedJbListAdapter.setFilteredListS(filteredListS);
        }
    }


    //fetching cust data by volley api

    private void getCustData() {

        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();
        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url=d+"candidate_info/company_post_job_list.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("todayhwmessage");


                    Log.d("message",msg);
                    if(msg.equals("Success")) {

                        JSONArray jsonArray=jsonObject.getJSONArray("bank_jobs");
                        for(int i=0;i<= jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String job_cat_name = jsonObject1.getString("job_cat_name");
                            String job_id = jsonObject1.getString("job_id");
                            String job_title = jsonObject1.getString("job_title");
                            String bu_cat_id = jsonObject1.getString("bu_cat_id");
                            String get_compname = jsonObject1.getString("get_compname");
                            String company_id = jsonObject1.getString("company_id");
                            String qualification_namw = jsonObject1.getString("qualification_namw");
                            String job_type = jsonObject1.getString("job_type");
                            String job_salary = jsonObject1.getString("job_salary");
                            String job_education = jsonObject1.getString("job_education");
                            String job_location = jsonObject1.getString("job_location");
                            String job_note = jsonObject1.getString("job_note");
                            String job_interview_date = jsonObject1.getString("job_interview_date");
                            String job_interview_time = jsonObject1.getString("job_interview_time");
                            String job_opening = jsonObject1.getString("job_opening");
                            String jobpost_status=jsonObject1.getString("jobpost_status");
                            String pro_img = jsonObject1.getString("pro_img");

                            Log.d("img",pro_img);

                            PostedJbListModel listModel = new PostedJbListModel(job_cat_name, job_id,
                                    job_title, bu_cat_id, get_compname,company_id,qualification_namw,job_type,
                                    job_salary,job_education,job_location,job_note,job_interview_date,job_interview_time
                                    ,job_opening,jobpost_status,pro_img);

                            list.add(listModel);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Wrong Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                postedJbListAdapter=new PostedJbListAdapter(getApplicationContext(),list);
                recyclerView.setAdapter(postedJbListAdapter);

                String l=String.valueOf(postedJbListAdapter.getItemCount());
                Log.d("size",l);
                postedJbListAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("company_id",cmp_id);
                Log.d("cmpid",cmp_id);


                return parms;
            }
        };

        queue.add(request);

    }




}