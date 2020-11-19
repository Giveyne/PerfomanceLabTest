package PerfomanceLab.task2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Task2 {
    private static final String POINT_ON_ONE_OF_THE_VERTEXES = "точка на одной из вершин";
    private static final String POINT_ON_ONE_OF_THE_SIDE = "точка на одной из сторон";
    private static final String POINT_INSIDE = "точка внутри";
    private static final String POINT_OUTSIDE = "точка снаружи";

    private static Float[] coordinates(String string) {
        Float[] floats = new Float[2];
        String[] coordinates;
        coordinates = string.replace("\\n", "").split(" ");
        floats[0] = Float.parseFloat(coordinates[0]);
        floats[1] = Float.parseFloat(coordinates[1]);
        return floats;
    }

    private static Map<Float[], String> wherePoint(List<Float[]> coordinatesQuadrangle, List<Float[]> coordinatesPains) {
        Map<Float[], String> mapResult = new LinkedHashMap<>();
        Float paintX;
        Float paintY;
        Float quadX1;
        Float quadY1;
        Float quadX2;
        Float quadY2;
        boolean[] positive = new boolean[4];
        for (Float[] paint : coordinatesPains) {

            for (int i = 0; i < coordinatesQuadrangle.size(); i++) {
                paintX = paint[0];
                paintY = paint[1];
                quadX1 = coordinatesQuadrangle.get(i)[0];
                quadY1 = coordinatesQuadrangle.get(i)[1];
                if (i < 3) {
                    quadX2 = coordinatesQuadrangle.get(i + 1)[0];
                    quadY2 = coordinatesQuadrangle.get(i + 1)[1];
                } else {
                    quadX2 = coordinatesQuadrangle.get(0)[0];
                    quadY2 = coordinatesQuadrangle.get(0)[1];
                }
                float resultCoordinates = (quadX2 - quadX1) * (paintY - quadY1) - (quadY2 - quadY1) * (paintX - quadX1);
                positive[i] = (resultCoordinates < 0);
                if ((paintX.equals(quadX1) && paintY.equals(quadY1)) || (paintX.equals(quadX2) && paintY.equals(quadY2))) {
                    mapResult.put(paint, POINT_ON_ONE_OF_THE_VERTEXES);
                    break;
                } else if (resultCoordinates == 0 && onInterval(paintX, paintY, quadX1, quadY1, quadX2, quadY2)) {
                    mapResult.put(paint, POINT_ON_ONE_OF_THE_SIDE);
                    break;
                }
            }

            if (positive[0] && positive[1] && positive[2] && positive[3]) {
                mapResult.putIfAbsent(paint, POINT_INSIDE);
            } else {
                mapResult.putIfAbsent(paint, POINT_OUTSIDE);
            }
        }

        return mapResult;
    }

    private static boolean onInterval(Float x1, Float y1, Float x2, Float y2, Float x3, Float y3) {
        double diffX1 = x1 - x2;
        double diffX2 = x1 - x3;
        double diffY1 = y1 - y2;
        double diffY2 = y1 - y3;
        double diffX3 = x2 - x3;
        double diffY3 = y2 - y3;

        return (Math.sqrt(Math.pow(diffX1, 2) + Math.pow(diffY1, 2)) + Math.sqrt(Math.pow(diffX2, 2) + Math.pow(diffY2, 2))) == Math.sqrt(Math.pow(diffX3, 2) + Math.pow(diffY3, 2));
    }

    public static void main(String[] args) {
        String path = args[0];
        String path2 = args[1];

        String string;
        List<Float[]> listCoordinates = new LinkedList<>();
        List<Float[]> listPoints = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path));
             BufferedReader readerPoint = new BufferedReader(new FileReader(path2))) {
            while ((string = reader.readLine()) != null) {
                listCoordinates.add(coordinates(string));
            }
            while ((string = readerPoint.readLine()) != null) {
                listPoints.add(coordinates(string));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Float[], String> map = wherePoint(listCoordinates, listPoints);
        Iterator<Map.Entry<Float[], String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Float[], String> pair = iterator.next();
            Float[] key = pair.getKey();
            String value = pair.getValue();
            System.out.println(key[0] + " " + key[1] + ":" + value);
        }

    }
}
