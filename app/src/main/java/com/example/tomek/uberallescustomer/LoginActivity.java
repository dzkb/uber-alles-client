package com.example.tomek.uberallescustomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_email)
    EditText emailEditText;
    @BindView(R.id.edit_text_password)
    EditText passwordEditText;


    @OnClick(R.id.forgot_password_text)
    public void onForgotPasswordTextClick(View v){
        Toast.makeText(this, "Forgot password implementation", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.sing_up_text)
    public void onSingupTextClick(View v){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick(View v){
        Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
