package about;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;

import smartphone.Application;
import smartphone.PageAppli;

public class PageMainAbout extends PageAppli
{
	private JLabel author;
	private JLabel version;
	
	/**
	 * Constructeur PageMainAbout
	 * @param appli
	 * @author SB
	 */
	public PageMainAbout(Application appli)
	{
		super(appli, "main");
		init();
	}
	
	/**
	 * Initialisation
	 * @author SB
	 */
	public void init()
	{
		setLayout(new FlowLayout());
		setBackground(Color.GREEN);
		author = new JLabel("Siméon Bobylev (SB) & Dany Maillard (DM)");
		version = new JLabel("v1.9");
		add(author);
		add(version);
	}
}
