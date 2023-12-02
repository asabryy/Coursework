import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class BadPlayer extends Player{

 public BadPlayer(Card[] cards){this.hand = new ArrayList<Card>(Arrays.asList(cards)); pnum = p; p++;}
 
  /* play a card */ 
 public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players){
   
   ArrayList<Card> vhand = vhand(discardPile.top(),this.hand,drawPile);
   if(vhand.isEmpty()){
     return false;
   }
  int index = 0;
  for (int i = 0; i < hand.size(); i++)
  {
   if(vhand.get(0).getRank() == hand.get(i).getRank() && vhand.get(0).getSuit().equals(hand.get(i).getSuit()))
   {
    index = i;
   }
  }
   
   
  discardPile.add(this.hand.remove(index));
  if( this.hand.size() == 0 ){return true;}
  return false;
 }
}