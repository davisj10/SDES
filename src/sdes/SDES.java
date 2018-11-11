package sdes;

import java.nio.charset.Charset;
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

    //Various permutations used by expPerm
	private static int[] IP = {1, 5 , 2, 0, 3, 7, 4, 6};
	private static int[] IPINV = {3, 0, 2, 4, 6, 1, 7, 5};
	private static int[] K1 = {0, 6, 8, 3, 7, 2, 9, 5};
	private static int[] K2 = {7, 2, 5, 4, 9, 1, 8, 0};
	private static int[] P4 = {1, 3, 2, 0};
	private static int[] EP = {3, 0, 1, 2, 1, 2, 3, 0};

	//The 2 permuted 8-bit keys from the entered 10 bit key
	private boolean[] k1;
	private boolean[] k2;

	//S0 block
	private static final boolean[][][] s0 = {
			{{false, true},{false, false},{true, true},{true, false}},
			{{true, true},{true,false},{false,true},{false,false}},
			{{false,false},{true,false},{false,true},{true,true}},
			{{true,true},{false,true},{true,true},{true,false}}
	};

	//S1 block
	private static final boolean[][][] s1 = {
			{{false,false,},{false,true},{true,false},{true,true}},
			{{true,false},{false,false},{false,true},{true,true}},
			{{true,true},{false,false},{false,true},{false,false}},
			{{true,false},{false,true},{false,false},{true,true}}
	};



	public SDES() {} //end default constructor

	/**
	 * Convert the given bit array to a single byte
	 * @author Tyler Robinson
	 * @param inp A bit array, max length is 8 bits
	 * @return Byte represented my the given bit array
	 */
	public byte bitArrayToByte(boolean[] inp) {
		 byte result;
		 StringBuilder sr = new StringBuilder();
		 //Convert boolean values of inp to 1's and zero's
		 for (boolean b : inp){
		 	if (b){
		 		sr.append(1);
			}
			else sr.append(0);
		 }

		 //Negative number conversion to byte was not working, so convert
        //negative numbers into their 2's complement positive counterpart
        //Prepend a '-' for the byte conversion
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

		 //Parse the string of 1's and 0's to a byte
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
		return new String(inp);
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
		//Trim negative bytes down from 32 bits to the appropriate size
		if (b < 0){
			test = test.substring(test.length() - size);
		}
		sr.append(test);

		//Extend the bit string to the appropriate length
		while (sr.length() < size){
			sr.insert(0, 0);
		}
		String bitString = sr.toString();

		//Convert the 1's and 0's of the bit strings to boolean values
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

		//Copy the x array to the result array, starting at 0
		System.arraycopy(x, 0 ,result, 0, x.length);

		//Copy the y array to the resutl array, starting at the end of the x array
		System.arraycopy(y, 0, result, x.length, y.length);

		return result;
	}

	/**
	 *
	 * @author Justin Davis
	 * @param cipher Array of bytes to be Decrypted
	 * @return Decrypted array of bytes
	 */
	public byte[] decrypt(byte[] cipher) {
		int size = cipher.length;
		for(int i = 0; i < size; i++) {
			cipher[i] = decryptByte(cipher[i]);
		}
		return cipher;
	}

	/**
	 *
	 * @author Justin Davis
	 * @param b Byte to be decrypted
	 * @return Decrypted byte
	 */
	public byte	decryptByte(byte b) {
		boolean[] temp = byteToBitArray(b, 8);
		temp = expPerm(temp, IP);
		temp = f(temp, k2);
		temp = concat(rh(temp), lh(temp)); //swap left and right halves
		temp = f(temp, k1);
		temp = expPerm(temp, IPINV);
		return bitArrayToByte(temp);
	}

	/**
	 *
	 * @author Justin Davis
	 * @param msg Byte array to be encrypted
	 * @return Encrypted byte array
	 */
	public byte[] encrypt(String msg) {
		byte[] cipher = msg.getBytes();
		int size = cipher.length;
		for(int i = 0; i < size; i++) {
			cipher[i] = encryptByte(cipher[i]);
		}
		return cipher;
	}

	/**
	 * @author Justin Davis
	 * @param b Byte to be encrypted
	 * @return Encrypted byte
	 */
	public byte	encryptByte(byte b) {
		boolean[] temp = byteToBitArray(b, 8);
		temp = expPerm(temp, IP);
		temp = f(temp, k1);
		temp = concat(rh(temp), lh(temp)); //swap left and right halves
		temp = f(temp, k2);
		temp = expPerm(temp, IPINV);
		return bitArrayToByte(temp);
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
		    //epv[] holds array index values that are used to pull indexes from inp into result
			result[i] = inp[epv[i]];
		}

		return result;
	}

    /**
     * This is the 'round' function
     * It is its own inverse
     * f(x,k) = L(x) xor F(R(x),k)) || R(x)
     * @param x Byte to be decrypted
     * @param k Key for the current round
     * @return Result of the round' function
     */
	public boolean[] f(boolean[] x, boolean[] k) {
		boolean[] left = lh(x);
		boolean[] right = rh(x);
		//Concatenate the left half of x XOR'd with the feistel network result of the right half of x with the current key
        //with the right half of x
		return concat((xor(left, feistel(k, right))), right);
	}

    /**
     * F(k,x) is a Feistel function F(k,x) = P4(s0(L(k xor EP(x))) || s1 (R (k xor EP(x)))
     * @param k Key for the current round
     * @param x Left half of the byte being decrypted
     * @return The result of the Feistel function
     */
	public boolean[] feistel(boolean[] k, boolean[] x) {
	    //Get the left and right halves of k xor EP(x)
		boolean[] lKXorEp = lh(xor(k, expPerm(x, EP)));
		boolean[] rKXorEp = rh(xor(k, expPerm(x, EP)));

		//Use the boolean values calculated for KxorX to determine the index values for the S blocks
		int i1 = Integer.parseInt(((lKXorEp[0]) ? "1" : "0") + ((lKXorEp[3] ? "1" : "0")), 2);
		int i2 = Integer.parseInt(((lKXorEp[1]) ? "1" : "0") + ((lKXorEp[2] ? "1" : "0")), 2);
		int i3 = Integer.parseInt(((rKXorEp[0]) ? "1" : "0") + ((rKXorEp[3] ? "1" : "0")), 2);
		int i4 = Integer.parseInt(((rKXorEp[1]) ? "1" : "0") + ((rKXorEp[2] ? "1" : "0")), 2);

		//Calculate the P4 permutation of the concatenated S block values
		return expPerm(concat(s0[i1][i2], s1[i3][i4]), P4);
	}

	/**
	 * Get a 10 bit key from the keyboard, such as 1010101010.
	 * Store it as an array of booleans in a field
	 * @author Tyler Robinson
	 * @param scanner Scanner used to receive key from user
	 */
	public void	getKey10(java.util.Scanner scanner) {
	    //Get the key from the user
		String keyString = scanner.nextLine();
		//remove any spaces entered by the user
		keyString = keyString.replaceAll(" ", "");
		boolean[] key = new boolean[keyString.length()];
		//Convert 1's and 0's entered by user into boolean values
		for (int i = 0; i < keyString.length(); i++){
			key[i] = keyString.charAt(i) == '1';
		}
		//Set the permuted keys for this encryption/decryption session
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

		//Copy the left half of inp to left
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

		//Copy right half of inp to right
		System.arraycopy(inp, 4, right, 0, 4);

		return right;
	}

	/**
	 * May need to use stdout!!!
	 *
	 * @author Justin Davis
	 * @param inp Boolean array to print to screen
	 */
	public void	show(boolean[] inp) {
		StringBuilder output = new StringBuilder();
		int size = inp.length;
		for (int i = 0; i < size; i++) {
			output.append(inp[i] ? 1 : 0);
		}
		System.out.print(output.toString());
	}

	/**
	 * May need to use stdout!!!
	 *
	 * @author Justin Davis
	 * @param byteArray Byte array to print to the screen
	 */
	public void	show(byte[] byteArray) {
		StringBuilder sb = new StringBuilder();
		for (byte b : byteArray){
			sb.append(b);
			sb.append(" ");
		}
		System.out.println(sb.toString());
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

		//XOR the corresponding values of x and y
		for (int i = 0; i < x.length; i++){
			result[i] = x[i] ^ y[i];
		}

		return result;
	}
	
} //end SDES
