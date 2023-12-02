//Comp 1406A, Assignment 2, Problem 1
//Vishnu Balabhaskaran, 101003096, October 4, 2016
//Tic tac toe game that is player vs player or player vs computer. 

import java.util.Scanner;

public class TicTacToe{
  
  
  
  static int TL;                                                //Static ints for each position on the board 
  static int TR;
  static int TM;
  static int MR;
  static int ML;
  static int C;
  static int BR;
  static int BL;
  static int BM;
  
 static Scanner userInput = new Scanner(System.in);            //Scanner to take user input for moves/play again option.
 
 
 //Getter for the player/computer's X or O based on the value of each player, where 1 is associated with X
 //and 2 is associated with O.
 
 public static String getXO(int playerVal){                     
   if (playerVal == 1){                                        
     return "X";                                               
   }
   if (playerVal == 2){
   return "O";
   }
  return " ";
 }
 
 //Getter for the move of the player, asks user for input and if the move is invalid prints out error message.
 //Enclosed in a Do-While loop such that the error message occurs only when the validMove function returns false.
 //Returns the valid input move.
  public static String getMove(){                              
    String move;                                               
    do{                                                       
      System.out.println("Enter your move. ");                  
      move = userInput.nextLine();                             
      if (validMove(move) == false){
        System.out.println("That is not a valid play.");
      }
    } while (validMove(move) == false);
  return move;
 }
 
//Function to set a given position on the board to the value of the player, i.e. 1 or 2/X or O.
//Takes input of String for the play to be made (e.g. tl or top left) and takes an int to set the position 
//to the player's int value.
  
 public static void playMove(String play, int player){              
   if (play.equals("tl") || play.equals("top left")){          
     TL = player;
   }
   if (play.equals("tm") || play.equals("top middle")){
     TM = player;
   }
   if (play.equals("tr") || play.equals("top right")){
     TR = player;
   }
   if (play.equals("ml") || play.equals("middle left")){
     ML = player;
   }
   if (play.equals("c") || play.equals("center")){
     C = player;
   }
   if (play.equals("mr") || play.equals("middle right")){
     MR = player;
   }
   if (play.equals("bl") || play.equals("bottom left")){
     BL = player;
   }
   if (play.equals("bm") || play.equals("bottom middle")){
     BM = player;
   }
   if (play.equals("br") || play.equals("bottom right")){
     BR = player;
   }
 }
 
 //Function to return the position at which the computer makes a move. Moves across each row to search for open 
 //positions.
 
 public static String computerMove(){                          
   if (TL == 0){                                               
     return "top left";
   }
   if (TM == 0){
     return "top middle";
   }
   if (TR == 0){
     return  "top right";
   }
   if (ML == 0){
     return  "middle left";
   }
   if (C == 0){
     return  "center";
   }
   if (MR == 0){
     return  "middle right";
   }
   if (BL == 0){
     return  "bottom left";
   }
   if (BM == 0){
     return  "bottom middle";
   }
   if (BR == 0){
     return  "bottom right";
   }
  return "";
 }
 
 //Returns true if the value of a position is zero, i.e. empty, and if the value of the input parameter move is one of
 //appropriate possible String inputs. Otherwise, returns false.
 
 public static boolean validMove(String move){                                
   if (((move.equals("tl") || move.equals("top left"))) && (TL == 0)){        
     return true;                                                             
   }                                                                          
   if (((move.equals("tm") || move.equals("top middle"))) && (TM == 0)){      
     return true;
   }
   if (((move.equals("tr") || move.equals("top right"))) && (TR == 0)){
     return true;
   }
   if (((move.equals("ml") || move.equals("middle left"))) && (ML == 0)){
     return true;
   }
   if (((move.equals("c") || move.equals("center"))) && (C == 0)){
     return true;
   }
   if (((move.equals("mr") || move.equals("middle right"))) && (MR == 0)){
     return true;
   }
   if (((move.equals("bl") || move.equals("bottom left"))) && (BL == 0)){
     return true;
   }
   if (((move.equals("bm") || move.equals("bottom middle"))) && (BM == 0)){
     return true;
   }
   if (((move.equals("br") || move.equals("bottom right"))) && (BR == 0)){
     return true;
   }
   return false;
 }
  
 //Draws the board. Uses the getXO() to display X or O depending on which player occupies a position.
 
 public static void board(){                                                   
  System.out.println("");                                                      
  System.out.println(" " + getXO(TL) + " | " + getXO(TM) + " | " + getXO(TR)); 
  System.out.println("---+---+---");
  System.out.println(" " + getXO(ML) + " | " + getXO(C) + " | " + getXO(MR));
  System.out.println("---+---+---");
  System.out.println(" " + getXO(BL) + " | " + getXO(BM) + " | " + getXO(BR));
  System.out.println("");
  System.out.println();
  }
 
 //Checks a row or diagonal for all being the same player value, i.e. 1 or 2, and if all three input int parameters 
 //x, y, z equal each other, returns true, otherwise, returns false.
 
 public static boolean checkRow(int x, int y, int z){                         
   if((x == y) & (x == z) & (x != 0)){                                        
     return true;                                                             
   }                                                                          
   return false;                                                              
 }
 
