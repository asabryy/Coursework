import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Random;
import java.util.Scanner;

public class Crazy8Game{
 public static boolean reversed = false;
 public static Random rand = new Random();
 
 public static void main(String[] args){
  //will use this to ocunt cards later
  int k = 0;
  Myplayer me = null;
  int gamesPlayed = 0;
  int maxPoints = 0;
  String maybepoints = "no";
  
  
  /* create the deck */
  Card[] deck = new Card[52];
  int index = 0;
  for(int r=2; r<=14; r+=1){
   for(int s=0; s<4; s+=1){
    deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
   }
  }
  
  /* shuffle the deck */
  Random rnd = new Random();
  Card swap;
  for(int i = deck.length-1; i>=0; i=i-1){
   int pos = rnd.nextInt(i+1);
   swap = deck[pos];
   deck[pos] = deck[i];
   deck[i] = swap;
  }  
  
  
  //Ask what kind of game"
  System.out.println("What kind of game would you like to play? (single or multi)");
  Scanner sc = new Scanner(System.in);
  String input = sc.nextLine();
  Player[] players;
  
  while(!input.equals("single") && !input.equals("multi")){
      System.out.println("Please input single or multi");
      input = sc.nextLine();
  }
  
  //this establishes if the game is single player
  if(input.equals("single")){
    String secondinput;
    players =  new Player[1];
    
    me = new Myplayer(Arrays.copyOfRange(deck, 0, 7));
   
    //now choose which behaviour you'd like to face
    System.out.println("Who would you like to face?");
    secondinput = sc.nextLine();
    if(secondinput.equals("eights")){
    
      players[0] = new MindTheEights(Arrays.copyOfRange(deck, 7, 14));
      
    } else if(secondinput.equals("hamper")){
      
      players[0] = new HamperLeader(Arrays.copyOfRange(deck, 7, 14));
      
    } else if(secondinput.equals("extra")){
      
      players[0] = new ExtraCards(Arrays.copyOfRange(deck, 7, 14));
      
    } else if(secondinput.equals("bad")){
      
      players[0] = new BadPlayer(Arrays.copyOfRange(deck, 7, 14));
    
    } else if(secondinput.equals("discard")){
    
      players[0] = new DiscardHighPoints(Arrays.copyOfRange(deck, 7, 14));
      
    } else {
    
      players[0] = new RandomPlayer(Arrays.copyOfRange(deck, 7, 14));

    }
   System.out.println("Your hand is: "  +  Arrays.toString(Arrays.copyOfRange(deck, 0, 7))); 
   System.out.println("Computer Player's hand is " +  Arrays.toString(Arrays.copyOfRange(deck, 7, 14))); 
  } else {
    //this sets up the multiplayer game
    System.out.println("Would you like 2, 3 or 4 players?");
    String playerNum = sc.nextLine();
    
    //makes sure you input correctly
    while(!playerNum.equals("3") && !playerNum.equals("4") && !playerNum.equals("2")){
      System.out.println("Please input 2, 3 or 4");
      playerNum = sc.nextLine();
    }
    
    System.out.println("Would you like to make it a game for points?");
    maybepoints = sc.nextLine();
    if(maybepoints.equals("yes")){
      System.out.println("How many points to win?");
      String maxpointsanswer = sc.nextLine();
      maxPoints = Integer.parseInt(maxpointsanswer);
    }
    
    int playerNumint = Integer.parseInt(playerNum);
    players =  new Player[playerNumint];
    
    //This creates the players in the game, asks what kind you'd like up to four times
    for(int i = 0; i < playerNumint; i++){
      
      String answer = "";
      while( !(answer.equals("random")) && !(answer.equals("hamper"))  && !(answer.equals("extra")) && !(answer.equals("eights")) && !(answer.equals("bad"))
           && !(answer.equals("discard"))){
        System.out.println("Please input a correct type of player");
        answer = sc.nextLine();
      }
      
      if(answer.equals("random")){
        players[i] = new RandomPlayer( Arrays.copyOfRange(deck, 0 + k, 5 + k) );
       
      } else if(answer.equals("hamper")){
        players[i] = new HamperLeader( Arrays.copyOfRange(deck, 0 + k, 5 + k) );

      } else if(answer.equals("extra")){
        players[i] = new ExtraCards( Arrays.copyOfRange(deck, 0 + k, 5 + k) );

      } else if(answer.equals("eights")){
        players[i] = new MindTheEights( Arrays.copyOfRange(deck, 0 + k, 5 + k) );

      } else if(answer.equals("bad")){
        players[i] = new BadPlayer( Arrays.copyOfRange(deck, 0 + k, 5 + k) );

      }else if(answer.equals("discard")){
        players[i] = new DiscardHighPoints(Arrays.copyOfRange(deck, 0 + k,5 + k));
      
      }
      k = k + 5;
    }
     k = 0;
     for(int i = 0; i < players.length;i++){
       System.out.println("Player " + i + "s hand: " +  Arrays.toString(Arrays.copyOfRange(deck, 0 + k, 5 + k))); 
       k = k+ 5;
     }
  }
  

  
  /* discard and draw piles */
  DiscardPile discardPile = new DiscardPile();
  Stack<Card> drawPile = new Stack<Card>();
  for(int i=15; i<deck.length; i++){
   drawPile.push(deck[i]);
  }
  
  //this just starts the deck at the right place depending on single player or multi
  if(input.equals("single")){
    System.out.println("draw pile is : " + Arrays.toString( Arrays.copyOfRange(deck, 10, deck.length) ));
  }else {
    System.out.println("draw pile is : " + Arrays.toString( Arrays.copyOfRange(deck, k, deck.length) ));
  }
  System.out.println();
     
  deck = null;  
  boolean win = false;
  
  //play the appropriate kind of game
  if(input.equals("single")){
   
    singlePlayerGame(me, players[0], discardPile, drawPile);
    
  }else{
    if(maybepoints.equals("no")){
  
  
      int player = -1;    // start game play with player 0
      boolean draw = false;
      ArrayList<Player> people = new ArrayList<Player>(Arrays.asList(players));
      discardPile.add( drawPile.pop());
      
      //this is the multiplayer game
      while( !win ){
        
        if(reversed == false){
          player = (player + 1) % players.length;
        } else {
          player = (player - 1);
          if(player < 0)
            player = players.length-1;
        }
        System.out.println("It's player " + player + "'s turn!");
        System.out.println("Player " + player + "'s hand is: " + players[player].getHand());
        System.out.println("The discardpile shows : " + discardPile.top() );
        
        
        win = people.get(player).play(discardPile, drawPile, people);
        activateCards(discardPile.top(),player, people,drawPile,discardPile);
        
        //to stop infinite loops, after every win check, check to see if theres a draw 
        if(isItADraw(drawPile,players.length, players, discardPile) == true){
          System.out.println("The game is a draw! No one can win.");
          break;
        }
        

        System.out.println("Player " + player + " has played :" + discardPile.top() );
        System.out.println();
      }
      System.out.println("winner is player " + player);
    } else {
      //calling did points win to see if the designated point goal was reached
      int player = -1; 
      boolean draw = false;
      ArrayList<Player> people = new ArrayList<Player>(Arrays.asList(players));
      discardPile.add( drawPile.pop());
      
      while(didWin(people,maxPoints) == false){
        while(!win){
          if(reversed == false){
            player = (player + 1) % players.length;
          } else {
            player = (player - 1);
            if(player < 0)
              player = players.length-1;
          }
          win = people.get(player).play(discardPile, drawPile, people);
          activateCards(discardPile.top(),player, people,drawPile,discardPile);
          
          if(isItADraw(drawPile,players.length, players, discardPile) == true){
            break;
          }
        }
        winPoints(people.get(player),people);
        for(int j = 0; j < people.size(); j++){
          System.out.println("Player " + j + " has " + people.get(j).getPoints() + " points!");
        }
        System.out.println();
      }
      System.out.println("The game is over, Player " + player + " has won with " + people.get(player).getPoints() + " points!");
    }
  }
 }
 
