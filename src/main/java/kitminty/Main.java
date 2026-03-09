package kitminty;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL15.*;

public class Main {

     static void main(String[] args) {
        glfwInit();

        long window = createWindow();

        FloatBuffer buffer = memAllocFloat(3 * 2);


        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glEnableClientState(GL_VERTEX_ARRAY);
        glVertexPointer(2, GL_FLOAT, 0, 0L);

        double Test = 0.5;

        while (!glfwWindowShouldClose(window)) {
            Test -= 0.1;

            buffer.put((float)-Test).put(-0.5f);
            buffer.put(+0.5f).put(-0.5f);
            buffer.put(+0.0f).put(+0.5f);

            buffer.flip();

            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            memFree(buffer);

            glfwPollEvents();
            glDrawArrays(GL_TRIANGLES, 0, 3);
            glfwSwapBuffers(window);
        }
        glfwTerminate();
        System.out.println("Fin.");
    }

    public static void maketriangle(FloatBuffer buffer, double x1, double y1, double x2, double y2, double x3, double y3) {

    }

    private static long createWindow() {
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        long window = glfwCreateWindow(800, 600, "Intro2", NULL, NULL);

        glfwMakeContextCurrent(window);

        createCapabilities();

        return window;
    }
}