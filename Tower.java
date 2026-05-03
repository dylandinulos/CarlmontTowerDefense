package main;
import java.awt.Color;
import java.awt.Graphics;

public class Tower {
    private int damage;
    private int cost;
    private int range;
    private String name;
    private long cooldown;
    private int lastDamageTime = 0;
    private int x, y;
    public Tower (int index, int x, int y){
        this.x = x;
        this.y = y;
        // ADD PRIORITY: TARGETTING THE ENEMY FURTHEST DOWN THE ROAD
        if (index == 1) //Student
        {
            damage = 50;
            cost = 1;
            range = 3;
            cooldown = 2000; 

        }
        else if (index == 2) //SuperSenior
        {
            damage = 3;
            cost = 3;
            range = 1;
            cooldown = 4000;
        }
        else if (index == 3) //Teacher
        {
            damage = 2;
            cost = 2;
            range = 4;
            cooldown = 2000;
        }
        else if (index == 4) //Counselor
        {
            damage = 3;
            cost = 5;
            range = 3;
            cooldown = 3000;
        }
        else if (index == 5) //Principal
        {
            damage = 5;
            cost = 7;
            range = 4;
            cooldown = 4000;
        }
        
    }
    public void update(){
        
    }
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 24, 24);
    }


}