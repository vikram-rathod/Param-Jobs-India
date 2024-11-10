package com.app.paramjobsindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.DomainName.DomainName;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CmpEditProfileActivity extends AppCompatActivity {

    ProgressDialog progress;
    Toolbar toolbar;

    SharedPreferences sharedPreferences_company;
    private static final String SHARED_PREF_NAME_COMPANY="myprefCOMPANY";
    private static final String Key_COMPANY_ID="COMPANYid";
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

    EditText cmp_name,cmp_address,cmp_contact,cmp_manager_name,cmp_mobnumber
            ,manger_emailid;

    Button updatebtn;
    String cmpid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmp_edit_profile);


        cmp_name=findViewById(R.id.cmpname);//edittext
        cmp_address=findViewById(R.id.cmpaddr);//edittext
        cmp_contact=findViewById(R.id.cmpcontact);//edittext
        cmp_manager_name=findViewById(R.id.cmpmanagername);//edittext
        cmp_mobnumber=findViewById(R.id.cmpmobno);//edittext
        manger_emailid=findViewById(R.id.cmpmngremailid);//edittext

        updatebtn=findViewById(R.id.cmpudbtn);//button

        sharedPreferences_company =getSharedPreferences(SHARED_PREF_NAME_COMPANY, Context.MODE_PRIVATE);
        cmpid=sharedPreferences_company.getString(Key_COMPANY_ID,null);

//        //coming data from cmpprofilefragment
//        cmp_name.setText(getIntent().getExtras().getString("cmpname"));
//        cmp_address.setText(getIntent().getExtras().getString("cmpaddress"));
//        cmp_contact.setText(getIntent().getExtras().getString("cmpcontactno"));
//        cmp_manager_name.setText(getIntent().getExtras().getString("cmpmanagername"));
//        cmp_mobnumber.setText(getIntent().getExtras().getString("cmpmobno"));
//        manger_emailid.setText(getIntent().getExtras().getString("mangeremailid"));

        cmp_name.setText(sharedPreferences_company.getString(KEY_COMPANY_NAME,null));
        cmp_address.setText(sharedPreferences_company.getString(KEY_COMPANY_ADDRESS,null));
        cmp_contact.setText(sharedPreferences_company.getString(Key_COMPANY_CONTACT,null));
        cmp_manager_name.setText(sharedPreferences_company.getString(KEY_COMPANY_MANAGERNAME,null));
        cmp_mobnumber.setText(sharedPreferences_company.getString(KEY_COMPANY_MOBILEN,null));
        manger_emailid.setText(sharedPreferences_company.getString(Key_COMPANY_MANGEREMAILID,null));

        progress=new ProgressDialog(this);
        progress.setMessage("Please Wait");
        progress.show();
        //giving delay of 3 sec to progress dialogue
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.setCanceledOnTouchOutside(true);
                progress.dismiss();

            }
        },3000);






        //updating candidate data

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfile();

            }
        });
        //toolbar with back button

        toolbar=findViewById(R.id.toolbareditcmpprofile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void updateProfile() {
        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();
        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url=d+"candidate_info/company_update_profile.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");


                    Log.d("message",msg);

                    SharedPreferences.Editor editorcmp = sharedPreferences_company.edit();
                    editorcmp.putString(Key_COMPANY_ID, cmpid);
                    editorcmp.putString(Key_COMPANY_CONTACT, cmp_contact.getText().toString());
                    editorcmp.putString(KEY_COMPANY_NAME, cmp_name.getText().toString());
                    editorcmp.putString(KEY_COMPANY_MOBILEN, cmp_mobnumber.getText().toString());
                    editorcmp.putString(KEY_COMPANY_ADDRESS, cmp_address.getText().toString());
                    editorcmp.putString(KEY_COMPANY_MANAGERNAME, cmp_manager_name.getText().toString());
                    editorcmp.putString(Key_COMPANY_MANGEREMAILID, manger_emailid.getText().toString());


//                        Saving values to editor
                    editorcmp.commit();
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    //goto Mainactivity

//                    finish();
                    Toast.makeText(CmpEditProfileActivity.this, msg, Toast.LENGTH_SHORT).show();
                    Intent j=new Intent(getApplicationContext(),CompanyMainActivity.class);
                    startActivity(j);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("company_id",cmpid);
                parms.put("company_name",cmp_name.getText().toString());
                parms.put("company_address",cmp_address.getText().toString());
                parms.put("company_contact_number",cmp_contact.getText().toString());
                parms.put("company_manager_name",cmp_manager_name.getText().toString());
                parms.put("company_mobile_number",cmp_mobnumber.getText().toString());
                parms.put("company_manager_mail_id",manger_emailid.getText().toString());


                return parms;
            }
        };

        queue.add(request);

    }

    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}