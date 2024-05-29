package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;


public class CreatingInitialMatrix {

    public static List<List<Coordinate>> creatingInitialMatrix() {
        List<Coordinate> coordinates = new ArrayList<>();

        //filling the "coordinates" of the coordinates:
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 30; y++) {
                Coordinate coordinate = new Coordinate();
                coordinate.setX(x);
                coordinate.setY(y);
                coordinates.add(coordinate);
            }
        }


        //filling the IDs of the coordinates:
        for (int i = 0; i < coordinates.size(); i++) {
            coordinates.get(i).setId(i);
        }


        //random generating the bombs:
        //using set to avoid duplicate values:
        TreeSet<Integer> bombs = new TreeSet<>();

        while (bombs.size() < 99) {
            Random random = new Random();
            bombs.add(random.nextInt(480));
        }

        for (Coordinate coordinate : coordinates) {
            if (bombs.contains(coordinate.getId())) {
                coordinate.setBomb(true);
            }
        }

        //creating matrix and filling it with the linear list coordinates:
        List<List<Coordinate>> matrix = new ArrayList<>();

        for (int x = 0; x < 16; x++) {
            List<Coordinate> temp = new ArrayList<>();
            for (int y = 0; y < 30; y++) {
                Coordinate cord = new Coordinate();
                for (Coordinate coordinate : coordinates) {
                    if (coordinate.getX() == x && coordinate.getY() == y) {
                        cord = coordinate;
                    }
                }
                temp.add(cord);
            }
            matrix.add(temp);
        }

        //counting and filling the number of bombs on a coordinates that are not bombs:
        //in four directions and four diagonal directions, total count of bombs around a number can be <=8;

        for (List<Coordinate> temp : matrix) {
            for (Coordinate coordinate : temp) {
                int x = coordinate.getX();
                int y = coordinate.getY();
                int cBombs = 0;
                for (Coordinate linearC : coordinates) {
                    if (linearC.isBomb()) {
                        if (linearC.getX() == x + 1 && linearC.getY() == y + 1) {
                            cBombs += 1;
                        }
                        if (linearC.getX() == x - 1 && linearC.getY() == y + 1) {
                            cBombs += 1;
                        }
                        if (linearC.getX() == x + 1 && linearC.getY() == y - 1) {
                            cBombs += 1;
                        }
                        if (linearC.getX() == x - 1 && linearC.getY() == y - 1) {
                            cBombs += 1;
                        }
                        if (linearC.getX() == x + 1 && linearC.getY() == y) {
                            cBombs += 1;
                        }
                        if (linearC.getX() == x - 1 && linearC.getY() == y) {
                            cBombs += 1;
                        }
                        if (linearC.getX() == x && linearC.getY() == y + 1) {
                            cBombs += 1;
                        }
                        if (linearC.getX() == x && linearC.getY() == y - 1) {
                            cBombs += 1;
                        }
                    }
                }
                coordinate.setCountBomb(cBombs);
            }
        }

        //setting the types,  isMarked, isVisible and isClicked of the coordinates:
        for (List<Coordinate> temp : matrix) {
            for (Coordinate coordinate : temp) {
                coordinate.setVisible(false);
                coordinate.setMarked(false);
                coordinate.setClicked(false);

                int countB = coordinate.getCountBomb();
                String countBS = String.valueOf(countB);
                if (coordinate.isBomb()) {
                    coordinate.setType("B");
                } else if (!coordinate.isBomb() && coordinate.getCountBomb() == 0) {
                    coordinate.setType("E");
                } else if (!coordinate.isBomb() && coordinate.getCountBomb() > 0) {
                    coordinate.setType(countBS);
                }
            }
        }
        return matrix;
    }
}
