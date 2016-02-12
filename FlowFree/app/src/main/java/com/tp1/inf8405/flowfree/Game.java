package com.tp1.inf8405.flowfree;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

public class Game extends Activity {
    int i = 0, j = 0;
    int firstClick, secondClick;
    int firstTouch, secondTouch;
    GridView gridview;
    final ImageAdapter im = new ImageAdapter(this);
    int posTouchDown = 0;
    int posTouchUp = 0;
    int posTouchMove = 0;

    enum Direction {haut, hautDroite, droite, basDroite, bas, basGauche, gauche, hautGauche, centre}

    ;

    enum Couleur {bleu, gris, jaune, kaki, marron, orange, pistache, rouge, vert, zblanc}

    ;

    enum FormObjet {cercle, cercleLigne, coin, ligne, caseVide}

    ;

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
    public String trouverProchaineDirection(int positionDepart, int positionActuel) {

        String direction = "";
        if (positionDepart > positionActuel) {
            if ((positionDepart - 1) == positionActuel)
                direction = "gauche";
            else if ((positionDepart - 7) == positionActuel)
                direction = "haut";
        } else {
            if ((positionDepart + 1) == positionActuel)
                direction = "droite";
            else if ((positionDepart + 7) == positionActuel)
                direction = "bas";
        }
        return direction;
    }

    public String TrouverDirectionImageActuelle(String nom) {
        String dir = "nonDir";

        if (nom.contains("bas") && !nom.contains("verticale"))
            dir = "bas";
        else if (nom.contains("gauche") && !nom.contains("horizontale"))
            dir = "gauche";
        else if (nom.contains("droite") && !nom.contains("horizontale"))
            dir = "droite";
        else if (nom.contains("haut") && !nom.contains("verticale"))
            dir = "haut";
        else if (nom.contains("horizontaledroite"))
            dir = "horizontaledroite";
        else if (nom.contains("horizontalegauche"))
            dir = "horizontalegauche";
        else if (nom.contains("verticalehaut"))
            dir = "verticalehaut";
        else if (nom.contains("verticalebas"))
            dir = "verticalebas";
        else if (nom.contains("pd"))
            dir = "pd";

        return dir;
    }

    public String CreerNouveauNomPourProchaineCase(String nomNow, String nomAfter, String colorNow, String formNow, String dirNow, String dirAfter) {
        String newNom = "nonName";
        String nomImageCaseSuivante = trouveShape(nomAfter);
        String dirImageSuivante = "", colorImageSuivante = "";


        /* On dessine 3 image pour la case suivante: ligne verticale, ligne horisontale, cercleQueue*/
        /*Phase 1: on vérifie si le prochaine image est ligne verticale */
        if ((((dirNow.equals("verticalebas") || dirNow.equals("verticalehaut") || dirNow.equals("pd")) && (dirAfter.equals("haut") || dirAfter.equals("bas"))) ||
                ((dirNow.equals("horizontalegauche") || dirNow.equals("horizontaledroite")) && ((dirAfter.equals("haut")) || (dirAfter.equals("bas"))))) &&
                (nomImageCaseSuivante.equals("case"))) {
            if (dirAfter.equals("haut")) {
                newNom = nomNow.replace(formNow, "ligne");
                newNom = newNom.replace(dirNow, "verticalebas");
            } else {
                newNom = nomNow.replace(formNow, "ligne");
                newNom = newNom.replace(dirNow, "verticalehaut");
            }
        }

        /*Phase 1: on vérifie si le prochaine image est ligne horizontale */
        if ((((dirNow.equals("horizontalegauche") || dirNow.equals("horizontaledroite") || dirNow.equals("pd")) && (dirAfter.equals("gauche") || dirAfter.equals("droite"))) ||
                ((dirNow.equals("verticalebas") || dirNow.equals("verticalehaut")) && ((dirAfter.equals("gauche")) || (dirAfter.equals("droite"))))
                        ) && nomImageCaseSuivante.equals("case")) {
            if (dirAfter.equals("gauche")) {
                newNom = nomNow.replace(formNow, "ligne");
                newNom = newNom.replace(dirNow, "horizontaledroite");
            } else {
                newNom = nomNow.replace(formNow, "ligne");
                newNom = newNom.replace(dirNow, "horizontalegauche");
            }
        }
        /*Phase 3: on vérifie si le prochaine image est un cercle entier cad point final */
        if (!nomImageCaseSuivante.equals("case")) {
            dirImageSuivante = trouverDirection(nomAfter);
            colorImageSuivante = TrouverColor(nomAfter);
            if (nomImageCaseSuivante.equals("cercle") && dirImageSuivante.equals("pd") && colorImageSuivante.equals(colorNow)) {
                if (dirAfter.equals("haut"))
                    newNom = nomAfter.replace(dirImageSuivante, "bas");
                else if (dirAfter.equals("bas"))
                    newNom = nomAfter.replace(dirImageSuivante, "haut");
                else if (dirAfter.equals("droite"))
                    newNom = nomAfter.replace(dirImageSuivante, "gauche");
                else if (dirAfter.equals("gauche"))
                    newNom = nomAfter.replace(dirImageSuivante, "droite");
                else if (dirNow.equals("pd"))
                    newNom = nomAfter.replace(dirImageSuivante, dirAfter);
            }
        }
        return newNom;
    }

