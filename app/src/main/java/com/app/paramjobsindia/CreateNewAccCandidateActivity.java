package com.app.paramjobsindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.DomainName.DomainName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CreateNewAccCandidateActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView nextbtn;
    EditText mobno;
    TextView otp,displaymobnumber,alredyaccount,timmer;
    Button createAccbtn;
    LinearLayout layout,timmer_layout;
    RadioGroup rgb;
    RadioButton rb,rbcandidate,rbcompany;
    SharedPreferences sharedPreferences_candidate,sharedPreferences_company;
    String type;
    //create a share preferences name and keys name
    //shareprefference of parammitra registration
    private static final String SHARED_PREF_NAME_CANDIDATE="myprefcandidate";
    private static final String KEY_NAME_CANDIDATE="candidateName";
    private static final String KEY_MOBILE_CANDIDATE="candidatemobile";
    private static final String Key_CANDIDATEID="candidateid";
    private static final String Key_CANDIDATE_JOBCARDNO="jobcardno";
    private static final String Key_CANDIDATE_EMAILID="candidate_email_id";
    private static final String Key_CANDIDATE_STATUS="candidate_status";
    private static final String Key_CANDIDATE_PROFILETYPE="candidate_profile_type";

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
    private static final String FORMAT = "%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_acc_candidate);

        nextbtn=findViewById(R.id.gonext);
        mobno=findViewById(R.id.mobno);
        otp=findViewById(R.id.otpcandidatecreate);
        createAccbtn=findViewById(R.id.createcanbtn);
        layout=findViewById(R.id.ll3);
        timmer_layout=findViewById(R.id.timmerlayoutcreateacc);
        timmer=findViewById(R.id.countdowntimmercreateacc);
        displaymobnumber=findViewById(R.id.mobnumber);
        alredyaccount=findViewById(R.id.alreadyaccount);
        rgb =  findViewById(R.id.create_acc_rgb);
        rbcandidate= findViewById(R.id.create_acc_candidaterb);
        rbcompany= findViewById(R.id.create_acc_companyrb);
        sharedPreferences_candidate =getSharedPreferences(SHARED_PREF_NAME_CANDIDATE, Context.MODE_PRIVATE);
        sharedPreferences_company =getSharedPreferences(SHARED_PREF_NAME_COMPANY, Context.MODE_PRIVATE);



        alredyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int sid = rgb.getCheckedRadioButtonId();
                rb = (RadioButton) findViewById(sid);
                if (sid == -1) {
                    Toast.makeText(getApplicationContext(), "Please select Option", Toast.LENGTH_LONG).show();
                } else {


                    //Toast.makeText(MainActivity.this,"Successfully Login", Toast.LENGTH_LONG).show();
                    if (rb.getText().equals("I am Job Seeker")) {//job seeker radio button is selected

                        type="2";
                    }
                    else if (rb.getText().equals("I am Company")) {//company radio button is selected
                        type="1";
                    }

                    if(!mobno.getText().toString().equals("")) {

                        getOtpData();
                    }
                    else {
                        Toast.makeText(CreateNewAccCandidateActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        createAccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int sid = rgb.getCheckedRadioButtonId();
                rb = (RadioButton) findViewById(sid);
                if (sid == -1) {
                    Toast.makeText(getApplicationContext(), "Please select Option", Toast.LENGTH_LONG).show();
                } else {


                    //Toast.makeText(MainActivity.this,"Successfully Login", Toast.LENGTH_LONG).show();
                    if (rb.getText().equals("I am Job Seeker")) {//job seeker radio button is selected

                        type = "2";//type 2 for candidate
                        verifyCandidateOtpData();//saving data to share prefferences

                    } else if (rb.getText().equals("I am Company")) {//company radio button is selected
                        type = "1";//type 1 for company
                        verifyCompanyOtpData();
                    }


                }
            }
        });
        //toolbar with back button

        toolbar=findViewById(R.id.toolbarcreateaccnt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void verifyCandidateOtpData() {


        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url=d+"candidate_info/candidate_verifyOtp.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");


                    Log.d("message",msg);
                    if(msg.equals(" Otp Verified Successfully")) {

                        JSONArray jsonArray=jsonObject.getJSONArray("candidate_details");
                        for(int i=0;i<= jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String cid=jsonObject1.getString("candidate_id");
                            String profiletype=jsonObject1.getString("profile_type");
                            String cstatus=jsonObject1.getString("candidate_status");
                            String cmob=jsonObject1.getString("candidate_number");

                            Log.d("number", cmob);


                            //put data on shared preference
                            SharedPreferences.Editor editoru = sharedPreferences_candidate.edit();
                            editoru.putString(Key_CANDIDATEID, cid);
                            editoru.putString(Key_CANDIDATE_PROFILETYPE, profiletype);
                            editoru.putString(Key_CANDIDATE_STATUS, cstatus);
                            editoru.putString(KEY_MOBILE_CANDIDATE, cmob);


//                        Saving values to editor
                            editoru.commit();


                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            //goto Mainactivity


                            Intent j=new Intent(getApplicationContext(),EditProfile.class);
                            startActivity(j);
                        }

                    }else {
                        Toast.makeText(getApplicationContext(), "Incorrect Mobile no & Password", Toast.LENGTH_SHORT).show();
                    }


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
                parms.put("otp",otp.getText().toString());
                return parms;
            }
        };

        queue.add(request);

    }


    private void getOtpData() {

        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url=d+"candidate_info/candidate_create_account_and_send_otp.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");

                    if(msg.equals("SMS Request Is Initiated")){
                        createAccbtn.setVisibility(View.VISIBLE);
                        timmer_layout.setVisibility(View.VISIBLE);
                        //showing only lAst 4 digits
                        String mask = mobno.getText().toString().replaceAll("\\w(?=\\w{4})", "*");
                        displaymobnumber.setText(mask);
                        layout.setVisibility(View.VISIBLE);
                        otp.setVisibility(View.VISIBLE);
                        nextbtn.setVisibility(View.GONE);
//                        login.setVisibility(View.VISIBLE);


                        new CountDownTimer(10000, 1000) { // adjust the milli seconds here

                            @SuppressLint({"SetTextI18n", "DefaultLocale"})
                            public void onTick(long millisUntilFinished) {

                                timmer.setText("" + String.format(FORMAT,
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                            }

                            @SuppressLint("SetTextI18n")
                            public void onFinish() {
                                createAccbtn.setEnabled(true);
                                createAccbtn.setBackgroundResource(R.color.lightteal);
                                timmer.setText("done!");
                                timmer_layout.setVisibility(View.GONE);
                            }
                        }.start();
                        nextbtn.setVisibility(View.GONE);

                    }else {
                        Toast.makeText(getApplicationContext(), "Wrong Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Log.d("type",type);
                Map<String,String> parms=new HashMap<>();
                parms.put("candidate_number",mobno.getText().toString());
                parms.put("profile_type",type);

                return parms;
            }
        };
        queue.add(request);
    }


    private void verifyCompanyOtpData() {
        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();
        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url=d+"candidate_info/candidate_verifyOtp.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");


                    Log.d("message",msg);
                    if(msg.equals(" Otp Verified Successfully")) {

                        JSONArray jsonArray=jsonObject.getJSONArray("company_details");
                        for(int i=0;i<= jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String company_id=jsonObject1.getString("company_id");
                            String profile_type=jsonObject1.getString("profile_type");
                            String company_name=jsonObject1.getString("company_name");
                            String company_address=jsonObject1.getString("company_address");
                            String company_contact_number=jsonObject1.getString("company_contact_number");
                            String company_contact_mail=jsonObject1.getString("company_contact_mail");
                            String company_manager_name=jsonObject1.getString("company_manager_name");
                            String company_mobile_number=jsonObject1.getString("company_mobile_number");
                            String company_manager_mail_id=jsonObject1.getString("company_manager_mail_id");
                            String refer_id=jsonObject1.getString("refer_id");
                            String company_password=jsonObject1.getString("company_password");
                            String company_status=jsonObject1.getString("company_status");
                            String company_cdate=jsonObject1.getString("company_cdate");

                            Log.d("number", company_mobile_number);
                            Log.d("cmpid", company_id);

                            //put data on shared preference
                            SharedPreferences.Editor editorcmp = sharedPreferences_company.edit();
                            editorcmp.putString(Key_COMPANY_ID, company_id);
                            editorcmp.putString(Key_COMPANY_PROFILETYPE, profile_type);
                            editorcmp.putString(Key_COMPANY_STATUS, company_status);
                            editorcmp.putString(Key_COMPANY_CONTACT, company_contact_number);
                            editorcmp.putString(KEY_COMPANY_NAME, company_name);
                            editorcmp.putString(KEY_COMPANY_MOBILEN, company_mobile_number);
                            editorcmp.putString(KEY_COMPANY_MAIL, company_contact_mail);
                            editorcmp.putString(KEY_COMPANY_ADDRESS, company_address);
                            editorcmp.putString(KEY_COMPANY_MANAGERNAME, company_manager_name);
                            editorcmp.putString(Key_COMPANY_MANGEREMAILID, company_manager_mail_id);
                            editorcmp.putString(Key_COMPANY_REFERID, refer_id);
                            editorcmp.putString(Key_COMPANY_PASSWORD, company_password);
                            editorcmp.putString(Key_COMPANY_DATE, company_cdate);

//                        Saving values to editor
                            editorcmp.commit();
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            //goto Mainactivity

                            Intent j=new Intent(getApplicationContext(), CmpEditProfileActivity.class);
                            startActivity(j);
                        }

                    }else {
                        Toast.makeText(getApplication(), "Incorrect Mobile no & Password", Toast.LENGTH_SHORT).show();
                    }


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
                parms.put("otp",otp.getText().toString());
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