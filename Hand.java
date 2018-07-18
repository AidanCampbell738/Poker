//Programmer: Aidan Campbell
//Description: stores each players hand info. Has methods to get hand values and types.
//Date Created: 12/18/2017
//Date Revised: 01/21/2018

import java.util.*;

public class Hand

{

   int[] holes;//store 2 hole cards, available to one player
   static int[] tableCards = new int[5];//available to all players
   int[] winningHand = new int[5];//best 5 cards make up winning hand. Only these cards are considered in determining hand vale
   
   //Constructor
   //pre: none
   //post: holes have been initialized to default values
   public Hand()
   
   {
   
      holes = new int[2];
      
   }
   
   //sets values of hole cards
   //pre: none
   //post: hole cards have been set to parameters
   public void setHoles(int a, int b)
   
   {
   
      holes[0] = a;
      holes[1] = b;
      
   }
   
   //returns first hole card
   //pre: hole cards have been dealt
   //post: first hole card is returned
   public int getFirstHole()
   
   {
   
      return(holes[0]);
      
   }
   
   //returns second hole card
   //pre: hole cards have been dealt
   //post: second hole card is returned
   public int getSecondHole()
   
   {
   
      return(holes[1]);
      
   }
   
   //sets all table cards
   //pre: none
   //post: table cards are set to input parameters
   public static void setTable(int a, int b, int c, int d, int e)
   
   {
      tableCards[0] = a;
      tableCards[1] = b;
      tableCards[2] = c;
      tableCards[3] = d;
      tableCards[4] = e;
   }
   
   //selected table card is returned
   //pre: table cards have been dealt
   //post: single table card is returned
   public static int getTable(int a)
   
   {
      return(tableCards[a - 1]); 
   }
   
   //selected winning hand card is returned
   //pre: hand type has been calculated
   //post: winning hand card is returned
   public int getWinHand(int b)
   
   {
   
      return(winningHand[b - 1]);
      
   }
   
   //returns value of 2 hole cards
   //pre: none
   //post: returns 1 - 5 in accordance with hand strength
   public int getHandValue1()
   
   {
   
      int spades = 0;
      int hearts = 0;
      int clubs = 0;
      int diamonds = 0;
      
      for(int i = 0; i < 2; i++)//counts amount of each suit in player's hand
      {
         if(holes[i] >= 0 && holes[i] <= 12)
         {
            spades += 1;
         }
         else if(holes[i] >= 13 && holes[i] <= 25)
         {
            hearts += 1;
         }
         else if(holes[i] >= 26 && holes[i] <= 38)
         {
            clubs += 1;
         }
         else
         {
            diamonds += 1;
         }
      }
   
      if(holes[0]%13 == holes[1]%13 && holes[0]%13 >= 7)//high pair
      {
         return(5);
      }
      else if(holes[0]%13 == holes[1]%13)//pair
      {
         return(4);
      }
      else if(holes[0] - holes[1] == 1 || holes[1] - holes[0] == 1)//2 cards in a row
      {
         return(3);
      }
      else if(diamonds == 2 || spades == 2 || hearts == 2 || diamonds == 2)//cards are same suit
      {
         return(3);
      }
      else if(holes[0]%13 >= 8 || holes[1]%13 >= 8)//one high card
      {
         return(2);
      }
      else
      {
         return(1);
      }
      
   }
   
   //returns value of 2 hole cards and the first 3 table cards
   //pre: none
   //post: returns 1 - 5 in accordance with hand strength
   public int getHandValue2()
   
