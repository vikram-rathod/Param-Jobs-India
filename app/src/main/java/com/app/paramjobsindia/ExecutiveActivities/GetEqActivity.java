package com.app.paramjobsindia.ExecutiveActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.Adapter.ExecutiveAdapter.EqAdapter;
import com.app.paramjobsindia.Model.EqModelList;
import com.app.paramjobsindia.databinding.ActivityGetEqBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GetEqActivity extends AppCompatActivity {
    EqAdapter eqAdapter;
    List<EqModelList> list=new ArrayList<>();
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME="mypref";
    private static final String Key_CMPID="cmpid";
    private static final String Key_USID="usid";
    private static final String KEY_USERTYPE="usertype";
    String cmp_id,us_id,us_type;

    ActivityGetEqBinding getEqBinding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getEqBinding=ActivityGetEqBinding.inflate(getLayoutInflater());
        setContentView(getEqBinding.getRoot());

        //adding cuctomers by clicking + floating btn

        getEqBinding.floating.setOnClickListener(v ->  {


                Intent intent=new Intent(getApplicationContext(),AddeqActivity.class);
                startActivity(intent);


        });

        //defining shareprefference for getting data from it.
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        cmp_id=sharedPreferences.getString(Key_CMPID,null);
        us_id=sharedPreferences.getString(Key_USID,null);
        us_type=sharedPreferences.getString(KEY_USERTYPE,null);

        //searchbar//
        getEqBinding.searchview.clearFocus();
        getEqBinding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        //auto sliding images
        LinearLayoutManager layoutManagerbank=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        getEqBinding.recyclerView.setLayoutManager(layoutManagerbank);
        eqAdapter=new EqAdapter(getApplicationContext(),list);
        getEqBinding.recyclerView.setAdapter(eqAdapter);
        getBankJobData();//function used for volley json data fetch

        //toolbar with back button
        setSupportActionBar(getEqBinding.toolBar);
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

        List<EqModelList> filteredListS=new ArrayList<>();

        for(EqModelList listS: list){
            if(listS.getExcel_cust_name().toLowerCase().contains(newTextS.toLowerCase())){
                filteredListS.add(listS);
            }
        }
        if (filteredListS.isEmpty()){
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else{

            eqAdapter.setFilteredListS(filteredListS);
        }
    }

    private void getBankJobData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1="https://comzent.in/comzentapp/apis/get_enquiry_list.php";

        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                Log.d("ss",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    String dd=respObj.getString("todayhwmessage");
                    Log.d("msg",dd);

                    if(dd.equals("Success")){
                        JSONArray jsonArray = respObj.getJSONArray("cust_info11");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String excelcustid = jsonObject.getString("excel_cust_id");
                            String excelcustname = jsonObject.getString("excel_cust_name");
                            String excelcustmobileno = jsonObject.getString("excel_cust_mobileno");

//                            String excelcustmobileno = jsonObject.getString("excel_cust_mobileno");
                            String followupdate = jsonObject.getString("follow_up_date");
                            String followupdesc = jsonObject.getString("follow_up_desc");
                            String followupmodname = jsonObject.getString("follow_up_mod_name");

                            EqModelList listModel = new EqModelList(excelcustid, excelcustname,
                                    excelcustmobileno,followupdate,followupdesc,followupmodname);
                            list.add(listModel);

                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                eqAdapter=new EqAdapter(getApplicationContext(),list);
                recyclerView.setAdapter(eqAdapter);
                eqAdapter.notifyDataSetChanged();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parms = new HashMap<>();
                parms.put("comp_id", cmp_id);
                parms.put("user_type", us_type);
                parms.put("us_id", us_id);


                return parms;
            }
        };

        queue.add(request);


    }
}