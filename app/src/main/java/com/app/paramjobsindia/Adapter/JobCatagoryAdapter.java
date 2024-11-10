package com.app.paramjobsindia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.paramjobsindia.CatagoryDetailJobsActivity;
import com.app.paramjobsindia.Model.JobCatagoryModelList;
import com.app.paramjobsindia.R;

import java.util.List;

public class JobCatagoryAdapter extends RecyclerView.Adapter<JobCatagoryAdapter.ViewHolder> {

    Context context;
    List<JobCatagoryModelList> itemlist;

    public JobCatagoryAdapter(Context context, List<JobCatagoryModelList> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    //search bar//
    public void setFilteredListS(List<JobCatagoryModelList> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    //setting layout for recyclerview//
    @NonNull
    @Override
    public JobCatagoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_layout_recycler_jobcatagory,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobCatagoryAdapter.ViewHolder holder, int position) {

        holder.txtbu_cat_name.setText(itemlist.get(position).getBu_cat_name());

        String id=itemlist.get(position).getBu_cat_id();
        String count=itemlist.get(position).getBu_cat_count();
        String name=itemlist.get(position).getBu_cat_name();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(v.getContext(), CatagoryDetailJobsActivity.class);
//                j.putExtra("price",n);
                j.putExtra("id",id);//transfer of data through intent
                j.putExtra("count",count);//transfer of data through intent
                j.putExtra("name",name);//transfer of data through intent

                v.getContext().startActivity(j);

            }
        });
        Log.d("jbName id",name+id);


        switch (name){
            case "Banking And Finance":
                holder.img.setImageResource(R.drawable.banking);
                break;

            case "Sales And Marketing":
                holder.img.setImageResource(R.drawable.sale);
                break;

            case "Internship ":
                holder.img.setImageResource(R.drawable.internship);
                break;

            case "Information Technologies":
                holder.img.setImageResource(R.drawable.it);
                break;

            case "Back Office":
                holder.img.setImageResource(R.drawable.bank);
                break;

            case "Accountant ":
                holder.img.setImageResource(R.drawable.accountant);
                break;

            case "Delivery Associate  ":
                holder.img.setImageResource(R.drawable.deliveryman);
                break;

            case "Admin":
                holder.img.setImageResource(R.drawable.admin);
                break;
            case "Industrial":
                holder.img.setImageResource(R.drawable.factory);
                break;
            case "Administration ":
                holder.img.setImageResource(R.drawable.blogger);
                break;

            default:
                holder.img.setImageResource(R.drawable.admin);

        }


    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtbu_cat_id, txtbu_cat_name,txtbu_cat_count;
        CardView cardView;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtbu_cat_name = itemView.findViewById(R.id.jobcat);
            cardView = itemView.findViewById(R.id.cardlayout);
            img = itemView.findViewById(R.id.jbimage);

        }
    }
}

