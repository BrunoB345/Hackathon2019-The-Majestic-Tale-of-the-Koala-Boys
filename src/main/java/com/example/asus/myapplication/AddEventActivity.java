package com.example.asus.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddEventActivity extends AppCompatActivity {

    private static int IMAGE_GALLERY_REQUEST = 20;
    Bitmap picture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){

            if(requestCode == IMAGE_GALLERY_REQUEST && data != null){

                Uri imageUri = data.getData();
                InputStream inputStream;


                try {
                    inputStream = getContentResolver().openInputStream(imageUri);


                    picture = BitmapFactory.decodeStream(inputStream);
                    if(sizeOf(picture) < 10748988)
                        ((ImageView)findViewById(R.id.imageView)).setImageBitmap(picture);
                    else
                        Toast.makeText(this, "Imagem muito grande", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Não foi possivel abrir a imagem", Toast.LENGTH_LONG).show();
                }

            }

        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }


    public void selectImg (View v) {

        Intent photoPickerIntent = new Intent (Intent.ACTION_PICK);


        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String path = pictureDirectory.getPath();
        Uri data = Uri.parse(path);
        photoPickerIntent.setDataAndType(data, "image/*");
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }


    public void addEvent(View view){
        String name = ((EditText) findViewById(R.id.editText1)).getText().toString();
        String date = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String hour = ((EditText) findViewById(R.id.editText3)).getText().toString();
        String description = ((EditText) findViewById(R.id.editText4)).getText().toString();
        String link = ((EditText) findViewById(R.id.editText5)).getText().toString();
        String location = ((EditText) findViewById(R.id.editText6)).getText().toString();
        String duration = ((EditText) findViewById(R.id.editText7)).getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("date", date);
        intent.putExtra("hour", hour);
        intent.putExtra("duration",duration);
        intent.putExtra("description", description);
        intent.putExtra("location", location);
        intent.putExtra("link", link);

        byte[] byteArray = null;
        if(picture != null) {
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            byteArray = bStream.toByteArray();
        }
        intent.putExtra("picture", byteArray);
        intent.putExtra("create", true);

        startActivity(intent);
        finish();
    }

}
