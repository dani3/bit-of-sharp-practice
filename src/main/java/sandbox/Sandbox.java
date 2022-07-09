package sandbox;

import engine.core.Application;
import engine.core.WindowProps;
import engine.windows.WindowsWindow;

public class Sandbox extends Application {

    public static Sandbox create() {
        return new Sandbox();
    }

    public Sandbox() {
        super(new WindowsWindow(
                new WindowProps("Sandbox", 800, 600)));
        pushLayer(new Sandbox3D());
    }
}
