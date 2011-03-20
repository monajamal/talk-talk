/**
 * Classe représentant un jeton passé de noeud en noeud pour effectuer une recherche sur un pseudo
 */
package talkTalk;

import java.io.Serializable;
import java.util.Vector;

public class JetonRecherche implements Serializable {
	
	private static final long serialVersionUID = -357079488709377275L;
	private Adresse reponse;
	private String pseudo;
	private Vector<String> noeuds;
	private String id;
	
	public JetonRecherche(String pseudo){
		this.id =this.toString();
		this.reponse = null;
		this.noeuds = new Vector<String>();
		this.pseudo = pseudo;
	}
	
	public void setReponse(Adresse reponse) {
		this.reponse = reponse;
	}
	public Adresse getReponse() {
		return reponse;
	}
	public void addNoeud(String noeuds) {
		this.noeuds.add(noeuds);
	}
	public Vector<String> getNoeuds() {
		return noeuds;
	}
	public String getId() {
		return id;
	}
	public String getPseudo() {
		return pseudo;
	}

}
