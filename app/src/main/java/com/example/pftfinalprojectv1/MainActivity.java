package com.example.pftfinalprojectv1;

import android.content.Intent;
import android.os.Bundle;

import android.database.Cursor;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView enter_employee_id_text, enter_password_text;

    private UsersDb usersDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        enter_employee_id_text = findViewById(R.id.enter_employee_id_text);
        enter_password_text = findViewById(R.id.enter_password_text);

        usersDb = new UsersDb(this);

        // When the user clicks create an account
        // direct user to activity_create_user.xml
        TextView createAccountTextView = findViewById(R.id.create_account_button);
        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        // When the user clicks notification settings
        // direct user to activity_sms.xml
        TextView notificationSettingsTextView = findViewById(R.id.notification_settings);
        notificationSettingsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SMSActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signIn(View view) {
        String employeeId = enter_employee_id_text.getText().toString();
        String password = enter_password_text.getText().toString();

        Cursor findUser = usersDb.findUser(employeeId, password);

        if(findUser != null && findUser.moveToFirst()) {
            findUser.close();
            Intent intent = new Intent(MainActivity.this, AllItemsActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Employee account not found!", Toast.LENGTH_SHORT).show();
        }
    }
}