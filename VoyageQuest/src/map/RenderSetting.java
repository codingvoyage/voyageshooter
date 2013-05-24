/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

/**
 *
 * @author user
 */
public class RenderSetting {
    public int drawnRow;
    public boolean drawBefore;

    public RenderSetting(int drawnRow, boolean isDrawnBeforeTile)
    {
        this.drawnRow = drawnRow;
        drawBefore = isDrawnBeforeTile;
    }
}
