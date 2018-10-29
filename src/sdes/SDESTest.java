package sdes;

public class SDESTest {

	public static void main(String[] args) {
		byte[][] s1 = {
				{00,01,10,11},
				{10,00,01,11},
				{11,00,01,00},
				{10,01,00,11}
		};
		
		SDES sdes = new SDES();
		
		boolean[] boo = {true,false,false,true,true,false};
		byte[] b = {12,43,52,11};
		
		//byte concat = s1[0][2]  s1[0][0];
		byte[] sample = sdes.encrypt("abcd");
		sdes.show(sample);

	}

}
