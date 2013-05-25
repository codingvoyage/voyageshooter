package scripting;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Edmund
 */
public class ScriptManager {
    //An array of all the Script objects
    private Script[] scriptCollection;
    
    //A HashMap which acts as a dictionary which ties command names with
    //their corresponding integer IDs
    public final HashMap<String, Integer> commandIDDictionary;
    
    public final int SCRIPT_CAPACITY = 50;
    
    /** path to scripts folder */
    public final String SCRIPT_FOLDER = "src/scripting/scripts/";
    
    public ScriptManager() 
    {
        //Let's make space for 20 scripts
        scriptCollection = new Script[SCRIPT_CAPACITY];
        
        //Let's set each object to null
        for (int i = 0; i < SCRIPT_CAPACITY; i++) 
        {
            scriptCollection[i] = null;
        }
        
        //Now based on our definition file, load the IDDictionary
        commandIDDictionary = createIDDictionary("DICTIONARY.txt");
        
    }
    
    private HashMap<String, Integer> createIDDictionary(String dictionaryFilename) 
    {
        //Create a HashMap which holds function and commandID pairs
        HashMap<String, Integer> newDictionary = new HashMap<String, Integer>();
        
        try {
            FileReader reader = new FileReader(SCRIPT_FOLDER + dictionaryFilename);
            Scanner in = new Scanner(reader);

            while (in.hasNextLine())
            {
                String myNextLine = in.nextLine();
                
                //Ignore if the next Line is a comment (--) or if it is blank
                if (!(myNextLine.equals("")||(myNextLine.substring(0,2).equals("--"))))
                {
                    //Get the name of the command
                    int indexOfSpace = myNextLine.indexOf(" ");
                    String commandNameKey = myNextLine.substring(0, indexOfSpace);
                    
                    //Except we need to be not-case-specific, so let's make it
                    //lowercase!
                    commandNameKey = commandNameKey.toLowerCase();
                    
                    //Find out the number, basically from ahead of the space 
                    //one step until the length of the line should be the number
                    String commandIDNumber = myNextLine.substring(indexOfSpace + 1,
                            myNextLine.length());
                    
                    //Now I need the number in integer form
                    int commandIDInteger = Integer.parseInt(commandIDNumber);
                    
                    //Now I can stick this in the HashMap!
                    newDictionary.put(commandNameKey, commandIDInteger);
                }
            }
        
            //Close the input now.
            in.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
        return newDictionary;
    }
    
    public HashMap<String, Integer> getCommandIDDictionary()
    {
        return commandIDDictionary;
    }
    
    public boolean loadScript(String filename, int indexID) 
    {
        //We only want this function to work when there was no existing
        //script on the provided indexID. It should be null, in that case.
        if (scriptCollection[indexID] == null)
        {
            scriptCollection[indexID] = new Script(filename, commandIDDictionary);
            return true;
        }
        
        //If there was one already, the user should have used the changeScript
        //function, so this one fails.
        return false;
    }
    
    
    
    public Script getScriptAtID(int scriptIDNumber) 
    {
        //Refers to the array, find the one with scriptIDnumber
        return scriptCollection[scriptIDNumber];
    }
    
}
