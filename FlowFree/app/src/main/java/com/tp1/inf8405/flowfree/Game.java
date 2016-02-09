package com.tp1.inf8405.flowfree;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.inf8405.tp1.flowfree.R;
import com.tp1.inf8405.flowfree.ImageAdapter;

public class Game extends Activity {
    int i=0;
    int firstClick,secondClick;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        final ImageAdapter im = new ImageAdapter(this);
        im.notifyDataSetChanged();
        gridview.setAdapter(im);
        gridview.invalidateViews();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(Game.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub
                i++;
                if( i %2!=0){
                    firstClick=arg2;
                }else{
                    secondClick=arg2;
                    Integer help=new Integer((Integer) im.getItem(firstClick));
                    im.setItem(firstClick, secondClick);
                    im.setItemInteger(secondClick, help);
                    im.notifyDataSetChanged();
                    System.out.println("Second Click "+i);
                }

            }
        });
    }

}

