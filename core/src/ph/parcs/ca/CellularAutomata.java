package ph.parcs.ca;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class CellularAutomata {

    private Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.VIOLET, Color.BLUE, Color.PURPLE, Color.VIOLET};
    private int[] ruleSet;

    private int row;
    private int col;

    private static final int BOARD_SIZE = 8;
    private int sizeIncreamentor = 1;

    private boolean randomColor;
    private Cell[][] board;

    public CellularAutomata() {
        row = Gdx.graphics.getWidth() / BOARD_SIZE;
        col = (Gdx.graphics.getHeight()) / BOARD_SIZE;
        board = new Cell[col][row];

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                Cell cell = new Cell(0, j * BOARD_SIZE, (i + 8) * BOARD_SIZE);
                cell.setColor(colors[j % colors.length]);
                board[i][j] = cell;
            }
        }
        Cell startCell = board[1][row / 2];
        startCell.setState(1);
    }

    public void generate(int rulesetNo) {
        this.ruleSet = getRuleset(rulesetNo);
        for (int i = 1; i < col - 1; i++) {
            for (int j = 1; j < row - 1; j++) {
                Cell left = board[i][j - 1];
                Cell middle = board[i][j];
                Cell right = board[i][j + 1];

                int state = computeRule(left, middle, right);
                board[i + 1][j].setState(state);
            }
        }
    }

    private int computeRule(Cell left, Cell middle, Cell right) {
        if (left.isAlive() && middle.isAlive() && right.isAlive()) return ruleSet[0];
        if (left.isAlive() && middle.isAlive() && !right.isAlive()) return ruleSet[1];
        if (left.isAlive() && !middle.isAlive() && right.isAlive()) return ruleSet[2];
        if (left.isAlive() && !middle.isAlive() && !right.isAlive()) return ruleSet[3];
        if (!left.isAlive() && middle.isAlive() && right.isAlive()) return ruleSet[4];
        if (!left.isAlive() && middle.isAlive() && !right.isAlive()) return ruleSet[5];
        if (!left.isAlive() && !middle.isAlive() && right.isAlive()) return ruleSet[6];
        if (!left.isAlive() && !middle.isAlive() && !right.isAlive()) return ruleSet[7];
        return 1;
    }

    public void draw(ShapeRenderer renderer) {
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                Cell cell = board[i][j];
                if (cell.isAlive()) {
                    if (randomColor) renderer.setColor(generateColor());
                    else renderer.setColor(cell.getColor());
                } else renderer.setColor(Color.BLACK);

                renderer.rect(cell.getX(), cell.getY(), BOARD_SIZE + sizeIncreamentor, BOARD_SIZE + sizeIncreamentor);
            }
        }
    }

    public int[] getRuleset(int ruleNumber) {
        String binary = String.format("%8s", Integer.toBinaryString(ruleNumber)).replace(' ', '0');
        int[] ruleset = new int[binary.length()];
        for (int i = 0; i < binary.length(); i++) {
            ruleset[i] = Character.getNumericValue(binary.charAt(i));
        }
        return ruleset;
    }

    public Color generateColor() {
        return colors[MathUtils.random(colors.length - 1)];
    }

    public void setRandomColor(boolean randomColor) {
        this.randomColor = randomColor;
    }

    public void increaseSize() {
        ++sizeIncreamentor;
    }

    public void decreaseSize() {
        --sizeIncreamentor;
    }

    public int getPixelSize() {
        return sizeIncreamentor + BOARD_SIZE;
    }
}
