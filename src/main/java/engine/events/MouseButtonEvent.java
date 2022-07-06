package engine.events;

public abstract class MouseButtonEvent extends Event {

    private MouseButton mButton;

    protected MouseButtonEvent(MouseButton button) {
        mButton = button;
    }

    public MouseButton getButton() {
        return mButton;
    }
}
