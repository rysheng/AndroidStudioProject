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

public class Show_infor extends AppCompatActivity {
    private EditText infor_name, infor_mobile, infor_email, infor_info;
    private int position, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_infor);
        infor_name = findViewById(R.id.infor_name);
        infor_mobile = findViewById(R.id.infor_mobie);
        infor_email = findViewById(R.id.infor_email);
        infor_info = findViewById(R.id.infor_info);
        position = getIntent().getIntExtra("position", 0);
        id = getIntent().getIntExtra("id", 0);
        if(getIntent().hasExtra("name")){
            infor_name.setText(getIntent().getStringExtra("name"));
            infor_mobile.setText(getIntent().getStringExtra("phone"));
            infor_email.setText(getIntent().getStringExtra("email"));
            infor_info.setText(getIntent().getStringExtra("infor"));
        }
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
                Intent intent = new Intent();
                intent.putExtra("position_get", position);
                intent.putExtra("id_get", id);
                intent.putExtra("name_get", infor_name.getText().toString());
                intent.putExtra("phone_get", infor_mobile.getText().toString());
                intent.putExtra("email_get", infor_email.getText().toString());
                intent.putExtra("infor_get", infor_info.getText().toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
            case R.id.menu_cancel:
                Intent i = new Intent();
                i.putExtra("position_get", position);
                i.putExtra("id_get", id);
                setResult(Activity.RESULT_CANCELED, i);
                finish();
            case R.id.menu_back:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}