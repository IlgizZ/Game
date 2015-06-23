import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ильгиз on 16.06.2015.
 */
public class Player {
    private BufferedImage img;
    private BufferedImage boardImg;
    private int width;
    private int boardHeight;

    {
        try {
            boardImg = ImageIO.read(new File("res/Rider/Board.png"));
            width = boardImg.getWidth();
            boardHeight = boardImg.getHeight();

            img = ImageIO.read(new File("res/Rider/Man.png"));
            img.getGraphics().drawImage(boardImg, 0, img.getHeight() - boardHeight, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int v = 0;
    private final int MAX_V = 60;
    private final int MAX_TOP = 80;
    private final int MAX_BOTTOM = 270;
    private int dv = 5;
    private int dy = 0;
    private int s = 0;
    private int sWithOutCrash = -1;

    private int layer1 = 0;
    private int layer2 = 640;

    private int x = 0;
    private int y = 170;

    public int getXLayer2() {
        return layer2;
    }

    public int getXLayer1() {
        return layer1;
    }

    public Image getImg() {
        return img;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getV() {
        return v;
    }

    public int getWidth() {
        return width;
    }

    public void ride() {

        s += v;
        sWithOutCrash += v;
        if (sWithOutCrash >= (40 * v) && v < MAX_V) {
            v += dv;
            sWithOutCrash = 0;
            int min = Road.getMinSpawnTime();
            int max = Road.getMaxSpawnTime();
            if (max > 1500) {
                Road.setMinSpawnTime(min - 500);
                Road.setMaxSpawnTime(max - 500);
            }
            System.out.println(v);
        }
        y += dy;
        if (y < MAX_TOP) {
            y = MAX_TOP;
        } else if (y > MAX_BOTTOM) {
            y = MAX_BOTTOM;
        }
        if (layer2 - v <= 0) {
            layer1 = 0;
            layer2 = 640;
        } else {
            layer1 -= v;
            layer2 -= v;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            dy = -v / dv - 3;
        } else if (key == KeyEvent.VK_DOWN) {
            dy = v / dv + 3;
        } else if (key == KeyEvent.VK_SPACE) {
            System.out.println("Jump animation");
        } else if (key == KeyEvent.VK_RIGHT && sWithOutCrash < 0) {
            Main.startGame = true;
            sWithOutCrash++;
            x = 20;
        }
    }

    public void keyRleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

    public void hitTest(Obstacle obstacle) {
        if ((obstacle.getX() <= x + width) && (obstacle.getX() + obstacle.getWidth() > x + width / 2) && (!isLower(obstacle))) {
            int boardX = x + width;
            int boardY = y + img.getHeight();
            int result = (int) (boardX * Road.K + obstacle.getImageStartY()) - boardY;
            if (result < 30 && !obstacle.getName().equals("1"))
                Road.gameOver();
        }
    }

    public boolean isLower(Obstacle obstacle) {
        int boardX = x + width;
        int boardY = y + img.getHeight();
        boolean result = ((int) (boardX * Road.K + obstacle.getImageStartY()) < boardY);
        return result;
    }
}
