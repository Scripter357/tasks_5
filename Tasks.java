import java.util.*;
import java.util.stream.*;
import java.text.DecimalFormat;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Main class
public class Tasks {
	public static void main(String[] args) {
		System.out.println("Enter the task number: ");
		String input = System.console().readLine();
		try {
		switch(input) {
			case "1":
				System.out.println("Select mode (encrypt/decrypt): ");
				String input2 = System.console().readLine();
				if(input2.equals("encrypt")) displayInts(encrypt(args[0]));
				else if(input2.equals("decrypt")) System.out.println(decrypt(strArrToIntArr(args)));
				return;
			case "2":
				System.out.println(canMove(args[0], args[1], args[2]));
				return;
			case "3":
				System.out.println(canComplete(args[0], args[1]));
				return;
			case "4":
				System.out.println(sumDigProd(strArrToIntArr(args)));
				return;
			case "5":
				displayStringList(sameVowelGroup(args));
				return;
			case "6":
				System.out.println(validateCard(args[0]));
				return;
			case "7":
				System.out.println(numToEng(Integer.parseInt(args[0])));
				return;
			case "8":
				System.out.println(hash(args[0], args[1]));
				return;
			case "9":
				System.out.println(correctTitle(args[0]));
				return;
			case "10":
				System.out.println(countUniqueBooks(args[0],args[1]));
				return;
			default:
				System.out.println("[ERROR] Input does not match a task number.");
		}
		} catch(Exception err) {
			System.out.println("An error has occured. Check what you're inputting as arguments. Error follows: ");
			System.out.println(err);
		}
	}
	
	//"Encrypts" a string
	public static int[] encrypt(String s) {
		String sl = s;
		char prev = ';';
		boolean first = true;
		int[] result = new int[sl.length()];
		for(int i = 0; i < sl.length(); i++) {
			char c = sl.charAt(i);
			if(!first) {
				result[i] = c - prev;
			} else {
				result[i] = c;
				first = false;
			}
			prev = c;
		}
		return result;
	}
	
	//"Decrypts" a string
	public static String decrypt(int[] arr) {
		char curr = 0;
		String result = "";
		for(int i = 0; i < arr.length; i++) {
			curr += arr[i];
			result = result.concat(Character.toString(curr));
		}
		return result;
	}
	
	//Checks the validity of chess moves.
	public static boolean canMove(String figure, String startp, String endp) {
		int startx = startp.charAt(0) - 'A';
		int starty = startp.charAt(1) - '1';
		int endx = endp.charAt(0) - 'A';
		int endy = endp.charAt(1) - '1';
		System.out.println(figure);
		System.out.println(startx);
		System.out.println(starty);
		System.out.println(endx);
		System.out.println(endy);
		if(figure.equals("BottomPawn")) {
			if(startx == endx && endy-starty < 3 && starty < endy) return true;
			return false;
		} else if(figure.equals("Rook")) {
			if(startx == endx || starty == endy) return true;
			return false;
		} else if(figure.equals("Knight")) {
			if((Math.abs(startx-endx) == 2 && Math.abs(starty-endy) == 1) || (Math.abs(startx-endx) == 1 && Math.abs(starty-endy) == 2)) return true;
			return false;
		} else if(figure.equals("Bishop")) {
			if(Math.abs(startx-endx) == Math.abs(starty-endy)) return true;
			return false;
		} else if(figure.equals("Queen")) {
			if(Math.abs(startx-endx) == Math.abs(starty-endy) || startx == endx || starty == endy) return true;
			return false;
		} else if(figure.equals("King")) {
			if(Math.abs(startx-endx) < 2 && Math.abs(starty-endy) < 2) return true;
			return false;
		}
		return false;
	}
	
	//Checks if first string can be completed to target string without character deletion
	public static boolean canComplete(String s, String target) {
		int j = 0;
		for(int i = 0; i < target.length(); i++) {
			char c = target.charAt(i);
			if(c == s.charAt(j)) {
				j++;
				if(j == s.length()) return true;
			}
		}
		return false;
	}
	
	
	//Sums of digits of sum of arguments until only one digit is left.
	public static int sumDigProd(int[] arr) {
		int n = 0;
		for(int i = 0; i < arr.length; i++) {
			n += arr[i];
		}
		while(n/10 > 0) {
			int temp = 1;
			String strtemp = Integer.toString(n);
			for(int i = 0; i < strtemp.length(); i++) {
				char c = strtemp.charAt(i);
				temp *= Integer.parseInt(Character.toString(c));
			}
			n = temp;
		}
		return n;
	}
	
