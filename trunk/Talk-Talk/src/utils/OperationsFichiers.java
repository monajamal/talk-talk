/**
 * @Description : Tout ce qui est des opérations sur les fichiers
 * 
 * @Auteur : Damien
 * @Date   : 22/07/2010
 * ----------------------------------------
 * Modifications :
 **/

package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class OperationsFichiers {
	/** Pour copier un fichier (le contenu d'un fichier)
	 * @param source : fichier source
	 * @param dest   : fichier destination
	 * @return       : résultat
	 */
	public static boolean copyFile(File source, File dest) {
		try {
			// Declaration et ouverture des flux
			FileInputStream sourceFile = new FileInputStream(source);
			try {
				FileOutputStream destinationFile = null;
				
				try {
					destinationFile = new FileOutputStream(dest);
					
					// Lecture par segment de 0.5Mo 
					byte buffer[] = new byte[512 * 1024];
					int nbLecture;
					
					while ((nbLecture = sourceFile.read(buffer)) != -1) {
						destinationFile.write(buffer, 0, nbLecture);
					}
				} finally {
					destinationFile.close();
				}
			} finally {
				sourceFile.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false; // Erreur
		}
		return true; // Résultat OK  
	}
	/** Pour supprimer tout ce que contient un dossier récursivement (fichiers, dossiers)
	 * @param f : le nom du dossier
	 */
	public static void deleteContentsOfDirectory(File f) {
		File l[] = f.listFiles();
		for (int i=0;i<l.length;i++) {
			if (l[i].isDirectory()) {
				deleteContentsOfDirectory(l[i]);//Je vide le contenu
				l[i].delete();//puis le dossier
			} else
				l[i].delete();//je supprime le fichier
		}
	}
	/** Pour supprimer tout un dossier récursivement (fichiers, dossiers)
	 * @param f : le nom du dossier
	 */
	public static void deleteDirectory(File f) {
		if (f.exists()) {
			deleteContentsOfDirectory(f);
			f.delete();
		}
	}
}