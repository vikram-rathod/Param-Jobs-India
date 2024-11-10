package com.app.paramjobsindia.FirebaseMessaging;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.app.paramjobsindia.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null)
        {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        }

        if (remoteMessage.getData().size() > 0)
        {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try
            {
                Map<String, String> params = remoteMessage.getData();
                JSONObject json = new JSONObject(params);
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    @Override
    public void onNewToken(@NotNull String token) {
        Log.d("TAG", "Refreshed token: " + token);
        ConstantsValue.DeviceId = token;
    }

    private void handleNotification(String message,String title) {
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        showNotificationMessage(getApplicationContext(), title, message, ConstantsValue.getnormaldatenew(), resultIntent);
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try
        {
            //JSONObject data = json.getJSONObject("data");
            String title = json.getString("title");
            String message = json.getString("body");
            boolean isBackground = json.getBoolean("is_background");
            String imageUrl = json.getString("image");
            String timestamp = json.getString("timestamp");
            String click_action = json.getString("click_action");
            String notificationid = json.getString("notificationid");
            //JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
           // Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);
            Log.e(TAG, "notificationid: " + notificationid);


            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            resultIntent.putExtra("click_action", click_action);
            resultIntent.putExtra("notificationid", notificationid);

            if (TextUtils.isEmpty(imageUrl))
            {
                showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
            }
            else
            {
                showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
            }
        }
        catch (JSONException e)
        {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        }
        catch (Exception e)
        {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
