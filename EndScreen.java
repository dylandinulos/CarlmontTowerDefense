package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class EndScreen
{
    private Image logo;

    public void loadImage(String fileName)
    {
        URL imageURL = getClass().getResource(fileName);

        if(imageURL != null)
        {
            logo = new ImageIcon(imageURL).getImage();
        }
    }
    public void render(Graphics g)
    {
        // Background
        Color background = new Color(200, 200, 200);

        g.setColor(background);
        g.fillRect(0, 0, 1440, 900);

        // Main panel
        Color panelColor = new Color(30, 30, 120);

        g.setColor(panelColor);
        g.fillRoundRect(370, 220, 700, 350, 40, 40);

        // Border
        g.setColor(Color.WHITE);
        g.drawRoundRect(370, 220, 700, 350, 40, 40);

        // GAME OVER
        Font titleFont = new Font("Arial", Font.BOLD, 72);

        g.setFont(titleFont);
        g.drawString("GAME OVER", 500, 340);

        // Message
        Font messageFont = new Font("Arial", Font.PLAIN, 34);

        g.setFont(messageFont);
        g.drawString("Thanks for playing!", 540, 430);
        g.drawString("Restart the game to try again.", 470, 490);

        // Bottom text
        Font bottomFont = new Font("Arial", Font.PLAIN, 24);

        g.setFont(bottomFont);
        g.setColor(Color.BLACK);

        g.drawString("Carlmont Tower Defense", 560, 780);
    }
}
