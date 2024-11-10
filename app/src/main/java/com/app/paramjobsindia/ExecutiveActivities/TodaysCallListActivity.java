package com.app.paramjobsindia.ExecutiveActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.app.paramjobsindia.Adapter.ExecutiveAdapter.TodaysCallListAdapter;
import com.app.paramjobsindia.Model.SelectFSModel;
import com.app.paramjobsindia.Model.TodaysCallListModel;
import com.app.paramjobsindia.R;
import com.app.paramjobsindia.databinding.ActivityTodaysCallListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TodaysCallListActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;

    Toolbar toolbar;
    TodaysCallListAdapter todaysCallListAdapter;
    List<TodaysCallListModel> list=new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    List<SelectFSModel> selectflist=new ArrayList<>();
    ArrayAdapter<SelectFSModel> selectFSModelArrayAdapter;
    Button fldatebtn;
    ImageView imgRefresh;
    SearchView searchView;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME="mypref";
    private static final String Key_CMPID="cmpid";
    private static final String Key_USID="usid";
    private static final String KEY_USERTYPE="usertype";
    String cmp_id,us_id,us_type,flstat,changeddate;
    Spinner spinnerf;



    ActivityTodaysCallListBinding todaysCallListBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todaysCallListBinding=ActivityTodaysCallListBinding.inflate(getLayoutInflater());
        setContentView(todaysCallListBinding.getRoot());

        todaysCallListBinding.refreshimg.setOnClickListener(v -> {
            finish();
            overridePendingTransition( 0, 0);
            startActivity(getIntent());
            overridePendingTransition( 0, 0);


        });

        //adding cuctomers by clicking + floating btn

        todaysCallListBinding.floatingbtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),AddeqActivity.class));
        });


        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        cmp_id=sharedPreferences.getString(Key_CMPID,null);
        us_id=sharedPreferences.getString(Key_USID,null);
        us_type=sharedPreferences.getString(KEY_USERTYPE,null);

        Log.d("ustype",us_type);

        //VIEWING DATA IN RECYCLER VIEW
        LinearLayoutManager layoutManagerbank=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        todaysCallListBinding.recyclerView.setLayoutManager(layoutManagerbank);
        todaysCallListAdapter=new TodaysCallListAdapter(getApplicationContext(),list);
        todaysCallListBinding.recyclerView.setAdapter(todaysCallListAdapter);


        getCustData();


        //searchbar//
        todaysCallListBinding.searchbar.clearFocus();
        todaysCallListBinding.searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        setSupportActionBar(todaysCallListBinding.toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        List<TodaysCallListModel> filteredListS=new ArrayList<>();

        for(TodaysCallListModel listS: list){
            if(listS.getExcel_cust_name().toLowerCase().contains(newTextS.toLowerCase())){
                filteredListS.add(listS);
            }
        }
        if (filteredListS.isEmpty()){
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else{

            todaysCallListAdapter.setFilteredListS(filteredListS);
        }
    }


    //fetching cust data by volley api
    private void getCustData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/comzentapp/apis/get_today_call_list.php";


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

                            String user_assign_data_id = jsonObject1.getString("user_assign_data_id");
                            String excel_cust_id = jsonObject1.getString("excel_cust_id");
                            String excel_cust_name = jsonObject1.getString("excel_cust_name");
                            String excel_cust_mobileno = jsonObject1.getString("excel_cust_mobileno");
                            String business_cat_name = jsonObject1.getString("business_cat_name");


                            TodaysCallListModel listModel = new TodaysCallListModel(user_assign_data_id,excel_cust_id, excel_cust_name,
                                    excel_cust_mobileno, business_cat_name);

                            list.add(listModel);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Wrong Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                todaysCallListAdapter=new TodaysCallListAdapter(getApplicationContext(),list);
                recyclerView.setAdapter(todaysCallListAdapter);

                String l=String.valueOf(todaysCallListAdapter.getItemCount());
                Log.d("size",l);
                todaysCallListAdapter.notifyDataSetChanged();

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
//                parms.put("follow_up_date",changeddate);
//                parms.put("follow_up_mod_id",flstat);

                return parms;
            }
        };

        queue.add(request);

    }

}