import java.util.ArrayList;
import java.util.Iterator;

/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {

	private static final int VIEW_RANGE = 12;
	private ArrayList<Tuple> encoded = new ArrayList<>();
	
	public String compress(String input) {
		// Initialise values as empty
		int cursor = 0;
		int matchPrevious = 0;
		int lookAhead = 0;
		int matchLength = 0;
		int matchLocation = 0;
		String str = "";
		
		while (cursor < input.length()) {
			// Clamp values to 0 and the input length, respectively
			lookAhead = (cursor + lookAhead < input.length()) ? cursor + lookAhead : input.length();
			matchPrevious = (cursor - VIEW_RANGE >= 0) ? cursor - VIEW_RANGE : 0;
			
			if (cursor == 0) {
				// Resets the string to be empty
				str = "";
			} else {
				str = input.substring(matchPrevious, cursor);
			}
			matchLength = 1;
			// Search for next character to match
			String target = input.substring(cursor, cursor + matchLength);
			// If match is found
			if (str.contains(target)) {
				matchLength++;
				while (matchLength <= lookAhead) {
					// Get next match
					target = input.substring(cursor, cursor + matchLength);
					matchLocation = str.indexOf(target);
					// Increment if within bounds, otherwise break
					if (matchLocation != -1 && (cursor + matchLength) < input.length()) {
						matchLength++;
					} else {
						break;
					}
				}
				matchLength--;
				matchLocation = str.indexOf(input.substring(cursor, cursor + matchLength));
				cursor += matchLength;
				// Sets the distance back to the match location
				int offsetValue = (cursor < (VIEW_RANGE + matchLength)) 
						? cursor - matchLocation - matchLength
						: VIEW_RANGE - matchLocation;
				String nextChar = input.substring(cursor, cursor + 1);
				encoded.add(new Tuple(offsetValue, matchLength, nextChar));
			} else {
				// Create new tuple, since no match was found
				String nextChar = input.substring(cursor, cursor + 1);
				encoded.add(new Tuple(0, 0, nextChar));
			}
			cursor++;
		}
		StringBuilder output = new StringBuilder();
		for (Tuple currentTuple : encoded) {
			output.append(currentTuple.toString()).append("\n");
		}
		return output.toString();
	}

	public String decompress(String tags) {
		StringBuilder output = new StringBuilder();
		for (Tuple currentTuple : encoded) {
			if (currentTuple.length == 0) {
				output.append(currentTuple.next);
			} else {
				for (int i = 0; i < currentTuple.length; i++) {
					output.append(output.charAt(output.length() - currentTuple.offset));
				}
				output.append(currentTuple.next);
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

class Tuple {
	public final int offset;
	public final int length;
	public final String next;

	Tuple(int offset, int length, String next) {
		this.offset = offset;
		this.length = length;
		this.next = next;
	}

	@Override
	public String toString() {
		return "[" + offset + "," + length + "," + next + "]";
	}

}