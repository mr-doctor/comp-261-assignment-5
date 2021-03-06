
public class HuffmanNode implements Comparable<HuffmanNode> {

	public final char character;
	public final int frequency;
	public final HuffmanNode left;
	public final HuffmanNode right;

	public HuffmanNode(char c, int f, HuffmanNode left, HuffmanNode right) {
		this.character = c;
		this.frequency = f;
		this.left = left;
		this.right = right;
	}

	public boolean isLeaf() {
		return (this.left == null && this.right == null);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(print("", true));

		return sb.toString();
	}

	private String print(String prefix, boolean isLeaf) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix).append(isLeaf ? "`-- " : "|-- ").append(character == '\0' ? "." : boxChar()).append("\n");
		if (left != null) {
			sb.append(left.print(prefix + (isLeaf ? "    " : "|    "), false));
		}

		if (right != null) {
			sb.append(right.print(prefix + (isLeaf ? "    " : "|    "), true));
		}
		return sb.toString();
	}

	private String boxChar() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (character == '\n') {
			sb.append("\\n");
		} else {
			sb.append(character);
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int compareTo(HuffmanNode other) {
		return this.frequency - other.frequency;
	}
}
