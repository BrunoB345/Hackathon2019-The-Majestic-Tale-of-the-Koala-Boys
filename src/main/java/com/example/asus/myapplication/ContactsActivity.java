package com.example.asus.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    final String EVENT_SUCCESSFULLY_SHARED = "Amigos convidados!";
    final String PERMISSION_DENIED = "Permissao negada";

    EditText number, message;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        number = findViewById(R.id.inputNumber);
        message = findViewById(R.id.inputMessage);
        send = findViewById(R.id.buttonSend);

        // send.setEnabled(false);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSend(v);
            }
        });

        if(checkPermission(Manifest.permission.SEND_SMS)){
            send.setEnabled(true);
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);
        }

    }

    public void onSend(View v){

        //String allNumbers = number.getText().toString().trim();
        //String[] phoneNumberArray = allNumbers.split(allNumbers);



        String phoneNumber = number.getText().toString();
        String smsMessage = message.getText().toString();

        if(phoneNumber.length() == 0 || smsMessage.length() == 0)
        {
            throw new RuntimeException("Invalid values!!!");
        }


        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, smsMessage, null,
                    null);
            Toast.makeText(this, EVENT_SUCCESSFULLY_SHARED, Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(this, PERMISSION_DENIED, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission(String premission){
        int check = ContextCompat.checkSelfPermission(this, premission);
        return (PackageManager.PERMISSION_GRANTED == check);
    }
}