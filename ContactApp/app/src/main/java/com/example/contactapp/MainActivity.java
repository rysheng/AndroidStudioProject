package com.example.contactapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Contact> contacts;
    private RecyclerView rv_contact;
    private MyAdapter myAdapter;
    private SearchView searchView;

    private ContactDatabase contactDatabase;
    private ContactDao contactDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.sv_search);
        rv_contact = findViewById(R.id.rv_contact);
        rv_contact.setLayoutManager(new LinearLayoutManager(this));
        contacts = new ArrayList<Contact>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                contactDatabase = ContactDatabase.getInstance(getApplicationContext());
                contactDao = contactDatabase.contactDao();
                List<Contact> dbcontacts = contactDao.getAllContact();
                for(Contact contact: dbcontacts){
                    contacts.add(contact);
                }
                myAdapter.notifyDataSetChanged();
            }
        });
        myAdapter = new MyAdapter(contacts, this);
        rv_contact.setAdapter(myAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myAdapter.getFilter().filter(s);
                return false;
            }
        });

    }

    public void onClickAddContact(View v){
        Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK && requestCode == 123) {
            Contact newContact = (Contact) data.getSerializableExtra("result");
            contacts.add(newContact);
            myAdapter = new MyAdapter(contacts, this);
            rv_contact.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    contactDao.insertContact(newContact);
                }
            });
        }
        if (resultCode == RESULT_OK && requestCode == 456){
            int position = data.getIntExtra("position_get", 0);
            Contact contact = new Contact(data.getIntExtra("id_get", 0),
                    data.getStringExtra("name_get"),
                    data.getStringExtra("phone_get"),
                    data.getStringExtra("email_get"),
                    data.getStringExtra("infor_get"));
            contacts.set(position, contact);
            myAdapter.notifyDataSetChanged();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    contactDao.update(contact);
                }
            });
        }
        if (resultCode == RESULT_CANCELED && requestCode == 456){
            int position = data.getIntExtra("position_get", 0);
            int id = data.getIntExtra("id_get", 0);
            contacts.remove(position);
            myAdapter.notifyDataSetChanged();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    contactDao.delete(id);
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}