package com.example.tomek.uberallescustomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tomek.uberallescustomer.FirebaseCouldMessaging.InstanceIdService;
import com.example.tomek.uberallescustomer.api.ApiClient;
import com.example.tomek.uberallescustomer.api.UserService;
import com.example.tomek.uberallescustomer.api.pojo.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tomek.uberallescustomer.LogedUserData.USER_PASSWORD;
import static com.example.tomek.uberallescustomer.LogedUserData.USER_NAME;
import static com.example.tomek.uberallescustomer.LogedUserData.USER_SURNAME;
import static com.example.tomek.uberallescustomer.LogedUserData.USER_PHONE;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_phone_number)
    EditText phoneNumberEditText;
    @BindView(R.id.edit_text_password)
    EditText passwordEditText;


    @OnClick(R.id.forgot_password_text)
    public void onForgotPasswordTextClick(View v) {
        new InstanceIdService().onTokenRefresh();
        Toast.makeText(this, "Forgot password implementation", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.sing_up_text)
    public void onSingupTextClick(View v){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick(View v) {
        String phoneNumber = phoneNumberEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(phoneNumber.length() > 0 && password.length() > 0 ) {
            checkCredentials(phoneNumber, password);
        } else {
            Toast.makeText(this, "Podaj dane logowania", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }
    public void wrongPasswordToast() {
        Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_SHORT).show();
    }
    public void checkCredentials(String phoneNumber, final String password) {
        UserService userService =
                ApiClient.createService(UserService.class, phoneNumber, password);
        Call<User> call = userService.basicLogin();
        call.enqueue(new Callback<User >() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    USER_NAME = response.body().firstName;
                    USER_SURNAME = response.body().lastName;
                    USER_PHONE = response.body().phoneNumber;
                    USER_PASSWORD = password;
                    System.out.println("udalo sie");
                    Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
                    startActivity(intent);
                } else {
                    // error response, no access to resource?
                    wrongPasswordToast();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });
    }
}
