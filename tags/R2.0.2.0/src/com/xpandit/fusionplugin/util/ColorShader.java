package com.xpandit.fusionplugin.util;

import java.awt.Color;

/**
 * A color shader that creates a number of distinct shades of a color
 * 
 * @author bffaustino
 * 
 */
public class ColorShader {

	/**
	 * Number of shades to create. Used in the fraction that darkens or lightens the base color
	 */
	private int numberShades;
	
	/**
	 * Counter of the darker shades already created
	 */
	private int currentDarkShade;
	
	/**
	 * Counter of the lighter shades already created
	 */
	private int currentLightShade;
	
	/**
	 * The base color of the color shader
	 */
	private Color baseColor;
	
	/**
	 * Last darker color generated by the color shader
	 */
	private Color lastDarkerColor;
	
	/**
	 * Last lighter color generated by the color shader
	 */
	private Color lastWhiterColor;

	/**
	 * ColorShader constructor
	 * 
	 * @param numberShades The number of shades of the color shader
	 * @param hexColor The hexadecimal of the color in the hexadecimal format #FFFFFF, without the #
	 */
	public ColorShader(int numberShades, String hexColor) {
		this.numberShades = numberShades;
		this.currentLightShade = 0;
		this.currentDarkShade = 0;
		this.baseColor = Color.decode("#" + hexColor);
	}
	
	/**
	 * Method that returns the next shade color to be used. It may return a lighter or darker shade. Each one has 
	 * a limit of numberShades to return.
	 * 
	 * @param lighter True or false, telling the method if it is supposed to return a lighter or darker shade
	 * @param isEnabled Tells the method if it has to return a shade or just the base color, enabling the user
	 * to choose if he wants to use the shader or not
	 * @return The hexadecimal value of the color to be used
	 */
	public String getNextShade(boolean lighter, boolean isEnabled){
		if(isEnabled)
			if(lighter){
				// if its still inside the number of lighter shades interval, increments the number of light shades used,
				// calculates the percentage by dividing the current number of calculated light shades by the total number of
				// possible shades and calls the method that calculates the lighter shade based on the percentage just calculated
				if(currentLightShade <= numberShades){
					currentLightShade++;
					double frac = (double) currentLightShade / (double) numberShades;
					lastWhiterColor = setLighter(frac);
				}
	
				return getHex(lastWhiterColor);
			}
			else {
				// if its still inside the number of darker shades interval, increments the number of dark shades used,
				// calculates the percentage by dividing the current number of calculated dark shades by the total number of
				// possible shades and calls the method that calculates the darker shade based on the percentage just calculated
				if(currentDarkShade <= numberShades){
					
					currentDarkShade++;
					double frac = (double) currentDarkShade / (double) numberShades;
					lastDarkerColor = setDarker(frac);
				}
	
				return getHex(lastDarkerColor);
			}
		else return getHex(baseColor);
	}
	
	/**
	 * Returns the hexadecimal value of the color in the input
	 * 
	 * @param currentColor Color to be converted to a hexadecimal value
	 * @return The hexadecimal value of the color that was given in the input of the method
	 */
	private String getHex(Color currentColor){
		String hex = Integer.toHexString(currentColor.getRGB());
		return hex.substring(2, hex.length());
	}
	
	/**
	 * Returns a lighter shade of the base color defined in the color shader. It essentially adds to the base color's
	 * red, green and blue components a percentage of the maximum possible value for the corresponding color component.
	 * 
	 * @param fraction The percentage to be applied in the maximum possible value for the red, green or blue color component
	 * @return The new Color for the lighter shade created
	 */
	private Color setLighter(double fraction){
		int red = (int) Math.round(Math.min(baseColor.getRed() + 255 * fraction, 255));
		int green = (int) Math.round(Math.min(baseColor.getGreen() + 255 * fraction, 255));
	    int blue = (int) Math.round(Math.min(baseColor.getBlue() + 255 * fraction, 255));
	    
	    return new Color(red, green, blue, baseColor.getAlpha());
	}
	
	/**
	 * Returns a darker shade of the base color defined in the color shader. It essentially subtracts to the base color's
	 * red, green and blue components a percentage of the maximum possible value for the corresponding color component.
	 * 
	 * @param fraction The percentage to be applied in the maximum possible value for the red, green or blue color component
	 * @return The new Color for the darker shade created
	 */
	private Color setDarker(double fraction){
		int red = (int) Math.round(Math.max(0, baseColor.getRed() - 255 * fraction));
		int green = (int) Math.round(Math.max(0, baseColor.getGreen() - 255 * fraction));
	    int blue = (int) Math.round(Math.max(0, baseColor.getBlue() - 255 * fraction));
	    
	    return new Color(red, green, blue, baseColor.getAlpha());
	}
}
