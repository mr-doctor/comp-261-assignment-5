
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
		sb.append(this.character);
		sb.append("\n");
		if (this.left != null) {
			sb.append(this.left.toString());
			sb.append("\n");
		}
		if (this.right != null) {
			sb.append(this.right.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	@Override
	public int compareTo(HuffmanNode other) {
		return this.frequency - other.frequency;
	}
}
