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
	public ContactGroup(String pseudo, String addr) {
		super(pseudo);
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
	public String getString() {
		String s = "";
		for (String membre : membres){
			s+=membre+",";
		}
		return s.substring(0,s.length()-1);
	}
}
