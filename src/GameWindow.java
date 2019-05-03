import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameWindow extends JFrame {
    private static GameWindow gameWindow;
    private static Image background;
    private static Image tg_logo;
    private static Image game_over;

    private static long last_frame_time;

    private static int score = 0;
    private static ArrayList<String> list_logo;


    private static Random randomGenerator;
    private static float tg_v = 200;
    private static float tg_left = 200;

    private static float tg_top = -100;

    public static void main(String[] args) throws IOException {

        randomGenerator = new Random();

        list_logo = new ArrayList<>();

        list_logo.add("insta.png");
        list_logo.add("facebook.png");
        list_logo.add("vk.png");
        list_logo.add("shazam.png");
        list_logo.add("skype.png");
        list_logo.add("google.png");
        list_logo.add("telegram.png");
        list_logo.add("telegram.png");
        list_logo.add("tg_white.png");
        list_logo.add("tg_white.png");
        list_logo.add("twitter.png");
        list_logo.add("youtube.png");


        background = ImageIO.read(GameWindow.class.getResourceAsStream(("tgbk.png")));
        tg_logo = ImageIO.read(GameWindow.class.getResourceAsStream("telegram.png"));
        game_over = ImageIO.read(GameWindow.class.getResourceAsStream(("go.png")));
        gameWindow = new GameWindow();
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setLocation(200, 100);
        gameWindow.setSize(906, 478);
        gameWindow.setResizable(false);
        last_frame_time = System.nanoTime();
        GameField gameField = new GameField();
        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float tg_right = tg_left + tg_logo.getWidth(null);
                float tg_bottom = tg_top + tg_logo.getHeight(null);
                boolean is_tg = x >= tg_left && x <= tg_right && y >= tg_top && y <= tg_bottom;
                if (is_tg) {
                    tg_top = -100;
                    tg_left = (int) (Math.random() * (gameField.getWidth() - tg_logo.getWidth(null)));
                    tg_v += 20;
                    score++;
                    gameWindow.setTitle("Blocked Telegram: " + score);

                    try {
                        tg_logo = ImageIO.read(GameWindow.class.getResourceAsStream((anyString())));
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                }
            }
        });
        gameWindow.add(gameField);
        gameWindow.setVisible(true);
    }

    private static void onRepaint(Graphics g) {
        long current_frame_time = System.nanoTime();
        float delta_time = (current_frame_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_frame_time;

        tg_top = tg_top + tg_v * delta_time;

        g.drawImage(background, 0, 0, null);
        g.drawImage(tg_logo, (int) tg_left, (int) tg_top, null);
        if (tg_top > gameWindow.getHeight()) {
            g.drawImage(game_over, gameWindow.getWidth() / 3, 130, null);
        }
    }

    private static String anyString() {
        int index = randomGenerator.nextInt(list_logo.size());
        return list_logo.get(index);
    }

    private static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}

