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

public class LoginActivity extends AppCompatActivity {
    Button nextbtn;
    RadioGroup rgb;
    RadioButton rb,rbcandidate,rbcompany;
    EditText mobno,otp;
    Button smtbtn;
    SharedPreferences sharedPreferences_candidate,sharedPreferences_company;
    TextView newcandidate,shownumber,timmer,toastmsg;
    LinearLayout layout,timmer_layout;
    Toolbar toolbar;
    ImageView prevBtn;

    //shareprefference of parammitra registration
    private static final String SHARED_PREF_NAME_CANDIDATE="myprefcandidate";
    private static final String Key_CANDIDATEID="candidateid";
    private static final String Key_CANDIDATE_JOBCARDNO="jobcardno";
    private static final String Key_CANDIDATE_PROFILETYPE="candidate_profile_type";
    private static final String KEY_CANDIDATE_NAME="candidateName";
    private static final String KEY_CANDIDATE_MOBILE="candidatemobile";
    private static final String Key_CANDIDATE_EMAILID="candidate_email_id";
    private static final String KEY_CANDIDATE_LOCAL="candidate_local";
    private static final String KEY_CANDIDATE_ADDRESS="candidate_address";
    private static final String KEY_CANDIDATE_LOCATION="candidate_location";
    private static final String Key_CANDIDATE_DISTRICT="candidate_district";
    private static final String Key_CANDIDATE_EDUCATION="candidate_education";
    private static final String Key_CANDIDATE_EXPERIENCE="candidate_exprince";
    private static final String Key_CANDIDATE_REFERID="refer_id";
    private static final String Key_CANDIDATE_PASSWORD="candidate_password";
    private static final String Key_CANDIDATE_STATUS="candidate_status";
    private static final String Key_CANDIDATE_DATE="candidate_cdate";


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

    String type;
    private static final String FORMAT = "%02d:%02d";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mobno=findViewById(R.id.mobnologinact);
        otp=findViewById(R.id.otpcandidateloginact);
        rgb=findViewById(R.id.loginrgbact);
        nextbtn=findViewById(R.id.gonextloginact);
        rbcandidate=findViewById(R.id.loginrbcandidateact);
        rbcompany=findViewById(R.id.loginrbcmpanyact);
        smtbtn=findViewById(R.id.logincanbtnact);
        newcandidate=findViewById(R.id.newuseractact);
        layout=findViewById(R.id.llloginact);
        shownumber=findViewById(R.id.mobnumberloginact);
        timmer=findViewById(R.id.countdowntimmerlogin);
        timmer_layout=findViewById(R.id.timmerlayoutlogin);
        toastmsg=findViewById(R.id.toast);
        prevBtn = findViewById(R.id.prev_btn);

        sharedPreferences_candidate =getSharedPreferences(SHARED_PREF_NAME_CANDIDATE, Context.MODE_PRIVATE);
        sharedPreferences_company =getSharedPreferences(SHARED_PREF_NAME_COMPANY, Context.MODE_PRIVATE);

        String cname=sharedPreferences_candidate.getString(KEY_CANDIDATE_NAME,null);
        String cmpname=sharedPreferences_company.getString(KEY_COMPANY_NAME,null);

        prevBtn.setOnClickListener(v -> {
            finish();
        });


