package src.gameObjects.gameObjects;

import java.awt.*;

public class BoosterAnimation extends GameObject
{
    private ObjectHandler handler;
    private int animationCounter = 5;
    private String spriteKey;
    private final int WIDTH = 32;
    private final int HEIGHT = 16;

    public BoosterAnimation(int x, int y, ObjectId id, ObjectHandler handler)
    {
        super(x, y, id);
        this.handler = handler;
        if(id == ObjectId.RedBooster)
            spriteKey = "red_boosters";
        else
            spriteKey = "blue_boosters";
        this.setSize(WIDTH, HEIGHT);
    }

    @Override
    public void tick()
    {
        animationCounter--;
        if(animationCounter == 0)
            handler.removeObject(this);
    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(handler.requestSprite(spriteKey), getX(), getY(), null);
    }

    public void takeDamage(int damage)
    {
    }
}
