public class Square {

    public boolean isBomb;
    public boolean open;
    public int numberOfBombs;
    public boolean flagged;

    public Square() {
        this.isBomb = false;
        this.open = false;
        this.flagged = false;
    }

    public String print() {
        if (flagged) {
            return "◇";
        }
        if (!open) {
            return "·";
        }
        if (!isBomb) {
            if (numberOfBombs == 0) {
                return " ";
            }
            return String.valueOf(numberOfBombs);
        }
        return "×";
    }

}
