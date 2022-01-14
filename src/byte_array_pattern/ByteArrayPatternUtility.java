package byte_array_pattern;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
/**
 * Makes byte array from an object array and vice versa, in respect to the predefined sequence/pattern.
 * @see Pattern For more info on the pattern and sequence. 
 * */
public class ByteArrayPatternUtility {
	private final Pattern pattern;
	/**
	 * @param pattern Text defining the pattern for the byte array.
	 * @see Pattern See this for how to define pattern. 
	 * */
	public ByteArrayPatternUtility(String pattern) {
		this.pattern = new Pattern(pattern);
	}
	public Object[] toData(byte[] message) throws Exception {
		if(message.length < this.pattern.getMessageLength()) {throw new Exception("message byte array given is smaller than the pattern's message length");}
		ByteBuffer buffer = ByteBuffer.wrap(message)
				.order(this.pattern.isLittleEndian() ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
		Object[] parsedMessage = new Object[pattern.getStructure().length];
		int index1 = 0, index2 = 0;
		for(Data d : this.pattern.getStructure()) {
			if(d.equals(Data.BYTE)) {
				parsedMessage[index1] = buffer.get(index2);
			}else if(d.equals(Data.SHORT)) {
				parsedMessage[index1] = buffer.getShort(index2);
			}else if(d.equals(Data.INTEGER)) {
				parsedMessage[index1] = buffer.getInt(index2);
			}else if(d.equals(Data.LONG)) {
				parsedMessage[index1] = buffer.getLong(index2);
			}else if(d.equals(Data.FLOAT)) {
				parsedMessage[index1] = buffer.getFloat(index2);
			}else if(d.equals(Data.DOUBLE)) {
				parsedMessage[index1] = buffer.getDouble(index2);
			}else {//Array
				byte[] b = new byte[d.getSize()];
				for(int i = 0; i< b.length; i++) {b[i] = buffer.get(index2+i);}
				parsedMessage[index1] = b;
			}
			index1++;
			index2 = index2 + d.getSize();
		}
		return parsedMessage;
	}
	public byte[] toByteArray(Object[] data) throws Exception {
		if(data.length > this.pattern.getMessageLength()) {throw new Exception("Data of greater length than message of pattern, " + data.length);}
		ByteBuffer buffer = ByteBuffer
				.allocate(this.pattern.getMessageLength())
				.order(this.pattern.isLittleEndian() ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
		int index = 0;
		for(Data d : this.pattern.getStructure()) {
			if(d.equals(Data.BYTE)) {
				buffer.put((byte)data[index]);
			}else if(d.equals(Data.SHORT)) {
				buffer.putShort((short)data[index]);
			}else if(d.equals(Data.INTEGER)) {
				buffer.putInt((int)data[index]);
			}else if(d.equals(Data.LONG)) {
				buffer.putLong((long)data[index]);
			}else if(d.equals(Data.FLOAT)) {
				buffer.putFloat((float)data[index]);
			}else if(d.equals(Data.DOUBLE)) {
				buffer.putDouble((double)data[index]);
			}else {//Array
				buffer.put((byte[])data[index]);
			}
			index++;
		}
		return buffer.array();	
	}
	public Pattern getPattern() {
		return pattern;
	}
}
