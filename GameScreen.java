package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameScreen extends JPanel implements MouseListener
{
    public static final int TILE_SIZE = 32;
    public static final int ROWS = 20;
    public static final int COLS = 20;

    public static final int SHOP_HEIGHT =
        TILE_SIZE * 3;

    private final int SHOP_TILE_SIZE =
        (int)(TILE_SIZE * 2.5);

    private Random random;

    private Image schoolImage;
    private Image fieldImage;

    private boolean placingTower = false;
    private int selectedTowerType = 1;

    private StartScreen startScreen = new StartScreen();
    private EndScreen endScreen = new EndScreen();

    // Road path
    Point[] path = {

        new Point(16, 336 + SHOP_HEIGHT),

        new Point(176, 336 + SHOP_HEIGHT),
        new Point(176, 144 + SHOP_HEIGHT),

        new Point(400, 144 + SHOP_HEIGHT),
        new Point(400, 464 + SHOP_HEIGHT),

        new Point(112, 464 + SHOP_HEIGHT),
        new Point(112, 208 + SHOP_HEIGHT),

        new Point(528, 208 + SHOP_HEIGHT),
        new Point(528, 528 + SHOP_HEIGHT),

        new Point(304, 528 + SHOP_HEIGHT),
        new Point(304, 336 + SHOP_HEIGHT),

        new Point(688, 336 + SHOP_HEIGHT)
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

        loadImages();

        for(int i = 0; i < COLS; i++)
        {
            map[10][i] = 1;
        }

        setupWaves();

        addMouseListener(this);

        setFocusable(true);
        requestFocusInWindow();

        Timer timer = new Timer(16, e -> {

            if(Game.State == Game.STATE.GAME)
            {
                update();
            }

            repaint();
        });

        timer.start();
    }

    private void loadImages()
    {
        try
        {
            URL imageURL =
                getClass().getResource("classroom.png");

            if(imageURL != null)
            {
                schoolImage =
                    new ImageIcon(imageURL).getImage();
            }

            URL fieldURL =
                getClass().getResource("field.png");

            if(fieldURL != null)
            {
                fieldImage =
                    new ImageIcon(fieldURL).getImage();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void drawShop(Graphics g)
    {
        g.setColor(Color.DARK_GRAY);

        g.fillRect(
            0,
            0,
            getWidth(),
            SHOP_HEIGHT
        );

        for(int i = 0; i < 5; i++)
        {
            int x =
                130 + 10 + i * (SHOP_TILE_SIZE + 10);

            int y = 10;

            g.setColor(Color.GRAY);

            g.fillRect(
                x,
                y,
                SHOP_TILE_SIZE,
                SHOP_TILE_SIZE
            );

            g.setColor(Color.BLACK);

            g.drawRect(
                x,
                y,
                SHOP_TILE_SIZE,
                SHOP_TILE_SIZE
            );

            Tower preview =
                new Tower(i + 1, x + 28, y + 28);

            preview.draw(g);
        }
    }

    private void setupWaves()
    {
        ArrayList<String> wave0 = new ArrayList<>();
        wave0.add("base");

        ArrayList<String> wave1 = new ArrayList<>();
        wave1.add("base");
        wave1.add("base");
        wave1.add("fast");

        ArrayList<String> wave2 = new ArrayList<>();
        wave2.add("tank");
        wave2.add("fast");
        wave2.add("fast");
        wave2.add("base");

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
        for(int i = enemies.size() - 1; i >= 0; i--)
        {
            Enemy enemy = enemies.get(i);

            enemy.update();

            enemy.progress = enemy.x;

            if(enemy.reachedEnd())
            {
                enemies.remove(i);

                lives--;

                money -= 5;

                if(lives <= 0)
                {
                    Game.State = Game.STATE.END;
                }
            }
        }

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

        for(Tower tower : towers)
        {
            tower.update(enemies);
        }

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

        g2.setStroke(new BasicStroke(1));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if(Game.State == Game.STATE.MENU)
        {
            startScreen.render(g);
            return;
        }

        if(Game.State == Game.STATE.END)
        {
            endScreen.render(g);
            return;
        }

        drawShop(g);

        for(int row = 0; row < ROWS; row++)
        {
            for(int col = 0; col < COLS; col++)
            {
                g.setColor(Color.GREEN);

                g.fillRect(
                    col * TILE_SIZE,
                    row * TILE_SIZE + SHOP_HEIGHT,
                    TILE_SIZE,
                    TILE_SIZE
                );

                g.setColor(Color.BLACK);

                g.drawRect(
                    col * TILE_SIZE,
                    row * TILE_SIZE + SHOP_HEIGHT,
                    TILE_SIZE,
                    TILE_SIZE
                );
            }
        }

        g.drawImage(
            schoolImage,
            14 * TILE_SIZE,
            2 * TILE_SIZE + SHOP_HEIGHT,
            4 * TILE_SIZE,
            3 * TILE_SIZE,
            null
        );

        g.drawImage(
            fieldImage,
            1 * TILE_SIZE,
            15 * TILE_SIZE + SHOP_HEIGHT,
            7 * TILE_SIZE,
            4 * TILE_SIZE,
            null
        );

        drawRoad(g);

        for(Enemy enemy : enemies)
        {
            enemy.draw(g);

            g.setColor(Color.RED);

            g.fillRect(
                (int)enemy.x - 8,
                (int)enemy.y - 16,
                16,
                4
            );

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

        for(Tower tower : towers)
        {
            tower.draw(g);
        }

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

        if(placingTower)
        {
            Point mouse =
                getMousePosition();

            if(mouse != null)
            {
                Graphics2D g2 =
                    (Graphics2D) g;

                g2.setComposite(
                    AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER,
                        0.5f
                    )
                );

                Tower ghost =
                    new Tower(
                        selectedTowerType,
                        mouse.x - 12,
                        mouse.y - 12
                    );

                ghost.draw(g);

                g2.setComposite(
                    AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER,
                        1f
                    )
                );
            }
        }

        g.setColor(Color.WHITE);

        g.fillRect(0, 0, 140, 80);

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
                (tower.getY() - SHOP_HEIGHT) / TILE_SIZE;

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
        if(Game.State == Game.STATE.MENU)
        {
            Game.State = Game.STATE.GAME;
            repaint();
            return;
        }

        int mouseX = e.getX();
        int mouseY = e.getY();

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

        for(int i = 0; i < 5; i++)
        {
            int x =
                140 + i * (SHOP_TILE_SIZE + 10);

            int y = 10;

            if(mouseX >= x &&
               mouseX <= x + SHOP_TILE_SIZE &&
               mouseY >= y &&
               mouseY <= y + SHOP_TILE_SIZE)
            {
                selectedTowerType = i + 1;

                placingTower = true;

                return;
            }
        }

        if(!placingTower)
        {
            return;
        }

        int col = mouseX / TILE_SIZE;

        int row =
            (mouseY - SHOP_HEIGHT) / TILE_SIZE;

        if(row < 0 || row >= ROWS ||
           col < 0 || col >= COLS)
        {
            return;
        }

        if(!isOnPath(mouseX, mouseY) &&
           !towerExists(row, col) &&
           money >= 10)
        {
            towers.add(
                new Tower(
                    selectedTowerType,
                    col * TILE_SIZE + 4,
                    row * TILE_SIZE + SHOP_HEIGHT + 4
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
