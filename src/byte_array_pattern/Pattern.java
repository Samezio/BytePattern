package byte_array_pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains pattern.
 * <h1>Pattern syntax</h1>
 * <b>{byte order};{sequence}</b>
 * <br>e.g.: <b>l;b:i:d:f:a[10]</b> for little endian sequence of a byte, integer, double, float, array of 10.
 * <ul>
 * <li><ul>Byte order: <li>l/L - Little endian</li><li>b/B - Big endian</li></ul></li>
 * <li>Sequence: {x}:{x}:{x}:....<ul>    where x is
 * <li>b/B - Byte (1byte)</li>
 * <li>s/S - Short (2byte)</li>
 * <li>i/I - Integer (4byte)</li>
 * <li>l/L - Long (8byte)</li>
 * <li>f/F - Float (4byte)</li>
 * <li>d/D - Double (8byte)</li>
 * <li>a/A[size] - Byte Array ({size} byte)</li>
 * </ul></li>
 * */
public class Pattern {
	private final boolean isLittleEndian;
	private final int messageLength;
	private final Data[] structure;
	/**
	 * @param pattern Text defining the pattern.
	 * */
	public Pattern(String pattern) throws IllegalArgumentException {
		super();
		pattern = pattern.replaceAll("//s+", "");
		String[] parts = pattern.split(";");
		
		if(parts.length != 2) {throw new IllegalArgumentException("Invalid pattern.");}
		
		boolean isLittleEndian;
		if(parts[0].equalsIgnoreCase("l")) {//Little Endian
			isLittleEndian = true;
		}else if(parts[0].equalsIgnoreCase("b")) {//Big Endian
			isLittleEndian = false;
		}else {
			throw new IllegalArgumentException("Invalid Endian(" + parts[0] + ") in pattern.");
		}
		
		List<Data> sequence = new ArrayList<>();
		for(String d : parts[1].split(":")) {
			if(d.equalsIgnoreCase("b")) {sequence.add(Data.BYTE);}//Byte : 1byte
			else if(d.equalsIgnoreCase("s")) {sequence.add(Data.SHORT);}//Short : 2byte
			else if(d.equalsIgnoreCase("i")) {sequence.add(Data.INTEGER);}//Integer : 4byte
			else if(d.equalsIgnoreCase("l")) {sequence.add(Data.LONG);}//Long : 8byte
			else if(d.equalsIgnoreCase("f")) {sequence.add(Data.FLOAT);}//Float : 4byte
			else if(d.equalsIgnoreCase("d")) {sequence.add(Data.DOUBLE);}//Double : 8byte
			else if(d.contains("a[")||d.contains("A[")) {//Byte array: ?bytes
				try {
					sequence.add(
							Data.ARRAY(
									Integer.parseInt(
											d.substring(
													d.indexOf('[')+1, 
													d.indexOf(']')
													)
											)
									)
							);
				}catch(NumberFormatException e) {
					throw new IllegalArgumentException("Invalid array length in " + d);
				}
			}else {
				throw new IllegalArgumentException("Invalid token in pattern sequence " + d);
			}
		}
		int messageLength = sequence.stream().map(data->data.getSize()).reduce(0, (a,b)-> a+b);
		this.isLittleEndian = isLittleEndian;
		this.messageLength = messageLength;
		this.structure = sequence.toArray(new Data[sequence.size()]);
	}
	/**
	 * @return boolean indicating the whether the pattern is little endian or not.
	 */
	public boolean isLittleEndian() {
		return isLittleEndian;
	}
	/**
	 * @return the messageLength
	 */
	public int getMessageLength() {
		return messageLength;
	}
	/**
	 * @return the structure i.e array of {@link Data}
	 */
	public Data[] getStructure() {
		return structure;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isLittleEndian ? 1231 : 1237);
		result = prime * result + messageLength;
		result = prime * result + Arrays.hashCode(structure);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pattern other = (Pattern) obj;
		if (isLittleEndian != other.isLittleEndian)
			return false;
		if (messageLength != other.messageLength)
			return false;
		if (!Arrays.equals(structure, other.structure))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Pattern [isLittleEndian=" + isLittleEndian + ", messageLength=" + messageLength + ", structure="
				+ Arrays.toString(structure) + "]";
	}
	
	private Pattern(boolean isLittleEndian, int messageLength, Data[] structure) {
		super();
		this.isLittleEndian = isLittleEndian;
		this.messageLength = messageLength;
		this.structure = structure;
	}
	/**
	 * @param pattern {@link Pattern} of which the endian is to be change.
	 * @return {@link Pattern} with the opposite endian of the given {@link Pattern}
	 * <b>Note:</b> this will not change the Endian of the passed {@link Pattern} object but will return a new {@link Pattern} object
	 * */
	public static Pattern changeEndian(Pattern pattern) {
		return new Pattern(!pattern.isLittleEndian, pattern.getMessageLength(), pattern.getStructure());
	}
}
