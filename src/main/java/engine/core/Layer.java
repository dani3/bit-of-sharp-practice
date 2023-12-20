package engine.core;

import engine.events.Event;
import engine.events.IEventListener;

public abstract class Layer implements IEventListener {

    private String mDebugName;

    public Layer() {
        mDebugName = "Layer";
    }

    public Layer(String name) {
        mDebugName = name;
    }

    public abstract void onAttach();

    public abstract void onDetach();

    public abstract void onUpdate(Timestep ts);

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
