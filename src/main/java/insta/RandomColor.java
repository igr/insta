package insta;

import java.awt.Color;
import java.util.Random;

public interface RandomColor {

	static RandomColor rgb() {
		return new RgbRandomColor();
	}
	static RandomColor hsb() {
		return new HsbRandomColor();
	}
	static RandomColor spectrum() {
		return new SpectrumRandomColor();
	}

	Color get();

	default Color[] getPair() {
		return new Color[] {get(), get()};
	}

	class RgbRandomColor implements RandomColor {
		private final Random rnd = new Random();

		@Override
		public Color get() {
			int red = rnd.nextInt(256);
			int green = rnd.nextInt(256);
			int blue = rnd.nextInt(256);

			return new Color(red, green, blue);
		}
	}

	class HsbRandomColor implements RandomColor {
		private final Random rnd = new Random();
		@Override
		public Color get() {
			final float hue = rnd.nextFloat();
			final float saturation = 0.9f;  // 1.0 for brilliant, 0.0 for dull
			final float luminance = 1.0f;   // 1.0 for brighter, 0.0 for black

			return Color.getHSBColor(hue, saturation, luminance);
		}
	}

	class SpectrumRandomColor implements RandomColor {

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