   {
   
      int spades = 0;
      int hearts = 0;
      int clubs = 0;
      int diamonds = 0;
      int matches = 0;
      int[] hand = {holes[0], holes[1], tableCards[0], tableCards[1], tableCards[2]};
      
      for(int i = 0; i < 5; i++)//counting amount in each suit
      {
         if(hand[i] >= 0 && hand[i] <= 12)
         {
            spades += 1;
         }
         else if(hand[i] >= 13 && hand[i] <= 25)
         {
            hearts += 1;
         }
         else if(hand[i] >= 26 && hand[i] <= 38)
         {
            clubs += 1;
         }
         else
         {
            diamonds += 1;
         }
      }
      
      for(int i = 0; i < 5; i++)//counting matches (times one card matches another)
      {
         for(int s = i + 1; s < 5; s++)
         {
            if(hand[i]%13 == hand[s]%13)
            {
               matches += 1;
            }
         }
      }
      
      hand[0] = hand[0]%13;//gets just the value (not the suit)
      hand[1] = hand[1]%13;//necessary in ordering cards by value
      hand[2] = hand[2]%13;
      hand[3] = hand[3]%13;
      hand[4] = hand[4]%13;
      
      for(int i = 0; i < 5; i++)//putting hand in order (to find a sequence)
      {
         for(int s = i; s < 5; s++)
         {
            if(hand[i] > hand[s])
            {
               int temp = hand[i];
               hand[i] = hand[s];
               hand[s] = temp;
            }
         }
      }
   
      //algorithm rewards pairs, sequences, and cards of the same suir
      if(matches >= 4)
      {
         return(5);
      }
      else if(matches >= 2)
      {
         return(4);
      }
      else if(matches >= 1)
      {
         return(3);
      }
      else if(diamonds >= 4 || spades >= 4 || hearts >= 4 || diamonds >= 4)
      {
         return(3);
      }
      else if(hand[4] - hand[0] <= 6 || hand[4] - hand[1] <= 5 || hand[3] - hand[0] <= 5 || hand[3] - hand[1] <= 3)//calculating a pseudo straight: cards are near each other
      {
         return(2);
      }
      else
      {
         return(1);
      }
      
   }
   
   //returns value of 2 hole cards and first 4 table cards
   //pre: none
   //post: returns 1 - 5 in accordance with hand strength
   public int getHandValue3()
   
   {
   
      int spades = 0;
      int hearts = 0;
      int clubs = 0;
      int diamonds = 0;
      int matches = 0;
      int[] hand = {holes[0], holes[1], tableCards[0], tableCards[1], tableCards[2], tableCards[3]};
      
      for(int i = 0; i < 6; i++)//counting suit occurences
      {
         if(hand[i] >= 0 && hand[i] <= 12)
         {
            spades += 1;
         }
         else if(hand[i] >= 13 && hand[i] <= 25)
         {
            hearts += 1;
         }
         else if(hand[i] >= 26 && hand[i] <= 38)
         {
            clubs += 1;
         }
         else
         {
            diamonds += 1;
         }
      }
      
      for(int i = 0; i < 6; i++)//counting matches
      {
         for(int s = i + 1; s < 6; s++)
         {
            if(hand[i]%13 == hand[s]%13)
            {
               matches += 1;
            }
         }
      }
      
      hand[0] = hand[0]%13;
      hand[1] = hand[1]%13;
      hand[2] = hand[2]%13;
      hand[3] = hand[3]%13;
      hand[4] = hand[4]%13;
      hand[5] = hand[5]%13;
      
      for(int i = 0; i < 6; i++)//putting hand in order
      {
         for(int s = i; s < 6; s++)
         {
            if(hand[i] > hand[s])
            {
               int temp = hand[i];
               hand[i] = hand[s];
               hand[s] = temp;
            }
         }
      }
   
      if(matches >= 4)
      {
         return(5);
      }
      else if(matches >= 3)
      {
         return(4);
      }
      else if(matches >= 2)
      {
         return(3);
      }
      else if(diamonds >= 4 || spades >= 4 || hearts >= 4 || diamonds >= 4)
      {
         return(4);
      }
      else if(hand[5] - hand[0] <= 6 || hand[5] - hand[1] <= 5 || hand[4] - hand[0] <= 5 || hand[4] - hand[1] <= 4)
      {
         return(3);
      }
      else if(matches == 1)
      {
         return(2);
      }
      else
      {
         return(1);
      }
      
   }
   
   //returns hand type (pair, flush, etc.)
   //pre: at least two players remain, all 5 table cards have been flipped over
   //post: returns a value from 0 - 9; each number corresponds with a hand type 
   //0: high card (nothing), 1: pair, 2: 2 pair, 3: 3 of a kind, 4: straight, 5: flush, 6: full house, 7: 4 of a kind, 8: straight flush, 9: royal flush
   public int getHandType()
   
