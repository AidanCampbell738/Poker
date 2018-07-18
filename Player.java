//Programmer: Aidan Campbell
//Description: stores each players info: money and their hand. superclass of user and comPlayer.
//Date Created: 12/18/2017
//Date Revised: 01/21/2018

import java.util.Random;

public class Player

{
   
   Hand playerHand;//has - a relationship with hand class
   double money;
   String letter;//letter is if they are player A, B... (position on board)
   int status = 2;
   static int counter = 1;//progresses through each player position (B, C, D, and E)
   static int cons = 0;//counts amount of each type of player
   static int aggr = 0;
   static int irra = 0;
   static int norm = 0;
   
   //Constructor
   //pre: none
   //post: player hand is created, money and letter are initialized
   public Player(double m, String l)
   
   {
   
      playerHand = new Hand();
      money = m;
      letter = l;
      
   }
   
   //sets player's money
   //pre: none
   //post: money is set to parameter
   public void setMoney(double m)
   
   {
   
      money = m;
      
   }
   
   //returns player's money
   //pre: none
   //post: money is returned
   public double getMoney()
   
   {
   
      return(money);
      
   }
   
   //returns player's status
   //pre: none
   //post: status is returned
   public int getStatus()
   
   {
   
      //0 = out of game (0 money)
      //1 = folded that round
      //2 = still in that round
      return(status);
      
   }
   
   //updates a player's status
   //pre: none
   //post: status is updated
   public void setStatus(int s)
   
   {
   
      status = s;
      
   }
   
   //sets hole cards
   //pre: none
   //post: hole cards are set in hand class
   public void setHoles1(int a, int b)
   
   {
   
      playerHand.setHoles(a, b);
      
   }
   
   //sets table cards
   //pre: none
   //post: table cards are set in hand class
   public static void setTable(int a, int b, int c, int d, int e)
   
   {
   
      Hand.setTable(a, b, c, d, e);
      
   }
   
   //returns selected table card
   //pre: table cards have been dealt
   //post: table card is returned
   public static int getTable(int b)
   
   {
   
      return(Hand.getTable(b));
      
   }
   
   //returns first hole card
   //pre: hole cards have been dealt
   //post: first hole card is returned
   public int getHole1()
   
   {
   
      return(playerHand.getFirstHole());
      
   }
   
   //returns second hole card
   //pre: hole cards have been dealt
   //post: second hole card is returned
   public int getHole2()
   
   {
   
      return(playerHand.getSecondHole());
      
   }
   
   //returns hand value based on the current betting round
   //pre: none
   //post: returns 1 - 5 based on hand value
   public int getHandValue(int round)
   
   {
   
      if(round == 1)//after opening deal
      {
         return(playerHand.getHandValue1());
      }
      else if(round == 2)//after flop
      {
         return(playerHand.getHandValue2());
      }
      else if(round == 3)//after turn
      {
         return(playerHand.getHandValue3());
      }
      else//after river
      {
         int temp = playerHand.getHandType();
         switch(temp)//hand types are adjusted to get reasonable hand values
         {
            case 0: temp = 1; 
            case 1: temp = 2; 
            case 2: temp = 3;
            case 3: 
            case 4: temp = 4;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9: temp = 5;
         }
         return(temp);
     }
      
   }
   
   //returns hand value type
   //pre: all cards have been dealt, at least 2 players remain
   //post: 0 - 9 is returned based on hand
   public int getHandType()
   
   {
   
      return(playerHand.getHandType());
      
   }
   
   //returns hand value type as a string
   //pre: all cards have been dealt, at least 2 players remain, betting is over
   //post: string containing hand type is returned
   public String getHandTypeS()
   
   {
   
      switch(playerHand.getHandType())
      {
         case 0: return("High Card");
         case 1: return("Pair");
         case 2: return("2 pair");
         case 3: return("3 of a kind");
         case 4: return("Straight");
         case 5: return("Flush");
         case 6: return("Full House");
         case 7: return("4 of a kind");
         case 8: return("Str Flush");
         default: return("Royal Flush");
      }
      
   }
   
   //returns one of 4 player types as a type of "Player" (polymorphism)
   //pre: none
   //post: one of 4 player types is returned
   public static Player getPlayer()
   
   {
   
      Random ran = new Random();
      int num = ran.nextInt(4);
      String letter = "";
      
      switch(counter)//assigning appropriate letter based on counter
      {
         case 1: letter = "B"; break;
         case 2: letter = "C"; break;
         case 3: letter = "D"; break;
         default: letter = "E";
      }
      //only one of each type can exist to ensure a balanced yet unpredictable game
      if(num == 0 && cons == 0)
      {
         cons += 1;
         counter += 1;
         return(new Conservative(20000, letter));
      }
      else if(num == 1 && aggr == 0)
      {
         aggr += 1;
         counter += 1;
         return(new Aggressive(20000, letter));   
      }
      else if(num == 2 && irra == 0)
      {
         irra += 1;
         counter += 1;
         return(new Irrational(20000, letter));
      }
      else if(num == 3 && norm == 0)
      {
         norm += 1;
         counter += 1;
         return(new Normal(20000, letter));
      }
      else
      {
         return(Player.getPlayer());//recursively calls method until a new player type is found
      }

   }
   
   //returns selected win hand card
   //pre: all cards have been dealt, at least 2 players remain
   //post: selected card is returned
   public int getWinHand(int a)
   
   {
   
      return(playerHand.getWinHand(a));
      
   }
   
   //returns player's letter (position on JFrame)
   //pre: none
   //post: letter is returned
   public String toString()
   
   {
   
      return(letter);
      
   }
   
