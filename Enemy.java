 

package main;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy{
    double x, y;
    double speed;
    int health;
    int maxHealth;
    //int reward;   ---  for later
    
    
    public Enemy(double x, double y, double speed, int health) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.maxHealth = health;
        //this.reward = reward;
    }

    public Enemy(double x, double y) {
        this(x, y, 1.5, 10);
    }

    public void update() {
        x+=speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval((int)x, (int)y, 16, 16);
    }

}


