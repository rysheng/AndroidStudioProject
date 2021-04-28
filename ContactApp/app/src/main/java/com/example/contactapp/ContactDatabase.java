package com.example.contactapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Contact.class  , version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactDao  contactDao();
    public static ContactDatabase instance;
    public static ContactDatabase getInstance(Context mContext){
        if(instance == null){
            instance = Room.databaseBuilder(mContext,
                    ContactDatabase.class, "database_contact").build();
        }
        return instance;
    }
}
