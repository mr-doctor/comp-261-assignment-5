import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

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
		long startTree = System.currentTimeMillis();
		char[] chars = text.toCharArray();
		Map<Character, Integer> freq = new HashMap<>();
		
		for (char c : chars) {
			if (freq.containsKey(c)) {
				freq.replace(c, freq.get(c)+1);
			} else {
				freq.put(c, 1);
			}
		}

		MinPQ<HuffmanNode> queue = new MinPQ<>();

		for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
			if (entry.getValue() > 0) {
				queue.insert(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));
			}
		}
		while (queue.size() > 1) {
			HuffmanNode a = queue.delMin();
			HuffmanNode b = queue.delMin();

			HuffmanNode c = new HuffmanNode('\0', a.frequency + b.frequency, a, b);
			queue.insert(c);
		}
		tree = queue.delMin();
		System.out.println("tree build = " + (System.currentTimeMillis() - startTree));
		System.out.println(tree);
	}

	/**
	 * Take an input string, text, and encode it with the stored tree. Should
	 * return the encoded text as a binary string, that is, a string containing
	 * only 1 and 0.
	 */
	public String encode(String text) {
		char[] chars = text.toCharArray();
		Map<Character, String> table = buildTable(tree);

		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			sb.append(table.get(c));
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
				char c = encoded.charAt(i);
				if (c == '1') {
					n = n.right;
				} else {
					n = n.left;
				}
				i++;
			}
			output.append(n.character);
			i--;
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

	private static Map<Character, String> buildTable(HuffmanNode root) {
		Map<Character, String> strings = new HashMap<>();
		buildTable(strings, root, "");
		return strings;
	}

	private static void buildTable(Map<Character, String> st, HuffmanNode x, String s) {
		if (x.isLeaf()) {
			st.put(x.character, s);
			return;
		}
		buildTable(st, x.left, s + '0');
		buildTable(st, x.right, s + '1');
	}

}
