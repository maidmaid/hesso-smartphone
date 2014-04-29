package smartphone;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Application extends JPanel
{
	private ArrayList<PageAppli> pages;
	private PageAppli previousPage;
	private String icon;
	private String name;
	
	/**
	 * Constructeur Application
	 * @author SB
	 */
	public Application()
	{
		super();
		pages = new ArrayList<>();
		setLayout(new GridLayout());
		setIcon("Images/" + getClass().getSimpleName() + ".png");
	}
	
	/**
	 * Modifier l'icone de l'application
	 * @param icon
	 */
	public void setIcon(String icon)
	{
		this.icon = icon;
	}
	
	/**
	 * Obtenir l'icone de l'application
	 * @author SB
	 */
	public String getIcon()
	{
		return this.icon;
	}
	
	/**
	 * Modifier le nom de l'application
	 * @author SB
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Obtenir le nom de l'application
	 * @author SB
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Rend actif un panel
	 * @param page
	 * @author /DM/
	 */
	protected void setActivePage(PageAppli page)
	{
		removeAll();
		add(page);
		page.requestFocus();
		repaint();
		revalidate();
	}
	
	/**
	 * Affiche une page
	 * @param alias alias de la page
	 * @author DM
	 */
	public void setActivePage(String alias)
	{
		PageAppli page = getPage(alias);
		
		if(page != null)
		{
			setActivePage(page);
		}
	}
	
	/**
	 * Retourne une page en fonction de son alias
	 * @param alias alias de la page
	 * @author DM
	 */
	private PageAppli getPage(String alias)
	{
		for(PageAppli page : pages)
		{
			if(page.getAlias() == alias)
			{
				return page;
			}
		}
		
		return null;
	}
	
	/**
	 * Retourne toutes les pages de l'application
	 * @author DM
	 */
	public ArrayList<PageAppli> getPages()
	{
		return pages;
	}
}
