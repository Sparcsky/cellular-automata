package ph.parcs.ca;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private BitmapFont font;
    private boolean randomColor;
    private CellularAutomata ca;
    private int ruleCounter;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        ca = new CellularAutomata();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        camera = new OrthographicCamera();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) ++ruleCounter;
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) --ruleCounter;
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) ca.increaseSize();
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) ca.decreaseSize();
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            randomColor = !randomColor;
            ca.setRandomColor(randomColor);
        }

        String binary = String.format("%8s", Integer.toBinaryString(ruleCounter)).replace(' ', '0');

        batch.begin();
        font.draw(batch, "Rule set decimal: " + ruleCounter, 16, Gdx.graphics.getHeight() - 5);
        font.draw(batch, "Rule set binary:  " + binary, 16, Gdx.graphics.getHeight() - (20));
        font.draw(batch, "Cell Size:  " + ca.getPixelSize(), 16, Gdx.graphics.getHeight() - (35));
        font.draw(batch, "Random color: " + randomColor, 16, Gdx.graphics.getHeight() - (50));
        font.draw(batch, "Programmed by Ian Parcon ", Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - (16));
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        ca.generate(ruleCounter);
        ca.draw(shapeRenderer);
        shapeRenderer.end();

    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(true,width,height);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
