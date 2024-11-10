package com.app.paramjobsindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.DomainName.DomainName;
import com.app.paramjobsindia.Model.SelectIvShedulestatusModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeJbStatusActivity extends AppCompatActivity {
    ProgressDialog progress;

    Toolbar toolbar;
    Spinner spinner;
    Button statuscheck;
    List<SelectIvShedulestatusModel> selectIvShedulestatuslist=new ArrayList<>();//for selecting interview shedule status using spinner
    private  ArrayAdapter<SelectIvShedulestatusModel> selectIvShedulestatusModelArrayAdapter;//for selecting interview shedule status using spinner

    String selectIvshedulestatus,jobid,candid,selectIvshedulestatus_name;
    String msg;
    byte[] inputData1=null, inputData2=null,inputData3=null,inputData4=null,inputData5=null;

    LinearLayout layout;
    ImageView up1, up_bankpassbook,up2,up_videorecording,up3,up_travelticket,up4,up_salaryproof,up5,up_offerletter;
    TextView bankpassbooktxt,bptxt,videorecordingtxt,vrtxt,traveltickettxt,tttxt,salaryprooftxt,
            sptxt,offerlettertxt,oltxt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_jb_status);

        spinner = findViewById(R.id.sp_shedule_iv_status);
        statuscheck =findViewById(R.id.check);
        layout =findViewById(R.id.shwlayout);

        jobid=getIntent().getExtras().getString("jobId");
        candid=getIntent().getExtras().getString("candId");

        selectIvShedulestatusModelArrayAdapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,selectIvShedulestatuslist);
        selectIvShedulestatusModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(selectIvShedulestatusModelArrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectIvShedulestatusModel selectstatusModel=(SelectIvShedulestatusModel)spinner.getSelectedItem();
                selectIvshedulestatus=selectstatusModel.getCt_jas_id();
                Log.d("Ct_jas_id", selectstatusModel.getCt_jas_id());
                selectIvshedulestatus_name=selectstatusModel.getCt_jas_name();
                Log.d("selectedSpinner", selectIvshedulestatus_name);

                if(selectIvshedulestatus_name.equals("Candidate Selected")){

                    layout.setVisibility(View.VISIBLE);


                }
//                Toast.makeText(AddCustomerActivity.this, flmod, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), spinc.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
            String status=selectIvshedulestatus;
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        statuscheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("selectIvshedulestatus", selectIvshedulestatus);
                if (selectIvshedulestatus_name.equals("Candidate Selected")) {

                    if ((bptxt.getText().equals("Bank PassBook Xerox Uploaded Successfully!")) &&
                            (vrtxt.getText().equals("VideoRecording Documents Uploaded Successfully!")) &&
                            (tttxt.getText().equals("Travel Ticket Uploaded Successfully!")) &&
                            (sptxt.getText().equals("Salary Proof Uploaded Successfully!")) &&
                            (oltxt.getText().equals("Offer Letter Upload Successfully!"))) {

                        uploadData();//changing status api calling
                    } else {

                        Toast.makeText(ChangeJbStatusActivity.this, "Upload All Documents", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    uploadData();//changing status api calling

                }
            }
        });

        getSpinnerData();



        up1=findViewById(R.id.u1);
        up_bankpassbook=findViewById(R.id.uppassxerox);
        bankpassbooktxt=findViewById(R.id.tpassxerox);
        bptxt=findViewById(R.id.t1passxerox);

        up1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf(1);
            }
        });


        up_bankpassbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress=new ProgressDialog(ChangeJbStatusActivity.this);
                progress.setMessage("Please Wait");
                progress.show();
                String url = "https://paramjobsindia.com/dash/apis/company_info/candidate_update_BankpassBookXerox.php";

                //our custom volley request
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));

                                    Log.d("result msg",obj.getString("message"));
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    if(obj.getString("message").equals("BankpassBookXerox Uploads Successfully")){
                                        bptxt.setText("Bank PassBook Xerox Uploaded Successfully!");
                                        bptxt.setTextColor(Color.GREEN);

                                        progress.dismiss();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext()(), "Multiple image error"+error.getMessage(), Toast.LENGTH_SHORT).show();
//                                if (error.getMessage().equals("java.lang.NullPointerException: Attempt to get length of null array")) {

                                    Toast.makeText(getApplicationContext(), "Please Select Photo", Toast.LENGTH_LONG).show();
                                    progress.dismiss();
