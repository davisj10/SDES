package sdes;

/**
 * A class that that can encrypt and decrypt messages using SDES.
 * 
 * @author Justin Davis
 * @author Quentin Terry
 * @author Tyler Robinson
 *
 */
public class SDES {

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
	
	public void	show(boolean[] inp) {}
	
	public void	show(byte[] byteArray) {}
	
	public boolean[] xor(boolean[] x, boolean[] y) {
		return y;
	}
	
} //end SDES
