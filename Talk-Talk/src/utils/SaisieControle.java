/**
 * @Description : Saisie dans la console
 * 
 * @Auteur : Damien
 * @Date   : 22/07/2010
 * ----------------------------------------
 * Modifications :
 **/

package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SaisieControle {
	/** Saisie d'une chaîne de caractère
	 * @param message : Message a afficher avant saisie
	 * @return        : Texte saisie
	 */
	public static String saisieString(String message) {
		Scanner scanner=new Scanner(System.in);
		String texte;
		scanner.useDelimiter("\n");			// pour utiliser \n comme délimiteur
		System.out.print(message+" : ");
		texte=scanner.next();
		return texte;
	}
	/** Saisie d'un caractère
	 * @param message : Message a afficher avant saisie
	 * @return        : Caractère saisie
	 */
	public static char saisieChar(String message) {
		Scanner scanner=new Scanner(System.in);
		char car;
		System.out.print(message+" : ");
		car=scanner.next().charAt(0);
		return car;
	}
	/** Saisie d'un nombre entier
	 * @param message : Message a afficher avant saisie
	 * @return        : Nombre saisie
	 */
	public static int saisieInt(String message) {
		return saisieInt(message,-1,-1);
	}
	/** Saisie d'un nombre entier avec minimum imposé
	 * @param message : Message a afficher avant saisie
	 * @param min     : Valeur minimal
	 * @return        : Nombre saisie
	 */
	public static int saisieInt(String message,int min) {
		return saisieInt(message,min,-1);
	}
	/** Saisie d'un nombre entier avec minimum et maximum imposé
	 * @param message : Message a afficher avant saisie
	 * @param min     : Valeur minimal
	 * @param max     : Valeur maximal
	 * @return        : Nombre saisie
	 */
	public static int saisieInt(String message,int min,int max) {
		Scanner scanner=new Scanner(System.in);
		int val=-1;
		while (val<0) {
			System.out.print(message+" : ");
			try {
				val=scanner.nextInt();
				if ( (val<min&&min!=-1) || (val>max&&max!=-1) ) {
					System.out.println("\tSaisie non conforme (hors borne) ! Rentrer une nouvelle valeur!");
					val=-2;
				}
			} catch(InputMismatchException e) {
				System.out.println("\tSaisie non conforme (pas un nombre) ! Rentrer une nouvelle valeur!");
				val=-3;
				scanner.next();
			}
		}
		return val;
	}
}