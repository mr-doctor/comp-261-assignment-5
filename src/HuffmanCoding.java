import java.util.PriorityQueue;
import java.util.Queue;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {

	private HuffmanNode tree;

	/**
	 * This would be a good place to compute and store the tree.
	 */
	public HuffmanCoding(String text) {
		int[] freq = new int[256];
		char[] chars = text.toCharArray();

		for (char c : chars) {
			freq[c]++;
		}

		Queue<HuffmanNode> queue = new PriorityQueue<>((o1, o2) -> -o1.compareTo(o2));

		for (int i = 0; i < freq.length; i++) {
			if (freq[i] > 0) {
				queue.add(new HuffmanNode((char) i, freq[i], null, null));
			}
		}

		while (queue.size() > 1) {
			HuffmanNode a = queue.poll();
			HuffmanNode b = queue.poll();

			HuffmanNode c = new HuffmanNode('\0', a.frequency + b.frequency, a, b);
			queue.add(c);
		}
		tree = queue.poll();
	}

	/**
	 * Take an input string, text, and encode it with the stored tree. Should
	 * return the encoded text as a binary string, that is, a string containing
	 * only 1 and 0.
	 */
	public String encode(String text) {
		char[] chars = text.toCharArray();
		String[] table = buildTable(tree);

		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			sb.append(table[c]);
		}

		return sb.toString();
	}

	/**
	 * Take encoded input as a binary string, decode it using the stored tree,
	 * and return the decoded text as a text string.
	 */
	public String decode(String encoded) {
		StringBuilder output = new StringBuilder();

		for (int i = 0; i < encoded.length(); i++) {
			HuffmanNode n = tree;

			while (!n.isLeaf()) {
				boolean bit = Boolean.parseBoolean(String.valueOf(encoded.charAt(i)));
				i = Math.min(i + 1, encoded.length() - 1);
				if (bit) {
					n = n.right;
				} else {
					n = n.left;
				}
			}
			output.append(n.character);
		}
		return output.toString();
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't wan to. It is called on every run and its return
	 * value is displayed on-screen. You could use this, for example, to print
	 * out the encoding tree.
	 */
	public String getInformation() {
		return "";
	}

	private static String[] buildTable(HuffmanNode root) {
		String[] st = new String[256];
		buildTable(st, root, "");
		return st;
	}

	private static void buildTable(String st[], HuffmanNode x, String s) {
		if (x.isLeaf()) {
			st[x.character] = s;
			return;
		}
		buildTable(st, x.left, s + '0');
		buildTable(st, x.right, s + '1');
	}
}
