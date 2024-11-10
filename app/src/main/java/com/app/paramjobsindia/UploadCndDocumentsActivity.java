package com.app.paramjobsindia;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadCndDocumentsActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView up1,up2,up3,up4,up5,up_resume,up_qualDoc,up_aadhar,up_pan,up_tc;
    TextView resumetxt,rtxt,qualtxt,qtxt,adhartxt,atxt,pantxt,ptxt,tctxt,ttxt;
    ProgressDialog progress;

    byte[] inputData1=null, inputData2=null,inputData3=null,inputData4=null,inputData5=null;
    SharedPreferences sharedPreferences_candidate;

    //create a share preferences name and keys name
    //shareprefference of parammitra registration
    private static final String SHARED_PREF_NAME_CANDIDATE="myprefcandidate";
    private static final String Key_CANDIDATEID="candidateid";

    String cndid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_cnd_documents);

        sharedPreferences_candidate =getSharedPreferences(SHARED_PREF_NAME_CANDIDATE, Context.MODE_PRIVATE);
        cndid=sharedPreferences_candidate.getString(Key_CANDIDATEID,null);




        up1=findViewById(R.id.up1);
        up_resume=findViewById(R.id.upres1);
        resumetxt=findViewById(R.id.tresume);
        rtxt=findViewById(R.id.t1);

        up1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              selectPdf(1);
            }
        });


        up_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress=new ProgressDialog(UploadCndDocumentsActivity.this);
                progress.setMessage("Please Wait");
                progress.show();
                String url = "https://paramjobsindia.com/dash/apis/candidate_info/candidate_update_resume.php";

                //our custom volley request
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));

                                    Log.d("result msg",obj.getString("message"));
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    if(obj.getString("message").equals("Qualification Document Uploads Successfully")){
                                        rtxt.setText("Resume Uploaded Successfully!");
                                        rtxt.setTextColor(Color.GREEN);

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
                        params.put("candidate_id", cndid);
                        return params;
                    }

                    /*
                     * Here we are passing image by renaming it with a unique name
                     * */


                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                        long imagename = System.currentTimeMillis();
                        params.put("studresume_image", new DataPart(imagename + ".png", inputData1));

                        return params;

                    }

                };

                //adding the request to volley
                Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);

            }
        });


        //uploading qual documents
        up2=findViewById(R.id.up2);
        up_qualDoc=findViewById(R.id.upqualdocument);
        qualtxt=findViewById(R.id.tqualdocument);
        qtxt=findViewById(R.id.t2);
        up2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf(2);
            }
        });
        up_qualDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progress=new ProgressDialog(UploadCndDocumentsActivity.this);
                progress.setMessage("Please Wait");
                progress.show();
                String url = "https://paramjobsindia.com/dash/apis/candidate_info/candidate_update_qualification.php";

                //our custom volley request
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));

                                    Log.d("result msg",obj.getString("message"));
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    if(obj.getString("message").equals("Qualification Document Uploads Successfully")){
                                        qtxt.setText("Qualification Documents Uploaded Successfully!");
                                        qtxt.setTextColor(Color.GREEN);
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
                        params.put("candidate_id", cndid);
                        return params;
                    }

                    /*
                     * Here we are passing image by renaming it with a unique name
                     * */


                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                        long imagename = System.currentTimeMillis();
                        params.put("studqualification_image", new DataPart(imagename + ".png", inputData2));

                        return params;

                    }

                };

                //adding the request to volley
                Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);

            }

        });


        //uploading qual documents
        up3=findViewById(R.id.up3);
        up_aadhar=findViewById(R.id.upadhar);
        adhartxt=findViewById(R.id.tadhar);
        atxt=findViewById(R.id.t3);
        up3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf(3);
            }
        });

        up_aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress=new ProgressDialog(UploadCndDocumentsActivity.this);
                progress.setMessage("Please Wait");
                progress.show();
                String url = "https://paramjobsindia.com/dash/apis/candidate_info/candidate_update_adharcard.php";

                //our custom volley request
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));

                                    Log.d("result msg",obj.getString("message"));
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    if(obj.getString("message").equals("Aadharcard Document Uploads Successfully")){
                                        atxt.setText("Aadhar Card Uploaded Successfully!");
                                        atxt.setTextColor(Color.GREEN);
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
                        params.put("candidate_id", cndid);
                        return params;
                    }

                    /*
                     * Here we are passing image by renaming it with a unique name
                     * */


                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                        long imagename = System.currentTimeMillis();
                        params.put("adharcard_image", new DataPart(imagename + ".png", inputData3));

                        return params;

                    }

                };

                //adding the request to volley
                Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);

            }

        });


        //uploading qual documents
        up4=findViewById(R.id.up4);
        up_pan=findViewById(R.id.uppan);
        pantxt=findViewById(R.id.tpan);
        ptxt=findViewById(R.id.t4);
        up4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf(4);
            }
        });

        up_pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress=new ProgressDialog(UploadCndDocumentsActivity.this);
                progress.setMessage("Please Wait");
                progress.show();
                String url = "https://paramjobsindia.com/dash/apis/candidate_info/candidate_update_pandacard.php";

                //our custom volley request
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));

                                    Log.d("result msg",obj.getString("status"));
                                    Toast.makeText(getApplicationContext(),"Pan Card Uploaded Successfully!", Toast.LENGTH_SHORT).show();

                                    if(obj.getString("status").equals("true")){
                                        ptxt.setText("Pan Card Uploaded Successfully!");
                                        ptxt.setTextColor(Color.GREEN);
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
                        params.put("candidate_id", cndid);
                        return params;
                    }

                    /*
                     * Here we are passing image by renaming it with a unique name
                     * */


                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                        long imagename = System.currentTimeMillis();
                        params.put("studpancard_image", new DataPart(imagename + ".png", inputData4));

                        return params;

                    }

                };

                //adding the request to volley
                Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);

            }

        });

        //uploading qual documents
        up5=findViewById(R.id.up5);
        up_tc=findViewById(R.id.uptc);
        tctxt=findViewById(R.id.ttc);
        ttxt=findViewById(R.id.t5);
        up5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf(5);
            }
        });

        up_tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress=new ProgressDialog(UploadCndDocumentsActivity.this);
                progress.setMessage("Please Wait");
                progress.show();
                String url = "https://paramjobsindia.com/dash/apis/candidate_info/candidate_update_studtclc.php";

                //our custom volley request
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));

                                    Log.d("result msg",obj.getString("message"));
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    if(obj.getString("message").equals("Studtclc Document Uploads Successfully")){
                                        ttxt.setText("T.C/L.C/Nationality Certificate Upload Successfully!");
                                        ttxt.setTextColor(Color.GREEN);
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
                        params.put("candidate_id", cndid);
                        return params;
                    }

                    /*
                     * Here we are passing image by renaming it with a unique name
                     * */


                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                        long imagename = System.currentTimeMillis();
                        params.put("studtclc", new DataPart(imagename + ".png", inputData5));

                        return params;

                    }

                };

                //adding the request to volley
                Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);

            }

        });

        //toolbar with back button

        toolbar=findViewById(R.id.toolbarupdoc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void selectPdf(int actionval){

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        if(actionval==1){
            startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),11);

        }
        if(actionval==2){
            startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),12);

        }
        if(actionval==3){
            startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),13);

        }
        if(actionval==4){
            startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),14);

        }
        if(actionval==5){
            startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),15);

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
                                resumetxt.setText(displayName);
                                rtxt.setText("Resume Selected");
                                rtxt.setVisibility(View.VISIBLE);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        resumetxt.setText(displayName);
                        rtxt.setText("Resume Uploaded");
                        rtxt.setVisibility(View.VISIBLE);


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
                                            qualtxt.setText(displayName);
                                            qtxt.setText("Qualification Document Selected");
                                            qtxt.setVisibility(View.VISIBLE);
                                        }
                                    } finally {
                                        cursor.close();
                                    }
                                } else if (uriString.startsWith("file://")) {
                                    displayName = myFile.getName();
                                    qualtxt.setText(displayName);
                                    qtxt.setText("Qualification Document Selected");
                                    rtxt.setVisibility(View.VISIBLE);


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
                                    adhartxt.setText(displayName);
                                    atxt.setText("Adhar Selected");
                                    atxt.setVisibility(View.VISIBLE);
                                }
                            } finally {
                                cursor.close();
                            }
                        } else if (uriString.startsWith("file://")) {
                            displayName = myFile.getName();
                            adhartxt.setText(displayName);
                            atxt.setText("Adhar Selected");
                            atxt.setVisibility(View.VISIBLE);

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
                                    pantxt.setText(displayName);
                                    ptxt.setText("PanCard Selected");
                                    ptxt.setVisibility(View.VISIBLE);
                                }
                            } finally {
                                cursor.close();
                            }
                        } else if (uriString.startsWith("file://")) {
                            displayName = myFile.getName();
                            pantxt.setText(displayName);
                            ptxt.setText("PanCard Selected");
                            ptxt.setVisibility(View.VISIBLE);

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
                                    tctxt.setText(displayName);
                                    ttxt.setText("T.C/L.C/Nationality Certificate Selected");
                                    ttxt.setVisibility(View.VISIBLE);
                                      }
                                 } finally {
                                cursor.close();
                                 }
                             } else if (uriString.startsWith("file://")) {
                                    displayName = myFile.getName();
                                    tctxt.setText(displayName);
                                 ttxt.setText("T.C/L.C/Nationality Certificate Selected");
                                ttxt.setVisibility(View.VISIBLE);


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



    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);


    }
}