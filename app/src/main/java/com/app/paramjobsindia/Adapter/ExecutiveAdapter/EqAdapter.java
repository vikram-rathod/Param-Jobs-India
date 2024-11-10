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
import com.app.paramjobsindia.Model.EqModelList;
import com.app.paramjobsindia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EqAdapter extends RecyclerView.Adapter<EqAdapter.ViewHolder> {

    Context context;
    List<EqModelList> itemlist;

    public EqAdapter(Context context, List<EqModelList> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    //search bar//
    public void setFilteredListS(List<EqModelList> filteredListS){
        this.itemlist=filteredListS;
        notifyDataSetChanged();
    }

    //setting layout for recyclerview//
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item_eqlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtsrno.setText(""+(position+1));

        if(itemlist.get(position).getExcel_cust_name()=="null"){
            holder.txtname.setText("");
        }
        else {
            holder.txtname.setText(itemlist.get(position).getExcel_cust_name());
        }

        if(itemlist.get(position).getFollow_up_date()=="null"){
            holder.txtdate.setText("");
        }
        else {
            String d=itemlist.get(position).getFollow_up_date();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
            //String datestring=sdf.format(d);

            try {
                Date date = format.parse(d);
                holder.txtdate.setText(sdf.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }        }
        if(itemlist.get(position).getExcel_cust_mobileno()=="null"){
            holder.txtmon.setText("");
        }
        else {
            holder.txtmon.setText(itemlist.get(position).getExcel_cust_mobileno());
        }
        if(itemlist.get(position).getFollow_up_desc()=="null"){
            holder.txtdesc.setText("");
        }
        else {
            holder.txtdesc.setText(Html.fromHtml(itemlist.get(position).getFollow_up_desc()));
        }
        if(itemlist.get(position).getFollow_up_mod_name()=="null"){
            holder.txtmode.setText("");
        }
        else {
            holder.txtmode.setText(itemlist.get(position).getFollow_up_mod_name());
        }


        String mn,custName,fDate,fDesc,exceCustId,busname,custadd;
        mn=itemlist.get(position).getExcel_cust_mobileno();
        custName=itemlist.get(position).getExcel_cust_name();
        fDate=itemlist.get(position).getFollow_up_date();
        fDesc=itemlist.get(position).getFollow_up_desc();
        exceCustId=itemlist.get(position).getExcel_cust_id();
//        custadd=itemlist.get(position).get

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
        holder.imgstaus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent j=new Intent(context.getApplicationContext(), EnquiryChangeStatusActivity.class);
//
//                j.putExtra("custName",custName);
//                j.putExtra("custMn",mn);
//                j.putExtra("fDate",fDate);
//                j.putExtra("fDesc",fDesc);
//                j.putExtra("exceCustId",exceCustId);
//
//                j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(j);

            }
        });
        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent l=new Intent(context.getApplicationContext(), EditEnquiryActivity.class);
//
//                l.putExtra("custName",custName);
//                l.putExtra("custMn",mn);
//                l.putExtra("fDate",fDate);
//                l.putExtra("fDesc",fDesc);
//                l.putExtra("exceCustId",exceCustId);
//                l.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(l);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtname,txtdate,txtmon,txtsrno,txtdesc,txtmode;
        ImageView imgwh,imgcl,imgstaus,imgedit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtsrno = itemView.findViewById(R.id.eqsrno);
            txtname = itemView.findViewById(R.id.eqname);
            txtdate = itemView.findViewById(R.id.eqdate);
            txtmon = itemView.findViewById(R.id.eqmn);
            txtdesc = itemView.findViewById(R.id.eqdisc);
            txtmode = itemView.findViewById(R.id.eqmode);
            imgwh = itemView.findViewById(R.id.whapeq);
            imgcl = itemView.findViewById(R.id.calleq);
            imgstaus = itemView.findViewById(R.id.statuseq);
            imgedit = itemView.findViewById(R.id.editeq);

        }
    }
}

