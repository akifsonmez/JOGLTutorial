package Lesson1;

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


    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

    }

    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
    }

    public void dispose(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
    }

    public static void main(String[] args) {

        // set openGL version 2
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glCanvas = new GLCanvas(capabilities);
        Render render = new Render();
        glCanvas.addGLEventListener(render);
        glCanvas.setSize(400, 400);

        final FPSAnimator animator = new FPSAnimator(glCanvas, 300, true);

        final JFrame frame = new JFrame("Lesson 1");

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

    private static void keyBindings(JPanel panel, final JFrame frame, Render render) {
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

