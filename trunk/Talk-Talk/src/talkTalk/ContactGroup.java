/**
 * Représente un contact de groupe
 */

package talkTalk;

import java.util.ArrayList;
import java.util.List;

public class ContactGroup extends Contact {

	private List<String> membres;
	
	public ContactGroup(String pseudo) {
		super(pseudo);
		this.membres = new ArrayList<String>();
		// TODO Auto-generated constructor stub
	}

	public ContactGroup(Contact c) {
		super(c.getPseudo());
	}

	public int getType(){
		return CONTACT_GROUP;
	}
	
	public List<String> getMembres() {
		return membres;
	}
	
	public void addMembres(String membre){
		membres.add(membre);
	}

	@Override
	public Adresse getAddr() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Distant getDistant() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDistant(Distant d) {
		// TODO Auto-generated method stub
		
	}
}
