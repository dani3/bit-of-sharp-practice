package engine.events;

public abstract class MouseButtonEvent extends Event {

    private static final int MOUSE_BUTTON_1 = 0;
    private static final int MOUSE_BUTTON_2 = 1;
    private static final int MOUSE_BUTTON_3 = 2;
    private static final int MOUSE_BUTTON_4 = 3;
    private static final int MOUSE_BUTTON_5 = 4;
    private static final int MOUSE_BUTTON_6 = 5;
    private static final int MOUSE_BUTTON_7 = 6;
    private static final int MOUSE_BUTTON_8 = 7;
    private static final int MOUSE_BUTTON_LAST = MOUSE_BUTTON_8;
    private static final int MOUSE_BUTTON_LEFT = MOUSE_BUTTON_1;
    private static final int MOUSE_BUTTON_RIGHT = MOUSE_BUTTON_2;
    private static final int MOUSE_BUTTON_MIDDLE = MOUSE_BUTTON_3;

    private MouseButton mButton;

    protected MouseButtonEvent(int button) {
        switch (button) {
            case MOUSE_BUTTON_LEFT:
                mButton = MouseButton.Left;
                break;
            case MOUSE_BUTTON_RIGHT:
                mButton = MouseButton.Right;
                break;
            case MOUSE_BUTTON_MIDDLE:
                mButton = MouseButton.Middle;
                break;
            default:
                mButton = MouseButton.Other;
                break;
        }
    }

    public MouseButton getButton() {
        return mButton;
    }
}
