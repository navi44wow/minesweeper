package org.example;

import java.util.Objects;

public class Coordinate {

    private int x;
    private int y;
    private int id;
    private boolean bomb;
    private int countBomb;
    private boolean marked;
    private boolean visible;
    private String type;

    public Coordinate() {
    }

    public Coordinate(int x, int y, int id, boolean bomb, int countBomb, boolean marked, boolean visible, String type) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.bomb = bomb;
        this.countBomb = countBomb;
        this.marked = marked;
        this.visible = visible;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBomb() {
        return bomb;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public int getCountBomb() {
        return countBomb;
    }

    public void setCountBomb(int countBomb) {
        this.countBomb = countBomb;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y && id == that.id && bomb == that.bomb && countBomb == that.countBomb && marked == that.marked && visible == that.visible && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, id, bomb, countBomb, marked, visible, type);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", bomb=" + bomb +
                ", countBomb=" + countBomb +
                ", marked=" + marked +
                ", visible=" + visible +
                ", type='" + type + '\'' +
                '}';
    }
}


