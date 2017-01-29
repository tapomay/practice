package gksfrgks.equilibrium;
/***
 * Given an array A your task is to tell at which position the equilibrium first occurs in the array. Equilibrium position in an array is a position such that the sum of elements below it is equal to the sum of elements after it.

Input:
The first line of input contains an integer T denoting the no of test cases then T test cases follow. First line of each test case contains an integer N denoting the size of the array. Then in the next line are N space separated values of the array A.

Output:
For each test case in a new  line print the position at which the elements are at equilibrium if no equilibrium point exists print -1.

Constraints:
1<=T<=100
1<=N<=100

Example:
Input:
2
1
1
5
1 3 5 2 2


Output:
1
3

Explanation:
1. Since its the only element hence its the only equilibrium point
2. For second test case equilibrium point is at position 3 as elements below it (1+3) = elements after it (2+2)
 * @author tadey
 * Commit Completed: 5Jan17 11:20AM
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

class GFG {
	public static void main(String[] args) {
		Equilibrium eq = new Equilibrium();
		List<String[]> inputs = eq.read();
		for (String[] strArr : inputs) {
//			System.out.format("Solving: [%s]\n", String.join(",", strArr));
			int pos = eq.solveOne(strArr);
			System.out.println(pos);
			// Consumer<? super String> action = (a) -> {
			// System.out.print(a);
			// };
			// Arrays.asList(strArr).stream().forEach(action);
		}
	}
}

public class Equilibrium implements GFGInputReader {

	public Integer solveOne(String[] strArr) {
		Function<String, Integer> intParser = (s) -> {
			return Integer.parseInt(s);
		};
		List<Integer> intArr = Arrays.asList(strArr).stream().map(intParser).collect(Collectors.toList());
//		System.out.println(intArr);
		List<Integer> forwardSums = forwardSum(intArr);
		List<Integer> backwardSums = backwardSum(intArr);
//		System.out.println(forwardSums);
//		System.out.println(backwardSums);
		int pos = -1;
		int N = intArr.size();
		if (intArr.size() == 1) {
			pos = 1;
		}
		else {
			for (int i = 1; i < N; i++) {
//				System.out.format("Comparing: %d vz %d\n", forwardSums.get(i), backwardSums.get(N-1-i));
				if (forwardSums.get(i).equals(backwardSums.get(N-1-i))) {
					pos = i+1;
					break;
				}
			}
		}
		return pos;
	}

	private List<Integer> backwardSum(List<Integer> intArr) {
		List<Integer> revIntArr = intArr.subList(0, intArr.size());
		Collections.reverse(revIntArr);
		return forwardSum(revIntArr);
	}

	private List<Integer> forwardSum(List<Integer> intArr) {
		int runningSum = 0;
		List<Integer> ret = new ArrayList<>(intArr.size());
		for (Integer val : intArr) {
			ret.add(runningSum);
			runningSum += val;
		}
		return ret;
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

	default String[] singleRead(Scanner scanner) {
		String len = scanner.nextLine();
		String string = scanner.nextLine();
		String[] split = string.split(" ");
		if (split.length != Integer.parseInt(len)) {
			System.err.format("Invalid input size for length: %s - (%s)", len, string);
		}
		return split;
	}

	default List<String[]> read() {
		Scanner in = new Scanner(System.in);
		List<String[]> inputs = parseInputs(in, this::singleRead);
		return inputs;
	}

}

