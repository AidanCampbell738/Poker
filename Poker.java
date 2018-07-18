//Programmer: Aidan Campbell
//Description: Plays a 5 round game of texas hold 'em poker
//Date Created: 12/18/2017
//Date Revised: 01/21/2018

import java.awt.event.*;//importing necessary classes
import java.util.Random;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.lang.*;
import java.text.NumberFormat;

public class Poker implements ActionListener

{
   //variables accessible to all methods in this class
   JFrame f;//main JFrame
   //the display is divided into 3 sections: main1 which contains the top 2 player info, main2 which contains the other 2 comPlayer info and the board, and main3 which contains user info and user buttons
   JPanel contentPane, main1, main2, main3;//contentpane contains the 3 main panes
   JPanel subPane1, subPane2, subPane3, subPane4, subPane5, subPane6, subPane7;//subpanes contain smaller sections such as all of one player's info
   JPanel pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10;//these panes contain even smaller panes necessary for certain labels
   //all of these panes are neccessary to ensure that when various elements become visible or invisible, the layout doesn't shift drastically
   JLabel cardA1, cardA2, cardB1, cardB2, cardC1, cardC2, cardD1, cardD2, cardE1, cardE2;//all player cards
   JLabel nameA, moneyA, actionA, nameB, moneyB, actionB, nameC, moneyC, actionC, nameD, moneyD, actionD, nameE, moneyE, actionE;//all labels showing each player's name, action, and money
   JLabel pot, description, table1, table2, table3, table4, table5;//pot shows current pot balance, description is stage in round (ex: opening deal), tables are the cards in the middle
   //showcards are the cards you see when starting the game
   JLabel showCard1, showCard2, showCard3, showCard4, showCard5, showCard6, showCard7, showCard8, showCard9, showCard10, showCard11, showCard12, showCard13, showCard14, showCard15, showCard16, showCard17, showCard18, showCard19, showCard20;
   JButton call, fold, raise, plus, minus, startRound;//user buttons
   JTextField userName;//where the user enters their name
   final int BIG_BLIND = 400;//The big blind value is used as the basis for most of the betting calculations
   int potValue = 0;//value of pot
   int flag = 1;//minor variable used whenever the user goes all in to ensure the counter works properly
   Timer myTimer;//timer controls most of the game
   Color gold = Color.decode("#FFD700");
   int[] counter = {1, 0, 0, 0, 0, 0};//round # out of 5, card # in the opening deal, deal# within a round, amount of players in, # players out, all - in players
   int[] amounts = {BIG_BLIND, BIG_BLIND, 0, 0};//current bet, difference in previous raises, user's raise
   int[] moneyPot = new int[5];//tracks money already placed in pot by each player
   int deadPlayers = 0;//amount of players who are out of the game 
   //declaring player objects; superclass object references subclass instance
   comPlayer pB = (comPlayer)Player.getPlayer();//gets the 1 of 4 player types
   comPlayer pC = (comPlayer)Player.getPlayer();//randomly associates each player type with each computer player to ensure a different, but balanced experience each game (user who plays multiple games can't find patterns in each player's behaviour)
   comPlayer pD = (comPlayer)Player.getPlayer();
   comPlayer pE = (comPlayer)Player.getPlayer();//making the variable of type "Player" didn't work for some reason so I just went with "comPlayer"
   User p5 = new User(20000,"A");//declaring user object
   ArrayList<Player> order = new ArrayList<Player>();//order arraylist stores the order of players (player B, C, D, E, A) for the blind bets
   Deck myDeck = new Deck();//creating a deck object
   String currentTurn = "";//stores the next player to go if betting is interrupted because of the deal
   NumberFormat money = NumberFormat.getCurrencyInstance();
   boolean allIn = false;//there are special rules for when a player goes "all - in"
   Color darkGreen = Color.decode("#004d00");//color of the poker table
              
   public Poker()
   
