package com.app.paramjobsindia.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.Adapter.BankJobAdapter;
import com.app.paramjobsindia.Adapter.DeliveryAssociateAdapter;
import com.app.paramjobsindia.Adapter.JobAdapter;
import com.app.paramjobsindia.Adapter.OtherJobsAdapter;
import com.app.paramjobsindia.Adapter.SalesJobAdapter;
import com.app.paramjobsindia.Adapter.SoftwareJobAdapter;
import com.app.paramjobsindia.DomainName.DomainName;
import com.app.paramjobsindia.Model.BankJobModelList;
import com.app.paramjobsindia.Model.DeliveryAssociateModelList;
import com.app.paramjobsindia.Model.OtherJobsModelList;
import com.app.paramjobsindia.Model.SalesJobModelList;
import com.app.paramjobsindia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JobsFragment extends Fragment {

    SearchView searchView;
    JobAdapter adapter;
    SoftwareJobAdapter softwareJobAdapter;

    BankJobAdapter bankJobAdapter;
    SalesJobAdapter salesJobAdapter;//sales and Marketing jobs
    DeliveryAssociateAdapter deliveryAssociateAdapter;
    OtherJobsAdapter otherJobsAdapter;

    List<BankJobModelList> listb=new ArrayList<>();
    List<SalesJobModelList> lists=new ArrayList<>();
    List<DeliveryAssociateModelList> listda=new ArrayList<>();
    List<OtherJobsModelList> listo=new ArrayList<>();

    RecyclerView rvbank,rvsale,rvdelivery,rvother;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jobs_fragment, container, false);

        searchView=view.findViewById(R.id.searchBarjob);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterlistS(newText);
                return true;
            }
        });

        //bank job data in adapter
        rvbank=view.findViewById(R.id.rv6jobsbank);//initializing recycler view
        LinearLayoutManager layoutManagerbank=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvbank.setLayoutManager(layoutManagerbank);
        bankJobAdapter=new BankJobAdapter(getContext(),listb);
        rvbank.setAdapter(bankJobAdapter);
        getBankJobData();//function used for volley json data fetch

        //sales job data in adapter
        rvsale=view.findViewById(R.id.rvjobsales);//initializing recycler view
        LinearLayoutManager layoutManagersales=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvsale.setLayoutManager(layoutManagersales);
        salesJobAdapter=new SalesJobAdapter(getContext(),lists);
        rvsale.setAdapter(salesJobAdapter);
        getSalesJobData();//function used for volley json data fetch

        //DeliveryAssociate job data in adapter
        rvdelivery=view.findViewById(R.id.rvjobdiliverya);//initializing recycler view
        LinearLayoutManager layoutManagerdelivery=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvdelivery.setLayoutManager(layoutManagerdelivery);
        deliveryAssociateAdapter=new DeliveryAssociateAdapter(getContext(),listda);
        rvdelivery.setAdapter(deliveryAssociateAdapter);
        getDeliveryJobData();//function used for volley json data fetch

        //Other  job data in adapter
        rvother=view.findViewById(R.id.rvjobother);//initializing recycler view
        LinearLayoutManager layoutManagerother=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvother.setLayoutManager(layoutManagerother);
        otherJobsAdapter=new OtherJobsAdapter(getContext(),listo);
        rvother.setAdapter(otherJobsAdapter);
        getOtherJobData();//function used for volley json data fetch

        return view;
    }

    ///search tab///
    private void filterlistS(String newTextS) {

        List<BankJobModelList> filteredListS=new ArrayList<>();
        List<SalesJobModelList> filteredListSale=new ArrayList<>();
        List<DeliveryAssociateModelList> filteredListD=new ArrayList<>();
        List<OtherJobsModelList> filteredListOther=new ArrayList<>();

        for(BankJobModelList listS: listb){
            if(listS.getJob_title().toLowerCase().contains(newTextS.toLowerCase())){
                filteredListS.add(listS);
            }
        }
        if (filteredListS.isEmpty()){
            rvbank.setVisibility(View.GONE);
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else{

            bankJobAdapter.setFilteredListS(filteredListS);
        }
//sales search
        for(SalesJobModelList listSale: lists){
            if(listSale.getJob_title().toLowerCase().contains(newTextS.toLowerCase())){
                filteredListSale.add(listSale);
            }
        }
        if (filteredListSale.isEmpty()){
            rvsale.setVisibility(View.GONE);
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else{

            salesJobAdapter.setFilteredListS(filteredListSale);
        }

        //Delivery search
        for(DeliveryAssociateModelList listD: listda){
            if(listD.getJob_title().toLowerCase().contains(newTextS.toLowerCase())){
                filteredListD.add(listD);
            }
        }
        if (filteredListD.isEmpty()){
            rvdelivery.setVisibility(View.GONE);
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else{

            deliveryAssociateAdapter.setFilteredListS(filteredListD);
        }

        //Other search
        for(OtherJobsModelList listO: listo){
            if(listO.getJob_title().toLowerCase().contains(newTextS.toLowerCase())){
                filteredListOther.add(listO);
            }
        }
        if (filteredListOther.isEmpty()){
            rvother.setVisibility(View.GONE);
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else{

            otherJobsAdapter.setFilteredListS(filteredListOther);
        }
    }


    //api fetching for Bank jobs
    private void getBankJobData() {

        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url1=d+"candidate_info/get_jobs_in_bank.php";

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

                    JSONArray jsonArray = respObj.getJSONArray("bank_jobs");

                    Log.d("class details", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String job_cat_name = jsonObject.getString("job_cat_name");
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
                        String pro_img = jsonObject.getString("pro_img");

                        BankJobModelList listModel = new BankJobModelList(job_cat_name, job_id,
                                job_title, bu_cat_id, get_compname, company_id,qualification_namw,job_type
                                ,job_salary,job_education,job_location,job_note,job_interview_date
                                ,job_interview_time,job_opening,pro_img);
                        listb.add(listModel);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                bankJobAdapter=new BankJobAdapter(getContext(),listb);
                rvbank.setAdapter(bankJobAdapter);
                bankJobAdapter.notifyDataSetChanged();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(request);


    }
    private void getSalesJobData() {

        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();


        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url1=d+"candidate_info/get_jobs_in_salesandmarketing.php";

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

                    JSONArray jsonArray = respObj.getJSONArray("salesandmarketing_jobs");

                    Log.d("class details", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String job_cat_name = jsonObject.getString("job_cat_name");
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
                        String pro_img = jsonObject.getString("pro_img");

                        SalesJobModelList listModel = new SalesJobModelList(job_cat_name, job_id,
                                job_title, bu_cat_id, get_compname, company_id,qualification_namw,job_type
                                ,job_salary,job_education,job_location,job_note,job_interview_date
                                ,job_interview_time,job_opening,pro_img);
                        lists.add(listModel);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                salesJobAdapter=new SalesJobAdapter(getContext(),lists);
                rvsale.setAdapter(salesJobAdapter);
                salesJobAdapter.notifyDataSetChanged();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(request);


    }
    private void getDeliveryJobData() {

        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url1=d+"candidate_info/get_jobs_in_delivery_associate.php";

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

                    JSONArray jsonArray = respObj.getJSONArray("da_jobs");

                    Log.d("class details", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String job_cat_name = jsonObject.getString("job_cat_name");
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
                        String pro_img = jsonObject.getString("pro_img");

                        DeliveryAssociateModelList listModel = new DeliveryAssociateModelList(job_cat_name, job_id,
                                job_title, bu_cat_id, get_compname, company_id,qualification_namw,job_type
                                ,job_salary,job_education,job_location,job_note,job_interview_date
                                ,job_interview_time,job_opening,pro_img);
                        listda.add(listModel);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                deliveryAssociateAdapter=new DeliveryAssociateAdapter(getContext(),listda);
                rvdelivery.setAdapter(deliveryAssociateAdapter);
                deliveryAssociateAdapter.notifyDataSetChanged();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(request);


    }
    private void getOtherJobData() {

        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();


        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url1=d+"candidate_info/get_jobs_in_other.php";

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

                    JSONArray jsonArray = respObj.getJSONArray("other_jobs");

                    Log.d("class details", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String job_cat_name = jsonObject.getString("job_cat_name");
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
                        String pro_img = jsonObject.getString("pro_img");

                        OtherJobsModelList listModel = new OtherJobsModelList(job_cat_name, job_id,
                                job_title, bu_cat_id, get_compname, company_id,qualification_namw,job_type
                                ,job_salary,job_education,job_location,job_note,job_interview_date
                                ,job_interview_time,job_opening,pro_img);
                        listo.add(listModel);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                otherJobsAdapter=new OtherJobsAdapter(getContext(),listo);
                rvother.setAdapter(otherJobsAdapter);
                otherJobsAdapter.notifyDataSetChanged();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(request);


    }

}
