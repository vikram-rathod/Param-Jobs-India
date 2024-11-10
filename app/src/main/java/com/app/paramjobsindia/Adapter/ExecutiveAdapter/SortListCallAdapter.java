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
import com.app.paramjobsindia.Model.SortListModel;
import com.app.paramjobsindia.R;
import com.app.paramjobsindia.databinding.RecyclerItemSortlistBinding;
import java.util.List;

public class SortListCallAdapter extends RecyclerView.Adapter<SortListCallAdapter.ViewHolder> {

    Context context;
    List<SortListModel> itemlist;

    public SortListCallAdapter(Context context, List<SortListModel> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    //search bar//
    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredListS(List<SortListModel> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    //setting layout for recyclerview//
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item_sortlist,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        SortListModel sortListModel=itemlist.get(position);
//        holder.recyclerItemSortlistBinding.setSortListEqObject(sortListModel);
//
//
//
//        String mn,custName,fDate,fDesc,custId,user_assign_data_id;
//        mn=itemlist.get(position).getExcel_cust_mobileno();
//        custName=itemlist.get(position).getExcel_cust_name();
//        fDate=itemlist.get(position).getFollow_up_date();
//        fDesc=itemlist.get(position).getFollow_up_desc();
//        custId=itemlist.get(position).getExcel_cust_id();
//        user_assign_data_id=itemlist.get(position).getUser_assign_data_id();
//
//        holder.recyclerItemSortlistBinding.whatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String wam="https://wa.me/+91"+mn+"?text=";
//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(wam));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//
//            }
//        });
//
//        //dial call to number
//        holder.recyclerItemSortlistBinding.callsortlist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent callIntent = new Intent(Intent.ACTION_DIAL);
//                callIntent.setData(Uri.parse("tel:"+mn));
//                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(callIntent);
//
//            }
//        });
//        holder.recyclerItemSortlistBinding.editsortlistfollowup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*
//                Intent j=new Intent(context.getApplicationContext(), TodaysPendingFollowupEditActivity.class);
//
//                j.putExtra("custName",custName);
//                j.putExtra("custMn",mn);
//                j.putExtra("fDate",fDate);
//                j.putExtra("fDesc",fDesc);
//                j.putExtra("exceCustId",custId);
//                j.putExtra("user_assign_data_id",user_assign_data_id);
//
//
//                j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(j);
//
//
//                 */
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtsr, txtcustN,txtcustMobN,txtflDate,txtfldesc,txtflmodname;
        ImageView imgwh,imgcl,imgedit;

        public ViewHolder(View view) {
            super(view);

            txtsr = itemView.findViewById(R.id.fsortlistsrno);
            txtcustN = itemView.findViewById(R.id.fsortlistcname);
            txtcustMobN = itemView.findViewById(R.id.fsortlistmn);
            txtflDate = itemView.findViewById(R.id.fsortlistdate);
            txtfldesc = itemView.findViewById(R.id.fsortlistfdesc);
            txtflmodname = itemView.findViewById(R.id.fsortlistflmname);
            imgwh = itemView.findViewById(R.id.fwhapsortlist);
            imgcl = itemView.findViewById(R.id.fcallsortlist);
            imgedit = itemView.findViewById(R.id.feditsortlist);

        }
    }
}

