/**
 * Représente un contact qui n'est pas un groupe.
 */
package talkTalk;

import java.util.List;


public class ContactAddr extends Contact2 {
	
	private Adresse address;
	private Distant distant;
	
	public ContactAddr(String pseudo) {
		super(pseudo);
		address = null;
		setDistant(null);
	}

	public ContactAddr(Contact2 c) {
		super(c.getPseudo());
	}
	
	public ContactAddr(String pseudo, String addr, int port) {
		super(pseudo);
		this.address = new Adresse(addr,port);
	}
	
	public ContactAddr(String pseudo, Adresse addr) {
		super(pseudo);
		this.address = addr;
	}

	public void setAddr(String addr, int port) {
		this.address = new Adresse(addr, port);
		if (addr==null){
			distant=null;
		}
	}

	public Adresse getAddr() {
		return address;
	}
	
	public int getType(){
		if (address !=null) {
			return CONTACT_NORMAL;
		} else {
			return CONTACT_UNKNOW;
		}
	}

	

	public void setDistant(Distant distant) {
		this.distant = distant;
	}

	public Distant getDistant() {
		return distant;
	}

	@Override
	public List<String> getMembres() {
		return null;
	}

	

}
