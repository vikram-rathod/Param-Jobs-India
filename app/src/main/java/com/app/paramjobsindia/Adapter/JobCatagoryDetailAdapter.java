package com.app.paramjobsindia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.paramjobsindia.Model.JobCatagoryDetailModelList;
import com.app.paramjobsindia.R;
import com.app.paramjobsindia.SingleJobDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JobCatagoryDetailAdapter extends RecyclerView.Adapter<JobCatagoryDetailAdapter.ViewHolder> {

    Context context;
    List<JobCatagoryDetailModelList> itemlist;

    public JobCatagoryDetailAdapter(Context context, List<JobCatagoryDetailModelList> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    //search bar//
    public void setFilteredListS(List<JobCatagoryDetailModelList> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    //setting layout for recyclerview//
    @NonNull
    @Override
    public JobCatagoryDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_layout_recycler_catagoryjobdetail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobCatagoryDetailAdapter.ViewHolder holder, int position) {

        holder.txtsalary.setText("₹"+itemlist.get(position).getJob_salary()+"/-");
        holder.txttitle.setText(itemlist.get(position).getJob_title());
        holder.txtcmpname.setText(itemlist.get(position).getGet_compname());
        holder.txtcity.setText("Location : "+itemlist.get(position).getJob_location());


        String job_id=itemlist.get(position).getJob_id();

        holder.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(v.getContext(), SingleJobDetailsActivity.class);
//                j.putExtra("price",n);
                j.putExtra("job_id",job_id);//transfer of data through intent
                j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this is apparently getting ignored... (ps: i've tried i.setFlags as well)

                v.getContext().startActivity(j);

            }
        });



    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtsalary, txttitle,txtcmpname,txtcity;
        Button apply;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtsalary = itemView.findViewById(R.id.salarycat);
            txttitle = itemView.findViewById(R.id.postcat);
            txtcmpname = itemView.findViewById(R.id.cmpnamecat);
            txtcity = itemView.findViewById(R.id.ctynamecat);
            apply = itemView.findViewById(R.id.applycat);


        }
    }
}

