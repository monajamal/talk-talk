package talkTalk;

import java.util.List;


public class ContactUnknow extends Contact{



	public ContactUnknow(String pseudo)
	{
		super(pseudo);
	}
	
	public String getString(){
		return "?";
	}
	
	public String getAddr() {
		return null;
	}
	
	public List<String> getMembres() {
		return null;
	}
	
	public Distant getDistant() {
		return null;
	}

	@Override
	public void setDistant(Distant d) {
		// TODO Auto-generated method stub
		
	}

}
    