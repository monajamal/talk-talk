package talkTalk;

public interface Affichage {

	public void afficherMessageRecu(String expediteur, String msg);
	public void afficherMessageEnvoye(String destinataire, String msg);
	public void afficherErreurRecu(String destinataire, String msg);
	public void afficherDestinataireInconnu(String destinataire);
}