        //if not an account
        newcandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(getApplicationContext(), CreateNewAccCandidateActivity.class);
                startActivity(i);

            }
        });
        //if having an account
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

                        type = "2";//type 2 for candidate


                    } else if (rb.getText().equals("I am Company")) {//company radio button is selected
                        type = "1";//type 1 for company
                    }
                    if(!mobno.getText().toString().equals(""))
                    getOtpData();
                    else
                        Toast.makeText(LoginActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //submit data
        smtbtn.setOnClickListener(new View.OnClickListener() {
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
    }
    private void getOtpData() {
        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();
        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url=d+"candidate_info/candidate_login.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");

                    Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();
                    if(msg.equals("SMS Request Is Initiated")){
                        toastmsg.setVisibility(View.GONE);
                        timmer_layout.setVisibility(View.VISIBLE);
                        smtbtn.setVisibility(View.VISIBLE);

                        //showing only lAst 4 digits
                        String mask = mobno.getText().toString().replaceAll("\\w(?=\\w{4})", "*");
                        shownumber.setText(mask);
                        layout.setVisibility(View.VISIBLE);
                        otp.setVisibility(View.VISIBLE);
                        smtbtn.setVisibility(View.VISIBLE);
                        nextbtn.setVisibility(View.GONE);

                        new CountDownTimer(10000, 1000) { // adjust the milli seconds here

                            @SuppressLint({"SetTextI18n", "DefaultLocale"})
                            public void onTick(long millisUntilFinished) {

                                timmer.setText("" + String.format(FORMAT,
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                            }

                            public void onFinish() {
                                smtbtn.setEnabled(true);
                                smtbtn.setBackgroundResource(R.color.lightteal);
                                timmer.setText("done!");
                                timmer_layout.setVisibility(View.GONE);
                            }
                        }.start();
                        nextbtn.setVisibility(View.GONE);

                    }else {
                        toastmsg.setVisibility(View.VISIBLE);
                        toastmsg.setText("Mobile Number is not registered. Please create account.");

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

                Map<String,String> parms=new HashMap<>();
                parms.put("candidate_number",mobno.getText().toString());
                parms.put("profile_type",type);

                return parms;
            }
        };

        queue.add(request);

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
                            String candidate_name=jsonObject1.getString("candidate_name");
                            String jobcardno=jsonObject1.getString("jobcardno");
                            String profile_type=jsonObject1.getString("profile_type");
                            String candidate_number=jsonObject1.getString("candidate_number");
                            String candidate_email_id=jsonObject1.getString("candidate_email_id");
                            String candidate_local=jsonObject1.getString("candidate_local");
                            String candidate_address=jsonObject1.getString("candidate_address");
                            String candidate_location=jsonObject1.getString("candidate_location");
                            String candidate_district=jsonObject1.getString("candidate_district");
                            String candidate_education=jsonObject1.getString("candidate_education");
                            String candidate_exprince=jsonObject1.getString("candidate_exprince");
                            String candidate_status=jsonObject1.getString("candidate_status");
                            String refer_id=jsonObject1.getString("refer_id");
                            String candidate_password=jsonObject1.getString("candidate_password");
                            String candidate_cdate=jsonObject1.getString("candidate_cdate");

                            Log.d("number", candidate_number);


                            //put data on shared preference
                            SharedPreferences.Editor editoru = sharedPreferences_candidate.edit();
                            editoru.putString(Key_CANDIDATEID, cid);
                            editoru.putString(Key_CANDIDATE_PROFILETYPE, profile_type);
                            editoru.putString(Key_CANDIDATE_STATUS, candidate_status);
                            editoru.putString(Key_CANDIDATE_JOBCARDNO, jobcardno);
                            editoru.putString(KEY_CANDIDATE_NAME, candidate_name);
                            editoru.putString(KEY_CANDIDATE_MOBILE, candidate_number);
                            editoru.putString(KEY_CANDIDATE_LOCAL, candidate_local);
                            editoru.putString(Key_CANDIDATE_EMAILID, candidate_email_id);
                            editoru.putString(KEY_CANDIDATE_ADDRESS, candidate_address);
                            editoru.putString(KEY_CANDIDATE_LOCATION, candidate_location);
                            editoru.putString(Key_CANDIDATE_DISTRICT, candidate_district);
                            editoru.putString(Key_CANDIDATE_EDUCATION, candidate_education);
                            editoru.putString(Key_CANDIDATE_EXPERIENCE, candidate_exprince);
                            editoru.putString(Key_CANDIDATE_REFERID, refer_id);
                            editoru.putString(Key_CANDIDATE_PASSWORD, candidate_password);
                            editoru.putString(Key_CANDIDATE_DATE, candidate_cdate);

//                        Saving values to editor
                            editoru.commit();


                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            //goto Mainactivity


                            Intent j=new Intent(getApplicationContext(), CandidateMainActivity.class);
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

                            Intent j=new Intent(getApplicationContext(), CompanyMainActivity.class);
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