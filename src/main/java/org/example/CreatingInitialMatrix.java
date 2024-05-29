package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;



    public class CreatingInitialMatrix {

        //public static void main(String[] args) {

        public static List<List<Coordinate>> creatingInitialMatrix() {
            List<Coordinate> coordinates = new ArrayList<>();

//попълваш координатите
            for (int x = 0; x < 16; x++) {

                for (int y = 0; y < 30; y++) {
                    Coordinate coordinate = new Coordinate();
                    coordinate.setX(x);
                    coordinate.setY(y);
                    coordinates.add(coordinate);
                }
            }


//попълваш ид
            //     System.out.println(coordinates.size());
            for (int i = 0; i < coordinates.size(); i++) {
                coordinates.get(i).setId(i);
            }


            //рандом генератор

            TreeSet<Integer> bombs = new TreeSet<>();

            while (bombs.size() < 99) {
                Random random = new Random();
                bombs.add(random.nextInt(480));
            }

            //   Collections.sort(bombs);
            //  System.out.println(bombs);

            //System.out.println(bombs.size());

            for (Coordinate coordinate : coordinates) {
                if (bombs.contains(coordinate.getId())) {
                    coordinate.setBomb(true);
                }
            }

            for (Coordinate coordinate : coordinates) {
//            System.out.print(coordinate);
//            System.out.println();
            }

            int counter = 0;

            for (Coordinate coordinate : coordinates) {
                if (coordinate.isBomb()) {
                    counter++;
                }
            }
            //  System.out.println(counter);


            //направи матрица:
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

            //   System.out.println("linear list: ");
            //принтираш каквото ти е нужно:
            for (int x = 0; x < 16; x++) {

                for (int y = 0; y < 30; y++) {

                    for (Coordinate coordinate : coordinates) {
                        if (coordinate.getX() == x &&
                                coordinate.getY() == y) {
                            if (coordinate.isBomb()) {
                                //   System.out.print("B ");
                            } else {
                                //  System.out.print(coordinate.getCountBomb() + " ");
                            }
                        }
                    }
                }
                // System.out.println();
            }


            //


            //  System.out.println("matrix");
            for (List<Coordinate> temp : matrix) {
                for (Coordinate coordinate : temp) {
                    //       System.out.print(coordinate.getId() + " ");
                }
                //    System.out.println();
            }

//броене и попълване на броя бомби около числото
            for (List<Coordinate> temp : matrix) {
                for (Coordinate coordinate : temp) {
                    int x = coordinate.getX();
                    int y = coordinate.getY();
                    int cBombs = 0;
                    for (int i = 0; i < coordinates.size(); i++) {
                        Coordinate linearC = coordinates.get(i);
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


            //  System.out.println("matrix filled counters: ");
            for (List<Coordinate> temp : matrix) {
                for (Coordinate coordinate : temp) {
                    if (coordinate.isBomb()) {
                        //       System.out.print("B ");
                    } else {
                        //       System.out.print(coordinate.getCountBomb() + " ");
                    }
                }
                //     System.out.println();
            }

            for (List<Coordinate> temp : matrix) {
                for (Coordinate coordinate : temp) {
                    coordinate.setVisible(false);
                    coordinate.setMarked(false);

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