   {
       
      Color darkRed = Color.decode("#610797");//"darkRed" is actually purple
      ImageIcon card = new ImageIcon("Card Pictures//53.png");//refers to the back of every card
      order.add(pB); order.add(pC); order.add(pD); order.add(pE); order.add(p5);//adding the players to order arraylist
      
      f = new JFrame("Poker");
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      contentPane = new JPanel();//principal JPanel
      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
      
      main1 = new JPanel();
      main1.setBackground(darkRed);
      main1.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
      main2 = new JPanel();
      main2.setBackground(darkRed);
      main3 = new JPanel();
      main3.setBackground(darkRed);
      main3.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
      
      subPane1 = new JPanel();//contains the cards of player C and their stats
      subPane1.setLayout(new GridLayout(1,3,0,0));
      subPane1.setBorder(BorderFactory.createEmptyBorder(0,0,0,100));//border separates the 2 players on main1
      subPane1.setBackground(darkRed);
      subPane2 = new JPanel();//contains the cards of player D and their stats
      subPane2.setLayout(new GridLayout(1,3,0,0));
      subPane2.setBackground(darkRed);
      subPane3 = new JPanel();//contains the cards of player B and their stats
      subPane3.setLayout(new GridLayout(2,1,0,0));
      subPane3.setBorder(BorderFactory.createEmptyBorder(25,20,0,20));
      subPane3.setBackground(darkRed);
      subPane4 = new JPanel();//contains the cards of player E and their stats
      subPane4.setLayout(new GridLayout(2,1,0,0));
      subPane4.setBorder(BorderFactory.createEmptyBorder(25,20,0,20));
      subPane4.setBackground(darkRed);
      subPane5 = new JPanel();//contains the 5 table cards, the description, and pot values
      subPane5.setLayout(new BoxLayout(subPane5, BoxLayout.PAGE_AXIS));
      subPane5.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
      subPane5.setPreferredSize(new Dimension(675,375));//size is necessary to fit all 5 card pictures
      subPane5.setBackground(darkGreen);
      subPane5.setOpaque(true);
      subPane6 = new JPanel();//contains the cards of the user/player A and their stats
      subPane6.setLayout(new GridLayout(1,3,0,0));
      subPane6.setBackground(darkRed);
      subPane7 = new JPanel();//contains the buttons
      subPane7.setLayout(new GridLayout(1,2,10,0));
      subPane7.setBorder(BorderFactory.createEmptyBorder(0,80,0,0));
      subPane7.setBackground(darkRed);
      
      pane1 = new JPanel();//contains the stats of player C
      pane1.setLayout(new GridLayout(3,1,0,0));
      pane1.setBorder(BorderFactory.createEmptyBorder(0,25,0,0));
      pane1.setBackground(darkRed);
      pane2 = new JPanel();//contains the stats of player D
      pane2.setLayout(new GridLayout(3,1,0,0));
      pane2.setBorder(BorderFactory.createEmptyBorder(0,0,0,25));
      pane2.setBackground(darkRed);
      pane3 = new JPanel();//contains the cards of player B
      pane3.setLayout(new GridLayout(1,2,6,0));
      pane3.setBackground(darkRed);
      pane4 = new JPanel();//contains the stats of player B
      pane4.setLayout(new GridLayout(3,1,0,0));
      pane4.setBackground(darkRed);
      pane5 = new JPanel();//contains the cards of player E
      pane5.setLayout(new GridLayout(1,2,6,0));
      pane5.setBackground(darkRed);
      pane6 = new JPanel();//contains the stats of player E
      pane6.setLayout(new GridLayout(3,1,0,0)); 
      pane6.setBackground(darkRed);
      pane7 = new JPanel();//contains the table cards; uses flow layout
      pane7.setBackground(darkGreen);
      pane8 = new JPanel();
      pane8.setLayout(new GridLayout(3,1,0,0));//contains the stats of user/player A
      pane8.setBorder(BorderFactory.createEmptyBorder(0,25,0,0));
      pane8.setBackground(darkRed);
      pane9 = new JPanel();
      pane9.setLayout(new GridLayout(2,1,10,10));//contains fold and call buttons
      pane9.setBackground(darkRed);
      pane10 = new JPanel();
      pane10.setLayout(new GridLayout(2,1,0,0));//contains plus and minus buttons
      pane10.setBackground(darkRed);
      
      cardC1 = new JLabel(card);//player C is the one on the top left
      cardC1.setVisible(false);
      cardC2 = new JLabel(card);
      cardC2.setVisible(false);
      subPane1.add(cardC1);
      subPane1.add(cardC2);
      nameC = new JLabel("Morty");//names are hard - coded into the game
      nameC.setFont(new Font("Arial", Font.BOLD, 25));//visuals for each label produce the desired display
      nameC.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
      nameC.setForeground(Color.WHITE);
      nameC.setVisible(false);
      moneyC = new JLabel("$20000.00");//setting default label values
      moneyC.setFont(new Font("Arial", Font.BOLD, 20));
      moneyC.setForeground(Color.WHITE);
      moneyC.setVisible(false);
      actionC = new JLabel("Check");
      actionC.setFont(new Font("Arial", Font.BOLD, 18));
      actionC.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
      actionC.setForeground(Color.WHITE);
      actionC.setVisible(false);
      pane1.add(nameC);
      pane1.add(moneyC);
      pane1.add(actionC);
      subPane1.add(pane1);
      
      cardD1 = new JLabel(card);
      cardD1.setVisible(false);
      cardD2 = new JLabel(card);
      cardD2.setVisible(false);
      nameD = new JLabel("Jerry");
      nameD.setFont(new Font("Arial", Font.BOLD, 25));
      nameD.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
      nameD.setForeground(Color.WHITE);
      nameD.setVisible(false);
      moneyD = new JLabel("$20000.00");
      moneyD.setFont(new Font("Arial", Font.BOLD, 20));
      moneyD.setForeground(Color.WHITE);
      moneyD.setVisible(false);
      actionD = new JLabel("Check");
      actionD.setFont(new Font("Arial", Font.BOLD, 18));
      actionD.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
      actionD.setForeground(Color.WHITE);
      actionD.setVisible(false);
      pane2.add(nameD);
      pane2.add(moneyD);
      pane2.add(actionD);
      subPane2.add(pane2);
      subPane2.add(cardD1);
      subPane2.add(cardD2);
      
      cardB1 = new JLabel(card);
      cardB1.setVisible(false);
      cardB2 = new JLabel(card);
      cardB2.setVisible(false);
      nameB = new JLabel("Richard");
      nameB.setFont(new Font("Arial", Font.BOLD, 25));
      nameB.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
      nameB.setForeground(Color.WHITE);
      nameB.setVisible(false);
      moneyB = new JLabel("$20000.00");
      moneyB.setFont(new Font("Arial", Font.BOLD, 20));
      moneyB.setForeground(Color.WHITE);
      moneyB.setVisible(false);
      actionB = new JLabel("Check");
      actionB.setFont(new Font("Arial", Font.BOLD, 18));
      actionB.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
      actionB.setForeground(Color.WHITE);
      actionB.setVisible(false);
      pane3.add(cardB1);
      pane3.add(cardB2);
      pane4.add(nameB);
      pane4.add(moneyB);
      pane4.add(actionB);
      subPane3.add(pane3);
      subPane3.add(pane4); 
      
      description = new JLabel("Welcome to Poker!");//opening greeting
      description.setFont(new Font("Cooper Black", Font.PLAIN, 30));
      description.setAlignmentX(JLabel.CENTER_ALIGNMENT);
      description.setForeground(Color.WHITE);
      description.setBorder(BorderFactory.createEmptyBorder(10,0,30,0));
      table1 = new JLabel(card);//table cards' default is the back card
      table1.setVisible(false);
      table2 = new JLabel(card);
      table2.setVisible(false);
      table3 = new JLabel(card);
      table3.setVisible(false);
      table4 = new JLabel(card);
      table4.setVisible(false);
      table5 = new JLabel(card);
      table5.setVisible(false);
      pane7.add(table1);
      pane7.add(table2);
      pane7.add(table3);
      pane7.add(table4);
      pane7.add(table5);
      pot = new JLabel("Pot: $0.00");//starting pot
      pot.setFont(new Font("Cooper Black", Font.PLAIN, 30));
      pot.setForeground(Color.WHITE);
      pot.setAlignmentX(JLabel.CENTER_ALIGNMENT);
      pot.setBorder(BorderFactory.createEmptyBorder(40,0,20,0));
      subPane5.add(description);
      subPane5.add(pane7);
      subPane5.add(pot);
      
      cardE1 = new JLabel(card);//final computer player
      cardE1.setVisible(false);
      cardE2 = new JLabel(card);
      cardE2.setVisible(false);
      nameE = new JLabel("Summer");
      nameE.setFont(new Font("Arial", Font.BOLD, 25));
      nameE.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
      nameE.setForeground(Color.WHITE);
      nameE.setVisible(false);
      moneyE = new JLabel("$20000.00");
      moneyE.setFont(new Font("Arial", Font.BOLD, 20));
      moneyE.setForeground(Color.WHITE);
      moneyE.setVisible(false);
      actionE = new JLabel("Check");
      actionE.setFont(new Font("Arial", Font.BOLD, 18));
      actionE.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
      actionE.setForeground(Color.WHITE);
      actionE.setVisible(false);
      pane5.add(cardE1);
      pane5.add(cardE2);
      pane6.add(nameE);
      pane6.add(moneyE);
      pane6.add(actionE);
      subPane4.add(pane5);
      subPane4.add(pane6);
      
      cardA1 = new JLabel(card);
      cardA1.setBorder(BorderFactory.createEmptyBorder(0,0,0,4));//ensures there is a slight gap between the 2 cards
      cardA1.setVisible(false);
      cardA2 = new JLabel(card);
      cardA2.setVisible(false);
      nameA = new JLabel("Player A");//name displayed will be the user inputted name
      nameA.setFont(new Font("Arial", Font.BOLD, 25));
      nameA.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
      nameA.setForeground(Color.WHITE);
      nameA.setVisible(false);
      moneyA = new JLabel("$20000.00");
      moneyA.setFont(new Font("Arial", Font.BOLD, 20));
      moneyA.setForeground(Color.WHITE);
      moneyA.setVisible(false);
      actionA = new JLabel("Check");
      actionA.setFont(new Font("Arial", Font.BOLD, 18));
      actionA.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
      actionA.setForeground(Color.WHITE);
      actionA.setVisible(false);
      pane8.add(nameA);
      pane8.add(moneyA);
      pane8.add(actionA);
      subPane6.add(cardA1);
      subPane6.add(cardA2);
      subPane6.add(pane8);
      
      call = new JButton("Call - 600");//call allows a player to call or check (match the previous bet or bet nothing)
      call.setFont(new Font("Cooper Black", Font.PLAIN, 27)); 
      call.setActionCommand("Call");//adding action commands and listeners for each button
      call.addActionListener(this);
      call.setVisible(false);
      fold = new JButton("Fold");//allows a player to fold
      fold.setFont(new Font("Cooper Black", Font.PLAIN, 27));
      fold.setActionCommand("Fold");
      fold.addActionListener(this);
      fold.setVisible(false);
      raise = new JButton("<html>Raise to<br>$200</html>");//html formats the button
      raise.setFont(new Font("Cooper Black", Font.PLAIN, 27));
      raise.setActionCommand("raise");
      raise.addActionListener(this);
      raise.setVisible(false);
      plus = new JButton("+");//plus allows the user to increase the raise/bet amount by 100
      plus.setFont(new Font("Cooper Black", Font.PLAIN, 27));
      plus.setActionCommand("plus");
      plus.addActionListener(this);
      plus.setVisible(false);
      minus = new JButton("-");
      minus.setFont(new Font("Cooper Black", Font.PLAIN, 27));
      minus.setActionCommand("minus");
      minus.addActionListener(this);
      minus.setVisible(false);
      startRound = new JButton("Start Game");//start round starts the game, round or displays the highscores
      startRound.setFont(new Font("Cooper Black", Font.PLAIN, 27));
      startRound.setActionCommand("name");//GUI goes straight to name entry after button is clicked
      startRound.addActionListener(this);
      userName = new JTextField(10);//user enters their name in a textfield at the start
      userName.setActionCommand("start game");
      userName.addActionListener(this);
      userName.setVisible(false);
      pane7.add(userName);
      pane9.add(call);
      pane9.add(fold);
      pane10.add(plus);
      pane10.add(minus);
      pane7.add(startRound);
      pot.setVisible(false);
      subPane7.add(pane9);
      subPane7.add(raise);
      subPane7.add(pane10);
      
      showCard1 = new JLabel(new ImageIcon("Card Pictures//8.png"));//showcards display symmetrical royal flushes in each suit
      showCard2 = new JLabel(new ImageIcon("Card Pictures//9.png"));
      showCard3 = new JLabel(new ImageIcon("Card Pictures//10.png"));
      showCard4 = new JLabel(new ImageIcon("Card Pictures//11.png"));
      showCard5 = new JLabel(new ImageIcon("Card Pictures//12.png"));
      showCard6 = new JLabel(new ImageIcon("Card Pictures//25.png"));
      showCard7 = new JLabel(new ImageIcon("Card Pictures//24.png"));
      showCard8 = new JLabel(new ImageIcon("Card Pictures//23.png"));
      showCard9 = new JLabel(new ImageIcon("Card Pictures//22.png"));
      showCard10 = new JLabel(new ImageIcon("Card Pictures//21.png"));
      main1.add(showCard1);
      main1.add(showCard2);
      main1.add(showCard3);
      main1.add(showCard4);
      main1.add(showCard5);
      main1.add(showCard6);
      main1.add(showCard7);
      main1.add(showCard8);
      main1.add(showCard9);
      main1.add(showCard10);
      subPane1.setVisible(false);
      subPane2.setVisible(false);
      showCard11 = new JLabel(new ImageIcon("Card Pictures//47.png"));
      showCard12 = new JLabel(new ImageIcon("Card Pictures//48.png"));
      showCard13 = new JLabel(new ImageIcon("Card Pictures//49.png"));
      showCard14 = new JLabel(new ImageIcon("Card Pictures//50.png"));
      showCard15 = new JLabel(new ImageIcon("Card Pictures//51.png"));
      showCard16 = new JLabel(new ImageIcon("Card Pictures//38.png"));
      showCard17 = new JLabel(new ImageIcon("Card Pictures//37.png"));
      showCard18 = new JLabel(new ImageIcon("Card Pictures//36.png"));
      showCard19 = new JLabel(new ImageIcon("Card Pictures//35.png"));
      showCard20 = new JLabel(new ImageIcon("Card Pictures//34.png"));
      main3.add(showCard11);
      main3.add(showCard12);
      main3.add(showCard13);
      main3.add(showCard14);
      main3.add(showCard15);
      main3.add(showCard16);
      main3.add(showCard17);
      main3.add(showCard18);
      main3.add(showCard19);
      main3.add(showCard20);
      subPane6.setVisible(false);
      subPane7.setVisible(false);
      
      main1.add(subPane1);//adding each subPane to its respective main panel
      main1.add(subPane2);
      main2.add(subPane3);
      main2.add(subPane5);
      main2.add(subPane4);    
      main3.add(subPane6);
      main3.add(subPane7);
   
      contentPane.add(main1);//adding each main pane to the contentPane
      contentPane.add(main2);
      contentPane.add(main3);
      
      f.setContentPane(contentPane);
      f.pack();
      f.setVisible(true);
      
   }
   
   //reacts to an event
   //pre: user caused event must have occured
   //post: GUI, player stats, and game progression are updated
   public void actionPerformed(ActionEvent event)
   