   {
   
      boolean pair = false;//boolean variables state whether each type occurs within the player's 7 possible cards
      boolean twoPair = false;
      boolean three = false;
      boolean four = false;
      boolean fullHouse = false;
      boolean flush = false;
      boolean straight = false;
      boolean strFlush = false;
      boolean royFlush = false;
      ArrayList<Integer> spades = new ArrayList<Integer>();//stores cards of each suit
      ArrayList<Integer> hearts = new ArrayList<Integer>();
      ArrayList<Integer> clubs = new ArrayList<Integer>();
      ArrayList<Integer> diamonds = new ArrayList<Integer>();
      ArrayList<Integer> straightCards = new ArrayList<Integer>();//stores cards involved in a straight
      ArrayList<Integer> flushCards = new ArrayList<Integer>();//stores cards involved in a flush
      int matches = 0;//matches is the amount of pairs exist (in a 3 of a kind, 3 matches exist card1 + card2, card1 + card3, card2 + card3)
      int sum = 0;
      int[] fullHand = {holes[0], holes[1], tableCards[0], tableCards[1], tableCards[2], tableCards[3], tableCards[4]};//all 7 possible cards
      ArrayList<Integer> pairSizes = new ArrayList<Integer>();//sizes of cards involved in a pair, 3 of a kind, etc.
      ArrayList<Integer> threeSizes = new ArrayList<Integer>();
      ArrayList<Integer> fourSizes = new ArrayList<Integer>();
      int[] counter = new int[13];//stores matches associated with each value
      ArrayList<Integer> noPairSizes = new ArrayList<Integer>();//stores all cards with no repeats and only their values (no suit)
      noPairSizes.add(holes[0]%13);
      noPairSizes.add(holes[1]%13);
      noPairSizes.add(tableCards[0]%13);
      noPairSizes.add(tableCards[1]%13);
      noPairSizes.add(tableCards[2]%13);
      noPairSizes.add(tableCards[3]%13);
      noPairSizes.add(tableCards[4]%13);
      int[] impCards = new int[7];//value of array is 1 at that index if a pair/triple, etc. exists
      
      for(int i = 0; i < 7; i++)//counts matches
      {
         for(int s = i + 1; s < 7; s++)
         {
            if(fullHand[i]%13 == fullHand[s]%13)
            {
               matches += 1;
               impCards[s] = 1;
               impCards[i] = 1;
               counter[fullHand[i]%13] += 1;
               if(noPairSizes.contains(fullHand[i]%13))//ensures that noPairSizes does not contain any repeats
               {
                  for(int p = 0; p < noPairSizes.size(); p++)
                  {
                     if(noPairSizes.get(p) == fullHand[i]%13)
                     {
                        noPairSizes.remove(p);
                     }
                  }
               }
              
            }
         }
      }
      
      for(int i = 0; i < 13; i++)//adding pair, etc. values to "pair, etc. Sizes" arraylist 
      {
         if(counter[i] == 1)
         {
            pairSizes.add(i);
            noPairSizes.add(i);
         }
         else if(counter[i] == 3)
         {
            threeSizes.add(i);
            noPairSizes.add(i);
         }
         else if(counter[i] == 6)
         {
            fourSizes.add(i);
            noPairSizes.add(i);
         }
         
      }

      for(int i = 0; i < pairSizes.size(); i++)//sorts pairSizes from greatest to least
      {
         for(int s = i; s < pairSizes.size(); s++)
         {
            if(pairSizes.get(i) < pairSizes.get(s))
            {
               int temp = pairSizes.get(i);
               pairSizes.set(i, pairSizes.get(s));
               pairSizes.set(s, temp);
            }
         }
      }
      
      
      for(int i = 0; i < threeSizes.size(); i++)//greatest to least
      {
         for(int s = i; s < threeSizes.size(); s++)
         {
            if(threeSizes.get(i) < threeSizes.get(s))
            {
               int temp = threeSizes.get(i);
               threeSizes.set(i, threeSizes.get(s));
               threeSizes.set(s, temp);
            }
         }
      }
      
      for(int i = 0; i < 7; i++)
      {
         sum += impCards[i];//sum stores amount of cards out of 7 that are involved in a pair/triple, etc.
      }
      
      if(matches == 1)
      {
         pair = true;
      }
      else if(matches == 2 || (matches == 3 && sum == 6))//if out of 7 cards, 3 pairs exist, the player's hand is a 2 pair
      {
         twoPair = true;
      } 
      else if(matches == 3 && sum == 3)
      {
         three = true;
      }
      else if(matches == 4 || matches == 5 ||(matches == 6 && sum ==6))
      {
         fullHouse = true;
      }
      else if((matches == 6 && sum == 4) || matches > 6)
      {
         four = true;
      }
      
      for(int i = 0; i < 7; i++)//counts amount of cards in each suit and adds them to array
      {
         if(fullHand[i] >= 0 && fullHand[i] <= 12)
         {
            spades.add(fullHand[i]%13);
         }
         else if(fullHand[i] >= 13 && fullHand[i] <= 25)
         {
            hearts.add(fullHand[i]%13);
         }
         else if(fullHand[i] >= 26 && fullHand[i] <= 38)
         {
            clubs.add(fullHand[i]%13);
         }
         else
         {
            diamonds.add(fullHand[i]%13);
         }
      }
      //if a flush exists
      if(spades.size() >= 5 || hearts.size() >= 5 || clubs.size() >= 5 || diamonds.size() >= 5)
      {
         flush = true;
         if(spades.size() >= 5)//sorts flush cards from greatest to least
         {
             for(int i = 0; i < spades.size(); i++)
             {
                  for(int s = i; s < spades.size(); s++)
                  {
                     if(spades.get(i) > spades.get(s))
                     {
                        int temp = spades.get(i);
                        spades.set(i, spades.get(s));
                        spades.set(s, temp);
                     } 
                  }
             }
         }
         else if(hearts.size() >= 5)
         {
             for(int i = 0; i < hearts.size(); i++)
             {
                  for(int s = i; s < hearts.size(); s++)
                  {
                     if(hearts.get(i) > hearts.get(s))
                     {
                        int temp = hearts.get(i);
                        hearts.set(i, hearts.get(s));
                        hearts.set(s, temp);
                     }
                  }
             }
         }
         
         else if(clubs.size() >= 5)
         {
             for(int i = 0; i < clubs.size(); i++)
             {
                 for(int s = i; s < clubs.size(); s++)
                 {
                     if(clubs.get(i) > clubs.get(s))
                     {
                        int temp = clubs.get(i);
                        clubs.set(i, clubs.get(s));
                        clubs.set(s, temp);
                     }
                 }  
             }
         }
         
         else
         {
             for(int i = 0; i < diamonds.size(); i++)
             {
                 for(int s = i; s < diamonds.size(); s++)
                 {
                     if(diamonds.get(i) > diamonds.get(s))
                     {
                        int temp = diamonds.get(i);
                        diamonds.set(i, diamonds.get(s));
                        diamonds.set(s, temp);
                     }  
                 }
             }
         }
      }
      
      for(int i = 0; i < noPairSizes.size(); i++)//sorts no pair sizes
      {
         for(int s = i; s < noPairSizes.size(); s++)
         {
            if(noPairSizes.get(i) < noPairSizes.get(s))
            {
               int temp = noPairSizes.get(i);
               noPairSizes.set(i, noPairSizes.get(s));
               noPairSizes.set(s, temp);
            }
         }
      }
      
      if(noPairSizes.size() < 5)//a straight hand must have at least 5 unique cards
      {
         straight = false;
      }
      else if(noPairSizes.get(0) - 1 == noPairSizes.get(1) && noPairSizes.get(1) - 1 == noPairSizes.get(2) && noPairSizes.get(2) - 1 == noPairSizes.get(3) && noPairSizes.get(3) - 1 == noPairSizes.get(4))
      {
         straight = true;
         straightCards.add(noPairSizes.get(4));//adding cards to arraylist
         straightCards.add(noPairSizes.get(3));
         straightCards.add(noPairSizes.get(2));
         straightCards.add(noPairSizes.get(1));
         straightCards.add(noPairSizes.get(0));
      }
      //if the hand has a lot of unique cards, the straight could exist in different places
      else if(noPairSizes.size() >= 6 && noPairSizes.get(1) - 1 == noPairSizes.get(2) && noPairSizes.get(2) - 1 == noPairSizes.get(3) && noPairSizes.get(3) - 1 == noPairSizes.get(4) && noPairSizes.get(4) - 1 == noPairSizes.get(5))
      {
         straight = true;
         straightCards.add(noPairSizes.get(5));
         straightCards.add(noPairSizes.get(4));
         straightCards.add(noPairSizes.get(3));
         straightCards.add(noPairSizes.get(2));
         straightCards.add(noPairSizes.get(1));
      }
      else if(noPairSizes.size() == 7 && noPairSizes.get(2) - 1 == noPairSizes.get(3) && noPairSizes.get(3) - 1 == noPairSizes.get(4) && noPairSizes.get(4) - 1 == noPairSizes.get(5) && noPairSizes.get(5) - 1 == noPairSizes.get(6))
      {
         straight = true;
         straightCards.add(noPairSizes.get(6));
         straightCards.add(noPairSizes.get(5));
         straightCards.add(noPairSizes.get(4));
         straightCards.add(noPairSizes.get(3));
         straightCards.add(noPairSizes.get(2));
      }
      //A 2 3 4 5 is a straight but the algorithm consideres aces to be high
      else if(noPairSizes.contains(12) && noPairSizes.contains(0) && noPairSizes.contains(1) && noPairSizes.contains(2) && noPairSizes.contains(3))
      {
         straight = true;
         straightCards.add(12);
         straightCards.add(0);
         straightCards.add(1);
         straightCards.add(2);
         straightCards.add(3);
      }
      //testing to see if straight flush exists (a straight and a flush can exist in the same hand without a straight flush existing)
      if(flush == true && straight == true)
      {  //tests if the cards involved in the straight and flush are exactly the same
         if(spades.size() >= 5 && spades.contains(straightCards.get(0)) && spades.contains(straightCards.get(1)) && spades.contains(straightCards.get(2)) && spades.contains(straightCards.get(3)) && spades.contains(straightCards.get(4)))
         {
            strFlush = true;
         }
         else if(hearts.size() >= 5 && hearts.contains(straightCards.get(0)) && hearts.contains(straightCards.get(1)) && hearts.contains(straightCards.get(2)) && hearts.contains(straightCards.get(3)) && hearts.contains(straightCards.get(4)))
         {
            strFlush = true;
         }
         else if(clubs.size() >= 5 && clubs.contains(straightCards.get(0)) && clubs.contains(straightCards.get(1)) && clubs.contains(straightCards.get(2)) && clubs.contains(straightCards.get(3)) && clubs.contains(straightCards.get(4)))
         {
            strFlush = true;
         }
         else if(diamonds.size() >= 5 && diamonds.contains(straightCards.get(0)) && diamonds.contains(straightCards.get(1)) && diamonds.contains(straightCards.get(2)) && diamonds.contains(straightCards.get(3)) && diamonds.contains(straightCards.get(4)))
         {
            strFlush = true;
         }
      }
      //a royal flush is a straight flush in the highest cards
      if(strFlush == true && straightCards.contains(11) && straightCards.contains(12))
      {
         royFlush = true;
      }
      //return:
      //0: high card
      //1: pair
      //2: 2 pair
      //3: 3 of a kind
      //4: straight
      //5: flush
      //6: full house
      //7: four of a kind
      //8: straight flush
      //9: royal flush
      if(matches == 0 && straight == false && flush == false)
      {
         winningHand[0] = noPairSizes.get(4);//greatest to least
         winningHand[1] = noPairSizes.get(3);
         winningHand[2] = noPairSizes.get(2);
         winningHand[3] = noPairSizes.get(1);
         winningHand[4] = noPairSizes.get(0);
         return(0);
      }   
      else if(pair == true && straight == false && flush == false)
      {
         winningHand[0] = pairSizes.get(0);//pair first, other cards in descending order
         winningHand[1] = pairSizes.get(0);
         noPairSizes.remove(new Integer(winningHand[0]));
         if(pairSizes.get(0) == noPairSizes.get(0))
         {
            noPairSizes.remove(0);
         }
         if(pairSizes.get(0) == noPairSizes.get(1))
         {
            noPairSizes.remove(1);
         }
         else if(pairSizes.get(0) == noPairSizes.get(2))
         {
            noPairSizes.remove(2);
         }
         winningHand[2] = noPairSizes.get(2);
         winningHand[3] = noPairSizes.get(1);
         winningHand[4] = noPairSizes.get(0);
         return(1);
      }
      else if(twoPair == true && straight == false && flush == false)
      {
         winningHand[0] = pairSizes.get(1);//pairs in descending order, highest other card
         winningHand[1] = pairSizes.get(1);
         winningHand[2] = pairSizes.get(0);
         winningHand[3] = pairSizes.get(0);
         
         noPairSizes.remove(new Integer(winningHand[2]));
         noPairSizes.remove(new Integer(winningHand[1]));
         if(pairSizes.get(0) == noPairSizes.get(0))
         {
            noPairSizes.remove(0);
         }
         if(pairSizes.get(1) == noPairSizes.get(0))
         {
            noPairSizes.remove(0);
         }
         if(pairSizes.get(0) == noPairSizes.get(0))
         {
            noPairSizes.remove(0);
         }
         winningHand[4] = noPairSizes.get(0);
         return(2);
      }
      else if(three == true && straight == false && flush == false)
      {
         winningHand[0] = threeSizes.get(0);//triple, 2 cards in descending order
         winningHand[1] = threeSizes.get(0);
         winningHand[2] = threeSizes.get(0);
         noPairSizes.remove(new Integer(winningHand[1]));
         if(threeSizes.get(0) == noPairSizes.get(1))
         {
            noPairSizes.remove(1);
         }
         else if(threeSizes.get(0) == noPairSizes.get(0))
         {
            noPairSizes.remove(0);
         }
         winningHand[3] = noPairSizes.get(1);
         winningHand[4] = noPairSizes.get(0);
         return(3);
      }
      else if(straight == true && flush == false && fullHouse == false && four == false)
      {
         winningHand[0] = straightCards.get(0);//straight in descending order
         winningHand[1] = straightCards.get(1);
         winningHand[2] = straightCards.get(2);
         winningHand[3] = straightCards.get(3);
         winningHand[4] = straightCards.get(4);
         return(4);
      }
      else if(flush == true && straight == false && fullHouse == false && four == false)
      {
         if(spades.size() >= 5) {//flush in descending order
         winningHand[0] = spades.get(0);
         winningHand[1] = spades.get(1);
         winningHand[2] = spades.get(2);
         winningHand[3] = spades.get(3);
         winningHand[4] = spades.get(4);
         } else if(hearts.size() >= 5) {
         winningHand[0] = hearts.get(0);
         winningHand[1] = hearts.get(1);
         winningHand[2] = hearts.get(2);
         winningHand[3] = hearts.get(3);
         winningHand[4] = hearts.get(4);
         } else if(clubs.size() >= 5) {
         winningHand[0] = clubs.get(0);
         winningHand[1] = clubs.get(1);
         winningHand[2] = clubs.get(2);
         winningHand[3] = clubs.get(3);
         winningHand[4] = clubs.get(4);
         } else {
         winningHand[0] = diamonds.get(0);
         winningHand[1] = diamonds.get(1);
         winningHand[2] = diamonds.get(2);
         winningHand[3] = diamonds.get(3);
         winningHand[4] = diamonds.get(4);
         }
         
         return(5);
      }
      else if(fullHouse == true && four == false && strFlush == false)
      {
         if(matches == 4 || matches == 5)
         {
            winningHand[0] = threeSizes.get(0);//triple cards, then pair cards
            winningHand[1] = threeSizes.get(0);
            winningHand[2] = threeSizes.get(0);
            winningHand[3] = pairSizes.get(0);
            winningHand[4] = pairSizes.get(0);
         }
         else
         {
            winningHand[0] = threeSizes.get(0);
            winningHand[1] = threeSizes.get(0);
            winningHand[2] = threeSizes.get(0);
            winningHand[3] = threeSizes.get(1);
            winningHand[4] = threeSizes.get(1);
         }
         return(6);
      }
      else if(four == true && strFlush == false)
      {
         winningHand[0] = fourSizes.get(0);//quad cards then one card
         winningHand[1] = fourSizes.get(0);
         winningHand[2] = fourSizes.get(0);
         winningHand[3] = fourSizes.get(0);
         noPairSizes.remove(new Integer(winningHand[1]));
         if(fourSizes.get(0) == noPairSizes.get(0))
         {
            winningHand[4] = noPairSizes.get(1);
         }
         else
         {
            winningHand[4] = noPairSizes.get(0);
         }
         return(7);
      }
      else if(royFlush == false)
      {
         winningHand[0] = straightCards.get(0);//descending order
         winningHand[1] = straightCards.get(1);
         winningHand[2] = straightCards.get(2);
         winningHand[3] = straightCards.get(3);
         winningHand[4] = straightCards.get(4);
         return(8);
      }
      else
      {
         winningHand[0] = straightCards.get(0);
         winningHand[1] = straightCards.get(1);
         winningHand[2] = straightCards.get(2);
         winningHand[3] = straightCards.get(3);
         winningHand[4] = straightCards.get(4);
         return(9);
      }   
      
   }
                             
}