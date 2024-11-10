package com.app.paramjobsindia.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.Adapter.JobCatagoryAdapter;
import com.app.paramjobsindia.DomainName.DomainName;
import com.app.paramjobsindia.Model.JobCatagoryModelList;
import com.app.paramjobsindia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CatagoryFragment extends Fragment {

    JobCatagoryAdapter adapter;//defining adpater
    RequestQueue requestQueue;//defining request for volley get
    List<JobCatagoryModelList> itemlist=new ArrayList<>();//initializing model arraylist
    RecyclerView recyclerv;//defining recyclerview

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.catagory_fragment, container, false);
        recyclerv=view.findViewById(R.id.recyclerView);
//gridlayout manager used for dispaying data in grid view
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),4,GridLayoutManager.VERTICAL,false);
        recyclerv.setLayoutManager(gridLayoutManager);
//        adapter=new JobCatagoryAdapter(getContext(),Myitemlist);
//        recyclerv.setAdapter(adapter);
        adapter=new JobCatagoryAdapter(getActivity(),itemlist);
        recyclerv.setAdapter(adapter);
        getData();//function used for volley json data fetch

        return view;
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
                adapter=new JobCatagoryAdapter(getActivity(),itemlist);
                recyclerv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
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
