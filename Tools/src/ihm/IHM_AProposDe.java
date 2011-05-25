/**
 * Domaine : IHM "A propos de ..." des applications fais par l'équipe
 * 
 * Auteur  : Damien
 * Date    : 22/07/2010
 * ----------------------------------------
 * Ressources : images/ et images/tux/
 * ----------------------------------------
 * Modifications :
 **/

package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import components.JHyperTextLink;

import nanoxml.XMLElement;
import nanoxml.XMLParseException;
import objects.BooleanUpdate;
import tests.Constantes;
import utils.Resources;

@SuppressWarnings("serial")
public class IHM_AProposDe extends JDialog {
	
	final static String NOM_TEAM = "Tux-Team";
	final static String LIEN = "http://code.google.com/p/tux-team/";
	final static String[] DEVELOPPEURS = {"Damien FINCK","Aurélien MATHELIN"};
	
	int index_crt_image;//index : de 0 à max-1
	int[] tab_alea;//contient de 1 à max sur les cases 0 à max-1
	boolean modeNORMAL;//sens de défilement des Tux
	
	static String maj_file_link;
	static String maj_file_version;
	
	static IHM_AProposDe ihm;
	static String titre;
	static String num_version;
	
	protected JPanel jp_image;;
		protected JLabel jl_image;
		protected JPanel jp_change;
			protected JButton jb_change_left;
			protected JButton jb_change_right;
	protected JPanel jp_infos;
		protected JPanel jp_infos_titre;
			protected JLabel jl_nomProduit;
			protected JLabel jl_resume;
		protected JPanel jp_infos_details;
			protected JPanel jp_infos_details_left;
				protected JLabel jl_nomTeam_left;
				protected JLabel jl_developpeur_left;
				protected JLabel jl_lien_left;
				protected JLabel jl_version_left;
				protected JLabel jl_licence_left;
			protected JPanel jp_infos_details_right;
				protected JLabel jl_nomTeam_right;
				protected JLabel jl_developpeur_right;
				protected JHyperTextLink jhtl_lien_right;
				protected JLabel jl_version_right;
				protected JHyperTextLink jhtl_licence_right;
		protected JLabel jl_copyright;
	protected JPanel jp_maj;//Soit ce JPanel
		protected JLabel jl_maj;
		protected JPanel jp_boutons;
			protected JButton jb_maj;
			protected JButton jb_ok;
	protected JPanel jp_maj_dl;//Soit ce JPanel
		protected JLabel jl_dl;
		protected JPanel jp_progress_total_dl;
			protected JPanel jp_progress_dl;//juste pour le composant soit pas gros
				protected JProgressBar jpb_maj;
			protected JLabel jl_valeur;
		protected JButton jb_masquer;
	protected JPanel jp_maj_close;//Soit ce JPanel
		protected JLabel jl_info_close;
		protected JPanel jp_close;
			protected JButton jb_restart;
			protected JButton jb_close;
		
	Timer minuteur;
	
