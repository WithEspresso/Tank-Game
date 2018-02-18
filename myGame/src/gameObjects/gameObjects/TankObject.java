
package src.gameObjects.gameObjects;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import static myGame.engine.Game.checkBounds;

public class TankObject extends GameObject
{
    private int width = 64;
    private int height = 64;
    private final double MAX_ANGLE_LEFT_FACING = 4.71238898038469;
    private final double MIN_ANGLE_LEFT_FACING = 1.8325957145940455;
    private final double MAX_ANGLE_RIGHT_FACING = -1.5707963267948961;
    private final double MIN_ANGLE_RIGHT_FACING = 0.7853981633974481;

    private Boolean isFacingLeft = false;
    private Boolean hasTakenDamage = false;
    private boolean lastWasLeft = false;

    private int shoot_cooldown = 0;
    private int jump_cooldown = 0;

    private int GRAVITY = 2;
    private final int MAX_SPEED = 5;
    private final int MAX_HEALTH = 211;
    private final String SHOOTING_SOUND = "tank_shot";
    private final String VICTORY_SOUND = "victory";
    private final String BOOSTER_SOUND = "booster_sound";

    private int health = MAX_HEALTH;
    private double angle = 0;

    private ObjectId boosterId;
    private ObjectHandler handler;
    private ObjectId bulletId;
    private Color tankColor;
    private Color thrustColor;
    
    
    public TankObject(int x, int y, ObjectHandler handler, ObjectId id) 
    {
        super(x, y, id);
        this.handler = handler;
        if(id == ObjectId.Tank1)
        {
            this.tankColor = Color.red;
            this.thrustColor = Color.pink;
            this.bulletId = ObjectId.Player1Bullet;
            this.boosterId = ObjectId.RedBooster;
        }
        else
        {
            this.tankColor = Color.BLUE;
            this.thrustColor = Color.cyan;
            this.bulletId = ObjectId.Player2Bullet;
            this.boosterId = ObjectId.BlueBooster;
        }
        setSize(width, height);
        setSecondaryID(SecondaryID.Player);
    }

    /*
    * Changes the angle of the cannon. Up/down keys increment it by Pi/12.
    * @param    Angle in radians
    * @return   none
    * */
    public void setAngle(double dr)
    {
        /*
        if(isFacingLeft) {
            if (angle > MAX_ANGLE_LEFT_FACING)
                angle = MAX_ANGLE_LEFT_FACING;
            if (angle < MIN_ANGLE_LEFT_FACING)
                angle = MIN_ANGLE_LEFT_FACING;
        }
        else
        {
            if (angle > MAX_ANGLE_RIGHT_FACING)
                angle = MAX_ANGLE_RIGHT_FACING;
            if (angle < MIN_ANGLE_RIGHT_FACING)
                angle = MIN_ANGLE_RIGHT_FACING;
        }
         */

        angle = angle + dr;
        System.out.println("Current angle in radians: " + angle);
    }
    
    
    /*
    * Checks to see if the bullet cooldown has expired. If the cooldown is done, allows the player to shoot a bullet
    * and resets the cooldowm.
    * @param    none
    * @return   none
    * */
    public void shootCheck()
    {
        if(shoot_cooldown < 1)
        {
            shootBullet();
            shoot_cooldown = 15;
        }
    }

    /*
    *   Checks to see if the player is currently facing left or right, then
    *   generates a bullet in front of the player. Adds a PI to the angle
    *   if the player is facing left and was previously facing right.
    *   @param  none
    *   @return none
    * */
    private void shootBullet()
    {
        int rightShootOffset = this.getWidth() + 16;
        if (isFacingLeft && !lastWasLeft || !isFacingLeft && lastWasLeft)
        {
            adjustCannonAngleForTurningAround();
            lastWasLeft = !(lastWasLeft);
        }
        GameObject bullet = new Bullet(getX() + rightShootOffset, getY(), this.bulletId, tankColor, angle, handler);

        handler.requestSound(SHOOTING_SOUND);
        handler.addObject(bullet);
    }

    private void adjustCannonAngleForTurningAround()
    {
        angle = Math.PI - angle;
    }

    /*
    *   Sets whether or not the tank is facing left or right.
    *   @param  A boolean value to indicate if the tank is facing left if true.
    *   @return none
    * */
    public void setFacingLeft(boolean isFacingLeft)
    {
        this.isFacingLeft = isFacingLeft;
    }

    /*
    *   Called whenever a collision with a bullet occurs. Subtracts
    *   the damage from the current health. If taking damage would result in death,
    *   calls the die function to remove this tank player from the game.
    *   @param  An integer value of damage to occur
    *   @return none
    * */
    public void takeDamage(int damage)
    {
        if(this.health - damage < 0)
            die();
        else
        {
            setHasTakenDamage(true);
            this.health -= damage;
        }
    }

    /*
    *   Used by the health bars, Returns the current health of the tank as a percentage.
    *   @param  none
    *   @return the current health of the tank in integer
    * */
    public int getHealthAsPercentage()
    {
        double percentage = ((double)this.health / MAX_HEALTH) * 100;
        return (int)percentage;
    }

