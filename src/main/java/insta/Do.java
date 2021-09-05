package insta;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.github.instagram4j.instagram4j.responses.accounts.LoginResponse;
import com.github.instagram4j.instagram4j.utils.IGChallengeUtils;
import com.oblac.nomen.Casing;
import com.oblac.nomen.Nomen;
import io.github.cdimascio.dotenv.Dotenv;
import jodd.io.FileUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class Do {

	public static final String COUNTER_TXT = "counter.txt";
	static Scanner scanner = new Scanner(System.in);

	static Callable<String> inputCode = () -> {
		System.out.print("Please input code: ");
		return scanner.nextLine();
	};

	static IGClient.Builder.LoginHandler challengeHandler = Do::accept;

	public static void main(String[] args) throws IOException {
		Dotenv.configure().ignoreIfMissing().load()
				.entries()
				.forEach(e -> System.setProperty(e.getKey(), e.getValue()));

		String text = FileUtil.readString(COUNTER_TXT).trim();
		int counter = Integer.parseInt(text) + 1;
		text = String.valueOf(counter);
		FileUtil.writeString(COUNTER_TXT, text);

		byte[] imageBytes = new SuprImage().createImage(text);

		FileUtil.writeBytes("foo.jpg", imageBytes);

		postImage(imageBytes, caption());

		System.out.println("Done...");
	}

	private static String caption() {
		return Nomen.est()
				.superb()
				.adjective()
				.color()
				.noun()
				.withSeparator(" ").withCasing(Casing.CAPITALIZE)
				.get();
	}

	private static void postImage(final byte[] imageBytes, final String caption) throws IGLoginException {
		System.out.println("Login in...");

		IGClient client = IGClient.builder()
				.username("oblacoder")
				.password(System.getProperty("INSTA_PASS"))
				.onChallenge(challengeHandler)
				.login();

		System.out.println("Oblacoder is in!");

		client.actions()
				.timeline()
				.uploadPhoto(imageBytes, caption)
				.thenAccept(response -> System.out.println("Uploaded photo " + response.getMedia().getId()))
				.exceptionally(tr -> {
					tr.printStackTrace();
					return null;
				})
				.join();
	}

	private static LoginResponse accept(IGClient client, LoginResponse response) {
		return IGChallengeUtils.resolveChallenge(client, response, inputCode);
	}
}
