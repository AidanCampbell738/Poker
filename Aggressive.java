//Programmer: Aidan Campbell
//Description: class for "aggressive" type of computer player: more likely to bet and raise
//Date Created: 12/18/2017
//Date Revised: 01/21/2018

import java.util.Random;

public class Aggressive extends comPlayer

{

   //Constructor
   //pre: none
   //post: name and superclass object are initialized
   public Aggressive(double m, String l)
   
   {
   
      super(m, l);//calling super class constructor with money and letter
      
   }
   
   //returns action of player
   //pre: it is the player's turn
   //post: action is returned
   public int getAction(int round, int bet)//returns player's action
   
   {
      //return value:
      //0 = fold
      //1 = check
      //2 = call
      //3 = bet
      //4 = raise
      //5 = all - in
      Random ran = new Random();
      double betSize = (double)bet/super.getMoney();//stores value of current bet as a percentage of remaining money
      int num = super.getHandValue(round)*5 + ran.nextInt(11);//num is a combination of the hand's value and RNG; this value determines the player's actions
      int raiseOdds = ran.nextInt(11);//if the player will raise

      //takes hand value, bet value, and RNG to determine action
      if(betSize == 0 && num >= 5)//num can range from 1 - 35
      {
         return(3);
      }
      else if(betSize == 0)
      {
         return(1);
      }
      else if(betSize < 0.05 && raiseOdds >= 6)
      {
         return(4);
      }
      else if(betSize < 0.05)
      {
         return(2);
      }
      else if(betSize < 0.2 && num >= 10 && raiseOdds >= 8)
      {
         return(4);
      }
      else if(betSize < 0.2 && num >= 10)
      {
         return(2);
      }
      else if(betSize < 0.5 && num >= 15 && raiseOdds >= 7)
      {
         return(4);
      }
      else if(betSize < 0.5 && num >= 5)
      {
         return(2);
      }
      else if(betSize < 0.8 && num >= 18 && raiseOdds >= 7)
      {
         return(4);
      }
      else if(betSize < 0.8 && num >= 15)
      {
         return(2);
      }
      else if(betSize >= 1.0 && num >= 25)
      {
         return(5);
      }
      else
      {
         return(0);
      }
           
   }
   
}