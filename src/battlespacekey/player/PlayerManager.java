package battlespacekey.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import smartphone.PageAppli;

import contact.Contact;
import contact.ContactManager;


/**
 * La classe PlayerManager permet la gestion d'une liste de Player
 * @author /DM/
 */
public class PlayerManager extends ArrayList<Player>
{
	private Player myPlayer;
	private PlayerComparator comparator;
	private PageAppli pagGame;
	
	/**
	 * Crée un PlayerManager
	 */
	public PlayerManager()
	{
		comparator = new PlayerComparator();
	}
	
	/**
	 * Définit la page de l'application ou les Player apparaitront
	 * @author /DM/
	 */
	public void setPageGame(PageAppli pagGame)
	{
		this.pagGame = pagGame;
	}
	
	/**
	 * Ajoute un Player
	 * @param p Player à ajouter. Vérfie s'il est mon Player.
	 * @author /DM/
	 */
	public boolean add(Player p)
	{		
		//DM/ Sauvegarde mon joueur
		if(p.isMyPlayer())
		{
			myPlayer = p;
		}
		
		pagGame.add(p);
		p.repaint();
		
		return super.add(p);
	}
	
	/**
	 * Crée mon joueur.
	 * Par défaut, récupère les infos dans le Contact owner grâce à ContactManager
	 * @author /DM/
	 */
	public void createMyPlayer()
	{
		//DM/ Récupère le owner
		ContactManager contactManager = new ContactManager();
		Contact owner = contactManager.getOwner();
		
		//DM/ Mon player
		myPlayer = new Player();
		myPlayer.setMyPlayer(true);
		myPlayer.setPseudo(owner.getFirstname());
		add(myPlayer);
	}
	
	/**
	 * Retourne un Player en fonction de son ID 
	 * @author /DM/
	 */
	public Player getPlayer(int id)
	{
		for(Player p : this)
		{
			if(p.getId() == id)
			{
				return p;
			}
		}
		
		Player p = new Player();
		
		return p;
	}

	/**
	 * Supprime un Player
	 * @param ID du Player
	 * @author /DM/
	 */
	public void removePlayer(int id)
	{
		Player p = getPlayer(id);
		pagGame.remove(p);
		remove(p);
		p.getAvatar().setUsed(false);
		pagGame.repaint();
		
	}
	
	/**
	 * Supprime un Player
	 * @param p Player à supprimer
	 * @author /DM/
	 */
	public void removePlayer(Player p)
	{
		removePlayer(p.getId());
	}

	/**
	 * Réajuste la position à l'écran des joueurs.
	 * A utiliser après un changement de score par exemple.
	 * @author /DM/
	 */
	public void readjust()
	{
		int x = 0;
		int y = 0;
		int ranking = 0;
		int late = 0;
		int advance = 0;
		
		//DM/ Trie les joueurs par score
		Collections.sort(this, comparator);

		//DM/ Attribue les nouvelles postions des joueurs
		for (int i = 0; i < size(); i++)
		{
			Player p = get(i); 

			//DM/ Calcule les nouvelles infos des joueurs
			switch (i)
			{
				case 0:
					x = 200 - (p.getWidth() / 2);
					y = 5;
					break;
				case 1:
					x = 95;
					y = 60;
					break;
				case 2:
					x = 245;
					y = 60;
					break;
				default:
					x = 200 - p.getWidth() + (i % 2 == 0 ? 0 : p.getWidth());
					y = 230 + ((p.getHeight() + 1) * (i - 3));
					break;
			}

			ranking = i + 1;
			late = i == 0 ? -1 : get(i - 1).getScore() - p.getScore();
			advance = i == size() - 1 ? -1 : p.getScore() - get(i + 1).getScore();
			
			//DM/ Attribue les nouvelles valeurs
			p.setLate(late);
			p.setAdvance(advance);
			p.setRanking(ranking);
			
			//DM/ Translate seulement si le classement a ete modifie
			if(p.rankingModified())
			{
				p.translate(x, y, 200);
				p.setRankingModified(false);
			}
		}
	}
	
	/**
	 * La classe PlayerComparator permet de trier les Player par rapport à leur score
	 * @author /DM/
	 */
	private class PlayerComparator implements Comparator<Player>
	{
		/**
		 * Compare le score de deux joueurs
		 * @author /DM/
		 */
		public int compare(Player p1, Player p2)
		{
			return p1.getScore() <= p2.getScore() ? 1 : -1;
		}
	}
	
	/**
	 * Retourne mon joueur
	 * @author /DM/
	 */
	public Player getMyPlayer()
	{
		return myPlayer;
	}
	
	/**
	 * Réinitialise tout le PlayerManager
	 * @author /DM/
	 */
	public void reset()
	{
		for(Player p : this)
		{
			pagGame.remove(p);
		}
		clear();
		createMyPlayer();
	}
}