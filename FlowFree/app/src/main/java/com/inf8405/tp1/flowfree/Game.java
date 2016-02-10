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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Game extends Activity {

    TableLayout table_game;
    int size = 8;
    int level = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        table_game = (TableLayout) findViewById(R.id.table_game);

        // Remplir la table selon le niveau et la taille passee en parametres
        BuildTable(size, level);

        table_game.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    int x = (int) event.getRawX();
                    int y = (int) event.getRawY();
                    System.out.println("x=" + x / 160);
                    System.out.println("y=" + y / 160);
//                    setPosition(x, y);
//                    drawCell(x / 160, y / 160);
                    return true;
                }
                return false;
            }
        });

    }

    private void BuildTable(int size, int level) {

        // Remplir la table
        for (int i = 1; i <= size; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            for (int j = 1; j <= size; j++) {
                // Tables de size == 7
                if (size == 7) {
                    // Level 1
                    if (level == 1) {
                        if ((i == 2 && j == 1) || (i == 7 && j == 1)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.RoyalBlue), i, j, false));
                        } else if ((i == 3 && j == 3) || (i == 4 && j == 5)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Red), i, j, false));
                        } else if ((i == 5 && j == 3) || (i == 6 && j == 5)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Yellow), i, j, false));
                        } else if ((i == 5 && j == 5) || (i == 6 && j == 2)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Orange), i, j, false));
                        } else if ((i == 6 && j == 1) || (i == 6 && j == 6)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.LimeGreen), i, j, false));
                        } else {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, Color.WHITE, i, j, false));
                        }
                    }
                    // Level 2
                    if (level == 2) {
                        if ((i == 6 && j == 1) || (i == 4 && j == 7)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.RoyalBlue), i, j, false));
                        } else if ((i == 5 && j == 7) || (i == 7 && j == 7)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Red), i, j, false));
                        } else if ((i == 6 && j == 2) || (i == 3 && j == 7)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Yellow), i, j, false));
                        } else if ((i == 6 && j == 4) || (i == 3 && j == 6)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Orange), i, j, false));
                        } else if ((i == 5 && j == 6) || (i == 7 && j == 6)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.LimeGreen), i, j, false));
                        } else if ((i == 2 && j == 2) || (i == 6 && j == 3)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.PaleTurquoise), i, j, false));
                        } else if ((i == 3 && j == 3) || (i == 2 && j == 6)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Gray), i, j, false));
                        } else {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, Color.WHITE, i, j, false));
                        }
                    }
                    // Level 3
                    if (level == 3) {
                        if ((i == 6 && j == 1) || (i == 5 && j == 4)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.RoyalBlue), i, j, false));
                        } else if ((i == 6 && j == 4) || (i == 7 && j == 7)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Red), i, j, false));
                        } else if ((i == 3 && j == 3) || (i == 3 && j == 5)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Yellow), i, j, false));
                        } else if ((i == 6 && j == 2) || (i == 6 && j == 5)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Orange), i, j, false));
                        } else if ((i == 4 && j == 2) || (i == 5 && j == 5)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.LimeGreen), i, j, false));
                        } else if ((i == 3 && j == 2) || (i == 5 && j == 6)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.PaleTurquoise), i, j, false));
                        } else {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, Color.WHITE, i, j, false));
                        }
                    }
                }


                // Tables de size == 8
                if (size == 8) {
                    // Level 1
                    if (level == 1) {
                        if ((i == 2 && j == 6) || (i == 2 && j == 8)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.RoyalBlue), i, j, false));
                        } else if ((i == 1 && j == 5) || (i == 6 && j == 5)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Red), i, j, false));
                        } else if ((i == 4 && j == 1) || (i == 4 && j == 7)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Yellow), i, j, false));
                        } else if ((i == 5 && j == 4) || (i == 6 && j == 3)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Orange), i, j, false));
                        } else if ((i == 2 && j == 1) || (i == 3 && j == 3)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.LimeGreen), i, j, false));
                        } else if ((i == 1 && j == 1) || (i == 3 && j == 1)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Maroon), i, j, false));
                        } else if ((i == 3 && j == 8) || (i == 8 && j == 8)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.PaleTurquoise), i, j, false));
                        } else if ((i == 2 && j == 7) || (i == 3 && j == 6)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Gray), i, j, false));
                        } else if ((i == 4 && j == 6) || (i == 5 && j == 3)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.PaleGreen), i, j, false));
                        } else {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, Color.WHITE, i, j, false));
                        }
                    }
                    // Level 2
                    if (level == 2) {
                        if ((i == 7 && j == 3) || (i == 6 && j == 6)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.RoyalBlue), i, j, false));
                        } else if ((i == 7 && j == 2) || (i == 5 && j == 4)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Red), i, j, false));
                        } else if ((i == 1 && j == 6) || (i == 4 && j == 6)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Yellow), i, j, false));
                        } else if ((i == 2 && j == 5) || (i == 7 && j == 4)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Orange), i, j, false));
                        } else if ((i == 2 && j == 7) || (i == 4 && j == 7)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.LimeGreen), i, j, false));
                        } else if ((i == 1 && j == 5) || (i == 1 && j == 7)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.PaleTurquoise), i, j, false));
                        } else if ((i == 3 && j == 3) || (i == 5 && j == 3)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.PaleGreen), i, j, false));
                        } else {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, Color.WHITE, i, j, false));
                        }
                    }
                    // Level 3
                    if (level == 3) {
                        if ((i == 2 && j == 2) || (i == 7 && j == 3)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.RoyalBlue), i, j, false));
                        } else if ((i == 6 && j == 3) || (i == 5 && j == 5)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Red), i, j, false));
                        } else if ((i == 2 && j == 6) || (i == 6 && j == 4)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Yellow), i, j, false));
                        } else if ((i == 2 && j == 4) || (i == 4 && j == 5)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Orange), i, j, false));
                        } else if ((i == 4 && j == 1) || (i == 1 && j == 4)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.LimeGreen), i, j, false));
                        } else if ((i == 2 && j == 3) || (i == 4 && j == 4)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.Maroon), i, j, false));
                        } else if ((i == 5 && j == 1) || (i == 2 && j == 5)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.PaleTurquoise), i, j, false));
                        } else if ((i == 3 && j == 6) || (i == 6 && j == 5)) {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, ContextCompat.getColor(this, R.color.PaleGreen), i, j, false));
                        } else {
                            row.addView(new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, Color.WHITE, i, j, false));
                        }
                    }
                }
            }

            table_game.addView(row);
        }
    }

//    public void drawCell(int x, int y) {
//        TableRow row = new TableRow(this);
//        row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//                LayoutParams.WRAP_CONTENT));
//        Cell tv = new Cell(this, Cell.Sharp.Circle, Cell.CellType.First, Color.BLUE, x, y, false);
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