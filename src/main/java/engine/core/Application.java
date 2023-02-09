package engine.core;

import engine.events.Event;
import engine.events.IEventListener;
import engine.events.WindowClosedEvent;
import engine.events.WindowResizedEvent;
import engine.renderer.Renderer;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Application implements IEventListener {

    private static final Logger mLogger = Logger.create(Application.class.getName());

    private static Application sInstance;

    private final Window mWindow;
    private final LayerStack mLayers = new LayerStack();

    private final Renderer mRenderer;
    private boolean mRunning = true;
    private boolean mMinimized = false;
    private float mLastFrameTime = 0.0f;

    public Application(Window window) {
        sInstance = this;
        mWindow = window;

        mRenderer = new Renderer();

        mWindow.addOnEventListener(this);
    }

    public static Application get() {
        if (sInstance == null) {
            mLogger.error("Application not initialized");
            System.exit(-1);
        }

        return sInstance;
    }

    public void run() {
        while (mRunning) {
            var time = (float) glfwGetTime();
            var ts = new Timestep(time - mLastFrameTime);
            mLastFrameTime = time;

            if (!mMinimized) {
                for (var layer : mLayers) {
                    layer.onUpdate(ts);
                }
            }

            mWindow.onUpdate();
        }
    }

    public void pushLayer(Layer layer) {
        mLayers.pushLayer(layer);
        layer.onAttach();
    }

    public void pushOverlay(Layer overlay) {
        mLayers.pushOverlay(overlay);
        overlay.onAttach();
    }

    public Window getWindow() {
        return mWindow;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof WindowClosedEvent e) {
            onWindowClosed(e);
        } else if (event instanceof WindowResizedEvent e) {
            onWindowResized(e);
        }

        for (Layer layer : mLayers) {
            layer.onEvent(event);
            if (event.mHandled) {
                break;
            }
        }
    }

    private boolean onWindowClosed(WindowClosedEvent _unused) {
        mRunning = false;
        return true;
    }

    private boolean onWindowResized(WindowResizedEvent event) {
        if (event.getWidth() == 0 || event.getHeight() == 0) {
            mMinimized = true;
            return false;
        }

        mMinimized = false;
        mRenderer.onEvent(event);

        return false;
    }
}
