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

import com.app.paramjobsindia.Model.SalesJobModelList;
import com.app.paramjobsindia.R;
import com.app.paramjobsindia.SingleJobDetailsActivity;

import java.util.List;

public class SalesJobAdapter extends RecyclerView.Adapter<SalesJobAdapter.ViewHolder> {

    Context context;
    List<SalesJobModelList> itemlist;

    public SalesJobAdapter(Context context, List<SalesJobModelList> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    //search bar//
    public void setFilteredListS(List<SalesJobModelList> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    //setting layout for recyclerview//
    @NonNull
    @Override
    public SalesJobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_layout_recycler,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesJobAdapter.ViewHolder holder, int position) {

        holder.txttitle.setText(itemlist.get(position).getJob_title());
        holder.txtsalary.setText("₹"+itemlist.get(position).getJob_salary()+"/-");
        holder.txtcmpname.setText(itemlist.get(position).getGet_compname());
        holder.txtcity.setText(itemlist.get(position).getJob_location());
        String title=itemlist.get(position).getJob_title();
        String salary=itemlist.get(position).getJob_salary();
        String job_id=itemlist.get(position).getJob_id();

        holder.txtbtn.setOnClickListener(new View.OnClickListener() {
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

        TextView txttitle, txtsalary,txtcmpname,txtcity;
        Button txtbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txttitle = itemView.findViewById(R.id.post);
            txtsalary = itemView.findViewById(R.id.salary);
            txtcmpname = itemView.findViewById(R.id.ctyname);
            txtcity = itemView.findViewById(R.id.cmpname);
            txtbtn = itemView.findViewById(R.id.apply);



        }
    }
}

