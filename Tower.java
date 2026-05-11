import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

<<<<<<< HEAD
public class Tower
{
=======
//For images
import java.net.URL;
import javax.swing.*;
import java.awt.*;

public class Tower {
>>>>>>> 87bebbaeead5fae1374660c6b2bf5b88dd3f90a9
    private int damage;
    private int cost;
    private int range;
    private int cooldown;

    private int x, y;
    private int timer = 0;
<<<<<<< HEAD

    public Tower(int index, int x, int y)
    {
=======
    private Image image;
    public Tower (int index, int x, int y){
>>>>>>> 87bebbaeead5fae1374660c6b2bf5b88dd3f90a9
        this.x = x;
        this.y = y;

        if(index == 1) // Student
        {
            damage = 100;
            cost = 1;
            range = 3;
<<<<<<< HEAD
            cooldown = 120;
=======
            cooldown = 120; 
            loadImage(x, y, "Student.jpg");
>>>>>>> 87bebbaeead5fae1374660c6b2bf5b88dd3f90a9
        }
        else if(index == 2) // SuperSenior
        {
            damage = 3;
            cost = 3;
            range = 1;
            cooldown = 180;
            loadImage(x, y, "SuperSenior.jpg");
        }
        else if(index == 3) // Teacher
        {
            damage = 2;
            cost = 2;
            range = 4;
            cooldown = 160;
            loadImage(x, y, "Teacher.jpg");
        }
<<<<<<< HEAD
        else if(index == 4) // Counselor
=======
        else if (index == 4) //Nurse
>>>>>>> 87bebbaeead5fae1374660c6b2bf5b88dd3f90a9
        {
            damage = 3;
            cost = 5;
            range = 3;
            cooldown = 120;
            loadImage(x, y, "Nurse.jpg");
        }
        else // Principal
        {
            damage = 5;
            cost = 7;
            range = 4;
            cooldown = 200;
<<<<<<< HEAD
        }
=======
            loadImage(x, y, "Principal.jpg");
        }                
>>>>>>> 87bebbaeead5fae1374660c6b2bf5b88dd3f90a9
    }

    public void update(ArrayList<Enemy> enemies)
    {
        timer++;

        if(timer < cooldown)
            return;

        Enemy target = null;
        double furthestProgress = -1;

        for(Enemy enemy : enemies)
        {
            double dx = enemy.x - x;
            double dy = enemy.y - y;

            double distance = Math.sqrt(dx * dx + dy * dy);

            if(distance <= range * 32)
            {
                if(enemy.progress > furthestProgress)
                {
                    furthestProgress = enemy.progress;
                    target = enemy;
                }
            }
        }

        if(target != null)
        {
            target.health -= damage;
            timer = 0;
        }
    }
<<<<<<< HEAD

    public void draw(Graphics g)
    {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 24, 24);
=======
   public void loadImage(int x, int y, String fileName)
    {
        URL ImageURL = getClass().getResource(fileName); //Remember to change this to our characters in the game
        this.image = new ImageIcon(ImageURL).getImage();
>>>>>>> 87bebbaeead5fae1374660c6b2bf5b88dd3f90a9
    }
    public void draw(Graphics g) 
    {
        g.drawImage(image, 320, 288, 32, 32, null); // We need to make the 0, 0 correspond to where they want to place the tower
    }     
    //Uncomment if we want the rectangle
    // public void draw(Graphics g) {
    //    g.setColor(Color.BLUE);
    //    g.fillRect(x, y, 24, 24);
    }

    // getters (needed for selection + range UI)
    public int getX() { return x; }
    public int getY() { return y; }
    public int getRange() { return range; }
}