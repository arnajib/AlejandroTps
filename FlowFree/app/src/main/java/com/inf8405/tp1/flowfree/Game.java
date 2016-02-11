package com.inf8405.tp1.flowfree;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Game extends Activity {

    TableLayout table_game;
    int size = 8;
    int level = 3;
    Cell[][] ArrayCell; // Tableau qui contient les cellules du jeu
//    int table_game_size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        table_game = (TableLayout) findViewById(R.id.table_game);

        // Remplir la table selon le niveau et la taille passee en parametres
        InitArrayCell(size, level);
        BuildTable(size);
        // Recupperer la taille de TableLayout table_game

        table_game.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    table_game.setMeasureWithLargestChildEnabled(true);
                    int cellWidth = table_game.getMeasuredWidth() / Game.this.size;
                    int cellHeight = table_game.getMeasuredHeight() / Game.this.size;

                    int IndexCol = (int) event.getX() / cellWidth;
                    int IndexRow = (int) event.getY() / cellHeight ;
                    System.out.println("");
                    System.out.println("");
                    System.out.println("-----------------------------");
                    System.out.println("cellWidth =" +  cellWidth);
                    System.out.println("cellHeight =" +  cellHeight);
                    System.out.println("event.getX() =" +  event.getX()/size);
                    System.out.println("event.getY() =" + event.getY()/size);
                    System.out.println("XIndex =" +  IndexRow);
                    System.out.println(" YIndex =" + IndexCol);
                    System.out.println("-----------------------------");
                    System.out.println("");
                    System.out.println("");
                    TextView score = (TextView)findViewById(R.id.score_id);
                    score.setText(" Col: "+IndexCol+" Row: "+IndexRow);
                    ArrayCell[IndexRow][IndexCol].setSharp(Cell.Sharp.Circle);
                    ArrayCell[IndexRow][IndexCol].setColor(Color.BLACK);
                    clearTableLayout();
                    BuildTable(size);
                    return true;
                }
                return false;
            }
        });

    }

    private void BuildTable(int size) {
        clearTableLayout();
        // Remplir le TableLayout
        for (int i = 0; i < size; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < size; j++) {
                row.addView(ArrayCell[i][j]);
            }
            table_game.addView(row);
        }
    }

    private void clearTableLayout() {
        int count = table_game.getChildCount();
        for (int i = 0; i < size; i++) {
            View child = table_game.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
        table_game.removeAllViewsInLayout();
    }


    private void InitArrayCell(int size, int level) {

        ArrayCell = new Cell[size][size];
        // Remplir le tableau
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Tables de size == 7
                if (size == 7) {
                    // Level 1
                    if (level == 1) {
                        if ((i == 1 && j == 0) || (i == 6 && j == 0)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.RoyalBlue), i, j, false);
                        } else if ((i == 2 && j == 2) || (i == 3 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Red), i, j, false);
                        } else if ((i == 4 && j == 2) || (i == 5 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Yellow), i, j, false);
                        } else if ((i == 4 && j == 4) || (i == 5 && j == 1)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Orange), i, j, false);
                        } else if ((i == 5 && j == 0) || (i == 5 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.LimeGreen), i, j, false);
                        } else {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, Color.WHITE, i, j, false);
                        }
                    }
                    // Level 2
                    if (level == 2) {
                        if ((i == 5 && j == 0) || (i == 3 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.RoyalBlue), i, j, false);
                        } else if ((i == 4 && j == 6) || (i == 6 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Red), i, j, false);
                        } else if ((i == 5 && j == 1) || (i == 2 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Yellow), i, j, false);
                        } else if ((i == 5 && j == 3) || (i == 2 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Orange), i, j, false);
                        } else if ((i == 4 && j == 5) || (i == 6 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.LimeGreen), i, j, false);
                        } else if ((i == 1 && j == 1) || (i == 5 && j == 2)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.PaleTurquoise), i, j, false);
                        } else if ((i == 2 && j == 2) || (i == 1 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Gray), i, j, false);
                        } else {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, Color.WHITE, i, j, false);
                        }
                    }
                    // Level 3
                    if (level == 3) {
                        if ((i == 5 && j == 0) || (i == 4 && j == 3)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.RoyalBlue), i, j, false);
                        } else if ((i == 5 && j == 3) || (i == 6 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Red), i, j, false);
                        } else if ((i == 2 && j == 2) || (i == 2 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Yellow), i, j, false);
                        } else if ((i == 5 && j == 1) || (i == 5 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Orange), i, j, false);
                        } else if ((i == 3 && j == 1) || (i == 4 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.LimeGreen), i, j, false);
                        } else if ((i == 2 && j == 1) || (i == 4 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.PaleTurquoise), i, j, false);
                        } else {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, Color.WHITE, i, j, false);
                        }
                    }
                }


                // Tables de size == 8
                if (size == 8) {
                    // Level 1
                    if (level == 1) {
                        if ((i == 1 && j == 5) || (i == 1 && j == 7)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.RoyalBlue), i, j, false);
                        } else if ((i == 0 && j == 4) || (i == 5 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Red), i, j, false);
                        } else if ((i == 3 && j == 0) || (i == 3 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Yellow), i, j, false);
                        } else if ((i == 4 && j == 3) || (i == 5 && j == 2)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Orange), i, j, false);
                        } else if ((i == 1 && j == 0) || (i == 2 && j == 2)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.LimeGreen), i, j, false);
                        } else if ((i == 0 && j == 0) || (i == 2 && j == 0)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Maroon), i, j, false);
                        } else if ((i == 2 && j == 7) || (i == 7 && j == 7)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.PaleTurquoise), i, j, false);
                        } else if ((i == 1 && j == 6) || (i == 2 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Gray), i, j, false);
                        } else if ((i == 3 && j == 5) || (i == 4 && j == 2)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.PaleGreen), i, j, false);
                        } else {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, Color.WHITE, i, j, false);
                        }
                    }
                    // Level 2
                    if (level == 2) {
                        if ((i == 6 && j == 2) || (i == 5 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.RoyalBlue), i, j, false);
                        } else if ((i == 6 && j == 1) || (i == 4 && j == 3)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Red), i, j, false);
                        } else if ((i == 0 && j == 5) || (i == 3 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Yellow), i, j, false);
                        } else if ((i == 1 && j == 4) || (i == 6 && j == 3)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Orange), i, j, false);
                        } else if ((i == 1 && j == 6) || (i == 3 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.LimeGreen), i, j, false);
                        } else if ((i == 0 && j == 4) || (i == 0 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.PaleTurquoise), i, j, false);
                        } else if ((i == 2 && j == 2) || (i == 4 && j == 2)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.PaleGreen), i, j, false);
                        } else {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, Color.WHITE, i, j, false);
                        }
                    }
                    // Level 3
                    if (level == 3) {
                        if ((i == 1 && j == 1) || (i == 6 && j == 2)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.RoyalBlue), i, j, false);
                        } else if ((i == 5 && j == 2) || (i == 4 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Red), i, j, false);
                        } else if ((i == 1 && j == 5) || (i == 5 && j == 3)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Yellow), i, j, false);
                        } else if ((i == 1 && j == 3) || (i == 3 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Orange), i, j, false);
                        } else if ((i == 3 && j == 0) || (i == 0 && j == 3)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.LimeGreen), i, j, false);
                        } else if ((i == 1 && j == 2) || (i == 3 && j == 3)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.Maroon), i, j, false);
                        } else if ((i == 4 && j == 0) || (i == 1 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.PaleTurquoise), i, j, false);
                        } else if ((i == 2 && j == 5) || (i == 5 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, ContextCompat.getColor(this, R.color.PaleGreen), i, j, false);
                        } else {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, Color.WHITE, i, j, false);
                        }
                    }
                }
            }

        }
    }
//    public void drawCell(int x, int y) {
//        TableRow row = new TableRow(this);
//        row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//                LayoutParams.WRAP_CONTENT));
//        Cell tv = new Cell(this, Cell.Sharp.Circle, Cell.CellType.None, Color.BLUE, x, y, false);
//        row.addView(tv, x, y);
//        table_game.addView(row);
//
//    }

//    public Pair<Integer, Integer> getPosition() {
//        return position;
//    }
//
//    public void setPosition(int x, int y) {
//        position = new Pair(x, y);
//
//    }
}