import java.nio.ByteBuffer;

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

	public void addTo(ByteBuffer b) {
		b.putInt(offset);
		b.putInt(length);
		b.putChar(character);
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d, %c)", offset, length, character);
	}
}