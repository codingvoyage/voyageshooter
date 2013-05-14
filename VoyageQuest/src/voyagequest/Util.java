package voyagequest;

/**
 * A utility class of random useful functions I don't want to hardcode repeatedly
 * elsewhere.
 * 
 * @author Edmund
 */
public class Util {
    /**
     * Given an integer bound, returns a random integer from
     * [min, max], so it's inclusive of min and max.
     * 
     * @param min minimum bound for random number
     * @param max maximum bound for random number
     * @return the random number
     */
    public static int rand(int min, int max)
    {
        int randomNumber = min + (int)(Math.random() * ((max - min + 1)));
        return randomNumber;
    }
    
    /**
     * Nobody likes typing out System.out.println();
     * It's easier to type Util.sop();
     * @param blah 
     */
    public static void sop(String blah)
    {
        System.out.println(blah);
    }
    
    public static void sop(int blah)
    {
        System.out.println(blah);
    }
}
