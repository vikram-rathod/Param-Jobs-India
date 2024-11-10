package com.app.paramjobsindia.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.paramjobsindia.CmpEditProfileActivity;
import com.app.paramjobsindia.R;

public class CmpProfilefragment extends Fragment {

    TextView cmp_name,cmp_address,cmp_contactno,cmp_managername,cmp_mobno,manger_emailid;
    ImageView edit;

    SharedPreferences sharedPreferences_company;
    //shareprefference of Company registration
    private static final String SHARED_PREF_NAME_COMPANY="myprefCOMPANY";
    private static final String Key_COMPANY_ID="COMPANYid";
    private static final String Key_COMPANY_PROFILETYPE="COMPANY_profile_type";
    private static final String KEY_COMPANY_NAME="COMPANYName";
    private static final String KEY_COMPANY_ADDRESS="COMPANYADDRESS";
    private static final String Key_COMPANY_CONTACT="COMPANYCONTACT";
    private static final String KEY_COMPANY_MAIL="COMPANYMAIL";
    private static final String KEY_COMPANY_MANAGERNAME="COMPANYMANAGERN";
    private static final String KEY_COMPANY_MOBILEN="COMPANYMOBILENO";
    private static final String Key_COMPANY_MANGEREMAILID="COMPANYMANAGEREMAIL";
    private static final String Key_COMPANY_REFERID="COMPANYREFER";
    private static final String Key_COMPANY_PASSWORD="COMPANYPASSWORD";
    private static final String Key_COMPANY_STATUS="COMPANYSTATUS";
    private static final String Key_COMPANY_DATE="COMPANYDATE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cmpprofile_fragment,container,false);

        edit=view.findViewById(R.id.cmpeditprofile);//edit image
        cmp_name=view.findViewById(R.id.pcmpname);//textview
        cmp_address=view.findViewById(R.id.pcmpaddr);//textview
        cmp_contactno=view.findViewById(R.id.pcmpcontno);//textview
        cmp_managername=view.findViewById(R.id.pcmpmngrname);//textview
        cmp_mobno=view.findViewById(R.id.pcmpmobno);//textview
        manger_emailid=view.findViewById(R.id.pcmpmngremailid);//textview


        sharedPreferences_company =getContext().getSharedPreferences(SHARED_PREF_NAME_COMPANY, Context.MODE_PRIVATE);

        cmp_name.setText(sharedPreferences_company.getString(KEY_COMPANY_NAME,null));
        cmp_address.setText(sharedPreferences_company.getString(KEY_COMPANY_ADDRESS,null));
        cmp_contactno.setText(sharedPreferences_company.getString(Key_COMPANY_CONTACT,null));
        cmp_managername.setText(sharedPreferences_company.getString(KEY_COMPANY_MANAGERNAME,null));
        cmp_mobno.setText(sharedPreferences_company.getString(KEY_COMPANY_MOBILEN,null));
        manger_emailid.setText(sharedPreferences_company.getString(Key_COMPANY_MANGEREMAILID,null));


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getContext(), CmpEditProfileActivity.class);
                i.putExtra("cmpname",cmp_name.getText().toString());
                i.putExtra("cmpaddress",cmp_address.getText().toString());
                i.putExtra("cmpcontactno",cmp_contactno.getText().toString());
                i.putExtra("cmpmanagername",cmp_managername.getText().toString());
                i.putExtra("cmpmobno",cmp_mobno.getText().toString());
                i.putExtra("mangeremailid",manger_emailid.getText().toString());

                startActivity(i);

            }
        });
        return view;
    }
}
