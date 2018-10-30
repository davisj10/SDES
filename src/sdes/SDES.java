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

	private static int[] IP = {1, 5 , 2, 0, 3, 7, 4, 6};
	private static int[] IPINV = {3, 0, 2, 4, 6, 1, 7, 5};
	private static int[] K1 = {0, 6, 8, 3, 7, 2, 9, 5};
	private static int[] K2 = {7, 2, 5, 4, 9, 1, 8, 0};
	private static int[] P4 = {1, 3, 2, 0};
	private static int[] EP = {3, 0, 1, 2, 1, 2, 3, 0};

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
	
	public boolean[] expPerm(boolean[] inp, int[] epv) {
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
	
	public void	getKey10(java.util.Scanner scanner) {

	}
	
	public boolean[] lh(boolean[] inp) {
		boolean[] left = new boolean[4];

		System.arraycopy(inp, 0, left, 0, 4);

		return left;
	}
	
	public boolean[] rh(boolean[] inp) {
		boolean[] right = new boolean[4];

		System.arraycopy(inp, 4, right, 0, 4);

		return right;
	}
	
	public void	show(boolean[] inp) {

	}
	
	public void	show(byte[] byteArray) {

	}
	
	public boolean[] xor(boolean[] x, boolean[] y) {

		return y;
	}
	
} //end SDES
