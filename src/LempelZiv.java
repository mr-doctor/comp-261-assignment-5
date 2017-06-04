// All of this code is my own, but
// some of the logic was from https://github.com/rongjiwang/Algorithms-and-Data-Structures/blob/master/StringSearchAndCompression/src/LempelZiv.java
import java.util.ArrayList;

public class LempelZiv {

	/**
	 * View window used to search through the string. Arbitrary value chosen.
	 */
	private static final int VIEW_RANGE = 144;

	public ArrayList<Tuple> compress(String text) {
		// Initialise values as empty
		int cursor = 0;
		int lookAhead = 0;
		ArrayList<Tuple> textCompressed = new ArrayList<>();

		while (cursor < text.length()) {
			// Clamp values to 0 and the input length, respectively
			lookAhead = (cursor + lookAhead >= text.length()) ? text.length() : cursor + lookAhead;
			int matchPrevious = (cursor - VIEW_RANGE < 0) ? 0 : cursor - VIEW_RANGE;
			int matchLength = 1;
			int matchLocation = 0;
			String str = (cursor == 0) ? "" : text.substring(matchPrevious, cursor);

			// Search for next character to match
			String next = text.substring(cursor, cursor + matchLength);
			// If match is found
			if (str.contains(next)) {
				matchLength++;
				while (matchLength <= lookAhead) {
					if (cursor + matchLength >= text.length() - 1) {
						matchLength = 1;
						break;
					}
					// Get next match
					next = text.substring(cursor, cursor + matchLength);
					matchLocation = str.indexOf(next);
					// Increment if within bounds, otherwise break
					if (cursor + matchLength < text.length() && matchLocation > -1) {
						matchLength++;
					} else {
						break;
					}
				}
				matchLength--;
				matchLocation = str.indexOf(text.substring(cursor, cursor + matchLength));
				// Move along to the next match
				cursor += matchLength;

				char c = text.charAt(cursor);

				// Sets the distance back to the match location
				int offsetValue = VIEW_RANGE - matchLocation;
				if ((VIEW_RANGE + matchLength) >= cursor) {
					offsetValue = cursor - matchLocation - matchLength;
				}
				textCompressed.add(new Tuple(offsetValue, matchLength, c));
			} else {
				// Create new tuple, since no match was found
				char c = text.charAt(cursor);
				textCompressed.add(new Tuple(0, 0, c));
			}
			cursor++;
		}
		return textCompressed;
	}

	public String convertCompressedToString(ArrayList<Tuple> compressed) {
		// Create a String of the encoded tuples
		StringBuilder output = new StringBuilder();
		for (Tuple currentTuple : compressed) {
			output.append(currentTuple.tupleString());
		}
		return output.toString();
	}

	public String decompress(ArrayList<Tuple> compressed) {
		StringBuilder output = new StringBuilder();
		// Check every tuple
		for (Tuple currentTuple : compressed) {
			if (currentTuple.length == 0) {
				output.append(currentTuple.character);
			} else {
				for (int i = 0; i < currentTuple.length; i++) {
					output.append(output.charAt(output.length() - currentTuple.offset));
				}
				output.append(currentTuple.character);
			}
		}
		return output.toString();
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