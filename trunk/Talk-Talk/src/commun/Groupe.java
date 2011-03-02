package commun;

import java.util.Vector;

public class Groupe extends Contact {
	
	private String name;
	Vector<Personne> amis;
	
	public Groupe(String name) {
		this.setName(name);
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
}