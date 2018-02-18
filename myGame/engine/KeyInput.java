package myGame.engine;

import myGame.gameObjects.TankObject;
import myGame.gameObjects.GameObject;
import myGame.gameObjects.ObjectHandler;
import myGame.gameObjects.ObjectId;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter
{
    ObjectHandler handler;
    public KeyInput(ObjectHandler handler)
    {
        this.handler = handler;
    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        for(int i=0; i<handler.object.size(); i++)
        {
            GameObject tempObject = handler.object.get(i);
            //PLAYER ONE CONTROLS
            if(tempObject.getId() == ObjectId.Tank1)
            {
                if(key == KeyEvent.VK_D)
                {
                    tempObject.setdx(5);
                    ((TankObject)tempObject).setFacingLeft(false);
                }
                if(key == KeyEvent.VK_A)
                {
                    tempObject.setdx(-5);
                    ((TankObject)tempObject).setFacingLeft(true);
                }
                if(key == KeyEvent.VK_F)
                {
                    ((TankObject)tempObject).shootCheck();
                }
                if(key == KeyEvent.VK_SPACE && !tempObject.getJumping())
                {
                    //tempObject.setJumping(true);
                    tempObject.setdy(-20);
                }
                if(key == KeyEvent.VK_W)
                {
                    ((TankObject)(tempObject)).setAngle(-Math.PI/12);
                }
                if(key == KeyEvent.VK_S)
                {
                    ((TankObject)(tempObject)).setAngle(Math.PI/12);
                }
            }
            //END PLAYER ONE CONTROLS

            //BEGIN PLAYER TWO CONTROLS
            if(tempObject.getId() == ObjectId.Tank2)
            {
                if(key == KeyEvent.VK_RIGHT)
                {
                    tempObject.setdx(5);
                    ((TankObject)tempObject).setFacingLeft(false);
                }
                if(key == KeyEvent.VK_LEFT)
                {
                    tempObject.setdx(-5);
                    ((TankObject)tempObject).setFacingLeft(true);
                }
                if(key == KeyEvent.VK_PERIOD && !tempObject.getJumping())
                {
                    //tempObject.setJumping(true);
                    tempObject.setdy(-20);
                }
                if(key == KeyEvent.VK_UP)
                {
                    ((TankObject)(tempObject)).setAngle(-Math.PI/12);
                }
                if(key == KeyEvent.VK_DOWN)
                {
                    ((TankObject)(tempObject)).setAngle(Math.PI/12);
                }
                if(key == KeyEvent.VK_P)
                {
                    ((TankObject)tempObject).shootCheck();
                }
            }
            //END PLAYER TWO CONTROLS
        }    
        if(key == KeyEvent.VK_ESCAPE)
        {
            System.exit(1);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        for(int i=0; i<handler.object.size(); i++)
        {
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ObjectId.Tank1)
            {
                if(key == KeyEvent.VK_D)
                {
                    tempObject.setdx(0);
                }
                if(key == KeyEvent.VK_A)
                {
                    tempObject.setdx(0);
                }
        }
        //END PLAYER ONE CONTROLS

        //BEGIN PLAYER TWO CONTROLS
        if(tempObject.getId() == ObjectId.Tank2)
        {
            if(key == KeyEvent.VK_RIGHT)
            {
                tempObject.setdx(0);
                ((TankObject)tempObject).setFacingLeft(false);
            }
            if(key == KeyEvent.VK_LEFT)
            {
                tempObject.setdx(0);
                ((TankObject)tempObject).setFacingLeft(true);
            }
            }
        }
        //END PLAYER TWO CONTROLS
    }
}
