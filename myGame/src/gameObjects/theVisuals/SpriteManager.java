package src.gameObjects.theVisuals;

import src.gameObjects.geometry.Vector;

import java.util.HashMap;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import javax.imageio.*;

/*
* This class is the sprite manager for the entire game. Instantiates all sprites needed
* in the game in a static hashmap before the game even runs.
* The public method getImage can be used to retrieve the existing myGame.images during runtime.
* */

public class SpriteManager
{
    //C:\Users\Danielle Nunez\Documents\TankGame\csc413-tankgame-team19-master\game\src\images\red_tank_right.png
        private static ImageIcon icon = null;
        private static Image image = null;
        private static String workingDirectory = System.getProperty("user.dir");
        private static String relativePath = "/images/";
        private static HashMap imagePathMap;
        private static HashMap imageMap;
        private static HashMap sizeMap;

        private static String[] keys = new String[]
                { "red_tank_left",
                        "red_tank_left_damaged",
                        "red_tank_right",
                        "red_tank_right_damaged",
                        "blue_tank_left",
                        "blue_tank_left_damaged",
                        "blue_tank_right",
                        "blue_tank_right_damaged",
                        "player_death_0",
                        "player_death_1",
                        "dead_block",
                        "block",
                        "destructable_block_1",
                        "destructable_block_2",
                        "destructable_block_3",
                        "blue_boosters",
                        "red_boosters",
                        "blue_tank_bullet",
                        "red_tank_bullet",
                        "player_one_loses",
                        "player_two_loses",
                        "minimap_overlay"
                };

        private static String[] paths = new String[]
                { "red_tank_left.png",
                        "red_tank_left_damaged.png",
                        "red_tank_right.png",
                        "red_tank_right_damaged.png",
                        "blue_tank_left.png",
                        "blue_tank_left_damaged.png",
                        "blue_tank_right.png",
                        "blue_tank_right_damaged.png",
                        "player_death_0.png",
                        "player_death_1.png",
                        "dead_block.png",
                        "block.png",
                        "destructable_block_1.png",
                        "destructable_block_2.png",
                        "destructable_block_3.png",
                        "blue_boosters.png",
                        "red_boosters.png",
                        "blue_tank_bullet.png",
                        "red_tank_bullet.png",
                        "player_one_loses.png",
                        "player_two_loses.png",
                        "minimapOverlay.png"
                };

        /*
        * Initializes HashMap of String full image paths and Images
        * created from the resources found at those paths.
        * This HashMap is used to load myGame.images from a given
        * key and display them.
        * */
        static
        {
            System.out.println(workingDirectory);
            System.out.println(relativePath);
            imagePathMap = new HashMap<String, String>();
            int size = keys.length;
            for(int i = 0; i < size; i++)
            {
                imagePathMap.put(keys[i], paths[i]);
            }

            imageMap = new HashMap<String, Object>();
            sizeMap = new HashMap<String, Vector>();
            String filePath = "";

            for(int i = 0; i < size; i++)
            {
                filePath = workingDirectory + relativePath + imagePathMap.get(keys[i]);
                System.out.println(filePath);
                icon = new ImageIcon(filePath);
                image = icon.getImage();
                imageMap.put(keys[i], image);

                File imageFile = new File(filePath);
                try
                {
                    BufferedImage image = ImageIO.read(imageFile);
                    Vector sizeVector = new Vector(image.getWidth(), image.getHeight());
                    sizeMap.put(keys[i], sizeVector);
                } catch (Exception ex)
                {
                    System.out.println("Cannot open: " + filePath);
                    ex.printStackTrace();
                }
            }
        }

        /*
        *   Returns a key from the HashMap initialized in the static block
        *   at the beginning of runtime.
        * @param    A string key to load an image with
        * @return   An image corresponding to the key given
        * */
        public Image getImage(String key)
        {
            return (Image)imageMap.get(key);
        }

        /*
        * Returns the size of an image in vector form given the
        * key value to look it up with.
        * */
        public static Vector getSizeVector(String key)
        {
            return (Vector)sizeMap.get(key);
        }
}
