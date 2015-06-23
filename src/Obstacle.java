import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ильгиз on 19.06.2015.
 */
public class Obstacle {
    private int x;
    private int startY = (int) (Math.random() * 150) + 150;
    private int imageStartY;
    private String name =
            String.valueOf((int) (Math.random() * 4) + 1);
    private BufferedImage img;
    private int width;
    private int height;

    private Road road;

    {
        try {
            img = ImageIO.read(new File("res/Obstacle/" + name + ".png"));
            width = img.getWidth();
            height = img.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getImageStartY() {
        return imageStartY;
    }

    public int getHeight() {
        return height;
    }

    public int getStartY() {
        return startY;
    }

    public Image getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public Obstacle(int x, Road road) {
        this.x = x;
        this.road = road;
        switch (name) {
            case "1":
                imageStartY = 45;
                break;
            case "2":
            case "3":
                imageStartY = 55;
                break;
            case "4":
                imageStartY = 25;
                break;

        }
        this.imageStartY += startY;
    }

    public void move() {
        x -= road.getP().getV();
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, road.getY(x, startY), width, height);
    }
}
