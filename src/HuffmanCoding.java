import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

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
		
		// Custom queue used from https://github.com/kevin-wayne/algs4/blob/master/src/main/java/edu/princeton/cs/algs4/MinPQ.java
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
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
	}

	/**
	 * Take an input string, text, and encode it with the stored tree. Should
	 * return the encoded text as a binary string, that is, a string containing
	 * only 1 and 0.
	 */
	public byte[] encode(String text) {
		char[] chars = text.toCharArray();
		Map<Character, String> table = buildTable(tree);

		StringBuilder bitString = new StringBuilder();
		for (char c : chars) {
			bitString.append(table.get(c));
		}
		
		// Allocate 4 bits for the length, 1/8 bits for each byte, + 1 for remainder
		ByteBuffer b = ByteBuffer.allocate(4 + bitString.length() / 8 + 1);
		b.putInt(bitString.length());
		byte[] backingArray = b.array();
		
		// Go over every bit, and encode it to a byte
		for (int pos = 0; pos < bitString.length(); pos++){
			// Every 8 bits, increment bytes by 1 and reset bits to 0
			int byteIndex = pos / 8;
			int bitIndex = pos % 8;
			
			// Edit the bit at the index
			if (bitString.charAt(pos) == '1'){
				backingArray[byteIndex + 4] |= 1 << bitIndex;
			}
		}
		
		return backingArray;
	}

	/**
	 * Take encoded input as a binary array, decode it using the stored tree,
	 * and return the decoded text as a text string.
	 */
	public String decode(byte[] encoded) {
		ByteBuffer bytes = ByteBuffer.wrap(encoded);
		int length = bytes.getInt();
		
		StringBuilder bitString = new StringBuilder();
		// Converts the encoded array of bytes to a string of bits
		for (int pos = 0; pos < length; pos++){
			int byteIndex = pos / 8;
			int bitIndex = pos % 8;
			
			// Selects the bit to be parsed using an AND mask
			int mask = 1 << bitIndex;
			// Isolates the bit
			boolean bit = (encoded[byteIndex + 4] & mask) != 0;
			// Check the bit and write accordingly
			if (bit){
				bitString.append('1');
			} else {
				bitString.append('0');
			}
		}
		
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < bitString.length(); i++) {
			HuffmanNode n = tree;
			while (!n.isLeaf()) {
				char c = bitString.charAt(i);
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