   //resolves a 2 player tie 
   //pre: all cards have been dealt, betting is done, 2 players have the same hand type
   //post: returns 0 if hands are exactly the same, 1 if the first player won, 2 if the second player won
   public static int resolveTie(Player a, Player b)//paramaters are declared generically as type Player     
      
   {
   
      int difference = 0;//difference in hand values 
      int hand = a.getHandType();//hand type for both players
      
      if(hand == 0 || hand == 5)//nothing or flush
      {
         difference = a.getWinHand(1) - b.getWinHand(1);//compares top cards in each hand (they are in order from greatest to least)
         if(difference > 0)
         {
            return(1);
         }
         else if(difference < 0)
         {
            return(2);
         }
         else 
         {
            difference = a.getWinHand(2) - b.getWinHand(2);//compares second highest cards in each hand
            if(difference > 0)
            {
               return(1);
            }
            else if(difference < 0)
            {
               return(2);
            }
            else
            {
               difference = a.getWinHand(3) - b.getWinHand(3);//compares third highest cards in each hand
               if(difference > 0)
               {
                  return(1);
               }
               else if(difference < 0)
               {
                  return(2);
               }
               else
               {
                  difference = a.getWinHand(4) - b.getWinHand(4);//compares fourth highest cards in each hand
                  if(difference > 0)
                  {
                     return(1);
                  }
                  else if(difference < 0)
                  {
                     return(2);
                  }
                  else
                  {
                     difference = a.getWinHand(5) - b.getWinHand(5);//compares lowest highest cards in each hand
                     if(difference > 0)
                     {
                        return(1);
                     }
                     else if(difference < 0)
                     {
                        return(2);
                     }
                     else
                     {
                        return(0);
                     }
                  }
               }
            }
         }
      }
      else if(hand == 1)//if both hands are pairs
      {
         difference = a.getWinHand(1) - b.getWinHand(1);//compares the values of each's pair then the 3 other cards in descending order
         if(difference > 0)
         {
            return(1);
         }
         else if(difference < 0)
         {
            return(2);
         }
         else 
         {
            difference = a.getWinHand(3) - b.getWinHand(3);
            if(difference > 0)
            {
               return(1);
            }
            else if(difference < 0)
            {
               return(2);
            }
            else
            {
               difference = a.getWinHand(4) - b.getWinHand(4);
               if(difference > 0)
               {
                  return(1);
               }
               else if(difference < 0)
               {
                  return(2);
               }
               else
               {
                  difference = a.getWinHand(5) - b.getWinHand(5);
                  if(difference > 0)
                  {
                     return(1);
                  }
                  else if(difference < 0)
                  {
                     return(2);
                  }
                  else
                  {
                     return(0);
                  }
               }
            }
         }
      }
      else if(hand == 2)//if both hands are 2 pairs
      {
         difference = a.getWinHand(1) - b.getWinHand(1);//compares highest of each's pairs, the lowest of each's pairs then the other card
         if(difference > 0)
         {
            return(1);
         }
         else if(difference < 0)
         {
            return(2);
         }
         else 
         {
            difference = a.getWinHand(3) - b.getWinHand(3);
            if(difference > 0)
            {
               return(1);
            }
            else if(difference < 0)
            {
               return(2);
            }
            else
            {
               difference = a.getWinHand(5) - b.getWinHand(5);
               if(difference > 0)
               {
                  return(1);
               }
               else if(difference < 0)
               {
                  return(2);
               }
               else
               {
                  return(0); 
               }
            }
         }
      }
      else if(hand == 3)//three of a kind
      {
         difference = a.getWinHand(1) - b.getWinHand(1);//compares cards in the three of a kind then the 2 other cards
         if(difference > 0)
         {
            return(1);
         }
         else if(difference < 0)
         {
            return(2);
         }
         else 
         {
            difference = a.getWinHand(4) - b.getWinHand(4);
            if(difference > 0)
            {
               return(1);
            }
            else if(difference < 0)
            {
               return(2);
            }
            else
            {
               difference = a.getWinHand(5) - b.getWinHand(5);
               if(difference > 0)
               {
                  return(1);
               }
               else if(difference < 0)
               {
                  return(2);
               }
               else
               {
                  return(0); 
               }
            }
         }
      }
      else if(hand == 4 || hand == 8|| hand == 9)//if straight, straight flush, or royal flush
      {
         difference = a.getWinHand(1) - b.getWinHand(1);//compares first card only (all others will be in descending order by 1)
         if(difference > 0)
         {
            return(1);
         }
         else if(difference < 0)
         {
            return(2);
         }
         else 
         {
            return(0);
         }
      }
      else if(hand == 6)//full house
      {
         difference = a.getWinHand(1) - b.getWinHand(1);//compares triple then pair
         if(difference > 0)
         {
            return(1);
         }
         else if(difference < 0)
         {
            return(2);
         }
         else 
         {
            difference = a.getWinHand(4) - b.getWinHand(4);
            if(difference > 0)
            {
               return(1);
            }
            else if(difference < 0)
            {
               return(2);
            }
            else
            {
               return(0);
            }
         }
      }
      else//four of a kind
      {
         difference = a.getWinHand(1) - b.getWinHand(1);//compares quad then one other card
         if(difference > 0)
         {
            return(1);
         }
         else if(difference < 0)
         {
            return(2);
         }
         else 
         {
            difference = a.getWinHand(5) - b.getWinHand(5);
            if(difference > 0)
            {
               return(1);
            }
            else if(difference < 0)
            {
               return(2);
            }
            else
            {
               return(0);
            }
         }
      }  
   }
}
      
   
   