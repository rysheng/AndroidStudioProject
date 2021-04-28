package com.example.piano.view;

import android.animation.RectEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.piano.R;
import androidx.annotation.Nullable;

import com.example.piano.model.Key;
import com.example.piano.util.SoundManager;

import java.net.InterfaceAddress;
import java.util.ArrayList;

public class PianoView extends View {
    public static final int Number_Keys = 14;
    private Paint black, white, yellow;
    private ArrayList<Key> whites;
    private ArrayList<Key> blacks;
    private  int keyWidth, keyHeight;
    ArrayList<Integer> k = new ArrayList<>();
    private  SoundManager msoundmanager;
    public PianoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        whites = new ArrayList<>();
        blacks = new ArrayList<>();

        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);

        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);

        msoundmanager = new SoundManager();

        msoundmanager = msoundmanager.getInstance();
        msoundmanager.init(getContext());
    }

    public  void playsound(int soundID){
        switch (soundID){
            case 1:
                msoundmanager.playSound(R.raw.c3);
                break;
            case 2:
                msoundmanager.playSound(R.raw.d3);
                break;
            case 3:
                msoundmanager.playSound(R.raw.e3);
                break;
            case 4:
                msoundmanager.playSound(R.raw.f3);
                break;
            case 5:
                msoundmanager.playSound(R.raw.g3);
                break;
            case 6:
                msoundmanager.playSound(R.raw.a3);
                break;
            case 7:
                msoundmanager.playSound(R.raw.b3);
                break;
            case 8:
                msoundmanager.playSound(R.raw.c4);
                break;
            case 9:
                msoundmanager.playSound(R.raw.d4);
                break;
            case 10:
                msoundmanager.playSound(R.raw.e4);
                break;
            case 11:
                msoundmanager.playSound(R.raw.f4);
                break;
            case 12:
                msoundmanager.playSound(R.raw.g4);
                break;
            case 13:
                msoundmanager.playSound(R.raw.a4);
                break;
            case 14:
                msoundmanager.playSound(R.raw.b4);
                break;
            case 15:
                msoundmanager.playSound(R.raw.db3);
                break;
            case 16:
                msoundmanager.playSound(R.raw.eb3);
                break;
            case 17:
                msoundmanager.playSound(R.raw.gb3);
                break;
            case 18:
                msoundmanager.playSound(R.raw.ab3);
                break;
            case 19:
                msoundmanager.playSound(R.raw.bb3);
                break;
            case 20:
                msoundmanager.playSound(R.raw.db4);
                break;
            case 21:
                msoundmanager.playSound(R.raw.eb4);
                break;
            case 22:
                msoundmanager.playSound(R.raw.gb4);
                break;
            case 23:
                msoundmanager.playSound(R.raw.ab4);
                break;
            case 24:
                msoundmanager.playSound(R.raw.bb4);
                break;
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        keyWidth = w/ Number_Keys;
        keyHeight = h/2;
        int count1 = 15;
        int count2 = 15;

        for(int i=0; i<Number_Keys; i++){
            int left = i*keyWidth;
            int right = left + keyWidth;


            if(i==Number_Keys-1){ right = w;}

            RectF rect = new RectF(left, h/2, right, h);
            whites.add(new Key(i+1, rect, false));


            RectF rect2 = new RectF(left, 0, right, h/2);
            whites.add(new Key(15-(i+1), rect2, false));
            //Khởi tạo phim màu đen
            if(i!=0 && i!=3 && i!=7 && i!=10){
                rect = new RectF((float)(i-1)*keyWidth+0.75f*keyWidth
                        ,h/2
                        ,(float)(i)*keyWidth+0.25f*keyWidth
                        ,1.5f*keyHeight);
                blacks.add(new Key(count1, rect, false));
                count1++;
            }
            if(i!=0 && i!=4 && i!=7 && i!=11 && i!=14){
                rect2 = new RectF((float)(i-1)*keyWidth+0.75f*keyWidth
                        ,0.5f*keyHeight
                        ,(float)(i)*keyWidth+0.25f*keyWidth
                        ,h/2);
                blacks.add(new Key(39-count2, rect2, false));
                count2++;
            }

        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Vẽ phím trắng
        for(Key key:whites){
            canvas.drawRect(key.rect, key.isPressed? yellow: white);
        }
        // Vẽ các đường thẳng giữa các phím trắng
        for( int i=1; i<Number_Keys; i++){
            canvas.drawLine(i*keyWidth, 0, i*keyWidth, 2*keyHeight, black);
        }
        for(Key key:blacks){
            canvas.drawRect(key.rect, key.isPressed? yellow: black);
        }
        canvas.drawLine(0, keyHeight, keyWidth*Number_Keys, keyHeight, black);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean isDownAction = action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_MOVE;

        for(int touchIndex=0; touchIndex < event.getPointerCount(); touchIndex++){
            float x = event.getX(touchIndex);
            float y = event.getY(touchIndex);
            int  BlackPressed = 0;
            int i=0;
            if(action == MotionEvent.ACTION_UP){
                k.clear();
                for(Key key:blacks){
                    key.isPressed = false;
                }
                for(Key key:whites){
                    key.isPressed = false;
                }
            }
            for(Key k:blacks){
                if(k.rect.contains(x,y)){
                    k.isPressed = isDownAction;
                    playsound(k.sound);
                    BlackPressed = 1;
                    break;
                }
            }
            if (BlackPressed==1) continue;
            for(Key k:whites){
                if(k.rect.contains(x,y)){
                    playsound(k.sound);
                    k.isPressed = isDownAction;
                }
            }
        }
        invalidate();
//        return super.onTouchEvent(event);
        return true;
    }
}
