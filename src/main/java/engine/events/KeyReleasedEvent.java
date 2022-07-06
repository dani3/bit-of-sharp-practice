package engine.events;

import java.text.MessageFormat;

public class KeyReleasedEvent extends KeyEvent {

    public KeyReleasedEvent(int keyCode) {
        super(keyCode);
    }

    @Override
    public EventType getType() {
        return EventType.KeyReleased;
    }

    @Override
    public EventCategory getCategory() {
        return EventCategory.Keyboard;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "KeyReleasedEvent:Keyboard keycode={0}", super.getKeyCode());
    }
}
