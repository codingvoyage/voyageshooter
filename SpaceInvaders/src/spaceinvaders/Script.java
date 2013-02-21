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
     * This is the part that loads the file, and then 
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
                    myNextLine = myNextLine.trim();
                    
                    //The formatLine class will work its magic and take the
                    //line as a String and make a Line object with properly
                    //formatted Parameters
                    Line formattedLineObject = formatLine(myNextLine);
                    
                    if (formattedLineObject != null)
                        //Add this to the master list of Lines
                        lineList.add(formattedLineObject);
                    
                }
            }
        }
        catch (FileNotFoundException e)
        {
            //Somehow, we have screwed up and inputted the wrong filename
            e.printStackTrace();
        }
    }
    
    //This is the method which will work its magic and format the 
    //line from String to proper Line object
    private Line formatLine(String unformattedLine)
    {
        //This is the actual Line object that we're going to construct, modify
        //and return. I'm not initializing it, because that should be done
        //in the following piece of code. If I messed up, then let the program
        //crash so that I will know.
        Line formattedLine = null;
        
        //currentIndex refers to the index of the currently examined 
        //character on the unformattedLine String
        int currentIndex = 0;
        
        //These are temporary values which hold how far the Scripting Parameter
        //reaches, from the currentIndex, to whatever delimits the Parameter. 
        int spaceIndex;
        int doubleQuoteIndex;
        
        //currentToken is the temporary variable for the short pieces which we 
        //have extracted.
        String currentToken = "";
        
        //So basically, the first token on the line will be the command.
        //This should be promptly converted to true after that happens.
        boolean doneWithCommandID = false;
        
        //Once areWeDone evaluates to true, we're done. This will happen
        //differently depending on the final Parameter. If it is 
        boolean areWeDone = false;
        while (!areWeDone)
        {
            
            //First of all, find the character at currentIndex
            char currentChar = unformattedLine.charAt(currentIndex);
            
            //Now, we ask ourselves whether this is a quotation mark...
            if (currentChar == '"') 
            {
                //Basically, if it's starting with a quotation mark, then we're
                //dealing with a String parameter. First of all, we'll find the
                //boundaries of the String variable in the script by using
                //indexOf to find the next time a '"' appears in the line,
                //starting from currentIndex + 1. A good 15 minute debugging 
                //session revealed to me that when it looks for the '"' starting
                //from currentIndex, if there is a '"' at currentIndex, that 
                //ends up counting. Which is why it failed. But now if I make it
                //currentIndex + 1, then it's all good. Even if it were a
                //empty String "", that still makes sense.
                doubleQuoteIndex = unformattedLine.indexOf("\"",
                        currentIndex + 1);
                
                
                //Now, the token which we take is a substring from the current
                //Index plus one, to the quotationIndex
                currentToken = unformattedLine.substring(currentIndex + 1,
                        doubleQuoteIndex);
                
                //So now we must add currentToken into the Line object
                //No shenanigans necessary because we know for a fact that it's
                //a String - it was delimited by double quotes...
                formattedLine.addParameter(currentToken);
                
                //At this point, we would move currentIndex. However, we can't.
                //See, unlike the following code for non-String Parameters, we
                //don't have to check if there's a space coming up because a 
                //String variable must be bounded by another double quote. But 
                //nonetheless now we must see whether we're at the end of the
                //Line, or else we might have a nasty crash.
                if (doubleQuoteIndex == unformattedLine.length() - 1)
                {
                    //We're done. Don't bother moving the currentIndex
                    areWeDone = true;
                } 
                else
                {
                    //Now, we move the currentIndex to the doubleQuoteIndex, and 
                    //then increment it by two. It's first at the double quote.
                    //Moving it once moves it to the space. Moving it a second 
                    //time arrives at the next parameter. 
                    currentIndex = doubleQuoteIndex + 2;
                }
                
            } 
            else 
            //Alright, so it's NOT a quotation mark. It's a normal character,
            //such as 'a' or ']' or 'd'. 
            {
                //So we want to find out where the next instance of a space is
                //starting from the currentIndex mark
                spaceIndex = unformattedLine.indexOf(" ", currentIndex);
                
                //So we know that if indexOf returns a -1, that means that it
                //couldn't find that next instance of a space. In that case, 
                //that means this is the last parameter on the line. 
                if (spaceIndex == -1)
                {
                    //Just take the substring from the currentIndex to the
                    //length of the entire String.
                    currentToken = unformattedLine.substring(currentIndex, 
                            unformattedLine.length());
                    
                    //So, we're done. 
                    areWeDone = true;
                } 
                else  
                //In this case, spaceIndex was not -1, meaning that there was in
                //fact another space coming up. 
                {
                    //Hence, take the substring from currentIndex to spaceIndex
                    currentToken = unformattedLine.substring(currentIndex,
                            spaceIndex);
                    
                    //Since we're still going on, we need to adjust currentIndex
                    //accordingly. Must move currentIndex to spaceIndex + 1
                    currentIndex = spaceIndex + 1;
                }
                
                //BUT WAIT. LET'S NOT FORGET ABOUT THE currentToken.
                //Unfortunately, the process will be slightly more complicated. 
                
                //First off, if the doneWithCommandID is false, then this
                //MUST BE the commandID UNLESS IT IS '--' WHICH DENOTES THAT
                //IT IS A COMMENT, for which we move on to the next line 
                //immediately.
                if (!doneWithCommandID)
                {
                    //IS IT NOT A COMMENT??
                    if (!currentToken.equals("--"))
                    {
                        formattedLine = new Line();
                        formattedLine.setCommandID(findCommandID(currentToken));
                        doneWithCommandID = true;
                    } 
                    else //So it was a comment!
                    {
                        areWeDone = true;
                        //What happens now is that the formatLine method will
                        //return a null, and the code will see that if it's a
                        //null, it just moves on. Hence, comments are ignored.
                    }
                } 
                else
                //Otherwise, yeah now we have to determine whether this is a 
                //boolean, an int, or a double. Simple. We see whether it's 
                //"true" or "false" at first. If not, fails the boolean test.
                //Then we try to convert it to a double. If that works, then 
                //great. Otherwise, try an int. If it finally fails again, then
                //it must be a String (!!)
                {
                    //Going to create a separate method to return what kind
                    //this is, or else this gets ugly fast
                    int whatKind = getCurrentType(currentToken);
                    
                    //Then we respond based on whatKind
                    switch (whatKind) 
                    {
                        case 0: //false
                            formattedLine.addParameter(false);
                            break;
                        case 1: //true
                            formattedLine.addParameter(true);
                            break;
                        case 2: //double
                            formattedLine.addParameter(
                                    Double.parseDouble(currentToken));
                            break;
                        case 3: //integer
                            formattedLine.addParameter(
                                    Integer.parseInt(currentToken));
                            break;
                        case 4: //give up. String
                            formattedLine.addParameter(currentToken);
                            break;
                    }
                    
                //This is the end bracket for the part which determined
                //the type of currentToken. That lets us pass it to Line
                //in an appropriate fashion.
                } 
                
            //This is the end bracket for the ENTIRE part which acts whenever
            //the currently indexed character is or is NOT a quotation mark
            }
            
        //This is the end bracket for the entire while loop. Basically, it
        //hits the end of the while loop with each token that gets extracted
        }
        
        //If it's still null, something's really wrong
        //assert (formattedLine != null);
        //Actually if it's null, then it's a comment!
        return formattedLine;
    }
    
    private int findCommandID(String commandName)
    {
        //There is really no other way around this...
        //Maybe I'll write something which will automate
        //this later, but for now, we'll just have our key here
        
        if (commandName.equals("Whatever"))
            return 0;
        
        return 1337;
    }
    
    //I realized that it would be super-unwieldy to place the code which
    //checks what type the token is in the loop, so I made it a private
    //function. Another neat thing is that the fact it's a function means
    //we have a sort of an early-out mechanism when determining what kind of
    //token it is. For example if it's "true", a 1 gets returned immediately.
    private int getCurrentType(String token)
    {
        //Alright, first we attempt to see if it's "true" or "false"
        if (token.equals("true"))
            return 1;
        else if (token.equals("false"))
            return 0;
        
        //If we're still here, let's try to convert it to a double...
        try
        {
            //If we even get here, then it worked. It's a double!
            double dummyVal = Double.parseDouble(token);
            return 2;
        }
        catch(NumberFormatException e) {}
                        
        //Alright, if we're here, then it's not a double! Try integer.
        try
        {
            //If we even get here, then it worked. It's an integer!
            int dummyVal = Integer.parseInt(token);
            return 3;
        }
        catch(NumberFormatException e) {}
        
        //What the hell. Alright. It's a string. Just make it so
        //No really, in fact it might play an important role in that what if
        //we have a function Compare GreaterEquals var1 var2. In this case,
        //although Compare is the command, the rest are special. Compare would
        //check first what kind of comparison to make, which GreaterEquals 
        //denotes. But we want to reserve the "  " for names which have
        //real strings, most prominently what we plan the state names to be.
        //SetNewState "Doing50BearAssCollection" "YES". These aren't additional
        //information for the interpreter... these are literals for the engine.
        return 4;
                    
    }
}