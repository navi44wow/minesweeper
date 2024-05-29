package org.example;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        String line = null;

        do {

            List<List<Coordinate>> newMatrix = CreatingInitialMatrix.creatingInitialMatrix();
            //първоначално принтираш матрица с идтата;


            File file = new File("C:\\Users\\User\\Desktop\\currentSolvedMatrix.csv");

            StringBuilder sb = new StringBuilder();

            for (List<Coordinate> temp : newMatrix) {
                for (Coordinate coordinate : temp) {
                    //  String s = ("|" + coordinate.getId() + " " + coordinate.getType() + " ");

                    String id = String.valueOf(coordinate.getId());
                    int len = id.length();
                    int xy = 3 - len;
                    String s = ("|" + " ".repeat(Math.max(0, xy)) + coordinate.getId()+ " " + coordinate.getType()  + " ");


                    sb.append(s);
                }
                sb.append("|");
                sb.append("\n");

            }

            try {

                FileWriter writer = new FileWriter(file);

                writer.write(sb.toString());
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            //

            System.out.println("initial matrix: ");
            for (List<Coordinate> temp : newMatrix) {
                for (Coordinate coordinate : temp) {
                    //System.out.print(coordinate.getId() + " ");
                    String id = String.valueOf(coordinate.getId());
                    int len = id.length();
                    int xy = 3 - len;
                    System.out.print("|" + " ".repeat(Math.max(0, xy)) + coordinate.getId() + " " + " ");
                }
                System.out.print("|");
                System.out.println();
                System.out.println("_".repeat(Math.max(0, 180)));
            }
            System.out.println("please start sweeping the mines:) ");

            do {
                //после почваш да попълваш
                String n = scanner.nextLine();
                int b = 0;
                int N = 0;
                String BMarked = null;
                //tuk с ид последвано от б - маркираш бомба; ид последвано от у - значи унмаркване

                try {
                    char bMarked = n.charAt(n.length() - 1);
                    BMarked = String.valueOf(bMarked);

                    if (BMarked.equals("b") || BMarked.equals("u")) {
                        String bombID = n.substring(0, n.length() - 1);
                        b = 1;
                        N = Integer.parseInt(bombID);
                        //} else if (n.charAt(n.length() - 1) != 'b' && n.charAt(n.length() - 1) != 'u') {
                    } else {

                        N = Integer.parseInt(n);
                    }
                } catch (Exception e) {
                    System.out.println("please make valid input between 0 and 479");
                }

                int x = 0;
                int y = 0;

                //тук проверява дали е бомба, ако е - играта свършва; ако не е - сетва стойността на кордината висибъл и кликд;

                //Coordinate coordTemp = newMatrix.get(N);
                for (List<Coordinate> temp : newMatrix) {
                    for (Coordinate coordinate : temp) {
                        if (coordinate.getId() == N) {

                            if (coordinate.isBomb() && b == 0 && !coordinate.isVisible()) {
                                //    z = 1;
                                x = 1;
                                System.out.println("Game over");
                                break;
                            } else if (!coordinate.isBomb() && b == 1 && !coordinate.isVisible()) {
                                //тук ако си го маркирал като бомба но то не е: какво правиш:
                                coordinate.setMarked(true);
                                coordinate.setVisible(true);


                            } else if (coordinate.isBomb() && b == 1 && !coordinate.isVisible()) {
                                //тук ако е бомба и ти го маркираш като тагова го правиш маркирано и видимо
                                coordinate.setMarked(true);
                                coordinate.setVisible(true);

                            } else if (Objects.equals(BMarked, "u") && coordinate.isMarked()) {
                                //тук какво правиш ако отмаркираш;
                                coordinate.setMarked(false);
                                coordinate.setVisible(false);

                            } else if (coordinate.getType().equals("E")) {
                                //тук ако не е бомба няма нужда да се маркира защото е емпти или цифра:
                                //ето тук ако е емпти: как действаме;

                                revealWholeFieldOfEmpties(coordinate, newMatrix);

                                //coordinate.setVisible(true);
                            } else {//тук ако не е бомба няма нужда да се маркира защото е емпти или цифра:
                                coordinate.setVisible(true);
                            }
                        }
                    }
                    if (x == 1) {
                        y = 1;
                        break;
                    }
                    System.out.println();
                }
                if (y == 1) {
                    break;
                }

                System.out.println("the matrix: ");
                for (List<Coordinate> temp : newMatrix) {
                    for (Coordinate coordinate : temp) {
                        String id = String.valueOf(coordinate.getId());

                        int xy = 3 - id.length();
                        if (coordinate.isVisible() && coordinate.isMarked()) {
                            xy = 3;
                            //System.out.print("|" + " ".repeat(xy) + " " + coordinate.getType());
                            System.out.print("|" + " ".repeat(xy) + " " + "B");
                        } else if (coordinate.isVisible() && !coordinate.isMarked()) {
                            xy = 3;
                            if (coordinate.getType().equals("E")) {
                                System.out.print("|" + " ".repeat(xy) + "  ");
                            } else {
                                System.out.print("|" + " ".repeat(xy) + " " + coordinate.getType());
                            }
                        } else {
                            System.out.print("|" + " ".repeat(Math.max(0, xy)) + coordinate.getId() + " " + " ");
                        }
                    }
                    System.out.print("|");
                    System.out.println();
                    System.out.println("_".repeat(Math.max(0, 180)));
                }
                System.out.println("please continue sweeping the mines:) ");


                //на всяко попълване я принтираш

                /*
                ако бомбите са повече - бреак и принт честито! и въпрос за нова игра!??
                 */
                int countVisible = 0;
                for (List<Coordinate> temp : newMatrix) {
                    for (Coordinate coordinate : temp) {
                        if (coordinate.isVisible() && !coordinate.isBomb()) {
                            countVisible++;
                        }
                    }
                }

                if (countVisible == 381) {
                    System.out.println("Congrats, you have won!");
                    break;
                }

            } while (!Objects.equals(line, "n"));
            //тук ако искаме продължаваме с нова игра, ако не, пишем n и програмата спира;
            System.out.println("new game?y/n");
            line = scanner.nextLine();
            if (line.equals("n")) {
                break;
            }


        } while (!line.equals("end"));

    }

    private static void revealWholeFieldOfEmpties(Coordinate coordinate, List<List<Coordinate>> matrix) {
//tova e selektnato taka che go pravish visible
        coordinate.setVisible(true);

        int x = coordinate.getX();
        int y = coordinate.getY();
        List<Coordinate> otherToBeRevealed = new ArrayList<>();

        for (int i = 0; i < matrix.size(); i++) {
            List<Coordinate> temp = matrix.get(i);
            for (int j = 0; j < temp.size(); j++) {
                Coordinate current = temp.get(j);
                int xC = current.getX();
                int yC = current.getY();
                //тук разпиши осемте  сценария за посоките:
                /*
                едното е за такива дето са емпти - добавяш ги в списък и за всяко правиш рекурсия
                другите дето са си числа- не ги слагаш в списъка, а само ги показваш
                 */
                if (xC == x - 1 && yC == y && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x + 1 && yC == y && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x && yC == y - 1 && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x && yC == y + 1 && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    otherToBeRevealed.add(current);
                }

                //
                if (xC == x - 1 && yC == y - 1 && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x + 1 && yC == y + 1 && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x + 1 && yC == y - 1 && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x - 1 && yC == y + 1 && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    otherToBeRevealed.add(current);
                }

                if (xC == x - 1 && yC == y && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                }
                if (xC == x + 1 && yC == y && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                }
                if (xC == x && yC == y - 1 && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                }
                if (xC == x && yC == y + 1 && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                }

                //diagonali:
                if (xC == x - 1 && yC == y + 1 && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                }
                if (xC == x + 1 && yC == y - 1 && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                }
                if (xC == x - 1 && yC == y - 1 && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                }
                if (xC == x + 1 && yC == y + 1 && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                }


            }

        }

        for (int i = 0; i < otherToBeRevealed.size(); i++) {
            Coordinate temp = otherToBeRevealed.get(i);
            revealWholeFieldOfEmpties(temp, matrix);

        }
    }
    //write recursion code here
    //	функцията рекурсивно
    //	 ако е емпти почвап да добавяш в списък


    //	 списъка всички ги маркираш висибъл
    //	 и ги вадиш от него
    //
    //	 и после всяко от списъка го действаш него;
    //	 за да няма повтаряне и стак овърфлоу - ако е висибъл не го добавяш
    //
    //
    //	 може само в 4 те посоки, без диагонали


}
