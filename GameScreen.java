package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.Random;
import javax.swing.JPanel;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameScreen extends JPanel{

    private Random random;
    

    
    private ArrayList<Enemy> enemies = new ArrayList<>();
    int score = 1000;
    int wave = 0;
    int enemiesToSpawn = 0;
    int enemiesSpawned = 0;
    int spawnTimer = 0;
    int spawnDelay = 60;
    private ArrayList<Tower> towers = new ArrayList<>();

    private Enemy createEnemy(String type) 
    {
        switch(type)
        {
        case "tank":
            return new Enemy(0, 328, 0.5, 120);
        case "fast":
            return new Enemy(0, 328, 1.6, 40);
        case "boss":
            return new Enemy(0, 328, 0.3, 300);
        default:
            return new Enemy(0, 328, 1, 70); // base
        }
    }

    public GameScreen() {
        
        
        random = new Random();

        Timer timer = new Timer(16, e -> {
            update();
            repaint();
        });
        timer.start();
        
        enemiesToSpawn = 1;
        enemiesSpawned = 0;
        towers.add(new Tower(1,5*32+4,320-28));
    }
    
    public void update() {
        
        for(int i =0; i<enemies.size();i++) {
            Enemy enemy = enemies.get(i);
            enemy.update();
            if(enemy.x>640) {
                enemies.remove(i);
                score-=enemy.health;
            }
        }
        if(wave<10){
        if(enemiesSpawned<enemiesToSpawn) {
            spawnTimer++;
            if(spawnTimer>=spawnDelay) {
                spawnTimer=0;
                if(spawnTimer>=spawnDelay) {
                spawnTimer=0;
                enemies.add(createEnemy("fast"));
                enemiesSpawned++;
            }
        } else if(enemies.size()==0) {
            wave++;
            enemiesToSpawn = wave*2;
            enemiesSpawned = 0;
            spawnDelay = (int)(spawnDelay/1.1);
            }
        }
        for(Tower tower : towers) {
            tower.update(enemies);
        }
        for(int i = 0; i < enemies.size();i++) {
            Enemy enemy = enemies.get(i);
            if(enemy.health <= 0) {
                enemies.remove(i);
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for(int y=0;y<20;y++) {
            for(int x=0;x<20;x++) {
                g.setColor(Color.GREEN);
                g.fillRect(x*32,y*32,32,32);
        }
        }
        for(int y=0;y<20;y++) {
            for(int x=0;x<20;x++) {
                g.setColor(Color.BLACK);
                g.drawRect(x*32,y*32,32,32);
        }
        }
        
        //PATH
        for(int i=0;i<20;i++){
            g.setColor(Color.GRAY);
            g.fillRect(i*32,10*32,32,32);
        }
        

        for(Enemy enemy : enemies) {
            enemy.draw(g);
        }
        g.setColor(Color.BLACK);
        g.drawString("Score: "+score, 10, 20);
        for(Tower tower : towers) {
            tower.draw(g);
            awdsadawdsawdsawd;
        }
    }

    

    private int getRandomInt(int min, int max) {
        return random.nextInt(100);
    }
    private Color getRandomColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }
}
