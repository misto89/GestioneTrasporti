/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viste;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Michele
 */
public class ColorManager {
    
    private static final float[] STANDARD_COLOR = Color.RGBtoHSB(240, 240, 240, null);
    
    private Color getStandardColor() {
        return Color.getHSBColor(STANDARD_COLOR[0], STANDARD_COLOR[1], STANDARD_COLOR[2]);
    }
    
    public void changeColor(JPanel panel) {
        panel.setBackground(getStandardColor());
    }
    
    public void changeColor(Container tablePanel) {
        tablePanel.setBackground(getStandardColor());
    }
    
    public void changeColor(JFrame frame) {
        frame.getContentPane().setBackground(getStandardColor());
    }
    
    public void changeColor(JDialog dialog) {
        dialog.getContentPane().setBackground(getStandardColor());
    }
    
    public void changeColor(JFileChooser fileChooser) {
        fileChooser.setBackground(getStandardColor()); 
    }
    
}