 public static void activateCards(Card topCard,int currentPlayer, ArrayList<Player> players, Stack<Card> drawPile,DiscardPile discardPile){
   if (topCard.getRank()== 2){
     players.get((currentPlayer + 1) % players.size()).addToHand(2,drawPile);
      if(reversed == false){
         currentPlayer = (currentPlayer + 1) % players.size();
        } else {
         currentPlayer = (currentPlayer - 1);
          if(currentPlayer < 0)
            currentPlayer = players.size()-1;
        }
   }else if (topCard.getRank()== 4){
      if(reversed == false){
          currentPlayer = (currentPlayer + 1) % players.size();
        } else {
          currentPlayer = (currentPlayer - 1);
          if(currentPlayer < 0)
            currentPlayer = players.size()-1;
        }
     
   }else if (topCard.getRank()== 7){
     if (reversed == true){
       reversed = false;
     } else {
       reversed = true;
     }
   }else if (topCard.getRank()== 8){
     discardPile.add(new Card(Card.SUITS[rand.nextInt(3)],"8"));
   }
 }
 
 //this gives the winner the points of all the other players cards
 public static void winPoints(Player winner,ArrayList<Player> players){
   for(int i = 0; i < players.size(); i++){
     if(i != winner.getPnum()){
       for(int j = 0; j < players.get(i).getSizeOfHand();j++){
         if (players.get(i).getActualHand().get(j).getRank() > 10){
           winner.setPoints(10);
         } else if(players.get(i).getActualHand().get(j).getRank() == 8){
           winner.setPoints(50);
         } else if(players.get(i).getActualHand().get(j).getRank() == 7){
           winner.setPoints(20);
         } else if((players.get(i).getActualHand().get(j).getRank() == 2) || (players.get(i).getActualHand().get(j).getRank() == 4)){
           winner.setPoints(25);
         } else{
           winner.setPoints(players.get(i).getActualHand().get(j).getRank());
         }
       }
     }
   }
 }
 
