package com.app.paramjobsindia.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.paramjobsindia.CandidateMainActivity;
import com.app.paramjobsindia.CompanyMainActivity;
import com.app.paramjobsindia.CreateNewAccCandidateActivity;
import com.app.paramjobsindia.DomainName.DomainName;
import com.app.paramjobsindia.EditProfile;
import com.app.paramjobsindia.R;
import com.app.paramjobsindia.UploadCndDocumentsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ProfileFragment extends Fragment {

    ImageView edit,set_img,pick_img,edit_document;

    TextView name,education,email,contact,address
            ,experience,hometown,district;
    AlertDialog.Builder builder;
    LinearLayout layout1,layout2,edulayout,explayout,hmlayout,distlayout;

    SharedPreferences sharedPreferences_candidate;

    //create a share preferences name and keys name
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

    String type;
    private static final String FORMAT = "%02d:%02d";

    int seconds , minutes;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.profile_fragment, container, false);
        edit=view.findViewById(R.id.editprofile);
        set_img=view.findViewById(R.id.profileimg);
        pick_img=view.findViewById(R.id.upload_dp);
        edulayout=view.findViewById(R.id.edu);
        explayout=view.findViewById(R.id.exp);
        hmlayout=view.findViewById(R.id.hom);
        distlayout=view.findViewById(R.id.dist);

        //for displaying profile data of layout2
        name=view.findViewById(R.id.pcname);
        education=view.findViewById(R.id.pceducation);
        email=view.findViewById(R.id.pcemailid);
        contact=view.findViewById(R.id.pccontact);
        address=view.findViewById(R.id.pcaddress);
        experience=view.findViewById(R.id.pcexperience);
        hometown=view.findViewById(R.id.pchome);
        district=view.findViewById(R.id.pcdistrict);
        edit_document=view.findViewById(R.id.uploaddoc);



        sharedPreferences_candidate =getContext().getSharedPreferences(SHARED_PREF_NAME_CANDIDATE, Context.MODE_PRIVATE);

        String cname=sharedPreferences_candidate.getString(KEY_CANDIDATE_NAME,null);


        if(cname!=null){

            name.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_NAME,null));
            education.setText(sharedPreferences_candidate.getString(Key_CANDIDATE_EDUCATION,null));
            email.setText(sharedPreferences_candidate.getString(Key_CANDIDATE_EMAILID,null));
            contact.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_MOBILE,null));
            address.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_ADDRESS,null));
            experience.setText(sharedPreferences_candidate.getString(Key_CANDIDATE_EXPERIENCE,null));
            hometown.setText(sharedPreferences_candidate.getString(KEY_CANDIDATE_LOCAL,null));
            district.setText(sharedPreferences_candidate.getString(Key_CANDIDATE_DISTRICT,null));

        }


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getContext(), EditProfile.class);
                startActivity(i);

            }
        });

        edit_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getContext(), UploadCndDocumentsActivity.class);
                startActivity(i);

            }
        });
        pick_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });

        return view;
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        builder = new AlertDialog.Builder(getContext());
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
                set_img.setImageBitmap(bitmap);
//                getFileDataFromDrawable(bitmap);

            } else if (requestCode == 2) {
                if (data.getData() != null) {

                    final Uri imageUri = data.getData();
                    try {
                        //getting bitmap object from uri
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                        set_img.setImageBitmap(bitmap);
//                        getFileDataFromDrawable(bitmap);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
