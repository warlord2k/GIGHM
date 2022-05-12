package io.github.kale_ko.gighm.rendering;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import io.github.kale_ko.gighm.input.InputManager;
import io.github.kale_ko.gighm.input.KeyAction;
import io.github.kale_ko.gighm.input.KeyCode;
import io.github.kale_ko.gighm.input.KeyMod;
import io.github.kale_ko.gighm.input.MouseAction;
import io.github.kale_ko.gighm.input.MouseButton;

/**
 * A window to render to
 * 
 * @version 1.5.0
 * @since 1.0.0
 */
public class Window {
    /**
     * The renderer being used by the window
     * 
     * @since 1.0.0
     */
    private Renderer renderer;

    /**
     * The input manager used to capture user input
     * 
     * @since 1.2.0
     */
    private InputManager inputManager;

    /**
     * The title of the window
     * 
     * @since 1.0.0
     */
    private String title;

    /**
     * The width of the window (Note this may not be accurate if the user can resize the window, use {@link #getWidth})
     * 
     * @since 1.0.0
     */
    private int width;

    /**
     * The height of the window (Note this may not be accurate if the user can resize the window, use {@link #getHeight})
     * 
     * @since 1.0.0
     */
    private int height;

    /**
     * Weather the window is maximized (Note this may not be accurate if the user can resize the window, use {@link #getMaximized})
     * 
     * @since 1.0.0
     */
    private boolean maximized;

    /**
     * Weather the window is resizable
     * 
     * @since 1.0.0
     */
    private boolean resizable;

    /**
     * Weather the window is initialized
     * 
     * @since 1.0.0
     */
    private boolean initialized = false;

    /**
     * The id of the window
     * 
     * @since 1.0.0
     */
    private long windowId;

    /**
     * Create a window to render to
     * 
     * @param title The title of the new window
     * 
     * @since 1.0.0
     */
    public Window(String title) {
        this(null, title);
    }

    /**
     * Create a window to render to
     * 
     * @param renderer The renderer to use when rendering the window
     * @param title The title of the new window
     * 
     * @since 1.0.0
     */
    public Window(Renderer renderer, String title) {
        this(renderer, null, title);
    }

    /**
     * Create a window to render to
     * 
     * @param renderer The renderer to use when rendering the window
     * @param inputManager The input manager used to capture user input
     * @param title The title of the new window
     * 
     * @since 1.2.0
     */
    public Window(Renderer renderer, InputManager inputManager, String title) {
        this(renderer, inputManager, title, 640, 360);
    }

    /**
     * Create a window to render to
     * 
     * @param title The title of the new window
     * @param width The width of the new window
     * @param height The height of the new window
     * 
     * @since 1.0.0
     */
    public Window(String title, int width, int height) {
        this(null, title, width, height);
    }

    /**
     * Create a window to render to
     * 
     * @param renderer The renderer to use when rendering the window
     * @param title The title of the new window
     * @param width The width of the new window
     * @param height The height of the new window
     * 
     * @since 1.0.0
     */
    public Window(Renderer renderer, String title, int width, int height) {
        this(renderer, null, title, width, height);
    }

    /**
     * Create a window to render to
     * 
     * @param renderer The renderer to use when rendering the window
     * @param inputManager The input manager used to capture user input
     * @param title The title of the new window
     * @param width The width of the new window
     * @param height The height of the new window
     * 
     * @since 1.2.0
     */
    public Window(Renderer renderer, InputManager inputManager, String title, int width, int height) {
        this(renderer, inputManager, title, width, height, false, false);
    }

    /**
     * Create a window to render to
     * 
     * @param title The title of the new window
     * @param width The width of the new window
     * @param height The height of the new window
     * @param maximized Weather the new window should be maxamized
     * @param resizable Weather the new window should be resizable
     * 
     * @since 1.0.0
     */
    public Window(String title, int width, int height, boolean maximized, boolean resizable) {
        this(null, null, title, width, height, maximized, resizable);
    }

    /**
     * Create a window to render to
     * 
     * @param renderer The renderer to use when rendering the window
     * @param title The title of the new window
     * @param width The width of the new window
     * @param height The height of the new window
     * @param maximized Weather the new window should be maxamized
     * @param resizable Weather the new window should be resizable
     * 
     * @since 1.2.0
     */
    public Window(Renderer renderer, String title, int width, int height, boolean maximized, boolean resizable) {
        this(renderer, null, title, width, height, maximized, resizable);
    }

    /**
     * Create a window to render to
     * 
     * @param renderer The renderer to use when rendering the window
     * @param inputManager The input manager used to capture user input
     * @param title The title of the new window
     * @param width The width of the new window
     * @param height The height of the new window
     * @param maximized Weather the new window should be maxamized
     * @param resizable Weather the new window should be resizable
     * 
     * @since 1.0.0
     */
    public Window(Renderer renderer, InputManager inputManager, String title, int width, int height, boolean maximized, boolean resizable) {
        this.renderer = renderer;
        this.inputManager = inputManager;

        this.title = title;
        this.width = width;
        this.height = height;

        this.maximized = maximized;
        this.resizable = resizable;

        Thread thread = new Thread(new Runnable() {
            public void run() {
                init();
            }
        }, "GIGHM-Main");
        thread.start();
    }

