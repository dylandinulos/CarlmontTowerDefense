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

    public void loadImage(String fileName)
    {
        URL imageURL =
            getClass().getResource(fileName);

        if(imageURL != null)
        {
            logo =
                new ImageIcon(imageURL)
                .getImage();
        }
    }

    public void render(Graphics g)
    {
        // Background
        Color background =
            new Color(200, 200, 200);

        g.setColor(background);

        g.fillRect(
            0,
            0,
            1440,
            900
        );

        // Title
        Font titleFont =
            new Font(
                "Arial",
                Font.BOLD,
                64
            );

        g.setFont(titleFont);

        g.setColor(Color.BLACK);

        g.drawString(
            "Carlmont Tower Defense",
            320,
            220
        );

        // Main box
        g.setColor(
            new Color(
                60,
                60,
                60
            )
        );

        g.fillRoundRect(
            420,
            420,
            600,
            160,
            30,
            30
        );

        // Border
        g.setColor(Color.WHITE);

        g.drawRoundRect(
            420,
            420,
            600,
            160,
            30,
            30
        );

        // Instructions
        Font infoFont =
            new Font(
                "Arial",
                Font.PLAIN,
                32
            );

        g.setFont(infoFont);

        g.drawString(
            "Click Anywhere to Start",
            520,
            500
        );

        g.drawString(
            "Defend the school from enemies!",
            470,
            550
        );

        // Bottom text
        Font bottomFont =
            new Font(
                "Arial",
                Font.PLAIN,
                22
            );

        g.setFont(bottomFont);

        g.setColor(Color.BLACK);

        g.drawString(
            "Built with Java Swing",
            590,
            820
        );
    }
}
