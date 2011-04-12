/**
 * Interface qui représente une sortie du programme
 */

package talkTalk;

import commun.Groupe;
import commun.Personne;

public interface Affichage {
	/**
	 * Fonction appelée lors de la reception d'un message
	 * @param exp l'expéditeur du message
	 * @param msg le message
	 */
	String afficherMessageRecu = "> %expediteur : %message\n";
	public void afficherMessageRecu(Personne expediteur, String message);
	/**
	 * Fonction appelée lors de la reception d'un message de groupe
	 * @param exp l'expéditeur du message
	 * @param grp le groupe concerné
	 * @param msg le message
	 */
	String afficherMessageRecuGrp = "> %expediteur (%groupe) : %message\n";
	public void afficherMessageRecuGrp(Groupe groupe, Personne expediteur, String message);
	/**
	 * Afficher le wizz reçu.
	 * @param exp l'expediteur du wizz
	 */
	String afficherWizzRecu = "%expediteur vous a envoyé un wizz\n";
	public void afficherWizzRecu(Personne expediteur);
	/**
	 * Afficher le wizz reçu.
	 * @param exp l'expediteur du wizz
	 */
	String afficherWizzRecuGrp = "%expediteur (%groupe) vous a envoyé un wizz\n";
	public void afficherWizzRecuGrp(Groupe grp, Personne expediteur);
	/**
	 * Fonction appelée lorsqu'un message est envoyé
	 * @param destinataire le destinataire 
	 * @param msg le message
	 */
	String afficherMessageEnvoye = "Envoi de %message à %destinataire\n";
	public void afficherMessageEnvoye(Personne destinataire, String message);
	/**
	 * Fonction appelée lorsqu'un wizz est envoyé
	 * @param destinataire le pauvre qui va être secoué
	 */
	String afficherWizzEnvoye = "Vous avez wizzé %destinataire\n";
	public void afficherWizzEnvoye(Personne destinataire);
	/**
	 * Fonction appelée lors d'une erreur d'envoi d'un message 
	 * @param destinataire le pseudo du destinataire
	 * @param msg le message
	 */
	String afficherErreurEnvoi = "Erreur : Le message suivant n'a pas pu être remis à %destinataire : \n%message\n";
	public void afficherErreurEnvoi(String destinataire, String message);
	/**
	 * Fonction appelée lorsqu'un destinataire est inconnu
	 * @param destinataire 
	 */
	String afficherDestinataireInconnu = "Destinataire inconnu : %destinataire\n";
	public void afficherDestinataireInconnu(String destinataire);
	/**
	 * Changement de statut de notre ami
	 * @param personne
	 */
	String changerStatut = "%pseudo change de statut : %statut\n";
	public void changerStatut(Personne personne);
	/**
	 * Changement de message de notre ami
	 * @param personne
	 */
	String changerMessage = "%pseudo change de message : %message\n";
	public void changerMessagePerso(Personne personne);
	/**
	 * Changement d'image de notre ami
	 * @param p 
	 */
	String changerImage = "%pseudo change d'image perso";
	public void changerImage(Personne personne);
	/**
	 * Affichage de la reception d'un fichier
	 * @param p
	 * @param fichier
	 */
	String afficherFichierRecu = "Fichier %fichier recu de %personne";
	public void afficherFichierRecu(Personne personne, String fichier);
	/**
	 * Appelée pour lancer l'ihm
	 */
	String start = "Je suis "+TalkTalk.pseudo+" et je suis connecté sur ["+TalkTalk.adressePerso+"] !\n";
	public void start();
	/**
	 * Appelée pour fermer l'ihm
	 */
	String stop = "SERVEUR ["+TalkTalk.adressePerso+"] : Server down !\n";
	public void stop();

}