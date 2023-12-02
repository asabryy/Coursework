public class counting{
  
  public static void main(String[] args) {
    int n = 1000;
    int fooCount = 0;
    int barCount = 0;
    for(int i = 1; i<=n+1; i++){
      for(int c = 1; c<=i+1; c++){
        fooCount = fooCount + 2;}
      //if(i>5){
        for(int j = 5; j<=i+1; j++){
          for(int k = 1; k<=j; k++){
            barCount++;}
        //}
      }
    }
    System.out.println("Foo ran: " + fooCount + " times");
    System.out.println("Bar ran: "+ barCount + " times");
  }
}