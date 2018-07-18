//Programmer: Aidan Campbell
//Description: super class of conservative, irrational, aggressive, and normal
//Date Created: 12/18/2017
//Date Revised: 01/21/2018

import java.util.Random;

abstract class comPlayer extends Player

{
   //Constructor
   //pre: none
   //post: superclass object is created
   public comPlayer(double m, String l)
   
   {
   
      super(m, l);
   
   }
   
   //returns how much a player will bet/raise by
   //pre: player's action is bet or raise
   //post: amount is returned
   public int howMuch(int bet, int betDif)
   
   {
   
      Random ran = new Random();
      double multiplier = (double)ran.nextInt(30)/100 + 1;//bet/raise can be up to 30% higher than minimum bet/raise
      int amount = 0;//stores amount player will bet/raise
   
      if(bet == 0)
      {
         amount = (int)(400 * multiplier);//an opening bet has to be at least the size of the big blind (400)
         return(amount);
      }
      else
      {
         amount = (int)((bet + betDif) * multiplier);//a subsequent raise has to be at least the previous bet plus the amount that that bet raised by
         if(betDif == 0)
         {
            amount = (int)((bet + 400) * multiplier);//a first raise has to be at least the previous bet plus the big blind (400)
         }
         return(amount);
      } 
      
   }
   
   //should return the action of a computer player
   //pre: must be that player's turn
   //post: number corresponding with action is returned
   //0: fold, 1: check, 2: call, 3: bet, 4: raise, 5: all in
   abstract int getAction(int round, int bet);
  
}