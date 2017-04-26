// CS_205 Go Fish
/**
 * Kevin Gottfried
 * Card class implements relatable
 * Demonstrates creating a card and methods used to relate cards.
 */
import java.util.*;

public class Card {

    private char letter;

    /**
     * Constructor Creates a new card from a random integer. Assigns a letter to the card.
     */
    public Card() {
        Random num = new Random();
        letter = (char) (num.nextInt(25) + 65);
    }

    /**
     * Constructor Creates a new card from a previously existing card Assigns
     * the letter from previous letter.
     *
     * @param letter
     */
    public Card(char letter) {
        this.letter = letter;
    }

    /**
     * Returns the numeric letter of the card
     *
     * @return letter The numeric letter of the card.
     */
    public int getValue() {
        return letter;
    }

    /**
     * getValueAsString This method returns the string letter of the cards letter
     *
     * @return String letter of letter
     */
    public String getValueAsString() {
        switch (letter) {
            case 'A':
                return "A";
            case 'B':
                return "B";
            case 'C':
                return "C";
            case 'D':
                return "D";
            case 'E':
                return "E";
            case 'F':
                return "F";
            case 'G':
                return "G";
            case 'H':
                return "H";
            case 'I':
                return "I";
            case 'J':
                return "J";
            case 'K':
                return "K";
            case 'L':
                return "L";
            case 'M':
                return "M";
            case 'N':
                return "N";
            case 'O':
                return "O";
            case 'P':
                return "P";
            case 'Q':
                return "Q";
            case 'R':
                return "R";
            case 'S':
                return "S";
            case 'T':
                return "T";
            case 'U':
                return "U";
            case 'V':
                return "V";
            case 'W':
                return "W";
            case 'X':
                return "X";
            default:
                return "??"; // null
        }
    }

    public boolean equals(Object cardOther) {

        return letter == ((Card) cardOther).getValue();
    }

    //test the card creating functionality
    public static void main(String [] args){
        Card h = new Card();
    }

}	

