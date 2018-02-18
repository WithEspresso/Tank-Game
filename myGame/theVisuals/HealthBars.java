package myGame.theVisuals;

import myGame.engine.Game;
import myGame.gameObjects.ObjectHandler;
import myGame.gameObjects.TankObject;
import myGame.gameObjects.GameObject;
import myGame.gameObjects.ObjectId;

import java.awt.*;

public class HealthBars extends GameObject
{
    private int tank1Health = 100;
    private int tank2Health = 100;
    private final int maxHealth = 100;
    private final String PLAYER_ONE = "プレーヤー 1      P l a y e r O n e";
    private final String PLAYER_TWO = "プレーヤー 2      P l a y e r T w o";
    private final String DEAD = "おまえもう死んでる　　　　ded";
    private final int fontsize = 20;
    private final String fontName = "unicode_sans";
    private int redValue = 255;

    private final int WIDTH = (int)(Game.WIDTH * 0.90);
    private final int HEIGHT = (int)(Game.HEIGHT * 0.10);

    private int healthBarMaxWidth = 900;
    private int healthBarLastTank1 = healthBarMaxWidth;
    private int healthBarLastTank2 = healthBarMaxWidth;

    private int healthBarHeight = 20;

    private ObjectHandler objectHandler;

    public HealthBars(int x, int y, ObjectId id, ObjectHandler objectHandler)
    {
        super(x, y, id);
        this.objectHandler = objectHandler;
        this.setSize(WIDTH, HEIGHT);
    }

    @Override
    public void tick()
    {
        getHealthInfo();
    }

    private void getHealthInfo()
    {
        GameObject temp;
        healthBarLastTank1 = tank1Health;
        healthBarLastTank2 = tank2Health;
        for(int i = 0; i < objectHandler.getSize(); i++)
        {
            temp = objectHandler.getObject(i);
            if(temp.getId() == ObjectId.Tank1)
            {
                this.tank1Health = ((TankObject)(temp)).getHealthAsPercentage();
            }
            if(temp.getId() == ObjectId.Tank2)
            {
                this.tank2Health = ((TankObject)(temp)).getHealthAsPercentage();
            }
        }
    }

    public void takeDamage(int damage)
    {
    }

    @Override
    public void render(Graphics g)
    {
        //Drawing container
        int tank2HealthBarYCoordinates = 15 + getY() + healthBarHeight + 32;
        g.setColor(Color.MAGENTA);
        g.setFont(new Font(fontName, Font.PLAIN, fontsize));
        g.drawRect(getX(), getY(), (int)(Game.WIDTH*0.80), (int)(Game.HEIGHT*0.10));


        //Draw Player One's Health bar
        g.setColor(Color.RED);
        g.fillRect(getX() + 32, 15 + getY(), tank1Health * 9, healthBarHeight);

        //Check for death
        if(tank1Health == 0)
            g.drawString(DEAD, getX() + 32, 15 + getY());
        else
            g.drawString(PLAYER_ONE, getX() + 32, 15 + getY());

        //Health bar creates flash effect when damage is taken.
        if(healthBarLastTank1 != tank1Health)
        {
            g.setColor(Color.white);
            g.fillRect(getX() + 32, 15 + getY(), tank1Health * 9, healthBarHeight);
        }


        //Draw Player two's health bar
        g.setColor(Color.CYAN);
        g.fillRect(getX() + 32, tank2HealthBarYCoordinates, tank2Health * 9, healthBarHeight);

        //Check for Death
        if(tank2Health == 0)
            g.drawString(DEAD, getX() + 32, tank2HealthBarYCoordinates);
        else
            g.drawString(PLAYER_TWO, getX() + 32, tank2HealthBarYCoordinates);

        //Flashing effect when damage is taken
        if(healthBarLastTank2 != tank2Health)
        {
            g.setColor(Color.black);
            g.fillRect(getX() + 32, tank2HealthBarYCoordinates, tank2Health * 9, healthBarHeight);
        }
    }
}