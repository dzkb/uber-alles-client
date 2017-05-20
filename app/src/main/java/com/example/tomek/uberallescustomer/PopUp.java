package com.example.tomek.uberallescustomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.tomek.uberallescustomer.FirebaseCouldMessaging.NotificationService.Type.CMFareCancellation;
import static com.example.tomek.uberallescustomer.FirebaseCouldMessaging.NotificationService.Type.CMFareCompletion;
import static com.example.tomek.uberallescustomer.FirebaseCouldMessaging.NotificationService.Type.CMFareConfirmation;
import static com.example.tomek.uberallescustomer.LogedUserData.driverPhone;
import static com.example.tomek.uberallescustomer.LogedUserData.typeOfNotification;


public class PopUp extends AppCompatActivity {

    @BindView(R.id.info_cancel_confirm)
    TextView info_cancel_confirm;
    @BindView(R.id.ok_btn)
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String typ = intent.getStringExtra("typ");
        if (typeOfNotification == CMFareCompletion)
            info_cancel_confirm.setText(R.string.completion_info);
        else {
            info_cancel_confirm.setText("Wybrany kierowca o numerze " + driverPhone + " anulował zlecenie wykonania Twojego przejazdu");
        }
    }

    @OnClick(R.id.ok_btn)
    public void onLoginButtonClick(View v) {
        finish();
    }
}
