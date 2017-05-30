
public class BrutusSearch {

	public int search(String pattern, String text) {
		char[] textChars = text.toCharArray();
		char[] patternChars = pattern.toCharArray();

		for (int i = 0; i <= textChars.length - patternChars.length; i++) {
			int j = 0;
			for (j = 0; j < patternChars.length; j++) {
				if (textChars[i + j] != patternChars[j]) {
					break;
				}
			}
			if (j == patternChars.length)
				return i;
		}

		return -1;
	}
}
