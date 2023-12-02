import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class Myplayer extends Player{
  public Myplayer(Card[] cards){
    this.hand = new ArrayList<Card>(Arrays.asList(cards));
    pnum = p; p++;
  }
 
  /* play a card */ 
 public boolean play(DiscardPile       discardPile, 
                     Stack<Card>       drawPile, 
           ArrayList<Player> players)
 {
  discardPile.add(this.hand.remove(0));
  if( this.hand.size() == 0 ){return true;}
  return false;
 }
}