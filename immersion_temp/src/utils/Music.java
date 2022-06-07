package utils;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * <h1>Music</h1>
 * <p>
 * <b>Includes: </b>{@link me.bug.utils.Music#playSound(String)} 
 * @author Bognár Miklós
 * @since 0.1
 *
 */

public class Music {
	
	static Clip clip;
	
	public static void playSound(String url) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		/**
		 * 
		 * @throws UnsupportedAudioFileException
		 * @throws IOException
		 * @throws LineUnavailableException
		 * @since 0.1
		 */
		
		File soundFile = new File("C:/Users/User/web_dev/immersion_temp/src/res/soundfx/"+url);
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		clip=AudioSystem.getClip();
		clip.open(audioIn);
		clip.start();
	}
	
	public static Clip getMusic() {
		
		/**
		 *@since 0.1 		 
		 */
		return clip;
	}

}
