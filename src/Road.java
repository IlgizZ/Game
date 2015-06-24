import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ильгиз on 16.06.2015.
 */
public class Road extends JPanel implements ActionListener, Runnable {
    private class RoadKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            p.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            p.keyRleased(e);
        }

    }

    private Image downLine = new ImageIcon("res/DownLine.png").getImage();

    private Image downLine2 = new ImageIcon("res/DownLine.png").getImage();
    private final int Y_DOWN_L = 350;

    private Image highLine1 = new ImageIcon("res/HighLine.png").getImage();
    private Image highLine2 = new ImageIcon("res/HighLine.png").getImage();
    private final int Y_HIGH_L = 150;

    private Image bg = new ImageIcon("res/Background.jpg").getImage();

    private Thread obstacleFactory = new Thread(this);
    private List<Obstacle> obstacles = new ArrayList();
    private static int minSpawnTime = 6000;
    private static int maxSpawnTime = 7500;
    public static final double K = 130d / 640;

    private Player p = new Player();

    private Timer mainTimer = new Timer(40, this);

    static int getY(int x, int b) {
        return (int) (K * x + b);
    }

    public static int getMinSpawnTime() {
        return minSpawnTime;
    }

    public static int getMaxSpawnTime() {
        return maxSpawnTime;
    }

    public Player getP() {
        return p;
    }

    public static void setMaxSpawnTime(int max) {
        maxSpawnTime = max;
    }

    public static void setMinSpawnTime(int min) {
        minSpawnTime = min;
    }

    public Road() {
        mainTimer.start();
        obstacleFactory.start();
        addKeyListener(new RoadKeyAdapter());
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bg, 0, 0, null);
        g.drawImage(highLine1, p.getXLayer1(), getY(p.getXLayer1(), Y_HIGH_L), null);
        g.drawImage(highLine2, p.getXLayer2(), getY(p.getXLayer2(), Y_HIGH_L), null);
        g.drawImage(p.getImg(), p.getX(), p.getY(), null);
        Iterator<Obstacle> it = obstacles.iterator();
        while (it.hasNext()) {
            Obstacle ob = it.next();
            int x = ob.getX();
            if (x <= -160) {
                it.remove();
            } else {
                int y = getY(x, ob.getStartY());
                g.drawImage(ob.getImg(), x, y, null);
                if (x <= (p.getX() + p.getWidth()) && p.isLower(ob)) {
                    g.drawImage(p.getImg(), p.getX(), p.getY(), null);
                }
            }
        }
        g.drawImage(downLine, p.getXLayer1(), getY(p.getXLayer1(), Y_DOWN_L), null);
        g.drawImage(downLine2, p.getXLayer2(), getY(p.getXLayer2(), Y_DOWN_L), null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        p.ride();
        obstacles.stream().forEach(obstacle -> obstacle.move());
        repaint();
        Iterator i = obstacles.iterator();
        while (i.hasNext())
            if (p.hitTest((Obstacle) i.next()))
                i.remove();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(1);
            if (Main.startGame) {
                try {
                    System.out.println(1);
                    Thread.sleep(minSpawnTime + (int) (Math.random() * (maxSpawnTime - minSpawnTime)));
                    obstacles.add(new Obstacle(640, this));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void gameOver() {
        Main.startGame = false;
        JOptionPane.showMessageDialog(null, "Game Over!");
        System.exit(1);
    }
}
