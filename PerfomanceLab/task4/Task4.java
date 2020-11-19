package PerfomanceLab.task4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Task4 {
    private static void findIntervals(List<Integer[]> times) {
        Map<Integer, Integer> eventMap = new TreeMap<>();
        for (Integer[] personTime : times) {
            eventMap.compute(personTime[0], (time, counter) -> counter == null ? 1 : ++counter);
            eventMap.compute(personTime[1], (time, counter) -> counter == null ? -1 : --counter);
        }

        int currentNumberOfPeople = 0;
        int maxPeople = 0;
        Integer startTime = null;
        List<Integer[]> intervals = new ArrayList<>();
        for (Map.Entry<Integer, Integer> event : eventMap.entrySet()) {
            Integer income = event.getValue();
            if (income == 0) {
                continue;
            }
            if (maxPeople != currentNumberOfPeople) {
                intervals.clear();
            }
            currentNumberOfPeople += event.getValue();
            if (income > 0 && maxPeople <= currentNumberOfPeople) {
                if (maxPeople != currentNumberOfPeople) {
                    intervals.clear();
                }
                maxPeople = currentNumberOfPeople;
                startTime = event.getKey();
            }
            if (income < 0 && startTime != null) {
                intervals.add(new Integer[]{startTime, event.getKey()});
                startTime = null;
            }
        }

        printIntervals(intervals);
    }

    private static void printIntervals(List<Integer[]> intervals) {
        intervals.forEach(interval -> System.out.printf("%s %s%n", toTime(interval[0]), toTime(interval[1])));
    }

    private static String toTime(Integer timeNumber) {
        String time = timeNumber.toString();
        int pos = time.length() - 2;
        return time.substring(0, pos) + ":" + time.substring(pos);
    }

    public static void main(String[] args) {

        String path = args[0];


        String visitTime;
        String[] visitTimeStartStop; // время входа и выхода
        List<Integer[]> listTimes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while ((visitTime = reader.readLine()) != null) {

                visitTimeStartStop = visitTime
                        .replace("\\n", "")
                        .replaceAll(":", "")
                        .split(" ");
                Integer[] arrInt = new Integer[2];
                arrInt[0] = Integer.parseInt(visitTimeStartStop[0]);
                arrInt[1] = Integer.parseInt(visitTimeStartStop[1]);
                listTimes.add(arrInt);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        findIntervals(listTimes);

    }
}
