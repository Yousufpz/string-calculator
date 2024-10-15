package main.java.calculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {
    public int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        String[] delimiters = {",", "\n"};
        String numberString = numbers;

        if (numbers.startsWith("//")) {
            int newLineIndex = numbers.indexOf("\n");
            String delimiterPart = numbers.substring(2, newLineIndex);
            numberString = numbers.substring(newLineIndex + 1);

            delimiters = extractDelimiters(delimiterPart);
        }

        List<Integer> nums = splitAndParse(numberString, delimiters);

        List<Integer> negatives = nums.stream()
                                      .filter(n -> n < 0)
                                      .collect(Collectors.toList());
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negatives);
        }

        return nums.stream()
                   .filter(n -> n <= 1000)
                   .mapToInt(Integer::intValue)
                   .sum();
    }

    private String[] extractDelimiters(String delimiterPart) {
        if (delimiterPart.startsWith("[") && delimiterPart.endsWith("]")) {
            return Arrays.stream(delimiterPart.split("\\]\\["))
                         .map(s -> s.replaceAll("[\\[\\]]", ""))
                         .map(Pattern::quote)
                         .toArray(String[]::new);
        } else {
            return new String[]{Pattern.quote(delimiterPart)};
        }
    }

    private List<Integer> splitAndParse(String numberString, String[] delimiters) {
        String delimiterRegex = String.join("|", delimiters);
        return Arrays.stream(numberString.split(delimiterRegex))
                     .map(String::trim)
                     .filter(s -> !s.isEmpty())
                     .map(this::parseNumber)
                     .collect(Collectors.toList());
    }

    private int parseNumber(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}