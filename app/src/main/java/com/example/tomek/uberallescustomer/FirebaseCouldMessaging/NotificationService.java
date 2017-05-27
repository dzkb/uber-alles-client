package com.example.tomek.uberallescustomer.FirebaseCouldMessaging;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import static android.R.attr.phoneNumber;
import static com.example.tomek.uberallescustomer.FirebaseCouldMessaging.NotificationService.Type.CMFareCompletion;
import static com.example.tomek.uberallescustomer.LogedUserData.typeOfNotification;
import static com.example.tomek.uberallescustomer.LogedUserData.driverPhone;

import com.example.tomek.uberallescustomer.PopUp;
import com.example.tomek.uberallescustomer.R;
import com.example.tomek.uberallescustomer.database.FeedReaderDbHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static android.R.attr.type;
import static android.content.ContentValues.TAG;
import static com.example.tomek.uberallescustomer.CustomerActivity.giveMeContext;
import static com.example.tomek.uberallescustomer.FirebaseCouldMessaging.NotificationService.Type.CMFareCancellation;
import static com.example.tomek.uberallescustomer.FirebaseCouldMessaging.NotificationService.Type.CMFareConfirmation;
import static com.example.tomek.uberallescustomer.FirebaseCouldMessaging.NotificationService.Type.CMLocalisationUpdate;
import static com.example.tomek.uberallescustomer.LogedUserData.USER_PHONE;
import static com.example.tomek.uberallescustomer.LogedUserData.upadateLocation;

public class NotificationService extends FirebaseMessagingService {

    private static final String CANCEL_MESSAGE = "Twój przejazd został anulowany przez kierowce";
    private static final String MESSAGE_TITLE = "UWAGA!";
    private static final String CONFIRM_MESSAGE = "Jeden z kierowców podjął Twój przejazd. ";
    private static final String CONFIRM_SUBMESSAGE = "Kliknij, aby wyświetlić dodatkowe informacje";
    private static final String COMPLETE_MSG = "Twój przejazd zakończył się";
    private static final String TOP_BAR_TITLE = "Aktualizacja statusu Twojego przejazdu";

    private FeedReaderDbHelper helper;

    public enum Type {
        CMLocalisationUpdate,
        CMFareConfirmation,
        CMFareCancellation,
        CMFareCompletion,
        CMFareRequest
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {
            helper = new FeedReaderDbHelper(giveMeContext());
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().toString());
            typeOfNotification = getTypeFromMessage(remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            Intent intent;
            switch (typeOfNotification) {
                case CMLocalisationUpdate:
                    upadateLocation(data.get("driverPhone"), data.get("latitude"), data.get("longitude"));
                    intent = new Intent(CMLocalisationUpdate.name());
                    intent.putExtra("latitude", Double.parseDouble(data.get("latitude")));
                    intent.putExtra("longitude", Double.parseDouble(data.get("longitude")));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    break;
                case CMFareConfirmation:
                    String driverPhoneNumber = data.get("driverPhone");
                    String fareId = data.get("id");
                    String driverName = data.get("driverName");
                    String carName = data.get("carName");
                    String carPlateNumber = data.get("carPlateNumber");
                    intent = new Intent(CMFareConfirmation.name())
                            .putExtra("driverPhone", driverPhoneNumber)
                            .putExtra("id", fareId)
                            .putExtra("driverName", driverName)
                            .putExtra("carName", carName)
                            .putExtra("carPlateNumber", carPlateNumber);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    createNotification(CMFareConfirmation.toString(), CONFIRM_MESSAGE, MESSAGE_TITLE, PopUp.class, CONFIRM_SUBMESSAGE,
                            driverPhoneNumber, fareId, driverName, carName, carPlateNumber);
                    helper.updateById(USER_PHONE, fareId, "confirmed");
                    break;
                case CMFareCancellation:
                    driverPhone = data.get("phoneNumber");
                    String id = data.get("id");
                    String which = data.get("origin");
                    intent = new Intent(CMFareCancellation.name())
                            .putExtra("phoneNumber", phoneNumber)
                            .putExtra("id", id)
                            .putExtra("origin", which);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    createNotification(CMFareCancellation.toString(), CANCEL_MESSAGE, MESSAGE_TITLE, PopUp.class, "Szukaj nowego kierowcy",
                            driverPhone, id, which);
                    helper.deleteById(USER_PHONE, id);
                    break;
                case CMFareCompletion:
                    String completedFareId = data.get("id");
                    createNotification(CMFareCancellation.toString(), COMPLETE_MSG, MESSAGE_TITLE, PopUp.class, "Dziękujemy");
                    helper.updateById(USER_PHONE, completedFareId, "completed");
                    break;
                default:
                    Log.i("INFO" + getClass().getName(), "Sekcja Default");
            }
        }
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage);
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        Log.d(TAG, "From: " + remoteMessage.getFrom());


    }

    public void showNotification(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        int mId = remoteMessage.getTtl();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_local_taxi_yellow_24dp)
                        .setContentTitle(title)
                        .setContentText(body);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }

    private void createNotification(String type, String message, String title, Class<?> cls, String subText, String... params) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification myNotication;
        Intent intent = new Intent(giveMeContext(), cls);
        intent.putExtra("typ", type);
        intent.putStringArrayListExtra("parameters", new ArrayList<String>(Arrays.asList(params)));
        PendingIntent pendingIntent = PendingIntent.getActivity(giveMeContext(), 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(giveMeContext());

        builder.setAutoCancel(false);
        builder.setTicker(TOP_BAR_TITLE);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_local_taxi_yellow_24dp);
        if (typeOfNotification != CMFareConfirmation)
            builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setOngoing(true);
        builder.setSubText(subText);   //API level 16
        builder.setNumber(100);
        builder.build();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);
    }

    private Type getTypeFromMessage(Map<String, String> data) {
        String type = data.get("type");
        if (type.equals(CMLocalisationUpdate.name())) return CMLocalisationUpdate;
        else if (type.equals(CMFareConfirmation.name())) return CMFareConfirmation;
        else if (type.equals(CMFareCancellation.name())) return CMFareCancellation;
        else return CMFareCompletion;
    }


}
