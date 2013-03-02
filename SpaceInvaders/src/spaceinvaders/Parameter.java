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
    private boolean b;
    private double d;
  
    //Here are all the constructors of the different supported types...
    public Parameter(String newString)
    {
        setStringValue(newString);
        whichType = 1;
    }
    
    public Parameter(boolean newBoolean)
    {
        setBooleanValue(newBoolean);
        whichType = 2;
    }
    
    public Parameter(double newDouble)
    {
        setDoubleValue(newDouble);;
        whichType = 3;
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
    
    public void setBooleanValue(boolean newBoolean)
    {
        b = newBoolean;
    }
    
    public void setDoubleValue(double newDouble)
    {
        d = newDouble;
    }
    
    public String toString() 
    {
        String returnedString = null;
        switch (getStoredType())
        {
            case 1:
                return getStringValue();
            case 2:
                return Boolean.toString(getBooleanValue());
            case 3:
                return Double.toString(getDoubleValue());
        }
        
        //null....
        return returnedString; 
    }
}


/*
 * If I ever support doubles, then let this be what is used to check...
 *
 * private static Pattern doublePattern = Pattern.compile("-?\\d+(\\.\\d*)?");
 * public boolean isDouble(String string) {
 *      return doublePattern.matcher(string).matches();
 * }
 */ 