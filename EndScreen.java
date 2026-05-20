package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import javax.swing.JPanel;
import java.net.URL;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JFrame;

public class EndScreen
{
    private Image logo;
    //image
    public void loadImage(String fileName)
    {
        URL imageURL =
            getClass().getResource(fileName);

        if(imageURL != null)
        {
            logo = new ImageIcon(imageURL).getImage();
        }
    }
    public void render(Graphics g)
    {
        Color c1 = new Color(200, 200, 200);
        Color c2 = new Color(0, 0, 200);
        g.setColor(c1);
        g.fillRect(0,0, 640, 640);
        g.setColor(c2);
        g.fillRect(150, 230, 350, 200);
        
        Font font1 = new Font ("arial", Font.BOLD, 50);
        g.setFont(font1);
        g.setColor(Color.BLACK);
        g.drawString("GAME OVER", 170, 100);
        
        Font font2 = new Font ("arial", Font.PLAIN, 25);
        g.setFont(font2);
        g.drawString("Thanks for playing!", 210, 480);
        g.drawString("Your Stats", 270, 200);
        
        /* ImageIcon i = new ImageIcon("Student.jpg");
        JLabel label = new JLabel(i);
        frame.add(label); */
    }
}