//                                }
                            }
                        }) {

                    /*
                     * If you want to add more parameters with the image
                     * you can do it here
                     * here we have only one parameter with the image
                     * which is tags
                     * */
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("candidate_id", candid);
                        params.put("job_id", jobid);

                        return params;
                    }

                    /*
                     * Here we are passing image by renaming it with a unique name
                     * */


                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                        long imagename = System.currentTimeMillis();
                        params.put("BankpassBookXerox_image", new DataPart(imagename + ".png", inputData1));

                        return params;

                    }

                };

                //adding the request to volley
                Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);

            }
        });


        //uploading qual documents
        up2=findViewById(R.id.u2);
        up_videorecording=findViewById(R.id.upvideorec);
        videorecordingtxt=findViewById(R.id.tvideorec);
        vrtxt=findViewById(R.id.t2videorec);
        up2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf(2);
            }
        });
        up_videorecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progress=new ProgressDialog(ChangeJbStatusActivity.this);
                progress.setMessage("Please Wait");
                progress.show();
                String url = "https://paramjobsindia.com/dash/apis/company_info/candidate_update_VideoRecording.php";

                //our custom volley request
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));

                                    Log.d("result msg",obj.getString("message"));
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    if(obj.getString("message").equals("VideoRecording Document Uploads Successfully")){
                                        vrtxt.setText("VideoRecording Documents Uploaded Successfully!");
                                        vrtxt.setTextColor(Color.GREEN);
                                        progress.dismiss();


                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext()(), "Multiple image error"+error.getMessage(), Toast.LENGTH_SHORT).show();
//                                if (error.getMessage().equals("java.lang.NullPointerException: Attempt to get length of null array")) {
                                Toast.makeText(getApplicationContext(), "Please Select Photo", Toast.LENGTH_LONG).show();
                                progress.dismiss();

//                                }
                            }
                        }) {

                    /*
                     * If you want to add more parameters with the image
                     * you can do it here
                     * here we have only one parameter with the image
                     * which is tags
                     * */
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("candidate_id", candid);
                        params.put("job_id", jobid);

                        return params;
                    }

                    /*
                     * Here we are passing image by renaming it with a unique name
                     * */


                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                        long imagename = System.currentTimeMillis();
                        params.put("VideoRecording_image", new DataPart(imagename + ".png", inputData2));

                        return params;

                    }

                };

                //adding the request to volley
                Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);

            }

        });


        //uploading qual documents
        up3=findViewById(R.id.u3);
        up_travelticket=findViewById(R.id.uptraveltckt);
        traveltickettxt=findViewById(R.id.ttraveltckt);
        tttxt=findViewById(R.id.t3traveltckt);
        up3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf(3);
            }
        });

        up_travelticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress=new ProgressDialog(ChangeJbStatusActivity.this);
                progress.setMessage("Please Wait");
                progress.show();
                String url = "https://paramjobsindia.com/dash/apis/company_info/candidate_update_TravelTicket.php";

                //our custom volley request
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));

                                    Log.d("result msg",obj.getString("message"));
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    if(obj.getString("message").equals("TravelTicket Uploads Successfully")){
                                        tttxt.setText("Travel Ticket Uploaded Successfully!");
                                        tttxt.setTextColor(Color.GREEN);
                                        progress.dismiss();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext()(), "Multiple image error"+error.getMessage(), Toast.LENGTH_SHORT).show();
//                                if (error.getMessage().equals("java.lang.NullPointerException: Attempt to get length of null array")) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                                Log.d("error",error.getMessage());
                                Toast.makeText(getApplicationContext(), "Please Select Photo", Toast.LENGTH_LONG).show();
                                    progress.dismiss();
