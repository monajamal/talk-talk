package ihm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import components.JHyperTextLink;

@SuppressWarnings("serial")
public class AProposDe extends JDialog {

	private final JPanel jp_image = new JPanel();
	private JLabel lblTuxteam;
	private JHyperTextLink jhtl_licence;
	private JHyperTextLink jhtl_projet;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AProposDe dialog = new AProposDe();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AProposDe() {
		setResizable(false);
		//setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		jp_image.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(jp_image, BorderLayout.CENTER);
		jp_image.setLayout(new BorderLayout(0, 0));
		{
			JLabel jl_image = new JLabel("");
			jl_image.setHorizontalAlignment(SwingConstants.CENTER);
			jl_image.setIcon(new ImageIcon(AProposDe.class.getResource("/ihm/images/tux/tux_0001.png")));
			jp_image.add(jl_image, BorderLayout.CENTER);
		}
		{
			JPanel panel = new JPanel();
			jp_image.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new GridLayout(1, 2, 0, 0));
			{
				JButton button = new JButton("");
				button.setIcon(new ImageIcon(AProposDe.class.getResource("/ihm/images/left.png")));
				panel.add(button);
			}
			{
				JButton button = new JButton("");
				button.setIcon(new ImageIcon(AProposDe.class.getResource("/ihm/images/right.png")));
				panel.add(button);
			}
		}
		{
			JPanel jp_infos = new JPanel();
			getContentPane().add(jp_infos, BorderLayout.EAST);
			jp_infos.setLayout(new BorderLayout(0, 0));
			{
				JPanel jp_titre = new JPanel();
				jp_infos.add(jp_titre, BorderLayout.NORTH);
				jp_titre.setLayout(new BorderLayout(0, 0));
				{
					JLabel jl_titre_produit = new JLabel("Titre du produit");
					jl_titre_produit.setFont(new Font("Tahoma", Font.BOLD, 30));
					jp_titre.add(jl_titre_produit, BorderLayout.NORTH);
				}
				{
					JLabel jl_sous_titre = new JLabel("Description du produit");
					jp_titre.add(jl_sous_titre);
				}
			}
			{
				JPanel jp_details = new JPanel();
				jp_infos.add(jp_details, BorderLayout.CENTER);
				jp_details.setLayout(new BorderLayout(0, 0));
				{
					JPanel jp_details_left = new JPanel();
					jp_details.add(jp_details_left, BorderLayout.WEST);
					jp_details_left.setLayout(new GridLayout(7, 1, 0, 0));
					{
						JLabel lblEquipe = new JLabel("Equipe :");
						lblEquipe.setFont(new Font("Tahoma", Font.BOLD, 11));
						jp_details_left.add(lblEquipe);
					}
					{
						JLabel lblDveloppeurs = new JLabel("Développeurs :     ");
						lblDveloppeurs.setFont(new Font("Tahoma", Font.BOLD, 11));
						jp_details_left.add(lblDveloppeurs);
					}
					{
						JLabel lblProjet = new JLabel("Projet :");
						lblProjet.setFont(new Font("Tahoma", Font.BOLD, 11));
						jp_details_left.add(lblProjet);
					}
					{
						JLabel lblVersion = new JLabel("Version :");
						lblVersion.setFont(new Font("Tahoma", Font.BOLD, 11));
						jp_details_left.add(lblVersion);
					}
					{
						JLabel lblLicence = new JLabel("Licence :");
						lblLicence.setFont(new Font("Tahoma", Font.BOLD, 11));
						jp_details_left.add(lblLicence);
					}
					{
						JLabel lblSupport = new JLabel("Support :");
						lblSupport.setFont(new Font("Tahoma", Font.BOLD, 11));
						jp_details_left.add(lblSupport);
					}
					{
						JLabel lblContact = new JLabel("Contact :");
						lblContact.setFont(new Font("Tahoma", Font.BOLD, 11));
						jp_details_left.add(lblContact);
					}
				}
				{
					JPanel jp_details_right = new JPanel();
					jp_details.add(jp_details_right, BorderLayout.CENTER);
					jp_details_right.setLayout(new GridLayout(7, 1, 0, 0));
					{
						lblTuxteam = new JLabel("Tux-Team");
						jp_details_right.add(lblTuxteam);
					}
					{
						JLabel lblDamienFinckAurlien = new JLabel("Damien FINCK & Aurélien MATHELIN");
						jp_details_right.add(lblDamienFinckAurlien);
					}
					{
						jhtl_projet = new JHyperTextLink("http://code.google.com/p/tux-team","http://code.google.com/p/tux-team");
						jp_details_right.add(jhtl_projet);
					}
					{
						JLabel label = new JLabel("2010.09.12");
						jp_details_right.add(label);
					}
					{
						jhtl_licence = new JHyperTextLink("GNU General Public License","http://www.gnu.org/licenses/gpl.html");
						jp_details_right.add(jhtl_licence);
					}
					{
						JLabel label = new JLabel("New label");
						jp_details_right.add(label);
					}
					{
						JLabel lblTux = new JLabel("tux-team@googlegroups.com");
						jp_details_right.add(lblTux);
					}
				}
			}
			{
				JPanel jp_copyright = new JPanel();
				jp_infos.add(jp_copyright, BorderLayout.SOUTH);
				{
					Component horizontalStrut = Box.createHorizontalStrut(20);
					jp_copyright.add(horizontalStrut);
				}
				{
					JLabel jl_copyright = new JLabel("Copyright © 2010 | Tux-Team. Tous droits réservés.");
					jp_copyright.add(jl_copyright);
					jl_copyright.setFont(new Font("Tahoma", Font.BOLD, 11));
				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(20);
					jp_copyright.add(horizontalStrut);
				}
			}
		}
		{
			JPanel jp_bas = new JPanel();
			getContentPane().add(jp_bas, BorderLayout.SOUTH);
			jp_bas.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblAuncueMise = new JLabel("Auncue mise à jour.");
				lblAuncueMise.setIcon(new ImageIcon(AProposDe.class.getResource("/ihm/images/valide.png")));
				jp_bas.add(lblAuncueMise, BorderLayout.WEST);
			}
			{
				JPanel jp_ok_maj = new JPanel();
				jp_bas.add(jp_ok_maj, BorderLayout.EAST);
				{
					JButton btnMettreJour = new JButton("Mettre à jour");
					jp_ok_maj.add(btnMettreJour);
				}
				{
					JButton jb_ok = new JButton("OK");
					jp_ok_maj.add(jb_ok);
					jb_ok.setActionCommand("OK");
					getRootPane().setDefaultButton(jb_ok);
				}
			}
		}
		
		
		setSize(550,270);//Dimensionner la fenêtre
		setLocationRelativeTo(null);
		/** Look & Feel **/
		//Divers.setLookAndFeel(this);
	}
}