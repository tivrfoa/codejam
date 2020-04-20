import java.util.TreeMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

/**
 * 
 * 
 */ 
public class PatternTest {

	private static Scanner sc;
	
	public static void main(String[] args) throws Exception {

		testPattern("a*b", "aaaaab");
		testPattern(".*COCONUTS", "*COCONUTS");
		testPattern(".*COCONUTS", "*CONUTS");
		testPattern(".*CONUTS", "*COCONUTS");
	}
	
	public static void testPattern(String pattern, String target) {
		// Pattern p = Pattern.compile("a*b");
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(target);
		boolean b = m.matches();

		System.out.println(b);
	}
}
