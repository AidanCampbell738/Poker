//Programmer: Aidan Campbell
//Description: class for "conservative" type of computer player: less likely to bet and raise
//Date Created: 12/18/2017
//Date Revised: 01/21/2018

import java.util.Random;

public class Conservative extends comPlayer

{
   //Constructor
   //pre: none
   //post: name and superclass object are initialized
   public Conservative(double m, String l)
   
   {
   
      super(m, l);//superclaas constructor (money and letter)
      
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

      if(betSize == 0 && num <= 25)//higher threshold for calling and raising
      {
         return(1);
      }
      else if(betSize == 0)
      {
         return(3);
      }
      else if(betSize < 0.05)
      {
         return(2);
      }
      else if(betSize < 0.1 && num >= 12 && num <= 27)
      {
         return(2);
      }
      else if(betSize < 0.1 && num > 27)
      {
         return(4);
      }
      else if(betSize < 0.2 && num >= 15 && num <= 28)
      {
         return(2);
      }
      else if(betSize < 0.2 && num > 28)
      {
         return(4);
      }
      else if(betSize < 0.5 && num >= 20)
      {
         return(2);
      }
      else if(betSize < 0.8 && num >= 25)
      {
         return(2);
      }
      else if(betSize >= 1.0 && num >= 30)
      {
         return(5);
      }
      else
      {
         return(0);
      }
           
   }
   
}
      
      
      
      