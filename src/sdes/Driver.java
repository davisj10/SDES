package sdes;

import java.util.*;

/**
 * Test the implementation of SDES.
 * 
 * @author Justin Davis
 * @version November 2018
 */
public class Driver
{
	
	public static void main(String[] args)
	{   	
		Scanner scanner = new Scanner (System.in);
		SDES sdes = new SDES();

		//get key from user
		System.out.println("Please input a 10 digit key to be used. Then press enter.");
		sdes.getKey10(scanner);

		byte [] cipher = null;
		String selection = "";
		
		//continually asks user for input
		while(true) {
			System.out.println("Press 1 to encrypt. 2 to decrypt. 3 to change key. 4 to exit.");
			selection = scanner.nextLine();
			switch (selection) {
			case "1": //encrypt message
				System.out.print("Plain text: ");
				String plain = scanner.nextLine();
				cipher = sdes.encrypt(plain);
				System.out.print("Cipher: ");
				sdes.show(cipher);
				System.out.println();
				break;
			case "2": //decrypt message
				System.out.println("Would you like to decrypt (1) current encrypted text, or (2) new cipher?");
				String select = scanner.nextLine();
				if(select.equalsIgnoreCase("1")) { //decrypt current cipher text
					if(cipher != null) {
						System.out.print("Decrypted text: ");
						System.out.println (sdes.byteArrayToString (sdes.decrypt(cipher)) + "\n");
					}
					else {
						System.out.println("Must first encrypt some plain text!\n");
					}
				}
				else if (select.equalsIgnoreCase("2")){ //decrypt custom cipher text
					System.out.print("Enter cipher: ");
					String newCipher = scanner.nextLine().trim();
					String[] newCipherArray = newCipher.split(" ");
					int size = newCipherArray.length;
					byte[] newCipherByteArray = new byte[size];
					for(int i = 0; i < size; i++) {
						newCipherByteArray[i] = Byte.parseByte(newCipherArray[i]);
					}
					byte[] decrypted = sdes.decrypt(newCipherByteArray);
					System.out.println("Decrpyted text: " + sdes.byteArrayToString(decrypted) + "\n");
				}
				else { //invalid choice
					System.out.println("Invalid selection! Try again.\n");
				}
				break;
			case "3": //updates key
				System.out.println("Please input a new 10 digit key to be used, then press enter.");
				sdes.getKey10(scanner);
				System.out.println();
				break;
			case "4": //stops the program
				System.out.println("Goodbye...");
				System.exit(0);
			}
		}
	}
} //end Driver