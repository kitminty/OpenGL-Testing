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

        String title = "GVF-V"; // The title of the window, WARNING, if title is
        // null, the code will segfault at glfwCreateWindow()
        boolean resizable = true; // Whether or not the window is resizable

        int m_width = 512; // width of the window
        int m_height = 512; // height of the window

        glfwDefaultWindowHints(); // Loads GLFW's default window settings
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // Sets window to be visible
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE); // Sets whether the window is resizable

        long id = glfwCreateWindow(m_width, m_height, title, NULL, NULL); // Does the actual window creation
        if ( id == NULL ) throw new RuntimeException("Failed to create window");

        glfwMakeContextCurrent(id); // glfwSwapInterval needs a context on the calling thread, otherwise will cause NO_CURRENT_CONTEXT error
        GL.createCapabilities(); // Will let lwjgl know we want to use this context as the context to draw with

        glfwSwapInterval(1); // How many draws to swap the buffer
        glfwShowWindow(id); // Shows the window

        double Time = 0.0;
        while(!glfwWindowShouldClose(id)) {
            Time += 0.01;
            renderPolygon(Time);

            glfwSwapBuffers(id);
            glfwPollEvents();
        }
    }

    static void renderPolygon(double Time) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        /*
        glBegin(GL_LINE_STRIP);
        glColor3f(1.0f, 0.3f, 0.3f);
        glVertex2f(0.0f, 0.0f);
        glVertex2f((float)lemx(Time), (float)lemy(Time));
        glEnd();
         */

        glLineWidth(10.0f); //for GL_LINE_STRIP
        glPointSize(10.0f); //for GL_POINTS

        glBegin(GL_LINE_STRIP);
        glColor3f(1.0f, 0.3f, 0.3f);
        glVertex2f(0.0f, 0.0f);
        glVertex2f((float)lemx(Time), (float)lemy(Time));
        glEnd();

        glBegin(GL_POINTS);
        glColor3f(0.3f, 1.0f, 1.0f);
        glVertex2f(0.0f, 0.0f);
        glVertex2f((float)lemx(Time), (float)lemy(Time));
        glEnd();
    }

    public static double lemx(double t) {
        return ((2*Math.cos(t))/(3-Math.cos(2*t)));
    }

    public static double lemy(double t) {
        return ((Math.sin(2*t))/(3-Math.cos(2*t)));
    }
}