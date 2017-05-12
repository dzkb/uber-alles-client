package com.example.tomek.uberallescustomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopUp extends AppCompatActivity {

    @BindView(R.id.popUpType)
    TextView type;
    @BindView(R.id.numberOfReporter)
    TextView numberOfReporter;
    @BindView(R.id.otherInfo)
    TextView otherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String typ = intent.getStringExtra("typ");
        String [] parameters = intent.getStringArrayExtra("parameters");
        numberOfReporter.setText(parameters[0]);
        otherInfo.setText(parameters[1]);
        type.setText(typ);

    }


}
