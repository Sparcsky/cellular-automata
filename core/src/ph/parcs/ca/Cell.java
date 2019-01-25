package ph.parcs.ca;

import com.badlogic.gdx.graphics.Color;

public class Cell {

    private Color color;
    private int state;
    private int x;
    private int y;

    public Cell(int state, int x, int y) {
        this.state = state;
        this.x = x;
        this.y = y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return state == 1;
    }
}
