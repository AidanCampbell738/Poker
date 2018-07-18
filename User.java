//Programmer: Aidan Campbell
//Description: class for the user and user info
//Date Created: 12/18/2017
//Date Revised: 01/21/2018

public class User extends Player

{

   String name;
   
   //Constructor
   //pre: none
   //post: name and superclass object are initialized
   public User(double m, String l)
   
   {
   
      super(m, l);
      name = "";
      
   }
   
   //sets user's name
   //pre: none
   //post: user's name is set to paramater value
   public void setName(String n)
   
   {
      
      name = n;
      
   }
   
   //returns user's name
   //pre: none
   //post: user's name is returned
   public String getName()
   
   {
   
      return(name);
      
   }
   
}