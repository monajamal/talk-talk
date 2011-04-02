package commun;

import java.rmi.RemoteException;

import talkTalk.Adresse;
import talkTalk.Distant;
import talkTalk.TalkTalk;

public class Personne extends Contact {
	
	public static final int AVAILABLE = 1;
	public static final int BUSY = 2;
	public static final int IDLE = 3;
	//public static final int INVISIBLE = 4;
	public static final int OFFLINE = 4;
	
	private String pseudo;
	private String alias;
	private int statut;
	private String image_perso;
	private String msg_perso;
	private Adresse address;
	private Distant distant;
	
	public Personne(String pseudo,String alias,int statut,String image_perso,String msg_perso,Adresse addr) {
		this.setPseudo(pseudo);
		this.setAlias(alias);
		this.setStatut(statut);
		this.setImage_perso(image_perso);
		this.setMsg_perso(msg_perso);
		this.setAddress(addr);
		this.setDistant(null);
	}
	public Personne(String pseudo, Adresse addr) {
		this.setPseudo(pseudo);
		this.setAlias(null);
		this.setStatut(OFFLINE);
		this.setImage_perso(null);
		this.setMsg_perso(null);
		this.setAddress(addr);
		this.setDistant(null);
	}
	
	@Override
	public String getName() {
		return this.getPseudo();
	}
	@Override
	public String getImg() {
		String res = "images/statut/";
		switch (this.getStatut()) {
			case AVAILABLE : res+="dispo.png";break;
			case BUSY : res+="occupe.png";break;
			case IDLE : res+="absent.png";break;
			//case 4 : res+="invisible.png";break;
			default : res+="offline.png";
		}
		return res;
	}
	@Override
	public int getType() {
		return Contact.PERSONNE;
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
	public void setAddress(Adresse address) {
		if (address==null || !address.equals(this.address)) {
			this.distant = null;
			this.address = address;
		}
	}
	public Adresse getAddress() {
		return address;
	}
	public void setDistant(Distant distant) {
		if (distant == null)
		{
			this.distant = null;
		} else if (this.distant==null || !this.distant.equals(distant)){
			this.distant = distant;
			//On s'abonne au changement 
			try {
				distant.abonnement(TalkTalk.pseudo, TalkTalk.adressePerso);
			} catch (RemoteException e) {
				distant=null;
			}
		}
	}
	public Distant getDistant() {
		return distant;
	}
}