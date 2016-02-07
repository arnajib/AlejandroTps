package com.tp1.inf8405.flowfree;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.tp1.inf8405.flowfree.ImageAdapter;

public class Game extends Activity {
    int i=0, j=0;
    int firstClick,secondClick;
    int firstTouch,secondTouch;
    GridView gridview;
    final ImageAdapter im = new ImageAdapter(this);
    int posTouchDown = 0;
    int posTouchUp = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        gridview.setAdapter(im);
        gridview.invalidateViews();
        addListenerToGrid();
        im.notifyDataSetChanged();



//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                i++;
//                if (i % 2 != 0) {
//                    firstClick = arg2;
//                    Toast.makeText(Game.this, "" + arg2,
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    secondClick = arg2;
//                    Integer help = new Integer((int) im.getItemIdTabPrincipal(firstClick));
//                    im.setItemInTabPrincipal(firstClick, secondClick);
//                    im.setItemInteger(secondClick, help);
//                    im.notifyDataSetChanged();
//
//                }
//
//            }
//        });
    }
    public void addListenerToGrid() {
        gridview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {

                int action = me.getActionMasked();
                float currentXPosition;
                float currentYPosition;


//                if (me.getAction() == MotionEvent.ACTION_DOWN) {
//                    //Toast.makeText(Game.this, " Touch Down: " + position,Toast.LENGTH_SHORT).show();
//                }else if (me.getAction() == MotionEvent.ACTION_MOVE){
//                    //Toast.makeText(Game.this, " Moving: " + position,Toast.LENGTH_SHORT).show();
//                }else if (me.getAction() == MotionEvent.ACTION_UP){
//                    //Toast.makeText(Game.this, " Up: " + position,Toast.LENGTH_SHORT).show();
//                }
                switch (me.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentXPosition = me.getX();
                        currentYPosition = me.getY();
                        posTouchDown = gridview.pointToPosition((int) currentXPosition, (int) currentYPosition);
                        Toast.makeText(Game.this, " Touch Down: " + posTouchDown,Toast.LENGTH_SHORT).show();
                        break;
//                    case MotionEvent.ACTION_UP:
//                        //Toast.makeText(Game.this, " Moving: ",Toast.LENGTH_SHORT).show();
//                        break;
                    case MotionEvent.ACTION_UP:
                        currentXPosition = me.getX();
                        currentYPosition = me.getY();
                        posTouchUp = gridview.pointToPosition((int) currentXPosition, (int) currentYPosition);
                        Integer help = new Integer((int) im.getItemIdTabPrincipal(posTouchDown));
                        im.setItemInTabPrincipal(posTouchUp, posTouchDown);
                        //im.setItemInteger(posTouchUp, help);
                        im.notifyDataSetChanged();
                        Toast.makeText(Game.this, " Up: " + posTouchUp,Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int) event.getX();         // NOTE: event.getHistorical... might be needed.
//        int y = (int) event.getY();
//        int position = gridview.pointToPosition((int) x, (int) y);
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            Toast.makeText(Game.this, " Touch Down: " + position,Toast.LENGTH_SHORT).show();
//        }else if (event.getAction() == MotionEvent.ACTION_MOVE){
//            Toast.makeText(Game.this, " Moving: " + position,Toast.LENGTH_SHORT).show();
//        }
//
//        return true;
//    }
}

