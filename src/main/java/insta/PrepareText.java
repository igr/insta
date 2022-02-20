package insta;

import jodd.io.FileUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PrepareText {

	public static void main(String[] args) throws IOException {
		String text = FileUtil.readString("text/LittlePrince.md");

		text = cutPrelude(text);
		List<String> words = convertTextToLines(text)
				.stream()
				.flatMap(s -> convertLineToWords(s).stream())
				.collect(Collectors.toList());

		printStats(words);

		Collections.reverse(words);
		StringBuilder out = new StringBuilder(text.length());
		words.forEach(it -> out.append(it).append("\n"));
		FileUtil.writeString("in.txt", out.toString().trim());
	}

	private static String cutPrelude(String text) {
		int ndx = text.indexOf("---");
		if (ndx < 0) {
			return text;
		}
		return text.substring(ndx + 3);
	}

	private static List<String> convertTextToLines(String text) {
		return Arrays.stream(text.split("\n"))
				.filter(it -> !it.isEmpty())
				.collect(Collectors.toList());
	}

	private static List<String> convertLineToWords(String line) {
		if (line.startsWith("#")) {
			return List.of(line);
		}
		return Arrays.stream(line.split("\\s+"))
				.filter(it -> !it.isEmpty())
				.collect(Collectors.toList());
	}

	private static void printStats(List<String> words) {
		int counter = 0;
		int maxLen = 0;
		String maxWord = "";

		for (String word : words) {
			counter++;
			if (word.length() > maxLen) {
				maxLen = word.length();
				maxWord = word;
			}
		}
		System.out.println("Total words: " + counter);
		System.out.println("Max word: " + maxWord);
		System.out.println("Max word len: " + maxLen);
	}
}
