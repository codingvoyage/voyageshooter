package spaceinvaders;

/**
 *
 * @author Edmund
 */
public class Parameter {
    /* Can hold either a String, an integer, a boolean, or a double
     * 1 for String
     * 2 for integer
     * 3 for boolean
     * 4 for double
     */
    int whichType;
    
    //Stores the actual value
    String s;
    int i;
    boolean b;
    double d;
  
    
    public Parameter(String newString)
    {
        s = newString;
        whichType = 1;
    }
    
    public Parameter(int newInteger)
    {
        i = newInteger;
        whichType = 2;
    }
    
    public Parameter(boolean newBoolean)
    {
        b = newBoolean;
        whichType = 3;
    }
    
    public Parameter(double newDouble)
    {
        d = newDouble;
        whichType = 4;
    }
    
    //Returns the type stored
    public int getStoredType() {
        return whichType;
    }
    
    //Returns the corresponding value
    public String getStringValue() 
    {
        return s;
    }
    
    public int getIntegerValue()
    {
        return i;
    }
    
    public boolean getBooleanValue()
    {
        return b;
    }
    
    public double getDoubleValue()
    {
        return d;
    }
    
    
}
