package battlespacekey.player;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * La classe AvatarManager permet de gérer une liste d'avatar
 * @author /DM/
 */
public class AvatarManager extends ArrayList<Avatar>
{
	/**
	 * Crée un AvatarManager
	 * @author /DM/
	 */
	public AvatarManager()
	{
		super();
		
		for(File iconFile : Avatar.getIconFiles())
		{
			Avatar a = new Avatar(iconFile.getName());
			add(a);
		}
	}
	
	/**
	 * Retourne un avatar en fonction du nom de l'icone
	 * @return Avatar correspendant au nom de fichier. Si non trouvé, Avatar vide.
	 * @author /DM/
	 */
	public Avatar getAvatar(String fileIcon)
	{
		for(Avatar avatar : this)
		{
			if(avatar.getIconFile().equals(fileIcon))
			{
				return avatar;
			}
		}
		
		return new Avatar();
	}
	
	/**
	 * Retourne une liste d'Avatar non utilisé par les Player
	 * @return liste d'Avatar
	 * @author /DM/
	 */
	public ArrayList<Avatar> getAvailablesAvatars()
	{
		ArrayList<Avatar> available = new ArrayList<>();
		
		for(Avatar avatar : this)
		{
			if(!avatar.isUsed())
			{
				available.add(avatar);
			}
		}
		
		return available;
	}
	
	/**
	 * Retourne un Avatar aléatoire non utilisé par les Player
	 * @return Avatar aléatoire non utilisé
	 */
	public Avatar getRandomAvailableAvatar()
	{
		Random random = new Random();
		
		ArrayList<Avatar> available = getAvailablesAvatars();
		
		int i = Math.abs(random.nextInt() % available.size());
		
		return available.get(i);
	}
	
	/**
	 * Réinitialise la liste d'Avatar
	 * @author /DM/
	 */
	public void reset()
	{
		for(Avatar a : this)
		{
			a.reset();
		}
	}
}
