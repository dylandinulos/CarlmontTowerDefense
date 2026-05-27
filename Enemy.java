package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Enemy
{
    double x, y;
    double speed;

    int health;
    int maxHealth;

    public double progress;

    Point[] path;

    int pathIndex = 0;

    public Enemy(Point[] path, double speed, int health)
    {
        this.path = path;
        this.speed = speed;

        this.health = health;
        this.maxHealth = health;

        x = path[0].x;
        y = path[0].y;
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
        int size = 28;

        g.setColor(Color.RED);

        g.fillOval((int)x - size / 2, (int)y - size / 2, size, size);

        g.setColor(Color.BLACK);

        g.drawOval((int)x - size / 2, (int)y - size / 2, size, size);
    }
}
