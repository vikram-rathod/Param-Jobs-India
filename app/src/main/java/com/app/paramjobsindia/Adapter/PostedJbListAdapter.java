package com.app.paramjobsindia.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.DomainName.DomainName;
import com.app.paramjobsindia.Model.PostedJbListModel;
import com.app.paramjobsindia.R;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostedJbListAdapter extends RecyclerView.Adapter<PostedJbListAdapter.ViewHolder> {
    String st, jb_status,jbId;
    Context context;
    List<PostedJbListModel> itemlist;

        public boolean Selected = false;
        public String Text;


    public PostedJbListAdapter(Context context, List<PostedJbListModel> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    //search bar//
    public void setFilteredListS(List<PostedJbListModel> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    //setting layout for recyclerview//
    @NonNull
    @Override
    public PostedJbListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item_postedjblist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostedJbListAdapter.ViewHolder holder, int position) {

        holder.txtsrno.setText(""+(position+1));

        if(itemlist.get(position).getJob_cat_name()!=null){

            holder.txtjob_cat_name.setText(itemlist.get(position).getJob_cat_name());
        }
        else {
            holder.txtjob_cat_name.setText("");
        }

        if(itemlist.get(position).getJob_title()=="null"){
            holder.txtjob_title.setText("");
        }
        else {
            holder.txtjob_title.setText(itemlist.get(position).getJob_title());
        }

        if(itemlist.get(position).getGet_compname()=="null"){
            holder.txtcmp_name.setText("");
        }
        else {
            holder.txtcmp_name.setText(itemlist.get(position).getGet_compname());
        }

        if(itemlist.get(position).getJob_interview_date()=="null"){
            holder.txtiv_date.setText("");
        }
        else {


            String d=itemlist.get(position).getJob_interview_date();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
            //String datestring=sdf.format(d);

            try {
                Date date = format.parse(d);
                holder.txtiv_date.setText(sdf.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }




        if(itemlist.get(position).getQualification_namw()=="null"){
            holder.txtqualification.setText("");
        }
        else {
            holder.txtqualification.setText(itemlist.get(position).getQualification_namw());
        }
        if(itemlist.get(position).getJob_type()=="null"){
            holder.txtjb_type.setText("");
        }
        else {
            holder.txtjb_type.setText(itemlist.get(position).getJob_type());
        }

        if(itemlist.get(position).getJob_salary()=="null"){
            holder.txtjb_salary.setText("");
        }
        else {

            holder.txtjb_salary.setText("₹"+itemlist.get(position).getJob_salary().toString()+"/-");
        }
        if(itemlist.get(position).getJob_education()=="null"){
            holder.txtjb_education.setText("");
        }
        else {

            holder.txtjb_education.setText(Html.fromHtml(itemlist.get(position).getJob_education()).toString());
        }
        if(itemlist.get(position).getJob_location()=="null"){
            holder.txtjb_location.setText("");
        }
        else {

            holder.txtjb_location.setText(Html.fromHtml(itemlist.get(position).getJob_location()).toString());
        }
        if(itemlist.get(position).getJob_note()=="null"){
            holder.txtjb_note.setText("");
        }
        else {

            holder.txtjb_note.setText(Html.fromHtml(itemlist.get(position).getJob_note()).toString());
        }



        if(itemlist.get(position).getJob_interview_time()=="null"){
            holder.txtiv_time.setText("");
        }
        else {


            String d=itemlist.get(position).getJob_interview_time();

            SimpleDateFormat format = new SimpleDateFormat("KK:mm:ss");
            SimpleDateFormat sdf=new SimpleDateFormat("KK:mm:ss aaa");
            //String datestring=sdf.format(d);

            try {
                Date date = format.parse(d);
                holder.txtiv_time.setText(sdf.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        if(itemlist.get(position).getJob_opening()=="null"){
            holder.txtopenings.setText("");
        }
        else {

            holder.txtopenings.setText(Html.fromHtml(itemlist.get(position).getJob_opening()).toString());
        }

        if(itemlist.get(position).getJobpost_status()=="null"){
            holder.txtstatus.setText("");
        }
        else {

           st=itemlist.get(position).getJobpost_status();

            if(st.equals("De-Active")){

                holder.toggleButton.setBackgroundColor(Color.RED);
                holder.txtstatus.setText(itemlist.get(position).getJobpost_status());
                holder.txtstatus.setBackgroundColor(Color.RED);

            }else if(st.equals("Active")){
                holder.toggleButton.setBackgroundColor(Color.parseColor("#2BB793"));
                holder.txtstatus.setText(st);
                holder.txtstatus.setBackgroundColor(Color.parseColor("#2BB793"));
            }
        }
        //image setting
        Glide.with(context)
                .load(itemlist.get(position).getPro_img())
                .placeholder(R.drawable.noimage)
                .into(holder.img);

        Log.d("img",itemlist.get(position).getPro_img());

        jbId=itemlist.get(position).getJob_id();

        //toggle btn for changing job status
        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    holder.toggleButton.setBackgroundColor(Color.parseColor("#2BB793"));//green color
                    holder.toggleButton.setText("Is Activated");
                    holder.txtstatus.setText("Active");
                    holder.txtstatus.setBackgroundColor(Color.parseColor("#2BB793"));
                    jb_status="1";


//                    Toast.makeText(context, " The toggle is enabled", Toast.LENGTH_SHORT).show();// The toggle is enabled

                }else{
                    holder.toggleButton.setBackgroundColor(Color.RED);
                    holder.toggleButton.setText("Is Deactivated");
                    holder.txtstatus.setText("Deactive");
                    holder.txtstatus.setBackgroundColor(Color.RED);
                    jb_status="0";
//                    Toast.makeText(context, "The toggle is disabled", Toast.LENGTH_SHORT).show();// The toggle is disabled

                }

                getCustData();//changing job status
            }

        });

        if(st.equals("De-Active")){
            holder.toggleButton.setChecked(false);


        }else if(st.equals("Active")){
            holder.toggleButton.setChecked(true);

        }

    }

//changing job status of server data

    private void getCustData() {



        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();
        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(context.getApplicationContext());
        String url=d+"candidate_info/company_post_status_change.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");


                    Log.d("message",msg);
//                    Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(context.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("job_id",jbId);
                parms.put("job_status",jb_status);


                return parms;
            }
        };

        queue.add(request);

    }


    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtsrno,txtjob_cat_name, txtjob_title,txtcmp_name,txtqualification,txtjb_type,
                txtjb_salary,txtjb_education,txtjb_location,
                txtjb_note, txtiv_date,txtiv_time,txtopenings,txtstatus;

        ImageView img;
        Switch statuschangSwitch;
        ToggleButton toggleButton;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtsrno = itemView.findViewById(R.id.srnopostjb);

            toggleButton= (ToggleButton)itemView.findViewById(R.id.toggleButton);

            textView = (TextView)itemView.findViewById(R.id.textView);
            txtjob_cat_name = itemView.findViewById(R.id.postjbcatname);
            txtjob_title = itemView.findViewById(R.id.postjbjbtitle);
            txtcmp_name = itemView.findViewById(R.id.postjbcmpname);
            txtqualification = itemView.findViewById(R.id.postjbqual);
            txtjb_type = itemView.findViewById(R.id.postjbjbtp);
            txtjb_salary = itemView.findViewById(R.id.postjbjbsal);
            txtjb_education = itemView.findViewById(R.id.postjbedu);
            txtjb_location = itemView.findViewById(R.id.postjblc);
            txtjb_note = itemView.findViewById(R.id.postjbnt);
            txtiv_date = itemView.findViewById(R.id.postjbivdt);
            txtiv_time = itemView.findViewById(R.id.postjbivtm);
            txtopenings = itemView.findViewById(R.id.postjbopn);
            txtstatus = itemView.findViewById(R.id.postjbstatus);
            img = itemView.findViewById(R.id.postjbimg);

        }
    }
}

