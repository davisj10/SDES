package sdes;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.Scanner;

/**
 * A class that that can encrypt and decrypt messages using SDES.
 * 
 * @author Justin Davis
 * @author Quentin Terry
 * @author Tyler Robinson
 *
 */
public class SDES {

	private static int[] IP = {1, 5 , 2, 0, 3, 7, 4, 6};
	private static int[] IPINV = {3, 0, 2, 4, 6, 1, 7, 5};
	private static int[] K1 = {0, 6, 8, 3, 7, 2, 9, 5};
	private static int[] K2 = {7, 2, 5, 4, 9, 1, 8, 0};
	private static int[] P4 = {1, 3, 2, 0};
	private static int[] EP = {3, 0, 1, 2, 1, 2, 3, 0};

	private boolean[] k1;
	private boolean[] k2;

	public SDES() {} //end default constructor

	/**
	 * Convert the given bit array to a single byte
	 * @author Tyler Robinson
	 * @param inp A bit array, max length is 8 bits
	 * @return Byte represented my the given bit array
	 */
	public int bitArrayToByte(boolean[] inp) {
		byte result;
		 StringBuilder sr = new StringBuilder();
		 for (boolean b : inp){
		 	if (b == true){
		 		sr.append(1);
			}
			else sr.append(0);
		 }

		 if (sr.charAt(0) == '1'){
		 	int i = sr.length() - 1;
		 	while (sr.charAt(i) == '0'){
		 		i--;
			}
			i--;
			while (i >= 0){
				if (sr.charAt(i) == '1'){
					sr.replace(i, i+1, "0");
				}
				else sr.replace(i, i+1, "1");
				i--;
			}
			sr.insert(0, "-");
		 }

		 String byteString = sr.toString();

		 result = Byte.parseByte(byteString, 2);


		 return result;
	}

	/**
	 * Convert the given byte array to a String
	 * @author Tyler Robinson
	 * @param inp An array of bytes, hopefully storing the codes of printable characters
	 * @return The characters as a string
	 */
	public String byteArrayToString(byte[] inp) {
		String byteChars = new String(inp);
		return byteChars;
	}

	/**
	 * Convert the given byte to a bit array, of the given size
	 * @author Tyler Robinson
	 * @param b The byte to be converted
	 * @param size The size of the resulting bit array.
	 * @return Bit array that represents the given byte
	 */
	public boolean[] byteToBitArray(byte b, int size) {
		boolean[] result = new boolean[size];
		StringBuilder sr = new StringBuilder();
		String test = Integer.toBinaryString(b);
		if (b < 0){
			test = test.substring(test.length() - size);
		}
		sr.append(test);

		while (sr.length() < size){
			sr.insert(0, 0);
		}
		String bitString = sr.toString();

		for (int i = 0; i < size; i++){
			result[i] = bitString.charAt(i) == '1';
		}

		return result;
	}

	/**
	 * Concatenate the two bit arrays, x||y
	 * @author Tyler Robinson
	 * @param x The bit array that will become the left of the resultant array
	 * @param y The bit array that will become the right of the resultant array
	 * @return The concatenation of x and y
	 */
	public boolean[] concat(boolean[] x, boolean[] y) {
		int size = x.length + y.length;
		boolean[] result = new boolean[size];

		System.arraycopy(x, 0 ,result, 0, x.length);

		System.arraycopy(y, 0, result, x.length, y.length);

		return result;
	}
	
	public byte[] decrypt(byte[] cipher) {

		return cipher;
	}
	
	public byte	decryptByte(byte b) {

		return b;
	}
	
	public byte[] encrypt(String msg) {

		return null;
	}
	
	public byte	encryptByte(byte b) {

		return b;
	}

	/**
	 * Expand and/or permutate and/or select from the bit array, inp, producing
	 * an expanded/permutated/selected bit array.  Use the expansion.permutation
	 * verctor epv.
	 * @param inp A bit array represented as booleans, true=1, false=0.
	 * @param epv An expansion and/or permutation and/or selection vector; all numbers in epv must be in the range
	 *            0..inp.length-1, i.e. they must be valid subscripts for inp.
	 * @return The permutated/expanded/selected bit array, or null if there is an error
	 * @throws java.lang.IndexOutOfBoundsException
	 */
	public boolean[] expPerm(boolean[] inp, int[] epv) throws IndexOutOfBoundsException {
		boolean[] result = new boolean[epv.length];
		for (int i = 0; i < epv.length; i++){
			result[i] = inp[epv[i]];
		}

		return result;
	}
	
	public boolean[] f(boolean[] x, boolean[] k) {

		return k;
	}
	
	public boolean[] feistel(boolean[] k, boolean[] x) {

		return x;
	}

	/**
	 * Get a 10 bit key from the keyboard, such as 1010101010.
	 * Store it as an array of booleans in a field
	 * @author Tyler Robinson
	 * @param scanner Scanner used to receive key from user
	 */
	public void	getKey10(java.util.Scanner scanner) {
		String keyString = scanner.nextLine();
		keyString.replaceAll(" ", "");
		keyString.split("");
		boolean[] key = new boolean[keyString.length()];
		for (int i = 0; i < keyString.length(); i++){
			key[i] = keyString.charAt(i) == '1';
		}
		this.k1 = expPerm(key, K1);
		this.k2 = expPerm(key, K2);
	}

	/**
	 * Left half of x, L(x)
	 * @author Tyler Robinson
	 * @param inp Array to take the left half from
	 * @return a bit array which is the left half of the parameter, inp.
	 */
	public boolean[] lh(boolean[] inp) {
		boolean[] left = new boolean[4];

		System.arraycopy(inp, 0, left, 0, 4);

		return left;
	}

	/**
	 * Right half of x, R(x)
	 * @author Tyler Robinson
	 * @param inp Array to take the right half of
	 * @return a bit array which is the right half of the parameter, inp.
	 */
	public boolean[] rh(boolean[] inp) {
		boolean[] right = new boolean[4];

		System.arraycopy(inp, 4, right, 0, 4);

		return right;
	}
	
	public void	show(boolean[] inp) {

	}
	
	public void	show(byte[] byteArray) {

	}

	/**
	 * Exclusive OR. x and y must have the same length. x xor y is the same as x != y
	 * @author Tyler Robinson
	 * @param x Bit array to compare against y
	 * @param y Bit array to compare against x
	 * @return Result of the xor comparison of x and y
	 */
	public boolean[] xor(boolean[] x, boolean[] y) {
		boolean[] result = new boolean[x.length];

		for (int i = 0; i < x.length; i++){
			result[i] = x[i] ^ y[i];
		}

		return result;
	}
	
} //end SDES
