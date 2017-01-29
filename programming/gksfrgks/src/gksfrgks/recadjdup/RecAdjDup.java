package gksfrgks.recadjdup;

/**
Given a string, recursively remove adjacent duplicate characters from string. The output string should not have any adjacent duplicates.
 

Input:
The first line of input contains an integer T denoting the no of test cases. Then T test cases follow. Each test case contains an string str.

Output:
For each test case output a new line containing the resulting string.

Constraints:
1<=T<=100
1<=Length of string<=50

Example:
Input:
2
geeksforgeek
acaaabbbacdddd

Output:
gksforgk
acac
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

class GFG {
	public static void main(String[] args) {
		RecAdjDup eq = new RecAdjDup();
		List<String> inputs = eq.read();
		for (String str : inputs) {
			// System.out.format("Solving: [%s]\n", String.join(",", strArr));
			String result = eq.solveOne(str);
			System.out.println(result);
			// Consumer<? super String> action = (a) -> {
			// System.out.print(a);
			// };
			// Arrays.asList(strArr).stream().forEach(action);
		}
	}
}

public class RecAdjDup implements GFGInputReader {

	public String solveOne(String str) {
		if (str.length() <= 1) {
			return str;
		}

		char[] charArray = str.toCharArray();
		List<String> retList = new ArrayList<String>();
		int i = 0;
		for (; i < charArray.length - 1;) {
			char ci = charArray[i];
			int j = i + 1;
			for (; j < charArray.length; j++) {
				char cj = charArray[j];
//				System.out.format("Comparing: %c vz %c\n", ci, cj);
				if (ci != cj) {
					break;
				}
			}

			if (j == i + 1) {
				retList.add("" + ci);
			}
			i = j;
//			System.out.format("%s\n", String.join("", retList));
		}
		if (i == charArray.length - 1) {// last pair does not match
			retList.add("" + charArray[i]);
		}
		String ret = String.join("", retList);

		if (ret.length() == charArray.length) {
			return str;
		} else {
			return solveOne(ret);
		}
	}

}

interface GFGInputReader {

	default <T> List<T> parseInputs(Scanner scanner, Function<Scanner, T> parser) {
		int count = scanner.nextInt();
		scanner.nextLine(); // ignore trailing new line after count
		List<T> ret = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			T apply = parser.apply(scanner);
			ret.add(apply);
		}
		return ret;
	}

	default String singleRead(Scanner scanner) {
		String string = scanner.nextLine();
		return string;
	}

	default List<String> read() {
		Scanner in = new Scanner(System.in);
		List<String> inputs = parseInputs(in, this::singleRead);
		return inputs;
	}

}
