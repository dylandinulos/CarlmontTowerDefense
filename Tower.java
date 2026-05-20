package main; 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Tower 
{
    private int damage;
    private int cost;
    private int range;
    private int cooldown;

    private int x, y;

    private int timer = 0;

    private Image image;

    public Tower(int index, int x, int y)
    {
        this.x = x;
        this.y = y;

        // Student
        if(index == 1)
        {
            damage = 100;
            cost = 1;
            range = 3;
            cooldown = 120;

            loadImage("Student.jpg");
        }

        // Super Senior
        else if(index == 2)
        {
            damage = 3;
            cost = 3;
            range = 1;
            cooldown = 180;

            loadImage("SuperSenior.jpg");
        }

        // Teacher
        else if(index == 3)
        {
            damage = 2;
            cost = 2;
            range = 4;
            cooldown = 160;

            loadImage("Teacher.jpg");
        }

        // Nurse / Counselor
        else if(index == 4)
        {
            damage = 35;
            cost = 5;
            range = 3;
            cooldown = 50;

            loadImage("Nurse.jpg");
        }

        // Principal
        else if(index == 5)
        {
            damage = 150;
            cost = 7;
            range = 4;
            cooldown = 200;

            loadImage("Principal.jpg");
        }

        // Fallback
        else
        {
            damage = 1;
            cost = 1;
            range = 2;
            cooldown = 120;
        }
    }

    public void update(ArrayList<Enemy> enemies)
    {
        timer++;

        // Still cooling down
        if(timer < cooldown)
        {
            return;
        }

        Enemy bestTarget = null;

        double bestProgress = -1;

        // Find furthest enemy in range
        for(Enemy enemy : enemies)
        {
            double dx = enemy.x - x;
            double dy = enemy.y - y;

            double distance =
                Math.sqrt(dx * dx + dy * dy);

            if(distance <= range * 32)
            {
                if(enemy.progress > bestProgress)
                {
                    bestProgress = enemy.progress;
                    bestTarget = enemy;
                }
            }
        }

        // Attack target
        if(bestTarget != null)
        {
            bestTarget.health -= damage;

            timer = 0;
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
                    new ImageIcon(imageURL).getImage();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g)
    {
        // Draw image if loaded
        if(image != null)
        {
            g.drawImage(
                image,
                x,
                y,
                24,
                24,
                null
            );
        }
        else
        {
            // Fallback rectangle
            g.setColor(Color.BLUE);

            g.fillRect(x, y, 24, 24);
        }
    }

    // Getters
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getRange()
    {
        return range;
    }

    public int getCost()
    {
        return cost;
    }
}
