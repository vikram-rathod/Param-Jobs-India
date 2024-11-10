package com.app.paramjobsindia.FirebaseMessaging;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConstantsValue {
    public static String DeviceId = "0";
    public static String UserID = "0";
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static Integer datacalltime = 30000;
    public static long DELAY_180 = 300;

    public static double latitude=0.0;
    public static double longitude=0.0;

    public static String getnormaldatenew() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return sdf.format(new Date());
    }

    public static void PostLocationData(Activity activity) {
        RequestQueue queue= Volley.newRequestQueue(activity);
        String url="https://gemstonews.in/gemstoneerp/apis/teacher/send_location.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, response -> {
            try
            {
                JSONObject jsonObject=new JSONObject(response);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show())
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> param=new HashMap<>();
                //param.put("userid", ConstantsValue.ID);
                param.put("user_id", ConstantsValue.UserID+"");
                param.put("latitude_id", ConstantsValue.latitude+"");
                param.put("longitude_id", ConstantsValue.longitude+"");
                return param;
            }
        };
        queue.add(request);
    }

    public static void GetFCMID() {
        final String[] token = {""};
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(task.isComplete()){
                token[0] = task.getResult();
                ConstantsValue.DeviceId = token[0];
                Log.e("AppConstants", "onComplete: new Token got: "+token[0] );
            }
        });
    }

    public static long gettimediff(String s) {
        long diffSecond = 0;
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String currenttime = format.format(new Date());
            Date d1 = format.parse(s);
            Date d2 = format.parse(currenttime);
            long diff = d2.getTime() - d1.getTime();
            diffSecond = diff / 1000 % 181;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diffSecond;
    }
}
