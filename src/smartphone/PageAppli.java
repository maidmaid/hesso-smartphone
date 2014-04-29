package smartphone;

import java.awt.Color;
import javax.swing.JPanel;

public class PageAppli extends JPanel
{
	protected Application appli;
	protected String alias;
	
	/**
	 * Constructeur PageAppli
	 * @param appli
	 * @param alias
	 * @author DM / SB
	 */
	public PageAppli(Application appli, String alias)
	{
		this.appli = appli;
		this.alias = alias;
		appli.getPages().add(this);
		setBackground(Color.WHITE);
	}
	
	/**
	 * Obtenir l'alias de la page
	 * @author DM
	 */
	public String getAlias()
	{
		return alias;
	}
}
