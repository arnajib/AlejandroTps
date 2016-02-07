package com.tp1.inf8405.flowfree;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * Created by najib on 04/02/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int lastClicked;
    ImageView iView;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {

        System.out.println("Item Is :-"+mThumbIds[position].toString());
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        System.out.println("Geting Id of Item "+mThumbIds[position]);
        if(iView != null){
            iView.setImageResource(mThumbIds[0]);
            Toast.makeText(mContext, "Call", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(155, 155));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View v, int position,
//                            long id) {
//        if (lastClicked != -1) {
//            // we previously selected a position
//            mThumbIds[lastClicked] = R.drawable.default_image;
//        }
//        if (lastClicked != position) {
//            mThumbIds[position] = R.drawable.image_required_when_grid_selected;
//        }
//        lastClicked = position;
//        mAdapter.notifyDataSetChanged(); // mAdapter is a reference to the GridView's adapter
//    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.white, R.drawable.white, R.drawable.pink, R.drawable.white, R.drawable.white, R.drawable.green, R.drawable.blue,
            R.drawable.white, R.drawable.white, R.drawable.white,R.drawable.white, R.drawable.white, R.drawable.pink, R.drawable.white,
            R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white,R.drawable.white,
            R.drawable.white, R.drawable.white, R.drawable.white,R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white,
            R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white,R.drawable.white,
            R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white,R.drawable.white, R.drawable.white,
            R.drawable.white, R.drawable.white, R.drawable.blue, R.drawable.white, R.drawable.green, R.drawable.white, R.drawable.white
    };

    public void setItem(int firstClick, int secondClick) {
        mThumbIds[firstClick] = mThumbIds[secondClick];
    }

    public void setItemInteger(int secondClick, Integer help) {
        mThumbIds[secondClick]=help;
    }
}