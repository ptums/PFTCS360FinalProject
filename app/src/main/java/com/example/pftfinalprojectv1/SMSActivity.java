package com.example.pftfinalprojectv1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SMSActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sms);

        // Toggle send sms button accessibility with on/off switch
        ToggleButton on_offToggleButton = findViewById(R.id.on_off);
        Button sendSMSButton = findViewById(R.id.send_sms);

        // set button to disabled by default
        sendSMSButton.setEnabled(false);
        on_offToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendSMSButton.setEnabled(isChecked);
            }
        });

        // go back to login screen
        TextView logOutTextView = findViewById(R.id.log_out);

        logOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SMSActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onButtonClick(View v) {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        } else {
            sendSMS();
        }
    }

    private void sendSMS() {

        EditText phoneText = findViewById(R.id.editTextPhone);
        String phoneNumber = String.valueOf(phoneText.getText());

        String message = "This is an example of an SMS sent to number: " + phoneNumber;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS Sent to: " + phoneNumber, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "SMS Failed to Send", Toast.LENGTH_SHORT).show();
        }
    }

}
