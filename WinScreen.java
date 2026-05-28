package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class WinScreen
{
    private Image logo;
    public void render(Graphics g)
    {
        Color backgroundColor = new Color(200, 200, 200);

        g.setColor(backgroundColor);
        g.fillRect(0, 0, 1440, 900);

        Color c1 = new Color(0, 128, 0);

        g.setColor(c1);
        g.fillRoundRect(370, 220, 700, 350, 40, 40);

        g.setColor(Color.WHITE);

        Font titleFont = new Font("Arial", Font.PLAIN, 38);

        g.setFont(titleFont);
        g.drawString("Congrats you won!", 560, 340);

        Font messageFont = new Font("Arial", Font.PLAIN, 28);

        g.drawString("Restart the game to play again!", 460, 490);
    }
}
