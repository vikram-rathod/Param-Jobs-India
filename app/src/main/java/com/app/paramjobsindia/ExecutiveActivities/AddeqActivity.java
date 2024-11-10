package com.app.paramjobsindia.ExecutiveActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.ExecutiveMainActivity;
import com.app.paramjobsindia.Model.SelectbiscatModel;
import com.app.paramjobsindia.Model.SelectflmodeModel;
import com.app.paramjobsindia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddeqActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText custmob,custname,busname,emailid,bsid,followupModId,
            followupDesc,custAdd;
    Button submitDatabtn,datepicker;
    private DatePickerDialog datePickerDialog;
    //for spinner data list
    List<SelectflmodeModel> flmodlist=new ArrayList<>();
    ArrayAdapter<SelectflmodeModel> flmodadapter;

    List<SelectbiscatModel> biscatlist=new ArrayList<>();
    ArrayAdapter<SelectbiscatModel> biscatadapter;
    Spinner spflmod,spbiscat;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME="mypref";
    private static final String Key_CMPID="cmpid";
    private static final String Key_USID="usid";

    String cmp_id,userid,flmod,biscat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addeq);
        custmob=findViewById(R.id.custmn);
        custname=findViewById(R.id.custnm);
        busname=findViewById(R.id.bsnm);
        emailid=findViewById(R.id.custeid);
        followupDesc=findViewById(R.id.fldesc);
        custAdd=findViewById(R.id.csadd);
        submitDatabtn=findViewById(R.id.smt);
        datepicker=findViewById(R.id.date_pickr);
        spflmod=findViewById(R.id.flmdid);
        spbiscat=findViewById(R.id.bscatid);




        // for class selection spinner
        spflmod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectflmodeModel selectfmModel=(SelectflmodeModel) spflmod.getSelectedItem();
                flmod=selectfmModel.getFl_mod_id();
                Log.d("id", selectfmModel.getFl_mod_id());

//                Toast.makeText(AddCustomerActivity.this, flmod, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), spinc.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // for business catagory selection spinner
        spbiscat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectbiscatModel selectbiscatModel=(SelectbiscatModel) spbiscat.getSelectedItem();
                biscat=selectbiscatModel.getBu_cat_id();
                Log.d("id2", selectbiscatModel.getBu_cat_id());
//                Toast.makeText(AddCustomerActivity.this, biscat, Toast.LENGTH_SHORT).show();

//                Toast.makeText(getContext(), spinc.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //defining shareprefference for getting data from it.
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        cmp_id=sharedPreferences.getString(Key_CMPID,null);
        userid=sharedPreferences.getString(Key_USID,null);

        //datepicker function for selection of date
        initDatepicker();



        //submitting datA
        submitDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((custname.getText().toString().equals(""))||((custmob.getText().toString().equals("")))){

                    Toast.makeText(AddeqActivity.this, "Please Enter Customer name", Toast.LENGTH_SHORT).show();

                }
                else {
                    SendCustData();
                }


            }
        });

        //date picker button
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });

        getSpringData();//for spinner api fetching


        //toolbar with back button
        toolbar=findViewById(R.id.toolbaraddeq);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    // get spinner data from api 03/09/2022

    private void getSpringData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1="https://comzent.in/comzentapp/apis/get_followup_and_buscat.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ClassDetails",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    JSONArray jsonArray=respObj.getJSONArray("foll_m_details");
                    JSONArray jsonArray2=respObj.getJSONArray("bu_cat_details");

                    Log.d("class details",jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String flname=jsonObject.optString("follow_up_mod_name");
                        String flid=jsonObject.optString("follow_up_mod_id");

                        flmodlist.add(new SelectflmodeModel(flid,flname));
//                        Log.d("s", String.valueOf(cllist));
                        flmodadapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,flmodlist);
                        flmodadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spflmod.setAdapter(flmodadapter);


                    }

                    for(int j=0;j<jsonArray2.length();j++){
                        JSONObject jsonObject=jsonArray2.getJSONObject(j);
                        String biscatname=jsonObject.optString("bu_cat_name");
                        String biscatid=jsonObject.optString("bu_cat_id");

                        biscatlist.add(new SelectbiscatModel(biscatid,biscatname));
//                        Log.d("s", String.valueOf(cllist));
                        biscatadapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,biscatlist);
                        biscatadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spbiscat.setAdapter(biscatadapter);


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

                Map<String, String> params = new HashMap<String, String>();


                params.put("us_id",userid);

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
        datepicker.setText(date);




        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month=month+1;
                String date=makeDateString(dayOfMonth,month,year);
                datepicker.setText(date);
            }

        };
        /*
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);


         */
        int style= AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        datePickerDialog=new DatePickerDialog(AddeqActivity.this,style,dateSetListener,year,month,day);
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

    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);


    }

    //fetching cust data by volley api

    private void SendCustData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/comzentapp/apis/add_new_enquiry.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    Toast.makeText(AddeqActivity.this,jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getApplicationContext(), ExecutiveMainActivity.class);
                    startActivity(intent);

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
                parms.put("comp_id",cmp_id);
                parms.put("us_id",userid);
                parms.put("excel_cust_mobileno",custmob.getText().toString());
                parms.put("excel_cust_name",custname.getText().toString());
                parms.put("excel_cust_businessname",busname.getText().toString());
                parms.put("excel_cust_email",emailid.getText().toString());
                parms.put("bu_cat_id",biscat);
                parms.put("follow_up_mod_id",flmod);
                parms.put("follow_up_date",datepicker.getText().toString());
                parms.put("follow_up_desc",followupDesc.getText().toString());
                parms.put("excel_cust_address",custAdd.getText().toString());


                return parms;
            }
        };

        queue.add(request);

    }

}