/**
   Kevin Gottfried
   Deck class creates a new potDeck that is used to create to half-decks.
   *This deck consists of 24 treasure cards to be dealt to each player.
*/
import java.util.Random;
public class Deck 
{
   /** 
   *  Number of cards in standard deck {@value #CARDS_IN_DECK}
   **/
   public final static int CARDS_IN_DECK = 24;

   private int top;
   private int deckSize;
   /** The collection of Cards */
   private Card [] deck;
   /** Current number of Cards in Deck */
   private int ct;
   
   /**
    * Constructs a regular 24-card deck.  Initially, the cards
    * are in a sorted order.  The shuffle() method can be called to
    * randomize the order.  
    */
   public Deck()
   {
      freshDeck();
   }
   /**
    * Create a new collection of 24 cards, in sorted order
    */
   public void freshDeck()
   {
      deck = new Card[CARDS_IN_DECK];
      top = 0;
	        int d = 0;
	        deckSize = 24;
	            for(int i=0; i<=23; i++)
	            {
	               deck[ct] = new Card((char) (i + 65));
                  ct = ct +1;
	            }
   }
   /** 
     * Remove and return the top Card on the Deck
     * @return A reference to a Card that was top on the Deck
     */
   public Card dealCard()
   {
      ct--;
      return deck[ct];
   }
   /** 
     * Return current number of Cards in Deck
     * @return number of Cards in Deck
     */
   public int cardsRemaining()
   {  
      return ct;
   }
   /** 
     * Randomize the order of Cards in Deck
     */
   public void shuffle()
   {
      int randNum;
      Card temp;
      Random r = new Random();
      for (int i = 0; i < ct; i++)
      {
         randNum = r.nextInt(ct);
         temp = deck[i];
         deck[i]=deck[randNum];
         deck[randNum]=temp;
      }
   }
   /** 
     * Determine if Deck is empty
     * @return true if there are no more cards, false otherwise
     */
   public boolean isEmpty()
   {
      return (cardsRemaining() == 0);
   }
   // test an implementation of shuffling and dealing a deck
    public static void main(String [] args){
        Deck h = new Deck();
        h.shuffle();
        while(!h.isEmpty()){
           Card cur = h.dealCard();
        }
    }
}

