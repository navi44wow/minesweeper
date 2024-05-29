package org.example;


import java.io.*;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        String line = null;

        do {
            //creating the matrix:
            List<List<Coordinate>> newMatrix = CreatingInitialMatrix.creatingInitialMatrix();

            //saving the solved matrix in a file in our local system, in order to compare, if needed;
            // you should  replace "User" with your username:

            File file = new File("C:\\Users\\User\\Desktop\\currentSolvedMatrix.csv");

            StringBuilder sb = new StringBuilder();

            for (List<Coordinate> temp : newMatrix) {
                for (Coordinate coordinate : temp) {
                    String id = String.valueOf(coordinate.getId());
                    int len = id.length();
                    int xy = 3 - len;
                    String s = ("|" + " ".repeat(Math.max(0, xy)) + coordinate.getId() + " " + coordinate.getType() + " ");
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

            //initial print of the matrix with ID values:
            System.out.println("initial matrix: ");
            System.out.println("initial count of mines: 99 ");

            for (List<Coordinate> temp : newMatrix) {
                for (Coordinate coordinate : temp) {
                    String id = String.valueOf(coordinate.getId());
                    int len = id.length();
                    int xy = 3 - len;
                    System.out.print("|" + " ".repeat(Math.max(0, xy)) + coordinate.getId());
                }
                System.out.print("|");
                System.out.println();
            }
            System.out.println("please start sweeping the mines:) ");
            System.out.println("use number between 0 and 479 to make a field visible(" +
                    "only if you are sure that it is not a bomb ;) );");
            System.out.println("use number between 0 and 479 followed by letter b as in" +
                    "the example - 470b to mark a field as bomb;");
            System.out.println("use number between 0 and 479 followed by letter u as in" +
                    "the example - 470u to unmark a field as bomb;");

            do {
                //here we start filling the matrix, or sweeping the mines:
                String n = scanner.nextLine();
                int b = 0;
                int N = 0;
                String BMarked = null;

                //this try catch block defend us from wrong input:
                try {
                    char bMarked = n.charAt(n.length() - 1);
                    BMarked = String.valueOf(bMarked);

                    if (BMarked.equals("b") || BMarked.equals("u")) {
                        String bombID = n.substring(0, n.length() - 1);
                        b = 1;
                        N = Integer.parseInt(bombID);
                    } else {
                        N = Integer.parseInt(n);
                    }
                } catch (Exception e) {
                    System.out.println("please make valid input between 0 and 479");
                }

                //we use these variables to help us break from nested loops:
                int x = 0;
                int y = 0;

                //here it checks if it is a bomb, if yes - game over;
                //if it is not a bomb - it sets the coordinate values of visible and marked as true:

                for (List<Coordinate> temp : newMatrix) {
                    for (Coordinate coordinate : temp) {
                        if (coordinate.getId() == N) {
                            if (coordinate.isBomb() && b == 0 && !coordinate.isVisible()) {
                                x = 1;
                                System.out.println("Game over");
                                break;
                            } else if (!coordinate.isBomb() && b == 1 && !coordinate.isVisible()) {
                                //if marked as bomb, but is not, we do:
                                coordinate.setMarked(true);
                                coordinate.setVisible(true);
                            } else if (coordinate.isBomb() && b == 1 && !coordinate.isVisible()) {
                                //if it is bomb, and you mark it, then you do:
                                coordinate.setMarked(true);
                                coordinate.setVisible(true);
                            } else if (Objects.equals(BMarked, "u") && coordinate.isMarked()) {
                                //you do this, if you unmark;
                                coordinate.setMarked(false);
                                coordinate.setVisible(false);
                            } else if (coordinate.getType().equals("E")) {
                                //here is the logic for clicked field, that is empty:
                                RevealWholeFieldOfEmpties.revealWholeFieldOfEmpties(coordinate, newMatrix);
                            } else {
                                //here if it is not a bomb it is unneeded to be marked because it is
                                //field that is empty or containing a number of bombs:
                                coordinate.setVisible(true);
                            }
                        }
                    }
                    //we use the x variable to check if we need to break; if x==1 we do y = 1, which will help
                    //us break from the outer loop too:
                    if (x == 1) {
                        y = 1;
                        break;
                    }
                    System.out.println();
                }
                //here y help us break from this outer loop too:
                if (y == 1) {
                    break;
                }

                //Lets count the bombs after this input:
                int countMarkedAsBombs = 0;
                for (List<Coordinate> temp : newMatrix) {
                    for (Coordinate coordinate : temp) {
                        if (coordinate.isMarked()) {
                            countMarkedAsBombs++;
                        }
                    }
                }

                int remainingBombs;
                //here, if we have marked more than 99 bombs, we want to print 0, which are te number of bombs remaining to be marked
                //and we do not want to print a negative number;
                if (countMarkedAsBombs > 99) {
                    remainingBombs = 0;
                } else {
                    remainingBombs = 99 - countMarkedAsBombs;
                }


                //after performing some of the above operations(depending on our input), we print
                //the temporary matrix, and ask the user to make next input:
                System.out.println("the current matrix: ");
                System.out.println("the remaining mines: " + remainingBombs);
                for (List<Coordinate> temp : newMatrix) {
                    for (Coordinate coordinate : temp) {
                        String id = String.valueOf(coordinate.getId());
                        int xy = 3 - id.length();
                        if (coordinate.isVisible() && coordinate.isMarked()) {
                            xy = 3;
                            System.out.print("|" + " ".repeat(xy - 1) + "B");
                        } else if (coordinate.isVisible() && !coordinate.isMarked()) {
                            xy = 3;
                            if (coordinate.getType().equals("E")) {
                                System.out.print("|" + " ".repeat(xy));
                            } else {
                                System.out.print("|" + " ".repeat(xy - 1) + coordinate.getType());
                            }
                        } else {
                            System.out.print("|" + " ".repeat(Math.max(0, xy)) + coordinate.getId());
                        }
                    }
                    System.out.print("|");
                    System.out.println();
                }
                System.out.println("please continue sweeping the mines:) ");

                //here we have check if we have successfully solved the game:
                int countVisible = 0;
                for (List<Coordinate> temp : newMatrix) {
                    for (Coordinate coordinate : temp) {
                        if (coordinate.isVisible() && !coordinate.isBomb()) {
                            countVisible++;
                        }
                    }
                }
                //if the check pass - we print congrats, we break from the current loop, and we are go in the outer one:
                if (countVisible == 381) {
                    System.out.println("Congrats, you have won!");
                    break;
                }
            } while (!Objects.equals(line, "n"));
            //here if we have won or lose the game, the program asks if we want to continue with new game:
            System.out.println("new game?y/n");
            line = scanner.nextLine();
            if (line.equals("n")) {
                break;
            }
        } while (!line.equals("end"));
    }
}
