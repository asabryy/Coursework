import java.util.Stack; 

public class Balanced{

  
  /** 
  Checks if a string is balanced or not.
     
   @param s is a non-null string
   @return true of the input <code>s</code> is balanced and false otherwise.
  **/
  public static boolean isBalanced(String s){
    Stack<Character> st = new Stack<Character>();
    char c;
    for(int i=0; i < s.length(); i++) {
        c = s.charAt(i);

        if(c == '('){
            st.push(c);
        }else if(c == '{'){
          st.push(c);
        }else if(c == ')')
          if(st.empty()){
           return false;
        }else if(st.peek() == '('){
                st.pop();
        }else{
                return false;
        }else if(c == '}'){
          if(st.empty()){
                return false;
          }else if(st.peek() == '{'){
                st.pop();
          }else{
            return false;}
        }
    }
    return st.empty();
    }
  
  /** 
  Counts the number of balanced strings in the input array.
  
  @param in is a non-null array of strings
   @return the number of strings in the input <code>in</code> that are balanced.
  **/
  public static int numberOfBalancedStrings(String[] in){
    int count = 0;
    for(int i=0; i < in.length; i++){
      if(isBalanced(in[i]) == true){
        count++;}
         }
      return count;
  }
  
  
}