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
		
		exp = TalkTalk.friends.get(expediteur);
		
		if (exp==null) {
			exp = new Personne(expediteur,addr_exp);
			TalkTalk.friends.put(expediteur,exp);
		} else {
			exp.setAddress(addr_exp);
		}
		TalkTalk.ihm.afficherMessageRecu(exp, m);
		
	}

	@Override
	public void sendWizz(String pseudo, Adresse addr_exp)
			throws RemoteException {
		Personne exp = null;
		exp = TalkTalk.friends.get(pseudo);
		
		if (exp==null) {
			exp = new Personne(pseudo,addr_exp);
			TalkTalk.friends.put(pseudo,exp);
		} else {
			exp.setAddress(addr_exp);
		}// TODO Auto-generated method stub
		TalkTalk.ihm.afficherWizzRecu(exp);
		
	}

	@Override
	public void sendMsgGr(String pseudo, Adresse addr_exp, String m,
			List<Adresse> grp) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendWizzGr(String pseudo, Adresse addrExp, List<Adresse> grp)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}