package org.example;


import java.io.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String line = null;

        do {

            // create Timestamp to mark the start of solving the game:
            Timestamp currentTimestamp = getTimestamp();

            //creating the matrix:
            List<List<Coordinate>> newMatrix = CreatingInitialMatrix.creatingInitialMatrix();

            //saving the solved matrix in a file in our local system, in order to compare, if needed;
            // you should  replace "User" with your username:

            generateFileToCrossCheck(newMatrix);

            //initial print of the matrix with ID values:
            initialPrint(newMatrix);

            doWhileContainingAllOtherMethods(scanner, line, currentTimestamp, newMatrix);
            //here if we have won or lose the game, the program asks if we want to continue with new game:
            System.out.println("new game?y/n");
            line = scanner.nextLine();
            if (line.equals("n")) {
                break;
            }
        } while (!line.equals("end"));
    }

    private static void doWhileContainingAllOtherMethods(Scanner scanner, String line, Timestamp currentTimestamp, List<List<Coordinate>> newMatrix) {
        do {
            //here we start filling the matrix, or sweeping the mines:
            String n = scanner.nextLine();
            int b = 0;
            int N = 0;
            String BMarked;

            //this try catch block defend us from wrong input:
            try {
                //here we use regular expression in order to validate that the input will be
                //either a number between 0-479 or the same ,followed by letter b:
                if (!n.matches("[0-9b]*")) {
                    throw new Exception();
                }

                char bMarked = n.charAt(n.length() - 1);
                BMarked = String.valueOf(bMarked);

                if (BMarked.equals("b")) {
                    String bombID = n.substring(0, n.length() - 1);
                    b = 1;
                    N = Integer.parseInt(bombID);
                } else {
                    N = Integer.parseInt(n);
                }
            } catch (Exception e) {
                System.out.println("please make valid input between 0-479 or 0b-479b(if you want to mark/unmark a bomb)");
            }

            //we use these variables to help us break from nested loops:
            int x = 0;
            int y = 0;

            //here it checks if it is a bomb, if yes - game over;
            //if it is not a bomb - it sets the coordinate values of visible and marked as true:

            y = checkInputAndDoAppropriateAction(newMatrix, b, N, x, y);

            //here y help us break from this outer loop too:
            if (y == 1) {
                break;
            }

            //Lets count the bombs after this input:
            int countMarkedAsBombs = getCountMarkedAsBombs(newMatrix);


            //here, if we have marked more than 99 bombs, we want to print 0, which are te number of bombs remaining to be marked.
            // we print a negative number in case that more than the needed 99 bombs are marked - this is a hint that something that has been
            //marked as bomb is not a bomb actually;
            int remainingBombs = 99 - countMarkedAsBombs;


            //after performing some of the above operations(depending on our input), we print
            //the temporary matrix, and ask the user to make next input:
            printAfterInput(newMatrix, remainingBombs);

            //here we have check if we have successfully solved the game:
            int countVisible = getCountVisible(newMatrix);

            //if the check pass - we print congrats, we break from the current loop, and we are go in the outer one:
            if (ifCheckPassedCongratsMethod(currentTimestamp, countVisible)) break;

        } while (!Objects.equals(line, "n"));
    }

    private static java.sql.Timestamp getTimestamp() {
        Calendar calendar = Calendar.getInstance();

        java.util.Date now = calendar.getTime();

        return new Timestamp(now.getTime());
    }

    private static void generateFileToCrossCheck(List<List<Coordinate>> newMatrix) {
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
    }

    private static void initialPrint(List<List<Coordinate>> newMatrix) {
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
                "the example - 470b to mark or unmark a field as bomb;");
    }

    private static int checkInputAndDoAppropriateAction(List<List<Coordinate>> newMatrix, int b, int N, int x, int y) {
        for (List<Coordinate> temp : newMatrix) {
            for (Coordinate coordinate : temp) {
                if (coordinate.getId() == N) {
                    if (coordinate.isBomb() && b == 0 && !coordinate.isVisible()) {
                        x = 1;
                        System.out.println("Game over");
                        break;
                    } else if (b == 1 && !coordinate.isVisible()) {
                        //if marked as bomb, but is not, we do:
                        //if it is bomb, and you mark it, then you do:
                        coordinate.setMarked(true);
                        coordinate.setVisible(true);
                    } else if (b == 1 && coordinate.isVisible() && !coordinate.isClicked()) {
                       /*
                       here you do the unmarking
                        */
                        coordinate.setMarked(false);
                        coordinate.setVisible(false);
                    } else if (coordinate.isBomb() && b == 1 && coordinate.isMarked()) {
                        //you do this, if you unmark;
                        coordinate.setMarked(false);
                        coordinate.setVisible(false);
                    } else if (b == 0 && coordinate.getType().equals("E") && !coordinate.isMarked()) {
                        //here is the logic for clicked field, that is empty:
                        RevealWholeFieldOfEmpties.revealWholeFieldOfEmpties(coordinate, newMatrix);
                    } else if (b == 0 && !coordinate.isMarked()) {
                        //here if it is not a bomb it is unneeded to be marked because it is
                        //field that is empty or containing a number of bombs:
                        coordinate.setVisible(true);
                        coordinate.setClicked(true);
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
        return y;
    }

    private static int getCountMarkedAsBombs(List<List<Coordinate>> newMatrix) {
        int countMarkedAsBombs = 0;
        for (List<Coordinate> temp : newMatrix) {
            for (Coordinate coordinate : temp) {
                if (coordinate.isMarked()) {
                    countMarkedAsBombs++;
                }
            }
        }
        return countMarkedAsBombs;
    }

    private static void printAfterInput(List<List<Coordinate>> newMatrix, int remainingBombs) {
        System.out.println("the current matrix: ");
        System.out.println("the remaining mines: " + remainingBombs);
        for (List<Coordinate> temp : newMatrix) {
            for (Coordinate coordinate : temp) {
                String id = String.valueOf(coordinate.getId());
                int xy = 3 - id.length();
                if (coordinate.isVisible() && coordinate.isMarked()) {
                    System.out.print("|" + " " + "B" + " ");
                } else if (coordinate.isVisible() && !coordinate.isMarked()) {
                    xy = 3;
                    if (coordinate.getType().equals("E")) {
                        System.out.print("|" + " ".repeat(xy));
                    } else {
                        System.out.print("|" + " " + coordinate.getType() + " ");
                    }
                } else {
                    if (coordinate.getId() <= 8) {
                        System.out.print("|" + " ".repeat(Math.max(0, xy - 1)) + "_" + coordinate.getId());
                    } else {
                        System.out.print("|" + " ".repeat(Math.max(0, xy)) + coordinate.getId());
                    }
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("please continue sweeping the mines:) ");
    }

    private static int getCountVisible(List<List<Coordinate>> newMatrix) {
        int countVisible = 0;
        for (List<Coordinate> temp : newMatrix) {
            for (Coordinate coordinate : temp) {
                if (coordinate.isVisible() && !coordinate.isBomb()) {
                    countVisible++;
                }
            }
        }
        return countVisible;
    }

    private static boolean ifCheckPassedCongratsMethod(Timestamp currentTimestamp, int countVisible) {
        if (countVisible == 381) {
            System.out.println("Congrats, you have won!");
            Timestamp currentTimestamp1 = getTimestamp();

            long diff = currentTimestamp1.getTime() - currentTimestamp.getTime();

            float difSec = (float) diff / 1000;
            System.out.println("the time that you needed to solve it in sec: " + difSec);

            return true;
        }
        return false;
    }

}
