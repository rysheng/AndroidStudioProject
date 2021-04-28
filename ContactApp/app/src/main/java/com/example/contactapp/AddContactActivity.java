package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {
    private EditText edt_name, edt_email, edt_mobie, edt_infor;
    private int cout = 0;
    private static ArrayList<Contact> mContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_mobie = findViewById(R.id.edt_mobie);
        edt_infor = findViewById(R.id.edt_info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:

                Contact newContact = new Contact( 0,  edt_name.getText().toString(),
                                                    edt_mobie.getText().toString(),
                                                    edt_email.getText().toString(),
                                                    edt_infor.getText().toString());
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",newContact);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
        case R.id.menu_cancel:
            finish();
        case R.id.menu_back:
            finish();
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}