    public String CreerNouveauNomPourCaseActuelle(String nom, String colorAfter, String formNow, String formAfter, String dirNow, String dirAfter) {
        String newNom = "nonName";
        String colorNow = TrouverColor(nom);

        /* Dans 2 cas, l'image de la case actuelle change, lorsqu'on débute a partir d'un cercle entier ou lorsqu'on tourne!* /

        /* Etape 1: On vérifie si on a débuté une trace a partir d'un cercle */
        if (formNow.equals("cercle") && dirNow.equals("pd") && (colorNow.equals(colorAfter) || colorAfter.equals("nonColor") )) {
            newNom = nom.replace("pd", dirAfter);
        /* Etape 2: on verifie si on tourne, si oui on essaie de trouver le nom de coin*/
        } else if (formNow.equals("ligne") && formAfter.equals("coin")) {

            newNom = nom.replace(formNow, formAfter);
            if ((dirNow.equals("horizontalegauche") && dirAfter.equals("bas")) || (dirNow.equals("verticalebas") && dirAfter.equals("gauche"))) {
                newNom = newNom.replace(dirNow, "bas");
            } else if ((dirNow.equals("verticalebas") && dirAfter.equals("droite")) || (dirNow.equals("horizontaledroite") && dirAfter.equals("bas"))) {
                newNom = newNom.replace(dirNow, "droite");
            } else if ((dirNow.equals("horizontaledroite") && dirAfter.equals("haut")) || (dirNow.equals("verticalehaut") && dirAfter.equals("droite"))) {
                newNom = newNom.replace(dirNow, "haut");
            } else if ((dirNow.equals("verticalehaut") && dirAfter.equals("gauche")) || (dirNow.equals("horizontalegauche") && dirAfter.equals("haut"))) {
                newNom = newNom.replace(dirNow, "gauche");
            }
        }


        return newNom;
    }

    public String TrouverColor(String nomImageActuellel) {
        String couleur = "nonColor";
        if (nomImageActuellel.endsWith("b"))
            couleur = "b";
        else if (nomImageActuellel.endsWith("g"))
            couleur = "g";
        else if (nomImageActuellel.endsWith("j"))
            couleur = "j";
        else if (nomImageActuellel.endsWith("k"))
            couleur = "k";
        else if (nomImageActuellel.endsWith("m"))
            couleur = "m";
        else if (nomImageActuellel.endsWith("o"))
            couleur = "o";
        else if (nomImageActuellel.endsWith("p"))
            couleur = "p";
        else if (nomImageActuellel.endsWith("r"))
            couleur = "r";
        else if (nomImageActuellel.endsWith("v"))
            couleur = "v";
        return couleur;
    }

    public String trouveShape(String nomCase) {
        String form = "";
        int idD = nomCase.indexOf("drawable");
        int idF = nomCase.indexOf("_");
        form = nomCase.substring(idD + 9, idF);
        return form;
    }

    public String trouverDirection(String nomCase) { // com.tp1.inf8405.flowfree:drawable/ligne_verticale_g
        String dirCaseActuelle = "";
        String temp = "";
        int idD = nomCase.indexOf("_");
        int idF = nomCase.indexOf("_", idD + 1);
        temp = nomCase.substring(idD + 1, idF);
        if (temp.equals("gauche")) dirCaseActuelle = "gauche";
        else if (temp.equals("haut")) dirCaseActuelle = "haut";
        else if (temp.equals("droite")) dirCaseActuelle = "droite";
        else if (temp.equals("verticalehaut")) dirCaseActuelle = "verticalehaut";
        else if (temp.equals("horizontalegauche")) dirCaseActuelle = "horizontalegauche";
        else if (temp.equals("verticalebas")) dirCaseActuelle = "verticalebas";
        else if (temp.equals("horizontaledroite")) dirCaseActuelle = "horizontaledroite";
        else dirCaseActuelle = "pd";

        return dirCaseActuelle;
    }

