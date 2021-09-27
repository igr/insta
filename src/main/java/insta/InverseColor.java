package insta;

import java.awt.Color;

public class InverseColor {

	public Color rgb(Color color) {
		int red = (~color.getRed()) & 0xff;
		int blue = (~color.getBlue()) & 0xff;
		int green = (~color.getGreen()) & 0xff;

		return new Color(red, green, blue);
	}

	public Color hsb(Color color) {
		float[] hsv = new float[3];
		Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
		hsv[0] += 0.5f;
		if (hsv[0] > 1.0f) {
			hsv[0] -= 1.0f;
		}
		return Color.getHSBColor(hsv[0], hsv[1], hsv[2]);
	}

	public Color hsb2(Color color) {
		float[] hsv = new float[3];
		Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
		hsv[0] += 0.5f;
		if (hsv[0] > 1.0f) {
			hsv[0] -= 1.0f;
		}
		hsv[2] = 0.25f;
		return Color.getHSBColor(hsv[0], hsv[1], hsv[2]);
	}
}
