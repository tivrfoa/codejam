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
		// System.err.println(T + " " + B);

		for (int t = 1; t <= T; ++t) {
			solve(t, B);
		}

		sc.close();

		// tests();
	}

	private static void solve(int t, int B) {

		List<int[]> list = new ArrayList<int[]>();
		int[] tmp1 = new int[B];
		Arrays.fill(tmp1, -1);
		list.add(tmp1);

		for (int i = 1; i <= 150; i++) {
			for (int b = 0; b < B; ++b) {
				out.println(b+1);
				int bit = Integer.parseInt(sc.nextLine());
				// System.err.println(bit);
				int size = list.size();
				for (int j = 0; j < size; j++) {
					int[] l = list.get(j);

					l[b] = bit;

					if (i > 1 && i % 10 == 1) {
						int[] tmp = invert(l);
						tmp[b] = bit;
						list.add(tmp);
						tmp = reverse(l);
						tmp[b] = bit;
						list.add(tmp);
						tmp = complementationAndReversal(l);
						tmp[b] = bit;
						list.add(tmp);
					}
				}
			}
			if (list.size() == 1) {
				boolean isComplete = true;
				int[] l = list.get(0);
				for (int j = 0; j < B; j++) {
					if (l[j] == -1) {
						isComplete = false;
						break;
					}
				}
				if (isComplete) {
					StringBuilder answer = new StringBuilder();
					for (int j = 0; j < B; j++) {
						answer.append(l[j]);
					}
					out.println(answer.toString());
					String reply = sc.nextLine();
					System.err.println("Reply: " + reply);
					if ("Y".equals(reply)) return;
					else System.exit(-1);
				}
			}
		}
		
	}

	private static int[] complementationAndReversal(int[] array) {
		return invert(reverse(array));
	}

	private static int[] invert(int[] array) {
		int[] inverted = new int[array.length];

		for (int i = 0; i < inverted.length; i++) {
			inverted[i] = array[i] == 0 ? 1 : 0;
		}

		return inverted;
	}

	private static int[] reverse(int[] array) {
		int[] reversed = new int[array.length];

		for (int i = 0, j = array.length - 1; i < array.length/2; i++, j--) {
			reversed[i] = array[j];
			reversed[j] = array[i];
		}

		return reversed;
	}


	private static void tests() {
		int[] test = {0, 1, 0, 0, 1, 1};
		System.out.print("--------- ORIGINAL ----------- ");
		for (int i : test) System.out.print(i + " ");

		System.out.print("\n--------- INVERTED ----------- ");
		int[] inverted = invert(test);		
		for (int i : inverted) System.out.print(i + " ");

		System.out.print("\n--------- REVERTED ----------- ");
		int[] reversed = reverse(test);
		for (int i : reversed) System.out.print(i + " ");
	}
}
