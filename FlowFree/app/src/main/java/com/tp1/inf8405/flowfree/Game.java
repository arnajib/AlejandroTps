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
    int posTouchMove = 0;
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
    public String trouverProchaineDirection(int positionDepart, int positionActuel){

        String direction = "";
        if(positionDepart > positionActuel){
            if((positionDepart - 1) == positionActuel)
                direction = "gauche";
            else if((positionDepart - 6) == positionActuel)
                direction = "hautDroite";
            else if((positionDepart - 7) == positionActuel)
                direction = "haut";
            else
                direction = "hautGauche";
        }
        else{
            if((positionDepart + 1) == positionActuel)
                direction = "droite";
            else if((positionDepart + 6) == positionActuel)
                direction = "basGauche";
            else if((positionDepart + 7) == positionActuel)
                direction = "bas";
            else
                direction = "basDroite";

        }
        return direction;
    }
    public String TrouverForm(String nom){
        String form = "cercle";
        if(nom.contains("cercle"))
            form = "cercle";
        else if (nom.contains("coin"))
            form = "ligne";
        else if(nom.contains("ligne"))
            form = "ligne";
        else
            form = "case_vide";

        return form;
    }
    public String TrouverDirectionImageActuelleFormOther(String nom){
        String dir = "pd";
        if(nom.contains("bas"))
            dir = "bas";
        else if (nom.contains("gauche"))
            dir = "gauche";
        else if(nom.contains("droite"))
            dir = "droite";
        else if(nom.contains("haut"))
            dir = "haut";
        else if(nom.contains("horizontale"))
            dir = "horizontale";
        else if(nom.contains("verticale"))
            dir = "verticale";

        return dir;
    }
    public String TrouverDirectionImageActuelleFormLinge(String nom){
        String dir = "horizontale";
        if(nom.contains("verticale"))
            dir = "verticale";
        return dir;
    }
    public String CreerNouveauNom(String nom, String ancienColor, String ancienForm, String ancienDir, String newDir){
        String newNom = "";
        int idxColore = 0;
        if(newDir == "gauche" || newDir == "droite" || newDir == "haut" || newDir == "bas") {
            if (ancienForm == "cercle" && ancienDir == "pd") {
                newNom = nom.replace("cercle", "ligne");
//            idxColore = nom.lastIndexOf("_") + 1;
//            String color = "" + (nom.charAt(idxColore));
                if(newDir == "gauche" || newDir == "droite")
                    newNom = newNom.replace("pd", "horizontale");
                else
                    newNom = newNom.replace("pd", "verticale");
            }
        }

        return newNom;
    }

    public String TrouverImageSuivante(String nom){  // com.tp1.inf8405.flowfree:drawable/blue
        String form = "linge";
        String dir = "";
        String newNom = "";

//        switch (trouveCouleur(nom))
//        {
//            case bleu:
//                form = TrouverForm(nom);
//                if(form == "ligne")
//                    dir = TrouverDirectionImageActuelleFormLinge(nom);
//                else
//                    dir = TrouverDirectionImageActuelleFormOther(nom);
//                newNom = CreerNouveauNom(nom, "b", form, dir);
//
//                break;
//            case gris:
//                form = TrouverForm(nom);
//                break;
//            case jaune:
//                form = TrouverForm(nom);
//                break;
//            case kaki:
//                form = TrouverForm(nom);
//                break;
//            case marron:
//                form = TrouverForm(nom);
//                break;
//            case orange:
//                form = TrouverForm(nom);
//                break;
//            case pistache:
//                form = TrouverForm(nom);
//                break;
//            case rouge:
//                form = TrouverForm(nom);
//                break;
//            case vert:
//                form = TrouverForm(nom);
//                break;
//            case zblanc:
//
//                break;
//        }
        return nom;
    }
    public String findColor(String nom){
        if(nom.endsWith("green"))
             nom = nom.replace("green", "ligne_horizontale_v");
        return nom;
    }
    public String TrouverColor(String nomImageActuellel){
        String couleur = "";
        // char c = SplitStr(nomImageActuellel);
        if(nomImageActuellel.endsWith("b"))
            couleur = "b";
        else if(nomImageActuellel.endsWith("g"))
            couleur = "g";
        else if(nomImageActuellel.endsWith("j"))
            couleur = "j";
        else if(nomImageActuellel.endsWith("k"))
            couleur = "k";
        else if(nomImageActuellel.endsWith("m"))
            couleur = "m";
        else if(nomImageActuellel.endsWith("o"))
            couleur = "o";
        else if(nomImageActuellel.endsWith("p"))
            couleur = "p";
        else if(nomImageActuellel.endsWith("r"))
            couleur = "r";
        else if(nomImageActuellel.endsWith("v"))
            couleur = "v";

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

//        if(positionDepart != positionActuel){
//            switch (trouverProchaineDirection(positionDepart, positionActuel)) {
//                case:
//            }
//
//        }
        return;

    }
    public boolean TestSiCaseDepartEstCercle(String nom){
        boolean reponse = false;
        if(TrouverForm(nom) == "cercle" && TrouverDirectionImageActuelleFormOther(nom) == "pd")
            reponse = true;
        return reponse;
    }
    public void addListenerToGrid() {
        gridview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {

                int action = me.getActionMasked();
                float currentXPosition;
                float currentYPosition;
                String color = "";
                String prochaindir = "";
                String newImageName = "";


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
                    case MotionEvent.ACTION_MOVE:
                        //Toast.makeText(Game.this, " Moving: ",Toast.LENGTH_SHORT).show();
                        currentXPosition = me.getX();
                        currentYPosition = me.getY();
                        posTouchMove = gridview.pointToPosition((int) currentXPosition, (int) currentYPosition);
                        if(posTouchMove != posTouchDown){
                            Integer help = new Integer((int) im.getItem(posTouchDown));
                            String nomImage = getResources().getResourceName(help); // com.tp1.inf8405.flowfree:drawable/blue
                            if(TestSiCaseDepartEstCercle(nomImage)){
                                color = TrouverColor(nomImage);
                                prochaindir = trouverProchaineDirection(posTouchDown, posTouchMove);
                                newImageName = CreerNouveauNom(nomImage,color,"cercle","pd",prochaindir);
                                int imageResourceIdentifier = getResources().getIdentifier(newImageName, "drawable", getPackageName());
                                int newImageId = im.getItemPosition(imageResourceIdentifier);
                                //im.setItemInTabPrincipal(posTouchUp, posTouchDown);
                                // im.setItemInteger(posTouchUp, help);
                                im.setItem(posTouchMove, newImageId);
                                im.notifyDataSetChanged();
                                posTouchDown = posTouchMove;
                            }
                            else
                            {
                                color = TrouverColor(nomImage);
                                prochaindir = trouverProchaineDirection(posTouchDown, posTouchMove);
                                String matchImage = findColor(nomImage);
                                int imageResourceIdentifier = getResources().getIdentifier(matchImage, "drawable", getPackageName());
                                int newImageId = im.getItemPosition(imageResourceIdentifier);
                                //im.setItemInTabPrincipal(posTouchUp, posTouchDown);
                                // im.setItemInteger(posTouchUp, help);
                                im.setItem(posTouchMove, newImageId);
                                im.notifyDataSetChanged();
                                posTouchDown = posTouchMove;
                            }

                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        currentXPosition = me.getX();
                        currentYPosition = me.getY();
                        posTouchUp = gridview.pointToPosition((int) currentXPosition, (int) currentYPosition);

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




