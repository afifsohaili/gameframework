package se350.afifsohaili.bejeweled;

import java.awt.Color;
import se350.afifsohaili.gameframework.Data;

/**
 * @author Afif Sohaili
 */
public class BejeweledData implements Data<Color>{
	private Color color;
	
	public BejeweledData(Color color) {
		this.color = color;
	}
	
	public BejeweledData(int rgb) {
		color = new Color(rgb);
	}
	
	@Override
	public Color getValue() {
		return new Color(color.getRed(), color.getGreen(), color.getBlue());
	}
	
	public boolean equals(BejeweledData data) {
		if (data == null || data.getValue().getRGB() != color.getRGB()) {
			return false;
		}
		return true;
	}
}