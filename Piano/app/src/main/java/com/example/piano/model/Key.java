package com.example.piano.model;

import android.graphics.RectF;

public class Key {
    public final int sound;
    public final RectF rect;
    public boolean isPressed;
    public Key(int sound, RectF rect, boolean isPressed) {
        this.sound = sound;
        this.rect = rect;
        this.isPressed = isPressed;
    }
}
