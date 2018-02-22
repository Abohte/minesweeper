import java.util.Scanner;

public class Main {

    public static int getUserInputX(int width) {
        Scanner player = new Scanner(System.in);
        System.out.print("Choose x (1-" + width +"): " );
        return player.nextInt(); // Scans the next token of the input as an int.
    }

    public static int getUserInputY(int length) {
        Scanner player = new Scanner(System.in);  // Reading from System.in
        System.out.print("Choose y (1-" + length + "): ");
        return player.nextInt(); // Scans the next token of the input as an int.
    }

    public static char getUserInputOpenOrFlag(int x, int y) {
        Scanner player = new Scanner(System.in);  // Reading from System.in
        System.out.print("Would you like to open (o) or flag (f) square (" + x + "," + y + ")?");
        return player.next().charAt(0);
    }

    public static char getUserInputOpenFlagged(int x, int y) {
        Scanner player = new Scanner(System.in);  // Reading from System.in
        System.out.print("Square (" + x + "," + y + ") is flagged. Would you like to open this square (y/n)?");
        return player.next().charAt(0);
    }

    public static void main(String[] args) {
        Board newBoard = new Board(8);

        boolean noBomb;

        do {
            noBomb = true;

            newBoard.showBoard();
            System.out.println("Choose a square to open/flag.");
            int inputX = getUserInputX(newBoard.width());
            int squareX = inputX - 1;
            int inputY = getUserInputY(newBoard.length());
            int squareY = inputY -1;
            char inputOpenOrFlag = getUserInputOpenOrFlag(inputX, inputY);

            try {
                switch (inputOpenOrFlag) {
                    case 'f' :
                        newBoard.flag(squareX, squareY);
                        System.out.print("\nSquare (" + inputX + "," + inputY + ") has been flagged.\n\n");
                        break;
                    case 'o' :
                        noBomb = newBoard.verwerk(squareX, squareY);
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.print("\nSquare (" + inputX + "," + inputY + ") does not exist. Please choose a valid square.\n\n");
            } catch (SquareOpenedException squareOpen) {
                System.out.print("\n" + squareOpen.getMessage() + "\n\n");
            } catch (SquareFlaggedException squareFlagged) {
                System.out.print("\n" + squareFlagged.getMessage() + "\n\n");
                char inputO = getUserInputOpenFlagged(inputX, inputY);
                if (inputO == 'j') {
                    try {
                        newBoard.flag(squareX, squareY);
                        noBomb = newBoard.verwerk(squareX, squareY);
                    } catch(SquareException notPossible) {

                    }
                }
            } catch (SquareException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } while(noBomb && !newBoard.isDone());
        newBoard.showBoard();
        if (!noBomb) {
            System.out.println("You have lost.");
        } else {
            System.out.println("You have won!");
        }
    }
}
