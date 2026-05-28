package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class StartScreen
{
    private Image logo;
    public void render(Graphics g)
    {
        //Background
        Color backgroundColor = new Color(46, 158, 255);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, 1440, 900);

        //text stuff
        Font title = new Font("Arial", Font.BOLD, 64);
        g.setFont(title);
        g.setColor(Color.BLACK);
        g.drawString("Carlmont Tower Defense", 320, 220);

        g.setColor(new Color(60, 60, 60));       
        g.fillRoundRect(400, 420, 600, 160, 30, 30);
        
        g.setColor(Color.WHITE);
        Font f2 = new Font("Arial", Font.PLAIN, 32);
        g.setFont(f2);
        g.drawString("Click Anywhere to Start", 530, 510);
    }
}
