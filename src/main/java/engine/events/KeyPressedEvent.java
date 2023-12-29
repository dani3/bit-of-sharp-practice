package engine.events;

import java.text.MessageFormat;

public class KeyPressedEvent extends KeyEvent {

    private final int mRepeatCount;

    public KeyPressedEvent(int keyCode, int repeatCount) {
        super(keyCode);
        mRepeatCount = repeatCount;
    }

    public int getRepeatCount() {
        return mRepeatCount;
    }

    @Override
    public EventType getType() {
        return EventType.KeyPressed;
    }

    @Override
    public EventCategory getCategory() {
        return EventCategory.Keyboard;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "KeyPressedEvent:Keyboard keycode={0}, repeatCount={1}", super.getKeyCode(), getRepeatCount());
    }
}
