package com.example.tomek.uberallescustomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
        String[] parameters = intent.getStringArrayExtra("parameters");
        //if (typeOfNotification == CMFareConfirmation)
            info_cancel_confirm.setText("dupa");
    }

    @OnClick(R.id.ok_btn)
    public void onLoginButtonClick(View v) {
        finish();
    }
}