    /**
     * Initialize the window
     * 
     * @throws RuntimeException If the window is already initialized or glfw fails to start/create the window
     * 
     * @since 1.0.0
     */
    public void init() {
        if (this.initialized) {
            throw new RuntimeException("The window is already initialized");
        }

        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));

        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }

        this.initialized = true;

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_FOCUSED, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, maximized ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);

        windowId = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowId == NULL) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        glfwSetKeyCallback(windowId, (window, key, scancode, action, mods) -> {
            if (inputManager != null) {
                inputManager.onKeyboardKey(KeyCode.valueOfGLFWKey(key, KeyMod.isPressed(KeyMod.SHIFT, mods)), KeyAction.valueOfGLFWEvent(action), KeyMod.getPressed(mods));
            }
        });

        glfwSetMouseButtonCallback(windowId, (window, button, action, mods) -> {
            if (inputManager != null) {
                inputManager.onMouseButton(MouseButton.valueOfGLFWButton(button), MouseAction.valueOfGLFWEvent(action));
            }
        });

        glfwSetCursorPosCallback(windowId, (window, x, y) -> {
            if (inputManager != null) {
                inputManager.onMouseMove((int) x, (int) y);
            }
        });

        glfwSetScrollCallback(windowId, (window, x, y) -> {
            if (inputManager != null) {
                inputManager.onMouseScroll((int) x, (int) y);
            }
        });

        glfwSetWindowSizeCallback(windowId, (window, newwidth, newheight) -> {
            if (renderer != null) {
                this.width = newwidth;
                this.height = newheight;

                renderer.getCamera().setWidth(newwidth);
                renderer.getCamera().setHeight(newheight);
                renderer.getCamera().setAspect(newwidth / newheight);

                renderer.getCamera().recalculateProjection();
            }
        });

        glfwSetWindowMaximizeCallback(windowId, (window, maxamized) -> {
            if (renderer != null) {
                renderer.getCamera().setWidth(getWidth());
                renderer.getCamera().setHeight(getHeight());
                renderer.getCamera().setAspect(getWidth() / getHeight());

                renderer.getCamera().recalculateProjection();
            }
        });

        MemoryStack stack = stackPush();
        IntBuffer cWidth = stack.mallocInt(1);
        IntBuffer cHeight = stack.mallocInt(1);
        glfwGetWindowSize(windowId, cWidth, cHeight);

        GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(windowId, (vid.width() - cWidth.get(0)) / 2, (vid.height() - cHeight.get(0)) / 2);

        glfwMakeContextCurrent(windowId);
        glfwSwapInterval(1);

        glfwShowWindow(windowId);

        if (renderer != null) {
            renderer.init();
        }

        while (!glfwWindowShouldClose(windowId)) {
            if (renderer != null) {
                renderer.render();
            }

            glfwSwapBuffers(windowId);

            if (inputManager != null && inputManager.getAutoResetDelta()) {
                inputManager.resetDelta();
            }

            glfwPollEvents();
        }

        glfwFreeCallbacks(windowId);
        glfwDestroyWindow(windowId);

        glfwTerminate();
    }

    /**
     * Get the renderer being used by the window
     * 
     * @return The renderer being used by the window
     * 
     * @since 1.5.0
     */
    public Renderer getRenderer() {
        return this.renderer;
    }

    /**
     * Set the renderer being used by the window
     * 
     * @param renderer The renderer being used by the window
     * 
     * @since 1.5.0
     */
    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Get the input manager used to capture user input
     * 
     * @return The input manager used to capture user input
     * 
     * @since 1.5.0
     */
    public InputManager getInputManager() {
        return this.inputManager;
    }

    /**
     * Set the input manager used to capture user input
     * 
     * @param inputManager The input manager used to capture user input
     * 
     * @since 1.5.0
     */
    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    /**
     * Get the title of the window
     * 
     * @return The title of the window
     * 
     * @since 1.0.0
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Change the title of the window
     * 
     * @param title The new title of the window
     * 
     * @since 1.0.0
     */
    public void setTitle(String title) {
        this.title = title;

        glfwSetWindowTitle(windowId, title);
    }

    /**
     * Get the current width of the window
     * 
     * @return The current width of the window
     * 
     * @since 1.0.0
     */
    public int getWidth() {
        MemoryStack stack = stackPush();
        IntBuffer cWidth = stack.mallocInt(1);
        glfwGetWindowSize(windowId, cWidth, null);

        return cWidth.get(0);
    }

    /**
     * Get the current height of the window
     * 
     * @return The current height of the window
     * 
     * @since 1.0.0
     */
    public int getHeight() {
        MemoryStack stack = stackPush();
        IntBuffer cHeight = stack.mallocInt(1);
        glfwGetWindowSize(windowId, null, cHeight);

        return cHeight.get(0);
    }

    /**
     * Change the current size of the window
     * 
     * @param width The new width of the window
     * @param height The new height of the window
     * 
     * @since 1.0.0
     */
    public void setWindowSize(int width, int height) {
        this.width = width;
        this.height = height;

        glfwSetWindowSize(windowId, width, height);
    }

    /**
     * Get weather the window is maximized
     * 
     * @return Weather the window is maximized
     * 
     * @since 1.0.0
     */
    public boolean getMaximized() {
        return glfwGetWindowAttrib(windowId, GLFW_MAXIMIZED) == GLFW_TRUE;
    }

    /**
     * Get weather the window is resizable
     * 
     * @return Weather the window is resizable
     * 
     * @since 1.0.0
     */
    public boolean getResizable() {
        return glfwGetWindowAttrib(windowId, GLFW_RESIZABLE) == GLFW_TRUE;
    }

    /**
     * Get weather the window is initialized
     * 
     * @return Weather the window is initialized
     * 
     * @since 1.0.0
     */
    public boolean getInitialized() {
        return this.initialized;
    }

    /**
     * Get the id of the window
     * 
     * @return The id of the window
     * 
     * @since 1.0.0
     */
    public long getWindowId() {
        return this.windowId;
    }
}