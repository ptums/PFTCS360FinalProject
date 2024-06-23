package com.example.pftfinalprojectv1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {
    private TextView enter_employee_id_text, enter_full_name_text, enter_password_text, enter_e_mail;

    private UsersDb usersDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);

        enter_employee_id_text = findViewById(R.id.enter_employee_id_text);
        enter_full_name_text = findViewById(R.id.enter_full_name_text);
        enter_password_text = findViewById(R.id.enter_password_text);
        enter_e_mail = findViewById(R.id.enter_e_mail);


        usersDb = new UsersDb(this);
        usersDb.getWritableDatabase();

        // When the user clicks back to login
        // direct user to activity_main.xml
        TextView createAccountTextView = findViewById(R.id.go_home_screen_button);
        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginScreen();
            }
        });

    }

    public void goToLoginScreen() {
        Intent goToLoginScreen = new Intent(CreateAccountActivity.this, MainActivity.class);
        startActivity(goToLoginScreen);
    }

    public void createAccountBtn(View view) {
        String employeeId = enter_employee_id_text.getText().toString();
        String fullName = enter_full_name_text.getText().toString();
        String password = enter_password_text.getText().toString();
        String email = enter_e_mail.getText().toString();

        // Insert new item
        usersDb.addUser(employeeId, fullName, email, password);

        enter_employee_id_text.setText("");
        enter_full_name_text.setText("");
        enter_password_text.setText("");
        enter_e_mail.setText("");
        usersDb.close();
        Toast.makeText(this, "New account has been created, you are being redirected back to login screen", Toast.LENGTH_SHORT).show();
        goToLoginScreen();

    }
}
