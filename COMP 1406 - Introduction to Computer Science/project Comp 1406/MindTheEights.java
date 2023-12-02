import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class MindTheEights extends Player{
  
    public MindTheEights(Card[] cards){this.hand = new ArrayList<Card>(Arrays.asList(cards)); pnum = p; p++;}
    
     public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players){
       boolean played = false;
       boolean timeToPlay8s = false;
       boolean any8sInHand = false;
        
       for(int j = 0; j < players.size();j++){
         if(players.get(j).getSizeOfHand() == 1){
           timeToPlay8s = true;
         }
       }
       for(int k = 0; k < hand.size();k++){
         if(hand.get(k).getRank() == 8){
           any8sInHand = true;
         }
       }
        
       //MINDTHEEGIHTS will hold on to their 8s until an opponent only has one card left
       if((timeToPlay8s == true) && (any8sInHand == true)){
         for(int i = 0; i < hand.size(); i++){
           
           //find your 8, and play it
           if(hand.get(i).getRank() == 8){
             discardPile.add(hand.remove(i));
             played = true;
             break;
           }
         }
       }else{
         for(int i = 0; i < hand.size();i++){
           
           //Aside from checkign for an 8 card, mindTheEights will act similar to randomPlayer
           if((hand.get(i).getSuit().equals(discardPile.top().getSuit())) || (hand.get(i).getRank() == discardPile.top().getRank())){
             discardPile.add(hand.remove(i));
             played = true;
             break;
           }
         }
       }
       //if you dont have matching suits or rank draw a card
      if(played == false){
        if(!(drawPile.empty())){
          while((drawPile.peek().getRank() != discardPile.top().getRank())
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