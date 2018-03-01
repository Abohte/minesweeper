import java.util.Random;

import static java.lang.Math.sqrt;

public class Board {

    private Square[][] BoardSquares;

    private int numberOfOpenSquares;

    public Board(int size) {
        this(size, size);
    }

    public Board(int width, int length) {
        this.numberOfOpenSquares = 0;
        this.BoardSquares = new Square[width][length];
        for (int y = 0 ; y < length ; y++) {
            for (int x = 0 ; x < width ; x++) {
                BoardSquares[x][y] = new Square();
            }
        }
        // bombs
        Random bombs = new Random();
        for (int i = 0 ; i < (int) sqrt(width * (length)) ; i++) {
            int x = bombs.nextInt(width);
            int y = bombs.nextInt(length);
            while (BoardSquares[x][y].isBomb) {
                x = bombs.nextInt(width);
                y = bombs.nextInt(length);
            }
            BoardSquares[x][y].isBomb = true;
        }
    }

    public int width() {
        return BoardSquares.length;
    }

    public int length() {
        return BoardSquares[0].length;
    }

    public boolean process(int x, int y) throws SquareException {
        if (BoardSquares[x][y].open) {
            throw new SquareOpenedException("Square (" + x + 1 + "," + y + 1  + ") has already been opened.");
        }
        if (BoardSquares[x][y].flagged) {
            throw new SquareFlaggedException("Square (" + x + 1 + "," + y + 1 + ") has already been flagged.");
        }
        BoardSquares[x][y].open = true;
        if (BoardSquares[x][y].isBomb) {
            openAllSquares();
            return false;
        } else {
            this.numberOfOpenSquares++;
            int numberOfBombs = numberOfSurroundingBombs(x, y);
            BoardSquares[x][y].numberOfBombs = numberOfBombs;
            if (numberOfBombs == 0) {
                openSurroundingSquares(x, y);
            }
            return true;
        }

    }

    public int numberOfSurroundingBombs(int x, int y) {
        int numberOfBombs = 0;
        for (int u = x - 1 ; u <= x + 1 ; u++) {
            for (int v = y - 1; v <= y + 1; v++) {
                if (!(0 <= u && u < width() && 0 <= v && v < length())) {
                    continue;
                }
                if (BoardSquares[u][v].isBomb) {
                		numberOfBombs += 1;
                }
            }
        }
        return numberOfBombs;
    }

    public void openSurroundingSquares(int x, int y) {
        for (int u = x - 1 ; u <= x + 1 ; u++) {
            for (int v = y -1 ; v <= y +1 ; v++) {
                if (!(0 <= u && u < width() && 0 <= v && v < length())) {
                    continue;
                }
                try {
                    process(u,v);
                } catch(Exception vakjeAlOpen) {
                    // vakje al open
                }
            }
        }
    }

    public void openAllSquares() {
        for (int y = 0 ; y < length() ; y++) {
            for (int x = 0; x < width(); x++) {
                BoardSquares[x][y].open = true;
                BoardSquares[x][y].numberOfBombs = numberOfSurroundingBombs(x, y);
            }
        }
    }

    public void flag(int x, int y) throws SquareOpenedException {
        if (BoardSquares[x][y].open) {
        		String messageSquareOpened = "You cannot flag square (" + x + 1 + "," + y + 1 + ").\nThis square has already been opened.";
            throw new SquareOpenedException(messageSquareOpened);
        }
        BoardSquares[x][y].flagged = !BoardSquares[x][y].flagged;
    }

    public void showBoard() {
        System.out.println();
        for (int y = 0 ; y < length() ; y++) {
            for (int x = 0 ; x < width() ; x++) {
                if(x % width() != width() -1){
                    System.out.print(BoardSquares[x][y].print() + "|");
                }  else {
                    System.out.print(BoardSquares[x][y].print() + "\n");
                }
            }
        }
        System.out.println();
    }

    public boolean isDone() {
        if (width() * length() - numberOfOpenSquares == (int) sqrt(width() * (length()))) {
            return true;
        } else {
            return false;
        }
    }
}
