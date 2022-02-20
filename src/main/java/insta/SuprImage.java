package insta;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SuprImage {
	public byte[] createImage(String text) throws IOException {
		boolean isChapter = false;
		if (text.startsWith("## ")) {
			text = text.substring(3);
			isChapter = true;
		}

		BufferedImage img = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

		final Color backgroundColor;
		if (isChapter) {
			backgroundColor = new Color(0x4d194d);
		} else {
			backgroundColor = new Color(0x272640);
		}
		final Color penColor = new Color(0xffffff);
		final Color titleColor = new Color(0x065a60);

		// draw

		g.setColor(backgroundColor);
		g.fill(new Rectangle(0, 0, 512, 512));

//		Font font = new Font("AppleMyungjo", Font.BOLD, 250);
//		Font font = new Font("Georgia", Font.BOLD, 250);
		Font font = new Font("IBM Plex Serif", Font.BOLD, 250);
		g.setFont(font);

		Font closestFont = scaleFont(g, font, text);
		g.setFont(closestFont);

		FontMetrics fm = g.getFontMetrics();
		final Rectangle2D stringBounds = fm.getStringBounds(text, g);
		int x = (int) ((512 - stringBounds.getWidth()) / 2d);
		int y = 256 - (fm.getHeight() / 2) + fm.getAscent();

		g.setColor(penColor);
		g.drawString(text, x, y);  // magic, use + 20 to center

		if (isChapter) {
			g.setColor(titleColor);
			g.fill(new Rectangle(0, 0, 512, 40));
			g.fill(new Rectangle(0, 512-40, 512, 512));
		}
		g.dispose();

		var baos = new ByteArrayOutputStream();
		ImageIO.write(img, "png", baos);
		return baos.toByteArray();
	}

	private static Font scaleFont(Graphics2D g2D, Font font, String text) {
		g2D.setFont(font);

		double scale = calcScale(
				g2D.getFontMetrics().stringWidth(text),
				g2D.getFontMetrics().getHeight(),
				512, 512);

		return g2D.getFont().deriveFont(AffineTransform.getScaleInstance(scale, scale));
	}

	private static double calcScale(int width, int height, int targetWidth, int targetHeight) {
		double scaleX = (double) targetWidth / width;
		double scaleY = (double) targetHeight / height;
		return Math.min(scaleX, scaleY);
	}
}