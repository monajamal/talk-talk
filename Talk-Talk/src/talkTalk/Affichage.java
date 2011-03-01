/**
 * Interface qui représente une sortie du programme
 */

package talkTalk;

public interface Affichage {
	
	/**
	 * Fonction appelée lors de la reception d'un message
	 * @param expediteur l'expéditeur du message
	 * @param msg le message
	 */
	public void afficherMessageRecu(Contact expediteur, String msg);
	/**
	 * Fonction appelée lorsqu'un message est envoyé
	 * @param destinataire le destinataire 
	 * @param msg le message
	 */
	public void afficherMessageEnvoye(Contact destinataire, String msg);
	/**
	 * Fonction appelée lors d'une erreur d'envoi d'un message 
	 * @param destinataire le pseudo du destinataire
	 * @param msg le message
	 */
	public void afficherErreurEnvoi(String destinataire, String msg);
	/**
	 * Fonction appelée lorsqu'un destinataire est inconnu
	 * @param destinataire 
	 */
	public void afficherDestinataireInconnu(String destinataire);
}
