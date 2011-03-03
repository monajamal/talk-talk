package talkTalk;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

import commun.Personne;

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
		Personne exp = null;
		for (Personne p :TalkTalk.friends){
			if (p.getAddress().equals(addr_exp)) {
				exp = p;
				break;
			}
		}
		if (exp==null) { //On a pas trouvé l'ip dans les contacts
			for (Personne p :TalkTalk.friends){ //On cherche le pseudo
				if (p.getPseudo().equals(expediteur)) {
					exp = p;
					p.setAddress(addr_exp);//MAJ a la reception de message
					break;
				}
			}
		}
		
		if (exp==null) {
			exp = new Personne(expediteur,addr_exp);
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