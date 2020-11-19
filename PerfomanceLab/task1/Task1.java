package PerfomanceLab.task1;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Task1 {

    private static float percentil90(List<Integer> list) {
        float number = 0.9f * (list.size() - 1) + 1;
        float numberFraction = number - (int) number;
        int nearestNumber = list.get((int) number - 1);
        int nextNearestNumber = list.get((int) number);
        return nearestNumber + numberFraction * (nextNearestNumber - nearestNumber);
    }

    private static float median(List<Integer> list) {
        float median;
        if (list.size() % 2 == 0) {
            median = (float) (list.get(list.size() / 2 - 1) + list.get(list.size() / 2)) / 2;
        } else {
            median = list.get(list.size() / 2);
        }
        return median;
    }

    private static float average(List<Integer> list) {
        double d = list.stream().collect(Collectors.averagingDouble(Integer::doubleValue));
        return (float) d;
    }


    public static void main(String[] args) throws IOException {

        String path = args[0];

        Scanner s = new Scanner(new FileInputStream(path));
        List<Integer> list = new ArrayList<>();
        while (s.hasNext()) {
            list.add(s.nextInt());
        }
        Collections.sort(list);
        if (list.size() == 0) {
            System.out.println("no items to compute!");
        } else if (list.size() == 1) {
            for (int i = 0; i < 5; i++) {
                System.out.printf("%.2f\n", (float) list.get(0));
            }
        } else {
            float medianNumber = median(list);
            float max = list.get(list.size() - 1);
            float min = list.get(0);
            float averageNumber = average(list);
            System.out.printf(Locale.US, "%.2f\n%.2f\n%.2f\n%.2f\n%.2f\n", percentil90(list), medianNumber, max, min, averageNumber);
        }
    }
}
