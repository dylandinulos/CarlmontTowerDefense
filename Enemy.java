package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Image;

import java.net.URL;

import javax.swing.ImageIcon;
public class Enemy
{
    double x, y;
    double speed;
    private Image image;

    int health;
    int maxHealth;

    public double progress;

    Point[] path;

    int pathIndex = 0;
    

    public Enemy(Point[] path, double speed, int health, String type)
    {
        this.path = path;
        this.speed = speed;

        this.health = health;
        this.maxHealth = health;

        x = path[0].x;
        y = path[0].y;
        
        if (type.equals("tank"))
        {
            loadImage("tank.png");
        }
        else if (type.equals("fast"))
        {
            loadImage("fast.png");
        }
        else if (type.equals("boss"))
        {
            loadImage("boss.png");
        }
        else
        {
            loadImage("normal.png");
        }
    }

    public void update()
    {
        if(pathIndex >= path.length - 1)
        {
            return;
        }

        Point target = path[pathIndex + 1];

        double dx = target.x - x;
        double dy = target.y - y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if(distance <= speed)
        {
            x = target.x;
            y = target.y;

            pathIndex++;
        }
        else
        {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }

        progress = pathIndex;
    }

    public boolean reachedEnd()
    {
        return pathIndex >= path.length - 1;
    }

    public void draw(Graphics g)
    {
        if (image != null)
        {
            int size = 28;
    
            g.setColor(Color.RED);
    
            g.drawImage(image, (int)x - size / 2, (int)y - size / 2, size, size, null);

        }
    }
    private void loadImage(String fileName)
    {
        try
        {
            URL imageURL =
                getClass().getResource(fileName);

            if(imageURL != null)
            {
                image =
                    new ImageIcon(imageURL)
                    .getImage();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
