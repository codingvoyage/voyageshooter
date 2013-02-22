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
    private int whichType;
    
    //Stores the actual value
    private String s;
    private int i;
    private boolean b;
    private double d;
  
    //Here are all the constructors of the different supported types...
    public Parameter(String newString)
    {
        setStringValue(newString);
        whichType = 1;
    }
    
    public Parameter(int newInteger)
    {
        setIntegerValue(newInteger);
        whichType = 2;
    }
    
    public Parameter(boolean newBoolean)
    {
        setBooleanValue(newBoolean);
        whichType = 3;
    }
    
    public Parameter(double newDouble)
    {
        setDoubleValue(newDouble);;
        whichType = 4;
    }
    
    //Returns the type stored
    public int getStoredType() {
        return whichType;
    }
    
    //Returns the corresponding value
    //All the getters come below
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

    //Sets the Parameter's variable to the provided value
    //All the setters come below 
    public void setStringValue(String newString) 
    {
        s = newString;
    }
    
    public void setIntegerValue(int newInteger)
    {
        i = newInteger;
    }
    
    public void setBooleanValue(boolean newBoolean)
    {
        b = newBoolean;
    }
    
    public void setDoubleValue(double newDouble)
    {
        d = newDouble;
    }
    
}
