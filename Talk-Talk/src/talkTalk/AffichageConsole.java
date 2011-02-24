package talkTalk;

public class AffichageConsole implements Affichage{

	@Override
	public void afficherErreurRecu(String destinataire, String msg) {
		System.out.println("Erreur : Le message suivant n'a pas pu être remis à "+destinataire+" : \n"+msg);
		
	}

	@Override
	public void afficherMessageEnvoye(String destinataire, String msg) {
		System.out.println("Envoi de "+msg+" à "+destinataire);
		
	}

	@Override
	public void afficherMessageRecu(String expediteur, String msg) {
		System.out.println("> "+expediteur+" : "+msg);
	}

	@Override
	public void afficherDestinataireInconnu(String destinataire) {
		// TODO Auto-generated method stub
		System.out.println("Destinataire inconnu : "+destinataire);
	}

}
