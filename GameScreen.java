package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GameScreen extends JPanel implements MouseListener
{
    public static final int TILE_SIZE = 32;
    public static final int ROWS = 20;
    public static final int COLS = 20;

    private Random random;

    // Road path
    Point[] path = {

        new Point(16, 336),

        new Point(176, 336),
        new Point(176, 144),

        new Point(400, 144),
        new Point(400, 464),

        new Point(112, 464),
        new Point(112, 208),

        new Point(528, 208),
        new Point(528, 528),

        new Point(304, 528),
        new Point(304, 336),

        new Point(688, 336)
    };

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<ArrayList<String>> waves = new ArrayList<>();

    private int[][] map = new int[ROWS][COLS];

    private int money = 100;
    private int lives = 20;

    private int wave = 0;
    private int currentEnemyIndex = 0;

    private int spawnTimer = 0;
    private int spawnDelay = 60;

    private Tower selectedTower = null;

    public GameScreen()
    {
        random = new Random();

        // Create simple road row for placement blocking
        for(int i = 0; i < COLS; i++)
        {
            map[10][i] = 1;
        }

        setupWaves();

        addMouseListener(this);

        // Example starter tower
        

        Timer timer = new Timer(16, e -> {
            if (Game.State == Game.STATE.GAME){
                update();
            }            
            repaint();
        });

        timer.start();
    }

    private void setupWaves()
    {
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

        // Wave 3
        ArrayList<String> wave3 = new ArrayList<>();
        wave3.add("tank");
        wave3.add("tank");
        wave3.add("fast");
        wave3.add("boss");

        waves.add(wave0);
        waves.add(wave1);
        waves.add(wave2);
        waves.add(wave3);
    }

    private Enemy createEnemy(String type)
    {
        switch(type)
        {
            case "tank":
                return new Enemy(path, 0.5, 120);

            case "fast":
                return new Enemy(path, 2.5, 40);

            case "boss":
                return new Enemy(path, 0.3, 300);

            default:
                return new Enemy(path, 1, 70);
        }
    }

    private boolean isOnPath(int mouseX, int mouseY)
    {
    for(int i = 0; i < path.length - 1; i++)
    {
        Point p1 = path[i];
        Point p2 = path[i + 1];

        double distance =
            pointToSegmentDistance(
                mouseX,
                mouseY,
                p1.x,
                p1.y,
                p2.x,
                p2.y
            );

        // road thickness
        if(distance < 20)
        {
            return true;
        }
    }

    return false;
    }

    private double pointToSegmentDistance(
    double px,
    double py,
    double x1,
    double y1,
    double x2,
    double y2
)
{
    double dx = x2 - x1;
    double dy = y2 - y1;

    if(dx == 0 && dy == 0)
    {
        dx = px - x1;
        dy = py - y1;

        return Math.sqrt(dx * dx + dy * dy);
    }

    double t =
        ((px - x1) * dx + (py - y1) * dy) /
        (dx * dx + dy * dy);

    t = Math.max(0, Math.min(1, t));

    double closestX = x1 + t * dx;
    double closestY = y1 + t * dy;

    dx = px - closestX;
    dy = py - closestY;

    return Math.sqrt(dx * dx + dy * dy);
    }

    public void update()
    {
        // Update enemies
        for(int i = enemies.size() - 1; i >= 0; i--)
        {
            Enemy enemy = enemies.get(i);

            enemy.update();

            // Optional targeting helper
            enemy.progress = enemy.x;

            // Enemy escaped
            if(enemy.x > 640)
            {
                enemies.remove(i);

                lives--;

                // Optional score penalty
                money -= 5;
            }
        }

        // Spawn enemies
        if(wave < waves.size())
        {
            ArrayList<String> currentWave = waves.get(wave);

            if(currentEnemyIndex < currentWave.size())
            {
                spawnTimer++;

                if(spawnTimer >= spawnDelay)
                {
                    spawnTimer = 0;

                    String type =
                        currentWave.get(currentEnemyIndex);

                    enemies.add(createEnemy(type));

                    currentEnemyIndex++;
                }
            }
            else if(enemies.size() == 0)
            {
                wave++;
                currentEnemyIndex = 0;
            }
        }

        // Update towers
        for(Tower tower : towers)
        {
            tower.update(enemies);
        }

        // Remove dead enemies
        for(int i = enemies.size() - 1; i >= 0; i--)
        {
            Enemy enemy = enemies.get(i);

            if(enemy.health <= 0)
            {
                money += 10;

                enemies.remove(i);
            }
        }
    }

    private void drawRoad(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        // Road base
        g2.setColor(Color.DARK_GRAY);

        g2.setStroke(new BasicStroke(24));

        for(int i = 0; i < path.length - 1; i++)
        {
            g2.drawLine(
                path[i].x,
                path[i].y,
                path[i + 1].x,
                path[i + 1].y
            );
        }

        // Yellow dashed center line
        g2.setColor(Color.YELLOW);

        g2.setStroke(
            new BasicStroke(
                3,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL,
                0,
                new float[]{10},
                0
            )
        );

        for(int i = 0; i < path.length - 1; i++)
        {
            g2.drawLine(
                path[i].x,
                path[i].y,
                path[i + 1].x,
                path[i + 1].y
            );
        }

        // Reset stroke
        g2.setStroke(new BasicStroke(1));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (Game.State == Game.STATE.MENU)
        {
            startScreen.render(g);
        }
        else 
        {
            // Draw grass tiles
            for(int row = 0; row < ROWS; row++)
            {
                for(int col = 0; col < COLS; col++)
                {
                    g.setColor(Color.GREEN);
    
                    g.fillRect(
                        col * TILE_SIZE,
                        row * TILE_SIZE,
                        TILE_SIZE,
                        TILE_SIZE
                    );
    
                    g.setColor(Color.BLACK);
    
                    g.drawRect(
                        col * TILE_SIZE,
                        row * TILE_SIZE,
                        TILE_SIZE,
                        TILE_SIZE
                    );
                }
            }
    
            // Draw road
            drawRoad(g);
    
            // Draw enemies
            for(Enemy enemy : enemies)
            {
                enemy.draw(g);
    
                // Health bar background
                g.setColor(Color.RED);
    
                g.fillRect(
                    (int)enemy.x - 8,
                    (int)enemy.y - 16,
                    16,
                    4
                );
    
                // Health bar
                g.setColor(Color.GREEN);
    
                int healthWidth =
                    (int)((enemy.health /
                    (double)enemy.maxHealth) * 16);
    
                g.fillRect(
                    (int)enemy.x - 8,
                    (int)enemy.y - 16,
                    healthWidth,
                    4
                );
            }
    
            // Draw towers
            for(Tower tower : towers)
            {
                tower.draw(g);
            }
    
            // Selected tower range
            if(selectedTower != null)
            {
                g.setColor(Color.WHITE);
    
                int radius =
                    selectedTower.getRange() * TILE_SIZE;
    
                g.drawOval(
                    selectedTower.getX() + 12 - radius,
                    selectedTower.getY() + 12 - radius,
                    radius * 2,
                    radius * 2
                );
            }
    
            // UI background
            g.setColor(Color.WHITE);
    
            g.fillRect(0, 0, 4 * 32, 3 * 32);
    
            // UI text
            g.setColor(Color.BLACK);
    
            g.drawString("Money: " + money, 10, 20);
            g.drawString("Lives: " + lives, 10, 40);
            g.drawString("Wave: " + wave, 10, 60);
        }
    }
    private boolean towerExists(int row, int col)
    {
        for(Tower tower : towers)
        {
            int towerRow =
                tower.getY() / TILE_SIZE;

            int towerCol =
                tower.getX() / TILE_SIZE;

            if(towerRow == row &&
               towerCol == col)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (Game.State == Game.STATE.MENU)
        {
            Game.State = Game.STATE.GAME;
            repaint();
            return;
        }
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Select existing tower
        for(Tower tower : towers)
        {
            if(mouseX >= tower.getX() &&
               mouseX <= tower.getX() + 24 &&
               mouseY >= tower.getY() &&
               mouseY <= tower.getY() + 24)
            {
                selectedTower = tower;

                return;
            }
        }

        int col = mouseX / TILE_SIZE;
        int row = mouseY / TILE_SIZE;

        // Bounds check
        if(row < 0 || row >= ROWS ||
           col < 0 || col >= COLS)
        {
            return;
        }

        // Place tower
        if(!isOnPath(mouseX, mouseY) &&
        !towerExists(row, col) &&
        money >= 10)
        {
            towers.add(
                new Tower(
                    1,
                    col * TILE_SIZE + 4,
                    row * TILE_SIZE + 4
                )
            );

            money -= 10;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    private int getRandomInt(int min, int max)
    {
        return random.nextInt(max - min + 1) + min;
    }

    private Color getRandomColor()
    {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        return new Color(r, g, b);
    }
}
