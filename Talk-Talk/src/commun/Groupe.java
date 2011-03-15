package commun;

import java.util.Vector;

public class Groupe extends Contact {

	private String name;
	Vector<Personne> membres;
	
	public Groupe(String name) {
		this.setName(name);
		membres = new Vector<Personne>();
	}
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public String getImg() {
		return "images/groupe.png";
	}
	@Override
	public int getType() {
		return Contact.GROUPE;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public Vector<Personne> getMembres() {
		return membres;
	}
	public void addMembre(Personne pers) {
		membres.add(pers);
	}
	public boolean removeMembre(Personne pers) {
		return membres.remove(pers);
	}
	public Vector<String> getPseudosMembres(){
		Vector<String> res = new Vector<String>();
		synchronized(membres){
			for (Personne p : membres){
				res.add(p.getPseudo());
			}
		}
		return res;
	}
	
	
}