   {
       
      String eventName = event.getActionCommand();//event name of action listener
      ActionListener myLis = 
         new ActionListener() {//new action listener for the timer (nested class)
         
          //timer that reacts to an event, continues to be called until gameplay is ready for the next user action
          //pre: user event must have occured
          //post: GUI, player stats and game progression are updated
            public void actionPerformed(ActionEvent e)
            
            {
               String ev = e.getActionCommand();//event name
               int bet = amounts[0];//bet is used inside the nested class
               int action;
               if(allIn == false && p5.getMoney() == 0 && flag == 0)//if the user just went all in 
               {
                  allIn = true;
                  flag = 1;//this will only iterate once after the user goes all in
                  counter[5] = 1;//all in players counter (how many have reacted to all in); once it reaches 5, the deals will occur
                  counter[3] = 1;//players that are in counter 
                  if(moneyPot[4] >= moneyPot[0] || pE.getStatus() != 2)//if a player has bet more than the user went all in for or they are out of the round, they do not need to bet again
                  {
                     counter[5] += 1;
                  }
                  if(moneyPot[2] >= moneyPot[0] || pC.getStatus() != 2)
                  {
                     counter[5] += 1;
                  }
                  if(moneyPot[3] >= moneyPot[0] || pD.getStatus() != 2)
                  {
                     counter[5] += 1;
                  }
                  if(moneyPot[1] >= moneyPot[0] || pB.getStatus() != 2)
                  {
                     counter[5] += 1;
                  }
               }
               else if(p5.getMoney() == 0 && flag == 0)//if the user went all in after another player went all in
               {
                  flag = 1;
                  counter[5] += 1;
                  counter[3] += 1;
               }
               
               if(ev.compareTo("Deal") == 0)//deals table cards and controls the showdown (end of the round)
               {
                  try{ Thread.sleep(2500); } 
                  catch (Exception ex){}//pause in gameplay
                  nameA.setForeground(Color.WHITE);//if the name is gold, that indicates that it's that player's turn
                  nameB.setForeground(Color.WHITE);
                  nameC.setForeground(Color.WHITE);
                  nameD.setForeground(Color.WHITE);
                  nameE.setForeground(Color.WHITE);
                  if(pB.getStatus() == 2 && allIn == false)//hides actions after each deal (unless they went all in)
                  {
                     actionB.setVisible(false);
                  }
                  if(pC.getStatus() == 2 && allIn == false)
                  {
                     actionC.setVisible(false);
                  }
                  if(pD.getStatus() == 2 && allIn == false)
                  {
                     actionD.setVisible(false);
                  }
                  if(pE.getStatus() == 2 && allIn == false)
                  {
                     actionE.setVisible(false);
                  }
                  if(p5.getStatus() == 2 && allIn == false)
                  {
                     actionA.setVisible(false);
                  }
                  moneyPot[0] = 0;//amount of money each player placed in pot resets for next betting round
                  moneyPot[1] = 0;
                  moneyPot[2] = 0;
                  moneyPot[3] = 0;
                  moneyPot[4] = 0;
                  bet = 0;
                  amounts[1] = 0;
                  amounts[3] = 0;
                  counter[2] += 1;//progression in current round increases by 1  
                  counter[3] = 0;//players in is back to 0
                  
                  if(counter[2] == 1)//deals "flop" cards: the first 3 table cards
                  {
                     description.setText("The Flop");
                     table1.setIcon(new ImageIcon("Card Pictures//" + Player.getTable(1) + ".png"));
                     table1.setVisible(true);
                     table2.setIcon(new ImageIcon("Card Pictures//" + Player.getTable(2) + ".png"));
                     table2.setVisible(true);
                     table3.setIcon(new ImageIcon("Card Pictures//" + Player.getTable(3) + ".png"));
                     table3.setVisible(true);
                     if(allIn == false)//if all in is true, the betting is over for that entire round; if it isn't betting resumes where it left off
                     {
                        ((Timer)e.getSource()).setActionCommand(currentTurn); 
                     } 
                  }
                  else if(counter[2] == 2)//deals "turn" card: 4th table card
                  {
                     description.setText("The Turn");
                     table4.setIcon(new ImageIcon("Card Pictures//" + Player.getTable(4) + ".png"));
                     table4.setVisible(true);
                     if(allIn == false)
                     {
                        ((Timer)e.getSource()).setActionCommand(currentTurn); 
                     }
                  }
                  else if(counter[2] == 3)//deals "river" card: last table card
                  {
                     description.setText("The River");
                     table5.setIcon(new ImageIcon("Card Pictures//" + Player.getTable(5) + ".png"));
                     table5.setVisible(true);
                     if(allIn == false)
                     {
                        ((Timer)e.getSource()).setActionCommand(currentTurn); 
                     }
                  }
                  else//end of the round where player's show their hole cards
                  {
                     if(pB.getStatus() == 2)
                     {
                        cardB1.setIcon(new ImageIcon("Card Pictures//" + pB.getHole1() + ".png"));//getting player's hole cards
                        cardB2.setIcon(new ImageIcon("Card Pictures//" + pB.getHole2() + ".png"));
                        actionB.setText(pB.getHandTypeS());
                        actionB.setVisible(true);//action label now shows hand type
                     }
                     if(pC.getStatus() == 2)
                     {
                        cardC1.setIcon(new ImageIcon("Card Pictures//" + pC.getHole1() + ".png"));
                        cardC2.setIcon(new ImageIcon("Card Pictures//" + pC.getHole2() + ".png"));
                        actionC.setText(pC.getHandTypeS());
                        actionC.setVisible(true);
                     }
                     if(pD.getStatus() == 2)
                     {
                        cardD1.setIcon(new ImageIcon("Card Pictures//" + pD.getHole1() + ".png"));
                        cardD2.setIcon(new ImageIcon("Card Pictures//" + pD.getHole2() + ".png"));
                        actionD.setText(pD.getHandTypeS());
                        actionD.setVisible(true);
                     }
                     if(pE.getStatus() == 2)
                     {
                        cardE1.setIcon(new ImageIcon("Card Pictures//" + pE.getHole1() + ".png"));
                        cardE2.setIcon(new ImageIcon("Card Pictures//" + pE.getHole2() + ".png"));
                        actionE.setText(pE.getHandTypeS());
                        actionE.setVisible(true);
                     }
                     if(p5.getStatus() == 2)
                     {
                        actionA.setText(p5.getHandTypeS());
                        actionA.setVisible(true);
                     }
                     ((Timer)e.getSource()).setActionCommand("End of Round 1");//sets up timer for next iteration   
                  }
               }
               else if(ev.compareTo("End of Round 1") == 0)//first part of end of round duties
               {
                  try{ Thread.sleep(2500); } 
                  catch (Exception ex){}
                  ArrayList<Integer> handValues = new ArrayList<Integer>();//arraylists used to find winning players and their hands
                  ArrayList<Player> winners = new ArrayList<Player>();
                  boolean tie = false;
                  boolean three = false;//if a three way tie produces a singular winner, it can't go to the 2 way tie algorithm
                  if(pB.status == 2)
                  {
                     handValues.add(pB.getHandType());
                     winners.add(pB);//winners consists of all players still participating
                  }
                  if(pC.status == 2)
                  {
                     handValues.add(pC.getHandType());
                     winners.add(pC);
                  }
                  if(pD.status == 2)
                  {
                     handValues.add(pD.getHandType());
                     winners.add(pD);
                  }
                  if(pE.status == 2)
                  {
                     handValues.add(pE.getHandType());
                     winners.add(pE);
                  }
                  if(p5.status == 2)
                  {
                     handValues.add(p5.getHandType());
                     winners.add(p5);
                  }
                  
                  for(int i = 0; i < handValues.size(); i++)//sorts hand values from greatest to least
                  {
                     for(int s = i; s < handValues.size(); s++)
                     {
                        if(handValues.get(i) < handValues.get(s))
                        {
                           int temp = handValues.get(i);
                           handValues.set(i, handValues.get(s));
                           handValues.set(s, temp);
                           Player p = winners.get(i);
                           winners.set(i, winners.get(s));//sorts corresponding winneres
                           winners.set(s, p);
                        }
                     }
                  }
                  if(handValues.size() >= 3 && handValues.get(0) == handValues.get(2))//three players have the same hand type
                  {
                     int firstWin = Player.resolveTie(winners.get(0), winners.get(1));//compare each pair individually
                     int secWin = Player.resolveTie(winners.get(0), winners.get(2));
                     int thirdWin = Player.resolveTie(winners.get(1), winners.get(2));
                     if(firstWin == 0 && secWin == 0 && thirdWin == 0)//if a true 3 way tie
                     {
                        winners.get(0).setMoney(winners.get(0).getMoney() + potValue/3);//split pot three ways
                        winners.get(1).setMoney(winners.get(1).getMoney() + potValue/3);
                        winners.get(2).setMoney(winners.get(2).getMoney() + potValue/3);
                        moneyA.setText(money.format(p5.getMoney()));
                        moneyB.setText(money.format(pB.getMoney()));
                        moneyC.setText(money.format(pC.getMoney()));
                        moneyD.setText(money.format(pD.getMoney()));
                        moneyE.setText(money.format(pE.getMoney()));
                        description.setText("3 Way Tie!");
                        tie = true;
                     }
                     else if(firstWin == 0 && secWin == 1)//2 way tie
                     {
                        winners.get(0).setMoney(winners.get(0).getMoney() + potValue/2);
                        winners.get(1).setMoney(winners.get(1).getMoney() + potValue/2);
                        moneyA.setText(money.format(p5.getMoney()));
                        moneyB.setText(money.format(pB.getMoney()));
                        moneyC.setText(money.format(pC.getMoney()));
                        moneyD.setText(money.format(pD.getMoney()));
                        moneyE.setText(money.format(pE.getMoney()));
                        description.setText("Tie!");
                        tie = true;
                     }
                     else if(secWin == 0 && firstWin == 1)
                     {
                        winners.get(0).setMoney(winners.get(0).getMoney() + potValue/2);
                        winners.get(2).setMoney(winners.get(2).getMoney() + potValue/2);
                        moneyA.setText(money.format(p5.getMoney()));
                        moneyB.setText(money.format(pB.getMoney()));
                        moneyC.setText(money.format(pC.getMoney()));
                        moneyD.setText(money.format(pD.getMoney()));
                        moneyE.setText(money.format(pE.getMoney()));
                        description.setText("Tie!");
                        tie = true;
                     }
                     else if(thirdWin == 0 && firstWin == 2)
                     {
                        winners.get(2).setMoney(winners.get(2).getMoney() + potValue/2);
                        winners.get(1).setMoney(winners.get(1).getMoney() + potValue/2);
                        moneyA.setText(money.format(p5.getMoney()));
                        moneyB.setText(money.format(pB.getMoney()));
                        moneyC.setText(money.format(pC.getMoney()));
                        moneyD.setText(money.format(pD.getMoney()));
                        moneyE.setText(money.format(pE.getMoney()));
                        description.setText("Tie!");
                        tie = true;
                     }
                     else//if only one player won
                     {
                        if(firstWin == 2 && thirdWin == 1)
                        {
                           winners.remove(0);//removes the players who lost
                        }
                        else if(secWin == 2 && thirdWin == 2)
                        {
                           winners.remove(1);
                           winners.remove(0);
                        }
                        three = true;
                     }
                  }       
                  else if(handValues.get(0) == handValues.get(1) && tie == false && three == false)//2 way tie
                  {
                     int tieBreaker = Player.resolveTie(winners.get(0), winners.get(1));//breaks tie
                     if(tieBreaker == 0)//true tie
                     {
                        winners.get(0).setMoney(winners.get(0).getMoney() + potValue/2);//splits pot
                        winners.get(1).setMoney(winners.get(1).getMoney() + potValue/2);
                        moneyA.setText(money.format(p5.getMoney()));//updates money
                        moneyB.setText(money.format(pB.getMoney()));
                        moneyC.setText(money.format(pC.getMoney()));
                        moneyD.setText(money.format(pD.getMoney()));
                        moneyE.setText(money.format(pE.getMoney()));
                        description.setText("Tie!");
                        tie = true;
                     }
                     else if(tieBreaker == 2)//removes loser
                     {
                        winners.remove(0);
                     }
                  }
                  if(tie == false)
                  {
                     winners.get(0).setMoney(winners.get(0).getMoney() + potValue);//sets winner's money
                     String playerLetter = winners.get(0).toString();//stores position of winner
                     counter[3] = 0;//resets counters
                     counter[4] = 0;
                     counter[5] = 0;
                     if(playerLetter.compareTo("A") == 0)//updates winner stats according to their position
                     {
                        nameA.setForeground(gold);
                        moneyA.setText(money.format(p5.getMoney()));
                        description.setText(p5.getName() + " wins Hand!");
                     }
                     else if(playerLetter.compareTo("B") == 0)
                     {
                        nameB.setForeground(gold);
                        moneyB.setText(money.format(pB.getMoney()));
                        description.setText("Richard wins Hand!");
                     }
                     else if(playerLetter.compareTo("C") == 0)
                     {
                        nameC.setForeground(gold);
                        moneyC.setText(money.format(pC.getMoney()));
                        description.setText("Morty wins Hand!");
                     }
                     else if(playerLetter.compareTo("D") == 0)
                     {
                        nameD.setForeground(gold);
                        moneyD.setText(money.format(pD.getMoney()));
                        description.setText("Jerry wins Hand!");
                     }
                     else
                     {
                        nameE.setForeground(gold);
                        moneyE.setText(money.format(pE.getMoney()));
                        description.setText("Summer wins Hand!");
                     }  
                  }
                  
                  ((Timer)e.getSource()).setActionCommand("End of Round 2"); 
               
               }
               else if(ev.compareTo("End of Round 2") == 0)//end of the round part 2
               {
                  try{ Thread.sleep(8000); } 
                  catch (Exception ex){}//waits after winners are displayed
                  deadPlayers = 0;
                  counter[0] += 1;//increases number of rounds played
                  counter[1] = 0;//resets all other counters
                  counter[2] = 0;
                  counter[3] = 0;
                  counter[4] = 0;
                  counter[5] = 0;
                  amounts[0] = BIG_BLIND;//resets raise and bet amounts
                  amounts[1] = BIG_BLIND;
                  amounts[2] = 0;
                  amounts[3] = 0;
                  moneyPot[0] = 0;//amount of money each player placed in pot resets for next betting round
                  moneyPot[1] = 0;
                  moneyPot[2] = 0;
                  moneyPot[3] = 0;
                  moneyPot[4] = 0;
                  allIn = false;
                  potValue = 0;
                  pot.setText("Pot: 0");
                  nameA.setForeground(Color.WHITE);
                  nameB.setForeground(Color.WHITE);
                  nameC.setForeground(Color.WHITE);
                  nameD.setForeground(Color.WHITE);
                  nameE.setForeground(Color.WHITE);
                  p5.setStatus(2);
                  pB.setStatus(2);
                  pC.setStatus(2);
                  pD.setStatus(2);
                  pE.setStatus(2);
                  actionA.setVisible(false);//sets all actions and cards to not visible
                  actionB.setVisible(false);
                  actionC.setVisible(false);
                  actionD.setVisible(false);
                  actionE.setVisible(false);
                  cardA1.setVisible(false);
                  cardA2.setVisible(false);
                  cardB1.setVisible(false);
                  cardB2.setVisible(false);
                  cardC1.setVisible(false);
                  cardC2.setVisible(false);
                  cardD1.setVisible(false);
                  cardD2.setVisible(false);
                  cardE1.setVisible(false);
                  cardE2.setVisible(false);
                  table1.setVisible(false);
                  table2.setVisible(false);
                  table3.setVisible(false);
                  table4.setVisible(false);
                  table5.setVisible(false);
                  pot.setVisible(false);
                  order.clear();//resets order
                  order.add(pB); order.add(pC); order.add(pD); order.add(pE); order.add(p5);
                  if(pB.getMoney() <= 0)//if player B is out
                  {
                     pB.setStatus(0);//updates status
                     actionB.setVisible(false);//updates GUI
                     nameB.setVisible(false);
                     moneyB.setVisible(false);
                     deadPlayers += 1;
                     if(order.contains(pB))//removes player from order
                     {
                        order.remove(pB);
                     }
                  }
                  if(pC.getMoney() <= 0)
                  {
                     pC.setStatus(0);
                     actionC.setVisible(false);
                     nameC.setVisible(false);
                     moneyC.setVisible(false);
                     deadPlayers += 1;
                     if(order.contains(pC))
                     {
                        order.remove(pC);
                     }
                  }
                  if(pD.getMoney() <= 0)
                  {
                     pD.setStatus(0);
                     actionD.setVisible(false);
                     nameD.setVisible(false);
                     moneyD.setVisible(false);
                     deadPlayers += 1;
                     if(order.contains(pD))
                     {
                        order.remove(pD);
                     }
                     
                  }
                  if(pE.getMoney() <= 0)
                  {
                     pE.setStatus(0);
                     actionE.setVisible(false);
                     nameE.setVisible(false);
                     moneyE.setVisible(false);
                     deadPlayers += 1;
                     if(order.contains(pE))
                     {
                        order.remove(pE);
                     }
                  }
                  //end of game for user if:
                  //they have 0 money
                  //they are the lasdt player standing
                  //5 rounds have passed
                  if(p5.getMoney() <= 0 || counter[0] > 5 || deadPlayers == 4)
                  {
                     if(p5.getMoney() <= 0)
                     {
                        description.setText("Game Over");//setting appropriate message
                     }
                     else if(counter[0] > 5)
                     {
                        description.setText("You survived 5 rounds!");
                     }
                     else
                     {
                        description.setText("Congratulations! You won!");
                     }
                     startRound.setText("See Highscores");//player can see highscores list
                     startRound.setActionCommand("HighScores");
                  }
                  else
                  {
                     description.setText("Round " + counter[0] + "/5");
                  }
                  startRound.setVisible(true);//prepares for next round
                  ((Timer)e.getSource()).stop();//stops timer
               }
               else if(ev.compareTo("Default Winner") == 0)//if only 1 player remains
               {
                  try{ Thread.sleep(2500); } 
                  catch (Exception ex){}
                  nameA.setForeground(Color.WHITE);
                  nameB.setForeground(Color.WHITE);
                  nameC.setForeground(Color.WHITE);
                  nameD.setForeground(Color.WHITE);
                  nameE.setForeground(Color.WHITE);
                  if(p5.getStatus() == 2)//gices pot top appropriate player
                  {
                     p5.setMoney(p5.getMoney() + potValue);
                     nameA.setForeground(gold);
                     moneyA.setText(money.format(p5.getMoney()));
                     description.setText(p5.getName() + " wins Hand!");
                  }
                  else if(pB.getStatus() == 2)
                  {
                     pB.setMoney(pB.getMoney() + potValue);
                     nameB.setForeground(gold);
                     moneyB.setText(money.format(pB.getMoney()));
                     description.setText("Richard wins Hand!");
                  }
                  else if(pC.getStatus() == 2)
                  {
                     pC.setMoney(pC.getMoney() + potValue);
                     nameC.setForeground(gold);
                     moneyC.setText(money.format(pC.getMoney()));
                     description.setText("Morty wins Hand!");
                  }
                  else if(pD.getStatus() == 2)
                  {
                     pD.setMoney(pD.getMoney() + potValue);
                     nameD.setForeground(gold);
                     moneyD.setText(money.format(pD.getMoney()));
                     description.setText("Jerry wins Hand!");
                  }
                  else
                  {
                     pE.setMoney(pE.getMoney() + potValue);
                     nameE.setForeground(gold);
                     moneyE.setText(money.format(pE.getMoney()));
                     description.setText("Summer wins Hand!");
                  }
                  counter[5] = 0;//resets counter
                  counter[4] = 0;
                  counter[3] = 0;
                  counter[2] = 0;
                  ((Timer)e.getSource()).setActionCommand("End of Round 2");//jumps to end of round
               }     
                       
               else if(ev.compareTo("Small Blind") == 0)//first bet of every round; equals half of big blind
               {
                  try{ Thread.sleep(2500); } 
                  catch (Exception ex){}
                  if(order.get(0).getMoney() <= 200)//will skip player if they do not have enough
                  {
                     order.remove(0);
                  }
                  if(order.get(0) == pB)//first blind is normally played by player B
                  {
                     pB.setMoney(pB.getMoney() - BIG_BLIND/2);
                     moneyB.setText(money.format(pB.getMoney()));
                     moneyPot[1] = BIG_BLIND/2;
                     actionB.setText("S Blind 200");
                     actionB.setVisible(true);
                     nameB.setForeground(gold);
                  }
                  else if(order.get(0) == pC)
                  {
                     pC.setMoney(pC.getMoney() - BIG_BLIND/2);
                     moneyC.setText(money.format(pC.getMoney()));
                     moneyPot[2] = BIG_BLIND/2;
                     actionC.setText("S Blind 200");
                     actionC.setVisible(true);
                     nameC.setForeground(gold);
                  }
                  else if(order.get(0) == pD)
                  {
                     pD.setMoney(pD.getMoney() - BIG_BLIND/2);
                     moneyD.setText(money.format(pD.getMoney()));
                     moneyPot[3] = BIG_BLIND/2;
                     actionD.setText("S Blind 200");
                     actionD.setVisible(true);
                     nameD.setForeground(gold);
                  }
                  else
                  {
                     pE.setMoney(pE.getMoney() - BIG_BLIND/2);
                     moneyE.setText(money.format(pE.getMoney()));
                     moneyPot[4] = BIG_BLIND/2;
                     actionE.setText("S Blind 200");
                     actionE.setVisible(true);
                     nameE.setForeground(gold);
                  }
                  potValue += BIG_BLIND/2;//updates pot
                  pot.setText("Pot: " + money.format(potValue));
                  ((Timer)e.getSource()).setActionCommand("Big Blind");//goes directly to big blinf
               }
               else if(ev.compareTo("Big Blind") == 0)//second bet of every round
               {
                  nameB.setForeground(Color.WHITE);
                  nameC.setForeground(Color.WHITE);
                  nameD.setForeground(Color.WHITE);
                  nameE.setForeground(Color.WHITE);
                  try{ Thread.sleep(2500); } 
                  catch (Exception ex){} 
                  if(order.get(1).getMoney() <= 400)//player will not do big blind if they cannot afford it
                  {
                     order.remove(1);
                  }
                  if(order.get(1) == pC)//normally done by Player C
                  {
                     nameC.setForeground(gold);
                     pC.setMoney(pC.getMoney() - BIG_BLIND);//updates money and GUI
                     moneyC.setText(money.format(pC.getMoney()));
                     moneyPot[2] = BIG_BLIND;
                     actionC.setText("B Blind 400");
                     actionC.setVisible(true);
                     nameC.setForeground(gold);
                     counter[3] = 1;
                  }
                  else if(order.get(1) == pD)
                  {
                     nameD.setForeground(gold);
                     pD.setMoney(pD.getMoney() - BIG_BLIND);
                     moneyD.setText(money.format(pD.getMoney()));
                     moneyPot[3] = BIG_BLIND;
                     actionD.setText("B Blind 400");
                     actionD.setVisible(true);
                     nameD.setForeground(gold);
                     counter[3] = 1;//counter updated to know when to deal flop
                  }
                  else if(order.get(1) == pE)
                  {
                     nameE.setForeground(gold);
                     pE.setMoney(pE.getMoney() - BIG_BLIND);
                     moneyE.setText(money.format(pE.getMoney()));
                     moneyPot[4] = BIG_BLIND;
                     actionE.setText("B Blind 400");
                     actionE.setVisible(true);
                     nameE.setForeground(gold);
                     counter[3] = 1;
                  }
                  else
                  {
                     nameA.setForeground(gold);
                     p5.setMoney(p5.getMoney() - BIG_BLIND);
                     moneyA.setText(money.format(p5.getMoney()));
                     moneyPot[0] = BIG_BLIND;
                     actionA.setText("B Blind 400");
                     actionA.setVisible(true);
                     nameA.setForeground(gold);
                     counter[3] = 1;
                  }
                  potValue += BIG_BLIND;
                  pot.setText("Pot: " + money.format(potValue));
                  bet = BIG_BLIND;
                  ((Timer)e.getSource()).setActionCommand("Deal part1");//goes to the opening deal
               }
               else if(ev.compareTo("Deal part1") == 0)//sets all hole cards and table cards
               {
                  try{ Thread.sleep(2500); } 
                  catch (Exception ex){}
                  nameA.setForeground(Color.WHITE);
                  nameB.setForeground(Color.WHITE);
                  nameC.setForeground(Color.WHITE);
                  nameD.setForeground(Color.WHITE);
                  nameE.setForeground(Color.WHITE);
                  pB.setHoles1(myDeck.drawCard(), myDeck.drawCard());
                  pC.setHoles1(myDeck.drawCard(), myDeck.drawCard());
                  pD.setHoles1(myDeck.drawCard(), myDeck.drawCard());
                  pE.setHoles1(myDeck.drawCard(), myDeck.drawCard());
                  p5.setHoles1(myDeck.drawCard(), myDeck.drawCard());
                  Player.setTable(myDeck.drawCard(), myDeck.drawCard(), myDeck.drawCard(), myDeck.drawCard(), myDeck.drawCard());
                  cardA1.setIcon(new ImageIcon("Card Pictures//" + p5.getHole1() + ".png"));
                  cardA2.setIcon(new ImageIcon("Card Pictures//" + p5.getHole2() + ".png"));
                  cardB1.setIcon(new ImageIcon("Card Pictures//53.png"));//back of card
                  cardB2.setIcon(new ImageIcon("Card Pictures//53.png"));
                  cardC1.setIcon(new ImageIcon("Card Pictures//53.png"));
                  cardC2.setIcon(new ImageIcon("Card Pictures//53.png"));
                  cardD1.setIcon(new ImageIcon("Card Pictures//53.png"));
                  cardD2.setIcon(new ImageIcon("Card Pictures//53.png"));
                  cardE1.setIcon(new ImageIcon("Card Pictures//53.png"));
                  cardE2.setIcon(new ImageIcon("Card Pictures//53.png"));
                  description.setText("Opening deal");
                  ((Timer)e.getSource()).setActionCommand("Deal part2");//second part of opening deal
               }
               else if(ev.compareTo("Deal part2") == 0)//simulates how a person would actually deal hands in poker
               {
                  try{ Thread.sleep(200); } 
                  catch (Exception ex){}
                  switch(counter[1])
                  {//only sets cards visible if the player is participating in that round
                     case 0: 
                        if(pB.getStatus() == 2) {cardB1.setVisible(true);}; 
                        break;
                     case 1: 
                        if(pC.getStatus() == 2) {cardC1.setVisible(true);}; 
                        break;
                     case 2: 
                        if(pD.getStatus() == 2) {cardD1.setVisible(true);}; 
                        break;
                     case 3: 
                        if(pE.getStatus() == 2) {cardE1.setVisible(true);}; 
                        break;
                     case 4: 
                        if(p5.getStatus() == 2) {cardA1.setVisible(true);}; 
                        break;
                     case 5: 
                        if(pB.getStatus() == 2) {cardB2.setVisible(true);}; 
                        break;
                     case 6: 
                        if(pC.getStatus() == 2) {cardC2.setVisible(true);}; 
                        break;
                     case 7: 
                        if(pD.getStatus() == 2) {cardD2.setVisible(true);}; 
                        break;
                     case 8: 
                        if(pE.getStatus() == 2) {cardE2.setVisible(true);}; 
                        break;
                     default: 
                        if(p5.getStatus() == 2) {cardA2.setVisible(true);};                   
                  }
                  counter[1] += 1;
                  if(counter[1] == 10)
                  {
                     description.setText("Betting");
                     if(order.size() > 2)
                     {
                        if(order.get(2) != p5)//goes to the first betting round
                        {
                           ((Timer)e.getSource()).setActionCommand("TakeTurn" + order.get(2).toString());
                        }
                        else//if 3/4 computer players are out
                        {
                           ((Timer)e.getSource()).setActionCommand("Prepare for A");
                        }
                     }
                     else
                     {
                        ((Timer)e.getSource()).setActionCommand("TakeTurn" + order.get(0).toString());
                     }  
                  }
                     
               }
               else if(ev.compareTo("TakeTurnB") == 0)//player B will take their turn
               {  
                  if(pB.getStatus() == 2)//will skip over player if they are not participating
                  {
                     try{ Thread.sleep(2000); } 
                     catch (Exception ex){}//delay between turns
                     description.setText("Betting");
                     action = pB.getAction(counter[2] + 1, bet);//getting player's action
                     nameA.setForeground(Color.WHITE);
                     nameB.setForeground(gold);//informs user that it's player B's turn
                     nameC.setForeground(Color.WHITE);
                     nameD.setForeground(Color.WHITE);
                     nameE.setForeground(Color.WHITE);
                     if(action == 0)//fold
                     {
                        cardB1.setVisible(false);
                        cardB2.setVisible(false);
                        actionB.setText("Fold");
                        pB.setStatus(1);
                        counter[3] += 1;
                        counter[4] += 1;
                        if(allIn == true)//player does not need to bet if folded during all in progression
                        {
                           counter[5] += 1;
                        }
                     }
                     else if(action == 1)//check
                     {
                        actionB.setText("Check");
                        counter[3] += 1;
                     }
                     else if(action == 2)//call
                     {
                        actionB.setText("Call " + bet);
                        pB.setMoney(pB.getMoney() - bet + moneyPot[1]);//considers how much the player already placed in the pot
                        moneyB.setText(money.format(pB.getMoney()));//updates player's and pot's money
                        potValue += bet - moneyPot[1];
                        moneyPot[1] = bet;//updates how much they placed in pot
                        pot.setText("Pot: " + money.format(potValue));
                        counter[3] += 1;
                        if(allIn == true)
                        {
                           counter[5] += 1;
                        }
                     }
                     else if(action == 3)//bet
                     {
                        bet = pB.howMuch(bet, amounts[1]);
                        if(bet >= pB.getMoney())//if player happens to bet more than they have
                        {
                           actionB.setText("All in!");
                           potValue += pB.getMoney();
                           moneyPot[1] = (int)pB.getMoney();
                           bet = (int)pB.getMoney();
                           pB.setMoney(0);
                        }
                        else
                        {
                           actionB.setText("Bet " + bet);
                           pB.setMoney(pB.getMoney() - bet + moneyPot[1]);
                           potValue += bet - moneyPot[1];
                           moneyPot[1] = bet;
                           counter[3] = 1;//if a player bets/raises all players will have to take another turn
                        }
                        moneyB.setText(money.format(pB.getMoney()));
                        pot.setText("Pot: " + money.format(potValue));
                     }
                     else if(action == 4)//raise
                     {
                     
                        if(allIn == true)//if someone went all in, a player cannot raise
                        {
                           counter[5] += 1;
                           actionB.setText("Call " + bet);//calls instead of raising
                           counter[3] += 1;
                           pB.setMoney(pB.getMoney() - bet + moneyPot[1]);
                           potValue += bet - moneyPot[1];
                           moneyPot[1] = bet;
                        } 
                        else
                        {
                           int temp = bet;
                           bet = pB.howMuch(bet, amounts[1]);
                           if(bet - moneyPot[1] >= pB.getMoney())//if player's raise is greater than what they currently have
                           {
                              actionB.setText("All in!");
                              potValue += pB.getMoney();
                              moneyPot[1] += (int)pB.getMoney();
                              bet += (int)pB.getMoney();
                              pB.setMoney(0);
                           }
                           else
                           {
                              actionB.setText("Raise " + bet);
                              pB.setMoney(pB.getMoney() - bet + moneyPot[1]);
                              potValue += bet - moneyPot[1];
                              amounts[1] = bet - temp;
                              moneyPot[1] = bet;
                              counter[3] = 1;
                           }
                           
                        }
                        moneyB.setText(money.format(pB.getMoney()));
                        pot.setText("Pot: " + money.format(potValue));
                     
                     }
                     else//a player calls but that amount is more than they have
                     {
                        actionB.setText("All In!");
                        potValue += pB.getMoney();
                        moneyPot[1] += (int)pB.getMoney();
                        pB.setMoney(0);
                        moneyB.setText(money.format(pB.getMoney()));
                        pot.setText("Pot: " + money.format(potValue));
                     }
                     actionB.setVisible(true);
                     if(allIn == false && pB.getMoney() == 0)//if player just went all in
                     {
                        allIn = true;//all in progression begins
                        counter[5] = 1;//resets betting counters
                        counter[3] = 1;
                        //a player who already played more than the current player or is out does not need to bet again
                        if(moneyPot[0] >= moneyPot[1] || p5.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                        if(moneyPot[2] >= moneyPot[1] || pC.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                        if(moneyPot[3] >= moneyPot[1] || pD.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                        if(moneyPot[4] >= moneyPot[1] || pE.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                     }
                     else if(pB.getMoney() == 0)//if the player went all in after someone else when all in
                     {
                        counter[5] += 1;
                        counter[3] += 1;
                     }
                  }
                  else//if the player is not participating 
                  {
                     counter[3] += 1;//algorithm pretends the user didn't raise or bet
                  }
                  ((Timer)e.getSource()).setActionCommand("TakeTurnC");//points to the next player
               }
               
               else if(ev.compareTo("TakeTurnC") == 0)//exactly the same as player B but with player C
               {
                  if(pC.getStatus() == 2)
                  {
                     try{ Thread.sleep(2000); } 
                     catch (Exception ex){}
                     description.setText("Betting");
                     action = pC.getAction(counter[2] + 1, bet);
                     nameA.setForeground(Color.WHITE);
                     nameB.setForeground(Color.WHITE);
                     nameC.setForeground(gold);
                     nameD.setForeground(Color.WHITE);
                     nameE.setForeground(Color.WHITE);
                     if(action == 0)
                     {
                        cardC1.setVisible(false);
                        cardC2.setVisible(false);
                        actionC.setText("Fold");
                        pC.setStatus(1);
                        counter[3] += 1;
                        counter[4] += 1;
                        if(allIn == true)
                        {
                           counter[5] += 1;
                        } 
                     }
                     else if(action == 1)
                     {
                        actionC.setText("Check");
                        counter[3] += 1;  
                     }
                     else if(action == 2)
                     {
                        actionC.setText("Call " + bet);
                        pC.setMoney(pC.getMoney() - bet + moneyPot[2]);
                        moneyC.setText(money.format(pC.getMoney()));
                        potValue += bet - moneyPot[2];
                        moneyPot[2] = bet;
                        pot.setText("Pot: " + money.format(potValue));
                        counter[3] += 1; 
                        if(allIn == true)
                        {
                           counter[5] += 1;
                        } 
                        
                     }
                     else if(action == 3)
                     {
                        bet = pC.howMuch(bet, amounts[2]);
                        if(bet >= pC.getMoney())
                        {
                           actionC.setText("All in!");
                           potValue += pC.getMoney();
                           moneyPot[2] = (int)pC.getMoney();
                           bet = (int)pC.getMoney();
                           pC.setMoney(0);
                           allIn = true;
                        }
                        else
                        {
                           actionC.setText("Bet " + bet);
                           pC.setMoney(pC.getMoney() - bet + moneyPot[2]);
                           potValue += bet - moneyPot[2];
                           moneyPot[2] = bet;
                           counter[3] = 1;
                        }
                        moneyC.setText(money.format(pC.getMoney()));
                        pot.setText("Pot: " + money.format(potValue));
                     }
                     else if(action == 4)
                     {
                     
                        if(allIn == true)
                        {
                           counter[5] += 1;
                           actionC.setText("Call " + bet);
                           counter[3] += 1;
                           pC.setMoney(pC.getMoney() - bet + moneyPot[2]);
                           potValue += bet - moneyPot[2];
                           moneyPot[2] = bet;
                        } 
                        else
                        {
                           int temp = bet;
                           bet = pC.howMuch(bet, amounts[1]);
                           if(bet - moneyPot[2] >= pC.getMoney())
                           {
                              actionC.setText("All in!");
                              potValue += pC.getMoney();
                              moneyPot[2] += (int)pC.getMoney();
                              bet += (int)pC.getMoney();
                              pC.setMoney(0);
                              allIn = true;
                              counter[5] = 1;
                           }
                           else
                           {
                              actionC.setText("Raise " + bet);
                              pC.setMoney(pC.getMoney() - bet + moneyPot[2]);
                              potValue += bet - moneyPot[2];
                              amounts[1] = bet - temp;
                              moneyPot[2] = bet;
                              counter[3] = 1;
                           }
                           
                        }
                        moneyC.setText(money.format(pC.getMoney()));
                        pot.setText("Pot: " + money.format(potValue));
                     
                     }
                     else
                     {
                        actionC.setText("All In!");
                        potValue += pC.getMoney();
                        moneyPot[2] += (int)pC.getMoney();
                        pC.setMoney(0);
                        moneyC.setText(money.format(pC.getMoney()));
                        pot.setText("Pot: " + money.format(potValue));
                     }
                     actionC.setVisible(true);
                     if(allIn == false && pC.getMoney() == 0)
                     {
                        allIn = true;
                        counter[5] = 1;
                        counter[3] = 1;
                        if(moneyPot[0] >= moneyPot[2] || p5.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                        if(moneyPot[1] >= moneyPot[2] || pB.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                        if(moneyPot[3] >= moneyPot[2] || pD.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                        if(moneyPot[4] >= moneyPot[2] || pE.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                     }
                     else if(pC.getMoney() == 0)
                     {
                        counter[5] += 1;
                        counter[3] += 1;
                     }
                  }
                  else
                  {
                     counter[3] += 1;  
                  }
                  ((Timer)e.getSource()).setActionCommand("TakeTurnD"); 
               }  
               else if(ev.compareTo("TakeTurnD") == 0)//same as players B and C
               { 
                  if(pD.getStatus() == 2)
                  {
                     try{ Thread.sleep(2000); } 
                     catch (Exception ex){}
                     description.setText("Betting");
                     action = pD.getAction(counter[2] + 1, bet);
                     nameA.setForeground(Color.WHITE);
                     nameB.setForeground(Color.WHITE);
                     nameC.setForeground(Color.WHITE);
                     nameD.setForeground(gold);
                     nameE.setForeground(Color.WHITE);
                     if(action == 0)
                     {
                        cardD1.setVisible(false);
                        cardD2.setVisible(false);
                        actionD.setText("Fold");
                        pD.setStatus(1);
                        counter[3] += 1;  
                        counter[4] += 1;
                        if(allIn == true)
                        {
                           counter[5] += 1;
                        } 
                     }
                     else if(action == 1)
                     {
                        actionD.setText("Check");
                        counter[3] += 1;  
                     }
                     else if(action == 2)
                     {
                        actionD.setText("Call " + bet);
                        pD.setMoney(pD.getMoney() - bet + moneyPot[3]);
                        moneyD.setText(money.format(pD.getMoney()));
                        potValue += bet - moneyPot[3];
                        moneyPot[3] = bet;
                        pot.setText("Pot: " + money.format(potValue));
                        counter[3] += 1;
                        if(allIn == true)
                        {
                           counter[5] += 1;
                        }   
                     }
                     else if(action == 3)
                     {
                        bet = pD.howMuch(bet, amounts[1]);
                        if(bet >= pD.getMoney())
                        {
                           actionD.setText("All in!");
                           potValue += pD.getMoney();
                           moneyPot[3] = (int)pD.getMoney();
                           bet = (int)pD.getMoney();
                           pD.setMoney(0);
                           allIn = true;
                        }
                        else
                        {
                           actionD.setText("Bet " + bet);
                           pD.setMoney(pD.getMoney() - bet + moneyPot[3]);
                           potValue += bet - moneyPot[3];
                           moneyPot[3] = bet;
                           counter[3] = 1;
                        }
                        moneyD.setText(money.format(pD.getMoney()));
                        pot.setText("Pot: " + money.format(potValue));
                     }
                     else if(action == 4)
                     {
                        if(allIn == true)
                        {
                           counter[5] += 1;
                           actionD.setText("Call " + bet);
                           counter[3] += 1;
                           pD.setMoney(pD.getMoney() - bet + moneyPot[3]);
                           potValue += bet - moneyPot[3];
                           moneyPot[3] = bet;
                        } 
                        else
                        {
                           int temp = bet;
                           bet = pD.howMuch(bet, amounts[1]);
                           if(bet - moneyPot[3] >= pD.getMoney())
                           {
                              actionD.setText("All in!");
                              potValue += pD.getMoney();
                              moneyPot[3] += (int)pD.getMoney();
                              bet += (int)pD.getMoney();
                              pD.setMoney(0);
                              allIn = true;
                              counter[5] = 1;
                           }
                           else
                           {
                              actionD.setText("Raise " + bet);
                              pD.setMoney(pD.getMoney() - bet + moneyPot[3]);
                              potValue += bet - moneyPot[3];
                              amounts[1] = bet - temp;
                              moneyPot[3] = bet;
                              counter[3] = 1;
                           }
                           
                        }
                        moneyD.setText(money.format(pD.getMoney()));
                        pot.setText("Pot: " + money.format(potValue));
                     }
                     else
                     {
                        actionD.setText("All In!");
                        potValue += pD.getMoney();
                        moneyPot[3] += (int)pD.getMoney();
                        pD.setMoney(0);
                        moneyD.setText(money.format(pD.getMoney()));
                        pot.setText("Pot: " + money.format(potValue));
                     }
                     actionD.setVisible(true);
                     if(allIn == false && pD.getMoney() == 0)
                     {
                        allIn = true;
                        counter[5] = 1;
                        counter[3] = 1;
                        if(moneyPot[0] >= moneyPot[3] || p5.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                        if(moneyPot[2] >= moneyPot[3] || pC.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                        if(moneyPot[1] >= moneyPot[3] || pB.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                        if(moneyPot[4] >= moneyPot[3] || pE.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                     }
                     else if(pD.getMoney() == 0)
                     {
                        counter[5] += 1;
                        counter[3] += 1;
                     }
                  }
                  else
                  {
                     counter[3] += 1;  
                  }
                  ((Timer)e.getSource()).setActionCommand("TakeTurnE");
                  
               }
               else if(ev.compareTo("TakeTurnE") == 0)//same as players B, C, and D
               {
                  if(pE.getStatus() == 2)
                  {
                     try{ Thread.sleep(2000); } 
                     catch (Exception ex){}
                     description.setText("Betting");
                     action = pE.getAction(counter[2] + 1, bet);
                     nameA.setForeground(Color.WHITE);
                     nameB.setForeground(Color.WHITE);
                     nameC.setForeground(Color.WHITE);
                     nameD.setForeground(Color.WHITE);
                     nameE.setForeground(gold);
                     if(action == 0)
                     {
                        cardE1.setVisible(false);
                        cardE2.setVisible(false);
                        actionE.setText("Fold");
                        pE.setStatus(1);
                        counter[3] += 1;
                        counter[4] += 1;
                        if(allIn == true)
                        {
                           counter[5] += 1;
                        }   
                     }
                     else if(action == 1)
                     {
                        actionE.setText("Check");
                        counter[3] += 1;  
                     }
                     else if(action == 2)
                     {
                        actionE.setText("Call " + bet);
                        pE.setMoney(pE.getMoney() - bet + moneyPot[4]);
                        moneyE.setText(money.format(pE.getMoney()));
                        potValue += bet - moneyPot[4];
                        moneyPot[4] = bet;
                        pot.setText("Pot: " + money.format(potValue));
                        counter[3] += 1;
                        if(allIn == true)
                        {
                           counter[5] += 1;
                        }   
                     }
                     else if(action == 3)
                     {
                        bet = pE.howMuch(bet, amounts[1]);
                        if(bet >= pE.getMoney())
                        {
                           actionE.setText("All in!");
                           potValue += pE.getMoney();
                           moneyPot[4] = (int)pE.getMoney();
                           bet = (int)pE.getMoney();
                           pE.setMoney(0);
                           allIn = true;
                        }
                        else
                        {
                           actionE.setText("Bet " + bet);
                           pE.setMoney(pE.getMoney() - bet + moneyPot[4]);
                           potValue += bet - moneyPot[4];
                           moneyPot[4] = bet;
                           counter[3] = 1;
                        }
                        moneyE.setText(money.format(pE.getMoney()));
                        pot.setText("Pot: " + money.format(potValue));
                     }
                     else if(action == 4)
                     {
                     
                        if(allIn == true)
                        {
                           counter[5] += 1;
                           actionE.setText("Call " + bet);
                           counter[3] += 1;
                           pE.setMoney(pE.getMoney() - bet + moneyPot[4]);
                           potValue += bet - moneyPot[4];
                           moneyPot[4] = bet;
                        } 
                        else
                        {
                           int temp = bet;
                           bet = pE.howMuch(bet, amounts[1]);
                           if(bet - moneyPot[4] >= pE.getMoney())
                           {
                              actionE.setText("All in!");
                              potValue += pE.getMoney();
                              moneyPot[4] += (int)pE.getMoney();
                              bet += (int)pE.getMoney();
                              pE.setMoney(0);
                              allIn = true;
                              counter[5] = 1;
                           }
                           else
                           {
                              actionE.setText("Raise " + bet);
                              pE.setMoney(pE.getMoney() - bet + moneyPot[4]);
                              potValue += bet - moneyPot[4];
                              amounts[1] = bet - temp;
                              moneyPot[4] = bet;
                              counter[3] = 1;
                           }
                           
                        }
                        moneyE.setText(money.format(pE.getMoney()));
                        pot.setText("Pot: " + money.format(potValue));
                     
                     }
                     else
                     {
                        actionE.setText("All In!");
                        potValue += pE.getMoney();
                        moneyPot[4] += (int)pE.getMoney();
                        pE.setMoney(0);
                        moneyE.setText(money.format(pE.getMoney()));
                        pot.setText("Pot: " + money.format(potValue));
                     }
                     actionE.setVisible(true);
                     if(allIn == false && pE.getMoney() == 0)
                     {
                        allIn = true;
                        counter[5] = 1;
                        counter[3] = 1;
                        if(moneyPot[0] >= moneyPot[4] || p5.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                        if(moneyPot[2] >= moneyPot[4] || pC.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                        if(moneyPot[3] >= moneyPot[4] || pD.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                        if(moneyPot[1] >= moneyPot[4] || pB.getStatus() != 2)
                        {
                           counter[5] += 1;
                        }
                     }
                     else if(pE.getMoney() == 0)
                     {
                        counter[5] += 1;
                        counter[3] += 1;
                     }
                  }
                  else
                  {
                     counter[3] += 1;
                  } 
                  ((Timer)e.getSource()).setActionCommand("Prepare for A");//it's user's turn after 
               }
               else if(ev.compareTo("Prepare for A") == 0)
               {
                  if(p5.getStatus() == 2)//if the user folded, it skips over them and goes right back to player B
                  {
                     try{ Thread.sleep(2000); } 
                     catch(Exception error) {}
                     description.setText("Betting");
                     nameA.setForeground(gold);
                     nameB.setForeground(Color.WHITE);
                     nameC.setForeground(Color.WHITE);
                     nameD.setForeground(Color.WHITE);
                     nameE.setForeground(Color.WHITE);
                     if(bet != 0)
                     {
                        call.setText("Call " + bet);
                        call.setActionCommand("Call");
                        raise.setText("<html>Raise to<br>" + (amounts[1] + bet) + "</html>");//raise to minimum raise amount
                        raise.setActionCommand("Raise");
                        amounts[2] = amounts[1] + bet;//stores user's raise/bet amount
                        amounts[3] = amounts[2];
                        if(amounts[1] == 0)//if this is a first raise in this betting round
                        {
                           raise.setText("<html>Raise to<br>" + (bet + BIG_BLIND) + "</html>");
                           amounts[2] = bet + BIG_BLIND;
                        }
                     }
                     else//if no has bet yet
                     {
                        call.setText("Check");
                        call.setActionCommand("Check");
                        raise.setText("<html>Bet<br>" + BIG_BLIND + "</html>");
                        raise.setActionCommand("Bet");
                        amounts[2] = BIG_BLIND;
                        amounts[3] = amounts[2];
                     }
                     call.setVisible(true);
                     fold.setVisible(true);
                     raise.setVisible(true);
                     plus.setVisible(true);//minus will become visible after plus is pressed
                     if(amounts[2] - moneyPot[0] >= p5.getMoney())
                     {
                        raise.setText("All in!");//user can raise/bet to go all in but not go more
                        plus.setVisible(false);
                     }
                     if(bet - moneyPot[0] >= p5.getMoney())
                     {
                        call.setText("All in!");//user can call to go all in
                        raise.setVisible(false);
                        plus.setVisible(false);
                     }
                     if(allIn == true)
                     {
                        raise.setVisible(false);//user cannot raise if someone else went all in
                        plus.setVisible(false);
                        minus.setVisible(false);
                     }
                     ((Timer)e.getSource()).stop();//waits for user event
                  }
                  else
                  {
                     counter[3] += 1;
                  }
                  ((Timer)e.getSource()).setActionCommand("TakeTurnB");   
               }
                  
               if(counter[3] == 5)//if all players have called/checked/folded
               {
                  currentTurn = ((Timer)e.getSource()).getActionCommand();
                  ((Timer)e.getSource()).setActionCommand("Deal");
               }
               if(counter[5] == 5 && counter[2] <= 3)//if all players have called/folded after an all in
               {
                  ((Timer)e.getSource()).setActionCommand("Deal");
               }
               if(counter[4] + deadPlayers == 4)//if only one player remains
               {
                  ((Timer)e.getSource()).setActionCommand("Default Winner");
               }
               amounts[0] = bet;//updating amounts        
            }
         };
      //timer has 0 seconds between iterations (specified time is indicated in each if statement)
      //actionPerformed method inside the timer nested class will continue to iterate until it is ready for more user input
      //inside the method, the actionCommand will update in accordance with game progression
      myTimer = new Timer(0, myLis);
      
      if(eventName.compareTo("start game") == 0)//prepares GUI to start the game
      {
         showCard1.setVisible(false); showCard2.setVisible(false); showCard3.setVisible(false); showCard4.setVisible(false); showCard5.setVisible(false);
         showCard6.setVisible(false); showCard7.setVisible(false); showCard8.setVisible(false); showCard9.setVisible(false); showCard10.setVisible(false);
         showCard11.setVisible(false); showCard12.setVisible(false); showCard13.setVisible(false); showCard14.setVisible(false); showCard15.setVisible(false);
         showCard16.setVisible(false); showCard17.setVisible(false); showCard18.setVisible(false); showCard19.setVisible(false); showCard20.setVisible(false);
         subPane1.setVisible(true);//contain player info hidden by show cards
         subPane2.setVisible(true);
         subPane6.setVisible(true);
         subPane7.setVisible(true);
         p5.setName(userName.getText());//setting user's name
         if(p5.getName().length() > 15)//changing font of JLabel (if name is too long it will mess up layout)
         {
            nameA.setFont(new Font("Arial", Font.BOLD, 12));
         }
         else if(p5.getName().length() > 7)
         {
            nameA.setFont(new Font("Arial", Font.BOLD, 18));
         }
         nameA.setText(p5.getName());//all player stats, except actions are visible 
         description.setText("Round 1/5");//player can begin first round out of 5
         startRound.setVisible(true); 
         startRound.setText("Start Round");
         startRound.setActionCommand("start round");
         userName.setVisible(false);
         nameA.setVisible(true);
         moneyA.setVisible(true);
         nameB.setVisible(true);
         moneyB.setVisible(true);
         nameC.setVisible(true);
         moneyC.setVisible(true);
         nameD.setVisible(true);
         moneyD.setVisible(true);
         nameE.setVisible(true);
         moneyE.setVisible(true);
      }
      else if(eventName.compareTo("name") == 0)
      {
         description.setText("Enter your name");//allows user to enter name
         startRound.setVisible(false);
         userName.setVisible(true);
      }
      else if(eventName.compareTo("start round") == 0)//starts round
      {
         myTimer.setActionCommand("Small Blind");//blinds are the first event in any round
         startRound.setVisible(false);
         myDeck.refill();//refills and shuffles deck
         myDeck.shuffle();
         description.setText("The Blinds");
         pot.setVisible(true);
         myTimer.start();  
      }
      else if(eventName.compareTo("plus") == 0)//user wants to increase raise/bet amount
      {
         int max = (int)p5.getMoney() + moneyPot[0];
         minus.setVisible(true);
         amounts[2] += 100;//increases by 100
         if(amounts[0] != 0 && amounts[2] < max)
         {
            raise.setText("<html>Raise to<br>" + amounts[2] + "</html>"); 
         }
         else if(amounts[2] < max)
         {  
            raise.setText("<html>Bet<br>" + amounts[2] + "</html>");
         }
         else
         {
            raise.setText("All in!");
            plus.setVisible(false);//if raise/bet amount is more than user currently has
         }
      }
      else if(eventName.compareTo("minus") == 0)//user wants to decrease raise/bet amount
      {
         plus.setVisible(true);
         amounts[2] -= 100;//decreases by 100
         if(amounts[0] != 0)
         {
            raise.setText("<html>Raise to<br>" + amounts[2] + "</html>"); 
         }
         else
         {  
            raise.setText("<html>Bet<br>" + amounts[2] + "</html>");
         }
         if(amounts[2] <= amounts[3])
         {
            minus.setVisible(false);//will become invisible if raise/bet is at the minimum
         }  
      }
      else if(eventName.compareTo("Fold") == 0)//user fold
      {
         cardA1.setVisible(false);
         cardA2.setVisible(false);
         p5.setStatus(1);//updating status
         counter[4] += 1;//updating folded players
         actionA.setText("Fold");//updating action
         actionA.setVisible(true);
         fold.setVisible(false);//all buttons are no longer visible
         call.setVisible(false);
         raise.setVisible(false);
         plus.setVisible(false);
         minus.setVisible(false);
         counter[3] += 1;
         myTimer.setActionCommand("TakeTurnB");
         if(allIn == true)
         {
            counter[5] += 1;
         }
         if(counter[3] == 5)//if gameplay is ready for the deal
         {
            currentTurn = myTimer.getActionCommand();
            myTimer.setActionCommand("Deal");
         }
         if(counter[4] + deadPlayers == 4)//if only one player remains
         {
            myTimer.setActionCommand("Default Winner");
         }
         myTimer.start();//starts timer
      }
      else if(eventName.compareTo("Call") == 0)//user calls
      {
         fold.setVisible(false);//buttons are no longer visible
         call.setVisible(false);
         raise.setVisible(false);
         plus.setVisible(false);
         minus.setVisible(false);
         myTimer.setActionCommand("TakeTurnB");
         if(allIn == true)
         {
            counter[5] += 1;
         }
         if(amounts[0] - moneyPot[0] >= p5.getMoney())//if call amount is greater than what user has
         {
            actionA.setText("All In!");
            actionA.setVisible(true);
            potValue += p5.getMoney();
            moneyPot[0] += (int)p5.getMoney();
            p5.setMoney(0);
            moneyA.setText(money.format(p5.getMoney()));
            pot.setText("Pot: " + money.format(potValue));
            counter[3] = 1;
            flag = 0;//setting flag to 0 (for all in if statement at start of nested class)
         }
         else
         {
            actionA.setText("Call " + amounts[0]);
            actionA.setVisible(true);
            p5.setMoney(p5.getMoney() - amounts[0] + moneyPot[0]);
            moneyA.setText(money.format(p5.getMoney()));
            potValue += amounts[0] - moneyPot[0];
            moneyPot[0] = amounts[0];
            pot.setText("Pot: " + money.format(potValue));
            counter[3] += 1;
         }
         if(counter[3] == 5)
         {
            currentTurn = myTimer.getActionCommand();
            myTimer.setActionCommand("Deal");
         }
         myTimer.start();
      }
      else if(eventName.compareTo("Check") == 0)//user checks
      {
         actionA.setText("Check");
         actionA.setVisible(true);
         counter[3] += 1;
         fold.setVisible(false);
         call.setVisible(false);
         raise.setVisible(false);
         plus.setVisible(false);
         minus.setVisible(false);
         myTimer.setActionCommand("TakeTurnB");
         if(counter[3] == 5)
         {
            currentTurn = myTimer.getActionCommand();
            myTimer.setActionCommand("Deal");
         }
         myTimer.start();
      }
      else if(eventName.compareTo("Raise") == 0)//user raises
      {
         int temp = amounts[0];//equal to previous bet
         if(amounts[2] - moneyPot[0] >= p5.getMoney())//if user raises more than they currently have
         {
            actionA.setText("All In!");
            actionA.setVisible(true);
            potValue += p5.getMoney();
            moneyPot[0] += (int)p5.getMoney();//setting money = to 0
            amounts[0] += (int)p5.getMoney();
            p5.setMoney(0);
            moneyA.setText(money.format(p5.getMoney()));
            pot.setText("Pot: " + money.format(potValue));
            counter[3] = 1;
            flag = 0;
         }
         else
         {
            amounts[0] = amounts[2];
            amounts[1] = amounts[0] - temp;
            actionA.setText("Raise " + amounts[0]);
            actionA.setVisible(true);
            p5.setMoney(p5.getMoney() - amounts[0] + moneyPot[0]);
            moneyA.setText(money.format(p5.getMoney()));
            potValue += amounts[0] - moneyPot[0];
            moneyPot[0] = amounts[0];
            pot.setText("Pot: " + money.format(potValue));
            counter[3] = 1;
         }
         fold.setVisible(false);
         call.setVisible(false);
         raise.setVisible(false);
         plus.setVisible(false);
         minus.setVisible(false);
         myTimer.setActionCommand("TakeTurnB");
         myTimer.start(); 
      }
      else if(eventName.compareTo("Bet") == 0)
      {
         if(amounts[2] - moneyPot[0] >= p5.getMoney())//if user bets more than they currently have
         {
            actionA.setText("All In!");
            actionA.setVisible(true);
            potValue += p5.getMoney();
            moneyPot[0] = (int)p5.getMoney();
            amounts[0] = (int)p5.getMoney();
            p5.setMoney(0);
            moneyA.setText(money.format(p5.getMoney()));
            pot.setText("Pot: " + money.format(potValue));
            counter[3] = 1;  
            flag = 0;
         }
         else
         {
            amounts[0] = amounts[2];
            actionA.setText("Bet " + amounts[0]);
            actionA.setVisible(true);
            p5.setMoney(p5.getMoney() - amounts[0] + moneyPot[0]);
            moneyA.setText(money.format(p5.getMoney()));
            potValue += amounts[0] - moneyPot[0];
            moneyPot[0] = amounts[0];
            pot.setText("Pot: " + money.format(potValue));
            counter[3] = 1;
         }
         fold.setVisible(false);
         call.setVisible(false);
         raise.setVisible(false);
         plus.setVisible(false);
         minus.setVisible(false);
         myTimer.setActionCommand("TakeTurnB");
         myTimer.start();
      }
      else//display the highscores list
      {
         JFrame scoresFrame = new JFrame("HighScores");//Creates new JFrame to display the highscores
         scoresFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         startRound.setVisible(false);
         File highScores = new File("HighScores.txt");//Creates object that refers to the file containing the highscores
         FileReader in;//Declaring objects necessary for file reading
         BufferedReader readFile;
         FileWriter out;//Declaring objects necessary for file writing
         BufferedWriter writeFile;
         String lineOfText;//Used in reading the file
          
         NumberFormat money = NumberFormat.getCurrencyInstance();
         int places[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};//array that stores the placement of each score; necessary in displaying a tie
         int ties = 0;//used to keep the same placement for tied scores
      
         String[] names = new String[16];//String array used for storing and sorting high score names; text file stores 15 scores but 16 elements are necessary to include the new user name
         double[] scores = new double[16];//Double array used for storing and sorting high score scores
      
         try
         {
            if(highScores.exists())//If the highscores file cannot be found, program will create a new list
            {
               in = new FileReader(highScores);//Opens reading streams
               readFile = new BufferedReader(in);
               for(int i = 0; i < 15; i++)//Reads a maximum of 15 names and scores
               {
                  lineOfText = readFile.readLine();//reads a name
                  names[i] = lineOfText;
                  if(lineOfText != null)//If less than 15 scores are present, the program will need to stop reading at a point
                  {
                     lineOfText = readFile.readLine();//reads a score
                     scores[i] = Double.parseDouble(lineOfText);
                  }
               
               }
               readFile.close();//Closes reading streams
               in.close();
            }
            else
            {
               highScores.createNewFile();//creates a new highscore file
            }
         } 
         catch(FileNotFoundException e) {}//catches possible exceptions
         catch(IOException e) {}
      
         if(p5.getMoney() >= scores[14])
         {
            scores[15] = p5.getMoney();//declares last element as user's score
            names[15] = p5.getName();
         }
         if(scores[14] == scores[15])//if scores 15 ties scores 14, scores 15 is placed in the highscores list
         {
            scores[14] = 0;
         } 
       
         for(int a = 0; a < 15; a++)//Sorts highscores in descending order using bubble sort
         {
            for(int b = 0; b < 15; b++)
            {
               if(scores[b] < scores[b + 1])
               {
                  double temp = scores[b];
                  scores[b] = scores[b + 1];
                  scores[b + 1] = temp;
                  String tempS = names[b];//Since the names are associated with the scores, if a score switches its respective name must also switch
                  names[b] = names[b + 1];
                  names[b + 1] = tempS;
               }  
               else if(a == 14 && scores[b] == scores[b + 1])//last iteration of loop (everything is essentially in order)
               {
                  ties += 1;
                  places[b + 1] = places[b];//the next placement is the same as the previous in a tie
               }
            }
         }
      
      //Formulates JTable with all highscores in descending order of score
         String[][] data = {{String.valueOf(places[0]),names[0], money.format(scores[0])},{String.valueOf(places[1]),names[1], money.format(scores[1])},{String.valueOf(places[2]),names[2], money.format(scores[2])},{String.valueOf(places[3]),names[3], money.format(scores[3])},{String.valueOf(places[4]),names[4], money.format(scores[4])},
                        {String.valueOf(places[5]),names[5], money.format(scores[5])},{String.valueOf(places[6]),names[6], money.format(scores[6])},{String.valueOf(places[7]),names[7], money.format(scores[7])},{String.valueOf(places[8]),names[8], money.format(scores[8])},{String.valueOf(places[9]),names[9], money.format(scores[9])},
                        {String.valueOf(places[10]),names[10], money.format(scores[10])},{String.valueOf(places[11]),names[11], money.format(scores[11])},{String.valueOf(places[12]),names[12], money.format(scores[12])},{String.valueOf(places[13]),names[13], money.format(scores[13])},{String.valueOf(places[14]),names[14], money.format(scores[14])}};
         String[] column = {"Ranking", "Name", "Winnings"};
         JTable t = new JTable(data,column);
         t.setBounds(0,0,750,450);//Setting boundaries of the table
         t.setRowHeight(30);//Setting the row height
         t.setFont(new Font("Arial", Font.BOLD, 21));//Setting the table font
         t.setBackground(darkGreen);//Setting the background colour
         t.setForeground(Color.WHITE);
         JTableHeader h = t.getTableHeader();//Allows me to modify the header colour, size, font, etc.
         h.setPreferredSize(new Dimension(250, 30));
         h.setBackground(Color.BLACK);//Different colours to show that it is a header
         h.setForeground(Color.WHITE);
         h.setFont(new Font("Arial", Font.BOLD, 21));
         JScrollPane sp = new JScrollPane(t);
         scoresFrame.add(sp);
         scoresFrame.setSize(800,500);//Sets size of JFrame
         scoresFrame.setVisible(true);//Makes it visible   
         
         try
         {
            out = new FileWriter(highScores);//open writing streams
            writeFile = new BufferedWriter(out);
            for(int i = 0; i < 15; i++)//Writes top 15 names and scores in high scores file
            {
               if(scores[i] != 0)//Will stop writing if less than 15 names are saved
               {
                  writeFile.write(names[i]);//writes name
                  writeFile.newLine();
                  writeFile.write(String.valueOf(scores[i]));//writesscore
                  writeFile.newLine();
               }
            }
            writeFile.close();//Close writing streams
            out.close();
         } catch(IOException e) {}//Catch possible exception 
      } 
   }
   //Runs poker game
   //pre: none
   //post: full game of texas hold em is run 
   private static void runGUI()
   {
      JFrame.setDefaultLookAndFeelDecorated(false); //sets look and feel
      Poker game = new Poker();//creates a new poker game
   }
	
   public static void main(String[] args)
   {//main method 
      javax.swing.SwingUtilities.invokeLater(
         new Runnable()
         {//event dispatching thread
            public void run()
            {
               runGUI();
            }
         });
   }
}

