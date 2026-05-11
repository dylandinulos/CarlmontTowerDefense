import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

//For images
import java.net.URL;
import javax.swing.*;
import java.awt.*;

public class Tower {
    private int damage;
    private int cost;
    private int range;
    private String name;
    private int cooldown;
    private int lastDamageTime = 0;
    private int x, y;
    private int timer = 0;
    private Image image;
    public Tower (int index, int x, int y){
        this.x = x;
        this.y = y;
        // ADD PRIORITY: TARGETTING THE ENEMY FURTHEST DOWN THE ROAD
        if (index == 1) //Student
        {
            damage = 100;
            cost = 1;
            range = 3;
            cooldown = 120; 
            loadImage(x, y, "Student.jpg");
        }
        else if (index == 2) //SuperSenior
        {
            damage = 3;
            cost = 3;
            range = 1;
            cooldown = 180;
            loadImage(x, y, "SuperSenior.jpg");
        }
        else if (index == 3) //Teacher
        {
            damage = 2;
            cost = 2;
            range = 4;
            cooldown = 160;
            loadImage(x, y, "Teacher.jpg");
        }
        else if (index == 4) //Nurse
        {
            damage = 3;
            cost = 5;
            range = 3;
            cooldown = 120;
            loadImage(x, y, "Nurse.jpg");
        }
        else if (index == 5) //Principal
        {
            damage = 5;
            cost = 7;
            range = 4;
            cooldown = 200;
            loadImage(x, y, "Principal.jpg");
        }                
    }
    public void update(ArrayList<Enemy> enemies) {
        timer++;
        if(timer>=cooldown){
            Enemy target = null;
            double furthestProgress = -1;
            for(Enemy enemy : enemies) {
                double distance = Math.sqrt(Math.pow(enemy.x - x, 2) + Math.pow(enemy.y - y, 2));
                if(distance <= range*32) {
                    if(enemy.progress > furthestProgress) {
                        furthestProgress = enemy.progress;
                        target = enemy;
                    }
                }
            }
            if(target != null) {
                target.health-=damage;
                timer =0;
            }
        }
    }
   public void loadImage(int x, int y, String fileName)
    {
        URL ImageURL = getClass().getResource(fileName); //Remember to change this to our characters in the game
        this.image = new ImageIcon(ImageURL).getImage();
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


}
