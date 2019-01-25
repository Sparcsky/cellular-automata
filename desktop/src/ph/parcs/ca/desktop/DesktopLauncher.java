package ph.parcs.ca.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ph.parcs.ca.Main;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width  = 1024;
        config.height = 576;
        config.title = "Elementary Cellular Automaton";
        new LwjglApplication(new Main(), config);
    }
}
