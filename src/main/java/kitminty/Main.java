package kitminty;


import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

//Isaac was here
public class Main {

    double Zoom = 0;
    double MouseInstPosX = 0;
    double MouseInstPosY = 0;
    static double MousePosX = 0;
    static double MousePosY = 0;
    static double ScreenPosX = 0;
    static double ScreenPosY = 0;
    double ScreenCurrentPosX = 0;
    double ScreenCurrentPosY = 0;
    boolean WasPressed = false;
    static boolean ScreenShouldMove = true;
    double NumPoints = 2;

    ArrayList<PointInstance> Points = new ArrayList<>();

    void main() {
        glfwInit();

        glfwDefaultWindowHints(); // Loads GLFW's default window settings
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // Sets window to be visible
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // Sets whether the window is resizable

        long id = glfwCreateWindow(512, 512, "GVF-V", NULL, NULL); // Does the actual window creation

        glfwMakeContextCurrent(id); // glfwSwapInterval needs a context on the calling thread, otherwise will cause NO_CURRENT_CONTEXT error
        GL.createCapabilities(); // Will let lwjgl know we want to use this context as the context to draw with

        glfwSwapInterval(1); // How many draws to swap the buffer
        glfwShowWindow(id); // Shows the window

        for (int i=0; i<=NumPoints; i++) {
            Points.add(new PointInstance());
        }

        while(!glfwWindowShouldClose(id)) {
            ScreenDragLogic(id);
            RenderLoop(id);

            glfwSwapBuffers(id);
            glfwPollEvents();
        }
    }

    public void RenderLoop(long WindowID) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //resets the frame every loop

        glfwSetScrollCallback(WindowID, new GLFWScrollCallback() { //finds zoom by finding scroll amount
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                Zoom = Zoom + yoffset/2;
            }
        });
        glBegin(GL_LINES);
        glColor3f(0.3f, 1.0f, 1.0f);
        for(double i=0; i<1; i += 0.01) {
            glVertex2d(LerpX(Points, i, ZoomCurve(Zoom), ScreenPosX), LerpY(Points, i, ZoomCurve(Zoom), ScreenPosY));
            glVertex2d(LerpX(Points, i+0.01, ZoomCurve(Zoom), ScreenPosX), LerpY(Points, i+0.01, ZoomCurve(Zoom), ScreenPosY));
        }
        glEnd();

        Points.get(1).PointLogic(WindowID, ZoomCurve(Zoom), ScreenPosX, ScreenPosY, 1.0, 0.3, 0.3, 10);
        Points.get(2).PointLogic(WindowID, ZoomCurve(Zoom), ScreenPosX, ScreenPosY, 0.3, 1.0, 0.3, 10);
    }

    public void ScreenDragLogic(long WindowID) {
        if (glfwGetMouseButton(WindowID, 0) == GLFW_PRESS && !WasPressed) {
            MouseInstPosX = GetCursorPosX(WindowID);
            MouseInstPosY = GetCursorPosY(WindowID);
        }
        WasPressed = glfwGetMouseButton(WindowID, 0) == GLFW_PRESS;

        MousePosX = GetCursorPosX(WindowID);
        MousePosY = GetCursorPosY(WindowID);

        if (glfwGetMouseButton(WindowID, 0) == GLFW_PRESS && ScreenShouldMove) {
            ScreenPosX = (MousePosX-MouseInstPosX)+ScreenCurrentPosX;
            ScreenPosY = (MousePosY-MouseInstPosY)+ScreenCurrentPosY;
        } else {
            ScreenCurrentPosX = ScreenPosX;
            ScreenCurrentPosY = ScreenPosY;
        }
    }

    public double ZoomCurve(double Input) {
        if(Input <= 0) {
            return 1/Math.abs(Input-1);
        } else {
            return Input+1;
        }
    }

    public double GetCursorPosX(long WindowID) {
         DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
         glfwGetCursorPos(WindowID, posX, null);
         return (posX.get()-WindowSizeX(WindowID)/2f)/(WindowSizeX(WindowID)/2f);
    }
    public double GetCursorPosY(long WindowID) {
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(WindowID, null, posY);
        return -(posY.get()-WindowSizeY(WindowID)/2f)/(WindowSizeY(WindowID)/2f);
    }

    public int WindowSizeX(long WindowID) {
        IntBuffer posX = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(WindowID, posX, null);
        return posX.get();
    }
    public int WindowSizeY(long WindowID) {
        IntBuffer posY = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(WindowID, null, posY);
        return posY.get();
    }

    public double LerpX(ArrayList<PointInstance> pointlist, double time, double zoom, double xoffset) {
        return zoom*(pointlist.get(1).PointPosX+(pointlist.get(2).PointPosX-pointlist.get(1).PointPosX)*time)+xoffset;
    }
    public double LerpY(ArrayList<PointInstance> pointlist, double time, double zoom, double yoffset) {
        return zoom*(pointlist.get(1).PointPosY+(pointlist.get(2).PointPosY-pointlist.get(1).PointPosY)*time)+yoffset;
    }
    /*
    public double lemx(double time, double zoom, double xoffset) {
        return zoom*((2*Math.cos(time))/(3-Math.cos(2*time)))+xoffset;
    }

    public double lemy(double time, double zoom, double yoffset) {
        return zoom*((Math.sin(2*time))/(3-Math.cos(2*time)))+yoffset;
    }
     */
}