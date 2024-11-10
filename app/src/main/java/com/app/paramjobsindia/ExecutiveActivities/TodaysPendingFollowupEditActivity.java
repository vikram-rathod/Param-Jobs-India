package com.app.paramjobsindia.ExecutiveActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.Model.SelectFSModel;
import com.app.paramjobsindia.Model.SelectQSModel;
import com.app.paramjobsindia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodaysPendingFollowupEditActivity extends AppCompatActivity {
    Toolbar toolbar;
    private DatePickerDialog datePickerDialog;
    SharedPreferences sharedPreferences;
    EditText etdesc;
    TextView name,mobile,dte,desc;
    Spinner spinnerf;
    Button fldatebtn,smt;
    List<SelectQSModel> selectqlist=new ArrayList<>();
    List<SelectFSModel> selectflist=new ArrayList<>();

    ArrayAdapter<SelectQSModel> selectQSModelArrayAdapter;
    ArrayAdapter<SelectFSModel> selectFSModelArrayAdapter;

    String quotstat,flstat,changeddate,excel_custId,usId,cmpId,userType,user_assign_data_id;

    //shareprefference
    private static final String SHARED_PREF_NAME="mypref";
    private static final String Key_USID="usid";
    private static final String Key_CMPID="cmpid";
    private static final String KEY_USERTYPE="usertype";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_pending_followup_edit);
        name=findViewById(R.id.pendtdflcustname);
        mobile=findViewById(R.id.pendtdflcustno);
        dte=findViewById(R.id.pendtdflcustfd);
        desc=findViewById(R.id.pendtdflcustfdesc);
        spinnerf=findViewById(R.id.pendtdfledspinnerf);
        fldatebtn=findViewById(R.id.pendtdfldt);
        etdesc=findViewById(R.id.pendtdfldesc);
        smt=findViewById(R.id.pendtdflsmt);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        usId=sharedPreferences.getString(Key_USID,null);
        Log.d("usId",usId);

        cmpId=sharedPreferences.getString(Key_CMPID,null);
        Log.d("cmpId",cmpId);

        userType=sharedPreferences.getString(KEY_USERTYPE,null);
        Log.d("userType",userType);


        //coming data from eqadapter

        excel_custId=getIntent().getExtras().getString("exceCustId");
        Log.d("excel_custId",excel_custId);
        name.setText(getIntent().getExtras().getString("custName"));
        mobile.setText(getIntent().getExtras().getString("custMn"));

        user_assign_data_id=getIntent().getExtras().getString("user_assign_data_id");
        Log.d("user_assign_data_id",user_assign_data_id);


        //selecting spinner data
        spinnerf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectFSModel selectQualModel=(SelectFSModel) spinnerf.getSelectedItem();
                flstat=selectQualModel.getFollow_up_mod_id();
                Log.d("id", selectQualModel.getFollow_up_mod_id());

//                Toast.makeText(AddCustomerActivity.this, flmod, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), spinc.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //get spinner data
        getSpringData();

        //datepicker function for selection of date
        initDatepicker();

        //date picker button
        fldatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });




        //submitting datA
        smt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitData();

            }

        });

        //toolbar with back button
        toolbar = findViewById(R.id.toolbarpendfldit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void submitData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url = "https://comzent.in/comzentapp/apis/insert_enquiry_followup.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    Log.d("message",msg);
                    if(msg.equals("Post Status Update Success")) {

                        finish();
//                        Intent intent = new Intent(TodaysPendingFollowupEditActivity.this, TodaysCallListActivity.class);
//                        startActivity(intent);

                    }


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

                Map<String,String> params=new HashMap<>();
                params.put("comp_id",cmpId);
                params.put("us_id",usId);
                params.put("us_type",userType);
                params.put("excel_cust_id",excel_custId);
                params.put("follow_up_mod_id",flstat);
                params.put("follow_up_date",changeddate);
                params.put("follow_up_desc",etdesc.getText().toString());
                params.put("user_assign_data_id",user_assign_data_id);

                return params;
            }
        };

        queue.add(request);

    }


    ///date picker function
    private void initDatepicker() {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);



        String date=makeDateString(day,month+1,year);
        Log.d("dtttt",date);
//        fldatebtn.setText(date);




        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month=month+1;
                String date=makeDateString(dayOfMonth,month,year);
                fldatebtn.setText(date);
                //changing date format


                String d1=fldatebtn.getText().toString();

                SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
                //String datestring=sdf.format(d);

                try {
                    Date date1 = format1.parse(d1);
                    changeddate=sdf1.format(date1);
                    Log.d("changeddate",changeddate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        };
        /*
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);


         */
        int style= AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        datePickerDialog=new DatePickerDialog(TodaysPendingFollowupEditActivity.this,style,dateSetListener,year,month,day);
    }
///date picker function


    private String makeDateString(int dayOfMonth, int month, int year) {

        int mth= month;
        String fm=""+mth;
        String fd=""+dayOfMonth;
        if(mth<10){
            fm ="0"+mth;
        }
        if (dayOfMonth<10){
            fd="0"+dayOfMonth;
        }
        String date= fd+"-"+fm+"-"+year;

        return date;
    }


    //for getting spring data
    private void getSpringData() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1="https://comzent.in/comzentapp/apis/get_followup_list.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //  Toast.makeText(getApplicationContext()(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    JSONArray jsonArray=respObj.getJSONArray("followup_details");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String spid=jsonObject.optString("follow_up_mod_id");
                        String spname=jsonObject.optString("follow_up_mod_name");

                        selectflist.add(new SelectFSModel(spid,spname));
//                        Log.d("s", String.valueOf(cllist));
                        selectFSModelArrayAdapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,selectflist);
                        selectFSModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerf.setAdapter(selectFSModelArrayAdapter);


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
        });

        queue.add(request);
    }

    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}