import javax.swing.*;

public class Main {
    public static boolean startGame = false;

    public static void main(String[] args) {
        JFrame f = new JFrame("Ice Board jumping");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(645, 630);
        f.setResizable(false);
        f.add(new Road());
        f.setVisible(true);

    }
}
