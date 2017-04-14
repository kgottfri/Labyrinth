// CS_205 Go Fish
/**
 * Kevin Gottfried
 * Card class implements relatable
 * Demonstrates creating a card and methods used to relate cards.
 */
import java.util.*;

public class Card {
    // intializes two variables for the card
//	private int suit;

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
     * the suit and letter from previous suit and letter.
     *
     * @param letter
     */
    public Card(char letter) {
        this.letter = letter;
    }
//   public Card(Card c1)
//   {
////      suit = c1.getSuit();
//      letter = c1.getValue();
//   }


    /**
     * Returns the numeric letter of the card
     *
     * @return letter The numeric letter of the card.
     */
    public int getValue() {
        return letter;
    }

    /**
     * getSuit This method returns the numeric letter of the suit
     *
     * @return suit The suit of the card.
     */
//	public int getSuit()
//	{
//		return suit;
//	}
    /**
     * getSuitAsString This method returns the string letter of the suit of a
     * card
     *
     * @return String letter of suit
     */
//   public String getSuitAsString()
//   {
//   	// Return a String representing the card's suit.
//   	// (If the card's suit is invalid, "??" is returned.)
//   	switch ( suit ) 
//      {
//   	   case 3: return "s"; // spades
//   	   case 2: return "h"; // hearts
//   	   case 1: return "d"; // diamonds
//   	   case 0: return "c"; // clubs
//   	   default: return "??"; // null
//      }
//   }
//   
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

    /**
     * toString Overides the toString method. Return a String representation of
     * this card, such as "10 of Hearts" or "Queen of Spades".
     */
//    public String toString() {
//        return letter.toString();
//    }

    /**
     * GreaterThan This method determines if a card is greater than a 2nd card
     * by comparing the letters.
     *
     * @param cardOther The second card to be compared.
     * @return true if card 1 greater than card 2
     */
    public boolean GreaterThan(Card cardOther) {
        return letter > cardOther.getValue();
    }

    /**
     * equals This method determines if two cards are equal by comparing their
     * letters.
     *
     * @return true if letters are equal.
     */
    public boolean equals(Object cardOther) {

        return letter == ((Card) cardOther).getValue();
    }

    public int compare(Card c1, Card c2) {
        if (c1.getValue() < c2.getValue()) {
            return 1;
        } else {
            return -1;
        }
    }
    
    public void Print(){
        System.out.println(letter);
    }
    
    //test the card creating functionality
    public static void main(String [] args){
        Card h = new Card();
        h.Print();
    }

}	

