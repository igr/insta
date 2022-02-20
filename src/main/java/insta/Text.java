package insta;

import jodd.io.FileUtil;

import java.io.IOException;
import java.io.UncheckedIOException;

public class Text {

	public static final String COUNTER_TXT = "counter.txt";

	private final String[] lines;
	private final int counter;

	public Text() throws IOException {
		lines = FileUtil.readLines("in.txt");
		counter = Integer.parseInt(FileUtil.readString(COUNTER_TXT).trim());
	}

	public String get() {
		return lines[counter];
	}

	public void commit() {
		try {
			FileUtil.writeString(COUNTER_TXT, String.valueOf(counter + 1));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public int index() {
		return counter;
	}
}
