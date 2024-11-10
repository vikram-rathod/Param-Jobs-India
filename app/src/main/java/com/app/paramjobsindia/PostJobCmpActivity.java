package com.app.paramjobsindia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
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
import com.app.paramjobsindia.Model.SelectJtypeModel;
import com.app.paramjobsindia.Model.SelectQualModel;
import com.app.paramjobsindia.Model.SelectstatusModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostJobCmpActivity extends AppCompatActivity {

    ProgressDialog progress;

    Toolbar toolbar;
    EditText jbtitle,jblocation,jbtype,jbsalry,
            jbopenings,jbdesc,jbeducation;
    Button ivdate,jbimg,smt,jbinterview_time;
    private DatePickerDialog datePickerDialog;
    //for spinner data for selcting qualification
    List<SelectQualModel> selquallist=new ArrayList<>();
    List<SelectJtypeModel> seljtypelist=new ArrayList<>();
    List<SelectstatusModel> selstatuslist=new ArrayList<>();

    ArrayAdapter<SelectQualModel> selectQualadapter;
    ArrayAdapter<SelectJtypeModel> selectJtypeadapter;
    ArrayAdapter<SelectstatusModel> selectJbstatusadapter;

    Spinner spselectqual,spjobtype,spjbstatus;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME_COMPANY="myprefCOMPANY";
    private static final String Key_COMPANY_ID="COMPANYid";
    private static final String Key_COMPANY_PROFILETYPE="COMPANY_profile_type";
    private static final String KEY_COMPANY_NAME="COMPANYName";

    String cmp_id,cmp_name,selectqual,selectjbtype,selectjbstatus,changeddate,time;

    //for image uploading

    RelativeLayout rv;
    ImageView img,cancelimg;
    byte[] bytesofimage=null;
    Bitmap bitmap;
    ArrayList<Uri> uriList = new ArrayList<>();
    List<Bitmap> imagesEncodedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job_cmp);

        jbtitle=findViewById(R.id.jbtit);//editext
        jblocation=findViewById(R.id.jbloc);//editext
        jbsalry=findViewById(R.id.jbsal);//editext
        jbinterview_time=findViewById(R.id.ivtm);//button
        jbopenings=findViewById(R.id.nopening);//editext
        jbdesc=findViewById(R.id.jbdesc);//editext
        jbeducation=findViewById(R.id.jbedu);//editext
        ivdate=findViewById(R.id.ivdt);//button
        jbimg=findViewById(R.id.upimg);//button
        smt=findViewById(R.id.jbsmt);//button
        spselectqual=findViewById(R.id.seledc);//Spinner
        rv=findViewById(R.id.rlayoutact);//Relative layout
        img=findViewById(R.id.img_uactivity);//image
        cancelimg=findViewById(R.id.imgcl_uactivity);//Image
        spjobtype=findViewById(R.id.jbtype);//Spinner
        spjbstatus=findViewById(R.id.jbst);//Spinner


        //adding spring data for jobtype and jobstatus

        seljtypelist.add(new SelectJtypeModel("2","Full Time"));
        seljtypelist.add(new SelectJtypeModel("1","Part Time"));

        selectJtypeadapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,seljtypelist);
        selectJtypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spjobtype.setAdapter(selectJtypeadapter);



        selstatuslist.add(new SelectstatusModel("1","Active"));
        selstatuslist.add(new SelectstatusModel("0","Deactive"));

        selectJbstatusadapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,selstatuslist);
        selectJbstatusadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spjbstatus.setAdapter(selectJbstatusadapter);

        //defining shareprefference for getting data from it.
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME_COMPANY, Context.MODE_PRIVATE);
        cmp_id=sharedPreferences.getString(Key_COMPANY_ID,null);
        cmp_name=sharedPreferences.getString(KEY_COMPANY_NAME,null);

        //datepicker function for selection of date
        initDatepicker();


        //selecting spinner data
        spselectqual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectQualModel selectQualModel=(SelectQualModel) spselectqual.getSelectedItem();
                selectqual=selectQualModel.getQualification_id();
                Log.d("id", selectQualModel.getQualification_id());

