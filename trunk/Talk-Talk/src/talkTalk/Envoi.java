package talkTalk;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Envoi extends Thread {

	private String dest;
	private String msg;
	
	public Envoi(String dest, String msg){
		super();
		this.dest = dest;
		this.msg = msg;
	}
	
	public void run(){
		Contact c;
		String addr;
		Distant d;
		c = TalkTalk.friends.get(dest);
		if (c==null || c.getType() == Contact.CONTACT_UNKNOW){
			//TODO Recherche du destinataire
			System.out.println("Destinataire inconnu");
		} else if (c.getType() == Contact.CONTACT_NORMAL){
			addr = c.getAddr();
			try {
				System.out.println("Envoi de "+msg+" à "+addr);
				d = (Distant)Naming.lookup("rmi://"+addr+"/TalkTalk");
				d.sendMsg(TalkTalk.pseudo,msg);
			}catch (MalformedURLException e) {
				System.out.println("1");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur : Le message suivant n'a pas pu être remis à "+dest+" : \n"+msg);
				//e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				System.out.println("3");
				e.printStackTrace();
			} 
			
		}
	}
}