    /*
    *   Removes this player object from the game, creates a new DeadTank object
    *   at this object's last coordinates. Plays a fanfare noise as well to
    *   celebrate the fact that life is short and you should cherish your loved ones.
    *   @param  none
    *   @return none
    * */
    private void die()
    {
        handler.removeObject(this);
        handler.addObject(new DeadTank(getX(),getY(), ObjectId.DeadTank, handler));
        handler.requestSound(VICTORY_SOUND);
        handler.addObject(new VictoryScreen(Game.WIDTH/4, 0, ObjectId.VictoryScreen, handler, getId()));
    }

    @Override
    public void tick()
    {
        shoot_cooldown--;
        jump_cooldown--;

        if(dy == -20)
        {
            if(jump_cooldown < 0)
            {
                jump_cooldown = 20;
                handler.addObject(new BoosterAnimation(getX() + 32, getY() + 32, boosterId, handler));
                handler.requestSound(BOOSTER_SOUND);
            }
            else
                setdy(0);
        }

        this.x = checkBounds((this.x + dx), 0, Game.RIGHTSIDE);
        this.y = checkBounds((this.y + dy), 0, Game.FLOOR);

        if(falling || jumping)
        {
            dy += GRAVITY;
            if(dy > MAX_SPEED)
            {
                dy = MAX_SPEED;
            }
        }
        checkCollision();
    }

    /*
    * Iterates through all of the gameobjects in the handler.
    * If a block or destructable block is found, stops the tank from moving.
    * If a bullet is found, the tank takes damage and the bullet is removed from the
    * object handler.
    * @param    none
    * @return   none
    * */
    private void checkCollision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ObjectId.Block || tempObject.getId() == ObjectId.DestructableBlock)
            {
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + (height / 2);
                    dy = 0;
                }
                if (getBounds().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - height;
                    dy = 0;
                    falling = false;
                    jumping = false;
                } else {
                    falling = true;
                }
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + (width / 2);
                }
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + (width / 2);
                }
            }
            if (tempObject.getSecondaryId() == SecondaryID.Bullet)
            {
                if((tempObject.getId() == ObjectId.Player1Bullet && this.getId() == ObjectId.Tank2) || (tempObject.getId() == ObjectId.Player2Bullet && this.getId() == ObjectId.Tank1))
                {
                    if (this.getBoundsRight().intersects(tempObject.getBounds()))
                    {
                        tempObject.setIsAlive(false);
                        int damage = ((Bullet) (tempObject)).getDamage();
                        this.takeDamage(damage);
                        handler.removeObject(tempObject);
                    }
                    if (this.getBoundsLeft().intersects(tempObject.getBounds()))
                    {
                        tempObject.setIsAlive(false);
                        int damage = ((Bullet) (tempObject)).getDamage();
                        this.takeDamage(damage);
                        handler.removeObject(tempObject);
                    }
                }
            }
        }
    }

    /*
    *   Determines the current state of the tank and draws the correct image accordingly at the
    *   current coordinates.
    *
    * @param    A Graphics object to draw with
    * @return   none
    * */
    @Override
    public void render(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        if (this.getIsAlive()) {
            if (this.getId() == ObjectId.Tank1) {
                if (hasTakenDamage) {
                    if (isFacingLeft) {
                        g2d.drawImage(handler.requestSprite("red_tank_left_damaged"), getX(), getY(), null);
                    } else {
                        g2d.drawImage(handler.requestSprite("red_tank_right_damaged"), getX(), getY(), null);
                    }
                    this.setHasTakenDamage();
                } else {
                    if (isFacingLeft) {
                        g2d.drawImage(handler.requestSprite("red_tank_left"), getX(), getY(), null);
                    } else {
                        g2d.drawImage(handler.requestSprite("red_tank_right"), getX(), getY(), null);
                    }
                }
            }
            else
                {
                    if (hasTakenDamage) {
                        if (isFacingLeft) {
                            g2d.drawImage(handler.requestSprite("blue_tank_left_damaged"), getX(), getY(), null);
                        } else {
                            g2d.drawImage(handler.requestSprite("blue_tank_right_damaged"), getX(), getY(), null);
                        }
                        this.setHasTakenDamage();
                    } else {
                        if (this.isFacingLeft) {
                            g2d.drawImage(handler.requestSprite("blue_tank_left"), getX(), getY(), null);
                        } else {
                            g2d.drawImage(handler.requestSprite("blue_tank_right"), getX(), getY(), null);
                        }
                    }
                }
            } else {
                g2d.drawImage(handler.requestSprite("player_death_0"), getX(), getY(), null);
            }
        }

    private int counter = 5;
    public void setHasTakenDamage()
    {
        counter--;
        if(counter == 0)
        {
            this.hasTakenDamage = false;
            counter = 5;
        }
    }

    public void setHasTakenDamage(boolean hasTakenDamage)
    {
        this.hasTakenDamage = hasTakenDamage;
        counter = 5;
    }

    /*
    * Returns the objectId of this GameObject
    * @param    none
    * @return   The objectId of this GameObject
    * */
    public ObjectId getId() 
    {
        return id;
    }

    @Override
    public Rectangle getBounds() 
    {        
        return new Rectangle((x+(width/2)),y+(height/2),width/2,height/2);
    }
    public Rectangle getBoundsRight() 
    {
        return new Rectangle(x+width-5,y+7,5,height-13);
    }
    public Rectangle getBoundsLeft() 
    {
        return new Rectangle(x,y+7,5,height-13);
    }
    public Rectangle getBoundsTop() 
    {
        return new Rectangle((x+(width/2)-(width/4)),y,width/2,height/2);
    }
}
