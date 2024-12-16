package io.github.markort147.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class Day4 {

    private static final String TEST = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX""";

    private static final char[] MAS = new char[]{'M', 'A', 'S'};
    private static final List<Function<String[], BiPredicate<Integer, Integer>>> XMAS_RULES = new ArrayList<>();
    private static final List<Function<String[], BiPredicate<Integer, Integer>>> MAS_RULES = new ArrayList<>();
    private static final Function<String[], BiFunction<Integer, Integer, char[][]>> MASK = lines -> (i, j) -> new char[][]{
            {lines[i].charAt(j), lines[i].charAt(j + 2)},
            {lines[i + 1].charAt(j + 1)},
            {lines[i + 2].charAt(j), lines[i + 2].charAt(j + 2)}
    };

    static {
        XMAS_RULES.add(lines -> (i, j) ->
                i - 3 >= 0
                        && Arrays.equals(new char[]{lines[i - 1].charAt(j), lines[i - 2].charAt(j), lines[i - 3].charAt(j)}, MAS));
        XMAS_RULES.add(lines -> (i, j) ->
                i + 3 < lines.length
                        && Arrays.equals(new char[]{lines[i + 1].charAt(j), lines[i + 2].charAt(j), lines[i + 3].charAt(j)}, MAS));
        XMAS_RULES.add(lines -> (i, j) ->
                j - 3 >= 0
                        && Arrays.equals(new char[]{lines[i].charAt(j - 1), lines[i].charAt(j - 2), lines[i].charAt(j - 3)}, MAS));
        XMAS_RULES.add(lines -> (i, j) ->
                j + 3 < lines[i].length()
                        && Arrays.equals(new char[]{lines[i].charAt(j + 1), lines[i].charAt(j + 2), lines[i].charAt(j + 3)}, MAS));
        XMAS_RULES.add(lines -> (i, j) ->
                i + 3 < lines.length && j + 3 < lines[i].length()
                        && Arrays.equals(new char[]{lines[i + 1].charAt(j + 1), lines[i + 2].charAt(j + 2), lines[i + 3].charAt(j + 3)}, MAS));
        XMAS_RULES.add(lines -> (i, j) ->
                i + 3 < lines.length && j - 3 >= 0
                        && Arrays.equals(new char[]{lines[i + 1].charAt(j - 1), lines[i + 2].charAt(j - 2), lines[i + 3].charAt(j - 3)}, MAS));
        XMAS_RULES.add(lines -> (i, j) ->
                i - 3 >= 0 && j - 3 >= 0
                        && Arrays.equals(new char[]{lines[i - 1].charAt(j - 1), lines[i - 2].charAt(j - 2), lines[i - 3].charAt(j - 3)}, MAS));
        XMAS_RULES.add(lines -> (i, j) ->
                i - 3 >= 0 && j + 3 < lines[i].length()
                        && Arrays.equals(new char[]{lines[i - 1].charAt(j + 1), lines[i - 2].charAt(j + 2), lines[i - 3].charAt(j + 3)}, MAS));
    }

    static {
        MAS_RULES.add(lines -> (i, j) ->
                i + 2 < lines.length && j + 2 < lines[i].length()
                        && Arrays.deepEquals(
                        MASK.apply(lines).apply(i, j),
                        new char[][]{
                                {'M', 'M'},
                                {'A'},
                                {'S', 'S'}
                        }
                )
        );
        MAS_RULES.add(lines -> (i, j) ->
                i + 2 < lines.length && j + 2 < lines[i].length()
                        && Arrays.deepEquals(
                        MASK.apply(lines).apply(i, j),
                        new char[][]{
                                {'S', 'M'},
                                {'A'},
                                {'S', 'M'}
                        }
                )
        );
        MAS_RULES.add(lines -> (i, j) ->
                i + 2 < lines.length && j + 2 < lines[i].length()
                        && Arrays.deepEquals(
                        MASK.apply(lines).apply(i, j),
                        new char[][]{
                                {'S', 'S'},
                                {'A'},
                                {'M', 'M'}
                        }
                )
        );
        MAS_RULES.add(lines -> (i, j) ->
                i + 2 < lines.length && j + 2 < lines[i].length()
                        && Arrays.deepEquals(
                        MASK.apply(lines).apply(i, j),
                        new char[][]{
                                {'M', 'S'},
                                {'A'},
                                {'M', 'S'}
                        }
                )
        );
    }

    public static void main(String[] args) throws IOException {
        var path = Path.of("src/resources/Day4.txt");
        var lines = String.join("\n", Files.readAllLines(path)).split("\n");

        lines = TEST.split("\n");

        var xmasCount = getXmasCount(lines);
        System.out.println("xmasCount: " + xmasCount);

        int crossMasCount = getCrossMasCount(lines);
        System.out.println("crossMasCount: " + crossMasCount);

    }

    private static int getCrossMasCount(String[] lines) {
        var res = 0;
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                int finalI = i, finalJ = j;
                if (MAS_RULES.stream().anyMatch(r -> r.apply(lines).test(finalI, finalJ))) res++;
            }
        }
        return res;
    }

    private static int getXmasCount(String[] lines) {
        var res = 0;
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                if (lines[i].charAt(j) == 'X') {
                    for (var rule : XMAS_RULES) {
                        if (rule.apply(lines).test(i, j)) res++;
                    }
                }
            }
        }
        return res;
    }
}