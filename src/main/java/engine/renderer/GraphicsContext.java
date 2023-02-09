package engine.renderer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL.*;

import java.text.MessageFormat;

import engine.core.Logger;

public class GraphicsContext {

    private static final Logger mLogger = Logger.create(GraphicsContext.class.getName());

    private final long mWindowHandle;

    public GraphicsContext(long windowHandle) {
        mWindowHandle = windowHandle;
    }

    public void init() {
        glfwMakeContextCurrent(mWindowHandle);
        createCapabilities();

        mLogger.info("OpenGL Info:");
        mLogger.info(MessageFormat.format("  Vendor: {0}", glGetString(GL_VENDOR)));
        mLogger.info(MessageFormat.format("  Renderer: {0}", glGetString(GL_RENDERER)));
        mLogger.info(MessageFormat.format("  Version: {0}", glGetString(GL_VERSION)));
    }

    public void swapBuffers() {
        glfwSwapBuffers(mWindowHandle);
    }
}
