/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {
	public int[] matchTable;

	public KMP(String pattern, String text) {
		this.matchTable = new int[pattern.length()];
		this.matchTable[0] = -1;
		this.matchTable[1] = 0;
		int j = 0;
		int pos = 2;

		while (pos < pattern.length()) {
			if (text.charAt(pos - 1) == text.charAt(j)) {
				this.matchTable[pos] = j + 1;
				pos++;
				j++;
			} else if (j > 0) {
				j = this.matchTable[j];
			} else {
				this.matchTable[pos] = 0;
				pos++;
			}
		}
	}

	/**
	 * Perform KMP substring search on the given text with the given pattern.
	 * 
	 * This should return the starting index of the first substring match if it
	 * exists, or -1 if it doesn't.
	 */
	public int search(String pattern, String text) {
		int s = 0;
		int t = 0;
		while (t + s < text.length()) {
			if (pattern.charAt(s) == text.charAt(t + s)) {
				s++;
				if (s == this.matchTable.length) {
					return t;
				}
			} else if (this.matchTable[s] == -1) {
				t = t + s + 1;
				s = 0;
			} else {
				t = t + s - this.matchTable[s];
				s = this.matchTable[s];
			}
		}
		return -1;
	}
}