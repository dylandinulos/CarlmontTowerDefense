package main;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tower {
    private int damage;
    private int cost;
    private int range;
    private String name;
    private int cooldown;
    private int lastDamageTime = 0;
    private int x, y;
    private int timer = 0;
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

        }
        else if (index == 2) //SuperSenior
        {
            damage = 3;
            cost = 3;
            range = 1;
            cooldown = 180;
        }
        else if (index == 3) //Teacher
        {
            damage = 2;
            cost = 2;
            range = 4;
            cooldown = 160;
        }
        else if (index == 4) //Counselor
        {
            damage = 3;
            cost = 5;
            range = 3;
            cooldown = 120;
        }
        else if (index == 5) //Principal
        {
            damage = 5;
            cost = 7;
            range = 4;
            cooldown = 200;
        }
        
    }
    public void update(ArrayList<Enemy> enemies) {
        timer++;
        if(timer>=cooldown){
            timer=0;
            for(Enemy enemy : enemies) {
                double distance = Math.sqrt(Math.pow(enemy.x - x, 2) + Math.pow(enemy.y - y, 2));
                if(distance <= range*32) {
                    enemy.health-=damage;
                    break; //REMOVE IF WE WANT SPLASH DAMAGE
                }
            }
        }
    }
        
    
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 24, 24);
    }


}
