public class BlueBubble extends Bubble {
  
  RedBubble rbubble;
  
  public BlueBubble(double x, double y, double speedX, double speedY, double radius, int health){
    super(x , y, radius);
    super.setSpeedX(speedX);
    super.setSpeedY(speedY);
    super.setHealth(health);
  }
  
  public void update(Bubble[] bubbles, int x1, int y1, int x2, int y2){
    if(super.collidesWith(rbubble) == true){
       this.x = (this.x + this.speedX);
       this.y = (this.y + this.speedY);
       super.setHealth(0);
       
    }
    
    if (super.getHealth() = 0){
      super.setSpeedX(0);
      super.setSpeedY(0);
    }
}
}