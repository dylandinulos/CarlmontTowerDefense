 

import java.awt.Color;
import java.awt.Graphics;

public class Enemy{
    double x, y;
    double speed;
    int health;
    int maxHealth;
    public double progress;
    //int reward;   ---  for later


    public Enemy(double x, double y, double speed, int health) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.maxHealth = health;
        //this.reward = reward;
    }

    public void update() {
        x+=speed;
        progress = x;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval((int)x, (int)y, 16, 16);
    }

}
