package Lesson4;

import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Render implements GLEventListener {

    private static GraphicsEnvironment graphicsEnvironment;
    private static boolean isFullScreen = false;
    public static DisplayMode dm, dmOld;
    private static Dimension xgraphic;
    private static Point point = new Point(0, 0);

    private GLU glu = new GLU();

    private float rquad = 0.0f, rtri = 0.0f;

    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // clear the screen and the depth buffer
        gl.glLoadIdentity(); // reset the view

        gl.glTranslatef(-1.5f, 0.0f, -6.0f); // move left 1.5 units and into the screeen 6.0
        gl.glRotatef(rtri, 0.0f, 1.0f, 0.0f);
        gl.glBegin(GL2.GL_TRIANGLES); // drawing using Triangles
        gl.glColor3f(1.0f, 0.0f, 0.0f); // red
        gl.glVertex3f(0.0f, 1.0f, 0.0f); // top
        gl.glColor3f(0.0f, 1.0f, 0.0f); // green
        gl.glVertex3f(-1.0f, -1.0f, 0.0f); // bottom left
        gl.glColor3f(0.0f, 0.0f, 1.0f); // blue
        gl.glVertex3f(1.0f, -1.0f, 0.0f); // bottom right
        gl.glEnd(); // finished drawing the Triangles

        gl.glLoadIdentity();
        gl.glTranslatef(1.5f, 0.0f, -6.0f);
        gl.glColor3f(0.5f, 0.5f, 1.0f);
        gl.glRotatef(rquad, 1.0f, 0.0f, 0.0f);

        gl.glBegin(GL2.GL_QUADS); // draw a quad
        gl.glVertex3f(-1.0f, 1.0f, 0.0f); // top left
        gl.glVertex3f(1.0f, 1.0f, 0.0f); // top right
        gl.glVertex3f(1.0f, -1.0f, 0.0f); // bottom right
        gl.glVertex3f(-1.0f, -1.0f, 0.0f); // bottom left
        gl.glEnd(); // done drawing the quad
        gl.glFlush();
        rtri += 0.2f;
        rquad -= 0.15f;
    }

    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
    }

    public void dispose(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();

        if (height <= 0)
            height = 1;

        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45f, h, 1.0, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public static void main(String[] args) {

        // set openGL version 2
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glCanvas = new GLCanvas(capabilities);
        Lesson4.Render render = new Lesson4.Render();
        glCanvas.addGLEventListener(render);
        glCanvas.setSize(400, 400);

        final FPSAnimator animator = new FPSAnimator(glCanvas, 300, true);

        final JFrame frame = new JFrame("Lesson 4");

        frame.getContentPane().add(glCanvas);

        // Shutdown
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (animator.isStarted())
                    animator.stop();
                System.exit(0);
            }
        });

        frame.setSize(frame.getContentPane().getPreferredSize());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(0, 0));
        frame.add(panel, BorderLayout.SOUTH);

        keyBindings(panel, frame, render);
        animator.start();


    }

    private static void keyBindings(JPanel panel, final JFrame frame, Lesson4.Render render) {
        ActionMap actionMap = panel.getActionMap();
        InputMap inputMap = panel.getInputMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "F1");
        actionMap.put("F1", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                fullScreen(frame);
            }
        });
    }

    protected static void fullScreen(JFrame frame) {
        if (!isFullScreen) {
            frame.dispose();
            frame.setUndecorated(true);
            frame.setVisible(true);
            frame.setResizable(false);
            xgraphic = frame.getSize();
            point = frame.getLocation();
            frame.setLocation(0, 0);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
            isFullScreen = true;
        } else {
            frame.dispose();
            frame.setUndecorated(false);
            frame.setResizable(true);
            frame.setLocation(point);
            frame.setSize(xgraphic);
            frame.setVisible(true);
            isFullScreen = false;

        }
    }
}