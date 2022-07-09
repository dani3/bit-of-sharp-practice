package engine.core;

import engine.events.IEventListener;

public abstract class Window {

    /**
     * Return the heigth of the window.
     *
     * @return the heigth of the window.
     */
    public abstract int getHeight();

    /**
     * Return the width of the window.
     *
     * @return the width of the window.
     */
    public abstract int getWidth();

    /**
     * Enable or disable the VSync.
     *
     * @param enabled true to enable VSync. False to disable it.
     */
    public abstract void setVSync(boolean enabled);

    /**
     * Return if VSync is enabled.
     *
     * @return true if enabled. False otherwise
     */
    public abstract boolean isVSync();

    /**
     * Add a new event listener.
     *
     * @param listener reference to an event listener.
     */
    public abstract void addOnEventListener(IEventListener listener);

    /**
     * Method called at every frame.
     */
    public abstract void onUpdate();
}
