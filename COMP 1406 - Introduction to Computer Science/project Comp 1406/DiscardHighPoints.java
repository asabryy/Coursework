import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class DiscardHighPoints extends Player{

 public DiscardHighPoints(Card[] cards){this.hand = new ArrayList<Card>(Arrays.asList(cards)); p++; pnum = p;}
 
  /* play a card */ 
 public boolean play(DiscardPile       discardPile, 
                     Stack<Card>       drawPile, 
           ArrayList<Player> players)
 {
  ArrayList<Card> vhand = vhand(discardPile.top(),hand,drawPile);
  int play = 0; //Determines index of hand to play.
  int hp = 0; //Keeps track of highest point card;
  
  for (int i = 0; i < vhand.size(); i++) //Find the highest-point card, save index in 'play' int.
  {
   if(points(vhand.get(i)) > hp )
   {
    hp = points(hand.get(i));
    play = i;
   }
  }
  
  int index = 0;
  for (int i = 0; i < hand.size(); i++)
  {
   if(vhand.get(play).getRank() == hand.get(i).getRank() && vhand.get(play).getSuit().equals(hand.get(i).getSuit()))
   {
    index = i;
   }
  }
  
  discardPile.add(this.hand.remove(index));
  if( this.hand.size() == 0 ){return true;}
  return false;
 }
 
 
 
 
 public int points(Card c)
 {
  int r = c.getRank();
  if (r > 10 && r < 14)  //facecard
  {
   return 10;
  }
  
  if (r == 14)  //ace
  {
   return 1;
  }
  
  if (r == 8)  //eight
  {
   return 50;
  }
  
  if (r == 2 || r == 4)  //2 or 4
  {
   return 25;
  }
  if (r == 7)  //7
  {
   return 20;
  }
  
  else //return face value;
  {
   return r;
  }
  
 }
 
}