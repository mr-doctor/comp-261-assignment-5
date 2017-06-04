/**
 * A tuple object that contains the data of the string, offset, and length.
 * 
 * @author Daniel
 *
 */
class Tuple {
	public final int offset;
	public final int length;
	public final char character;

	Tuple(int offset, int length, char character) {
		this.offset = offset;
		this.length = length;
		this.character = character;
	}

	public String tupleString() {
		return "" + offset + length + character;
	}
}