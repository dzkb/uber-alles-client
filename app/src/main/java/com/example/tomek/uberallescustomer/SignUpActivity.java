package com.example.tomek.uberallescustomer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.tomek.uberallescustomer.api.ApiClient;
import com.example.tomek.uberallescustomer.api.UserService;
import com.example.tomek.uberallescustomer.api.pojo.CreateAccount;
import com.example.tomek.uberallescustomer.api.pojo.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_create_first_name)
    EditText editTextCreateFirstName;
    @BindView(R.id.edit_text_create_last_name)
    EditText editTextCreateLastName;
    @BindView(R.id.edit_text_create_password)
    EditText editTextCreatePassword;
    @BindView(R.id.edit_text_create_confirm_password)
    EditText editTextCreateConfirmPassword;
    @BindView(R.id.edit_text_create_phone_number)
    EditText editTextCreateConfirmPhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }
    public void createAccount() {
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
}
