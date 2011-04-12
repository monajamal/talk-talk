package talkTalk;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
		Personne exp = TalkTalk.ajouterContact(expediteur);
		TalkTalk.ihm.afficherMessageRecu(exp, m);

	}

	@Override
	public void sendWizz(String pseudo, Adresse addr_exp)
	throws RemoteException {
		Personne exp = TalkTalk.ajouterContact(pseudo);
		TalkTalk.ihm.afficherWizzRecu(exp);
	}

	@Override
	public void sendMsgGr(String pseudo, Adresse addr_exp, String m, String grp_name,
			List<String> grp) throws RemoteException {
		Groupe groupe = null;
		Personne exp = TalkTalk.ajouterContact(pseudo);
		groupe = TalkTalk.groupes.get(grp_name);

		if (groupe==null) {
			groupe = new Groupe(grp_name);
			groupe.addMembre(exp);
			for (String membres : grp){
				groupe.addMembre(TalkTalk.ajouterContact(membres));
				
			}
			TalkTalk.groupes.put(grp_name,groupe);
		} 
		//TODO : verifier les membres ?
		TalkTalk.ihm.afficherMessageRecuGrp(groupe,exp, m); 

	}

	@Override
	public void sendWizzGr(String pseudo, Adresse addrExp,String grp_name, List<String> grp)
	throws RemoteException {
		Groupe groupe = null;
		Personne exp = TalkTalk.ajouterContact(pseudo);
		groupe = TalkTalk.groupes.get(grp_name);

		if (groupe==null) {
			groupe = new Groupe(grp_name);
			groupe.addMembre(exp);
			for (String membres : grp){
				groupe.addMembre(TalkTalk.ajouterContact(membres));
			}
			TalkTalk.groupes.put(grp_name,groupe); 
		} 
		
		TalkTalk.ihm.afficherWizzRecuGrp(groupe,exp); 
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

	@Override
	public void abonnement(String pseudo, Adresse addr) throws RemoteException {
		Personne p = TalkTalk.ajouterContact(pseudo);
		p.setAddress(addr);
		TalkTalk.abonnes.add(p);
		//On lui envoie directement le statut et l'image perso
		Envoi env = new Envoi(p,TalkTalk.statut);
		env.start();
		if (TalkTalk.image != null) {
			EnvoiFichier envFic = new EnvoiFichier(p,TalkTalk.image,true);
			envFic.start();
		}
		
	}

	@Override
	public void setStatut(String pseudo, int newStatut) throws RemoteException{
		Personne p = TalkTalk.friends.get(pseudo);
		if (p!=null) { //Si on le connait pas on s'en fout
			p.setStatut(newStatut);
			TalkTalk.ihm.changerStatut(p);
		}
	}

	@Override
	public int getStatut() throws RemoteException{
		return TalkTalk.statut;
	}

	@Override
	public void sendFichier(String pseudo, Adresse adressePerso,
			String fichier, byte[] donnees)throws RemoteException {
		Personne p = TalkTalk.ajouterContact(pseudo);
		p.setAddress(adressePerso);
		TalkTalk.ihm.afficherFichierRecu(p,fichier);
		File f = new File(fichier);
		
		try {
			f.createNewFile();
			DataOutputStream d = new DataOutputStream(new FileOutputStream(f));
			d.write(donnees);
			d.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setImagePerso(String pseudo, byte[] img) throws RemoteException{
		Personne p = TalkTalk.friends.get(pseudo);
		if (p!=null){
			File f = new File(pseudo);
			//TODO : le mettre qqpart correctement
			try {
				if (!f.exists()){
					f.createNewFile();
				}
				DataOutputStream d = new DataOutputStream(new FileOutputStream(f));
				d.write(img);
				d.close();
				p.setImage_perso(pseudo); //TODO : changer la aussi 0
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		TalkTalk.ihm.changerImage(p);
	}

	
}