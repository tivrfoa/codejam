import java.util.*;
import java.io.*;
import static java.lang.System.out;

/**
 * 
 * ESAb ATAd
 * 
 * 
 * 150 maximum queries
 * 
 */
public class Solution {

	private static Scanner sc;

	public static void main(String[] args) throws Exception {

		sc = new Scanner(System.in);

		String[] tb = sc.nextLine().split(" ");
		int T = Integer.parseInt(tb[0]);
		int B = Integer.parseInt(tb[1]);

		for (int t = 1; t <= T; ++t) {
			solve(t, B);
		}

		sc.close();

		// tests();
	}

	private static void solve(int t, int B) {

		HashSet<List<Integer>> hashSet = new HashSet<List<Integer>>();
		Integer[] tmp1 = new Integer[B];
		Arrays.fill(tmp1, -1);
		hashSet.add(Arrays.asList(tmp1));

		for (int i = 1; i <= 150; i++) {
			for (int b = 0; b < B; ++b) {
				out.println(b+1);
				int bit = Integer.parseInt(sc.nextLine());
				for (List<Integer> l : hashSet) {
					l.set(b, bit);

					if (i % 10 == 1) {
						List<Integer> tmp = Arrays.asList(invert((Integer[])l.toArray()));
						tmp.set(b, bit);
						hashSet.add(tmp);
						tmp = Arrays.asList(reverse((Integer[])l.toArray()));
						tmp.set(b, bit);
						hashSet.add(tmp);
						tmp = Arrays.asList(complementationAndReversal((Integer[])l.toArray()));
						tmp.set(b, bit);
						hashSet.add(tmp);
					}
				}
			}
			if (hashSet.size() == 1) {
				boolean isComplete = true;
				for (List<Integer> l : hashSet) {
					for (int j = 0; j < B; j++) {
						if (l.get(j) == -1) {
							isComplete = false;
							break;
						}
					}
				}
				if (isComplete) {
					StringBuilder answer = new StringBuilder();
					for (List<Integer> l : hashSet) {
						for (int j = 0; j < B; j++) {
							answer.append(l.get(j));
						}
					}
					out.println(answer.toString());
					String reply = sc.nextLine();
					if ("Y".equals(reply)) return;
					else System.exit(-1);
				}
			}
		}
		
	}

	private static Integer[] complementationAndReversal(Integer[] array) {
		return invert(reverse(array));
	}

	private static Integer[] invert(Integer[] array) {
		Integer[] inverted = new Integer[array.length];

		for (int i = 0; i < inverted.length; i++) {
			inverted[i] = array[i] == 0 ? 1 : 0;
		}

		return inverted;
	}

	private static Integer[] reverse(Integer[] array) {
		Integer[] reversed = new Integer[array.length];

		for (int i = 0, j = array.length - 1; i < array.length/2; i++, j--) {
			reversed[i] = array[j];
			reversed[j] = array[i];
		}

		return reversed;
	}


	private static void tests() {
		Integer[] test = {0, 1, 0, 0, 1, 1};
		System.out.print("--------- ORIGINAL ----------- ");
		for (Integer i : test) System.out.print(i + " ");

		System.out.print("\n--------- INVERTED ----------- ");
		Integer[] inverted = invert(test);		
		for (Integer i : inverted) System.out.print(i + " ");

		System.out.print("\n--------- REVERTED ----------- ");
		Integer[] reversed = reverse(test);
		for (Integer i : reversed) System.out.print(i + " ");
	}
}
