import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class HamperLeader extends Player{

 public HamperLeader(Card[] cards){this.hand = new ArrayList<Card>(Arrays.asList(cards));  pnum = p; p++;}
 
  /* play a card */ 
 public boolean play(DiscardPile       discardPile, 
                     Stack<Card>       drawPile, 
           ArrayList<Player> players)
 {
  int play = 0;  //Which card to play from my hand.
  int leader = 10000; //Determine who has least # of cards
  
  ArrayList<Card> vhand = vhand(discardPile.top(),hand,drawPile);
  if(vhand.isEmpty()) //If I drew all cards from drawpile, but none of them could be played...
  {
   return false;
  }
  
  
  
  for (int i = 0; i < players.size(); i++)
  {
   if (players.get(i).hand.size() < leader)
   {
    leader = players.get(i).hand.size();
   }
  }
  
  if (players.size() == pnum+1){
   
   if (players.get(0).hand.size() == leader && direction(discardPile)==false)
   {
    for (int i = 0; i < vhand.size(); i++)
    {
     if (isSpecial(vhand.get(i).getRank()))
     {
      play = i;
      break;
     }
    }
    
    
   }
   
  }
  else if (players.get(pnum+1).hand.size() == leader && direction(discardPile)==false)
  {
   for (int i = 0; i < vhand.size(); i++)
   {
    if (isSpecial(vhand.get(i).getRank()))
    {
     play = i;
     break;
    }
   }
  }
  
  if ( pnum == 0){
   if (players.get(players.size()-1).hand.size() == leader && direction(discardPile)==true)
   {
    for (int i = 0; i < vhand.size(); i++)
    {
     if (isSpecial(vhand.get(i).getRank()))
     {
      play = i;
      break;
     }
    }
   }
   
  }
  
  else if (players.get(pnum-1).hand.size() == leader && direction(discardPile)==true)
  {
   for (int i = 0; i < vhand.size(); i++)
   {
    if (isSpecial(vhand.get(i).getRank()))
    {
     play = i;
     break;
    }
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
 
 
 
 public boolean direction(DiscardPile discardPile)
 {
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
 

 
 
}