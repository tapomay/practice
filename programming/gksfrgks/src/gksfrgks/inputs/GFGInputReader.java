package gksfrgks.inputs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public interface GFGInputReader {

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
