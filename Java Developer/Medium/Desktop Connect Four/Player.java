package four;

public class Player {
    //region Attributes
    private final char LETTER; // 'X' or 'O'
    //endregion

    //region Constructor
    protected Player(char letter) {
        this.LETTER = letter;
    }
    //endregion

    //region Getter
    public char getLetter() {return LETTER;}
    //endregion
}
