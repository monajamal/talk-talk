package talkTalk;


public class ContactAddr extends Contact {
	
	private String address;

	public ContactAddr(String pseudo) {
		super(pseudo);
	}

	public ContactAddr(String pseudo, String addr) {
		super(pseudo);
		this.address = addr;
	}
	
	public ContactAddr(Contact c) {
		super(c.getPseudo());
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
	

}
