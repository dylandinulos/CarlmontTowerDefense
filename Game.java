package main;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends JFrame 
{

    private GameScreen gameScreen;
    private BufferedImage img;
    private StartScreen startScreen;

    public enum STATE{
        MENU,
        GAME,
        END
    };
    
    public static STATE State = STATE.MENU;
    public Game() {
        setSize(640,640);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        gameScreen = new GameScreen();
        add(gameScreen);
        setVisible(true);
    }



    public static void main(String[] args) {
        Game game = new Game();
    }
}
