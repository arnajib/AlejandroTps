package com.inf8405.tp1.flowfree;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;


import java.util.ArrayList;


public class Game extends Activity {

    TableLayout table_game;
    // TODO: remplacer size et level a partir de level Activity
    int size = 7;
    int level = 1;
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
    AlertDialog.Builder alert_exit;
    AlertDialog.Builder alert_game_over;

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
        alert.setTitle("Bravo vous avez gagné");

        if (level == 3 && size == 8) alert.setMessage("Vous avez réussi tous les niveaux");

        alert.setMessage("Passez au niveau suivant");
        alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                goNextLevel();
            }
        });
        alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                resetGame();
            }
        });

        // Afficher message pour quitter le jeu

        alert_exit = new AlertDialog.Builder(Game.this);
        alert_exit.setTitle("Exit Game");
        alert_exit.setMessage("Voulez vous quitter le jeu");
        alert_exit.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();// Sortir de l'application et revenir a la fenetre pour choisir nouveau
                // levels
            }
        });
        alert_exit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return; // Rien faire et continuer a jouer
            }
        });

        // Afficher message pour partie perdue

        alert_game_over = new AlertDialog.Builder(Game.this);
        alert_game_over.setTitle("Game over");
        alert_game_over.setMessage("Voulez vous rejouer");
        alert_game_over.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                resetGame();

            }
        });
        alert_game_over.setNegativeButton("Quitter le jeu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();// Sortir de l'application et revenir a la fenetre pour choisir nouveau
                // levels
            }
        });


        // Boutons pour initialiser et quitter le jeu
        Button button_reset = (Button) findViewById(R.id.button_reset);
        button_reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetGame();
            }
        });

        Button button_exit = (Button) findViewById(R.id.button_exit);
        button_exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alert_exit.show();
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

                // Verifier que les IndexRow et IndexCol ne depasseront pas les limites
                IndexRow = IndexRow > (size - 1) ? (size - 1) : IndexRow;
                IndexCol = IndexCol > (size - 1) ? (size - 1) : IndexCol;

                // Une fois une cellule est touchee c est l evenement ActionDown qui est capte
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
                            addCellUsed(ArrayCell[IndexRow][IndexCol], true);
                            previous_cell = cell_used.get(cell_used.size() - 1);
                            current_cell = cell_used.get(cell_used.size() - 1);
                        }
                    }

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (ArrayCell[IndexRow][IndexCol].getColor() != color_chosen ||
                            !ArrayCell[IndexRow][IndexCol].isUsed()) {
                        clearCellDrawen();
                    } else {
                        if (!isConnected(ArrayCell[IndexRow][IndexCol])) {
                            ArrayCell[IndexRow][IndexCol].setType(Cell.CellType.Second);
                            redesignSecondCircle(IndexCurrentCellRow, IndexCurrentCellCol);
                            // Mettre a jour le nombre de tubes
                            if (isTubeValide()) {
                                ++game_score;
                            } else {
                                clearCellDrawen();
                            }

                        }
                        cell_used.clear();

                        setScore(game_score);
                    }
                    // verifier si la partie est gagnee
                    if (isSuccessfulParty()) {
                        alert.show();
                    }

                    // verifier si la partie est inachevee
                    if (isGameOver()) {
                        alert_game_over.show();
                    }


                }
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() ==
                        MotionEvent.ACTION_MOVE) {

                    if (cell_used.size() > 0 && (ArrayCell[IndexRow][IndexCol].getColor() ==
                            Color.WHITE || ArrayCell[IndexRow][IndexCol].isUsed())) {
                        if (!addCellUsed(ArrayCell[IndexRow][IndexCol], false)) {
                            active_draw = false;
                        }
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
                    return true;
                }
                return false;
            }
        });

    }

    // Dès qu'une cellule est traversée par deux couleurs, cette fonction renvoie True, cela va
    // permettera de définir si la partie est réussie ou non
    private boolean isOver() {

        for (int i = 0; i < cell_used.size() - 1; i++) {
            if ((cell_used.get(i).getIndexRow() - cell_used.get(i + 1).getIndexRow() > 1) ||
                    (cell_used.get(i).getIndexCol() - cell_used.get(i + 1).getIndexCol() > 1)) {
                over = true;
            }
        }

        return over;
    }

    // Fonction qui vérifie si la partie a été gagnée
    private boolean isSuccessfulParty() {
        boolean success = true;

        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                if (ArrayCell[row][col].getColor() == Color.WHITE) success = false;
            }
        }

        return (success && !isOver());
    }

    // Fonction qui vérifie si la partie a été perdue
    private boolean isGameOver() {
        boolean game_over = false;
        int number_first_circle = 0;
        int number_seconde_circle = 0;
        int number_used_circle = 0;
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                if (ArrayCell[row][col].isUsed()) {
                    ++number_used_circle;
                    if (ArrayCell[row][col].getType() == Cell.CellType.First) {
                        ++number_first_circle;
                    } else if (ArrayCell[row][col].getType() == Cell.CellType.Second) {
                        ++number_seconde_circle;
                    }
                }
            }
        }

        if ((number_first_circle == number_seconde_circle) && (number_first_circle ==
                number_used_circle / 2))
            game_over = true;
        return game_over && !isSuccessfulParty();
    }

    // Fonction qui vérifie si deux cercles de même couleurs sont reliées
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

    // Ajoute les cellules qui sont en train d'être dessiner
    private boolean addCellUsed(Cell cell_to_add, boolean isFirst) {
        boolean exist = false;
        if (!isConnected(cell_to_add)) {
            for (Cell item : cell_used) {
                if (item.getIndexRow() == cell_to_add.getIndexRow() && item.getIndexCol() ==
                        cell_to_add.getIndexCol()) {
                    exist = true;
                }
            }
            if (!exist) {
                cell_used.add(cell_to_add);
            }
            return true;
        } else {
            return false;
        }
    }

    // Vérifier si le cercle est un cercle finale
    private boolean isSecondCircle(int IndexRow, int IndexCol) {
        boolean secondeCircle = false;
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                if (ArrayCell[row][col].getColor() == ArrayCell[IndexRow][IndexCol].getColor() &&
                        ArrayCell[row][col].getType() == Cell.CellType.First) {
                    secondeCircle = true;
                }
            }
        }

        return secondeCircle;
    }

    // redissine les cercles selon la direction
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

    // tracer les coins
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
        BuildTable(size);
    }

    // tracer les tubes
    private void drawTube(int IndexRow, int IndexCol, int color_chosen, boolean active, Cell
            .Sharp sharp) {
        if (active_draw) {
            if (!ArrayCell[IndexRow][IndexCol].isUsed()) {
                if (sharp == Cell.Sharp.UpDown && (IndexRow != 0 && IndexRow != size - 1)) {
                    ArrayCell[IndexRow][IndexCol].setSharp(sharp);
                    ArrayCell[IndexRow][IndexCol].setColor(color_chosen);
                    BuildTable(size);
                } else if (sharp == Cell.Sharp.LeftRight && (IndexCol != 0 && IndexCol != size -
                        1)) {

                    ArrayCell[IndexRow][IndexCol].setSharp(sharp);
                    ArrayCell[IndexRow][IndexCol].setColor(color_chosen);
                    BuildTable(size);
                }
            } else if (isSecondCircle(IndexRow, IndexCol)) {
                redesignSecondCircle(IndexRow, IndexCol);
            }

        }
    }

    // verifier si le tube est valide
    private boolean isTubeValide() {
        if (cell_used.size() > 0) {
            Cell cell_first = cell_used.get(0);
            for (Cell item : cell_used) {
                if (item.getColor() != cell_first.getColor() && item.isUsed()) {
                    return false;
                }

            }
        }

        return true;
    }

    // met à jour le score
    private void setScore(int score) {
        game_score = score;
        TextView text_score = (TextView) findViewById(R.id.score_id);
        text_score.setText(" " + game_score);
    }

    // met à jour le level
    private void setLevel(int lev) {
        level = lev;
        TextView text_level = (TextView) findViewById(R.id.level_id);
        text_level.setText(" " + level);
    }

    // met à jour la taille
    private void setSize(int siz) {
        size = siz;
        TextView text_size = (TextView) findViewById(R.id.size_id);
        text_size.setText(" " + size);
    }

    // créer la table avec les bonnes cercles
    private void BuildTable(int size) {
        setLevel(level);
        setSize(size);
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

    // effacer les éléments de la table
    private void clearTableLayout() {
        int count = table_game.getChildCount();
        for (int i = 0; i < size; i++) {
            View child = table_game.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
        table_game.removeAllViewsInLayout();
    }

    // Incrémenter le level
    private void goNextLevel() {
        if (size == 7) {
            if (level < 3) {
                ++level;
            } else {
                ++size;
                level = 1;
            }
        } else if (size == 8) {
            if (level < 3) {
                ++level;
            }
        }
        InitArrayCell(size, level);
        BuildTable(size);
        game_score = 0;
        setScore(game_score);
    }

    // Initialiser le jeu
    private void resetGame() {
        InitArrayCell(size, level);
        BuildTable(size);
        game_score = 0;
        setScore(game_score);
    }

    // Initialiser le tableau avec les bonnes cercles selon le niveau
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