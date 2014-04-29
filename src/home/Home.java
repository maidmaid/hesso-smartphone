package home;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import smartphone.Application;
import smartphone.Smartphone;
import about.About;
import battlespacekey.BattleSpaceKey;
import contact.ContactAppli;

public class Home extends Application
{
	private ArrayList<Application> applications = new ArrayList<>();
	public Smartphone smartphone;
	
	/**
	 * Constructeur Home
	 * @param smartphone
	 * @author DM / SB
	 */
	public Home(Smartphone smartphone)
	{
		this.smartphone = smartphone;
		setName("Home");
		setIcon("Images/Home.png");
		setLayout(new FlowLayout());
		setBackground(Color.WHITE);
		
		Application contact = new ContactAppli();
		Application game = new BattleSpaceKey();
		Application about = new About();
		
		applications.add(contact);
		applications.add(game);
		applications.add(about);
		
		//SB/ Ajouter des applications vides dans le menu
		for (int i = 4; i < 13; i++)
		{
			String s = "Appli " + i;
			Application appli = new Application();
			appli.setName(s);
			appli.setIcon("Images/Empty.png");
			applications.add(appli);
		}
		
		for (Application appli : applications)
		{
			String name = appli.getName();
			String icon = appli.getIcon();
			
			JButton b = new JButton(name);
			b.setBackground(Color.WHITE);
			b.setBorderPainted(false);
			b.setVerticalTextPosition(SwingConstants.BOTTOM);
			b.setHorizontalTextPosition(SwingConstants.CENTER);
			OnClickAppli launcher = new OnClickAppli(appli);
			b.setIcon(new ImageIcon(icon));
			b.addMouseListener(launcher);
			add(b);
		}
	}
	
	/**
	 * Executer une action lors d'un clic sur une application
	 * @author SB
	 *
	 */
	public class OnClickAppli extends MouseAdapter
	{
		private Application appli;
		
		public OnClickAppli(Application appli)
		{
			this.appli = appli;
		}
		
		public void mouseClicked(MouseEvent e)
		{
			smartphone.setApplication(appli);
		}
	}
}
