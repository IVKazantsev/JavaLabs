import Figures.Bishop;
import Figures.Figure;
import Figures.King;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;

import java.util.ArrayList;

public class Board {
    private final Figure[][] fields = new Figure[8][8];
    private final int[][] kings = new int[2][2];
    public boolean[] isKingChecked = new boolean[2];
    private final ArrayList<String> takeWhite = new ArrayList<>(16);
    private final ArrayList<String> takeBlack = new ArrayList<>(16);

    public char getColorGaming() {
        return colorGaming;
    }

    public void setColorGaming(char colorGaming) {
        this.colorGaming = colorGaming;
    }

    private char colorGaming;

    public void init() {
        this.fields[0] = new Figure[]{
                new Rook("R", 'w'), new Knight("N", 'w'),
                new Bishop("B", 'w'), new Queen("Q", 'w'),
                new King("K", 'w'), new Bishop("B", 'w'),
                new Knight("N", 'w'), new Rook("R", 'w'),
        };
        this.kings[0] = new int[]{0, 4}; // White King

        this.fields[1] = new Figure[]{
                new Pawn("P", 'w'), new Pawn("P", 'w'),
                new Pawn("P", 'w'), new Pawn("P", 'w'),
                new Pawn("P", 'w'), new Pawn("P", 'w'),
                new Pawn("P", 'w'), new Pawn("P", 'w'),
        };

        this.fields[7] = new Figure[]{
                new Rook("R", 'b'), new Knight("N", 'b'),
                new Bishop("B", 'b'), new Queen("Q", 'b'),
                new King("K", 'b'), new Bishop("B", 'b'),
                new Knight("N", 'b'), new Rook("R", 'b'),
        };
        this.kings[1] = new int[]{7, 4}; // Black King
        this.fields[6] = new Figure[]{
                new Pawn("P", 'b'), new Pawn("P", 'b'),
                new Pawn("P", 'b'), new Pawn("P", 'b'),
                new Pawn("P", 'b'), new Pawn("P", 'b'),
                new Pawn("P", 'b'), new Pawn("P", 'b'),
        };
    }

    public String getCell(int row, int col) {
        Figure figure = this.fields[row][col];
        if (figure == null) {
            return "    ";
        }
        return " " + figure.getColor() + figure.getName() + " ";
    }

    public ArrayList<String> getTakeWhite() {
        return takeWhite;
    }

    public ArrayList<String> getTakeBlack() {
        return takeBlack;
    }

    public boolean isPathEmpty(int row1, int col1, int row2, int col2) { // Обработка препятствий на пути
        if (this.fields[row1][col1] instanceof Knight) {
            return true;
        }

        int temp; // Сортируем ход по возрастанию, чтобы корректно работали следующие циклы

        if (row1 > row2) {
            temp = row1;
            row1 = row2;
            row2 = temp;

            temp = col1;
            col1 = col2;
            col2 = temp;
        }
        if (row1 == row2) {
            if (col1 < col2) {
                for (int i = col1 + 1; i < col2; i++) {
                    if (this.fields[row1][i] != null) {
                        return false;
                    }
                }
            } else {
                for (int i = col1 - 1; i > col2; i--) {
                    if (this.fields[row1][i] != null) {
                        return false;
                    }
                }
            }
        } else if (col1 == col2) {
            for (int i = row1 + 1; i < row2; i++) {
                if (this.fields[i][col1] != null) {
                    return false;
                }
            }
        } else {
            for (int i = row1 + 1; i < row2; i++) {
                if (col1 < col2) {
                    for (int j = col1 + 1; j < col2; j++) {
                        if (this.fields[i][j] != null) {
                            return false;
                        }
                        i++;
                    }
                } else {
                    for (int j = col1 - 1; j > col2; j--) {
                        if (this.fields[i][j] != null) {
                            return false;
                        }
                        i++;
                    }
                }
            }
        }
        return true;
    }

    public boolean isCheck() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.fields[i][j] == null) {
                    continue;
                }
                if (this.fields[i][j].getColor() == 'w') {
                    if (this.fields[i][j].canAttack(i, j, this.kings[1][0], this.kings[1][1]) &&
                            isPathEmpty(i, j, this.kings[1][0], this.kings[1][1])) {
                        isKingChecked[1] = true;
                        return true;
                    }
                } else {
                    if (this.fields[i][j].canAttack(i, j, this.kings[0][0], this.kings[0][1]) &&
                            isPathEmpty(i, j, this.kings[0][0], this.kings[0][1])) {
                        isKingChecked[0] = true;
                        return true;
                    }
                }

            }
        }
        isKingChecked[0] = false;
        isKingChecked[1] = false;
        return false;
    }

    public void kingMoving(int row1, int col1) {
        if (this.fields[row1][col1] instanceof King) {
            if (this.fields[row1][col1].getColor() == 'w') {
                this.kings[0][0] = row1;
                this.kings[0][1] = col1;
            } else {
                this.kings[1][0] = row1;
                this.kings[1][1] = col1;
            }
        }
    }

    public boolean isThereAnyMove(char color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.fields[i][j] == null) {
                    continue;
                }
                for (int k = 0; k < 8; k++) {
                    for (int t = 0; t < 8; t++) {
                        if (this.fields[i][j].canMove(i, j, k, t)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean move_figure(int row1, int col1, int row2, int col2) {

        Figure figure = this.fields[row1][col1];

        if (colorGaming != figure.getColor()) { // Обработка попытки хода одной стороны во время хода другой
            System.out.println("Сейчас ходит другая сторона!");
            return false;
        }

        if (figure.canMove(row1, col1, row2, col2) && this.fields[row2][col2] == null) {
            if (!isPathEmpty(row1, col1, row2, col2)) { // Обработка препятствий
                System.out.println("Вы не можете ходить сквозь другие фигуры!");
                return false;
            }

            System.out.println("move");
            this.fields[row2][col2] = figure;
            this.fields[row1][col1] = null;

            kingMoving(row2, col2);

            if (isCheck()) {
                System.out.println("Шах!");
            }

            return true;
        } else if (figure.canAttack(row1, col1, row2, col2) && this.fields[row2][col2] != null && this.fields[row2][col2].getColor() != this.fields[row1][col1].getColor()) {
            if (!isPathEmpty(row1, col1, row2, col2)) { // Обработка препятствий
                System.out.println("Вы не можете ходить сквозь другие фигуры!");
                return false;
            }
            if (this.fields[row2][col2] instanceof King) { // Обработка случая нападения на короля
                System.out.println("Нельзя атаковать короля!");
                return false;
            }

            System.out.println("attack");
            switch (this.fields[row2][col2].getColor()) {
                case 'w':
                    this.takeWhite.add(this.fields[row2][col2].getColor() + this.fields[row2][col2].getName());
                    break;
                case 'b':
                    this.takeBlack.add(this.fields[row2][col2].getColor() + this.fields[row2][col2].getName());
                    break;
            }
            this.fields[row2][col2] = figure;
            this.fields[row1][col1] = null;

            kingMoving(row2, col2);

            if (isCheck()) {
                System.out.println("Шах!");
            }
            return true;
        }
        return false;
    }

    public void print_board() {
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for (int row = 7; row > -1; row--) {
            System.out.print(row);
            for (int col = 0; col < 8; col++) {
                System.out.print("|" + getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for (int col = 0; col < 8; col++) {
            System.out.print("    " + col);
        }
    }
}