	public IHM_AProposDe(JFrame j,Class<?> c,String titre,String sous_titre,String num_version,BooleanUpdate applyUpdateOnReload) {
		/** Titre **/
		super(j,"A propos de ...");
		/** Initialisation **/
		taille_tab();alea();index_crt_image=0;
		/** Accessibilité ihm **/
		IHM_AProposDe.ihm=this;
		IHM_AProposDe.titre=titre;
		IHM_AProposDe.num_version=num_version;
		/** Évènement **/
		ActionListener action = new Event_AProposDe(this,index_crt_image,tab_alea,titre,applyUpdateOnReload);
		/** Barre de Menu **/
		creeBarreDeMenu();
		/** Interface **/
		creeInterface(titre,sous_titre,num_version,index_crt_image,tab_alea,action);
		/** Zone de notification **/
		menuContextuel();
		/** Fenêtre **/
		creeFenetre(c);
		
		/** Processus à part pour afficher l'infarce pendant que les mises à jours sont vérifiées**/
		Thread th_search_maj = new Thread() {
			public void run() {
				/** Recherche de mise à jour **/
				maj(IHM_AProposDe.titre,IHM_AProposDe.num_version);
			}
		};
		th_search_maj.start();
		
		/** Minuteur pour changer les Tux **/
		minuteur = new Timer(5000,action);
		minuteur.start();
		modeNORMAL=true;
	}
	public static void creeBarreDeMenu() {
		/** Création des éléments     **/
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
	}
	public static void creeInterface(String titre,String sous_titre,String num_version,int index_crt_image,int[] tab_alea,ActionListener action) {
		/** Création des éléments     **/
		ihm.jp_image = new JPanel(new BorderLayout());
			ihm.jl_image = new JLabel(Resources.getImageIcon("images/tux/tux_"+nomImage(tab_alea[index_crt_image])+".png",IHM_AProposDe.class));
			ihm.jp_change = new JPanel(new GridLayout(1,2));
				ihm.jb_change_left = new JButton(Resources.getImageIcon("images/left.png",IHM_AProposDe.class));
				ihm.jb_change_right = new JButton(Resources.getImageIcon("images/right.png",IHM_AProposDe.class));
		ihm.jp_infos = new JPanel(new BorderLayout());
			ihm.jp_infos_titre = new JPanel(new BorderLayout());
				ihm.jl_nomProduit = new JLabel(titre,SwingConstants.CENTER);
				ihm.jl_resume = new JLabel(sous_titre);
			ihm.jp_infos_details = new JPanel(new BorderLayout());
				ihm.jp_infos_details_left = new JPanel(new GridLayout(5,1));
					ihm.jl_nomTeam_left = new JLabel("Equipe :");
					ihm.jl_developpeur_left = new JLabel("Développeurs :     ");
					ihm.jl_lien_left = new JLabel("Projet :");
					ihm.jl_version_left = new JLabel("Version :");
					ihm.jl_licence_left = new JLabel("Licence :");
				ihm.jp_infos_details_right = new JPanel(new GridLayout(5,1));
					ihm.jl_nomTeam_right = new JLabel(NOM_TEAM);
					String dev ="<html>";
					for (int i=0;i<DEVELOPPEURS.length;i++) dev+="<p>"+DEVELOPPEURS[i]+"</p>";
					dev+="</html>";
					ihm.jl_developpeur_right = new JLabel(dev);
					ihm.jhtl_lien_right = new JHyperTextLink(LIEN,LIEN);
					ihm.jl_version_right = new JLabel(num_version);
					ihm.jhtl_licence_right = new JHyperTextLink("GNU General Public License","http://www.gnu.org/licenses/gpl.html");
			ihm.jl_copyright = new JLabel("     Copyright © 2010 | "+NOM_TEAM+". Tous droits réservés     ",SwingConstants.CENTER);
		ihm.jp_maj = new JPanel(new BorderLayout());//Soit ce JPanel
			ihm.jl_maj = new JLabel("Vérification des mises à jour...",Resources.getImageIcon("images/load.gif",IHM_AProposDe.class),SwingConstants.LEFT);
			ihm.jp_boutons = new JPanel();
				ihm.jb_maj = new JButton("Mettre à jour");
				ihm.jb_ok = new JButton("OK");
		ihm.jp_maj_dl = new JPanel(new BorderLayout());//Soit ce JPanel
			ihm.jl_dl = new JLabel("Téléchargement en cours...",Resources.getImageIcon("images/download.png",IHM_AProposDe.class),SwingConstants.LEFT);
				ihm.jp_progress_total_dl = new JPanel(new BorderLayout());
					ihm.jp_progress_dl = new JPanel();
						ihm.jpb_maj = new JProgressBar();
					ihm.jl_valeur = new JLabel("0%");
				ihm.jb_masquer = new JButton("Masquer");
		ihm.jp_maj_close = new JPanel(new BorderLayout());//Soit ce JPanel
			ihm.jl_info_close = new JLabel("Téléchargement terminé !",Resources.getImageIcon("images/download.png",IHM_AProposDe.class),SwingConstants.LEFT);
			ihm.jp_close = new JPanel();
				ihm.jb_restart = new JButton("Rédémarrer l'application");
				ihm.jb_close = new JButton("Fermer");
		/** Paramétrage des éléments  **/
		Font fonte_titre = new Font(Font.SANS_SERIF,Font.BOLD,30);
		ihm.jl_nomProduit.setFont(fonte_titre);
		Font gras = new Font(Font.SANS_SERIF,Font.BOLD,12);
		ihm.jl_nomTeam_left.setFont(gras);
		ihm.jl_developpeur_left.setFont(gras);
		ihm.jl_lien_left.setFont(gras);
		ihm.jl_version_left.setFont(gras);
		ihm.jl_licence_left.setFont(gras);
		ihm.jl_copyright.setFont(gras);
		ihm.jb_maj.setVisible(false);
		ihm.jl_info_close.setForeground(new Color(47,143,26));
		ihm.jb_restart.setForeground(new Color(47,143,26));
		
		if (index_crt_image==0) ihm.jb_change_left.setEnabled(false);
		else if (index_crt_image==tab_alea.length-1) ihm.jb_change_right.setEnabled(false);
		
		/** Action sur les éléments   **/
		ihm.jb_change_left.addActionListener(action);
		ihm.jb_change_right.addActionListener(action);
		ihm.jb_maj.addActionListener(action);
		ihm.jb_ok.addActionListener(action);
		ihm.jb_masquer.addActionListener(action);
		ihm.jb_restart.addActionListener(action);
		ihm.jb_close.addActionListener(action);
		/** Montage des éléments      **/
		ihm.add(ihm.jp_image,BorderLayout.CENTER);
			ihm.jp_image.add(ihm.jl_image,BorderLayout.CENTER);
			ihm.jp_image.add(ihm.jp_change,BorderLayout.SOUTH);
				ihm.jp_change.add(ihm.jb_change_left);
				ihm.jp_change.add(ihm.jb_change_right);
		ihm.add(ihm.jp_infos,BorderLayout.EAST);
			ihm.jp_infos.add(ihm.jp_infos_titre,BorderLayout.NORTH);
				ihm.jp_infos_titre.add(ihm.jl_nomProduit,BorderLayout.NORTH);
				ihm.jp_infos_titre.add(ihm.jl_resume,BorderLayout.CENTER);
			ihm.jp_infos.add(ihm.jp_infos_details,BorderLayout.CENTER);
				ihm.jp_infos_details.add(ihm.jp_infos_details_left,BorderLayout.WEST);
					ihm.jp_infos_details_left.add(ihm.jl_nomTeam_left);
					ihm.jp_infos_details_left.add(ihm.jl_developpeur_left);
					ihm.jp_infos_details_left.add(ihm.jl_lien_left);
					ihm.jp_infos_details_left.add(ihm.jl_version_left);
					ihm.jp_infos_details_left.add(ihm.jl_licence_left);
				ihm.jp_infos_details.add(ihm.jp_infos_details_right,BorderLayout.CENTER);
					ihm.jp_infos_details_right.add(ihm.jl_nomTeam_right);
					ihm.jp_infos_details_right.add(ihm.jl_developpeur_right);
					ihm.jp_infos_details_right.add(ihm.jhtl_lien_right);
					ihm.jp_infos_details_right.add(ihm.jl_version_right);
					ihm.jp_infos_details_right.add(ihm.jhtl_licence_right);
			ihm.jp_infos.add(ihm.jl_copyright,BorderLayout.SOUTH);
		ihm.add(ihm.jp_maj,BorderLayout.SOUTH);//Soit ce JPanel
			ihm.jp_maj.add(ihm.jl_maj,BorderLayout.WEST);
			ihm.jp_maj.add(ihm.jp_boutons,BorderLayout.EAST);
				ihm.jp_boutons.add(ihm.jb_maj);
				ihm.jp_boutons.add(ihm.jb_ok);
	}
	public static void menuContextuel() {
		/** Création des éléments     **/
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
	}
	public static void creeFenetre(Class<?> c) {
		ihm.setUndecorated(false);//Affichage de la barre de titre dans la fenêtre
		//ihm.setLayout(new FlowLayout());//Layout par défaut pour JFrame : BorderLayout
		ihm.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);//Action de fermeture
		ihm.setResizable(false);//Redimensionnement de la fenêtre
		/* Dimension : choisir entre pack() ou setSize(width,height) */
		//ihm.pack();//Auto-dimensionner
		ihm.setSize(550,250);//Dimensionner la fenêtre
		//ihm.setExtendedState(MAXIMIZED_BOTH);//Mettre en mode taille écran
		/* ----------------------------------------------------- */
		ihm.setLocationRelativeTo(null);//Positionner la fenêtre
		ihm.setIconImage(Resources.getImage("images/icon.png",c));//Images dans la barre des tâches
		ihm.setVisible(true);//Rendre la fenêtre visible
	}
	
	public void taille_tab() {
		//je cherche le nombre de tux qui existe
		boolean exists=true;
		int res=1;
		while (exists) {
			try {
				new ImageIcon(IHM_AProposDe.class.getResource("images/tux/tux_"+nomImage(res)+".png"));
			} catch(NullPointerException e) {
				exists=false;
			}
			res++;
		}
		res-=2;
		
		tab_alea = new int[res];//Taille du tableau
		for (int i=0;i<tab_alea.length;i++) tab_alea[i]=0;
	}
	public void alea() {
		int i=1;
		while (i<=tab_alea.length) {
			int alea=(int)Math.round(Math.random()*(tab_alea.length-1));//entre 0 et (max-1)
			while (tab_alea[alea]!=0) {
				alea=(int)Math.round(Math.random()*(tab_alea.length-1));//entre 0 et (max-1)
			}
			tab_alea[alea]=i;
			
			i++;
		}
	}
	public static String nomImage(int nb) {
		String res="";
		if (nb<10) res="000";
		else if (nb<100) res="00";
		else if (nb<1000) res="0";
		return res+nb;
	}
	public void maj(String titre,String num_version) {
		int res=2;
		//Download the XML file
		String content_xml="";
		try {
			URL url =new URL("http://code.google.com/feeds/p/tux-team/downloads/basic");
			InputStreamReader ipsr = new InputStreamReader(url.openStream());
			BufferedReader br = new BufferedReader(ipsr);
			String line = null;
			StringBuffer buffer = new StringBuffer();
			while ((line = br.readLine()) != null) {
				buffer.append(line).append('\n');
			}
			br.close();
			content_xml=buffer.toString();
			
			//Read The xml file
			res=lecture_xml(titre,num_version,content_xml);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		switch (res) {
		case 0 : 
			ihm.jl_maj.setText("Pas de mises à jour disponible. ("+num_version+")");
			ihm.jl_maj.setIcon(Resources.getImageIcon("images/valide.png",IHM_AProposDe.class));
			break;
		case 1 : 
			ihm.jl_maj.setText("Une mise à jour est diponible. ("+maj_file_version+")");
			ihm.jl_maj.setIcon(Resources.getImageIcon("images/warning.png",IHM_AProposDe.class));
			ihm.jb_maj.setVisible(true);
			break;
		default :
			ihm.jl_maj.setText("Serveur de mise à jour non disponible.");
			ihm.jl_maj.setIcon(Resources.getImageIcon("images/erreur.png",IHM_AProposDe.class));
		}
		
	}
	public int lecture_xml(String titre,String num_version,String buffer) {
		int res=2;
		try {
			//J'ouvre le XML
			XMLElement xml = new XMLElement();
			xml.parseString(buffer);
			//Je cherche toutes les entry (indice 5 et après)
			int i=5;
			while (i<xml.getChildren().size()) {
				XMLElement xml_entry = (XMLElement) xml.getChildren().get(i);
				//Je cherche le titre le l'entry
				XMLElement xml_title = (XMLElement) xml_entry.getChildren().get(3);
				String title = xml_title.getContent();
				//Si le titre correspond à ce projet
				if (title.contains(titre)) {
					//Je cherche la balise <content>
					XMLElement xml_content = (XMLElement) xml_entry.getChildren().get(5);
					String content = xml_content.getContent();
					String link=content.substring(content.indexOf("http://"),content.indexOf("Download")-2);
					IHM_AProposDe.maj_file_link=link;
					//Je cherche les infos
					String inf=content.substring(content.indexOf(titre),content.indexOf("Labels"));
					//J'ai le titre et le numéro de version/date
					String[] infos = inf.split(" - ");
					maj_file_version=infos[1].trim();
					//Résultat
					if (num_version.equals(maj_file_version)) res=0;
					else res=1;
				}
				i++;
			}
		} catch (XMLParseException e) {
			e.printStackTrace();
		}
		return res;
	}
	public  void download(String nom_fichier_dl,BooleanUpdate applyUpdateOnReload) {
		
		new File(Constantes.DOS_UPDATES).mkdir();
		
		int diviseur=1024;
		String nom_diviseur="Ko";
		
		int pourcentage=0;
		float taille_dl=0;
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(1);
		df.setMinimumFractionDigits(1);
		df.setDecimalSeparatorAlwaysShown(true);
		
		URL url;
		try {
			//URL du fichier à dl
			url = new URL(nom_fichier_dl);
			//Connection
			URLConnection uc = url.openConnection();
			//Tailel du fichier
			float taille_total = uc.getContentLength();
			if (taille_total>1000000) {diviseur=1024*1024;nom_diviseur="Mo";}
			//Flux de lecture
			InputStream in = uc.getInputStream();
			String FileName = url.getFile();
			FileName = FileName.substring(FileName.lastIndexOf('/') + 1);
			FileOutputStream WritenFile = new FileOutputStream(Constantes.DOS_UPDATES+FileName);
			byte[] buff = new byte[1024];
			int l = in.read(buff);
			
			//MAJ IHM
			taille_dl+=l;//ajout nombre d'octet dl
			pourcentage=Math.round(taille_dl/taille_total*100);
			ihm.jpb_maj.setValue(pourcentage);
			ihm.jl_valeur.setText(pourcentage+"% ("+df.format((taille_dl/diviseur))+" sur "+df.format((taille_total/diviseur))+" "+nom_diviseur+")");
			
			while(l>0) {
				
				WritenFile.write(buff,0,l);
				l = in.read(buff);
				
				//MAJ IHM
				taille_dl+=l;//ajout nombre d'octet dl
				pourcentage=Math.round(taille_dl/taille_total*100);
				ihm.jpb_maj.setValue(pourcentage);
				ihm.jl_valeur.setText(pourcentage+"% ("+df.format((taille_dl/diviseur))+" sur "+df.format((taille_total/diviseur))+" "+nom_diviseur+")");
			}
			WritenFile.flush();
			WritenFile.close();
			
			//Renommage du fichier d'arrivé
			//String new_name=FileName.substring(0,FileName.indexOf(maj_file_version)-1)+FileName.substring(FileName.indexOf(maj_file_version)+10);
			String new_name="update_from_"+num_version+"_to_"+maj_file_version+".jar";
			File f_old = new File(Constantes.DOS_UPDATES+FileName);
			File f_new = new File(Constantes.DOS_UPDATES+new_name);
			f_old.renameTo(f_new);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		end_dl(applyUpdateOnReload);
	}
	public void next_tux() {
		ihm.jb_change_left.setEnabled(true);
		if (index_crt_image<tab_alea.length-1) {
			index_crt_image++;
			ihm.jl_image.setIcon(Resources.getImageIcon("images/tux/tux_"+IHM_AProposDe.nomImage(tab_alea[index_crt_image])+".png",IHM_AProposDe.class));
		}
		if (index_crt_image==tab_alea.length-1) {
			ihm.jb_change_right.setEnabled(false);
			modeNORMAL=false;
		}
	}
	public void previous_tux() {
		ihm.jb_change_right.setEnabled(true);
		if (index_crt_image>0) {
			index_crt_image--;
			ihm.jl_image.setIcon(Resources.getImageIcon("images/tux/tux_"+IHM_AProposDe.nomImage(tab_alea[index_crt_image])+".png",IHM_AProposDe.class));
		}
		if (index_crt_image==0) {
			ihm.jb_change_left.setEnabled(false);
			ihm.modeNORMAL=true;
		}
	}
	public void end_dl(BooleanUpdate applyUpdateOnReload) {
		/**Je change le JPanel pour afficher le téléchargement de la mise à jour**/
		//Je supprime le label et met une progressbar
		ihm.remove(ihm.jp_maj_dl);
		//Je cré le nouveau JPanel
		ihm.add(ihm.jp_maj_close,BorderLayout.SOUTH);//Soit ce JPanel
			ihm.jp_maj_close.add(ihm.jl_info_close,BorderLayout.CENTER);
			ihm.jp_maj_close.add(ihm.jp_close,BorderLayout.EAST);
				ihm.jp_close.add(ihm.jb_restart);
				ihm.jp_close.add(ihm.jb_close);
		//J'actualise l'interface
		ihm.repaint();
		ihm.validate();
		
		//Le programme principale sait qu'il faudra installer les mises à jour
		applyUpdateOnReload.setValue(true);
	}
}
class Event_AProposDe implements ActionListener {
	IHM_AProposDe ihm;
	int index_crt_image;
	int[] tab_alea;
	String titre;
	BooleanUpdate applyUpdateOnReload;
	public Event_AProposDe(IHM_AProposDe ihm,int index_crt_image,int[] tab_alea,String titre,BooleanUpdate applyUpdateOnReload) {
		this.ihm=ihm;
		this.index_crt_image=index_crt_image;
		this.tab_alea=tab_alea;
		this.titre=titre;
		this.applyUpdateOnReload=applyUpdateOnReload;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj=arg0.getSource();
		if(obj instanceof JButton) {
			JButton jb = (JButton)obj;
			if (jb == ihm.jb_change_left) {
				ihm.previous_tux();
				//Timer
				ihm.minuteur.setInitialDelay(20000);
				ihm.minuteur.restart();//pour le restart(); il faut le InitialDelay
			} else if (jb == ihm.jb_change_right) {
				ihm.next_tux();
				//Timer
				ihm.minuteur.setInitialDelay(20000);
				ihm.minuteur.restart();//pour le restart(); il faut le InitialDelay
			} else if (jb == ihm.jb_maj) {
				/* Je crée un Thread séparé parce que l'actualisation de l'interface
				 * (après changement de JPanel et pendant le téléchargement du fichier) n'est pas automatique.
				 * Il se fait UNIQUEMENT quand le ActionListener est totalement fini.
				 * Ce qui bloque aussi les boutons "appuyer" jusqu'à la fin du ActionListener.
				 * 
				 * Avec ce Thread, le calcul (téléchargement) se fait symultanément à l'actualisation de l'interface
				 */
				Thread th = new Thread() {
					public void run() {
						/**Je change le JPanel pour afficher le téléchargement de la mise à jour**/
						//Je supprime le label et met une progressbar
						ihm.remove(ihm.jp_maj);
						//Je crée le nouveau JPanel
						ihm.add(ihm.jp_maj_dl,BorderLayout.SOUTH);//Soit ce JPanel
							ihm.jp_maj_dl.add(ihm.jl_dl,BorderLayout.WEST);
							ihm.jp_maj_dl.add(ihm.jp_progress_total_dl,BorderLayout.CENTER);
								ihm.jp_progress_total_dl.add(ihm.jp_progress_dl,BorderLayout.WEST);
									ihm.jp_progress_dl.add(ihm.jpb_maj);
								ihm.jp_progress_total_dl.add(ihm.jl_valeur,BorderLayout.CENTER);
							ihm.jp_maj_dl.add(ihm.jb_masquer,BorderLayout.EAST);
						//J'actualise l'interface
						ihm.repaint();
						ihm.validate();
						/** Je lance le téléchargement du fichier **/
						ihm.download(IHM_AProposDe.maj_file_link,applyUpdateOnReload);
					}
				};
				//Lancement du Thread;
				th.start();
			
			} else if (jb == ihm.jb_masquer) {
				ihm.setVisible(false);
			} else if (jb == ihm.jb_ok || jb == ihm.jb_close) {
				ihm.dispose();
			} else if (jb == ihm.jb_restart) {
				
				Thread th = new Thread() {
					public void run() {
						//Création de la commande du type : scripts\\exec.bat updates\\update_from_2010.07.15_to_2010.07.16.jar
						String cmd="scripts\\exec.bat "+Constantes.DOS_UPDATES+"update_from_"+IHM_AProposDe.num_version+"_to_"+IHM_AProposDe.maj_file_version+".jar";
						//j'exceute cette commande
						try {
							Runtime.getRuntime().exec(cmd);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				//Lancement du Thread;
				th.start();
				
				System.exit(0);
			}
		} else if (obj instanceof Timer) {
			Timer t = (Timer)obj;
			if (t==this.ihm.minuteur) {
				ihm.minuteur.setDelay(5000);
				//Je fais défiler les Tux
				if (ihm.modeNORMAL) ihm.next_tux();
				else ihm.previous_tux();
			}
		}
	}
}