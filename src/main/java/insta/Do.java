package insta;

import io.github.cdimascio.dotenv.Dotenv;
import jodd.http.HttpRequest;

import java.io.IOException;

public class Do {
	public static void main(String[] args) throws IOException {
		checkNetOrDie();

		Dotenv.configure().ignoreIfMissing()
				.load()
				.entries()
				.forEach(e -> System.setProperty(e.getKey(), e.getValue()));

		Text text = new Text();

		var image = new SuprImage().createImage(text.get());
		image.savePng("story/" + text.index() + ".png");

		InstaPost instagram = new InstaPost();
		instagram.postImage(image.toJpegBytes(), "#" + text.get());

		System.out.println("Word " + text.index() + ": '" + text.get() + "' posted");
		text.commit();
		System.out.println("Done.\n\n\n");
	}

	private static void checkNetOrDie() {
		HttpRequest.get("http://google.com").send();
	}

}
