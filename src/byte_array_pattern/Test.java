package byte_array_pattern;

import java.util.Arrays;

public class Test {
	public static void main(String[] args) {
		String littlePattern = "l;i:i:l:i:s:d:a[3]:f:b:b";
		String bigPattern = "b;i:i:l:i:s:d:a[3]:f:b:b";
		
		try {
			ByteArrayPatternUtility littleArrayPatternUtility = new ByteArrayPatternUtility(littlePattern);
			System.out.println("Little endian pattern: " + littleArrayPatternUtility.getPattern());
			
			ByteArrayPatternUtility bigArrayPatternUtility = new ByteArrayPatternUtility(bigPattern);
			System.out.println("Big endian pattern: " + bigArrayPatternUtility.getPattern());
			
			Object[] originalData = new Object[] {
					10,15,9000000l,78,(short)66,70.125,new byte[] {5,9,7}, 8.69f, (byte)77, (byte)80
					};
			System.out.println("Original data array: " + Arrays.toString(originalData));
			
			System.out.println("Using little endian pattern");
			
			byte[] bArray = littleArrayPatternUtility.toByteArray(originalData);
			System.out.println("Byte Array: " + Arrays.toString(bArray));
			
			Object[] objArray = littleArrayPatternUtility.toData(bArray);
			System.out.println("Data array: " + Arrays.toString(objArray));
			
			System.out.println("Using big endian pattern");
			
			bArray = bigArrayPatternUtility.toByteArray(originalData);
			System.out.println("Byte Array: " + Arrays.toString(bArray));
			
			objArray = bigArrayPatternUtility.toData(bArray);
			System.out.println("Data array: " + Arrays.toString(objArray));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
