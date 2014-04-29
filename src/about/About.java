package about;

import smartphone.Application;

public class About extends Application
{
	/**
	 * Constructeur About
	 * @author SB
	 */
	public About()
	{
		setName("About");
		setIcon("Images/Appli_About.png");
		
		PageMainAbout pagMainAbout = new PageMainAbout(this);
		setActivePage(pagMainAbout);
	}
	
	
}
