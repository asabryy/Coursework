import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created by Abdul's PC on 2017-08-15.
 */
public class ExtraCards extends Player {

    public int pnum;
    public int p;

    public ExtraCards(Card[] cards) {
        this.hand = new ArrayList<Card>(Arrays.asList(cards));
        pnum = p;
        p++;
    }

    /* play a card */
    public boolean play(DiscardPile discardPile,
                        Stack<Card> drawPile,
                        ArrayList<Player> players) {
        int play=0;
        if (!(direction(discardPile))) {
            if (pnum == players.size() - 1) {
                if (players.get(0).getSizeOfHand() == 1) {
                    int a = doIhavePower(hand);
                    if (a != 1000) {
                        discardPile.add(hand.remove(a));
                        System.out.println("Player has played" + discardPile.top().toString());
                    } else {
                        this.hand.add(drawPile.pop());
                        System.out.println("Player has drawn" + hand.get(hand.size() - 1).toString());
                        while ((!(hand.get(hand.size() - 1).getRank() == 2)) || (!(hand.get(hand.size() - 1).getRank() == 4)) ||
                                (!(hand.get(hand.size() - 1).getRank() == 7)) || (!(hand.get(hand.size() - 1).getRank() == 8))) {
                            if (drawPile.empty())
                                break;
                            this.hand.add(drawPile.pop());
                            System.out.println("Player has drawn" + hand.get(hand.size() - 1).toString());
                        }
                        discardPile.add(hand.remove(doIhavePower(hand)));
                    }
                } else{
                   for(int i = 0; i < hand.size();i++){
      
                     //if the card is the same suit, or same rank, or an 8, play it, otherwise draw
                     if(((hand.get(i).getSuit().equals(discardPile.top().getSuit())) || (hand.get(i).getRank() == discardPile.top().getRank())) && ( (hand.get(i).getRank() != 2) && 
                        (hand.get(i).getRank() != 4) && (hand.get(i).getRank() != 7) && (hand.get(i).getRank() != 8))){
                       discardPile.add(hand.remove(i));

                       break;
                     }
                   }
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
          

        }
      }
                }
            } else {
                if (players.get(pnum+1).getSizeOfHand() == 1) {
                    int a = doIhavePower(hand);
                    if (a != 1000) {
                        discardPile.add(hand.remove(a));
                        System.out.println("Player has played" + discardPile.top().toString());
                    } else {
                        this.hand.add(drawPile.pop());
                        System.out.println("Player has drawn" + hand.get(hand.size() - 1).toString());
                        while ((!(hand.get(hand.size() - 1).getRank() == 2)) || (!(hand.get(hand.size() - 1).getRank() == 4)) ||
                                (!(hand.get(hand.size() - 1).getRank() == 7)) || (!(hand.get(hand.size() - 1).getRank() == 8))) {
                            if (drawPile.empty())
                                break;
                            this.hand.add(drawPile.pop());
                            System.out.println("Player has drawn" + hand.get(hand.size() - 1).toString());
                        }
                        discardPile.add(hand.remove(doIhavePower(hand)));
                    }
                } else{
                   for(int i = 0; i < hand.size();i++){
      
                     //if the card is the same suit, or same rank, or an 8, play it, otherwise draw
                     if(((hand.get(i).getSuit().equals(discardPile.top().getSuit())) || (hand.get(i).getRank() == discardPile.top().getRank())) && ( (hand.get(i).getRank() != 2) && 
                        (hand.get(i).getRank() != 4) && (hand.get(i).getRank() != 7) && (hand.get(i).getRank() != 8))){
                       discardPile.add(hand.remove(i));

                       break;
                     }
                   }
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

        }
      }
                }
            }
       } else {
            if (pnum == 0) {
                if (players.get(players.size()-1).getSizeOfHand() == 1) {
                    int a = doIhavePower(hand);
                    if (a != 1000) {
                        discardPile.add(hand.remove(a));
                        System.out.println("Player has played" + discardPile.top().toString());
                    } else {
                        this.hand.add(drawPile.pop());
                        System.out.println("Player has drawn" + hand.get(hand.size() - 1).toString());
                        while ((!(hand.get(hand.size() - 1).getRank() == 2)) || (!(hand.get(hand.size() - 1).getRank() == 4)) ||
                                (!(hand.get(hand.size() - 1).getRank() == 7)) || (!(hand.get(hand.size() - 1).getRank() == 8))) {
                            if (drawPile.empty())
                                break;
                            this.hand.add(drawPile.pop());
                            System.out.println("Player has drawn" + hand.get(hand.size() - 1).toString());
                        }
                        discardPile.add(hand.remove(doIhavePower(hand)));
                    }
                }else{
                   for(int i = 0; i < hand.size();i++){
      
                     //if the card is the same suit, or same rank, or an 8, play it, otherwise draw
                     if(((hand.get(i).getSuit().equals(discardPile.top().getSuit())) || (hand.get(i).getRank() == discardPile.top().getRank())) && ( (hand.get(i).getRank() != 2) && 
                        (hand.get(i).getRank() != 4) && (hand.get(i).getRank() != 7) && (hand.get(i).getRank() != 8))){
                       discardPile.add(hand.remove(i));

                       break;
                     }
                   }
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
          

        }
      }
                }     
            } else {
                if (players.get(pnum - 1).getSizeOfHand() == 1) {
                    int a = doIhavePower(hand);
                    if (a != 1000) {
                        discardPile.add(hand.remove(a));
                        System.out.println("Player has played" + discardPile.top().toString());
                    } else {
                        this.hand.add(drawPile.pop());
                        System.out.println("Player has drawn" + hand.get(hand.size() - 1).toString());
                        while ((!(hand.get(hand.size() - 1).getRank() == 2)) || (!(hand.get(hand.size() - 1).getRank() == 4)) ||
                                (!(hand.get(hand.size() - 1).getRank() == 7)) || (!(hand.get(hand.size() - 1).getRank() == 8))) {
                            if (drawPile.empty())
                                break;
                            this.hand.add(drawPile.pop());
                            System.out.println("Player has drawn" + hand.get(hand.size() - 1).toString());
                        }
                        discardPile.add(hand.remove(doIhavePower(hand)));
                    }
                }
            }
        }
        if(hand.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

        public int doIhavePower(ArrayList<Card> myhand) {
        int k = 0;
            for(int i = 0; i < myhand.size(); i++) {
                if((myhand.get(i).getRank() == 2) || (myhand.get(i).getRank() == 4) || (myhand.get(i).getRank() == 7) ||
                        (myhand.get(i).getRank() == 8)) {
                    k = i;
                    return k;
                }
            }
            return 1000;
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
                    return vhand;
                }
            }
        }

        return vhand;
    }
}