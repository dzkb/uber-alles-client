package com.example.tomek.uberallescustomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tomek.uberallescustomer.api.ApiClient;
import com.example.tomek.uberallescustomer.api.UserService;
import com.example.tomek.uberallescustomer.api.pojo.CreateAccount;
import com.example.tomek.uberallescustomer.api.pojo.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        request();
    }
    public void requestCreate() {
        UserService loginService =
                ApiClient.createService(UserService.class, "700800300", "dupa.8");
        CreateAccount acc = new CreateAccount(884056858, "pashhs","password","password");
        Call<User> call = loginService.createAccount(acc);
        call.enqueue(new Callback<User >() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // user object available
                    System.out.println("udalo sie");
                } else {
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });
    }
    public void request() {
        UserService loginService =
                ApiClient.createService(UserService.class, "884056858", "password");
        Call<User> call = loginService.basicLogin();
        call.enqueue(new Callback<User >() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // user object available
                    System.out.println("udalo sie");
                } else {
                    // error response, no access to resource?
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
