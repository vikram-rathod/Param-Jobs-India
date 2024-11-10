package com.app.paramjobsindia.ExecutiveActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.app.paramjobsindia.Adapter.ExecutiveAdapter.FilteredCmpCallListAdapter;
import com.app.paramjobsindia.Model.FilteredCmpCallListmodel;
import com.app.paramjobsindia.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilteredCmpCallActivity extends AppCompatActivity {
    Toolbar toolbar;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="mypref";
    private static final String Key_CMPID="cmpid";
    private static final String Key_USID="usid";
    private static final String KEY_USERTYPE="usertype";

    //for filtered data

    FilteredCmpCallListAdapter filteredCmpCallListAdapter;
    List<FilteredCmpCallListmodel> list2=new ArrayList<>();
    RecyclerView recyclerView2;
    String cmp_id,us_id,us_type,changeddate,flstat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_cmp_call);
        changeddate=getIntent().getExtras().getString("changeddate");
        flstat= getIntent().getExtras().getString("flstat");

        //VIEWING DATA IN RECYCLER VIEW for filtered data
        recyclerView2=findViewById(R.id.rvfilteredcmpcl);//initializing recycler view
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView2.setLayoutManager(layoutManager);
        
        filteredCmpCallListAdapter=new FilteredCmpCallListAdapter(getApplicationContext(),list2);
        recyclerView2.setAdapter(filteredCmpCallListAdapter);
        //selecting spinner data

        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        cmp_id=sharedPreferences.getString(Key_CMPID,null);
        us_id=sharedPreferences.getString(Key_USID,null);
        us_type=sharedPreferences.getString(KEY_USERTYPE,null);

        getCustFilteredData();
        //toolbar with back button
        toolbar=findViewById(R.id.toolbarfcmpcl);
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

    private void getCustFilteredData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/comzentapp/apis/get_today_complet_calls.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("todayhwmessage");


                    Log.d("message",msg);
                    if(msg.equals("Success")) {

                        JSONArray jsonArray=jsonObject.getJSONArray("cust_info11");
                        for(int j=0;j<= jsonArray.length();j++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(j);

                            String excel_cust_id = jsonObject1.getString("excel_cust_id");
                            String user_assign_data_id = jsonObject1.getString("user_assign_data_id");
                            String excel_cust_name = jsonObject1.getString("excel_cust_name");
                            String excel_cust_mobileno = jsonObject1.getString("excel_cust_mobileno");
                            String follow_up_date = jsonObject1.getString("follow_up_date");
                            String follow_up_desc = jsonObject1.getString("follow_up_desc");
                            String follow_up_mod_name = jsonObject1.getString("follow_up_mod_name");

//                            SortListModel listModel = new SortListModel(excel_cust_id,user_assign_data_id, excel_cust_name,
//                                    excel_cust_mobileno, follow_up_date,follow_up_desc,follow_up_mod_name);
//
//                            list.add(listModel);


                            FilteredCmpCallListmodel model = new FilteredCmpCallListmodel(excel_cust_id,user_assign_data_id, excel_cust_name,
                                    excel_cust_mobileno, follow_up_date,follow_up_desc,follow_up_mod_name);

                            list2.add(model);
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No Record Found..!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                sortListCallAdapter=new SortListCallAdapter(getApplicationContext(),list);
//                recyclerView.setAdapter(sortListCallAdapter);
//
//                String l=String.valueOf(sortListCallAdapter.getItemCount());
//                Log.d("size",l);
//                sortListCallAdapter.notifyDataSetChanged();

                filteredCmpCallListAdapter=new FilteredCmpCallListAdapter(getApplicationContext(),list2);
                recyclerView2.setAdapter(filteredCmpCallListAdapter);

                String l=String.valueOf(filteredCmpCallListAdapter.getItemCount());
                Log.d("size",l);
                filteredCmpCallListAdapter.notifyDataSetChanged();

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
                Log.d("comp_id",cmp_id);
                parms.put("us_id",us_id);
                Log.d("us_id",us_id);

                parms.put("us_type",us_type);
                Log.d("us_type",us_type);

                parms.put("follow_up_date",changeddate);
                Log.d("changeddate",changeddate);

                parms.put("follow_up_mod_id",flstat);
                Log.d("flstat",flstat);

                return parms;
            }
        };

        queue.add(request);

    }

}