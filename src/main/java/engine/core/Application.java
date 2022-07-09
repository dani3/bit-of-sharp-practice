package engine.core;

import engine.events.Event;
import engine.events.IEventListener;
import engine.events.WindowClosedEvent;
import engine.events.WindowResizedEvent;

public class Application implements IEventListener {

    private Window mWindow;
    private LayerStack mLayers;

    public Application(Window window) {
        mWindow = window;
        mLayers = new LayerStack();
    }

    public void run() {
        // TODO Auto-generated method stub

    }

    public void pushLayer(Layer layer) {
        // TODO Auto-generated method stub

    }

    public void pushOverlay(Layer overlay) {
        // TODO Auto-generated method stub

    }

    protected void onWindowClosed(WindowClosedEvent event) {
        // TODO Auto-generated method stub

    }

    protected void onWindowResized(WindowResizedEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEvent(Event event) {
        // TODO Auto-generated method stub

    }
}
