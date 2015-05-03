package br.unb.idj.collisions.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Some come functionalities.
 * 
 * @author Luciano Santos
 */
public class Common {
	public static final String resourcesPath = "/br/unb/idj/collisions/resources/";

	/**
	 * Loads an internal texture, given its file name.
	 * 
	 * @param name
	 *            the file name.
	 * 
	 * @return the loaded image, or null, if can't load.
	 * 
	 * @throws IOException
	 *             if any IO error occurs;
	 */
	public static BufferedImage loadTexture(String name) throws IOException {
		String path = resourcesPath + name;
		try (InputStream input = Common.class.getResourceAsStream(path)) {
			return ImageIO.read(input);
		}
	}

	/**
	 * Loads an internal font, given its file name.
	 * 
	 * @param name
	 *            the file name.
	 * 
	 * @return the loaded font, or null, if can't load.
	 * 
	 * @throws IOException
	 *             if any IO error occurs;
	 */
	public static Font loadFont(String name) throws IOException, FontFormatException {
		String path = resourcesPath + name;
		try (InputStream input = Common.class.getResourceAsStream(path)) {
			return Font.createFont(Font.TRUETYPE_FONT, input);
		}
	}
}
