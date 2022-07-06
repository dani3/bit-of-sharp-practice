package engine.events;

public abstract class KeyEvent extends Event {

    private int mKeyCode;

    protected KeyEvent(int keyCode) {
        mKeyCode = keyCode;
    }

    public int getKeyCode() {
        return mKeyCode;
    }
}
