package PerfomanceLab.task3;

import java.io.*;
import java.util.*;

public class Task3 {

    public static void main(String[] args) throws IOException {

        String path = args[0];
        List<Double> list = new ArrayList<>();

        try (BufferedReader br1 = new BufferedReader(new FileReader(path + "\\Cash1.txt"));
             BufferedReader br2 = new BufferedReader(new FileReader(path + "\\Cash2.txt"));
             BufferedReader br3 = new BufferedReader(new FileReader(path + "\\Cash3.txt"));
             BufferedReader br4 = new BufferedReader(new FileReader(path + "\\Cash4.txt"));
             BufferedReader br5 = new BufferedReader(new FileReader(path + "\\Cash5.txt"))) {
            String line1 = br1.readLine().replace("\\n", "");
            String line2 = br2.readLine().replace("\\n", "");
            String line3 = br3.readLine().replace("\\n", "");
            String line4 = br4.readLine().replace("\\n", "");
            String line5 = br5.readLine().replace("\\n", "");

            while (line1 != null) {
                list.add(Double.parseDouble(line1.replace("\\n", ""))
                        + Double.parseDouble(line2)
                        + Double.parseDouble(line3)
                        + Double.parseDouble(line4)
                        + Double.parseDouble(line5));

                if ((line1 = br1.readLine()) != null) {
                    line2 = br2.readLine().replace("\\n", "");
                    line3 = br3.readLine().replace("\\n", "");
                    line4 = br4.readLine().replace("\\n", "");
                    line5 = br5.readLine().replace("\\n", "");
                }
            }
        }
        System.out.println(list.indexOf(list.stream().max(Double::compareTo).get()) + 1);
    }

}
