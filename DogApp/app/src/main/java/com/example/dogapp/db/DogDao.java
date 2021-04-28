package com.example.dogapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dogapp.model.DogBreed;

import java.util.List;

@Dao
public interface DogDao {
    @Query("SELECT * FROM Dogbreed")
    public List<DogBreed> getAllDogs();

    @Insert
    public void insertDog(DogBreed... dogBreed);

    @Query("DELETE FROM dogbreed")
    public void deleteAll();

    @Query("SELECT * FROM DOGBREED WHERE Name LIKE '%' || :name || '%'")
    public  List<DogBreed> searchByName(String name);
}
