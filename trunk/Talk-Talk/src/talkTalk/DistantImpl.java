package talkTalk;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

public class DistantImpl implements Distant {
	
	private static final long serialVersionUID = -4154160384111399024L;
	public DistantImpl() throws RemoteException {
		super();
	}

	@Override
	public Vector<String> searchContact(String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMsg(String expediteur, Adresse addr_exp, String m)
			throws RemoteException {
		Contact exp = null;
		for (Contact c :TalkTalk.friends){
			if (c.getType()==Contact.CONTACT_NORMAL && c.getAddr().equals(addr_exp)) {
				exp = c;
				break;
			}
		}
		if (exp==null) { //On a pas trouvé l'ip dans les contacts
			for (Contact c :TalkTalk.friends){ //On cherche le pseudo
				if (c.getType()==Contact.CONTACT_UNKNOW && c.getPseudo().equals(expediteur)) {
					exp = c;
					break;
				}
			}
		}
		
		if (exp==null) {
			exp = new ContactAddr(expediteur,addr_exp);
		}
		TalkTalk.aff.afficherMessageRecu(exp, m);
		
	}

	@Override
	public void sendWizz(String pseudo, Adresse addr_exp)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMsgGr(String pseudo, Adresse addr_exp, String m,
			List<Adresse> grp) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}