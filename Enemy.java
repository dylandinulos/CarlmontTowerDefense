
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Enemy
{
    double x, y;

    double speed;

    int health;
    int maxHealth;
    //camo stuff
    public boolean camo;

    public double progress;
    
    public int reward;

    Point[] path;

    int pathIndex = 0;

        public Enemy(Point[] path,double speed,int health,boolean camo,int reward)
    {
        this.path = path;
    
        this.speed = speed;
        
        this.health = health;
        this.maxHealth = health;
        this.reward = reward;
        this.camo = camo;
    
        x = path[0].x;
        y = path[0].y;
    }

    public void update()
    {
        // Already reached end
        if(pathIndex >= path.length - 1)
        {
            return;
        }

        // Next target point
        Point target =
            path[pathIndex + 1];

        double dx =
            target.x - x;

        double dy =
            target.y - y;

        double distance =
            Math.sqrt(
                dx * dx +
                dy * dy
            );

        // Reached target point
        if(distance <= speed)
        {
            x = target.x;
            y = target.y;

            pathIndex++;
        }
        else
        {
            // Move toward target
            x +=
                (dx / distance)
                * speed;

            y +=
                (dy / distance)
                * speed;
        }

        // Used for tower targeting
        progress = pathIndex;
    }

    public boolean reachedEnd()
    {
        return pathIndex >=
               path.length - 1;
    }

    public void draw(Graphics g)
    {
        // Bigger enemy size for 1440x900
        int size = 28;

        // Main body
        g.setColor(Color.RED);

        g.fillOval(
            (int)x - size / 2,
            (int)y - size / 2,
            size,
            size
        );

        // Outline
        g.setColor(Color.BLACK);

        g.drawOval(
            (int)x - size / 2,
            (int)y - size / 2,
            size,
            size
        );
    }
}

