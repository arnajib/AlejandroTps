package com.tp1.inf8405.flowfree;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by najib on 04/02/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int lastClicked;
    ImageView iView;
    ArrayList<String> arrayList;


    public ImageAdapter(Context c) {

        mContext = c;
//        arrayList = new ArrayList<>();
//        arrayList.add("case_vide");
//        arrayList.add("cercle_b");
//        arrayList.add("cercle_g");
//        arrayList.add("cercle_j");
//        arrayList.add("cercle_l");
//        arrayList.add("cercle_m");
//        arrayList.add("cercle_o");
//        arrayList.add("cercle_p");
//        arrayList.add("cercle_r");
//        arrayList.add("cercle_v");
//        arrayList.add("cercle_bas_b");
//        arrayList.add("cercle_bas_g");
//        arrayList.add("cercle_bas_j");
//        arrayList.add("cercle_bas_l");
//        arrayList.add("cercle_bas_m");
//        arrayList.add("cercle_bas_o");
//        arrayList.add("cercle_bas_p");
//        arrayList.add("cercle_bas_r");
//        arrayList.add("cercle_bas_v");



    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {

        //System.out.println("Item Is :-"+mThumbIds[position].toString());
        return mThumbIds[position];
    }
    public Object getItemTabPrincipal(int position) {
        return mThumbIdsRefPrincipal[position];
    }

    public long getItemId(int position) {
//         if(iView != null){
//            iView.setImageResource(mThumbIds[0]);
//            Toast.makeText(mContext, "Call", Toast.LENGTH_SHORT).show();
//        }
        return position;
    }
    public long getItemIdTabPrincipal(int position) {
//        if(iView != null){
//            iView.setImageResource(mThumbIdsRefPrincipal[position]);
//            Toast.makeText(mContext, "Call", Toast.LENGTH_SHORT).show();
//        }

        return mThumbIdsRefPrincipal[position];
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
        mThumbIds[firstClick] = mThumbIdsRefPrincipal[secondClick];
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
            R.drawable.white, R.drawable.white, R.drawable.cercle_pd_r, R.drawable.white, R.drawable.white, R.drawable.cercle_pd_v, R.drawable.cercle_pd_b,
            R.drawable.white, R.drawable.white, R.drawable.white,R.drawable.white, R.drawable.white, R.drawable.cercle_pd_r, R.drawable.white,
            R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white,R.drawable.white,
            R.drawable.white, R.drawable.white, R.drawable.white,R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white,
            R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white,R.drawable.white,
            R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white,R.drawable.white, R.drawable.white,
            R.drawable.white, R.drawable.white, R.drawable.cercle_pd_b, R.drawable.white, R.drawable.cercle_pd_v, R.drawable.white, R.drawable.white
    };


    // references to our images
    private Integer[] mThumbIdsRefPrincipal = {
            R.drawable.case_vide,
            R.drawable.cercle_pd_b,
            R.drawable.cercle_pd_g ,
            R.drawable.cercle_pd_j,
            R.drawable.cercle_pd_l,
            R.drawable.cercle_pd_m,
            R.drawable.cercle_pd_o,
            R.drawable.cercle_pd_p,
            R.drawable.cercle_pd_r,
            R.drawable.cercle_pd_v,
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
            R.drawable.coin_bas_b,
            R.drawable.coin_droite_b,
            R.drawable.coin_gauche_b,
            R.drawable.coin_haut_b,
            R.drawable.coin_bas_g,
            R.drawable.coin_droite_g,
            R.drawable.coin_gauche_g,
            R.drawable.coin_haut_g,
            R.drawable.coin_bas_j,
            R.drawable.coin_droite_j,
            R.drawable.coin_gauche_j,
            R.drawable.coin_haut_j,
            R.drawable.coin_bas_m,
            R.drawable.coin_droite_m,
            R.drawable.coin_gauche_m,
            R.drawable.coin_haut_m,
            R.drawable.coin_bas_o,
            R.drawable.coin_droite_o,
            R.drawable.coin_gauche_o,
            R.drawable.coin_haut_o,
            R.drawable.coin_bas_p,
            R.drawable.coin_droite_p,
            R.drawable.coin_gauche_p,
            R.drawable.coin_haut_p,
            R.drawable.coin_bas_r,
            R.drawable.coin_droite_r,
            R.drawable.coin_gauche_r,
            R.drawable.coin_haut_r,
            R.drawable.coin_bas_v,
            R.drawable.coin_droite_v,
            R.drawable.coin_gauche_v,
            R.drawable.coin_haut_v,
            R.drawable.coin_bas_b,
            R.drawable.coin_droite_b,
            R.drawable.coin_gauche_b,
            R.drawable.coin_haut_b,
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
    };

    public int getItemPosition(int imageResourceIdentifier) {
        for(int i = 0; i < mThumbIdsRefPrincipal.length; i++)
        {
            if(mThumbIdsRefPrincipal[i] == imageResourceIdentifier)
                return i;
        }
        return -1;
    }
}