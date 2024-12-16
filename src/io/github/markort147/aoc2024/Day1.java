package io.github.markort147.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day1 {

    private static final String mock = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3""";

    public static void main(String[] args) throws IOException {
        Path path = Path.of("src/resources/Day1.txt");
        List<String> lines = Files.readAllLines(path);

//        lines = Arrays.asList(mock.split("\n"));
        System.out.println("lines: " + lines);

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        fillLists(lines, list1, list2);

        var distance = getDistance(list1, list2);

        var similarity = 0L;
        for (int a : list1) {
            similarity += a * list2.stream().filter(b -> b == a).count();
        }

        System.out.println("distance: " + distance);
        System.out.println("similarity: " + similarity);
    }

    private static void fillLists(List<String> lines, List<Integer> list1, List<Integer> list2) {
        for (String line : lines) {
//            System.out.println("line: " + line);
            String[] split = line.split("\\s+");
//            System.out.println("\tsplit[0]=" + split[0] + " split[1]=" + split[1]);
            list1.add(Integer.parseInt(split[0]));
            list2.add(Integer.parseInt(split[1]));
        }
    }

    private static int getDistance(List<Integer> list1, List<Integer> list2) {
        list1.sort(Integer::compareTo);
        list2.sort(Integer::compareTo);
        var res = 0;
        for (int i = 0; i < list1.size(); i++) {
            res += Math.abs(list1.get(i) - list2.get(i));
        }
        return res;
    }
}