package main;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends JFrame
{
    private GameScreen gameScreen;

    private BufferedImage img;

    private StartScreen startScreen;

    public enum STATE
    {
        MENU,
        GAME,
        END
    };

    public static STATE State =
        STATE.MENU;

    public Game()
    {
        // Better size for MacBook Air
        setSize(1440, 900);

        setTitle(
            "Carlmont Tower Defense"
        );

        setDefaultCloseOperation(
            EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setResizable(false);

        gameScreen =
            new GameScreen();

        add(gameScreen);

        setVisible(true);
    }

    public static void main(
        String[] args
    )
    {
        Game game = new Game();
    }
}