//                Toast.makeText(AddCustomerActivity.this, flmod, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), spinc.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spjobtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectJtypeModel selectJtypeModel=(SelectJtypeModel) spjobtype.getSelectedItem();
                selectjbtype=selectJtypeModel.getJtype_id();
                Log.d("id", selectJtypeModel.getJtype_id());

//                Toast.makeText(AddCustomerActivity.this, flmod, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), spinc.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spjbstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectstatusModel selectstatusModel=(SelectstatusModel) spjbstatus.getSelectedItem();
                selectjbstatus=selectstatusModel.getJbstatus_id();
                Log.d("statusid", selectstatusModel.getJbstatus_id());

//                Toast.makeText(AddCustomerActivity.this, flmod, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), spinc.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        jbimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();//select image function

            }
        });

        cancelimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img.setImageDrawable(null);
                rv.setVisibility(View.GONE);

            }
        });

        //submitting datA
        smt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((jbtitle.getText().toString().equals(""))||((jblocation.getText().toString().equals(""))||
                       (jbsalry.getText().toString().equals(""))||
                        (jbinterview_time.getText().toString().equals(""))|| (jbopenings.getText().toString().equals(""))||
                        (jbdesc.getText().toString().equals("")))){

                    Toast.makeText(getApplicationContext(), "Please Enter Customer name", Toast.LENGTH_SHORT).show();

                }
                else {


                    if(img.getDrawable()==null){
                        uploadData();
                    }
                    else {
                        uploadBitData(bitmap);

                    }

                    progress=new ProgressDialog(PostJobCmpActivity.this);
                    progress.setMessage("Please Wait");
                    progress.show();
                }


            }
        });

        //date picker button
        ivdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });


        //changing date format


        String d=ivdate.getText().toString();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //String datestring=sdf.format(d);

        try {
            Date date = format.parse(d);
            changeddate=sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // listener for our pick date button
        jbinterview_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(PostJobCmpActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.


                                time=(hourOfDay + ":" + minute);
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                                SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
                                Date dt;
                                try {
                                    dt = sdf.parse(time);
                                    System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
                                    jbinterview_time.setText(sdfs.format(dt));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });


        getSpringData();//for spinner api fetching
        //toolbar with back button

        toolbar=findViewById(R.id.toolbaraddeq);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

//selecting images from camera or gallery
    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(icamera, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                    }
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "select picture"),2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//for single image camera
            if (requestCode == 1) {

                Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
                rv.setVisibility(View.VISIBLE);
                img.setImageBitmap(bitmap);
                getFileDataFromDrawable(bitmap);


            } else if (requestCode == 2) {//for multiimage
                //getting the image Uri
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;

                    Toast.makeText(getApplicationContext(), "Please select Single image", Toast.LENGTH_SHORT).show();

                    while (currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        currentItem = currentItem + 1;
                        try {
                            uriList.add(imageUri);
                            imagesEncodedList.add(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri));  // Code to handle multiple images
//                            encodeBitmapImg(bitmap);

                            Log.d("imageUri", String.valueOf(imageUri));

                        } catch (Exception e) {
//                        Log.e( "File select error", e);
                        }
                    }

                } else if (data.getData() != null) {//for single img gallery

                    final Uri imageUri2 = data.getData();

                    try {

                        uriList.add(imageUri2);//for multiimage
                        Log.d("imageUri2", String.valueOf(imageUri2));//for multiimage
                        Log.d("imageUri2Bitmap", String.valueOf(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri2)));

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri2);//single image display
                        rv.setVisibility(View.VISIBLE);
                        img.setImageBitmap(bitmap);//single image display
                        getFileDataFromDrawable(bitmap);


                    } catch (Exception e) {
//                    Log.e(TAG, "File select error", e);
                    }

                }
            }
        }
    }


    public void getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        bytesofimage=byteArrayOutputStream.toByteArray();
        Log.d("img", String.valueOf(bytesofimage));
    }


    //for getting spring data
    private void getSpringData() {
        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1=d+"candidate_info/get_qualification_list.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //  Toast.makeText(getApplicationContext()(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    JSONArray jsonArray=respObj.getJSONArray("qulification_details");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String spid=jsonObject.optString("qualification_id");
                        String spname=jsonObject.optString("qualification_name");

                        selquallist.add(new SelectQualModel(spid,spname));
//                        Log.d("s", String.valueOf(cllist));
                        selectQualadapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,selquallist);
                        selectQualadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spselectqual.setAdapter(selectQualadapter);


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



    ///date picker function
    private void initDatepicker() {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);



        String date=makeDateString(day,month+1,year);
        Log.d("dtttt",date);
        ivdate.setText(date);




        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month=month+1;
                String date=makeDateString(dayOfMonth,month,year);
                ivdate.setText(date);
            }

        };
        /*
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);


         */
        int style= AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        datePickerDialog=new DatePickerDialog(PostJobCmpActivity.this,style,dateSetListener,year,month,day);
    }
