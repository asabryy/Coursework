import java.util.ArrayList;
import java.util.Stack;

public abstract class Player{
 protected ArrayList<Card> hand;
 
 //helps us identify which playuer number we are
 protected static int p = 0;
 protected int pnum;
 
 protected int wins;
 protected int points;
 
 public int getPoints(){
   return points;
 }
 public void setPoints(int i){
   points = points + i;
 }
 public int getSizeOfHand(){
  return hand.size();
 }
 public ArrayList<Card> getActualHand(){
   return hand;
 }
 
 public void addToHand(int num,Stack<Card> drawPile){
   for(int i = 0; i < num; i++){
     if(drawPile.empty()){
     } else{
       System.out.println("Player has to draw 2 cards, they drew " + drawPile.peek().toString());
       hand.add(drawPile.pop());
     }
   }
 }
 
 
 /* play a card  */
 public abstract boolean play(DiscardPile       discardPile, 
                              Stack<Card>       drawPile, 
                     ArrayList<Player> players);
 
 //this just returns the players hadn as a string
 public String getHand(){
   String myHand = "[ ";
   for(int i = 0; i < hand.size(); i++){
     myHand = myHand + hand.get(i).toString();
     if(i+1 != hand.size()){
       myHand = myHand + ", ";
     }
   }
   myHand = myHand + " ]";
   return myHand;
 }
 
 public boolean isSpecial(int r){
  
  if(r == 7 || r == 4 || r == 2 || r==8)
  {
   return true;
  }
  return false;
  
 }
 
 public int getPnum(){
   return pnum;
 }
  public boolean direction(DiscardPile discardPile){
  int dir = 0;
  for (int i = 0; i < discardPile.size(); i++)
  {
   if (discardPile.get(i).getRank() == 7)
   {
    dir++;
   }
   
  }
  
  
  if (dir%2 == 1){
   
   return true;
  }
  
  return false;
 }
  
   public ArrayList<Card> vhand(Card top, ArrayList<Card> hand, Stack<Card> drawpile) //determines valid cards in player's hand.
 {
  ArrayList<Card> vhand = new ArrayList<Card>();
  
  for (int i = 0; i < hand.size(); i++){
   
   if(hand.get(i).getRank() == top.getRank() || hand.get(i).getSuit().equals(top.getSuit()) || hand.get(i).getRank() == 8 )
   {
    vhand.add(hand.get(i));
   }
  }
  
  if(vhand.size() > 0)
  {
   return vhand;
  }
  else
  {
   for (int i = 0; i < drawpile.size(); i++)
   {
    if (drawpile.peek().getRank() == top.getRank() || drawpile.peek().getSuit().equals(top.getSuit()) || drawpile.peek().getRank() == 8)
    {
     vhand.add(drawpile.pop());
     hand.add(vhand.get(0));
     System.out.println("Player drew a card!, it was " + hand.get(hand.size() - 1).toString());
     return vhand;
    }
    hand.add(drawpile.pop());
   }
  }
  
  return vhand;
 }
  
  
 // return true if player wins game by playing last card
 // returns false otherwise
 // side effects: plays a card to top of discard Pile, possibly taking zero
 //               or more cards from the top of the drawPile
 //               card played must be valid card
 
}