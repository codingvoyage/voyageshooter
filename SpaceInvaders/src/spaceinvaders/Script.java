package spaceinvaders;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *
 * @author Edmund
 */
public class Script {
    ArrayList<Line> lineList;
    
    public Script(String filename)
    {
        lineList = new ArrayList<Line>();
        
        loadFile(filename);
        
    }
    
    public Line getLine(int index)
    {
        return lineList.get(index);
    }
    
    public int getLineCount()
    {
        return lineList.size();
    }
    /*
     * This is the part that 
     */
    private void loadFile(String filename) 
    {
        
        
        try {
            //So like script.txt would end up being scripts/script.txt
            //which is where it should be...
            FileReader reader = new FileReader("scripts/" + filename);
            Scanner in = new Scanner(reader);

            //Now, we step through it line by line.
            while (in.hasNextLine())
            {
                //Take in the next line as a String
                String myNextLine = in.nextLine();
                
                //Of course, if the next line contains nothing, then ignore
                //Everything else that happens will happen if the line isn't ""
                if (!myNextLine.equals("")) {
                    
                    //Alright, first let's trim it just in case there
                    //is whitespace
                    //myNextLine = myNextLine.trim();
                    
                    //Start from 0, and read until you hit a space. 
                    int lineLength = myNextLine.length();
                    
                    boolean areWeDone = false;
                    int positionOnLine = 0;
                    String currentToken = "";
                    
                    //Testing purposes.
                    //0 so commandID is 0
                    //1 is "one parameter"
                    Line nextLine = new Line(0);
                    nextLine.addParameter(myNextLine);
                    
                    lineList.add(nextLine);
                    
                    //while (!areWeDone)
                    //{
                        //Basically, see when's the next time a space occurs
                        //OR if we're in the middle of a String, then when the 
                        //next " occurs
                        
                        
                        
                        
                    //}
                    
                    
                    
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    
    
    
    
}
