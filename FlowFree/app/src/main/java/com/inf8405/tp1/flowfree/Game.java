package com.inf8405.tp1.flowfree;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;


public class Game extends Activity {

    TableLayout table_game;
    int size = 8;
    int level = 3;
    Cell[][] ArrayCell; // Tableau qui contient les cellules du jeu
    ArrayList<Cell> cell_used = new ArrayList<>();
    int game_score = 0;
    boolean active_draw = false;
    int color_chosen;
    int IndexPreviousCellRow = 0;
    int IndexPreviousCellCol = 0;
    Cell previous_cell ;
    Cell current_cell;

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
                // Receppurer les parametres de TableLayout
                table_game.setMeasureWithLargestChildEnabled(true);
                int cellWidth = table_game.getMeasuredWidth() / Game.this.size;
                int cellHeight = table_game.getMeasuredHeight() / Game.this.size;

                // position de la case appuyee
                int IndexCol = (int) event.getX() / cellWidth;
                int IndexRow = (int) event.getY() / cellHeight;

                //
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (ArrayCell[IndexRow][IndexCol].getColor() != Color.WHITE &&
                            ArrayCell[IndexRow][IndexCol].isUsed()) {
                        IndexPreviousCellRow = IndexRow;
                        IndexPreviousCellCol = IndexCol;
                        active_draw = true;
                        color_chosen = ArrayCell[IndexRow][IndexCol].getColor();
                        ArrayCell[IndexRow][IndexCol].setType(Cell.CellType.First);
                        addCellUsed(ArrayCell[IndexRow][IndexCol]);
                        previous_cell = cell_used.get(cell_used.size() - 1);
                        current_cell = cell_used.get(cell_used.size() - 1);
                    }

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    color_chosen =  Color.WHITE;
                    if (ArrayCell[IndexRow][IndexCol].getColor() != color_chosen) {
                        active_draw = false;
                        clearCellDrawen();
                        System.out.println("ACTIONUP ****");
                    } else {
                        // TODO : Increment score
                        ArrayCell[IndexRow][IndexCol].setType(Cell.CellType.Second);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() ==
                        MotionEvent.ACTION_MOVE) {

                    if (cell_used.size() > 0) {
                        addCellUsed(ArrayCell[IndexRow][IndexCol]);
                    }
                    if (cell_used.size() > 1) {
                        previous_cell = cell_used.get(cell_used.size() - 2);
                        current_cell = cell_used.get(cell_used.size() - 1);
                        IndexPreviousCellRow = previous_cell.getIndexRow();
                        IndexPreviousCellCol = previous_cell.getIndexCol();

                        System.out.println("previous_cell : X " + previous_cell.getIndexRow() + " -" +
                                " Y " + previous_cell.getIndexCol());
                        System.out.println("current_cell : X " + current_cell.getIndexRow() + " - Y" +
                                " " + current_cell.getIndexCol());
                    }
// From Down to Up
                    if (IndexPreviousCellRow > IndexRow && IndexPreviousCellCol == IndexCol) {
                        drawTube(IndexRow, IndexCol,  color_chosen, active_draw, Cell.Sharp.UpDown);
                        if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() && ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell.Sharp.Circle)
                            ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp.CircleUp);
                    }

                    // From Up to Down
                    if (IndexPreviousCellRow < IndexRow && IndexPreviousCellCol == IndexCol) {
                        drawTube(IndexRow, IndexCol, color_chosen, active_draw, Cell.Sharp.UpDown);
                        if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() && ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell.Sharp.Circle)
                            ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp.CircleDown);
                    }

                    // From Right to Left
                    if (IndexPreviousCellRow == IndexRow && IndexPreviousCellCol > IndexCol) {
                        drawTube(IndexRow, IndexCol, color_chosen, active_draw, Cell.Sharp.LeftRight);
                        if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() && ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell.Sharp.Circle)
                            ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp.CircleLeft);
                    }

                    // From Left to Right
                    if (IndexPreviousCellRow == IndexRow && IndexPreviousCellCol < IndexCol) {
                        drawTube(IndexRow, IndexCol, color_chosen, active_draw, Cell.Sharp.LeftRight);
                        if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() && ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell.Sharp.Circle)
                            ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp.CircleRight);
                    }

                    // Corners

                    // From Down to Right
                    if (IndexPreviousCellRow > IndexRow && IndexPreviousCellCol < IndexCol) {
                        drawTube(IndexRow, IndexCol,  color_chosen, active_draw, Cell.Sharp.RightDown);
                        if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() && ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell.Sharp.Circle)
                            ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp.CircleUp);
                    }

                    // From Up to Right
                    if (IndexPreviousCellRow < IndexRow && IndexPreviousCellCol < IndexCol) {
                        drawTube(IndexRow, IndexCol,  color_chosen, active_draw, Cell.Sharp.RightUp);
                        if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() && ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell.Sharp.Circle)
                            ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp.CircleDown);
                    }

                    // From Down to Left
                    if (IndexPreviousCellRow > IndexRow && IndexPreviousCellCol > IndexCol) {
                        drawTube(IndexRow, IndexCol,  color_chosen, active_draw, Cell.Sharp.LeftDown);
                        if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() && ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell.Sharp.Circle)
                            ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp.CircleLeft);
                    }

                    // From Up to Left
                    if (IndexPreviousCellRow < IndexRow && IndexPreviousCellCol > IndexCol) {
                        drawTube(IndexRow, IndexCol, color_chosen, active_draw, Cell.Sharp.LeftUp);
                        if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() && ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell.Sharp.Circle)
                            ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp.CircleRight);
                    }
                    setScore(100);
                    return true;
                }
                return false;
            }
        });

    }

    private void addCellUsed(Cell cell_to_add) {
        boolean exist = false;
        for (Cell item : cell_used) {
            if (item.getIndexRow() == cell_to_add.getIndexRow() && item.getIndexCol() == cell_to_add.getIndexCol()) {
                exist = true;

            }
        }
        if (!exist) {
            cell_used.add(cell_to_add);
            System.out.println("Size :" + cell_used.size());

        }
    }

    private void clearCellDrawen() {
        for (Cell item : cell_used) {
            int IndexRow = item.getIndexCol();
            int IndexCol = item.getIndexRow();
            if (!(ArrayCell[IndexRow][IndexCol].getSharp() == Cell.Sharp.Circle ||
                    ArrayCell[IndexRow][IndexCol].getSharp() == Cell.Sharp.CircleUp ||
                    ArrayCell[IndexRow][IndexCol].getSharp() == Cell.Sharp.CircleDown ||
                    ArrayCell[IndexRow][IndexCol].getSharp() == Cell.Sharp.CircleRight ||
                    ArrayCell[IndexRow][IndexCol].getSharp() == Cell.Sharp.CircleLeft)) {

                ArrayCell[IndexRow][IndexCol].setColor(Color.WHITE);
            }
            ArrayCell[IndexRow][IndexCol].setSharp(Cell.Sharp.Circle);
            ArrayCell[IndexRow][IndexCol].setType(Cell.CellType.None);
        }
        cell_used.clear();
        clearTableLayout();
        BuildTable(size);
    }

    private void drawTube(int IndexRow, int IndexCol, int color_chosen, boolean active, Cell
            .Sharp sharp) {
        if (active_draw && !ArrayCell[IndexRow][IndexCol].isUsed()) {
            if (sharp == Cell.Sharp.UpDown && (IndexRow != 0 && IndexRow != size - 1)) {
                ArrayCell[IndexRow][IndexCol].setSharp(sharp);
                ArrayCell[IndexRow][IndexCol].setColor(color_chosen);
                clearTableLayout();
                BuildTable(size);
            } else if (sharp == Cell.Sharp.LeftRight && (IndexCol != 0 && IndexCol != size - 1)) {
                ArrayCell[IndexRow][IndexCol].setSharp(sharp);
                ArrayCell[IndexRow][IndexCol].setColor(color_chosen);
                clearTableLayout();
                BuildTable(size);
            }
        }

    }

    private void setScore(int score) {
        game_score = score;
        TextView text_score = (TextView) findViewById(R.id.score_id);
        text_score.setText(" " + game_score);
    }

    private void BuildTable(int size) {
        clearTableLayout();
        // Remplir la TableLayout
        for (int i = 0; i < size; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams
                    .WRAP_CONTENT));
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
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.RoyalBlue), i, j,
                                    true);
                        } else if ((i == 2 && j == 2) || (i == 3 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Red), i, j, true);
                        } else if ((i == 4 && j == 2) || (i == 5 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Yellow), i, j,
                                    true);
                        } else if ((i == 4 && j == 4) || (i == 5 && j == 1)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Orange), i, j,
                                    true);
                        } else if ((i == 5 && j == 0) || (i == 5 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.LimeGreen), i, j,
                                    true);
                        } else {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, Color.WHITE, i, j, false);
                        }
                    }
                    // Level 2
                    if (level == 2) {
                        if ((i == 5 && j == 0) || (i == 3 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.RoyalBlue), i, j,
                                    true);
                        } else if ((i == 4 && j == 6) || (i == 6 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Red), i, j, true);
                        } else if ((i == 5 && j == 1) || (i == 2 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Yellow), i, j,
                                    true);
                        } else if ((i == 5 && j == 3) || (i == 2 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Orange), i, j,
                                    true);
                        } else if ((i == 4 && j == 5) || (i == 6 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.LimeGreen), i, j,
                                    true);
                        } else if ((i == 1 && j == 1) || (i == 5 && j == 2)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleTurquoise),
                                    i, j, true);
                        } else if ((i == 2 && j == 2) || (i == 1 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Gray), i, j, true);
                        } else {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, Color.WHITE, i, j, false);
                        }
                    }
                    // Level 3
                    if (level == 3) {
                        if ((i == 5 && j == 0) || (i == 4 && j == 3)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.RoyalBlue), i, j,
                                    true);
                        } else if ((i == 5 && j == 3) || (i == 6 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Red), i, j, true);
                        } else if ((i == 2 && j == 2) || (i == 2 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Yellow), i, j,
                                    true);
                        } else if ((i == 5 && j == 1) || (i == 5 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Orange), i, j,
                                    true);
                        } else if ((i == 3 && j == 1) || (i == 4 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.LimeGreen), i, j,
                                    true);
                        } else if ((i == 2 && j == 1) || (i == 4 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleTurquoise),
                                    i, j, true);
                        } else {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, Color.WHITE, i, j, false);
                        }
                    }
                }


                // Tables de size == 8
                if (size == 8) {
                    // Level 1
                    if (level == 1) {
                        if ((i == 1 && j == 5) || (i == 1 && j == 7)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.RoyalBlue), i, j,
                                    true);
                        } else if ((i == 0 && j == 4) || (i == 5 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Red), i, j, true);
                        } else if ((i == 3 && j == 0) || (i == 3 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Yellow), i, j,
                                    true);
                        } else if ((i == 4 && j == 3) || (i == 5 && j == 2)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Orange), i, j,
                                    true);
                        } else if ((i == 1 && j == 0) || (i == 2 && j == 2)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.LimeGreen), i, j,
                                    true);
                        } else if ((i == 0 && j == 0) || (i == 2 && j == 0)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Maroon), i, j,
                                    true);
                        } else if ((i == 2 && j == 7) || (i == 7 && j == 7)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleTurquoise),
                                    i, j, true);
                        } else if ((i == 1 && j == 6) || (i == 2 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Gray), i, j, true);
                        } else if ((i == 3 && j == 5) || (i == 4 && j == 2)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleGreen), i, j,
                                    true);
                        } else {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, Color.WHITE, i, j, false);
                        }
                    }
                    // Level 2
                    if (level == 2) {
                        if ((i == 6 && j == 2) || (i == 5 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.RoyalBlue), i, j,
                                    true);
                        } else if ((i == 6 && j == 1) || (i == 4 && j == 3)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Red), i, j, true);
                        } else if ((i == 0 && j == 5) || (i == 3 && j == 5)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Yellow), i, j,
                                    true);
                        } else if ((i == 1 && j == 4) || (i == 6 && j == 3)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Orange), i, j,
                                    true);
                        } else if ((i == 1 && j == 6) || (i == 3 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.LimeGreen), i, j,
                                    true);
                        } else if ((i == 0 && j == 4) || (i == 0 && j == 6)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleTurquoise),
                                    i, j, true);
                        } else if ((i == 2 && j == 2) || (i == 4 && j == 2)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleGreen), i, j,
                                    true);
                        } else {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, Color.WHITE, i, j, false);
                        }
                    }
                    // Level 3
                    if (level == 3) {
                        if ((i == 1 && j == 1) || (i == 6 && j == 2)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.RoyalBlue), i, j,
                                    true);
                        } else if ((i == 5 && j == 2) || (i == 4 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Red), i, j, true);
                        } else if ((i == 1 && j == 5) || (i == 5 && j == 3)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Yellow), i, j,
                                    true);
                        } else if ((i == 1 && j == 3) || (i == 3 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Orange), i, j,
                                    true);
                        } else if ((i == 3 && j == 0) || (i == 0 && j == 3)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.LimeGreen), i, j,
                                    true);
                        } else if ((i == 1 && j == 2) || (i == 3 && j == 3)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Maroon), i, j,
                                    true);
                        } else if ((i == 4 && j == 0) || (i == 1 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleTurquoise),
                                    i, j, true);
                        } else if ((i == 2 && j == 5) || (i == 5 && j == 4)) {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleGreen), i, j,
                                    true);
                        } else {
                            ArrayCell[i][j] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, Color.WHITE, i, j, false);
                        }
                    }
                }
            }

        }
    }

}