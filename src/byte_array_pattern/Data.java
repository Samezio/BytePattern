package byte_array_pattern;

/**
 * POJO class to encapsulate Data type for {@link Pattern}.
 * */
public class Data {
	private final int size;
	private final boolean isDecimal;
	/**
	 * @param Size of data
	 * @param isDecimal isDecimal
	 * */
	private Data(int size, boolean isDecimal) {
		this.size = size;
		this.isDecimal = isDecimal;
	}
	/**
	 * @return the size of the Data.
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @return the boolean indicating whether the Data is decimal or not.
	 */
	public boolean isDecimal() {
		return isDecimal;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isDecimal ? 1231 : 1237);
		result = prime * result + size;
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
		Data other = (Data) obj;
		if (isDecimal != other.isDecimal)
			return false;
		if (size != other.size)
			return false;
		return true;
	}
	@Override
	public String toString() {
		if(this.equals(BYTE)) {return "BYTE";}
		else if(this.equals(SHORT)) {return "SHORT";}
		else if(this.equals(INTEGER)) {return "INTEGER";}
		else if(this.equals(LONG)) {return "LONG";}
		else if(this.equals(FLOAT)) {return "FLOAT";}
		else if(this.equals(DOUBLE)) {return "DOUBLE";}
		else if(!this.isDecimal) {return "ARRAY[" + this.size + "]";}
		return "Data [size=" + size + ", isDecimal=" + isDecimal + "]";
	}
	/**
	 * {@link Data} object for Byte data type.
	 * */
	public static final Data BYTE = new Data(1, false);
	/**
	 * {@link Data} object for Short data type.
	 * */
	public static final Data SHORT = new Data(2, false);
	/**
	 * {@link Data} object for Integer data type.
	 * */
	public static final Data INTEGER = new Data(4, false);
	/**
	 * {@link Data} object for Long data type.
	 * */
	public static final Data LONG = new Data(8, false);
	/**
	 * {@link Data} object for Float data type.
	 * */
	public static final Data FLOAT = new Data(4, true);
	/**
	 * {@link Data} object for Double data type.
	 * */
	public static final Data DOUBLE = new Data(8, true);
	/**
	 * @return {@link Data} object for Byte array of given length.
	 * */
	public static final Data ARRAY(int length) {return new Data(length, false);}
}
