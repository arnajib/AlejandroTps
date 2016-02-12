package com.inf8405.tp1.flowfree;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;
import android.view.View;

/**
 * Created by Najib on 08/02/2016.
 */
public class Cell extends View {

    // Forme de la cellule, Cercle pour les points à relier
    // et Rectangle pour les lignes qui les relient
    public enum Sharp {
        Circle,
        UpDown,
        LeftRight,
        LeftDown,
        LeftUp,
        RightDown,
        RightUp,
        CircleUp,
        CircleDown,
        CircleLeft,
        CircleRight,
        None
    }

    // Type de la cellule, First : la première cliquée
    public enum CellType {
        First,
        Second,
        None
    }


    // Attribues de la cellule
    private Sharp sharp;
    private CellType type;
    private int color;
    private int indexRow;
    private int indexCol;
    private boolean used;
    private Paint paint;

    // Constructeur par defaut
    public Cell(Context context) {
        super(context);
        this.sharp = Sharp.Circle;
        this.type = CellType.None;
        this.color = Color.WHITE;
        this.indexRow = 0;
        this.indexCol = 0;
        this.used = false;
        this.paint = new Paint();
    }


    // Constructeur par parametres
    public Cell(Context context, Sharp sharp, CellType type, int color, int indexRow, int indexCol, boolean used) {
        super(context);
        this.sharp = sharp;
        this.type = type;
        this.color = color;
        this.indexRow = indexRow;
        this.indexCol = indexCol;
        this.used = used;
        this.paint = new Paint();
    }

    // Constructeur par defaut
    public Cell(Context context, Cell cell) {
        super(context);
        this.sharp = cell.sharp;
        this.type = cell.getType();
        this.color = cell.color;
        this.indexRow = cell.indexRow;
        this.indexCol = cell.indexCol;
        this.used = false;
        this.paint = new Paint();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(this.color);
        this.setBackgroundResource(R.drawable.cell_shape);

        // les paramètres du rectangle
        float rectIndexLeft;
        float rectIndexTop;
        float rectIndexRight;
        float rectIndexBottom;

        // Les paramètres du cercle
        float circleIndexCX;
        float circleIndexCY;
        float circleRadius;

        switch (this.sharp) {
            // Dessiner un cercle
            case Circle:
                // Initialiser les paramètres du cercle
                circleIndexCX = (float) getWidth() / 2;
                circleIndexCY = (float) getHeight() / 2;
                circleRadius = (float) getHeight() / 3;
                canvas.drawCircle(circleIndexCX, circleIndexCY, circleRadius, paint);
                break;

            // Dessiner un rectangle de haut en bas
            case UpDown:
                // Initialiser les paramètres du rectangle
                rectIndexLeft = (float) getWidth() / 4;
                rectIndexTop = 0;
                rectIndexRight = (float) getWidth() * 3 / 4;
                rectIndexBottom = (float) getHeight();
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);
                break;

            // Dessiner un rectangle de gauche à droite
            case LeftRight:
                // Initialiser les paramètres du rectangle
                rectIndexLeft = 0;
                rectIndexTop = (float) getWidth() / 4;
                rectIndexRight = (float) getWidth();
                rectIndexBottom = (float) getHeight() * 3 / 4;
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);
                break;

