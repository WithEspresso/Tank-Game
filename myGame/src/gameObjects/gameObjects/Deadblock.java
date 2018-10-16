package src.gameObjects.gameObjects;

import src.gameObjects.theVisuals.SpriteManager;

import java.awt.*;

public class Deadblock extends GameObject
{
    private int animationCounter = 30;
    private ObjectHandler handler;
    private String spriteKey = "dead_block";
    private String message = "*boom*";
    private String fontName = "unicode_sans";
    private int fontSize = 20;

    private final int WIDTH = 32;
    private final int HEIGHT = 32;

    public Deadblock(int x, int y, ObjectId id, ObjectHandler handler)
    {
        super(x, y, id);
        this.handler = handler;
        setdy(-2);
        setdx(2);
        setSize(WIDTH, HEIGHT);
    }

    public void takeDamage(int damage)
    {
    }

    @Override
    public void tick()
    {
        setY(getY() + getdy());
        if(animationCounter < 15)
            this.setdx(-2);
        setX(getX() + getdx());

        animationCounter--;
        if(animationCounter <= 0)
            handler.removeObject(this);
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.pink);
        g.setFont(new Font(fontName, Font.PLAIN, fontSize));
        g.drawString(message, getX(), getY());

        g.setColor(Color.white);
        g.drawString(message, getX() + 1, getY());
        //g.drawString(message, getX() * 1, getY());
        /*
        g.drawImage(handler.requestSprite("dead_block"), getX(), getY() + 20, null);
        g.drawImage(handler.requestSprite("dead_block"), getX() * -1, getY(), null);
        g.drawImage(handler.requestSprite("dead_block"), getX() - 10, getY(), null);
        */
    }
}
