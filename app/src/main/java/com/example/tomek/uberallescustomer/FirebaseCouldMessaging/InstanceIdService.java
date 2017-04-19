package com.example.tomek.uberallescustomer.FirebaseCouldMessaging;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;
import static com.example.tomek.uberallescustomer.FirebaseCouldMessaging.Connect.call;


public class InstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(final String refreshedToken) {

        new Thread() {
            @Override
            public void run() {
                String resp = call("https://uberalles.herokuapp.com/test/messaging?to=" + refreshedToken + "&notification=Zapomniałeś hasła? Twój problem");
                Log.d(TAG, "RESPONSE: " + resp);
            }
        }.start();

    }
}
