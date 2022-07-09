package engine.windows;

import java.util.List;

import engine.core.Window;
import engine.core.WindowProps;
import engine.events.IEventListener;

public class WindowsWindow extends Window {

    private List<IEventListener> mListeners;

    public WindowsWindow(WindowProps props) {

    }

    @Override
    public int getHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getWidth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setVSync(boolean enabled) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isVSync() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addOnEventListener(IEventListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void onUpdate() {
        // TODO Auto-generated method stub

    }
}
