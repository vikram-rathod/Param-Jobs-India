package com.app.paramjobsindia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.paramjobsindia.ChangeJbStatusActivity;
import com.app.paramjobsindia.Model.AppliedJobcmpnyModelList;
import com.app.paramjobsindia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppliedJobCmpnyAdapter extends RecyclerView.Adapter<AppliedJobCmpnyAdapter.ViewHolder> {

    Context context;
    List<AppliedJobcmpnyModelList> itemlist;



    public AppliedJobCmpnyAdapter(Context context, List<AppliedJobcmpnyModelList> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
//

    }

    //search bar//
    public void setFilteredListS(List<AppliedJobcmpnyModelList> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    //setting layout for recyclerview//
    @NonNull
    @Override
    public AppliedJobCmpnyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_layout_recycler_cmpnyappliedjobs,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppliedJobCmpnyAdapter.ViewHolder holder, int position) {

        holder.txtsrno.setText(""+(position+1));
        if(itemlist.get(position).getCandidate_name()=="null"){

            holder.txtcndname.setText("");
        }else {
            holder.txtcndname.setText(itemlist.get(position).getCandidate_name());

        }


        if(itemlist.get(position).getJob_salary()=="null"){

            holder.txtsalary.setText("");
        }else {
            holder.txtsalary.setText("₹"+itemlist.get(position).getJob_salary()+"/-");

        }

        if(itemlist.get(position).getGet_compname()=="null"){

            holder.txtcmpname.setText("");
        }else {
            holder.txtcmpname.setText(itemlist.get(position).getGet_compname());

        }

        if(itemlist.get(position).getCandidate_number()=="null"){

            holder.txtcndnumber.setText("");
        }else {
            holder.txtcndnumber.setText(itemlist.get(position).getCandidate_number());

        }

        if(itemlist.get(position).getCandidate_email_id()=="null"){

            holder.txtemailid.setText("");
        }else {
            holder.txtemailid.setText(itemlist.get(position).getCandidate_email_id());

        }

        if(itemlist.get(position).getCandidate_education()=="null"){

            holder.txtcndedu.setText("");
        }else {
            holder.txtcndedu.setText(itemlist.get(position).getCandidate_education());

        }


        if(itemlist.get(position).getCandidate_exprince()=="null"){

            holder.txtcndexp.setText("");
        }else {
            holder.txtcndexp.setText(itemlist.get(position).getCandidate_exprince());

        }

        if(itemlist.get(position).getJob_education()=="null"){

            holder.txtjbedu.setText("");
        }else {
            holder.txtjbedu.setText(itemlist.get(position).getJob_education());

        }

        if(itemlist.get(position).getJob_interview_time()=="null"){

            holder.txtintim.setText("");
        }else {

            String d=itemlist.get(position).getJob_interview_time();

            Log.d("time",itemlist.get(position).getJob_interview_time());
            SimpleDateFormat format = new SimpleDateFormat("KK:mm:ss");
            SimpleDateFormat sdf=new SimpleDateFormat("hh.mm a");
            //String datestring=sdf.format(d);

            try {
                Date date = format.parse(d);
                holder.txtintim.setText(sdf.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        if(itemlist.get(position).getJob_title()=="null"){

            holder.txtjbtitle.setText("");
        }else {
            holder.txtjbtitle.setText(itemlist.get(position).getJob_title());

        }
        if(itemlist.get(position).getCandidate_post_applied_status()=="null"){

            holder.txtivstat.setText("");
        }else {
            holder.txtivstat.setText(itemlist.get(position).getCandidate_post_applied_status());

        }

        String d=itemlist.get(position).getJob_interview_date().toString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        //String datestring=sdf.format(d);

        try {
            Date date = format.parse(d);
            holder.txtindate.setText(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }


//
        String jobid=itemlist.get(position).getJob_id();

        Log.d("jobiiiid",itemlist.get(position).getJob_id());
        String candid=itemlist.get(position).getCandidate_id();


        //spinner selection of item api fetch



            holder.change_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent=new Intent(context.getApplicationContext(), ChangeJbStatusActivity.class);
                    intent.putExtra("jobId",jobid);
                    intent.putExtra("candId",candid);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                }
            });

        }

    //end of spinner selection of item api fetch




    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtcndname, txtjbcardno,txtcndnumber,txtemailid,txtcndadd,txtcndedu,txtcndexp,
                txtcmpname,txtcmpid,txtcndqual,txtjbtype,txtsalary,txtjbedu,txtlocation,txtindate,txtintim,
                txtjbopening,txtsrno,txtjbtitle,txtivstat;

        Button change_status;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtcndname = itemView.findViewById(R.id.cndname);

            txtcndnumber = itemView.findViewById(R.id.cndno);
            txtemailid = itemView.findViewById(R.id.cndemailid);
            txtsrno = itemView.findViewById(R.id.srno);

            txtcndedu = itemView.findViewById(R.id.cndedu);
            txtcndexp = itemView.findViewById(R.id.cndexp);
            txtcmpname = itemView.findViewById(R.id.cndgetcmpname);
//            txtcmpid = itemView.findViewById(R.id.cmpid);
//            txtcndqual = itemView.findViewById(R.id.cndqual);
//            txtjbtype = itemView.findViewById(R.id.cndjobtyp);
            txtsalary = itemView.findViewById(R.id.cndjbsalary);
            txtjbedu = itemView.findViewById(R.id.cndjbedu);
//            txtlocation = itemView.findViewById(R.id.cndjblocation);
            txtindate = itemView.findViewById(R.id.cndindate);
            txtintim = itemView.findViewById(R.id.cndintm);
//            txtjbopening = itemView.findViewById(R.id.cndopenings);
            txtjbtitle = itemView.findViewById(R.id.cndjbtitle);
            txtivstat = itemView.findViewById(R.id.cndintstatus);
            change_status = itemView.findViewById(R.id.changejbstatustxt);


        }
    }
}