	//Checks for word with the same vowels used as first word
	public static List<String> sameVowelGroup(String[] args) {
		ArrayList<List<Character> > vowelLists = new ArrayList<List<Character> >(args.length);
		List<String> result = new ArrayList<>();
		boolean first = true;
		for(int i = 0; i < args.length; i++) {
			vowelLists.add(getVowelList(args[i]));
			if(!first) {
				if(vowelLists.get(0).equals(vowelLists.get(i))) {
					result.add(args[i]);
				}
			} else {
				result.add(args[i]);
				first = false;
			}
		}
		return result;
	}
	
	//Assembles vowel list from a sting.
	private static List<Character> getVowelList(String s) {
		List<Character> alist = new ArrayList<>();
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
				if(!alist.contains(c)) alist.add(c);
			}
		}
		Collections.sort(alist);
		return alist;
	}
	
	//Validates card number
	public static boolean validateCard(String s) {
		if(s.length() < 14 || s.length() > 20) return false;
		int sum = 0;
		for(int i = 0; i <= s.length()-2; i++) {
			int temp = Integer.parseInt(Character.toString(s.charAt(s.length()-i-2)))*((1-i%2)+1);
			if (temp/10 > 0) {
				sum += temp/10;
				sum += temp%10;
			} else {
				sum += temp;
			}
		}
		return Integer.toString(10-sum%10).charAt(0) == (s.charAt(s.length()-1));
	}
	
	//Replaces repeating symbols with stars
	public static String numToEng(int n) {
		String result = "";
		String[] sub10s = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
		String[] roots = {"twen", "thir", "for", "fif", "six", "seven", "eigh", "nine"};
		String[] exceptions = {"ten", "eleven", "twelve"};
		int hungreds = n/100;
		int decades = n/10 - (n/100)*10;
		int other = n%10;
		if(hungreds > 0) {
			result = result.concat(sub10s[hungreds]+" hungred ");
		}
		if(decades > 1) {
			result = result.concat(roots[decades-2]+"ty ");
			result = result.concat(sub10s[other]);
		} else if(decades == 1) {
			if(other > 2) result = result.concat(roots[other-2]+"teen");
			else result = result.concat(exceptions[other]);
		} else {
			result = result.concat(sub10s[other]);
		}
		return result;
	}
	
	public static String hash(String s, String s2) {
		if(s2.equals("--custom")) {
			return customHashFunc(Integer.parseInt(s));
		}
		try {
			return toHexString(getSHA(s));
		} catch(NoSuchAlgorithmException e) {
			System.out.println("Exception thrown for incorrect algorithm: " + e);
		}
		return "You should never see this message. Whatever you did - good luck.";
	}
	
	//Custom hash function
	private static String customHashFunc(int n) {
		int dn = (n % 95);
		if(dn < 10) return "0"+Integer.toString(dn);
		return Integer.toString(dn);
	}
	
	//SHA-256 black magic
	private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}
	
	//Transforms whatever SHA spews out into hex string
	private static String toHexString(byte[] hash) {
		BigInteger number = new BigInteger(1, hash);
		StringBuilder hexString = new StringBuilder(number.toString(16));
		
		// Adding zeroes for consistent length
		while (hexString.length() < 64)
		{
			hexString.insert(0, '0');
		}
		return hexString.toString();
	}
	
	//Corrects title to Game of Thrones format
	public static String correctTitle(String s) {
		String ss[] = s.split(" ");
		String buffer = "";
		for(int i = 0; i < ss.length; i++) {
			String temp = ss[i].toLowerCase();
			if(temp.equals("of") || temp.equals("the") || temp.equals("and") || temp.equals("in")) {
				buffer = buffer.concat(temp);
			} else {
				boolean first = true;
				String buffer2 = "";
				for(int j = 0; j < temp.length(); j++) {
					char c = temp.charAt(j);
					if(first) {
						first = false;
						buffer2 = buffer2.concat(Character.toString(c-32));
					} else {
						buffer2 = buffer2.concat(Character.toString(c));
					}
				}
				buffer = buffer.concat(buffer2);
			}
			buffer = buffer.concat(" ");
		}
		return buffer;
	}
	
	//Counts "books"
	public static String hexLattice(String s, String marker) {
		
	}
	
	//Converts an array of string to an array of integers
	protected static int[] strArrToIntArr(String array[]) {
		int nums[] = new int[array.length];
		for(int i = 0; i < array.length; i++) {
			nums[i] = Integer.parseInt(array[i]);
		}
		return nums;
	}
	
	//Converts an array of string to an array of doubles
	protected static double[] strArrToDoubleArr(String array[]) {
		double nums[] = new double[array.length];
		for(int i = 0; i < array.length; i++) {
			nums[i] = Double.parseDouble(array[i]);
		}
		return nums;
	}
	
	//Displays an int array
	protected static void displayInts(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
	
	
	//Displays a string list
	protected static void displayStringList(List<String> arr) {
		for(int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}
	}
}

