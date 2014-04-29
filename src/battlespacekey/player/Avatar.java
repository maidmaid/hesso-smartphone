package battlespacekey.player;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;;

/**
 * La classe Avatar permet au joueur de définir son personnage sous forme d'image
 * @author /DM/
 */
public class Avatar extends JLabel
{
	private String iconFile;
	private String iconsDir;
	private boolean used;
	
	/**
	 * Crée un Avatar
	 * @author /DM/
	 */
	public Avatar()
	{
		init();
	}
	
	/**
	 * Crée un Avatar
	 * @param iconFile nom du fichier de l'icone
	 * @author /DM/
	 */
	public Avatar(String iconFile)
	{
		init();
		setIconFile(iconFile);
	}
	
	/**
	 * Initialise l'Avatar
	 * @author /DM/
	 */
	private void init()
	{
		setHorizontalTextPosition(JLabel.CENTER);
		setVerticalTextPosition(JLabel.BOTTOM);
		iconsDir = "src/battlespacekey/images/64/";
		used = false;
	}
	
	/**
	 * Retourne le nom du fichier de l'icone
	 * @author /DM/
	 */
	public String getIconFile()
	{
		return iconFile;
	}
	
	/**
	 * Définit le nom du fichier de l'icone
	 * @author /DM/
	 */
	public void setIconFile(String iconFile)
	{
		this.iconFile = iconFile;
		ImageIcon icon = new ImageIcon(iconsDir + iconFile);
		setIcon(icon);
	}
	
	/**
	 * Retourne une liste de toutes les icones possibles
	 * @return liste de toutes les icones possibles
	 * @author /DM/
	 */
	static public File[] getIconFiles()
	{
		File iconsDir = new File("src/battlespacekey/images/64/");
		return iconsDir.listFiles();
	}
	
	/**
	 * Définit si l'avatar est utilisé
	 * @author /DM/
	 */
	public void setUsed(boolean used)
	{
		this.used = used;
	}
	
	/**
	 * Retourne si l'avatar est déjà utilisé
	 * @author /DM/
	 */
	public boolean isUsed()
	{
		return used;
	}

	/**
	 * Réinitialise l'avatar
	 * @author /DM/
	 */
	public void reset()
	{
		setUsed(false);
		setText("");
	}
}
