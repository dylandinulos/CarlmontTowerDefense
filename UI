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

public Class StartScreen
{
  private Image logo;
    //private JFrame frame;
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
        g.setColor(c1);
        g.fillRect(0,0, 640, 640);
        
        Font font1 = new Font ("arial", Font.BOLD, 50);
        g.setFont(font1);
        g.setColor(Color.BLACK);
        g.drawString("Carlmont Tower Defense", 20, 100);
        
        Font font2 = new Font ("arial", Font.PLAIN, 25);
        g.setFont(font2);
        g.drawString("Click to Play!", 240, 480);
        
        /* ImageIcon i = new ImageIcon("Student.jpg");
        JLabel label = new JLabel(i);
        frame.add(label); */
    }
}
