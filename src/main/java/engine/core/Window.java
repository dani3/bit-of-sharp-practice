package engine.core;

import engine.events.IEventListener;
import engine.events.KeyPressedEvent;
import engine.events.KeyReleasedEvent;
import engine.events.KeyTypedEvent;
import engine.events.MouseButtonPressedEvent;
import engine.events.MouseButtonReleasedEvent;
import engine.events.MouseMovedEvent;
import engine.events.MouseScrolledEvent;
import engine.events.WindowClosedEvent;
import engine.events.WindowResizedEvent;
import engine.renderer.GraphicsContext;

import java.text.MessageFormat;

import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

public class Window implements AutoCloseable {

    private static final Logger mLogger = Logger.create(Window.class.getName());

    private IEventListener mListener;

    private GraphicsContext mContext;

    private long mWindow;

    private static class WindowData {
        private String title;
        private int height;
        private int width;
        private boolean vsync;
    }

    private WindowData mData = new WindowData();

    public Window(WindowProps props) {
        init(props);
    }

    @Override
    public void close() {
        shutdown();
    }

    public int getHeight() {
        return mData.height;
    }

    public int getWidth() {
        return mData.width;
    }

    public void setVSync(boolean enabled) {
        mData.vsync = enabled;
        glfwSwapInterval((enabled) ? 1 : 0);
    }

    public boolean isVSync() {
        return mData.vsync;
    }

    public void addOnEventListener(IEventListener listener) {
        mListener = listener;
    }

    public void onUpdate() {
        glfwPollEvents();
        // TODO context
    }

    public long getNativeWindow() {
        return mWindow;
    }

    private void init(WindowProps props) {
        mData.title = props.getTitle();
        mData.height = props.getHeight();
        mData.width = props.getWidth();

        mLogger.info(MessageFormat.format(
                "Creating window {0} ({1}, {2})", mData.title, mData.width, mData.height));

        var success = glfwInit();
        if (!success) {
            GLFWErrorCallback errorfun = GLFWErrorCallback.createPrint();
            glfwSetErrorCallback(errorfun);
            mLogger.error("Could not initialize GLFW");
            throw new IllegalStateException("Could not initialize GLFW");
        }

        mWindow = glfwCreateWindow(
                mData.width, mData.height, mData.title, 0, 0);

        mContext = new GraphicsContext(mWindow);
        mContext.init();

        setVSync(true);

        // Set GLFW callbacks.

        glfwSetWindowSizeCallback(mWindow, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                mData.width = width;
                mData.height = height;

                WindowResizedEvent event = new WindowResizedEvent(height, width);
                mListener.onEvent(event);
            }
        });

        glfwSetWindowCloseCallback(mWindow, new GLFWWindowCloseCallback() {
            @Override
            public void invoke(long window) {
                WindowClosedEvent event = new WindowClosedEvent();
                mListener.onEvent(event);
            }
        });

        glfwSetCharCallback(mWindow, new GLFWCharCallback() {
            @Override
            public void invoke(long window, int codepoint) {
                KeyTypedEvent event = new KeyTypedEvent(codepoint);
                mListener.onEvent(event);
            }
        });

        glfwSetKeyCallback(mWindow, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                switch (action) {
                    case GLFW_PRESS: {
                        KeyPressedEvent event = new KeyPressedEvent(key, 0);
                        mListener.onEvent(event);
                        break;
                    }
                    case GLFW_RELEASE: {
                        KeyReleasedEvent event = new KeyReleasedEvent(key);
                        mListener.onEvent(event);
                        break;
                    }
                    case GLFW_REPEAT: {
                        KeyPressedEvent event = new KeyPressedEvent(key, 1);
                        mListener.onEvent(event);
                        break;
                    }
                }
            }
        });

        glfwSetMouseButtonCallback(mWindow, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                switch (action) {
                    case GLFW_PRESS: {
                        MouseButtonPressedEvent event = new MouseButtonPressedEvent(button);
                        mListener.onEvent(event);
                        break;
                    }
                    case GLFW_RELEASE: {
                        MouseButtonReleasedEvent event = new MouseButtonReleasedEvent(button);
                        mListener.onEvent(event);
                        break;
                    }
                }
            }
        });

        glfwSetScrollCallback(mWindow, new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                MouseScrolledEvent event = new MouseScrolledEvent((float) xoffset, (float) yoffset);
                mListener.onEvent(event);
            }
        });

        glfwSetCursorPosCallback(mWindow, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                MouseMovedEvent event = new MouseMovedEvent((float) xpos, (float) ypos);
                mListener.onEvent(event);
            }
        });
    }

    private void shutdown() {
        glfwDestroyWindow(mWindow);
    }
}
