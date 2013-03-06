package spaceinvaders;

/**
 * Parameter
 * 
 * @author Edmund
 * @author Bakesale
 */
public class Parameter {
    /* 
     * 1 for String
     * 2 for boolean
     * 3 for double
     * 4 for double array
     */
    private int whichType;
    
    //Stores the actual value
    private String s;
    private boolean b;
    private double d;
    private double[] da;
    
    //Is this Parameter used as an identifier for a variable under Scripting?
    private boolean isIdentifier;
  
    //Here are all the constructors of the different supported types...
    public Parameter(String newString)
    {
        setStringValue(newString);
        whichType = 1;
        setIdentifier(false);
    }
    
    public Parameter(boolean newBoolean)
    {
        setBooleanValue(newBoolean);
        whichType = 2;
        setIdentifier(false);
        
    }
    
    public Parameter(double newDouble)
    {
        setDoubleValue(newDouble);
        whichType = 3;
        setIdentifier(false);
    }
    
    public Parameter(double[] newDoubleArray)
    {
        setDoubleArrayValue(newDoubleArray);
        whichType = 4;
        setIdentifier(false);
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
    
    public double[] getDoubleArrayValue()
    {
        return da;
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
    
    public void setDoubleArrayValue(double[] newDoubleArray)
    {
        da = newDoubleArray;
    }
    
    public String toString() 
    {
        String returnedString = null;
        switch (getStoredType())
        {
            case 1: // String
                return getStringValue();
            case 2: // boolean
                return Boolean.toString(getBooleanValue());
            case 3: // double
                return Double.toString(getDoubleValue());
            case 4: // double[]
                String array = "{";
                for(double val : da ) {
                    array += val + ", ";
                }
                // Oh no, we'll have a fence-post error. Let's remove the trailing comma and space.
                array = array.substring(0, array.length() - 2);
                array += "}";
                
                return array;
        }
        
        //null....
        return returnedString; 
    }
    
    public boolean isIdentifier() 
    { 
        return isIdentifier;
    }

    public void setIdentifier(boolean isIdentifier)
    {
        this.isIdentifier = isIdentifier;
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
