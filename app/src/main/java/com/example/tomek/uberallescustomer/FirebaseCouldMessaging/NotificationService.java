package com.example.tomek.uberallescustomer.FirebaseCouldMessaging;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.tomek.uberallescustomer.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.tomek.uberallescustomer.FirebaseCouldMessaging.NotificationService.Type.CMFareCancellation;
import static com.example.tomek.uberallescustomer.FirebaseCouldMessaging.NotificationService.Type.CMFareConfirmation;
import static com.example.tomek.uberallescustomer.FirebaseCouldMessaging.NotificationService.Type.CMLocalisationUpdate;
import static com.example.tomek.uberallescustomer.LogedUserData.upadateLocation;

public class NotificationService extends FirebaseMessagingService {

    enum Type {
        CMLocalisationUpdate,
        CMFareConfirmation,
        CMFareCancellation
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null ) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().toString());
            Type type = getTypeFromMessage(remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            switch (type) {
                case CMLocalisationUpdate:
                    upadateLocation(data.get("driverPhone"), data.get("latitude"), data.get("longitude"));
                    break;
                case CMFareConfirmation:
                    /*uruchomienie popup'a który wyświetli numer telefonu kierowcy który akceptował
                     * ogólnie po kliknęciu w strzałkę na mapie i przejściu do storny gdzie wyświetla sie szacunkowy
                      * czas oczekiwania oraz po kliknęciu zamów nie powinno sie przechodzic do podsumowania, tylko
                      * spowrotem do fragmentu zamawiania jednocześcnie wyświetlajac popup że "twoje zlecenie czeka na potwierdzenie
                      * przez kierowce (przechowujemy je w shared lub w operacyjnej, zeby je móc potem odczytac) nie bedzie tez
                      * ono widoczne w historii. dopiero po potwierdzeniu przez kierowce znajdzie sie
                      * ono w historii gdzie bedzie mozna podejzec dane kierowcy i miejsce na mapie gdzie sie znajduje aktualne*/
                    String driverPhone = data.get("driverPhone");
                    String fareId = data.get("id");
                    String driverName = data.get("driverName");
                    String carName = data.get("carName");
                    String carPlateNumber = data.get("carPlateNumber");
                    break;
                case CMFareCancellation:
                    break;
            }
        }
        if (remoteMessage.getNotification() != null ) {
            showNotification(remoteMessage);
        }
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
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

    private Type getTypeFromMessage(Map<String, String> data) {
        String type = data.get("type");
        if (type.equals(CMLocalisationUpdate.name())) return CMLocalisationUpdate;
        else if (type.equals(CMFareConfirmation.name())) return CMFareConfirmation;
        else return CMFareCancellation;
    }
}