            // Dessiner un rectangle pour corner de gauche en bas
            case LeftDown:
                // Initialiser les paramètres du 1er rectangle
                rectIndexLeft = 0;
                rectIndexTop = (float) getWidth() / 4;
                rectIndexRight = (float) getWidth() / 2;
                rectIndexBottom = (float) getHeight() * 3 / 4;
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);

                // Initialiser les paramètres du 2eme rectangle
                rectIndexLeft = (float) getWidth() / 4;
                rectIndexTop = (float) getHeight() / 4;
                rectIndexRight = (float) getWidth() * 3 / 4;
                rectIndexBottom = (float) getHeight();
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);
                break;
                // Dessiner un rectangle pour corner de gauche en haut
            case LeftUp:
                // Initialiser les paramètres du 1er rectangle
                rectIndexLeft = 0;
                rectIndexTop = (float) getWidth() / 4;
                rectIndexRight = (float) getWidth() / 2;
                rectIndexBottom = (float) getHeight() * 3 / 4;
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);

                // Initialiser les paramètres du 2eme rectangle
                rectIndexLeft = (float) getWidth() / 4;
                rectIndexTop = 0;
                rectIndexRight = (float) getWidth() * 3 / 4;
                rectIndexBottom = (float) getHeight() * 3 / 4;
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);

                break;

            // Dessiner un rectangle pour corner de gauche en haut
            case RightDown:
                // Initialiser les paramètres du 1er rectangle
                rectIndexLeft = (float) getWidth() / 2;
                rectIndexTop = (float) getWidth() / 4;
                rectIndexRight = (float) getWidth();
                rectIndexBottom = (float) getHeight() * 3 / 4;
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);

                // Initialiser les paramètres du 2eme rectangle
                rectIndexLeft = (float) getWidth() / 4;
                rectIndexTop = (float) getHeight() / 4;
                rectIndexRight = (float) getWidth() * 3 / 4;
                rectIndexBottom = (float) getHeight();
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);

                break;

            // Dessiner un rectangle pour corner de gauche en haut
            case RightUp:
                // Initialiser les paramètres du 1er rectangle
                rectIndexLeft = (float) getWidth() / 2;
                rectIndexTop = (float) getWidth() / 4;
                rectIndexRight = (float) getWidth();
                rectIndexBottom = (float) getHeight() * 3 / 4;
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);

                // Initialiser les paramètres du 2eme rectangle
                rectIndexLeft = (float) getWidth() / 4;
                rectIndexTop = 0;
                rectIndexRight = (float) getWidth() * 3 / 4;
                rectIndexBottom = (float) getHeight() * 3 / 4;
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);

                break;


            // Dessiner un rectangle pour corner de gauche en haut
            case CircleRight:
                // Initialiser les paramètres du cercle
                circleIndexCX = (float) getWidth() / 2;
                circleIndexCY = (float) getHeight() / 2;
                circleRadius = (float) getHeight() / 3;
                canvas.drawCircle(circleIndexCX, circleIndexCY, circleRadius, paint);

                // Initialiser les paramètres du 2eme rectangle
                rectIndexLeft = (float) getWidth() / 2;
                rectIndexTop = (float) getWidth() / 4;
                rectIndexRight = (float) getWidth();
                rectIndexBottom = (float) getHeight() * 3 / 4;
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);

                break;

            // Dessiner un rectangle pour corner de gauche en haut
            case  CircleUp:
                // Initialiser les paramètres du cercle
                circleIndexCX = (float) getWidth() / 2;
                circleIndexCY = (float) getHeight() / 2;
                circleRadius = (float) getHeight() / 3;
                canvas.drawCircle(circleIndexCX, circleIndexCY, circleRadius, paint);

                // Initialiser les paramètres du 2eme rectangle
                rectIndexLeft = (float) getWidth() / 4;
                rectIndexTop = 0;
                rectIndexRight = (float) getWidth() * 3 / 4;
                rectIndexBottom = (float) getHeight() * 3 / 4;
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);

                break;

            // Dessiner un rectangle pour corner de gauche en haut
            case  CircleLeft:
                // Initialiser les paramètres du cercle
                circleIndexCX = (float) getWidth() / 2;
                circleIndexCY = (float) getHeight() / 2;
                circleRadius = (float) getHeight() / 3;
                canvas.drawCircle(circleIndexCX, circleIndexCY, circleRadius, paint);

                // Initialiser les paramètres du 2eme rectangle
                rectIndexLeft = 0;
                rectIndexTop = (float) getWidth() / 4;
                rectIndexRight = (float) getWidth() / 2;
                rectIndexBottom = (float) getHeight() * 3 / 4;
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);

                break;

            // Dessiner un rectangle pour corner de gauche en haut
            case  CircleDown:
                // Initialiser les paramètres du cercle
                circleIndexCX = (float) getWidth() / 2;
                circleIndexCY = (float) getHeight() / 2;
                circleRadius = (float) getHeight() / 3;
                canvas.drawCircle(circleIndexCX, circleIndexCY, circleRadius, paint);

                // Initialiser les paramètres du 2eme rectangle
                rectIndexLeft = (float) getWidth() / 4;
                rectIndexTop = (float) getHeight() / 4;
                rectIndexRight = (float) getWidth() * 3 / 4;
                rectIndexBottom = (float) getHeight();
                canvas.drawRect(rectIndexLeft, rectIndexTop, rectIndexRight, rectIndexBottom, paint);

                break;

            default:
                paint.setColor(Color.WHITE);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, getHeight() / 3, paint);
        }

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //Sets the cells as square
        if (width > height) {
            size = height;
        } else {
            size = width;
        }
        setMeasuredDimension(size, size);
    }

    // Getters and Setters
    public Sharp getSharp() {
        return sharp;
    }

    public void setSharp(Sharp sharp) {
        this.sharp = sharp;
    }


    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getIndexRow() {
        return indexRow;
    }

    public void setIndexRow(int indexRow) {
        this.indexRow = indexRow;
    }

    public int getIndexCol() {
        return indexCol;
    }

    public void setIndexY(int indexCol) {
        this.indexCol = indexCol;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }


}

