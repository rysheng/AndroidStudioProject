package com.example.dogapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class DogBreed implements Serializable {
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    private int id;

    @SerializedName("name")
    @ColumnInfo(name="name")
    private String name;

    @SerializedName("life_span")
    @ColumnInfo(name = "life_span")
    private String lifeSpan;

    @SerializedName("origin")
    @ColumnInfo(name = "origin")
    private String origin;

    @SerializedName("url")
    private String url;

    @SerializedName("bred_for")
    @ColumnInfo(name = "bred_for")
    private String bred_for;

    @SerializedName("breed_group")
    @ColumnInfo(name = "breed_group")
    private String breed_group;

    public boolean isShowMenu(){return showMenu;}
    public void setShowMenu(boolean showMenu){this.showMenu = showMenu;}

    @SerializedName("temperament")
    private String temperament;

    private boolean showMenu=false;

    public String getBred_for() {
        return bred_for;
    }

    public void setBred_for(String bred_for) {
        this.bred_for = bred_for;
    }

    public String getBreed_group() {
        return breed_group;
    }

    public void setBreed_group(String breed_group) {
        this.breed_group = breed_group;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DogBreed(int id, String name, String lifeSpan, String origin, String url, String bred_for, String breed_group, String temperament) {
        this.id = id;
        this.name = name;
        this.lifeSpan = lifeSpan;
        this.origin = origin;
        this.url = url;
        this.bred_for = bred_for;
        this.breed_group = breed_group;
        this.temperament = temperament;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}

