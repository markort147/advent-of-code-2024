package io.github.markort147.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Day3 {
    static final Pattern patternA = Pattern.compile("(?<=\\()\\d+");
    static final Pattern patternB = Pattern.compile("(?<=,)\\d+");
    static final Pattern mulPattern = Pattern.compile("mul\\(\\d+,\\d+\\)");

    public static void main(String[] args) throws IOException {
        var file = Path.of("src/resources/Day3.txt");
        var input = Files.readString(file);
//        input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](do()mul(11,8)don't()undo()?mul(8,5))don't()mul(8,50))";

        List<String> boxes = getBoxes(input);
        long res = getRes(boxes);
        System.out.println("res=" + res);
    }

    private static long getRes(List<String> boxes) {
        long res = 0L;
        for (String box : boxes) {
            System.out.println("curLine=" + box);
            var matcher = mulPattern.matcher(box);
            while (matcher.find()) {
                var curMul = matcher.group();
                System.out.println("\tcurMul=" + curMul);
                res += getAdding(matcher.group());
            }
        }
        return res;
    }

    private static List<String> getBoxes(String input) {
        List<String> boxes = new ArrayList<>();
        for (int pos = 0; pos < input.length(); ) {

            int doIdx;
            if (pos == 0 && !input.startsWith("don't\\(\\)")) {
                doIdx = 0;
            } else {
                doIdx = input.indexOf("do()", pos);
            }
            int dontIdx = input.indexOf("don't()", doIdx);

            String currLine;
            if (doIdx != -1) {
                if (dontIdx != -1) {
                    currLine = input.substring(doIdx, dontIdx);
                    pos = dontIdx + 7;
                } else {
                    currLine = input.substring(doIdx);
                    pos = input.length();
                }
                boxes.add(currLine);
            } else {
                pos = input.length();
            }
        }
        return boxes;
    }

    private static long getAdding(String group) {
        var matcherA = patternA.matcher(group);
        var matcherB = patternB.matcher(group);
        if (matcherA.find() && matcherB.find()) {
            var a = Long.parseLong(matcherA.group());
            var b = Long.parseLong(matcherB.group());
            var c = a * b;
            System.out.printf("\t\t%d * %d = %d%n", a, b, c);
            return c;
        }
        return 0;
    }
}