import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class RandomPlayer extends Player{
  
  
  public RandomPlayer(Card[] cards){this.hand = new ArrayList<Card>(Arrays.asList(cards)); pnum = p; p++;}
  
  public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players){
    boolean played = false;

    //randomplayer will simply go through their hand and play a valid card, if no valid card he will draw
    for(int i = 0; i < hand.size();i++){
      
       //if the card is the same suit, or same rank, or an 8, play it, otherwise draw
       if((hand.get(i).getSuit().equals(discardPile.top().getSuit())) || (hand.get(i).getRank() == discardPile.top().getRank()) 
            || (hand.get(i).getRank() == 8)){
          discardPile.add(hand.remove(i));
          played = true;
          break;
       }
    }
    
    //otherwise draw cards until you can play it
    if(played == false){
      
      //System.out.println("Player drew a card!, it was " + drawPile.peek().toString());
      if(!(drawPile.empty())){
        while((drawPile.peek().getRank() != 8) && (drawPile.peek().getRank() != discardPile.top().getRank())
                && (!(drawPile.peek().getSuit().equals(discardPile.top().getSuit())))){
          hand.add(drawPile.pop());
          System.out.println("Player drew a card!, it was " + hand.get(hand.size() - 1).toString());
          if(drawPile.empty()){
            System.out.println("The draw pile is empty, Pass.");
            break;
          }
        }
        
        if(!(drawPile.empty())){
          hand.add(drawPile.pop());
          System.out.println("Player drew a card!, it was " + hand.get(hand.size() - 1).toString());
          System.out.println("Player  played " + hand.get(hand.size() - 1).toString());
          discardPile.add(this.hand.remove(hand.size()-1));
          
          played = true;
        }
      }
    }
    //discardPile.add(this.hand.remove(hand.size()-1));
    if(hand.size() == 0){
      return true;
    } else {
      return false;
    }
  }
}