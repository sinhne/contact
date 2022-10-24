package com.example.contentprovider_trung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.contentprovider_trung.model.Contact;

import java.util.ArrayList;

public class DanhBa extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_PERMISSIONS= 1001;

    ListView lvDanhBa;
    ArrayList<Contact> dsDanhBa;
    ArrayAdapter<Contact> adapterDanhBa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_ba);

        if (ContextCompat.checkSelfPermission(
                DanhBa.this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DanhBa.this,new String[]{""+
                    "android.permission.READ_CONTACTS"},1001);
        }
        AnhXa();

        ShowAllContact();

    }

    private void ShowAllContact() {
        Uri uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor cursor= getContentResolver().query(uri, null,null,null,null);
        dsDanhBa.clear();
        while (cursor.moveToNext()){

            String tenCotName=ContactsContract.Contacts.DISPLAY_NAME;
            String tenCotPhone=ContactsContract.CommonDataKinds.Phone.NUMBER;

            int vitriCotName=cursor.getColumnIndex(tenCotName);
            int vitriCotPhone=cursor.getColumnIndex(tenCotPhone);

            String name=cursor.getString(vitriCotName);
            String phone=cursor.getString(vitriCotPhone);

            Contact contact =new Contact(name,phone);
            dsDanhBa.add(contact);
            adapterDanhBa.notifyDataSetChanged();
        }
    }

    private void AnhXa() {
        lvDanhBa=findViewById(R.id.lvDanhBa);
        dsDanhBa=new ArrayList<>();
        adapterDanhBa= new ArrayAdapter<>(
                DanhBa.this, android.R.layout.simple_list_item_1,dsDanhBa
        );
        lvDanhBa.setAdapter(adapterDanhBa);
    }
}