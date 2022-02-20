package insta;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.github.instagram4j.instagram4j.responses.accounts.LoginResponse;
import com.github.instagram4j.instagram4j.utils.IGChallengeUtils;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class InstaPost {

	Scanner scanner = new Scanner(System.in);

	Callable<String> inputCode = () -> {
		System.out.print("Please input code: ");
		return scanner.nextLine();
	};

	IGClient.Builder.LoginHandler challengeHandler = this::accept;

	public void postImage(final byte[] imageBytes, final String caption) throws IGLoginException {
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

	private LoginResponse accept(IGClient client, LoginResponse response) {
		return IGChallengeUtils.resolveChallenge(client, response, inputCode);
	}

}
