package com.app.paramjobsindia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.paramjobsindia.Model.AppliedJobModelList;
import com.app.paramjobsindia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppliedJobAdapter extends RecyclerView.Adapter<AppliedJobAdapter.ViewHolder> {

    Context context;
    List<AppliedJobModelList> itemlist;

    public AppliedJobAdapter(Context context, List<AppliedJobModelList> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    //search bar//
    public void setFilteredListS(List<AppliedJobModelList> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    //setting layout for recyclerview//
    @NonNull
    @Override
    public AppliedJobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_layout_recycler_appliedjobs,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppliedJobAdapter.ViewHolder holder, int position) {
        holder.txtsrno.setText(""+(position+1));

        if(itemlist.get(position).getJob_title()=="null"){

            holder.txttitle.setText("");
        }else {
            holder.txttitle.setText(itemlist.get(position).getJob_title());

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
        if(itemlist.get(position).getJob_location()=="null"){

            holder.txtcity.setText("");
        }else {
            holder.txtcity.setText(itemlist.get(position).getJob_location());

        }
        if(itemlist.get(position).getJob_title()=="null"){

            holder.txtedu.setText("");
        }else {
            holder.txtedu.setText((itemlist.get(position).getJob_education()));

        }
        if(itemlist.get(position).getJob_title()=="null"){

            holder.txtjtype.setText("");
        }else {
            holder.txtjtype.setText(itemlist.get(position).getJob_type());

        }
        if(itemlist.get(position).getJob_title()=="null"){

            holder.txtjopening.setText("");
        }else {
            holder.txtjopening.setText(itemlist.get(position).getJob_opening());

        }


        if(itemlist.get(position).getCandidate_post_applied_status()=="null"){

            holder.txtstatus.setText("");
        }else {
            holder.txtstatus.setText(itemlist.get(position).getCandidate_post_applied_status());

        }

        String d=itemlist.get(position).getJob_interview_date().toString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        //String datestring=sdf.format(d);

        try {
            Date date = format.parse(d);
            holder.txtdate.setText(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtsrno,txttitle, txtsalary,txtcmpname,txtcity,txtedu,txtdate,txtjtype,
                txtjopening,txtstatus;
        Button txtbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtsrno = itemView.findViewById(R.id.srnoapp);

            txttitle = itemView.findViewById(R.id.titlejob);
            txtsalary = itemView.findViewById(R.id.salaryjob);
            txtcmpname = itemView.findViewById(R.id.cmpnamejob);
            txtcity = itemView.findViewById(R.id.ctynamejob);
            txtedu = itemView.findViewById(R.id.edujob);
            txtdate = itemView.findViewById(R.id.datejob);
            txtjtype = itemView.findViewById(R.id.jobtype);
            txtjopening = itemView.findViewById(R.id.openingsjob);
            txtstatus = itemView.findViewById(R.id.appstatusjb);



        }
    }
}

