
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
            damage = 40;
            cost = 100;
            range = 3;
            cooldown = 60;

            loadImage("Student.jpg");
        }

        // Super Senior
        else if(index == 2)
        {
            damage = 80;
            cost = 140;
            range = 2;
            cooldown = 90;

            loadImage("SuperSenior.jpg");
        }

        // Teacher
        else if(index == 3)
        {
            damage = 90;
            cost = 120;
            range = 5;
            cooldown = 80;

            loadImage("Teacher.jpg");
        }

        // Nurse / Counselor
        else if(index == 4)
        {
            damage = 10;
            cost = 150;
            range = 4;
            cooldown = 10;

            loadImage("Nurse.jpg");
        }

        // Principal
        else if(index == 5)
        {
            damage = 100;
            cost = 180;
            range = 6;
            cooldown = 150;

            loadImage("Principal.jpg");
        }

        // Fallback
        else
        {
            damage = 1;
            cost = 1;
            range = 2;
            cooldown = 60;
        }
    }

    public void update(
        ArrayList<Enemy> enemies
    )
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
            double dx =
                enemy.x - x;

            double dy =
                enemy.y - y;

            double distance =
                Math.sqrt(
                    dx * dx +
                    dy * dy
                );

            if(distance <=
               range *
               GameScreen.TILE_SIZE)
            {
                if(enemy.progress >
                   bestProgress)
                {
                    bestProgress =
                        enemy.progress;

                    bestTarget =
                        enemy;
                }
            }
        }

        // Attack
        if(bestTarget != null)
        {
            bestTarget.health -=
                damage;

            timer = 0;
        }
    }

    private void loadImage(
        String fileName
    )
    {
        try
        {
            URL imageURL =
                getClass().getResource(
                    fileName
                );

            if(imageURL != null)
            {
                image =
                    new ImageIcon(
                        imageURL
                    ).getImage();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g)
    {
        // Bigger towers for 40px tiles
        int towerSize = 32;

        // Draw image if loaded
        if(image != null)
        {
            g.drawImage(
                image,
                x,
                y,
                towerSize,
                towerSize,
                null
            );
        }
        else
        {
            // Fallback
            g.setColor(Color.BLUE);

            g.fillRect(
                x,
                y,
                towerSize,
                towerSize
            );
        }

        // Border
        g.setColor(Color.BLACK);

        g.drawRect(
            x,
            y,
            towerSize,
            towerSize
        );
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



