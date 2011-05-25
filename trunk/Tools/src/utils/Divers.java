/**
 * @Description : Divers
 * 
 * @Auteur : Damien
 * @Date   : 22/07/2010
 * ----------------------------------------
 * Modifications :
 **/

package utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Divers {
	/** Pour mettre le look définit par l'équipe
	 * @param f : Frame a mettre selon le look de la Team
	 */
	public static void setLookAndFeel(JFrame f) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(f);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	/** Pour supprimer les accents d'une chaîne de caractères
	 * @param text : Chaîne de caractère à modifier
	 * @return : Chaîne de caractère sans accents
	 */
	public static String removeAccents(String text) {
		return  Normalizer.normalize(text,Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
}