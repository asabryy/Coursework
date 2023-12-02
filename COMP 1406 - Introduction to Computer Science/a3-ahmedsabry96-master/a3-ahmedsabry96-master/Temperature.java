 public class Temperature{

  // names for the different temperature scales
  public static String[] scales = {"Celsius", "Fahrenheit", "Kelvin"};

  public double temp;
  public String scale;
  public char s;
  public String[] symbol; 

  /* ----------------------------------------------------
   * constructors
   * ----------------------------------------------------
   */

  public Temperature(double temp){
    // - creates a temperature object with given value in Celsius
    this.temp = temp;
    Temperature temprature = new Temperature(temp);
    
  }

  public Temperature(double temp, String scale){
    // - creates a temperature object with given temp using the given scale
    // - scale must be one of the three strings in the scales attribute
    //   OR the the first letter of the name (upper case)
    //   Examples: new Temperature(12.3, "K")
    //             new Tempersture(-90.2, "Celsius")
    this(temp);
    this.scale = scale;
  }

  /* ----------------------------------------------------
   * methods
   * ----------------------------------------------------
   */

  public char getScale(){
    // - returns the current scale of the object
    //   as a single character
    //   'C' for Celsuius, 'F' for Fahrenheit, 'K' for Kelvin

    // add your code and return the correct char
    if (scale == scales[0] || scale == "C"){
      s = scale.charAt(0);
      symbol[0] = "C";
    }else if (scale == scales[1] || scale == "F"){
      s = scale.charAt(0);
      symbol[1] = "F";
    }else if (scale == scales[2] || scale == "K"){
      s = scale.charAt(0);
      symbol[2] = "K";
    }
    
    return s;
  }

  public double getTemp(){
    // - returns the object's temperature using its current scale
    
    return temp;
  }


  
  public void setScale(String scale){
    // - scale must be one of the three strings in the scales attribute
    //   OR the first letter of the name (see the second constructor)
    // - sets the scale of the current object
    if (getScale() == 'C' && (scale == scales[1] || scale == symbol[1])){
      temp = temp * (9/5)+ 32;
    }else if (getScale() == 'C' && (scale == scales[2] || scale == symbol[2])){
      temp = temp + 273.15;
    }else if (getScale() == 'F' && (scale == scales[0] || scale == symbol[0])){
      temp = (temp - 32) * (5/9);
    }else if (getScale() == 'F' && (scale == scales[2] || scale == symbol[2])){
      temp = (temp + 459.67) * (5/9);
    }else if (getScale() == 'K' && (scale == scales[0] || scale == symbol[0])){
      temp = temp - 273.15;
    }else if (getScale() == 'K' && (scale == scales[1] || scale == symbol[1])){
      temp = temp * (9/5) - 459.67;
    }else{
      temp = temp;
    }
    this.scale = scale; 
  }


  public void setTemp(double temp){
    // - sets the objects temperature to that specified using the
    //   current scale of the object
    this.temp = temp;
  }

  public void setTemp(double temp, char scale){
    // - sets the objects temperature to that specified using the
    //   specified scale ('K', 'C' or 'F')
    this.temp = temp;
    String sc = Character.toString(scale);
    setScale(sc);
     
  }

  public void setTemp(double temp, String scale){
    // - sets the objects temperature to that specified using the
    //   specified scale
    // - scale must be one of the three strings in the scales attribute
    //   OR the the first letter of the name (upper case)
    setTemp(temp, scale);
    setScale(scale);
    
  }








  /* do not change anything below this line */
  /* -------------------------------------- */

  /* string representation of object */
  public String temp(){
    return "" + this.getTemp() + this.getScale();
  }



  /* Override the toString() method                                */
  /* with this we will not need the temp() method from above       */
  /* we will cover this soon!                                      */
  /* you do not need to use this for this assignment!              */
  @Override
  public String toString(){
    return "" + this.getTemp() + this.getScale();
  }

}
