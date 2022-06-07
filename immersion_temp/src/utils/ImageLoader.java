package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * <h1>ImageLoader</h1> - <i>General image loader class</i>
 * <p>
 * <b>Includes: </b>{@link me.bug.utils.ImageLoader#load(String)}
 * <p>
 * Instantiation is required.
 * @author Bognár Miklós
 * @since 0.1
 *
 */

public class ImageLoader {
	public BufferedImage load(String url) throws IOException {
		
		/**
		 * @throws IOException
		 * @since 0.1
		 */
		BufferedImage img = null;
		img = ImageIO.read(getClass().getResource("/res/graphics/" + url));

		return img;
	}
}
