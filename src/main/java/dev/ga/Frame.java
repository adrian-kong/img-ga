package dev.ga;

import dev.ga.algorithm.impl.ImageGeneticAlgorithm;
import dev.ga.data.IData;
import dev.ga.data.Pixel;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.*;
import java.util.concurrent.Executors;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

// LWJGL Template
public class Frame {

    private long window;

    private static int WIDTH;
    private static int HEIGHT;

    private ImageGeneticAlgorithm algorithm;

    private BufferedImage image;

    public static void main(String[] args) {
        new Frame().run();
    }

    public void run() {
        try {
            image = ImageIO.read(new File("peng.png"));
            WIDTH = image.getWidth();
            HEIGHT = image.getHeight();
            algorithm = new ImageGeneticAlgorithm();
            algorithm.input(image);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(WIDTH, HEIGHT, "img-ga", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            processImage();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void processImage() {
        System.out.println(algorithm.getBest().getFitness());
        for (IData<Pixel> datum : algorithm.getBest().getData()) {
            Pixel pixel = datum.getData();
            drawRect(pixel.getX(), pixel.getY());
        }
        algorithm.next();
    }

    private void drawRect(double x, double y) {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(1, 1, 1, 0.3F);
        double size = 3d;
        glBegin(GL_QUADS);
        glVertex2d(x, y);
        glVertex2d(x, y + size);
        glVertex2d(x + size, y + size);
        glVertex2d(x + size, y);
        glEnd();
    }

}
