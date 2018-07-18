//Programmer: Aidan Campbell
//Description: class for "normal" type of computer player: simulates a regular player
//Date Created: 12/18/2017
//Date Revised: 01/21/2018

import java.util.Random;

public class Normal extends comPlayer

{
   //Constructor
   //pre: none
   //post: name and superclass object are initialized
   public Normal(double m, String l)
   
   {
   
      super(m, l);//calling super class constructor with money and letter
      
   }
   
   //returns action of player
   //pre: it is the player's turn
   //post: action is returned
   public int getAction(int round, int bet)
   
   {
      //return value:
      //0 = fold
      //1 = check
      //2 = call
      //3 = bet
      //4 = raise
      //5 = all - in
      Random ran = new Random();
      double betSize = (double)bet/super.getMoney();
      int num = super.getHandValue(round)*5 + ran.nextInt(11);

      if(betSize == 0 && num <= 20)
      {
         return(1);
      }
      else if(betSize == 0)
      {
         return(3);
      }
      else if(num <= 12 && betSize < 0.35)
      {
         return(0);
      }
      else if(num > 12 && num <= 20 && betSize < 0.35)
      {
         return(2);
      }
      else if(num > 20 && betSize < 0.35)
      {
         return(4);
      }
      else if(num <= 15 && betSize < 1.0)
      {
         return(0);
      }
      else if(num > 15 && num <= 24 && betSize < 1.0)
      {
         return(2);
      }
      else if(num > 24 && betSize < 1.0)
      {
         return(4);
      }
      else if(betSize >= 1.0 && num >= 28)
      {
         return(5);
      }
      else
      {
         return(0);
      }

           
   }
   
}