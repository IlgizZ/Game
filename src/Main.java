import javax.swing.*;

public class Main {
    private static boolean startGame = false;

    static void setStartGame(boolean startGame) {
        Main.startGame = startGame;
    }

    static boolean isStartGame() {
        return startGame;
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Ice Board jumping");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(645, 630);
        f.setResizable(false);
        f.add(new Road());
        f.setVisible(true);

    }
}
