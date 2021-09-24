package insta;

import jodd.io.FileUtil;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import lombok.Data;
import lombok.SneakyThrows;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class JsonColors {

	public static final String SPECTRUM_JSON_FILE_NAME = "spectrum.json";

	@Data
	public static class SpectrumColor {
		private int id;
		private int day;
		private int month;
		private String color;
		private boolean used;
	}

	private final List<SpectrumColor> colors;

	@SneakyThrows
	public JsonColors() {
		colors = load();
	}

	private List<SpectrumColor> load() throws IOException {
		String json = FileUtil.readString(SPECTRUM_JSON_FILE_NAME);
		return JsonParser.create()
				.parseAsList(json, SpectrumColor.class)
				.stream()
				.filter(it -> !it.used)
				.toList();
	}

	@SneakyThrows
	public void update() {
		var json = JsonSerializer.create().serialize(colors);
		FileUtil.writeString(SPECTRUM_JSON_FILE_NAME, json);
	}

	public Color nextRandomColor() {
		int ndx = new Random().nextInt(colors.size());
		return Color.decode(colors.get(ndx).getColor());
	}

	public Color[] nextRandomColors() {
		int ndx = new Random().nextInt(colors.size());
		var color1 = Color.decode(colors.get(ndx).getColor());

		ndx += colors.size() / 2;
		if (ndx >= colors.size()) {
			ndx -= colors.size();
		}
		var color2 = Color.decode(colors.get(ndx).getColor());

		return new Color[]{color1, color2};
	}
}
