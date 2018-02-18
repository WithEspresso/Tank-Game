package myGame.gameObjects;

public abstract class PlayerObject extends GameObject
{
    int health;
    private boolean isAlive = true;

    public PlayerObject(int x, int y, ObjectId id)
    {
        super(x, y, id);
    }

    public void takeDamage(int damage)
    {
        if(this.health - damage > 0)
            this.health -= damage;
        else
        {
            die();
        }
    }

    private void die()
    {
        //handler.requestSprite("player_death");
        isAlive = false;
    }
}
