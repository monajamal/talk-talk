package commun;

import java.util.Vector;

public class Groupe extends Contact {
	
	private String name;
	Vector<Personne> membres;
	
	public Groupe(String name) {
		this.setName(name);
		membres = new Vector<Personne>();
	}
	public void setName(String name) {
		this.name = name;
	}
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
	
	public Vector<Personne> getMembres(){
		return membres;
	}
	
	public void addMembre(Personne pers){
		membres.add(pers);
	}
	
	public boolean removeMembre(Personne pers)
	{
		return membres.remove(pers);
	}
	
}