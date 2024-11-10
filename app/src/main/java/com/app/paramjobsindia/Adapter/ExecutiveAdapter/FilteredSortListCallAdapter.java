package com.app.paramjobsindia.Adapter.ExecutiveAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.paramjobsindia.ExecutiveActivities.TodaysPendingFollowupEditActivity;
import com.app.paramjobsindia.Model.FilteredSortListmodel;
import com.app.paramjobsindia.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FilteredSortListCallAdapter extends RecyclerView.Adapter<FilteredSortListCallAdapter.ViewHolder> {

    Context context;
    List<FilteredSortListmodel> itemlist;

    public FilteredSortListCallAdapter(Context context, List<FilteredSortListmodel> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    //search bar//
    public void setFilteredListS(List<FilteredSortListmodel> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    //setting layout for recyclerview//
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item_filteredsortlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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


        if(itemlist.get(position).getFollow_up_date()=="null"){
            holder.txtflDate.setText("");
        }
        else {


            String d=itemlist.get(position).getFollow_up_date();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
            //String datestring=sdf.format(d);

            try {
                Date date = format.parse(d);
                holder.txtflDate.setText(sdf.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        if(itemlist.get(position).getFollow_up_mod_name()=="null"){
            holder.txtflmodname.setText("");
        }
        else {
            holder.txtflmodname.setText(itemlist.get(position).getFollow_up_mod_name());
        }
        if(itemlist.get(position).getFollow_up_desc()=="null"){
            holder.txtfldesc.setText("");
        }
        else {
            holder.txtfldesc.setText(Html.fromHtml(itemlist.get(position).getFollow_up_desc()));
        }

        String mn,custName,fDate,fDesc,custId,user_assign_data_id;
        mn=itemlist.get(position).getExcel_cust_mobileno();
        custName=itemlist.get(position).getExcel_cust_name();
        fDate=itemlist.get(position).getFollow_up_date();
        fDesc=itemlist.get(position).getFollow_up_desc();
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
                Intent j=new Intent(context.getApplicationContext(), TodaysPendingFollowupEditActivity.class);

                j.putExtra("custName",custName);
                j.putExtra("custMn",mn);
                j.putExtra("fDate",fDate);
                j.putExtra("fDesc",fDesc);
                j.putExtra("exceCustId",custId);
                j.putExtra("user_assign_data_id",user_assign_data_id);


                j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(j);

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtsr, txtcustN,txtcustMobN,txtflDate,txtfldesc,txtflmodname;
        ImageView imgwh,imgcl,imgedit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

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

