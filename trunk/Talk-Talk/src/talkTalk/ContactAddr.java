package talkTalk;

import java.util.List;


public class ContactAddr extends Contact {
	
	private String address;
	private Distant distant;
	
	public ContactAddr(String pseudo) {
		super(pseudo);
		setDistant(null);
	}

	public ContactAddr(Contact c) {
		super(c.getPseudo());
	}
	
	public ContactAddr(String pseudo, String addr) {
		super(pseudo);
		this.address = addr;
	}
	
	public void setAddr(String addr) {
		this.address = addr;
	}

	public String getAddr() {
		return address;
	}
	
	public int getType(){
		return CONTACT_NORMAL;
	}

	@Override
	public String getString() {
		return address;
	}

	public void setDistant(Distant distant) {
		this.distant = distant;
	}

	public Distant getDistant() {
		return distant;
	}

	@Override
	public List<String> getMembres() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
