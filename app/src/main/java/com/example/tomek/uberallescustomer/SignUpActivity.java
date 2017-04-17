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
    EditText editTextCreatePhoneNumber;

    @OnClick(R.id.btn_signup)
    public void onSignUpButtonClick(View v) {
        Toast.makeText(this, "Tego sie nie spodziewales", Toast.LENGTH_SHORT).show();
        String password = editTextCreatePassword.getText().toString();
        String phoneNumber = editTextCreatePhoneNumber.getText().toString();
        String firstName = editTextCreateFirstName.getText().toString();
        String lastName = editTextCreateLastName.getText().toString();
        if (password.equals(editTextCreateConfirmPassword.getText().toString())) {
           if (phoneNumber.length() > 0 &&
                   firstName.length() > 0 &&
                   lastName.length() > 0 &&
                   password.length() > 0) {
               CreateAccount account = new CreateAccount(Integer.parseInt(phoneNumber), firstName, lastName, password);
               createAccount(account);
           } else {
               Toast.makeText(this, "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show();
           }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }
    public void createAccount(CreateAccount account) {
        UserService loginService =
                ApiClient.createService(UserService.class);
        Call<User> call = loginService.createAccount(account);
        final String phoneNumber= account.phoneNumber.toString();
        final String password= account.password;
        call.enqueue(new Callback<User >() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // user object available
                    Intent intent = new Intent(getBaseContext(), CustomerActivity.class);
                    intent.putExtra("phone_number", phoneNumber);
                    intent.putExtra("password", password);
                    System.out.println("udalo sie " + phoneNumber + password);
                    startActivity(intent);
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
