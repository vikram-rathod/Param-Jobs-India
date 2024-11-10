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

import com.app.paramjobsindia.DetailJob;
import com.app.paramjobsindia.Model.MarketingJobModelList;
import com.app.paramjobsindia.R;

import java.util.List;

public class MarketingJobAdapter extends RecyclerView.Adapter<MarketingJobAdapter.ViewHolder> {

    Context context;
    List<MarketingJobModelList> itemlist;

    public MarketingJobAdapter(Context context, List<MarketingJobModelList> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    //search bar//
    public void setFilteredListS(List<MarketingJobModelList> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    //setting layout for recyclerview//
    @NonNull
    @Override
    public MarketingJobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_layout_recycler,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketingJobAdapter.ViewHolder holder, int position) {

        holder.txttitle.setText(itemlist.get(position).getTitle());
        holder.txtsalary.setText(itemlist.get(position).getSalary());
        String title=itemlist.get(position).getTitle();
        String salary=itemlist.get(position).getSalary();

        holder.txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(v.getContext(), DetailJob.class);
//                j.putExtra("price",n);
                j.putExtra("salary",salary);//transfer of data through intent
                j.putExtra("title",title);//transfer of data through intent

                v.getContext().startActivity(j);

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txttitle, txtsalary;
        Button txtbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txttitle = itemView.findViewById(R.id.post);
            txtsalary = itemView.findViewById(R.id.salary);
            txtbtn = itemView.findViewById(R.id.apply);



        }
    }
}

