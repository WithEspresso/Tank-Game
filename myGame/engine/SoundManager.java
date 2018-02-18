package myGame.engine;

import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;

public class SoundManager
{
    private static String workingDirectory = System.getProperty("user.dir");
    private static String MUSICPATH = workingDirectory + "\\game\\src\\sounds\\";
    private static HashMap soundPathMap;

    private static String[] keys = new String[]
            {"background_music",
                    "explosion",
                    "victory",
                    "tank_shot",
                    "booster_sound"
            };

    private static String[] paths = new String[]
            {"terran1.wav",
                    "Explosion_large.wav",
                    "victory.wav",
                    "tank_shot.wav",
                    "boost_noise.wav"
            };

    static
    {
        System.out.println(workingDirectory);
        soundPathMap = new HashMap<String, String>();
        int size = keys.length;
        for(int i = 0; i < size; i++)
        {
            String fullPath = MUSICPATH + keys[i];
            soundPathMap.put(keys[i], paths[i]);
        }
    }

    /*
    * Given a key, will use that key to lookup a sound in the static HashMap
    * and play that sound. We're assuming that all keys exist in the hashmap
    * @param    The key corresponding to the desired sound
    * @reutrn   None, but a sound will be played on a new thread.
    * */
    public void playMusic(String key)
    {
        String fullPath = MUSICPATH + soundPathMap.get(key);
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fullPath));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e)
                {
                    System.out.println("Error: Unable to play sound for: " + fullPath);
                }
            }
        }).start();
    }
}
