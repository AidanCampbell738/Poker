//Programmer: Aidan Campbell
//Description: stores deck information
//Date Created: 12/18/2017
//Date Revised: 01/21/2018

import java.util.*;

public class Deck

{

   ArrayList<Integer> cardPile;//the deck of cards itself
   int cardsLeft = 52;//cards left in the deck
   
   //Constructor
   //pre: none
   //post: creates a deck where the cards are in order
   public Deck()
   
   {
   
      cardPile = new ArrayList<Integer>();
      
      for(int i = 0; i < 52; i++)
      {
         cardPile.add(i);
      }
      
   }
   
   //shuffles deck
   //pre: none
   //post: deck is no longer in order
   public void shuffle()
   
   {
   
      Collections.shuffle(cardPile);
      
   }
   
   //clears deck and refills it in order
   //pre: none
   //post: deck is full and in order
   public void refill()
   
   {
   
      cardPile.clear();
      cardsLeft = 52;
      
      for(int i = 0; i < 52; i++)
      {
         cardPile.add(i);
      }
      
   }
   
   //removes a card from the top of the pile
   //pre: none
   //post: removed card is returned
   public int drawCard()
   
   {
      int card = cardPile.get(cardsLeft - 1);
      cardPile.remove(cardsLeft - 1);
      cardsLeft -= 1;
      return(card);
      
   }
   
}
      
      
      
      
      
      
   
   