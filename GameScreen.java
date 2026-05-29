package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
    public static final int TILE_SIZE = 40;

    // 1440 / 40
    public static final int COLS = 36;

    // Fits vertically inside 900 height
    public static final int ROWS = 19;

    public static final int SHOP_HEIGHT = TILE_SIZE * 3;

    private final int SHOP_TILE_SIZE = (int)(TILE_SIZE * 2.5);

    private Random random;

    private Image schoolImage;
    private Image fieldImage;
    
    //pausein and ff functions 
    private boolean paused = false;
    private boolean betweenWaves = true;
    private int gameSpeed = 1;
    private boolean gameStarted = false;
    private boolean placingTower = false;
    private int selectedTowerType = 1;

    //The various displays
    private StartScreen startScreen = new StartScreen();
    private EndScreen endScreen = new EndScreen();
    private WinScreen winScreen = new WinScreen();
    

    
    // Looping path with upward start
    Point[] path = {

    //  middle-left
    new Point(0, 460 + SHOP_HEIGHT),

    // Go upward first
    new Point(300, 460 + SHOP_HEIGHT),

    new Point(300, 220 + SHOP_HEIGHT),

    //  right
    new Point(660, 220 + SHOP_HEIGHT),

    //  down
    new Point(660, 660 + SHOP_HEIGHT),

    //  right
    new Point(1180, 660 + SHOP_HEIGHT),

    // Go back up
    new Point(1180, 320 + SHOP_HEIGHT),

    // Go back up
    new Point(1180, 320 + SHOP_HEIGHT),

    // Back left
    new Point(820, 320 + SHOP_HEIGHT),

    // Down again
    new Point(820, 500 + SHOP_HEIGHT),

    // Final exit
    new Point(1440, 500 + SHOP_HEIGHT)
    };

    private ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Tower> towers = new ArrayList<>();

    private ArrayList<ArrayList<String>> waves = new ArrayList<>();

    private int[][] map = new int[ROWS][COLS];

    private int money = 80;
    private int lives = 10;
    private int waveBonus = 20;
    private int wave = 0;
    private int currentEnemyIndex = 0;

    private int spawnTimer = 0;
    private int spawnDelay = 60;

    private Tower selectedTower = null;
    
    public GameScreen()
    {
        random = new Random();

        loadImages();

        setupWaves();

        addMouseListener(this);

        setFocusable(true);

        requestFocusInWindow();

        Timer timer = new Timer(16, e -> 
        {
        if(Game.State == Game.STATE.GAME)
        {
            if(!paused)
            {
                if(!betweenWaves)
                {
                    for(int i = 0; i < gameSpeed; i++)
                    {
                            update();
                    }
                }
            }
        }   
        repaint();
        });
        timer.start();
    }

    private void loadImages()
    {
        try
        {
            URL imageURL = getClass().getResource("classroom.png");

            if(imageURL != null)
            {
                schoolImage = new ImageIcon(imageURL).getImage();
            }

            URL fieldURL = getClass().getResource("field.png");

            if(fieldURL != null)
            {
                fieldImage = new ImageIcon(fieldURL).getImage();
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
        g.fillRect(0, 0, getWidth(), SHOP_HEIGHT);
    
        for(int i = 0; i < 5; i++)
        {
            int x = 160 + i * (SHOP_TILE_SIZE + 30);
            int y = 10;
    
            g.setColor(Color.GRAY);
            g.fillRect(x, y, SHOP_TILE_SIZE, SHOP_TILE_SIZE);
    
            g.setColor(Color.BLACK);
            g.drawRect(x, y, SHOP_TILE_SIZE, SHOP_TILE_SIZE);
    
            Tower shopTower = new Tower(i + 1, x + 34, y + 34);
    
            shopTower.draw(g);
    
            g.setColor(Color.WHITE);
            g.drawString("$" + shopTower.getCost(), x + 80, y + SHOP_TILE_SIZE + 10);
            g.drawString(shopTower.getName(), x, y + SHOP_TILE_SIZE + 10);
        }
    }
    private void setupWaves()
    {
        ArrayList<String> wave0 = new ArrayList<>();
        wave0.add("base");
        wave0.add("base");
        wave0.add("base");

        
        ArrayList<String> wave1 = new ArrayList<>();
        wave1.add("base");
        wave1.add("base");
        wave1.add("base");
        wave1.add("base");
        wave1.add("base");
    
        ArrayList<String> wave2 = new ArrayList<>();
        wave2.add("base");
        wave2.add("fast");
        wave2.add("base");
        wave2.add("fast");
        wave2.add("base");
        wave2.add("base");
        wave2.add("base");
    
        ArrayList<String> wave3 = new ArrayList<>();
        wave3.add("base");
        wave3.add("fast");
        wave3.add("fast");
        wave3.add("base");
        wave3.add("tank");
    
        ArrayList<String> wave4 = new ArrayList<>();
        wave4.add("base");
        wave4.add("tank");
        wave4.add("fast");
        wave4.add("tank");
        wave4.add("base");
        wave4.add("tank");
        
    
        ArrayList<String> wave5 = new ArrayList<>();
        wave5.add("base");
        wave5.add("camo");
        wave5.add("fast");
        wave5.add("base");
        wave5.add("camo");
        wave5.add("tank");

    
        ArrayList<String> wave6 = new ArrayList<>();
        wave6.add("base");
        wave6.add("base");
        wave6.add("base");
        wave6.add("base");
        wave6.add("base");
        wave6.add("camo");
        wave6.add("fast");
        wave6.add("tank");
        wave6.add("tank");
        wave6.add("tank");
        wave6.add("camo");
    
        ArrayList<String> wave7 = new ArrayList<>();
        wave7.add("fast");
        wave7.add("fast");
        wave7.add("camo");
        wave7.add("camo");
        wave7.add("fast");
        wave7.add("camo");
        wave7.add("tank");
        wave7.add("tank");
        wave7.add("tank");

        
        ArrayList<String> wave8 = new ArrayList<>();
        wave8.add("base");
        wave8.add("base");
        wave8.add("base");
        wave8.add("base");
        wave8.add("fast");
        wave8.add("fast");
        wave8.add("fast");
        wave8.add("fast");
        wave8.add("camo");
        wave8.add("camo");
        wave8.add("camo");
        wave8.add("camo");
        wave8.add("camo");
        wave8.add("tank");
        wave8.add("tank");
        wave8.add("tank");
        
    
        ArrayList<String> wave9 = new ArrayList<>();
        wave9.add("fast");
        wave9.add("fast");
        wave9.add("fast");
        wave9.add("base");
        wave9.add("base");
        wave9.add("camo");
        wave9.add("camo");
        wave9.add("tank");
        wave9.add("tank");
        wave9.add("tank");
        wave9.add("tank");
        wave9.add("boss");
        wave9.add("boss");
    
        ArrayList<String> wave10 = new ArrayList<>();
        wave10.add("base");
        wave10.add("base");
        wave10.add("base");
        wave10.add("base");
        wave10.add("base");
        wave10.add("tank");
        wave10.add("fast");
        wave10.add("camo");
        wave10.add("camo");
        wave10.add("fast");
        wave10.add("tank");
        wave10.add("tank");
        wave10.add("tank");
        wave10.add("tank");
        wave10.add("tank");
        wave10.add("tank");
        wave10.add("boss");
        wave10.add("boss");
        wave10.add("boss");
        wave10.add("boss");
        wave10.add("boss");

    
        waves.add(wave0);
        waves.add(wave1);
        waves.add(wave2);
        waves.add(wave3);
        waves.add(wave4);
        waves.add(wave5);
        waves.add(wave6);
        waves.add(wave7);
        waves.add(wave8);
        waves.add(wave9);
        waves.add(wave10);
    }
    private Enemy createEnemy(String type)
    {
        switch(type)
        {
            case "tank":
                return new Enemy(path, 1, 500, false, 20, "tank");
    
            case "fast":
                return new Enemy(path, 5, 80, false, 12, "fast");
    
            case "boss":
                return new Enemy(path, 0.6, 2000, false, 150, "boss");
    
            case "camo":
                return new Enemy(path, 3, 200, true, 15, "camo");
    
            default:
                return new Enemy(path, 2, 50, false, 8, "normal");
        }
    }
    private boolean isOnPath(int mouseX, int mouseY)
    {
        for(int i = 0; i < path.length - 1; i++)
        {
            Point p1 = path[i];
            Point p2 = path[i + 1];

            double distance = pointToSegmentDistance(mouseX, mouseY, p1.x, p1.y, p2.x, p2.y);

            if(distance < 30)
            {
                return true;
            }
        }
        return false;
    }
    private boolean isOnSchool(int mouseX, int mouseY)
    {
        int schoolX = 27 * TILE_SIZE;
        int schoolY = 1 * TILE_SIZE + SHOP_HEIGHT;

        int schoolWidth = 6 * TILE_SIZE;
        int schoolHeight = 5 * TILE_SIZE;

        return mouseX >= schoolX && mouseX <= schoolX + schoolWidth && mouseY >= schoolY && mouseY <= schoolY + schoolHeight;
    }
    private boolean isOnField(int mouseX, int mouseY)
    {
        int fieldX = 2 * TILE_SIZE;
        int fieldY = 13 * TILE_SIZE + SHOP_HEIGHT;

        int fieldWidth = 9 * TILE_SIZE;
        int fieldHeight = 4 * TILE_SIZE;

        return mouseX >= fieldX && mouseX <= fieldX + fieldWidth && mouseY >= fieldY && mouseY <= fieldY + fieldHeight;
    }
    private double pointToSegmentDistance(double px, double py, double x1, double y1, double x2, double y2)
    {   
        double dx = x2 - x1;
        double dy = y2 - y1;
        if(dx == 0 && dy == 0)
        {
            dx = px - x1;
            dy = py - y1;

            return Math.sqrt(dx * dx + dy * dy);
        }

        double t = ((px - x1) * dx + (py - y1) * dy) / (dx * dx + dy * dy);

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
                money -= 10;

                if(lives <= 0)
                {
                    Game.State = Game.STATE.END;
                }
            }
        }
        if(wave < waves.size() && !betweenWaves)
        {
            ArrayList<String> currentWave = waves.get(wave);

            if(currentEnemyIndex < currentWave.size())
            {
                spawnTimer++;
                if(spawnTimer >= spawnDelay)
                {
                    spawnTimer = 0;

                    String type = currentWave.get(currentEnemyIndex);

                    enemies.add(createEnemy(type));

                    currentEnemyIndex++;
                }
            }
            else if(enemies.size() == 0)
            {
                money += waveBonus;
                wave++;
                currentEnemyIndex = 0;
                waveBonus = Math.min(50, waveBonus + 2);
                if(gameSpeed == 1)
                {
                    betweenWaves = true;
                }
            }
        }
        else
        {
            Game.State = Game.STATE.WIN;
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
                money += enemy.reward;

                enemies.remove(i);
            }
        }
    }
    //draws start button
    private void drawStartButton(Graphics g)
    {
        if(betweenWaves)
        {
            int x = getWidth() - 180;
            int y = 50;
    
            g.setColor(Color.BLACK);
            g.fillRect(x, y, 160, 30);
    
            g.setColor(Color.WHITE);
            g.drawString("START WAVE", x + 30, y + 20);
        }
    }
    private void drawRoad(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(36));

        for(int i = 0; i < path.length - 1; i++)
        {
            g2.drawLine(path[i].x, path[i].y, path[i + 1].x, path[i + 1].y);
        }

        g2.setColor(Color.YELLOW);

        g2.setStroke(new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{15}, 0));

        for(int i = 0; i < path.length - 1; i++)
        {
            g2.drawLine(path[i].x, path[i].y, path[i + 1].x, path[i + 1].y);
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
        if(Game.State == Game.STATE.WIN)
        {
            winScreen.render(g);
            return;
        }

        drawShop(g);

        // Grid
        for(int row = 0; row < ROWS; row++)
        {
            for(int col = 0; col < COLS; col++)
            {
                g.setColor(new Color(50, 170, 50));

                g.fillRect(col * TILE_SIZE, row * TILE_SIZE + SHOP_HEIGHT, TILE_SIZE, TILE_SIZE);

                g.setColor(Color.BLACK);

                g.drawRect(col * TILE_SIZE, row * TILE_SIZE + SHOP_HEIGHT, TILE_SIZE, TILE_SIZE);
            }
        }

        // School image
        g.drawImage(schoolImage, 27 * TILE_SIZE, 1 * TILE_SIZE + SHOP_HEIGHT, 6 * TILE_SIZE, 5 * TILE_SIZE, null);
        
        // Field image
        g.drawImage(fieldImage, 2 * TILE_SIZE, 13 * TILE_SIZE + SHOP_HEIGHT, 9 * TILE_SIZE, 4 * TILE_SIZE, null);
        
        drawRoad(g);
        drawButtons(g);
        
        // Draw enemies
        for(Enemy enemy : enemies)
        {
            enemy.draw(g);
        
            g.setColor(Color.RED);
            g.fillRect((int)enemy.x - 16, (int)enemy.y - 24, 32, 6);
        
            g.setColor(Color.GREEN);
        
            int healthWidth = (int)((enemy.health / (double)enemy.maxHealth) * 32);
        
            g.fillRect((int)enemy.x - 16, (int)enemy.y - 24, healthWidth, 6);
        }
        
        // Towers
        for(Tower tower : towers)
        {
            tower.draw(g);
        }
        
        // Selected tower range
        if(selectedTower != null)
        {
            g.setColor(Color.WHITE);
        
            int radius = selectedTower.getRange() * TILE_SIZE;
        
            g.drawOval(selectedTower.getX() + 16 - radius, selectedTower.getY() + 16 - radius, radius * 2, radius * 2);
        }
        
        // Ghost tower
        if(placingTower)
        {
            Point mouse = getMousePosition();       
            if(mouse != null)
            {
                Graphics2D g2 = (Graphics2D) g;
        
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        
                Tower ghost = new Tower(selectedTowerType, mouse.x - 16, mouse.y - 16);
        
                ghost.draw(g);
        
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }
        }       
        if(placingTower)
        {
            Point mouse = getMousePosition();

            if(mouse != null)
            {
                g.setColor(new Color(255, 255, 255, 120));

                Tower previewTower =
                    new Tower(selectedTowerType, mouse.x, mouse.y);

                int previewRange =
                    previewTower.getRange() * TILE_SIZE;

                g.drawOval(
                    mouse.x - previewRange,
                    mouse.y - previewRange,
                    previewRange * 2,
                    previewRange * 2
                );
            }
        }
        
        // Stats box
        g.setColor(Color.WHITE);
        g.fillRect(10, 10, 135, 95);
        
        g.setColor(Color.BLACK);
        g.drawRect(10, 10, 135, 95);
        
        g.setFont(new Font("Arial", Font.BOLD, 20));
        
        g.drawString("Money: " + money, 20, 38);
        g.drawString("Lives: " + lives, 20, 63);
        g.drawString("Wave: " + wave, 20, 88);
        
        drawStartButton(g);
    }
    private boolean towerExists(int row, int col)
    {
        for(Tower tower : towers)
        {
            int towerRow = (tower.getY() - SHOP_HEIGHT) / TILE_SIZE;
            int towerCol = tower.getX() / TILE_SIZE;

            if(towerRow == row && towerCol == col)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        boolean clickedTower = false;
        if (Game.State == Game.STATE.MENU)
        {
            Game.State = Game.STATE.GAME;

            repaint();

            return;
        }

        int mouseX = e.getX();
        int mouseY = e.getY();

        int shopX = getWidth() - 320;
        int shopY = 10;

        // PAUSE BUTTON
        if (mouseX >= shopX && mouseX <= shopX + 100 && mouseY >= shopY && mouseY <= shopY + 30)
        {
            paused = !paused;
            return;
        }
        
        // FAST FORWARD BUTTON
        if (mouseX >= shopX + 110 && mouseX <= shopX + 190 && mouseY >= shopY && mouseY <= shopY + 30)
        {
            gameSpeed++;
        
            if(gameSpeed > 3)
            {
                gameSpeed = 1;
            }    
            return;
        }
        
        // START WAVE BUTTON
        int startX = getWidth() - 180;
        int startY = 50;
        
        if (betweenWaves && mouseX >= startX && mouseX <= startX + 160 && mouseY >= startY && mouseY <= startY + 30)
        {
            betweenWaves = false;        
            gameStarted = true;
        
            spawnTimer = 0;
            currentEnemyIndex = 0;
        
            return;
        }
        
        // Select placed tower
        for(Tower tower : towers)
        {
            if (mouseX >= tower.getX() && mouseX <= tower.getX() + 32 && mouseY >= tower.getY() && mouseY <= tower.getY() + 32)
            {
                selectedTower = tower;
                clickedTower = true;
                return;
            }
        }
        
        // Shop selection
        for(int i = 0; i < 5; i++)
        {
            int x = 160 + i * (SHOP_TILE_SIZE + 30);
        
            int y = 10;
        
            if (mouseX >= x && mouseX <= x + SHOP_TILE_SIZE && mouseY >= y && mouseY <= y + SHOP_TILE_SIZE)
            {
                // Clicking same tower again cancels placement
                if (placingTower && selectedTowerType == i + 1)
                {
                    placingTower = false;
                }
                else
                {
                    // Switch to different tower
                    selectedTowerType = i + 1;
                    placingTower = true;
                }
            
                selectedTower = null;
                return;
            }
        }
        
        boolean clickedUI = clickedTower || placingTower || mouseY <= SHOP_HEIGHT;
        
        if (!clickedUI)
        {
            selectedTower = null;
        }       
        
        if (!placingTower)
        {
            return;
        }

        int col = mouseX / TILE_SIZE;

        int row = (mouseY - SHOP_HEIGHT) / TILE_SIZE;

        if (row < 0 || row >= ROWS || col < 0 || col >= COLS)
        {
            return;
        }

        Tower newTower = new Tower(selectedTowerType, col * TILE_SIZE + 4, row * TILE_SIZE + SHOP_HEIGHT + 4);
    
        if (!isOnPath(mouseX, mouseY) && !isOnSchool(mouseX, mouseY) && !isOnField(mouseX, mouseY) && !towerExists(row, col) && money >= newTower.getCost())
        {
            towers.add(newTower);
            
            money -= newTower.getCost();
            
            selectedTower = null;
            placingTower = false;
        }
    }
    
    private void drawButtons(Graphics g)
    {
        if(!gameStarted)
        {
            return;
        }

        int shopX = getWidth() - 320;
        int shopY = 10;
    
        // PAUSE (always visible)
        g.setColor(Color.BLACK);
        g.fillRect(shopX, shopY, 100, 30);
    
        g.setColor(Color.WHITE);
        g.drawString(paused ? "Resume" : "Pause", shopX + 20, shopY + 20);
    
        // FAST FORWARD (ONLY when wave is running)
        if(!betweenWaves)
        {
            g.setColor(Color.BLACK);
            g.fillRect(shopX + 110, shopY, 80, 30);
    
            g.setColor(Color.WHITE);
            g.drawString("x" + gameSpeed, shopX + 125, shopY + 20);
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
