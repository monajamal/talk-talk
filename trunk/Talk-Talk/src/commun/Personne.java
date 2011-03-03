package commun;

import talkTalk.Adresse;
import talkTalk.Distant;

public class Personne extends Contact {
	
	public static final int AVAILABLE = 1;
	public static final int BUSY = 2;
	public static final int IDLE = 3;
	public static final int INVISIBLE = 4;
	public static final int OFFLINE = 5;
	
	private String pseudo;
	private String alias;
	private int statut;
	private String image_perso;
	private String msg_perso;
	private String dns;
	private Adresse address;
	private Distant distant;
	
	public Personne(String pseudo,String alias,int statut,String image_perso,String msg_perso,Adresse addr,String dns) {
		this.setPseudo(pseudo);
		this.setAlias(alias);
		this.setStatut(statut);
		this.setImage_perso(image_perso);
		this.setMsg_perso(msg_perso);
		this.setAddress(addr);
		this.setDns(dns);
	}
	public Personne(String pseudo, Adresse addr) {
		this.setPseudo(pseudo);
		this.setAddress(addr);
		this.setAlias(null);
		this.setStatut(OFFLINE);
		this.setImage_perso(null);
		this.setMsg_perso(null);
		this.setAddress(addr);
		this.setDns(null);
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAlias() {
		return alias;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public int getStatut() {
		return statut;
	}
	public void setImage_perso(String image_perso) {
		this.image_perso = image_perso;
	}
	public String getImage_perso() {
		return image_perso;
	}
	public void setMsg_perso(String msg_perso) {
		this.msg_perso = msg_perso;
	}
	public String getMsg_perso() {
		return msg_perso;
	}
	
	public void setDns(String dns) {
		this.dns = dns;
	}
	public String getDns() {
		return dns;
	}
	@Override
	public String getName() {
		return this.getPseudo();
	}
	@Override
	public String getImg() {
		String res = "images/statut/";
		switch (this.getStatut()) {
			case 1 : res+="dispo.png";break;
			case 2 : res+="occupe.png";break;
			case 3 : res+="absent.png";break;
			case 4 : res+="invisible.png";break;
			default : res+="offline.png";
		}
		return res;
	}
	@Override
	public int getType() {
		return Contact.FRIEND;
	}
	public void setAddress(Adresse address) {
		this.address = address;
	}
	public Adresse getAddress() {
		return address;
	}
	public void setDistant(Distant distant) {
		this.distant = distant;
	}
	public Distant getDistant() {
		return distant;
	}
}