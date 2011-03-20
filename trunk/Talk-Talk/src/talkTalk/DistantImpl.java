package talkTalk;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import commun.Groupe;
import commun.Personne;

public class DistantImpl implements Distant {

	private static final long serialVersionUID = -4154160384111399024L;

	public DistantImpl() throws RemoteException {
		super();
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
		}
		TalkTalk.ihm.afficherWizzRecu(exp);
	}

	@Override
	public void sendMsgGr(String pseudo, Adresse addr_exp, String m, String grp_name,
			List<String> grp) throws RemoteException {
		Groupe groupe = null;
		groupe = TalkTalk.groupes.get(grp_name);

		if (groupe==null) {
			groupe = new Groupe(grp_name);
			//TODO :Rajouter membres
			TalkTalk.groupes.put(grp_name,groupe);
		} 
		//TODO : verifier les membres ?
		//TalkTalk.ihm.affichermsgRecu(exp); //TODO : affichage groupe

	}

	@Override
	public void sendWizzGr(String pseudo, Adresse addrExp,String grp_name, List<String> grp)
	throws RemoteException {
		Groupe groupe = null;
		groupe = TalkTalk.groupes.get(grp_name);

		if (groupe==null) {
			groupe = new Groupe(grp_name);
			//TODO :Rajouter membres
			TalkTalk.groupes.put(grp_name,groupe); 
		} 
		//TODO : affichage groupe
	}

	@Override
	public JetonRecherche searchContact(JetonRecherche jeton)
	throws RemoteException {

		Personne personneSearch = TalkTalk.friends.get(jeton.getPseudo());

		Personne suivant = null;
		boolean found;

		jeton.addNoeud(TalkTalk.pseudo); //On se rajoute au jeton
		if (TalkTalk.pseudo.equals(jeton.getPseudo())) {
			//C'est moi qu'on cherche
			//TODO : enlever ce commentaire 
			System.out.println("Je vois pas comment on peut arriver là, à part en se cherchant soi même");
			jeton.setReponse(TalkTalk.adressePerso);
		} else if (personneSearch !=null && personneSearch.getAddress()!=null){
			//On a la réponse
			System.out.println("On a la réponse : "+personneSearch.getAddress());
			jeton.setReponse(personneSearch.getAddress());
		} else {
			//On a pas la réponse nous même
			System.out.println("On a pas la réponse");
			do {
				synchronized(TalkTalk.friends)
				{
					found = false;
					for (String pseudo : TalkTalk.friends.keySet()){
						if (!jeton.getNoeuds().contains(pseudo)){ //Pas deja passé par là
							suivant = TalkTalk.friends.get(pseudo);
							if (suivant.getAddress() != null){ //L'adresse est renseigné
								System.out.println("On a trouve :"+pseudo);
								found = true;
								break;
							}
						}
					}
				}
				if (found) { //On essaye d'envoyer le jeton a suivant
					Distant d = suivant.getDistant();
					boolean lookup=false;
					boolean done=false;
					while (!lookup && !done){ //On réessaye si on a pas encore fait de lookup
						if (d == null ) {//On a pas encore cherché l'interface distante
							try {
								lookup = true;
								d = (Distant)Naming.lookup("rmi://"+suivant.getAddress().toString()+"/TalkTalk");
								suivant.setDistant(d); //On a cherché la reference, on stocke
							} catch (MalformedURLException e) {
								jeton.addNoeud(suivant.getPseudo()); //Il ne répond pas on le rajoute pour pas redemander
								suivant.setDistant(null);
							} catch (NotBoundException e) {
								jeton.addNoeud(suivant.getPseudo()); //Il ne répond pas on le rajoute pour pas redemander
								suivant.setDistant(null);
							} catch (RemoteException e) {
								jeton.addNoeud(suivant.getPseudo()); //Il ne répond pas on le rajoute pour pas redemander
								suivant.setDistant(null);
							}
						}
						if (d!=null){
							try {
								jeton = d.searchContact(jeton);
								done=true;
							} catch (RemoteException e) {
								jeton.addNoeud(suivant.getPseudo()); //Il ne répond pas on le rajoute pour pas redemander
								suivant.setDistant(null);
							}
						} else {
							//On a rate le lookup
							done = true;
						}
					}
				}
			} while(jeton.getReponse()==null && found); //Tant qu'on a pas la réponse et qu'on a trouvé qqun à qui l'envoyer
			if (personneSearch !=null) {		// renseigner adresse si on est interessé
				personneSearch.setAddress(jeton.getReponse());
			}
		}
		return jeton;
	}
}