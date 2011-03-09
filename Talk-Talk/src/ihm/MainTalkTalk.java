package ihm;

import java.util.Vector;

import talkTalk.Adresse;

import commun.Contact;
import commun.Groupe;
import commun.Personne;

public class MainTalkTalk {
	
	static Vector<Contact> ami;
	
	public static void main(String[] args) {
		ami = new Vector<Contact>();
		ami.add(new Personne("Damien","TeeTux",Personne.AVAILABLE,"images/tux.png",null,new Adresse("192.168.1.1",1099)));
		ami.add(new Personne("Marie-Hélène","Plop",Personne.IDLE,"images/schtroumpfette.png",null,new Adresse("192.168.1.1",22222)));
		ami.add(new Personne("Jonathan","Tux",Personne.BUSY,"images/tux1.png",null,new Adresse("192.168.1.1",1099)));
		ami.add(new Personne("William",null,Personne.OFFLINE,null,null,new Adresse("192.168.1.1",1099)));
		ami.add(new Groupe("Groupe"));
		ami.add(new Personne("FastWriting",null,Personne.INVISIBLE,null,null,new Adresse("192.168.1.1",1099)));
		
		//ami.get(0).
		new IHM("TalkTalk");
		
	}
}