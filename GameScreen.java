import java.awt.Color;
import java.awt.Graphics;
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

        // Create road
        for(int i = 0; i < COLS; i++)
        {
            map[10][i] = 1;
        }

        setupWaves();

        addMouseListener(this);

        Timer timer = new Timer(16, e -> {
            update();
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
                return new Enemy(0, 328, 0.5, 120);

            case "fast":
                return new Enemy(0, 328, 2.2, 30);

            case "boss":
                return new Enemy(0, 328, 0.3, 300);

            default:
                return new Enemy(0, 328, 1, 70);
        }
    }

    public void update()
    {
        // Update enemies
        for(int i = 0; i < enemies.size(); i++)
        {
            Enemy enemy = enemies.get(i);

            enemy.update();

            // IMPORTANT
            enemy.progress = enemy.x;

            if(enemy.x > 640)
            {
                enemies.remove(i);
                lives--;

                i--;
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
        for(int i = 0; i < enemies.size(); i++)
        {
            Enemy enemy = enemies.get(i);

            if(enemy.health <= 0)
            {
                money += 10;

                enemies.remove(i);

                i--;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Draw map
        for(int row = 0; row < ROWS; row++)
        {
            for(int col = 0; col < COLS; col++)
            {
                if(map[row][col] == 1)
                {
                    g.setColor(Color.GRAY);
                }
                else
                {
                    g.setColor(Color.GREEN);
                }

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

        // Draw enemies
        for(Enemy enemy : enemies)
        {
            enemy.draw(g);

            // Health bar background
            g.setColor(Color.RED);

            g.fillRect(
                (int)enemy.x,
                (int)enemy.y - 8,
                16,
                4
            );

            // Health bar
            g.setColor(Color.GREEN);

            int healthWidth =
                (int)((enemy.health /
                (double)enemy.maxHealth) * 16);

            g.fillRect(
                (int)enemy.x,
                (int)enemy.y - 8,
                healthWidth,
                4
            );
        }

        // Draw towers
        for(Tower tower : towers)
        {
            tower.draw(g);
        }

        // Draw selected tower range
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
<<<<<<< HEAD

        // UI
=======
        g.setColor(Color.WHITE);
        g.fillRect( 0, 0, 4*32, 1 *32);
     
>>>>>>> 87bebbaeead5fae1374660c6b2bf5b88dd3f90a9
        g.setColor(Color.BLACK);

        g.drawString("Money: " + money, 10, 20);
        g.drawString("Lives: " + lives, 10, 40);
        g.drawString("Wave: " + wave, 10, 60);
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
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Check if clicking existing tower
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
        if(map[row][col] == 0 &&
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