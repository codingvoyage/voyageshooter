/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.util.ArrayList;

/**
 *
 * @author Edmund
 */
public class Line {
    int commandID;
    //String commandName;
    ArrayList<Parameter> parameterList;
    
    //Constructors
    
    public Line(int commandID)
    {
        this.commandID = commandID;
        parameterList = new ArrayList<Parameter>();
    }
    
    //Setters and getters for commandID
    public void setCommandID(int newCommandID) 
    {
        commandID = newCommandID;
    }
    
    public int getCommandID()
    {
        return commandID;
    }
    
    
    //For debugging
    public String toString()
    {
        //First, it's the command ID
        String returnThis = Integer.toString(commandID);
        
        //Now add all of the parameters, if applicable
        for (int i = 0; i < parameterList.size(); i++)
        {
            //Add a space..
            returnThis += " ";
            
            //Add the parameter
            //Now this depends on the type it is...
            Parameter currentParameter = getParameter(i);
            int parameterType = currentParameter.getStoredType();
            switch (parameterType)
            {
                case 1:
                    returnThis += currentParameter.getStringValue();
                    break;
                case 2:
                    returnThis += currentParameter.getIntegerValue();
                    break;
                case 3:
                    returnThis += currentParameter.getBooleanValue();
                    break;
                case 4:
                    returnThis += currentParameter.getDoubleValue();
                    break;
            }
        }
        
        return returnThis;
    }
    
    //Adds a new parameter which contains the indicated value
    public void addParameter(int newIntegerParameter) 
    {
        addParameter(new Parameter(newIntegerParameter));
    }
    
    public void addParameter(String newStringParameter) 
    {
        addParameter(new Parameter(newStringParameter));
    }
    
    public void addParameter(boolean newBooleanParameter) 
    {
        addParameter(new Parameter(newBooleanParameter));
    }
    
    public void addParameter(double newDoubleParameter) 
    {
        addParameter(new Parameter(newDoubleParameter));
    }
    
    //Returns the value of the parameter at the indicated index
    public String getStringParameter(int index)
    {
        return getParameter(index).getStringValue();
    }
    
    public int getIntegerParameter(int index)
    {
        return getParameter(index).getIntegerValue();
    }
    
    public boolean getBooleanParameter(int index)
    {
        return getParameter(index).getBooleanValue();
    }
    
    public double getDoubleParameter(int index)
    {
        return getParameter(index).getDoubleValue();
    }
    
    //Of course, the user should be able to tell how many Parameters there are
    public int getParameterCount()
    {
        return parameterList.size();
    }
    
    //Adds the Parameter object to the end of the ArrayList
    private void addParameter(Parameter newParameter) {
        parameterList.add(newParameter);
    }
    
    //Returns the Parameter object at the selected index
    //This is kept separate so that the user never has to deal with
    //the Parameter objects; interactions should only happen through Line
    private Parameter getParameter(int index) {
        return parameterList.get(index);
    }
}
