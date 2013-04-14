package spaceinvaders.entity.upgrades;

import java.util.ArrayList;

/**
 * At the end of each level, 
 * players can spend their points to purchase
 * upgrades, such as new weapons and stat boosts.<br/>
 * All data is loaded via the global JSON data file
 * @author Brian Yang
 */
public class Shop {
    
    public static ArrayList<Weapons> newWeapons = new ArrayList<Weapons>();
    
    public Shop() {
        
    }
    
}
