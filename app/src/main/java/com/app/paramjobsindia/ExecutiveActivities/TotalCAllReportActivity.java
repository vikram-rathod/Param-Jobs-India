package com.app.paramjobsindia.ExecutiveActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TotalCAllReportActivity extends AppCompatActivity {
    Toolbar toolbar;

    TextView total_call,sort_list_call,sort_other_call,noof_call;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME="mypref";
    private static final String Key_CMPID="cmpid";
    private static final String Key_USID="usid";
    private static final String KEY_USERTYPE="usertype";

    String cmp_id,us_type,us_id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_call_report);
        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        cmp_id=sharedPreferences.getString(Key_CMPID,null);
        us_type=sharedPreferences.getString(KEY_USERTYPE,null);
        us_id=sharedPreferences.getString(Key_USID,null);

        total_call=findViewById(R.id.tdclrpttotalcall);//initializing recycler view
        sort_list_call=findViewById(R.id.tdclrpttoslistcalls);//initializing recycler view
        sort_other_call=findViewById(R.id.tdclrpttosothercall);//initializing recycler view
        noof_call=findViewById(R.id.tdclrpttonoof);//initializing recycler view


        getCustData();


        //toolbar with back button
        toolbar=findViewById(R.id.toolbartodayclreport);
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


    //fetching cust data by volley api

    private void getCustData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/comzentapp/apis/get_todays_call_reports.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("todayhwmessage");


                    Log.d("message",msg);


                    JSONArray jsonArray=jsonObject.getJSONArray("cust_info11");
                    for(int i=0;i<= jsonArray.length();i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String todays_total_call = jsonObject1.getString("todays_total_call");
                        String todays_sort_list_call = jsonObject1.getString("todays_sort_list_call");
                        String todays_sort_other_call = jsonObject1.getString("todays_sort_other_call");
                        String todays_noof_call = jsonObject1.getString("todays_noof_call");

                        Log.d("todaystotalcall",todays_total_call);
                        total_call.setText(todays_total_call);
                        sort_list_call.setText(todays_sort_list_call);
                        sort_other_call.setText(todays_sort_other_call);
                        noof_call.setText(todays_noof_call);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


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
                parms.put("comp_id",cmp_id);
                parms.put("us_type",us_type);
                parms.put("us_id",us_id);

                return parms;
            }
        };

        queue.add(request);

    }

}