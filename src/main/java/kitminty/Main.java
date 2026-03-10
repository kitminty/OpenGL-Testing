package kitminty;


import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.glViewport;


public class Main {
     static void main() {
        glfwInit();

        String title = "MyTitle"; // The title of the window, WARNING, if title is
        // null, the code will segfault at glfwCreateWindow()
        boolean resizable = true; // Whether or not the window is resizable

        int m_width = 1024; // width of the window
        int m_height = 768; // height of the window

        glfwDefaultWindowHints(); // Loads GLFW's default window settings
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // Sets window to be visible
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE); // Sets whether the window is resizable

        long id = glfwCreateWindow(m_width, m_height, title, NULL, NULL); // Does the actual window creation
        if ( id == NULL ) throw new RuntimeException("Failed to create window");

        glfwMakeContextCurrent(id); // glfwSwapInterval needs a context on the calling thread, otherwise will cause NO_CURRENT_CONTEXT error
        GL.createCapabilities(); // Will let lwjgl know we want to use this context as the context to draw with

        glfwSwapInterval(1); // How many draws to swap the buffer
        glfwShowWindow(id); // Shows the window

        while(!glfwWindowShouldClose(id)) {
            renderPolygon();

            glfwSwapBuffers(id);
            glfwPollEvents();
        }
    }

    static void renderPolygon() {
        glBegin(GL_LINE_STRIP);
        glColor3f(1.0f, 0.3f, 0.3f);
        glColor3f(0.8f, 0.8f, 0.8f);
        glVertex2f(-0.5f, -0.5f);
        glEnd();
        glBegin(GL_LINE_STRIP);
        glColor3f(1.0f, 0.3f, 0.3f);
        glVertex2f(-0.5f, +0.5f);
        glVertex2f(+0.0f, +0.5f);
        glEnd();
    }
}