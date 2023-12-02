public class RedBubble extends Bubble {
  
  BlueBubble bbubble;
  
  public RedBubble(double x, double y, double speedX, double speedY, double radius, int health){
    super(x , y, radius);
    super.setSpeedX(speedX);
    super.setSpeedY(speedY);
    super.setHealth(health);
  }
  
  public void update(Bubble[] bubbles, int x1, int y1, int x2, int y2){
    super.hitsWall(x1, y1, x2, y2);  
    
    if(super.collidesWith(bbubble) == true){
        this.setHealth(this.getHealth() + bbubble.getHealth());
        bbubble.setHealth(0);
      
      
}
}
}
  
  