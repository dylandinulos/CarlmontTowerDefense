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
    private String name;
    
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
            name = "Student";
            damage = 5;
            cost = 20;
            range = 3;
            cooldown = 30;

            loadImage("Student.png");
        }

        // Super Senior
        else if(index == 2)
        {
            name = "Super Senior";
            damage = 55;
            cost = 50;
            range = 3;
            cooldown = 120;

            loadImage("SuperSenior.png");
        }

        // Teacher
        else if(index == 3)
        {
            name = "Teacher";
            damage = 20;
            cost = 90;
            range = 4;
            cooldown = 40;

            loadImage("Teacher.png");
        }

        // Nurse
        else if(index == 4)
        {
            name = "Nurse";
            damage = 60;
            cost = 150;
            range = 4;
            cooldown = 180;

            loadImage("Nurse.png");
        }

        // Principal
        else if(index == 5)
        {
            name = "Principal";
            damage = 110;
            cost = 300;
            range = 5;
            cooldown = 60;

            loadImage("Principal.png");
        }

        // Fallback
        else
        {
            name = null;
            damage = 1;
            cost = 1;
            range = 2;
            cooldown = 120;
        }
    }

    public void update(ArrayList<Enemy> enemies)
    {
        timer++;

        // Cooling down
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

            double distance = Math.sqrt(dx * dx + dy * dy);

            if(distance <= range * GameScreen.TILE_SIZE)
            {
                if(enemy.progress > bestProgress)
                {
                    bestProgress = enemy.progress;
                    bestTarget = enemy;
                }
            }
        }

        // Attack
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
            URL imageURL = getClass().getResource(fileName);

            if(imageURL != null)
            {
                image =new ImageIcon(imageURL).getImage();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g)
    {
        // Bigger towers for larger tiles
        int towerSize = 32;

        // Draw image if loaded
        if(image != null)
        {
            g.drawImage(image, x, y, towerSize, towerSize, null);
        }
        else
        {
            // Fallback
            g.setColor(Color.BLUE);

            g.fillRect(x, y, towerSize, towerSize);
        }

        // Border
        g.setColor(Color.BLACK);

        g.drawRect(x, y, towerSize, towerSize);
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
    
    public String getName()
    {
        return name;
    }

    // Static cost lookup
    public static int getCost(int towerType)
    {
        if(towerType == 1)
        {
            return 20;
        }
        else if(towerType == 2)
        {
            return 50;
        }
        else if(towerType == 3)
        {
            return 90;
        }
        else if(towerType == 4)
        {
            return 150;
        }
        else
        {
            return 300;
        }
    }
}
