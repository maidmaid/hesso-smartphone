package smartphone;

import home.Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Smartphone extends JFrame
{
	private JPanel head;
	private Application main;
	private JPanel foot;
	private Application appliActive;
	
	private JLabel lblHeadLeft;
	private JLabel lblHeadCenter;
	private JLabel lblHeadRight;
	
	private JButton btnCloseAll;
	private JButton btnHome;
	private JButton btnReturn;
	
	private Home home;
	private Timer t;

	/**
	 * Constructeur Smartphone
	 * @author SB
	 */
	public Smartphone()
	{
		init();
	}
	
	/**
	 * Initialisation
	 * @author SB
	 */
	public void init()
	{
		head = new JPanel();
		head.setBorder(new BevelBorder(BevelBorder.RAISED));
		main = new Application();
		foot = new JPanel();
		appliActive = new Application();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 600);
		setVisible(true);
		
		//SB/ Labels pour les 3 affichages du menu haut
		lblHeadLeft = new JLabel("", JLabel.CENTER);
		lblHeadCenter = new JLabel("", JLabel.CENTER);
		lblHeadRight = new JLabel("", JLabel.CENTER);
		
		//SB/ Boutons pour le menu bas
		btnCloseAll = new JButton();
		btnCloseAll.setEnabled(false);
		btnCloseAll.setBackground(Color.WHITE);
		btnHome = new JButton("");
		btnHome.setBackground(Color.WHITE);
		btnReturn = new JButton();
		btnReturn.setEnabled(false);
		btnReturn.setBackground(Color.WHITE);

		//SB/ Ajout de tous les pannels/labels/boutons
		add(head, BorderLayout.NORTH);
		head.setLayout(new GridLayout(1, 3));
		head.setBackground(Color.WHITE);
		head.add(lblHeadLeft, BorderLayout.WEST);
		head.add(lblHeadCenter, BorderLayout.CENTER);
		head.add(lblHeadRight, BorderLayout.EAST);
		
		add(foot, BorderLayout.SOUTH);
		foot.setLayout(new GridLayout(1, 3));
		foot.setBackground(Color.green);
		foot.add(btnCloseAll, BorderLayout.WEST);
		foot.add(btnHome, BorderLayout.CENTER);
		foot.add(btnReturn, BorderLayout.EAST);
		
		//SB/ Afficher la date et l'heure au démarrage
		lblHeadRight.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		lblHeadCenter.setText(new SimpleDateFormat("dd MMM yyyy").format(new Date()));
		
		//SB/ Initialiser bouton Home
		home = new Home(this);
		btnHome.setIcon(new ImageIcon(home.getIcon()));
		setApplication(home);
		btnHome.addMouseListener(new HomeAction());
		
		//SB/ Timer pour l'heure uniquement
		t = new Timer(900, new TimerAction());
		t.start();
	}
	
	/**
	 * Redéfinir le menu central
	 * @author SB
	 * @param appli
	 */
	public void setApplication(Application appli)
	{	
		getContentPane().remove(appliActive);
		getContentPane().add(appli, BorderLayout.CENTER);
		revalidate();
		repaint();
		appliActive = appli;
		lblHeadLeft.setText(appli.getName());
	}
	
	/**
	 * Permet de revenir au menu principal
	 * @author SB
	 *
	 */
	private class HomeAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			setApplication(home);
		}
	}
	
	/**
	 * Permet d'utiliser un timer
	 * @author SB
	 */
	private class TimerAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			lblHeadRight.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		}
	}
}