    public String trouveProchaineForm(String nomCaseActeulle, String nomImagCaseSuivante, String formNow, String dirNow, String dirAfter) {
        String prochaineForm = "cercle";
        if (formNow.equals("ligne")) {
            if ((dirAfter.equals("verticalehaut") || dirAfter.equals("verticalebas") || dirAfter.equals("haut") || dirAfter.equals("bas")) && ((dirNow.equals("verticalehaut") || dirNow.equals("verticalebas") || dirNow.equals("haut") || dirNow.equals("bas")))) {
                prochaineForm = "ligne";
            } else if ((dirAfter.equals("horizontalegauche") || dirAfter.equals("horizontaledroite") || dirAfter.equals("gauche") || dirAfter.equals("droite")) && ((dirNow.equals("horizontalegauche") || dirNow.equals("horizontaledroite") || dirNow.equals("gauche") || dirNow.equals("droite")))) {
                prochaineForm = "ligne";
            } else
                prochaineForm = "coin";
        }
        return prochaineForm;
    }
    public void effacerLeResteDeTrace(){

    }

    public void addListenerToGrid() {
        gridview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {

                int action = me.getActionMasked();
                float currentXPosition;
                float currentYPosition;
                String colorNow = "";
                String colorAfter = "";
                String dirNow = "";
                String dirAfter = "";
                String newImageNameCaseSuivante = "";
                String newImageNameCaseActuelle = "";
                String nomCaseActeulle = "";
                String formNow = "";
                int imageResourceIdentifier = 0;
                int newImageId = 0;
                String formAfter = "";
                String nomimageCaseSuivante = "";
                Integer idCaseSuivante;

                switch (me.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentXPosition = me.getX();
                        currentYPosition = me.getY();
                        posTouchDown = gridview.pointToPosition((int) currentXPosition, (int) currentYPosition);
                        Toast.makeText(Game.this, " Touch Down: " + posTouchDown, Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //Toast.makeText(Game.this, " Moving: ",Toast.LENGTH_SHORT).show();
                        currentXPosition = me.getX();
                        currentYPosition = me.getY();
                        posTouchMove = gridview.pointToPosition((int) currentXPosition, (int) currentYPosition);

                        /* On teste si on a changé de case */
                        if (posTouchMove != posTouchDown) {

                            /* Ici on trouve le nom de l'image de la case actuelle cad la case d'où on vient de sortir et tous ses dépandants */
                            Integer idCaseActuelle = new Integer((int) im.getItem(posTouchDown));
                            nomCaseActeulle = getResources().getResourceName(idCaseActuelle); // com.tp1.inf8405.flowfree:drawable/...
                            colorNow = TrouverColor(nomCaseActeulle);
                            formNow = trouveShape(nomCaseActeulle);
                            dirNow = TrouverDirectionImageActuelle(nomCaseActeulle);
                            dirAfter = trouverProchaineDirection(posTouchDown, posTouchMove);


                            /* Ici on trouve d'abord nom de l'image de la case suivante cad la case où on vient de rentrer */
                            idCaseSuivante = new Integer((int) im.getItem(posTouchMove));
                            nomimageCaseSuivante = getResources().getResourceName(idCaseSuivante); // com.tp1.inf8405.flowfree:drawable/..
                            colorAfter = TrouverColor(nomimageCaseSuivante);
                            formAfter = trouveProchaineForm(nomCaseActeulle, nomimageCaseSuivante, formNow, dirNow, dirAfter);

                            /* Ici on change l'image de la case actuelle */

                            newImageNameCaseActuelle = CreerNouveauNomPourCaseActuelle(nomCaseActeulle, colorAfter, formNow, formAfter, dirNow, dirAfter);
                            newImageNameCaseSuivante = CreerNouveauNomPourProchaineCase(nomCaseActeulle, nomimageCaseSuivante, colorNow, formNow, dirNow, dirAfter);
                            if (!newImageNameCaseActuelle.equals("nonName")) {
                                imageResourceIdentifier = getResources().getIdentifier(newImageNameCaseActuelle, "drawable", getPackageName());
                                newImageId = im.getItemPosition(imageResourceIdentifier);
                                im.setItem(posTouchDown, newImageId);
                            }
                            /* Ici on change l'image de la case suivante */
                            if (!newImageNameCaseSuivante.equals("nonName")) {
                                imageResourceIdentifier = getResources().getIdentifier(newImageNameCaseSuivante, "drawable", getPackageName());
                                newImageId = im.getItemPosition(imageResourceIdentifier);
                                im.setItem(posTouchMove, newImageId);
                            }
                            /* Ici on met a jour le tableau des cases*/
                            im.notifyDataSetChanged();
                            posTouchDown = posTouchMove;

                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        currentXPosition = me.getX();
                        currentYPosition = me.getY();
                        posTouchUp = gridview.pointToPosition((int) currentXPosition, (int) currentYPosition);

                        Toast.makeText(Game.this, " Up: " + posTouchUp, Toast.LENGTH_SHORT).show();
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




