 

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
    int spawnTimer = 0;
    int spawnDelay = 60;
    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<ArrayList<String>> waves = new ArrayList<>(); // WAVES
    private int currentEnemyIndex = 0;

    private Enemy createEnemy(String type) 
    {
        switch(type)
        {
        case "tank":
            return new Enemy(0, 328, 0.5, 120);
        case "fast":
            return new Enemy(0, 328, 2.5, 40);
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
        

        towers.add(new Tower(1,5*32+4,320-28));
        
        //WAVES <<<<<
        // Wave 0
        ArrayList<String> wave0 = new ArrayList<>();
        wave0.add("base");

        // Wave 1
        ArrayList<String> wave1 = new ArrayList<>();
        wave1.add("base");
        wave1.add("base");
        wave1.add("fast");

        // Wave 2
        ArrayList<String> wave2 = new ArrayList<>();
        wave2.add("tank");
        wave2.add("fast");
        wave2.add("fast");
        wave2.add("base");

        // Wave 3 (example mix)
        ArrayList<String> wave3 = new ArrayList<>();
        wave3.add("tank");
        wave3.add("tank");
        wave3.add("fast");
        wave3.add("boss");

        // Add all waves
        waves.add(wave0);
        waves.add(wave1);
        waves.add(wave2);
        waves.add(wave3);

    }

    
    public void update() 
    {
        
        for(int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.update();
            if(enemy.x>640) {
                enemies.remove(i);
                score-=enemy.health;
            }
        }
        if(wave<10){
        if(wave < waves.size()) {
        ArrayList<String> currentWave = waves.get(wave);

        if(currentEnemyIndex < currentWave.size()) 
        {
            spawnTimer++;
            if(spawnTimer >= spawnDelay) {
                spawnTimer = 0;

                String type = currentWave.get(currentEnemyIndex);
                enemies.add(createEnemy(type));

                currentEnemyIndex++;
            }
        } 
        else if(enemies.size() == 0) 
        {
            // next wave
            wave++;
            currentEnemyIndex = 0;
            }
        }
        for(Tower tower : towers) {
            tower.update(enemies);
        }
        for(int i = enemies.size() - 1; i >= 0; i--)
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
