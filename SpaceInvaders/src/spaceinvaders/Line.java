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
    public Line(int parameterCount)
    {
        parameterList = new ArrayList<Parameter>(parameterCount);
    }
    
    public Line(int commandID, int parameterCount)
    {
        this.commandID = commandID;
        parameterList = new ArrayList<Parameter>(parameterCount);
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
    
}
