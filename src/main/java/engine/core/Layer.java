package engine.core;

import engine.events.Event;
import engine.events.IEventListener;

/**
 * Abstract class that represents a Layer in the game.
 */
public abstract class Layer implements IEventListener {

    private final String mDebugName;

    /**
     * Construct a new Layer.
     */
    public Layer() {
        mDebugName = "Layer";
    }

    /**
     * Construct a new layer with a given name.
     * @param name Layer name.
     */
    public Layer(String name) {
        mDebugName = name;
    }

    /**
     * Handler called when the layer is added to the execution.
     */
    public abstract void onAttach();

    /**
     * Handler called when the layer gets removed from the execution.
     */
    public abstract void onDetach();

    /**
     * Handler called on every frame.
     *
     * @param ts Timestep object.
     */
    public abstract void onUpdate(Timestep ts);

    /**
     * Handler called when a new event has been emitted.
     *
     * @param event Event object.
     */
    @Override
    public abstract void onEvent(Event event);

    @Override
    public boolean equals(Object other) {
        if (other instanceof Layer layer) {
            return layer.mDebugName.equals(mDebugName);
        }

        return false;
    }
}
