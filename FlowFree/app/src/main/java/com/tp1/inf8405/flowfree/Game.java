package com.tp1.inf8405.flowfree;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

public class Game extends Activity {
    int i=0, j=0;
    int firstClick,secondClick;
    int firstTouch,secondTouch;
    GridView gridview;
    final ImageAdapter im = new ImageAdapter(this);
    int posTouchDown = 0;
    int posTouchUp = 0;
    enum Direction {haut, hautDroite,droite,basDroite,bas,basGauche,gauche,hautGauche,centre};
    enum Couleur {bleu,gris,jaune,kaki,marron,orange,pistache,rouge,vert,zblanc};
    enum FormObjet{cercle,cercleLigne,coin,ligne,caseVide};
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
    /*
    *              (pa - 8) hg            h (pa - 7)        hd (pa - 6)
    *
    *              (pa - 1) g             pa                d (pa +1)
    *
    *              (pa + 6) bg           b  (pa + 7)       bd (pa + 8)
    *
    * */
    public Direction trouveDirection(int positionDepart, int positionActuel){

        Direction direction = Direction.centre;
        if(positionDepart < positionActuel){
            if((positionDepart - 1) == positionActuel)
                direction = Direction.gauche;
            else if((positionDepart - 6) == positionActuel)
                direction = Direction.hautDroite;
            else if((positionDepart - 7) == positionActuel)
                direction = Direction.haut;
            else
                direction = Direction.hautGauche;
        }
        else{
            if((positionDepart + 1) == positionActuel)
                direction = Direction.droite;
            else if((positionDepart + 6) == positionActuel)
                direction = Direction.basGauche;
            else if((positionDepart + 7) == positionActuel)
                direction = Direction.bas;
            else
                direction = Direction.basDroite;

        }
        return direction;
    }
    public char SplitStr(String nom){
        char c = 'z';
        int pos = (nom.indexOf(".") - 1);
        c = nom.charAt(pos);
        return c;
    }
    public String findColor(String nom){
        if(nom.endsWith("green"))
             nom = nom.replace("green", "ligne_horizontale_v");
        return nom;
    }
    public Couleur trouveCouleur(String nomImageActuellel){
        Couleur couleur = Couleur.zblanc;
        char c = SplitStr(nomImageActuellel);
        if(c == 'b')
            couleur = Couleur.bleu;
        else if(c == 'g')
            couleur = Couleur.gris;
        else if(c == 'j')
            couleur = Couleur.jaune;
        else if(c == 'k')
            couleur = Couleur.kaki;
        else if(c == 'm')
            couleur = Couleur.marron;
        else if(c == 'o')
            couleur = Couleur.orange;
        else if(c == 'p')
            couleur = Couleur.pistache;
        else if(c == 'r')
            couleur = Couleur.rouge;
        else if(c == 'v')
            couleur = Couleur.vert;

        return couleur;
    }
    public FormObjet trouveShape(){
        FormObjet f = FormObjet.cercle;
        return f;
    }
    public String BuildPathImage(){
        String path = "/src/drawable/cercle_vert.png";

        return path;
    }
    public void trouveImageConvenable(int positionDepart, int positionActuel){

        if(positionDepart != positionActuel){
            switch (trouveDirection(positionDepart, positionActuel)) {
                case haut:
            }

        }
        return;

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
                        Integer help = new Integer((int) im.getItem(posTouchDown));
                        String nomImage = getResources().getResourceName(help); // com.tp1.inf8405.flowfree:drawable/blue
                        String matchImage = findColor(nomImage);
                        int imageResourceIdentifier = getResources().getIdentifier(matchImage, "drawable", getPackageName());
                        int newImageId = im.getItemPosition(imageResourceIdentifier);
                        //im.setItemInTabPrincipal(posTouchUp, posTouchDown);
                        // im.setItemInteger(posTouchUp, help);
                        im.setItem(posTouchUp, newImageId);
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