//                                }
                            }
                        }) {

                    /*
                     * If you want to add more parameters with the image
                     * you can do it here
                     * here we have only one parameter with the image
                     * which is tags
                     * */
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("candidate_id", candid);
                        params.put("job_id", jobid);

                        return params;
                    }

                    /*
                     * Here we are passing image by renaming it with a unique name
                     * */


                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                        long imagename = System.currentTimeMillis();
                        params.put("OfferLetter", new DataPart(imagename + ".png", inputData3));

                        return params;

                    }

                };

                //adding the request to volley
                Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);

            }

        });


        //uploading qual documents
        up4=findViewById(R.id.u4);
        up_salaryproof=findViewById(R.id.upsalaryproof);
        salaryprooftxt=findViewById(R.id.tsalaryproof);
        sptxt=findViewById(R.id.t4salaryproof);
        up4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf(4);
            }
        });

        up_salaryproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress=new ProgressDialog(ChangeJbStatusActivity.this);
                progress.setMessage("Please Wait");
                progress.show();
                String url = "https://paramjobsindia.com/dash/apis/company_info/candidate_update_SalaryProof.php";

                //our custom volley request
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));

                                    Log.d("result msg",obj.getString("status"));
                                    Toast.makeText(getApplicationContext(),"Salary Proof Uploaded Successfully!", Toast.LENGTH_SHORT).show();

                                    if(obj.getString("status").equals("true")){
                                        sptxt.setText("Salary Proof Uploaded Successfully!");
                                        sptxt.setTextColor(Color.GREEN);
                                        progress.dismiss();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext()(), "Multiple image error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                                if (error.getMessage().equals("java.lang.NullPointerException: Attempt to get length of null array")) {
                                    Toast.makeText(getApplicationContext(), "Please Select Photo", Toast.LENGTH_LONG).show();
                                    progress.dismiss();
                                }
                            }
                        }) {

                    /*
                     * If you want to add more parameters with the image
                     * you can do it here
                     * here we have only one parameter with the image
                     * which is tags
                     * */
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("candidate_id", candid);
                        params.put("job_id", jobid);

                        return params;
                    }

                    /*
                     * Here we are passing image by renaming it with a unique name
                     * */


                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                        long imagename = System.currentTimeMillis();
                        params.put("SalaryProof", new DataPart(imagename + ".png", inputData4));

                        return params;

                    }

                };

                //adding the request to volley
                Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);

            }

        });

        //uploading qual documents
        up5=findViewById(R.id.u5);
        up_offerletter=findViewById(R.id.upofferletter);
        offerlettertxt=findViewById(R.id.tofferletter);
        oltxt=findViewById(R.id.t5offerletter);
        up5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf(5);
            }
        });

        up_offerletter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress=new ProgressDialog(ChangeJbStatusActivity.this);
                progress.setMessage("Please Wait");
                progress.show();
                String url = "https://paramjobsindia.com/dash/apis/company_info/candidate_update_OfferLetter.php";

                //our custom volley request
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));

                                    Log.d("result msg",obj.getString("message"));
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    if(obj.getString("message").equals("OfferLetter Document Uploads Successfully")){
                                        oltxt.setText("Offer Letter Upload Successfully!");
                                        oltxt.setTextColor(Color.GREEN);
                                        progress.dismiss();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext()(), "Multiple image error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                                if (error.getMessage().equals("java.lang.NullPointerException: Attempt to get length of null array")) {
                                    Toast.makeText(getApplicationContext(), "Please Select Photo", Toast.LENGTH_LONG).show();
                                    progress.dismiss();
                                }
                            }
                        }) {

                    /*
                     * If you want to add more parameters with the image
                     * you can do it here
                     * here we have only one parameter with the image
                     * which is tags
                     * */
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("candidate_id", candid);
                        params.put("job_id", jobid);

                        return params;
                    }

                    /*
                     * Here we are passing image by renaming it with a unique name
                     * */


                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                        long imagename = System.currentTimeMillis();
                        params.put("OfferLetter", new DataPart(imagename + ".png", inputData5));

                        return params;

                    }

                };

                //adding the request to volley
                Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);

            }

        });

        //toolbar with back button

        toolbar=findViewById(R.id.toolbarchngejbstatus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);

    }


    public void getSpinnerData(){
        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1=d+"candidate_info/get_interview_status_list.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //  Toast.makeText(getApplicationContext()(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    JSONArray jsonArray=respObj.getJSONArray("inter_status_details");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String spid=jsonObject.optString("ct_jas_id");
                        String spname=jsonObject.optString("ct_jas_name");

                        selectIvShedulestatuslist.add(new SelectIvShedulestatusModel(spid,spname));
//                        Log.d("s", String.valueOf(cllist));
                        selectIvShedulestatusModelArrayAdapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,selectIvShedulestatuslist);
                        selectIvShedulestatusModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        spselectqual.setAdapter(selectIvShedulestatusModelArrayAdapter);
                        spinner.setAdapter(selectIvShedulestatusModelArrayAdapter);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }

    public void uploadData() {
        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();
        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url = d+"candidate_info/company_applied_job_candidate_change_status.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    msg=jsonObject.getString("message");
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                    Log.d("message",msg);
                    if(msg.equals("Post Status Update Success")){

                        finish();



                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("error",error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<>();
                Log.d("jbidddd",jobid);
                params.put("job_id",jobid);
                params.put("ct_jas_id",selectIvshedulestatus);
                params.put("candidate_id",candid);
                Log.d("candidate_id",candid);

                return params;
            }
        };

        queue.add(request);




    }


    public void selectPdf(int actionval) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);

        if (actionval == 1) {
            intent.setType("application/pdf");
            startActivityForResult(Intent.createChooser(intent, "Select Pdf File"), 11);

        }
        if (actionval == 2) {
            intent.setType("video/*");
            startActivityForResult(Intent.createChooser(intent, "Select Video File"), 12);

        }
        if (actionval == 3) {
            intent.setType("application/pdf");
            startActivityForResult(Intent.createChooser(intent, "Select Pdf File"), 13);

        }
        if (actionval == 4) {
            intent.setType("application/pdf");
            startActivityForResult(Intent.createChooser(intent, "Select Pdf File"), 14);

        }
        if (actionval == 5) {
            intent.setType("application/pdf");
            startActivityForResult(Intent.createChooser(intent, "Select Pdf File"), 15);

        }

    }

        @SuppressLint("Range")
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                if (requestCode == 11) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    InputStream iStream = null;


                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    Log.d("myFile", String.valueOf(myFile));
                    String path = myFile.getAbsolutePath();
                    Log.d("path", path);


                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Toast.makeText(this, "welcome", Toast.LENGTH_SHORT).show();
                                bankpassbooktxt.setText(displayName);
                                bptxt.setText("BankPassBook Selected");
                                bptxt.setVisibility(View.VISIBLE);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        bankpassbooktxt.setText(displayName);
                        bptxt.setText("BankPassBook Uploaded");
                        bptxt.setVisibility(View.VISIBLE);


                    }

                    try {
                        iStream = getContentResolver().openInputStream(uri);
                        inputData1 = getBytes(iStream);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }




                if (requestCode == 12) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    Log.d("myFile", String.valueOf(myFile));
                    String path = myFile.getAbsolutePath();
                    Log.d("path", path);
                    InputStream iStream = null;


                    try {
                        iStream = getContentResolver().openInputStream(uri);
                        inputData2 = getBytes(iStream);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                videorecordingtxt.setText(displayName);
                                vrtxt.setText("Video Recording Selected");
                                vrtxt.setVisibility(View.VISIBLE);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        videorecordingtxt.setText(displayName);
                        vrtxt.setText("Video Recording Selected");
                        vrtxt.setVisibility(View.VISIBLE);


                    }
                }
                if (requestCode == 13) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    Log.d("myFile", String.valueOf(myFile));
                    String path = myFile.getAbsolutePath();
                    Log.d("path", path);
                    InputStream iStream = null;


                    try {
                        iStream = getContentResolver().openInputStream(uri);
                        inputData3 = getBytes(iStream);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                traveltickettxt.setText(displayName);
                                tttxt.setText("Travel Ticket Selected");
                                tttxt.setVisibility(View.VISIBLE);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        traveltickettxt.setText(displayName);
                        tttxt.setText("Travel Ticket Selected");
                        tttxt.setVisibility(View.VISIBLE);

                    }
                }
                if (requestCode == 14) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    Log.d("myFile", String.valueOf(myFile));
                    String path = myFile.getAbsolutePath();
                    Log.d("path", path);
                    InputStream iStream = null;


                    try {
                        iStream = getContentResolver().openInputStream(uri);
                        inputData4 = getBytes(iStream);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                salaryprooftxt.setText(displayName);
                                sptxt.setText("Salary Proof Selected");
                                sptxt.setVisibility(View.VISIBLE);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        salaryprooftxt.setText(displayName);
                        sptxt.setText("Salary Proof Selected");
                        sptxt.setVisibility(View.VISIBLE);

                    }
                }
                if (requestCode == 15) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    Log.d("myFile", String.valueOf(myFile));
                    String path = myFile.getAbsolutePath();
                    Log.d("path", path);
                    InputStream iStream = null;


                    try {
                        iStream = getContentResolver().openInputStream(uri);
                        inputData5 = getBytes(iStream);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                offerlettertxt.setText(displayName);
                                oltxt.setText("Offer Letter Selected");
                                oltxt.setVisibility(View.VISIBLE);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        offerlettertxt.setText(displayName);
                        oltxt.setText("Offer Letter Selected");
                        oltxt.setVisibility(View.VISIBLE);


                    }
                }

            }
        }


        public byte[] getBytes(InputStream inputStream) throws IOException {
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        }





}