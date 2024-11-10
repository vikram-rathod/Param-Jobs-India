package com.app.paramjobsindia.Adapter.ExecutiveAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.paramjobsindia.Model.TodaysCallListModel;
import com.app.paramjobsindia.R;

import java.util.List;

public class TodaysCallListAdapter extends RecyclerView.Adapter<TodaysCallListAdapter.ViewHolder> {

    Context context;
    List<TodaysCallListModel> itemlist;

    public TodaysCallListAdapter( Context context,List<TodaysCallListModel> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    //search bar//
    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredListS(List<TodaysCallListModel> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodaysCallListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item_todayscalllist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodaysCallListAdapter.ViewHolder holder, int position) {
        holder.txtsr.setText(""+(position+1));

        if(itemlist.get(position).getExcel_cust_name()!=null){

            holder.txtcustN.setText(itemlist.get(position).getExcel_cust_name());
        }
        else {
            holder.txtcustN.setText("");
        }

        if(itemlist.get(position).getExcel_cust_mobileno()=="null"){
            holder.txtcustMobN.setText("");
        }
        else {
            holder.txtcustMobN.setText(itemlist.get(position).getExcel_cust_mobileno());
        }

        if(itemlist.get(position).getBusiness_cat_name()=="null"){
            holder.txtcatname.setText("");
        }
        else {
            holder.txtcatname.setText(itemlist.get(position).getBusiness_cat_name());
        }


        String mn,custName,fDate,fDesc,custId,user_assign_data_id;
        mn=itemlist.get(position).getExcel_cust_mobileno();
        custName=itemlist.get(position).getExcel_cust_name();
        custId=itemlist.get(position).getExcel_cust_id();
        user_assign_data_id=itemlist.get(position).getUser_assign_data_id();

        holder.imgwh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wam="https://wa.me/+91"+mn+"?text=";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wam));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        //dial call to number
        holder.imgcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+mn));
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callIntent);

            }
        });
        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent j=new Intent(context.getApplicationContext(), TodaysPendingFollowupEditActivity.class);

                j.putExtra("custName",custName);
                j.putExtra("custMn",mn);
                j.putExtra("user_assign_data_id",user_assign_data_id);
//                j.putExtra("fDesc",fDesc);
                j.putExtra("exceCustId",custId);


                j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(j);


                 */
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtsr, txtcustN,txtcatname,txtcustMobN;
        ImageView imgwh,imgcl,imgedit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtsr = itemView.findViewById(R.id.todaycalllistsrno);
            txtcustN = itemView.findViewById(R.id.todaycalllistcname);
            txtcustMobN = itemView.findViewById(R.id.todaycalllistmn);
            txtcatname = itemView.findViewById(R.id.todaycalllistcatname);
            imgwh = itemView.findViewById(R.id.whapcalllist);
            imgcl = itemView.findViewById(R.id.callcalllist);
            imgedit = itemView.findViewById(R.id.editcalllist);
        }
    }
}
