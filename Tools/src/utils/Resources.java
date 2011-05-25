/**
 * @Description : Pour ouvrir une image en ressource interne à l'exécutable
 * 
 * @Auteur : Damien
 * @Date   : 22/07/2010
 * ----------------------------------------
 * Modifications :
 **/

package utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;

public class Resources {
	/** Pour ouvrir une ImageIcon en ressource interne à l'exécutable
	 * @param adresse : adresse de l'image (avec des "/")
	 * @param c       : le nom de class principale (qui contient l'image)
	 * @return        : l'image
	 */
	public static ImageIcon getImageIcon(String adresse,Class<?> c) {
		// Normalisation de l'adresse
		adresse=adresse.replace("/", File.separator);
		adresse=adresse.replace("\\", File.separator);
		// Ressource
		ImageIcon img = null;
		try {
			img = new ImageIcon(c.getResource(adresse));
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		return img;
	}
	/** Pour ouvrir une Image en ressource interne à l'exécutable
	 * @param adresse : adresse de l'image (avec des "/")
	 * @param c       : le nom de class principale (qui contient l'image)
	 * @return        : l'image
	 */
	public static Image getImage(String adresse,Class<?> c) {
		// Normalisation de l'adresse
		adresse=adresse.replace("/", File.separator);
		adresse=adresse.replace("\\", File.separator);
		// Ressource
		Image img = null;
		try {
			img = Toolkit.getDefaultToolkit().getImage(c.getResource(adresse));
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		return img;
	}
}