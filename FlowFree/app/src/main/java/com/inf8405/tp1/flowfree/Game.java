package com.inf8405.tp1.flowfree;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.ArrayList;


public class Game extends Activity {

    TableLayout table_game;
    // TODO: remplacer size et level a partir de level Activity
    int size = 7;
    int level = 2;
    Cell[][] ArrayCell; // Tableau qui contient les cellules du jeu
    ArrayList<Cell> cell_used = new ArrayList<>();
    int game_score = 0;
    boolean active_draw = false;
    int color_chosen;
    int IndexPreviousCellRow = 0;
    int IndexPreviousCellCol = 0;
    int IndexCurrentCellRow = 0;
    int IndexCurrentCellCol = 0;
    Cell previous_cell;
    Cell current_cell;
    boolean over = false; // Partie perdue
    AlertDialog.Builder alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        table_game = (TableLayout) findViewById(R.id.table_game);

        // Remplir la table selon le niveau et la taille passee en parametres
        InitArrayCell(size, level);
        BuildTable(size);

        //Afficher message de victoire
        alert = new AlertDialog.Builder(Game.this);
        alert.setTitle("About");
        alert.setMessage("Sample About");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id) {
                Toast.makeText (Game.this, "Success", Toast.LENGTH_SHORT) .show();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(Game.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });


        table_game.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                // Receppurer les parametres de TableLayout
                table_game.setMeasureWithLargestChildEnabled(true);
                int cellWidth = table_game.getMeasuredWidth() / Game.this.size;
                int cellHeight = table_game.getMeasuredHeight() / Game.this.size;

                // position de la case appuyee
                int IndexRow = (int) event.getY() / cellWidth;
                int IndexCol = (int) event.getX() / cellHeight;
                //
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (ArrayCell[IndexRow][IndexCol].getColor() != Color.WHITE &&
                            ArrayCell[IndexRow][IndexCol].isUsed()) {
                        IndexPreviousCellRow = IndexRow;
                        IndexPreviousCellCol = IndexCol;
                        IndexCurrentCellRow = IndexRow;
                        IndexCurrentCellCol = IndexCol;
                        active_draw = true;
                        color_chosen = ArrayCell[IndexRow][IndexCol].getColor();


                        if (!isConnected(ArrayCell[IndexRow][IndexCol])) {
                            ArrayCell[IndexRow][IndexCol].setType(Cell.CellType.First);
                            addCellUsed(ArrayCell[IndexRow][IndexCol]);
                            previous_cell = cell_used.get(cell_used.size() - 1);
                            current_cell = cell_used.get(cell_used.size() - 1);
                        }
                    }

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    color_chosen =  Color.WHITE;
                    if (ArrayCell[IndexRow][IndexCol].getColor() != color_chosen ||
                            !ArrayCell[IndexRow][IndexCol].isUsed()) {
                        active_draw = false;
                        clearCellDrawen();
                    } else {
                        if (!isConnected(ArrayCell[IndexRow][IndexCol])) {
                            ArrayCell[IndexRow][IndexCol].setType(Cell.CellType.Second);
                            redesignSecondCircle(IndexCurrentCellRow, IndexCurrentCellCol);
                            // Mettre a jour le nombre de tubes
                                ++game_score;

                        }
                        cell_used.clear();

                        setScore(game_score);
                    }
                    if(isSuccessfulParty())
                    {
                        alert.show();
                    }

                }
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() ==
                        MotionEvent.ACTION_MOVE) {

                    if (cell_used.size() > 0 && (ArrayCell[IndexRow][IndexCol].getColor() ==
                            Color.WHITE || ArrayCell[IndexRow][IndexCol].isUsed())) {
                        addCellUsed(ArrayCell[IndexRow][IndexCol]);
                    }
                    if (cell_used.size() > 1) {
                        previous_cell = cell_used.get(cell_used.size() - 2);
                        current_cell = cell_used.get(cell_used.size() - 1);
                        IndexPreviousCellRow = previous_cell.getIndexRow();
                        IndexPreviousCellCol = previous_cell.getIndexCol();
                        IndexCurrentCellRow = current_cell.getIndexRow();
                        IndexCurrentCellCol = current_cell.getIndexCol();

                    }

                    // Dessiner les lignes entre les cercles
                    DrawLine();
                    // Corners


                    return true;
                }
                return false;
            }
        });

    }

    private boolean isOver() {

        for (int i = 0; i < cell_used.size() - 1; i++) {
            if ((cell_used.get(i).getIndexRow() - cell_used.get(i + 1).getIndexRow() > 1) ||
                    (cell_used.get(i).getIndexCol() - cell_used.get(i + 1).getIndexCol() > 1)) {
                over = true;
            }
        }

        return over;
    }

    private boolean isSuccessfulParty(){
        boolean success = true;

        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                  if( ArrayCell[row][col].getColor()== Color.WHITE)
                      success = false;
            }
        }

        return (success && !isOver());
    }
    private boolean isConnected(Cell cell) {
        boolean connected = false;
        if (cell.getType() != Cell.CellType.None && cell.isUsed()) {
            for (int row = 0; row < size; ++row) {
                for (int col = 0; col < size; ++col) {
                    if (cell.getType() == Cell.CellType.First) {
                        if (cell.getColor() == ArrayCell[row][col].getColor() &&
                                ArrayCell[row][col].getType() == Cell.CellType.Second)
                            connected = true;
                    } else {
                        if (cell.getColor() == ArrayCell[row][col].getColor() &&
                                ArrayCell[row][col].getType() == Cell.CellType.First)
                            connected = true;

                    }
                }
            }
        }
        return connected;

    }

    private void addCellUsed(Cell cell_to_add) {
        boolean exist = false;
        for (Cell item : cell_used) {
            if (item.getIndexRow() == cell_to_add.getIndexRow() && item.getIndexCol() ==
                    cell_to_add.getIndexCol()) {
                exist = true;

            }
        }
        if (!exist) {
            cell_used.add(cell_to_add);
        }
    }

    private void redesignSecondCircle(int IndexRow, int IndexCol) {
        // From Up to Down
        if (IndexPreviousCellRow > IndexCurrentCellRow && IndexPreviousCellCol ==
                IndexCurrentCellCol) {
            if (ArrayCell[IndexCurrentCellRow][IndexCurrentCellCol].isUsed() &&
                    ArrayCell[IndexCurrentCellRow][IndexCurrentCellCol].getSharp() == Cell.Sharp
                            .Circle)
                ArrayCell[IndexCurrentCellRow][IndexCurrentCellCol].setSharp(Cell.Sharp.CircleDown);
        }

        // From Down to Up
        if (IndexPreviousCellRow < IndexCurrentCellRow && IndexPreviousCellCol == IndexCol) {
            if (ArrayCell[IndexCurrentCellRow][IndexCurrentCellCol].isUsed() &&
                    ArrayCell[IndexCurrentCellRow][IndexCurrentCellCol].getSharp() == Cell.Sharp
                            .Circle)
                ArrayCell[IndexCurrentCellRow][IndexCurrentCellCol].setSharp(Cell.Sharp.CircleUp);
        }

        // From Right to Left
        if (IndexPreviousCellRow == IndexCurrentCellRow && IndexPreviousCellCol >
                IndexCurrentCellCol) {
            if (ArrayCell[IndexCurrentCellRow][IndexCurrentCellCol].isUsed() &&
                    ArrayCell[IndexCurrentCellRow][IndexCurrentCellCol].getSharp() == Cell.Sharp
                            .Circle)
                ArrayCell[IndexCurrentCellRow][IndexCurrentCellCol].setSharp(Cell.Sharp
                        .CircleRight);
        }

        // From Left to Right
        if (IndexPreviousCellRow == IndexCurrentCellRow && IndexPreviousCellCol <
                IndexCurrentCellCol) {
            if (ArrayCell[IndexCurrentCellRow][IndexCurrentCellCol].isUsed() &&
                    ArrayCell[IndexCurrentCellRow][IndexCurrentCellCol].getSharp() == Cell.Sharp
                            .Circle)
                ArrayCell[IndexCurrentCellRow][IndexCurrentCellCol].setSharp(Cell.Sharp.CircleLeft);
        }


        clearTableLayout();
        BuildTable(size);
    }

    private void DrawLine() {

        // cellules temporaires pour verifier si nous avons un coins
        Cell cell_a = null;
        Cell cell_b = null;
        Cell cell_c = null;

        // pour avoir un corner, il faut au moins 3 cellules choisies
        if (cell_used.size() >= 3) {
            cell_a = cell_used.get(cell_used.size() - 3);
            cell_b = cell_used.get(cell_used.size() - 2);
            cell_c = cell_used.get(cell_used.size() - 1);

        }

        // Verifier si c'est un corner
        if ((cell_used.size() >= 3) && (cell_a.getIndexRow() != cell_c.getIndexRow() && cell_a
                .getIndexCol() != cell_c.getIndexCol())) {

            // From Left to Down or From Down to Left
            if (((cell_c.getIndexRow() == cell_b.getIndexRow()) && (cell_a.getIndexCol() ==
                    cell_b.getIndexCol()) && (cell_a.getIndexCol() > cell_c.getIndexCol()) &&
                    (cell_a.getIndexRow() > cell_c.getIndexRow())) || ((cell_a.getIndexRow() ==
                    cell_b.getIndexRow()) && (cell_c.getIndexCol() == cell_b.getIndexCol()) &&
                    (cell_a.getIndexCol() < cell_c.getIndexCol()) && (cell_a.getIndexRow() <
                    cell_c.getIndexRow()))) {

                if (!ArrayCell[cell_b.getIndexRow()][cell_b.getIndexCol()].isUsed()) {
                    ArrayCell[cell_b.getIndexRow()][cell_b.getIndexCol()].setSharp(Cell.Sharp
                            .LeftDown);
                    ArrayCell[cell_b.getIndexRow()][cell_b.getIndexCol()].setColor(color_chosen);
                    clearTableLayout();
                    BuildTable(size);
                }
                System.out.println("LeftDown");

            }

            // From Left to Up or From Up to Left
            if (((cell_a.getIndexRow() == cell_b.getIndexRow()) && (cell_c.getIndexCol() ==
                    cell_b.getIndexCol()) && (cell_a.getIndexCol() < cell_c.getIndexCol()) &&
                    (cell_a.getIndexRow() > cell_c.getIndexRow())) || ((cell_c.getIndexRow() ==
                    cell_b.getIndexRow()) && (cell_a.getIndexCol() == cell_b.getIndexCol()) &&
                    (cell_a.getIndexCol() > cell_c.getIndexCol()) && (cell_a.getIndexRow() <
                    cell_c.getIndexRow()))) {

                if (!ArrayCell[cell_b.getIndexRow()][cell_b.getIndexCol()].isUsed()) {
                    ArrayCell[cell_b.getIndexRow()][cell_b.getIndexCol()].setSharp(Cell.Sharp
                            .LeftUp);
                    ArrayCell[cell_b.getIndexRow()][cell_b.getIndexCol()].setColor(color_chosen);
                    clearTableLayout();
                    BuildTable(size);
                }
                System.out.println("LeftUp");

            }

            // From Right to Down or From Down to Right
            if (((cell_a.getIndexRow() == cell_b.getIndexRow()) && (cell_c.getIndexCol() ==
                    cell_b.getIndexCol()) && (cell_a.getIndexCol() > cell_c.getIndexCol()) &&
                    (cell_a.getIndexRow() < cell_c.getIndexRow())) || ((cell_c.getIndexRow() ==
                    cell_b.getIndexRow()) && (cell_a.getIndexCol() == cell_b.getIndexCol()) &&
                    (cell_a.getIndexCol() < cell_c.getIndexCol()) && (cell_a.getIndexRow() >
                    cell_c.getIndexRow()))) {

                if (!ArrayCell[cell_b.getIndexRow()][cell_b.getIndexCol()].isUsed()) {
                    ArrayCell[cell_b.getIndexRow()][cell_b.getIndexCol()].setSharp(Cell.Sharp
                            .RightDown);
                    ArrayCell[cell_b.getIndexRow()][cell_b.getIndexCol()].setColor(color_chosen);
                    clearTableLayout();
                    BuildTable(size);
                }
                System.out.println("RightDown");

            }

            // From Right to Up or From Up to Right
            if (((cell_a.getIndexRow() == cell_b.getIndexRow()) && (cell_c.getIndexCol() ==
                    cell_b.getIndexCol()) && (cell_a.getIndexCol() > cell_c.getIndexCol()) &&
                    (cell_a.getIndexRow() > cell_c.getIndexRow())) || ((cell_c.getIndexRow() ==
                    cell_b.getIndexRow()) && (cell_a.getIndexCol() == cell_b.getIndexCol()) &&
                    (cell_a.getIndexCol() < cell_c.getIndexCol()) && (cell_a.getIndexRow() <
                    cell_c.getIndexRow()))) {

                if (!ArrayCell[cell_b.getIndexRow()][cell_b.getIndexCol()].isUsed()) {
                    ArrayCell[cell_b.getIndexRow()][cell_b.getIndexCol()].setSharp(Cell.Sharp
                            .RightUp);
                    ArrayCell[cell_b.getIndexRow()][cell_b.getIndexCol()].setColor(color_chosen);
                    clearTableLayout();
                    BuildTable(size);
                }
                System.out.println("RightUp");

            }

        } else {
            // From Down to Up
            if (IndexPreviousCellRow > IndexCurrentCellRow && IndexPreviousCellCol ==
                    IndexCurrentCellCol) {

                drawTube(IndexPreviousCellRow, IndexPreviousCellCol, color_chosen, active_draw,
                        Cell.Sharp.UpDown);
                if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() &&
                        ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell
                                .Sharp.Circle)
                    ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp
                            .CircleUp);
            }

            // From Up to Down
            if (IndexPreviousCellRow < IndexCurrentCellRow && IndexPreviousCellCol ==
                    IndexCurrentCellCol) {
                drawTube(IndexPreviousCellRow, IndexPreviousCellCol, color_chosen, active_draw,
                        Cell.Sharp.UpDown);
                if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() &&
                        ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell
                                .Sharp.Circle)
                    ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp
                            .CircleDown);
            }

            // From Right to Left
            if (IndexPreviousCellRow == IndexCurrentCellRow && IndexPreviousCellCol >
                    IndexCurrentCellCol) {
                drawTube(IndexPreviousCellRow, IndexPreviousCellCol, color_chosen, active_draw,
                        Cell.Sharp.LeftRight);
                if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() &&
                        ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell
                                .Sharp.Circle)
                    ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp
                            .CircleLeft);
            }

            // From Left to Right
            if (IndexPreviousCellRow == IndexCurrentCellRow && IndexPreviousCellCol <
                    IndexCurrentCellCol) {
                drawTube(IndexPreviousCellRow, IndexPreviousCellCol, color_chosen, active_draw,
                        Cell.Sharp.LeftRight);
                if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() &&
                        ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell
                                .Sharp.Circle)
                    ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp
                            .CircleRight);
            }
            // From Down to Right
            if (IndexPreviousCellRow > IndexCurrentCellRow && IndexPreviousCellCol <
                    IndexCurrentCellCol) {
                drawTube(IndexPreviousCellRow, IndexPreviousCellCol, color_chosen, active_draw,
                        Cell.Sharp.RightDown);
                if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() &&
                        ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell
                                .Sharp.Circle)
                    ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp
                            .CircleUp);
            }

            // From Up to Right
            if (IndexPreviousCellRow < IndexCurrentCellRow && IndexPreviousCellCol <
                    IndexCurrentCellCol) {
                drawTube(IndexPreviousCellRow, IndexPreviousCellCol, color_chosen, active_draw,
                        Cell.Sharp.RightUp);
                if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() &&
                        ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell
                                .Sharp.Circle)
                    ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp
                            .CircleDown);
            }

            // From Down to Left
            if (IndexPreviousCellRow > IndexCurrentCellRow && IndexPreviousCellCol >
                    IndexCurrentCellCol) {
                drawTube(IndexPreviousCellRow, IndexPreviousCellCol, color_chosen, active_draw,
                        Cell.Sharp.LeftDown);
                if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() &&
                        ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell
                                .Sharp.Circle)
                    ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp
                            .CircleLeft);

            }

            // From Up to Left
            if (IndexPreviousCellRow < IndexCurrentCellRow && IndexPreviousCellCol >
                    IndexCurrentCellCol) {
                drawTube(IndexPreviousCellRow, IndexPreviousCellCol, color_chosen, active_draw,
                        Cell.Sharp.LeftUp);
                if (ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].isUsed() &&
                        ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].getSharp() == Cell
                                .Sharp.Circle)
                    ArrayCell[IndexPreviousCellRow][IndexPreviousCellCol].setSharp(Cell.Sharp
                            .CircleRight);
            }
        }
    }

    private void clearCellDrawen() {
        System.out.println("Size :" + cell_used.size());
        for (Cell item : cell_used) {
            int IndexCol = item.getIndexCol();
            int IndexRow = item.getIndexRow();
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
//        clearTableLayout();
        BuildTable(size);
    }

    private void drawTube(int IndexRow, int IndexCol, int color_chosen, boolean active, Cell
            .Sharp sharp) {
        if (active_draw && !ArrayCell[IndexRow][IndexCol].isUsed()) {
            if (sharp == Cell.Sharp.UpDown && (IndexRow != 0 && IndexRow != size - 1)) {
                ArrayCell[IndexRow][IndexCol].setSharp(sharp);
                ArrayCell[IndexRow][IndexCol].setColor(color_chosen);
//                clearTableLayout();
                BuildTable(size);
            } else if (sharp == Cell.Sharp.LeftRight && (IndexCol != 0 && IndexCol != size - 1)) {
                ArrayCell[IndexRow][IndexCol].setSharp(sharp);
                ArrayCell[IndexRow][IndexCol].setColor(color_chosen);
//                clearTableLayout();
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
            for (int col = 0; col < size; col++) {
                row.addView(ArrayCell[i][col]);
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
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Tables de size == 7
                if (size == 7) {
                    // Level 1
                    if (level == 1) {
                        if ((row == 1 && col == 0) || (row == 6 && col == 0)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.RoyalBlue), row,
                                    col, true);
                        } else if ((row == 2 && col == 2) || (row == 3 && col == 4)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Red), row, col,
                                    true);
                        } else if ((row == 4 && col == 2) || (row == 5 && col == 4)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Yellow), row,
                                    col, true);
                        } else if ((row == 4 && col == 4) || (row == 5 && col == 1)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Orange), row,
                                    col, true);
                        } else if ((row == 5 && col == 0) || (row == 5 && col == 5)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.LimeGreen), row,
                                    col, true);
                        } else {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, Color.WHITE, row, col, false);
                        }
                    }
                    // Level 2
                    if (level == 2) {
                        if ((row == 5 && col == 0) || (row == 3 && col == 6)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.RoyalBlue), row,
                                    col, true);
                        } else if ((row == 4 && col == 6) || (row == 6 && col == 6)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Red), row, col,
                                    true);
                        } else if ((row == 5 && col == 1) || (row == 2 && col == 6)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Yellow), row,
                                    col, true);
                        } else if ((row == 5 && col == 3) || (row == 2 && col == 5)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Orange), row,
                                    col, true);
                        } else if ((row == 4 && col == 5) || (row == 6 && col == 5)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.LimeGreen), row,
                                    col, true);
                        } else if ((row == 1 && col == 1) || (row == 5 && col == 2)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleTurquoise),
                                    row, col, true);
                        } else if ((row == 2 && col == 2) || (row == 1 && col == 5)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Gray), row, col,
                                    true);
                        } else {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, Color.WHITE, row, col, false);
                        }
                    }
                    // Level 3
                    if (level == 3) {
                        if ((row == 5 && col == 0) || (row == 4 && col == 3)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.RoyalBlue), row,
                                    col, true);
                        } else if ((row == 5 && col == 3) || (row == 6 && col == 6)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Red), row, col,
                                    true);
                        } else if ((row == 2 && col == 2) || (row == 2 && col == 4)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Yellow), row,
                                    col, true);
                        } else if ((row == 5 && col == 1) || (row == 5 && col == 4)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Orange), row,
                                    col, true);
                        } else if ((row == 3 && col == 1) || (row == 4 && col == 4)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.LimeGreen), row,
                                    col, true);
                        } else if ((row == 2 && col == 1) || (row == 4 && col == 5)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleTurquoise),
                                    row, col, true);
                        } else {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, Color.WHITE, row, col, false);
                        }
                    }
                }


                // Tables de size == 8
                if (size == 8) {
                    // Level 1
                    if (level == 1) {
                        if ((row == 1 && col == 5) || (row == 1 && col == 7)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.RoyalBlue), row,
                                    col, true);
                        } else if ((row == 0 && col == 4) || (row == 5 && col == 4)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Red), row, col,
                                    true);
                        } else if ((row == 3 && col == 0) || (row == 3 && col == 6)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Yellow), row,
                                    col, true);
                        } else if ((row == 4 && col == 3) || (row == 5 && col == 2)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Orange), row,
                                    col, true);
                        } else if ((row == 1 && col == 0) || (row == 2 && col == 2)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.LimeGreen), row,
                                    col, true);
                        } else if ((row == 0 && col == 0) || (row == 2 && col == 0)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Maroon), row,
                                    col, true);
                        } else if ((row == 2 && col == 7) || (row == 7 && col == 7)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleTurquoise),
                                    row, col, true);
                        } else if ((row == 1 && col == 6) || (row == 2 && col == 5)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Gray), row, col,
                                    true);
                        } else if ((row == 3 && col == 5) || (row == 4 && col == 2)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleGreen), row,
                                    col, true);
                        } else {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, Color.WHITE, row, col, false);
                        }
                    }
                    // Level 2
                    if (level == 2) {
                        if ((row == 6 && col == 2) || (row == 5 && col == 5)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.RoyalBlue), row,
                                    col, true);
                        } else if ((row == 6 && col == 1) || (row == 4 && col == 3)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Red), row, col,
                                    true);
                        } else if ((row == 0 && col == 5) || (row == 3 && col == 5)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Yellow), row,
                                    col, true);
                        } else if ((row == 1 && col == 4) || (row == 6 && col == 3)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Orange), row,
                                    col, true);
                        } else if ((row == 1 && col == 6) || (row == 3 && col == 6)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.LimeGreen), row,
                                    col, true);
                        } else if ((row == 0 && col == 4) || (row == 0 && col == 6)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleTurquoise),
                                    row, col, true);
                        } else if ((row == 2 && col == 2) || (row == 4 && col == 2)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleGreen), row,
                                    col, true);
                        } else {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, Color.WHITE, row, col, false);
                        }
                    }
                    // Level 3
                    if (level == 3) {
                        if ((row == 1 && col == 1) || (row == 6 && col == 2)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.RoyalBlue), row,
                                    col, true);
                        } else if ((row == 5 && col == 2) || (row == 4 && col == 4)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Red), row, col,
                                    true);
                        } else if ((row == 1 && col == 5) || (row == 5 && col == 3)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Yellow), row,
                                    col, true);
                        } else if ((row == 1 && col == 3) || (row == 3 && col == 4)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Orange), row,
                                    col, true);
                        } else if ((row == 3 && col == 0) || (row == 0 && col == 3)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.LimeGreen), row,
                                    col, true);
                        } else if ((row == 1 && col == 2) || (row == 3 && col == 3)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.Maroon), row,
                                    col, true);
                        } else if ((row == 4 && col == 0) || (row == 1 && col == 4)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleTurquoise),
                                    row, col, true);
                        } else if ((row == 2 && col == 5) || (row == 5 && col == 4)) {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, ContextCompat.getColor(this, R.color.PaleGreen), row,
                                    col, true);
                        } else {
                            ArrayCell[row][col] = new Cell(this, Cell.Sharp.Circle, Cell.CellType
                                    .None, Color.WHITE, row, col, false);
                        }
                    }
                }
            }

        }
    }

}