package gui.types;

import gui.types.Dialog;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.TrueTypeFont;
import voyagequest.Globals;

/**
 * Processes the dialog text to print out one character at a time 
 * and fit it to the dialog box.
 * @author Brian Yang
 */
public class DialogParser implements Runnable {
    
    /** Words of the String */
    private String[] words;
    /** The dialog box */
    private Dialog box;
    /** Update interval */
    private int delta;
    /** Font */
    public static final TrueTypeFont FONT = Globals.FONT;
    
    /**
     * 
     * @param text
     * @param box 
     */
    public DialogParser(String text, Dialog box) {
        this.box = box;
        words = text.split(" ");
    }
    
    /**
     * Processes and prints the dialog box
     */
    @Override
    public void run() {
        int totalWidth = 300;
        int curPos = 0;
        for (String s : words) {
            int width = FONT.getWidth(s);
            
            if (curPos + width > totalWidth) {
                curPos = 0;
                System.out.println();
            }
            
            char[] chars = s.toCharArray();
                for (char c : chars) {           
                    System.out.print(c);
                    
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DialogParser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                System.out.print(" ");
                curPos += width;
        }
   }

    
}
