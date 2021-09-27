package insta;

import java.awt.Color;
import java.util.Random;

public interface RandomColor {

	public static RandomColor rgb() {
		return new RgbRandomColor();
	}
	public static RandomColor hsb() {
		return new HsbRandomColor();
	}
	public static RandomColor hsb2() {
		return new HsbRandomColor2();
	}
	public static RandomColor spectrum() {
		return new SpectrumRandomColor();
	}

	Color get();

	default Color[] getPair() {
		return new Color[] {get(), get()};
	}

	public static class RgbRandomColor implements RandomColor {
		private final Random rnd = new Random();

		@Override
		public Color get() {
			int red = rnd.nextInt(256);
			int green = rnd.nextInt(256);
			int blue = rnd.nextInt(256);

			return new Color(red, green, blue);
		}
	}

	public static class HsbRandomColor implements RandomColor {
		private final Random rnd = new Random();
		@Override
		public Color get() {
			final float hue = rnd.nextFloat();
			final float saturation = 0.9f;  // 1.0 for brilliant, 0.0 for dull
			final float luminance = 1.0f;   // 1.0 for brighter, 0.0 for black

			return Color.getHSBColor(hue, saturation, luminance);
		}
	}

	public static class HsbRandomColor2 implements RandomColor {
		private final Random rnd = new Random();
		@Override
		public Color get() {
			final float hue = rnd.nextFloat();
			final float saturation = 0.9f + rnd.nextFloat() / 10.0f;  // 1.0 for brilliant, 0.0 for dull
			final float luminance = 0.8f + rnd.nextFloat() / 4f;   // 1.0 for brighter, 0.0 for black

			return Color.getHSBColor(hue, saturation, luminance);
		}
	}


	public static class SpectrumRandomColor implements RandomColor {

		private final JsonColors jsonColors = new JsonColors();

		@Override
		public Color get() {
			return jsonColors.nextRandomColor();
		}

		@Override
		public Color[] getPair() {
			return jsonColors.nextRandomColors();
		}
	}
}