 //Uses the checkRow function to check each possible winning combination, and if one of them is present, returns true, 
 //otherwise returns false.
 public static boolean checkBoard(){                                          
   if(checkRow(TL, TM, TR) == true){                                         
     return true;                                                             
   }                                                                         
   if(checkRow(ML, C, MR) == true){
     return true;
   }
   if(checkRow(BL, BM, BR) == true){
     return true;
   }
   if(checkRow(TL, ML, BL) == true){
     return true;
   }
   if(checkRow(TM, C, BM) == true){
     return true;
   }
   if(checkRow(TR, MR, BR) == true){
     return true;
   }
   if(checkRow(TL, C, BR) == true){
     return true;
   }
   if(checkRow(TR, C, BL) == true){
     return true;
   }
   return false;
 }
  
 public static void main(String[] args){
   
   int strCheck = 1;                                    //Variable used to check if args[0].compareTo("2p") is 0 or not. 
   String playAgain = "";                                     
   String p1Move = "";
   String p2Move = "";
   String computerMove = "";
   boolean gameOver = false;                                  //Variable used to determine if a game is won or drawn.
   
   
   if(args.length == 1){                                      //If one argument is entered but it is not 2p, error 
     strCheck = args[0].compareTo("2p");                      //message is displayed.
     if(strCheck != 0){
       System.out.println("Incorrect command line argument entered.");
     }
   }
    
   if(args.length > 1){                                                         //If more than one argument is entered,
     System.out.println("Incorrect amount of command line arguments entered."); //error message is displayed.
   }
   

   
   if(strCheck == 0){                                               //If the input argument is 2p then the game starts a
                                                                    //player vs player game.
     
     for (int i = 1; i <=9; i++){                  //For loop that occurs 9 times, given that there is a max of 9 turns.
       
       
       p1Move = getMove();                         //Player 1's move is acquired using the getMove() function.
       playMove(p1Move, 1);                        //playMove sets the position to the value of 1 (X).
       board();                                    //Board is displayed.
       if (checkBoard() ==  true){                 //After each move checkBoard() is called to check if there is a win.
         System.out.println("Player 1 wins.");     
         gameOver = true;                          
         System.out.println("Would you like to play again?");
         playAgain = userInput.nextLine();         //Scanner is used to ask player to play again.
         if(playAgain.equalsIgnoreCase("no")){     //If no is input, endds game with appropriate message.
           System.out.println("Thank you for playing");
           break;
         }
         if(playAgain.equalsIgnoreCase("yes")){    //If scanner input is yes, sets the for loop back to 1, and sets each
           i = 1;                                  //position value to 0, i.e. empty.
           TM = TR = TL = MR = ML = MR = C = BL = BR = BM = 0;
           }
       }
           
        p2Move = getMove();                        //Player 2 is the exact same as Player 1, except 2 is used instead of
        playMove(p2Move, 2);                       //1 in the playMove function, i.e. O is placed instead of X.
        board();
       if (checkBoard() ==  true){
         System.out.println("Player 2 wins.");
         gameOver = true;
         System.out.println("Would you like to play again?");
         playAgain = userInput.nextLine();
         if(playAgain.equalsIgnoreCase("no")){
           System.out.println("Thank you for playing");
           break;
         }
         if(playAgain.equalsIgnoreCase("yes")){
           i = 1;
           TM = TR = TL = MR = ML = MR = C = BL = BR = BM = 0;
           }
       }
       
     }
   }
     
   if(args.length == 0){                          //If no arguments are input, then a player vs computer game starts.
     for (int i = 1; i <=9; i++){
       
       
       p1Move = getMove();                        //Player moves are the same as above.
       playMove(p1Move, 1);
       board();
       if (checkBoard() ==  true){
         System.out.println("Player wins.");
         gameOver = true;
         System.out.println("Would you like to play again?");
         playAgain = userInput.nextLine();
         if(playAgain.equalsIgnoreCase("no")){
           System.out.println("Thank you for playing");
           break;
         }
         if(playAgain.equalsIgnoreCase("yes")){
           i = 1;
           TM = TR = TL = MR = ML = MR = C = BL = BR = BM = 0;
           }
         
       }
       
       
       if (i < 9){                               //Computer moves follow the same principles as the player moves,
         computerMove = computerMove();          //except uses the computerMove() function in which it picks the next
                                                 //available position for the computer's move.
         System.out.println("Computer plays at " + computerMove);
         playMove(computerMove, 2);
         board();
         if (checkBoard() == true){
           System.out.println("Computer wins.");
           gameOver = true;
           System.out.println("Would you like to play again?");
           playAgain = userInput.nextLine();
           if(playAgain.equalsIgnoreCase("no")){
             System.out.println("Thank you for playing");
             break;             
           }
           if(playAgain.equalsIgnoreCase("yes")){
             i = 1;
             TM = TR = TL = MR = ML = MR = C = BL = BR = BM = 0;
           }
         }
         i++;
       }
     }
     if (gameOver == false){                                     //If after the for loop ends and gameOver remains false
       System.out.println("Draw.");                              //prints out Draw and asks user if they want to 
       System.out.println("Would you like to play again?");      //play again.
       playAgain = userInput.nextLine();
       if(playAgain.equalsIgnoreCase("no")){
         System.out.println("Thank you for playing");
       }

     }
   }
 }
}
