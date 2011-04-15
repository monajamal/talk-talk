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

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Resources {
	/** Pour ouvrir une ImageIcon en ressource interne à l'exécutable
	 * @param adresse : adresse de l'image (avec des "/")
	 * @param c       : le nom de class principale (qui contient l'image)
	 * @return        : l'image
	 */
	public static ImageIcon getImageIcon(String adresse,Class<?> c) {
		
		if (adresse.contains("\\"))
			JOptionPane.showMessageDialog(null, "Attention ! Vous avez utiliser un \\ pour l'ImageIcon d'adresse : "+adresse);
		
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
		
		if (adresse.contains("\\"))
			JOptionPane.showMessageDialog(null, "Attention ! Vous avez utiliser un \\ pour l'Image d'adresse : "+adresse);
		
		Image img = null;
		try {
			img = Toolkit.getDefaultToolkit().getImage(c.getResource(adresse));
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		return img;
	}
}