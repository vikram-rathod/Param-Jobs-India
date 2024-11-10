package com.app.paramjobsindia.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.Adapter.BankJobAdapter;
import com.app.paramjobsindia.Adapter.DeliveryAssociateAdapter;
import com.app.paramjobsindia.Adapter.JobAdapter;
import com.app.paramjobsindia.Adapter.JobCatagoryAdapter;
import com.app.paramjobsindia.Adapter.OtherJobsAdapter;
import com.app.paramjobsindia.Adapter.SalesJobAdapter;
import com.app.paramjobsindia.Adapter.SoftwareJobAdapter;
import com.app.paramjobsindia.DomainName.DomainName;
import com.app.paramjobsindia.Model.BankJobModelList;
import com.app.paramjobsindia.Model.DeliveryAssociateModelList;
import com.app.paramjobsindia.Model.JobCatagoryModelList;
import com.app.paramjobsindia.Model.OtherJobsModelList;
import com.app.paramjobsindia.Model.SalesJobModelList;
import com.app.paramjobsindia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    SearchView searchView;//defining searchview

    ViewFlipper flipper;
    CardView j1,j2;
    JobAdapter adapter;
    SoftwareJobAdapter softwareJobAdapter;
//    SalesJobAdapter salesJobAdapter;
//    MarketingJobAdapter marketingJobAdapter;
//    HrJobAdapter hrJobAdapter;
//    CallingJobAdapter callingJobAdapter;
//    BankJobAdapter bankJobAdapter;


    BankJobAdapter bankJobAdapter;
    SalesJobAdapter salesJobAdapter;//sales and Marketing jobs
    DeliveryAssociateAdapter deliveryAssociateAdapter;
    OtherJobsAdapter otherJobsAdapter;

//    List<JobModelList> list=new ArrayList<>();
//    List<SoftwareJobModelList> list1=new ArrayList<>();
//    List<SalesJobModelList> list2=new ArrayList<>();
//    List<MarketingJobModelList> list3=new ArrayList<>();
//    List<HrJobModelList> list4=new ArrayList<>();
//    List<CallingJobModelList> list5=new ArrayList<>();
//    List<BankJobModelList> list6=new ArrayList<>();
    RecyclerView recyclerView,rv1,rv2,rv3,rv4,rv5,rv6,rvjabcatagory;

    List<BankJobModelList> listb=new ArrayList<>();
    List<SalesJobModelList> lists=new ArrayList<>();
    List<DeliveryAssociateModelList> listda=new ArrayList<>();
    List<OtherJobsModelList> listo=new ArrayList<>();

    RecyclerView rvbank,rvsale,rvdelivery,rvother;


    JobCatagoryAdapter adapterjc;//defining adpater
    RequestQueue requestQueue;//defining request for volley get
    List<JobCatagoryModelList> itemlist=new ArrayList<>();//initializing model arraylist
    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);


        rvjabcatagory=view.findViewById(R.id.jobcatagorydetailjobbb);//initializing recycler view



        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),4,GridLayoutManager.VERTICAL,false);
        rvjabcatagory.setLayoutManager(gridLayoutManager);
//        adapter=new JobCatagoryAdapter(getContext(),Myitemlist);
//        rvjabcatagory.setAdapter(adapter);
        adapterjc=new JobCatagoryAdapter(getActivity(),itemlist);
        rvjabcatagory.setAdapter(adapter);
        getData();//function used for volley json data fetch



        //auto sliding images
        flipper=view.findViewById(R.id.flipper);
        int imgarray[]={R.drawable.joba,R.drawable.jobb,R.drawable.jobc};

        for(int i=0;i<imgarray.length;i++)
            showimage(imgarray[i]);

        //sliding image end//


        //bank job data in adapter
        rvbank=view.findViewById(R.id.rvbank);//initializing recycler view
        LinearLayoutManager layoutManagerbank=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvbank.setLayoutManager(layoutManagerbank);
        bankJobAdapter=new BankJobAdapter(getContext(),listb);
        rvbank.setAdapter(bankJobAdapter);
        getBankJobData();//function used for volley json data fetch

        //sales job data in adapter
        rvsale=view.findViewById(R.id.rvsale);//initializing recycler view
        LinearLayoutManager layoutManagersales=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvsale.setLayoutManager(layoutManagersales);
        salesJobAdapter=new SalesJobAdapter(getContext(),lists);
        rvsale.setAdapter(salesJobAdapter);
        getSalesJobData();//function used for volley json data fetch

        //DeliveryAssociate job data in adapter
        rvdelivery=view.findViewById(R.id.rvda);//initializing recycler view
        LinearLayoutManager layoutManagerdelivery=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvdelivery.setLayoutManager(layoutManagerdelivery);
        deliveryAssociateAdapter=new DeliveryAssociateAdapter(getContext(),listda);
        rvdelivery.setAdapter(deliveryAssociateAdapter);
        getDeliveryJobData();//function used for volley json data fetch

        //Other  job data in adapter
        rvother=view.findViewById(R.id.rvo);//initializing recycler view
        LinearLayoutManager layoutManagerother=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvother.setLayoutManager(layoutManagerother);
        otherJobsAdapter=new OtherJobsAdapter(getContext(),listo);
        rvother.setAdapter(otherJobsAdapter);
        getOtherJobData();//function used for volley json data fetch


        return view;
    }

    private void showimage(int img) {

        ImageView imageView=new ImageView(getContext());
        imageView.setBackgroundResource(img);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
        flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);

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




    private void getData() {

        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();

        requestQueue= Volley.newRequestQueue(getActivity());
        String url=d+"candidate_info/get_job_categories.php";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray=response.getJSONArray("job_cat");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("bu_cat_id");
                        String name = jsonObject.getString("bu_cat_name");
                        String count = jsonObject.getString("bu_cat_count");

                        JobCatagoryModelList pModel = new JobCatagoryModelList(id,name,count);
                        itemlist.add(pModel);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                adapterjc=new JobCatagoryAdapter(getActivity(),itemlist);
                rvjabcatagory.setAdapter(adapterjc);
                adapterjc.notifyDataSetChanged();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

}