///date picker function


    private String makeDateString(int dayOfMonth, int month, int year) {

        int mth= month;
        String fm=""+mth;
        String fd=""+dayOfMonth;
        if(mth<10){
            fm ="0"+mth;
        }
        if (dayOfMonth<10){
            fd="0"+dayOfMonth;
        }
        String date= fd+"-"+fm+"-"+year;

        return date;
    }

    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);


    }

//uploading image to server
    public void uploadBitData(Bitmap bitmap) {
        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();
        //getting the tag from the edittext
//        final String tags = editTextTags.getText().toString().trim();
        String url = d+"candidate_info/company_post_job.php";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            progress.dismiss();

                            Intent i=new Intent(getApplicationContext(), PstedJobListCmpActivity.class);
                            startActivity(i);
                            finish();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        if(error.getMessage().equals("java.lang.NullPointerException: Attempt to get length of null array")){
                            progress.dismiss();
                            Toast.makeText(getApplicationContext(), "Please Select Photo", Toast.LENGTH_LONG).show();
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
                params.put("company_id",cmp_id);
                params.put("company_name",cmp_name);
                params.put("qualification_id",selectqual);
                params.put("job_title",jbtitle.getText().toString());
                params.put("bu_cat_id","1");
                params.put("job_type",selectjbtype);
                params.put("job_salary",jbsalry.getText().toString());
                params.put("job_education",jbeducation.getText().toString());
                params.put("job_location",jblocation.getText().toString());
                params.put("job_note",jbdesc.getText().toString());
                params.put("job_interview_date",changeddate);
                params.put("job_interview_time",time);
                params.put("job_opening", jbopenings.getText().toString());
                params.put("job_status",selectjbstatus);

//                    params.put("hw_material_file",datecal.getText().toString().trim());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();

                    /*
                    for (int i = 0; i < images.size(); i++) {

                        long imageName2 = System.currentTimeMillis();
                        params.put("images["+i+"]", new DataPart(imageName2 + ".jpg", getFileDataFromDrawableData(images.get(i))));
                    }
                    */
                params.put("gal_image", new DataPart(imagename + ".png", bytesofimage));

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);
    }


    //fetching api if image is not present
    private void uploadData() {
        DomainName domainName=new DomainName();
        String d=domainName.getDomain_name();
        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url = d+"candidate_info/company_post_job.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");

                    progress.dismiss();
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();


                    Intent i=new Intent(getApplicationContext(), PstedJobListCmpActivity.class);
                    startActivity(i);
                    finish();

                    Log.d("message",msg);


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

                Map<String,String> params=new HashMap<>();
                params.put("company_id",cmp_id);
                params.put("company_name",cmp_name);
                params.put("qualification_id",selectqual);
                params.put("job_title",jbtitle.getText().toString());
                params.put("bu_cat_id","1");
                params.put("job_type",selectjbtype);
                params.put("job_salary",jbsalry.getText().toString());
                params.put("job_education",jbeducation.getText().toString());
                params.put("job_location",jblocation.getText().toString());
                params.put("job_note",jbdesc.getText().toString());
                params.put("job_interview_date", changeddate);
                params.put("job_interview_time",time);
                params.put("job_opening", jbopenings.getText().toString());
                params.put("job_status", selectjbstatus);
                return params;
            }
        };

        queue.add(request);

    }

    public void opendialogue(View view) {
    }
}