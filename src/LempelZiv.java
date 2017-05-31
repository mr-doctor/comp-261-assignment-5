/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {
	
	private static final int VIEW_RANGE = 12;
	
	/**
	 * Take uncompressed input as a text string, compress it, and return it as a
	 * text string.
	 */
	public String compress(String input) {
		char[] chars = input.toCharArray();
		
		int cursor = 0;
		while (cursor < input.length()) {
			int lookAhead = 0;
			int previousMatch = 0;
			while (true) {
				int match = buildString(chars, cursor, lookAhead)
						.compareTo(buildString(chars, 
								(cursor < VIEW_RANGE) ? 0 : cursor - VIEW_RANGE, 
								cursor - 1));
				if (match == 0) {
					previousMatch = match;
					lookAhead++;
				}
			}
		}
		return "";
	}
	
	private String buildString(char[] chars, int cursor, int distance) {
		StringBuilder sb = new StringBuilder();
		for (int i=cursor; i<cursor + distance; i++) {
			sb.append(chars[i]);
		}
		return sb.toString();
	}

	/**
	 * Take compressed input as a text string, decompress it, and return it as a
	 * text string.
	 */
	public String decompress(String compressed) {
		// TODO fill this in.
		return "";
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't want to. It is called on every run and its return
	 * value is displayed on-screen. You can use this to print out any relevant
	 * information from your compression.
	 */
	public String getInformation() {
		return "";
	}
}
