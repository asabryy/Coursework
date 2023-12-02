# Assignment 3 
(Due Monday, July 24, at 5:30 pm)
---

---
The intention is get practice writing constructors and simple object behaviour.

---

## submit-03 

Create and push a `submit-03` file for this assignment. The file, as before, will contain four lines: your student ID number, your cmail username, your name and your GitHub username (in that order). Give your full cunet email address (without the dots in your name). For example, a sample file might look like

200976283      
catkittensish@cmail.carleton.ca   
Cat Kittenish   
supercoderkitty    

Note: The file must is a plain text file. Do not change the format of the file anything else (such as rich text, or .docx, etc). Do not add an extension to the file. Be sure to check that your operating system (in particular if you are using Windows) does not have a hidden .txt extension for this file.

## Temperature

Complete the provided `Temperature` class. Add 
any attributes and helper methods as needed. You must complete the constructors and methods in the provided class (without changing any signatures, return types, or modifiers).

In this problem you will need to be able to convert temperatures between Celsius, Fahrenheit and Kelvin. For help, see   

[https://en.wikipedia.org/wiki/Conversion_of_units_of_temperature](https://en.wikipedia.org/wiki/Conversion_of_units_of_temperature)

A temperature object holds a single temperature and displays it in one of the three scales. Once a scale has been set, it will display the temperature in that scale until changed. The default scale is Celsius if not specified. 

Examples:
```text
Temperature t = new Temperature(10.1);
System.out.println(t.getScale());    // outputs the char 'C'
System.out.println(t.temp());        // outputs 10.1C
t.setScale("F");
System.out.println(t.temp());        // outputs 50.18F
System.out.println(t.getScale());    // outputs the char 'F'
```

Note: Repeatedly changing the scale should not "change" the value of the temperature. For example,

```
Temperature t = new Temperature(10.1);
for(int i=0; i<10000; i+=1){
   t.setScale("F");
   t.setScale("C");
}
System.out.println(t.temp());        // should output 10.1C
``` 

Note: You should have __no__ static attributes or methods in your class.


## Extreme Temperatures

Complete the `MaxTemp` class.  The class consists of a single constructor and a single getter method. 

- The constructor takes an array of `Temperature` objects as input. 
- The getter method either returns an array of doubles with exactly two doubles in it or returns `null`. If the object was created with one more more `Temperature` objects in the constructor's input array then the output consists of the maximum temperature of all `Temperature` objects passed to the constructor and a count of how many times that maximum was present in the array passed to the constructor (in that order). If zero `Temperature` objects were passed to the constructor (in the array) then the getter returns `null`.  

Note: The max temperature returned must be displayed in the __Kelvin__ scale.   

Note: Different `Temperature` objects in the array passed to the constructor may have different temperature scales set for themselves.

Since the `Temperature` objects will store a floating point number for the temperature, you will use the provided `EPSILON` constant in the `MaxTemp` class and consider two temperatures as equal if their absolute difference is smaller than `EPSILON`. Therefore,  if  `Math.abs(temp1 - temp2) < ESILON` then temp1 and temp2 are considered equal. Note: different `Temperature` objects passed to the constructor 


For example, if the array `{new Temperature(101.12, "K"), new Temperature(-333.67, "F"), new Temperature(101.11, "K")}` is passed to the constructor, and `EPSILON = 0.1`, then the `getMax` getter will return `[101.12, 2.0]`.

The return value should still display the maximum temperature (in K). In the example above, even though we consider 101.11 and 101.12 the "same", 101.12 is still the max to be returned.






