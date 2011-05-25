/**
 * @Description : Classe de debug de programme
 * 
 * @Auteur : Damien
 * @Date   : 10/11/2010
 * ----------------------------------------
 * Modifications :
 **/
/* Comment ça marche ?
=> Programmation
import utils.Debug;
public class Demo {
	public static void main(String[] args) {
		Debug.start(args);
		Debug.addLog("Start Demo", Debug.LOG_INFO, "all");
		//Algorithme "TRUC"
		Debug.addLog("Algorithme truc", Debug.LOG_INFO, "truc");
		Debug.addLog("Erreur. La variable ne doit pas être négative", Debug.LOG_ERREUR, "truc");
		//Algorithme "MACHIN"
		Debug.addLog("Algorithme machin", Debug.LOG_INFO, "machin");
		Debug.addLog("Attention, il semblerait qu'un élément ne soit pas opérationnel", Debug.LOG_WARNING, "machin");
	}
}

=> Utilisation
$> java Demo -debug=false
aucun affichage dans la console
$> java Demo -debug=true
affiche les logs généraux (tag=all) ainsi que les logs d'erreurs
$> java Demo -debug=true -tag=all
affiche les logs généraux (tag=all) ainsi que les logs d'erreurs
$> java Demo -debug=true -tag=truc
affiche les logs "truc" ainsi que les logs d'erreurs
$> java Demo -debug=true -tag=truc-machin
affiche les logs "truc" et "machin" ainsi que les logs d'erreurs
 */

package tests;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import utils.Divers;

public class Debug {
	
	public static boolean DEBUG=false;
	public static String TAG;
	public static int LOG_INFO=0;
	public static int LOG_WARNING=1;
	public static int LOG_ERREUR=2;

	public static void start(String[] args) {
		TAG="simple";
		if (args.length>0) {
			for (int i=0;i<args.length;i++) {
				args[i]=args[i].toLowerCase();
				// debug, -debug, --debug, --debug=true, etc...
				if (args[i].contains("debug")) {
					DEBUG=true;
				} else if (args[i].substring(0,5).equals("-tag=")) {
					TAG=Divers.removeAccents(args[i].substring(5).toLowerCase());
				}
			}
		}
	}
	public static void addLog(String text) {
		addLog(text, Debug.LOG_INFO, "all");
	}
	public static void addLog(String text,int MessageType) {
		addLog(text, MessageType, "all");
	}
	public static void addLog(String text,int MessageType,String tag) {
		if (DEBUG) {
			tag=Divers.removeAccents(tag);

			if (tag.equals("all") || TAG.contains(tag.toLowerCase()) || MessageType==LOG_ERREUR || TAG.equals("all")) {
				Date d = new Date();
				String l;
				switch (MessageType) {
				case 0  : l=" info  ";break;
				case 1  : l="Warning";break;
				case 2  : l="ERREUR ";break;
				default : l="  log  ";
				}
				if (MessageType==LOG_ERREUR)
					System.err.println("["+l+"] "+dateLog(d)+" : "+text);
				else
					System.out.println("["+l+"] "+dateLog(d)+" : "+text);
			}
		}
	}
	public static boolean verrouillage(int day,int month,int year) {
		/*int year = 2010;
		int month = 11;
		int day = 26;
*/
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		Calendar fin = Calendar.getInstance();
		fin.set(year,month-1,day);

		if (today.before(fin)) {
			JOptionPane.showMessageDialog(null, "Ceci est une version Bêta à durée limité.");
			return false;
		} else {
			JOptionPane.showMessageDialog(null, "Cette version Bêta à expirée.");
			return true;
		}
	}

	static String dateLog(Date d) {
		String res="";
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		res+=(cal.get(Calendar.HOUR_OF_DAY)<10)?"0"+cal.get(Calendar.HOUR_OF_DAY):cal.get(Calendar.HOUR_OF_DAY);
		res+="h";
		res+=(cal.get(Calendar.MINUTE)<10)?"0"+cal.get(Calendar.MINUTE):cal.get(Calendar.MINUTE);
		res+="m";
		res+=(cal.get(Calendar.SECOND)<10)?"0"+cal.get(Calendar.SECOND):cal.get(Calendar.SECOND);
		res+="s";
		int m=cal.get(Calendar.MILLISECOND);String r="";if (m<10) r+="0";if (m<100) r+="0";r+=m;
		res+=r;
		return res;
	}
}