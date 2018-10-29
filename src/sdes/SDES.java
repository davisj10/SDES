package sdes;

import java.util.Arrays;

/**
 * A class that that can encrypt and decrypt messages using SDES.
 * 
 * @author Justin Davis
 * @author Quentin Terry
 * @author Tyler Robinson
 *
 */
public class SDES {

	private boolean[] k1;
	private boolean[] k2;
	
	private static final byte[][] s0 = {
			{01,00,11,10},
			{11,10,01,00},
			{00,10,01,11},
			{11,01,11,10}
	};
	
	private static final byte[][] s1 = {
			{00,01,10,11},
			{10,00,01,11},
			{11,00,01,00},
			{10,01,00,11}
	};
	
	/**
	 * 
	 * @author Justin Davis
	 */
	public SDES() {} //end default constructor
	
	public byte bitArrayToByte(boolean[] inp) {
		return 0;
	}
	
	public String byteArrayToString(byte[] inp) {
		return null;
	}
	
	public boolean[] byteToBitArray(byte b, int size) {
		return null;
	}
	
	public boolean[] concat(boolean[] x, boolean[] y) {
		return y;
	}
	
	/**
	 * 
	 * @author Justin Davis
	 * @param cipher
	 * @return
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
	 * @param b
	 * @return
	 */
	public byte	decryptByte(byte b) {
		return b;
	}
	
	/**
	 * 
	 * @author Justin Davis
	 * @param msg
	 * @return
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
	 * 
	 * @author Justin Davis
	 * @param b
	 * @return
	 */
	public byte	encryptByte(byte b) {
		return b;
	}
	
	public boolean[] expPerm(boolean[] inp, int[] epv) {
		return inp;
	}
	
	public boolean[] f(boolean[] x, boolean[] k) {
		return k;
	}
	
	public boolean[] feistel(boolean[] k, boolean[] x) {
		return x;
	}
	
	public void	getKey10(java.util.Scanner scanner) {}
	
	public boolean[] lh(boolean[] inp) {
		return inp;
	}
	
	public boolean[] rh(boolean[] inp) {
		return inp;
	}
	
	/**
	 * May need to use stdout!!!
	 * 
	 * @author Justin Davis
	 * @param inp
	 */
	public void	show(boolean[] inp) {
		String output = "";
		int size = inp.length;
		for (int i = 0; i < size; i++) {
			output += inp[i] ? 1 : 0;
		}
		System.out.print(output);
	}
	
	/**
	 * May need to use stdout!!!
	 * 
	 * @author Justin Davis
	 * @param byteArray
	 */
	public void	show(byte[] byteArray) {
		String output = "";
		System.out.println(Arrays.toString(byteArray));
	}
	
	public boolean[] xor(boolean[] x, boolean[] y) {
		return y;
	}
	
} //end SDES
