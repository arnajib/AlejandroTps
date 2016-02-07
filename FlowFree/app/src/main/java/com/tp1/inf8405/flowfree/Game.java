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
import android.widget.Toast;
import com.tp1.inf8405.flowfree.ImageAdapter;

public class Game extends Activity {
    int i=0, j=0;
    int firstClick,secondClick;
    int firstTouch,secondTouch;
    GridView gridview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        final ImageAdapter im = new ImageAdapter(this);
        im.notifyDataSetChanged();
        gridview.setAdapter(im);
        gridview.invalidateViews();

        gridview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(parent.getContext(), "Spinner item 1!", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(parent.getContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(parent.getContext(), "Spinner item 3!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(Game.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

//        gridview.setOnTouchListener(new AdapterView.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                j++;
//                if ()
//                    return false;
//            }
//        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                i++;
                if (i % 2 != 0) {
                    firstClick = arg2;
                } else {
                    secondClick = arg2;
                    Integer help = new Integer((int) im.getItemIdTabPrincipal(firstClick));
                    im.setItemInTabPrincipal(firstClick, secondClick);
                    im.setItemInteger(secondClick, help);
                    im.notifyDataSetChanged();
                    System.out.println("Second Click " + i);
                }

            }
        });
    }
    public void addListenerToGrid() {
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        final ImageAdapter im2 = new ImageAdapter(this);
        im2.notifyDataSetChanged();
        gridview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {

                int action = me.getActionMasked();
                float currentXPosition = me.getX();
                float currentYPosition = me.getY();
                int position = gridview.pointToPosition((int) currentXPosition, (int) currentYPosition);
                if (action == MotionEvent.ACTION_UP) {
                    // Key was pressed here
                    im2.setItemInTabPrincipal(position, position) ;
                }

                return true;
            }
        });
    }
}

