/**
 * Classe qui affiche les événements dans la console
 */

package talkTalk;

public class AffichageConsole implements Affichage{

	@Override
	public void afficherErreurEnvoi(String destinataire, String msg) {
		System.out.println("Erreur : Le message suivant n'a pas pu être remis à "+destinataire+" : \n"+msg);
		
	}

	@Override
	public void afficherMessageRecu(Contact expediteur, String msg) {
		System.out.println("> "+expediteur.getPseudo()+" : "+msg);
	}

	@Override
	public void afficherDestinataireInconnu(String destinataire) {
		// TODO Auto-generated method stub
		System.out.println("Destinataire inconnu : "+destinataire);
	}

	@Override
	public void afficherMessageEnvoye(Contact destinataire, String msg) {
		System.out.println("Envoi de "+msg+" à "+destinataire.getPseudo());
	}

}
