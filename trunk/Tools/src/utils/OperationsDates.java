/**
 * @Description : Tout ce qui est des opérations sur les dates
 * 
 * @Auteur : Damien
 * @Date   : 22/07/2010
 * ----------------------------------------
 * Modifications :
 **/

package utils;

import java.util.Calendar;
import java.util.Date;

public class OperationsDates {
	
	public final static String[] MONTH = {"janvier","février","mars","avril","mai","juin","juillet","août","septembre","octobre","novembre","décembre"};
	public final static String[] MONTH_SIZE = {"janvier  ","février  ","mars     ","avril    ","mai      ","juin     ","juillet  ","ao�t     ","septembre","octobre  ","novembre ","décembre "};
	
	/** Transforme une date en chaîne
	 * @param s : Date en chaîne au format jj/mm/aaaa
	 * @return  : Renvoi la date au format Date
	 */
	public static Date toDate(String s) {
		String[] tab = s.split("/");
		int jour = Integer.parseInt(tab[0]);
		int mois = Integer.parseInt(tab[1]);
		int annee = Integer.parseInt(tab[2]);
		
		Calendar cal = Calendar.getInstance();
		cal.set(annee,mois-1,jour);
		Date date = new Date();
		date = cal.getTime();
		return date;
	}
	/** Transforme une date en string
	 * @param d : Date a transformé en chaîne
	 * @return  : Format de sortie : jj/mm/aaaa
	 */
	public static String toString(Date d) {
		String res;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		
		res=(cal.get(Calendar.DATE)<10)?"0"+cal.get(Calendar.DATE):""+cal.get(Calendar.DATE);
		res+="/";
		res+=((cal.get(Calendar.MONTH)+1)<10)?"0"+(cal.get(Calendar.MONTH)+1):""+(cal.get(Calendar.MONTH)+1);
		res+="/";
		res+=cal.get(Calendar.YEAR);

		return res;
	}
	/** Transforme une date/heure en string
	 * @param d : Date/heure a transformé en chaîne
	 * @return  : Format de sortie : jj/mm/aaaa hh:mm:ss
	 */
	public static String dateHeureToString(Date d) {
		String res;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		
		res=(cal.get(Calendar.DATE)<10)?"0"+cal.get(Calendar.DATE):""+cal.get(Calendar.DATE);
		res+="/";
		res+=((cal.get(Calendar.MONTH)+1)<10)?"0"+(cal.get(Calendar.MONTH)+1):""+(cal.get(Calendar.MONTH)+1);
		res+="/";
		res+=cal.get(Calendar.YEAR);
		res+=" ";
		res+=cal.get(Calendar.HOUR_OF_DAY);
		res+=":";
		res+=cal.get(Calendar.MINUTE);
		res+=":";
		res+=cal.get(Calendar.SECOND);

		return res;
	}
}