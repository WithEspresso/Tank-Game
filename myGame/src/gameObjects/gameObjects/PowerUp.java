package src.gameObjects.gameObjects;

import myGame.engine.Game;

import java.awt.*;

import static myGame.engine.Game.checkBounds;

public class PowerUp extends GameObject
{
    public PowerUp(int x, int y, ObjectId id)
    {
        super(x, y, id);
        //todo: RNG AN INITIAL ANGLE AND WE GONE BOUNCE
    }

    @Override
    public void tick()
    {
        this.x = checkBounds((this.x + dx), 0, Game.RIGHTSIDE);
        this.y = checkBounds((this.y + dy), 0, Game.HEIGHT);
    }

    public void takeDamage(int damage)
    {
    }

    @Override
    public void render(Graphics g)
    {
    }
}