 //this determines if the correct amount of points have been reached at the end of a game
 public static boolean didWin(ArrayList<Player> players,int maxPoints){
   int currpoints = 0;
   for(int i = 0; i < players.size();i++){
     if(currpoints < players.get(i).getPoints()){
       currpoints = players.get(i).getPoints();
     }
   }
   if (currpoints >= maxPoints){
     return true;
   }else{
     return false;
   }
 }
 
 //go through all the players cards when drawpile empty, can they play a card? no? draw.
 public static boolean isItADraw(Stack<Card> drawPile, int numofPlayers,Player[] players,DiscardPile discardPile){
   boolean button = true;
   if(drawPile.empty()){
     for(int i = 0; i < numofPlayers; i++){
       for(int j = 0; j < players[i].getSizeOfHand();j++){
         if( (players[i].hand.get(j).getRank() == 8) || (players[i].hand.get(j).getRank() == discardPile.top().getRank())
              || (players[i].hand.get(j).getSuit().equals(discardPile.top().getSuit()))){
           button = false;
           return button;
         }
       }
     }
   }
   if(drawPile.empty()){
     return button;
   } else {
     return false;
   }
 }
 
 //this plays a single palyer game bewteen the user and a playertype of our choice
 private static void singlePlayerGame(Myplayer me, Player computer, DiscardPile discardPile1, Stack<Card> drawPile1){
  int myWin = 0;
  boolean win = false;
  Scanner sm = new Scanner(System.in);
  discardPile1.add(drawPile1.pop());
  ArrayList<Player> ourPlayers = new ArrayList<Player>();
  ourPlayers.add(me);
  ourPlayers.add(computer);
  
  while(!win){
    
    System.out.println("Your current hand is " + me.getHand());  
    System.out.println("The top of the discard pile shows: " + discardPile1.top());
    System.out.println("What card would you like to play? (starting from 0) or type 'draw' ");
    String cardplay = sm.nextLine();
    int cardPlay;
    
    //if the player wants to draw a card
    if(cardplay.equals("draw")){    
      if(drawPile1.empty()){
        System.out.println("sorry the deck is empty, play a card or pass");
        cardplay = sm.nextLine();
        cardPlay = Integer.parseInt(cardplay);
      }
      while(cardplay.equals("draw")){
        me.hand.add(drawPile1.pop());
        System.out.println("You drew a card! it was : " + me.hand.get(me.getSizeOfHand() - 1).toString());
        System.out.println("Your current hand is " + me.getHand());  
        System.out.println("It is still your turn, please make a move or draw again!");
        cardplay = sm.nextLine();
      }    
    }
    
    cardPlay = Integer.parseInt(cardplay);
      
    //this makes sure you're playing the right card number
    while(cardPlay >= me.getSizeOfHand()){
      System.out.println("Please input a correct number");
      cardplay = sm.nextLine();
      cardPlay = Integer.parseInt(cardplay);
    }
     
    //this makes sure you're playing the right card
    while((me.hand.get(cardPlay).getRank() != 8) && (me.hand.get(cardPlay).getRank() != discardPile1.top().getRank())
         && (!(me.hand.get(cardPlay).getSuit().equals(discardPile1.top().getSuit())))){
      System.out.println("You can't play that card sorry please choose another or draw");
      cardplay = sm.nextLine();
      cardPlay = Integer.parseInt(cardplay);
    }
    
    discardPile1.add(me.hand.remove(cardPlay));
    activateCards(discardPile1.top(),0, ourPlayers,drawPile1,discardPile1);
    System.out.println("You have played " + discardPile1.top() + "!");
  
  if(me.getSizeOfHand() == 0){
    win = true;
    myWin = 1;
    break;
  }
     
     //now the computer makes a move
  System.out.println("It is now the computer's turn");
  System.out.println("The computer's hand is: " + computer.getHand());
  System.out.println("The top of the discard pile shows: " + discardPile1.top());
  win = computer.play(discardPile1, drawPile1, ourPlayers);
  activateCards(discardPile1.top(),1, ourPlayers,drawPile1,discardPile1);
  if( computer.getSizeOfHand() == 0){
    myWin = 2;
  }
  System.out.println("The computer has played " + discardPile1.top());
  System.out.println(); 
 }
  
  //annoucne who won
  if(myWin == 1){
    System.out.println("You have won!");
  } else if(myWin == 2) {
    System.out.println("The computer has won!");
  }
 }
}