package cartograph;
// TODO: move this into its own package
// TODO: add test scripts to the Fisher-Yates package

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Wrapper class for the Fishery-Yates shuffling algorithm
 * @author cglass
 *
 */
public class FisherYates {

	/**
	 * Performs a Fisher-Yates shuffle on the given list.
	 * Important: irreversibly alters the order of the given list
	 * https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
	 */
	public static <E> void shuffle(List<E> list) {
		Random r = new Random();
		for (int i = list.size() - 1; i > 0; i -= 1) {
			int j = r.nextInt(i + 1);
			swap(list, i, j);
		}
	}

	/**
	 * Swaps the elements at indices i and j in the given list
	 */
	private static <E> void swap(List<E> list, int i, int j) {
		E temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}

	/**
	 * Asserts that the 
	 * @param nTests
	 * @param error
	 */
	public static void testShuffle(int nTests, double error) {
		String test = "abc";
		List<Character> list = toList(test);
		Map<String, Integer> map = new TreeMap<String, Integer>();
		for (int i = 0; i < nTests; i += 1) {
			shuffle(list);
			String shuffled = "";
			for (Character c : list) {
				shuffled = shuffled + c;
			}
			if (!map.containsKey(shuffled)) {
				map.put(shuffled, 0);
			}
			map.put(shuffled, map.get(shuffled) + 1);
		}

		for (String key : map.keySet()) {
			System.out.println(key + ": " + map.get(key));
		}
	}

	/**
	 * Returns a list of all of the characters in the given string, in their original order 
	 * @param str
	 * @return
	 */
	private static List<Character> toList(String str) {
		List<Character> list = new ArrayList<Character>();
		for (int i = 0; i < str.length(); i += 1) {
			list.add(str.charAt(i));
		}
		return list;
	}

	/**
	 * Returns a string of all list elements concatenated together, in their original order
	 */
	private static <Character> String toString(List<Character> list) {
		String str = "";
		for (Character item : list) {
			str = str + item.toString();
		}
		return str;
	}
}
