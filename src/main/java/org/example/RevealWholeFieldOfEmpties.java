package org.example;

import java.util.ArrayList;
import java.util.List;

public class RevealWholeFieldOfEmpties {

    public static void revealWholeFieldOfEmpties(Coordinate coordinate, List<List<Coordinate>> matrix) {
        coordinate.setVisible(true);
        coordinate.setClicked(true);

        int x = coordinate.getX();
        int y = coordinate.getY();
        List<Coordinate> otherToBeRevealed = new ArrayList<>();

        for (List<Coordinate> temp : matrix) {
            for (Coordinate current : temp) {
                int xC = current.getX();
                int yC = current.getY();

                /*
                here we check in all 8 directions to see if the surrounding fields are also empty;
                if they are empty, we make them visible, and add them to otherToBeReveal list:
                 */

                if (xC == x - 1 && yC == y && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x + 1 && yC == y && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x && yC == y - 1 && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x && yC == y + 1 && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x - 1 && yC == y - 1 && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x + 1 && yC == y + 1 && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x + 1 && yC == y - 1 && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                    otherToBeRevealed.add(current);
                }
                if (xC == x - 1 && yC == y + 1 && current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                    otherToBeRevealed.add(current);
                }
                /*
                   here we check in all 8 directions to see if the surrounding fields are NOT empty;
                if they are NOT empty, we make them visible,but we do not add them to otherToBeReveal list:
                 */
                if (xC == x - 1 && yC == y && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                }
                if (xC == x + 1 && yC == y && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                }
                if (xC == x && yC == y - 1 && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                }
                if (xC == x && yC == y + 1 && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                }
                if (xC == x - 1 && yC == y + 1 && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                }
                if (xC == x + 1 && yC == y - 1 && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                }
                if (xC == x - 1 && yC == y - 1 && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                }
                if (xC == x + 1 && yC == y + 1 && !current.getType().equals("B") && !current.getType().equals("E")
                        && !current.isVisible()) {
                    current.setVisible(true);
                    current.setClicked(true);
                }
            }
        }
        //here we use recursion, to do the above logic, to all the elements of the orderToBeRevealedList:
        for (Coordinate temp : otherToBeRevealed) {
            revealWholeFieldOfEmpties(temp, matrix);
        }
    }
}
