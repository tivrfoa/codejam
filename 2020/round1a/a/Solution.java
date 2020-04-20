import java.util.TreeMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

/**
 * 
 * 
 */ 
public class Solution {

	private static Scanner sc;
	
	public static void main(String[] args) throws Exception {

		sc = new Scanner(System.in);		
		int T = sc.nextInt();
		
		for (int t = 1; t <= T; ++t) {
			solve(t);			
		}
		
		sc.close();
	}
	
	private static void solve(int t) {

		TreeMap<String, Pattern> stringPatterns = new TreeMap<>();
		
		int N = sc.nextInt();
		String[] pp = new String[N];

		String biggerString = "";
		for (int i = 0; i < N; i++) {
			pp[i] = sc.next();
			/*if (pp[i].length() > biggerString.length()) {
				biggerString
			}*/
			String tmp = pp[i].replaceAll("\\*", ".*");
			stringPatterns.put(pp[i], Pattern.compile(tmp));
		}

		boolean allEquals = true;
		outer:
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				Pattern p1 = stringPatterns.get(pp[i]);
				Pattern p2 = stringPatterns.get(pp[j]);
				if (!areEquivalentPatterns(p1, p2, pp[j], pp[i])) {
					allEquals = false;
					break outer;
				}	
			}
		}

		if (allEquals) {

		} else {
			out.printf("Case #%d: *\n", t);
		}
	}

	private static int getFirstLetter(String s) {
		int first_letter = -1;

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != '*') {
				first_letter = i;
				break;
			}
		}

		return first_letter;
	}

	private static int getLastLetter(String s) {
		int last_letter = -1;

		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) != '*') {
				last_letter = i;
				break;
			}
		}

		return last_letter;
	}

	public static boolean areEquivalentPatterns(Pattern p1, Pattern p2,
			String s1, String s2) {

		int f1 = getFirstLetter(s1);
		if (f1 == -1) return true;
		int f2 = getLastLetter(s1);
		String str1 = s1.substring(f1, f2 + 1);
		f1 = getFirstLetter(s2);
		if (f1 == -1) return true;
		f2 = getLastLetter(s2);
		String str2 = s2.substring(f1, f2 + 1);

		return p1.matcher(str1).matches() ||
				p2.matcher(str2).matches();
	}

	//Pattern p = Pattern.compile("a*b");
		/*Pattern p = Pattern.compile("a.*b");
		Matcher m = p.matcher("aaaaab");
		boolean b = m.matches();

		System.out.println(b);
		System.out.println(m.groupCount());
		System.out.println(p.matcher("accccb").matches());*/
}
