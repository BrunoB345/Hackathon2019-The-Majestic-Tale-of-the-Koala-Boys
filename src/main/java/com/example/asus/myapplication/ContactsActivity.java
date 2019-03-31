package com.example.asus.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ContactsActivity extends AppCompatActivity {

    private static final int PICK_CONTACT = 1000;

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    final String EVENT_SUCCESSFULLY_SHARED = "Amigos convidados!";
    final String PERMISSION_DENIED = "Permissao negada";
    final String SMS_TEXT = "Olá %s, estás interessado em ir comigo a %s no dia %s às %s?";
    // Identifier for the permission request
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;
    TextView number;
    FloatingActionButton send;
    String contactName, eventName,day,hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        number = findViewById(R.id.inputNumber);
        send = findViewById(R.id.buttonSend);

         eventName = getIntent().getStringExtra("name");
         day = getIntent().getStringExtra("date");
         hour = getIntent().getStringExtra("hour");

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

        if(!checkPermission(Manifest.permission.READ_CONTACTS)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    READ_CONTACTS_PERMISSIONS_REQUEST);
        }

    }

    public void pickAContactNumber(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    contactPicked(data);
                   /* Uri contactData = data.getData();
                    Cursor phone = getContentResolver().query(contactData, null, null, null, null);

                    if (phone.moveToFirst()) {
                       String name = phone.getString(phone.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        // Todo something when contact number selected

                        String selection = "DISPLAY_NAME == " + name;
                        String[] projection = new String[] {
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                        };

                        Cursor crContacts = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, selection, null, null);
                        String phoneNum = "Numero não encontrado";
                        if(crContacts.moveToFirst())
                           phoneNum = crContacts.getString(crContacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        number.setText(phoneNum);
                    }*/
                }
                break;
        }
    }



        private void contactPicked(Intent data) {
            Cursor cursor = null;
            try {
                String phone, name;
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();
                phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                number.setText(phone);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public void onSend(View v){

        //String allNumbers = number.getText().toString().trim();
        //String[] phoneNumberArray = allNumbers.split(allNumbers);



        String phoneNumber = number.getText().toString();
       // String smsMessage = message.getText().toString();

        if(phoneNumber.length() == 0 /*|| smsMessage.length() == 0*/)
        {
            throw new RuntimeException("Invalid values!!!");
        }


        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null,
                    "Olá " + contactName + ", estás interessado em ir comigo a " + eventName + " no dia " + day + " às " + hour +"?",
                    null,null);
            Toast.makeText(this, EVENT_SUCCESSFULLY_SHARED, Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(this, PERMISSION_DENIED, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (PackageManager.PERMISSION_GRANTED == check);
    }
}