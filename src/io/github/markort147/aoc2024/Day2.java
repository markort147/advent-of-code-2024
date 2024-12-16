package io.github.markort147.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 {

    private static final String TEST = """
            7 6 4 2 1
            1 2 7 8 9
            9 7 6 2 1
            1 3 2 4 5
            8 6 4 4 1
            1 3 6 7 9
            1 2 3 4 10""";

    private static final int FAULT_TOLERANCE = 1;

    public static void main(String[] args) throws IOException {
        var path = Path.of("src/resources/Day2.txt");
        List<String> lines = Files.readAllLines(path);

//        lines = Arrays.asList(TEST.split("\n"));

        List<List<String>> splitLines = lines.stream().map(l -> Arrays.stream(l.split(" ")).toList()).toList();
        List<List<Integer>> intLines = splitLines.stream().map(l -> l.stream().map(Integer::parseInt).toList()).toList();
        var res = intLines.stream()
                .filter(l -> isSafe(l, 0))
                .count();
        System.out.println("res: " + res);
    }

    private static boolean isSafe(List<Integer> line, int faults) {

        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < faults; i++) {
            prefix.append("\t");
        }

        boolean ascending = false;
        for (int i = 0; i < line.size() - 1; i++) {
            var diff = line.get(i) - line.get(i + 1);
            if (diff != 0) {
                if (i == 0) ascending = diff > 0;
                if (((diff > 0 && ascending) || (diff < 0 && !ascending)) && Math.abs(diff) <= 3)
                    continue;
            }
            System.out.println(prefix + "line: " + line + " is safe: " + false);
            if (faults < FAULT_TOLERANCE) {
                for (int j = 0; j < line.size(); j++) {
                    var subList = new ArrayList<>(line);
                    subList.remove(j);
                    if (isSafe(subList, faults + 1))
                        return true;
                }
            }
            return false;
        }
        System.out.println(prefix + "line: " + line + " is safe: " + true);
        return true;
    }
}