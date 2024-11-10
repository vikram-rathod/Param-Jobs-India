package com.app.paramjobsindia.ExecutiveActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.Adapter.ExecutiveAdapter.TodaysCmpCallListAdapter;
import com.app.paramjobsindia.Model.SelectFSModel;
import com.app.paramjobsindia.Model.TodaysCmpCallListModel;
import com.app.paramjobsindia.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

public class CmpCallListActivity extends AppCompatActivity {
    Toolbar toolbar;
    TodaysCmpCallListAdapter todaysCmpCallListAdapter;
    List<TodaysCmpCallListModel> list=new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ImageView imgRefresh,sbmtimg;
    private DatePickerDialog datePickerDialog;

    List<SelectFSModel> selectflist=new ArrayList<>();
    ArrayAdapter<SelectFSModel> selectFSModelArrayAdapter;
    Spinner spinnerf;
    Button fldatebtn;

    SearchView searchView;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME="mypref";
    private static final String Key_CMPID="cmpid";
    private static final String Key_USID="usid";
    private static final String KEY_USERTYPE="usertype";

    String cmp_id,us_id,us_type,changeddate,flstat;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmp_call_list);
        floatingActionButton=findViewById(R.id.addtodaycmpcalllist);//initialize floating button
        imgRefresh=findViewById(R.id.refreshimgcmp);//initialize floating button
        spinnerf=findViewById(R.id.spcmpcalll);
        fldatebtn=findViewById(R.id.cmpcallldatepicker);

        sbmtimg=findViewById(R.id.submitcmpcalll);
        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);

            }
        });
        //adding cuctomers by clicking + floating btn

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),AddeqActivity.class);
                startActivity(intent);

            }
        });


        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        cmp_id=sharedPreferences.getString(Key_CMPID,null);
        Log.d("cmp_id",cmp_id);
        us_id=sharedPreferences.getString(Key_USID,null);
        Log.d("usid",us_id);
        us_type=sharedPreferences.getString(KEY_USERTYPE,null);


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




        //VIEWING DATA IN RECYCLER VIEW
        recyclerView=findViewById(R.id.rvtodaycmpcalllist);//initializing recycler view
        LinearLayoutManager layoutManagerbank=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManagerbank);
         
        todaysCmpCallListAdapter=new TodaysCmpCallListAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(todaysCmpCallListAdapter);


        getCustData();


        //searchbar//
        searchView=findViewById(R.id.searchtodaycmpcalllist);
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



        sbmtimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                filterlistS(newTextS);


                Intent intent=new Intent(getApplicationContext(),FilteredCmpCallActivity.class);
                intent.putExtra("changeddate",changeddate);
                intent.putExtra("flstat",flstat);
                startActivity(intent);


            }
        });
        //toolbar with back button
        toolbar=findViewById(R.id.toolbartodaycmpcalllist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        datePickerDialog=new DatePickerDialog(CmpCallListActivity.this,style,dateSetListener,year,month,day);
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

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);


    }

    ///search tab///
    private void filterlistS(String newTextS) {

        List<TodaysCmpCallListModel> filteredListS=new ArrayList<>();

        for(TodaysCmpCallListModel listS: list){
            if(listS.getExcel_cust_name().toLowerCase().contains(newTextS.toLowerCase())){
                filteredListS.add(listS);
            }
        }
        if (filteredListS.isEmpty()){
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else{

            todaysCmpCallListAdapter.setFilteredListS(filteredListS);
        }
    }


    //fetching cust data by volley api

    private void getCustData() {

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
                        for(int i=0;i<= jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String excel_cust_id = jsonObject1.getString("excel_cust_id");
                            String user_assign_data_id = jsonObject1.getString("user_assign_data_id");
                            String excel_cust_name = jsonObject1.getString("excel_cust_name");
                            String excel_cust_mobileno = jsonObject1.getString("excel_cust_mobileno");
                            String follow_up_date = jsonObject1.getString("follow_up_date");
                            String follow_up_desc = jsonObject1.getString("follow_up_desc");
                            String follow_up_mod_name = jsonObject1.getString("follow_up_mod_name");


                            TodaysCmpCallListModel listModel = new TodaysCmpCallListModel(excel_cust_id,user_assign_data_id, excel_cust_name,
                                    excel_cust_mobileno, follow_up_date,follow_up_desc,follow_up_mod_name);

                            list.add(listModel);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "No Record Found..!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                todaysCmpCallListAdapter=new TodaysCmpCallListAdapter(getApplicationContext(),list);
                recyclerView.setAdapter(todaysCmpCallListAdapter);

                String l=String.valueOf(todaysCmpCallListAdapter.getItemCount());
                Log.d("size",l);
                todaysCmpCallListAdapter.notifyDataSetChanged();

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
                parms.put("us_id",us_id);
                parms.put("us_type",us_type);

                return parms;
            }
        };

        queue.add(request);

    }
}