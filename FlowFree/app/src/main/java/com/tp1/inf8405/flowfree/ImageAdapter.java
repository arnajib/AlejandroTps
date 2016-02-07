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
    public Object getItemTabPrincipal(int position) {
        return mThumbIdsRefPrincipal[position];
    }

    public long getItemId(int position) {
         if(iView != null){
            iView.setImageResource(mThumbIds[0]);
            Toast.makeText(mContext, "Call", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }
    public long getItemIdTabPrincipal(int position) {
        if(iView != null){
            iView.setImageResource(mThumbIdsRefPrincipal[0]);
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

    public void setItem(int firstClick, int secondClick) {
        mThumbIds[firstClick] = mThumbIds[secondClick];
    }

    public void setItemInteger(int secondClick, Integer help) {
        mThumbIds[secondClick]=help;
    }

    public void setItemInTabPrincipal(int firstTouch, int secondTouch) {
        mThumbIds[firstTouch] = mThumbIdsRefPrincipal[secondTouch];
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

    // references to our images
    private Integer[] mThumbIdsRefPrincipal = {
            R.drawable.case_vide, R.drawable.cercle_b,
            R.drawable.cercle_g ,
            R.drawable.cercle_j,
            R.drawable.cercle_l,
            R.drawable.cercle_m,
            R.drawable.cercle_o,
            R.drawable.cercle_p,
            R.drawable.cercle_r,
            R.drawable.cercle_v,
            R.drawable.cercle_bas_b,
            R.drawable.cercle_bas_g,
            R.drawable.cercle_bas_j,
            R.drawable.cercle_bas_l,
            R.drawable.cercle_bas_m,
            R.drawable.cercle_bas_o,
            R.drawable.cercle_bas_p,
            R.drawable.cercle_bas_r,
            R.drawable.cercle_bas_v,
            R.drawable.cercle_droite_b,
            R.drawable.cercle_droite_g,
            R.drawable.cercle_droite_p,
            R.drawable.cercle_droite_j,
            R.drawable.cercle_droite_l,
            R.drawable.cercle_droite_m,
            R.drawable.cercle_droite_o,
            R.drawable.cercle_droite_r,
            R.drawable.cercle_droite_v,
            R.drawable.cercle_gauche_b,
            R.drawable.cercle_gauche_g,
            R.drawable.cercle_gauche_j,
            R.drawable.cercle_gauche_l,
            R.drawable.cercle_gauche_m,
            R.drawable.cercle_gauche_o,
            R.drawable.cercle_gauche_p,
            R.drawable.cercle_gauche_r,
            R.drawable.cercle_gauche_v,
            R.drawable.cercle_haut_b,
            R.drawable.cercle_haut_g,
            R.drawable.cercle_haut_j,
            R.drawable.cercle_haut_l,
            R.drawable.cercle_haut_m,
            R.drawable.cercle_haut_o,
            R.drawable.cercle_haut_p,
            R.drawable.cercle_haut_r,
            R.drawable.cercle_haut_v,
            R.drawable.coin1_b,
            R.drawable.coin2_b,
            R.drawable.coin3_b,
            R.drawable.coin4_b,
            R.drawable.coin1_g,
            R.drawable.coin2_g,
            R.drawable.coin3_g,
            R.drawable.coin4_g,
            R.drawable.coin1_j,
            R.drawable.coin2_j,
            R.drawable.coin3_j,
            R.drawable.coin4_j,
            R.drawable.coin1_l,
            R.drawable.coin2_l,
            R.drawable.coin3_l,
            R.drawable.coin4_l,
            R.drawable.coin1_m,
            R.drawable.coin2_m,
            R.drawable.coin3_m,
            R.drawable.coin4_m,
            R.drawable.coin1_o,
            R.drawable.coin2_o,
            R.drawable.coin3_o,
            R.drawable.coin4_o,
            R.drawable.coin1_r,
            R.drawable.coin2_r,
            R.drawable.coin3_r,
            R.drawable.coin4_r,
            R.drawable.coin1_p,
            R.drawable.coin2_p,
            R.drawable.coin3_p,
            R.drawable.coin4_p,
            R.drawable.coin1_v,
            R.drawable.coin2_v,
            R.drawable.coin3_v,
            R.drawable.coin4_v,
            R.drawable.ligne_horizontale_b,
            R.drawable.ligne_verticale_b,
            R.drawable.ligne_horizontale_g,
            R.drawable.ligne_verticale_g,
            R.drawable.ligne_horizontale_j,
            R.drawable.ligne_verticale_j,
            R.drawable.ligne_horizontale_l,
            R.drawable.ligne_verticale_l,
            R.drawable.ligne_horizontale_m,
            R.drawable.ligne_verticale_m,
            R.drawable.ligne_horizontale_o,
            R.drawable.ligne_verticale_o,
            R.drawable.ligne_horizontale_r,
            R.drawable.ligne_verticale_r,
            R.drawable.ligne_horizontale_v,
            R.drawable.ligne_verticale_v,
            R.drawable.ligne_horizontale_p,
            R.drawable.ligne_verticale_p,
            R.drawable.white,
            R.drawable.pink,
            R.drawable.green,
            R.drawable.blue,
            R.drawable.white

    };
}