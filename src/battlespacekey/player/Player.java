package battlespacekey.player;

import java.awt.Color;

/**
 * La classe Player permet la gestion d'un joueur dans le jeu
 * @author /DM/
 */
public class Player extends Box
{
	private int score;
	private int targetScore;
	private int ranking;
	private boolean rankingModified;
	private int id;
	private boolean isMyPlayer;
	private String pseudo;
	private boolean host;
	private Avatar avatar;

	/**
	 * Crée un Player
	 * @author /DM/
	 */
	public Player()
	{
		super();
		init();
	}
	
	/**
	 * Crée un Player
	 * @param id ID du Player
	 * @author /DM/
	 */
	public Player(int id)
	{
		super();
		init();
		setId(id);
	}
	
	/**
	 * Crée un Player
	 * @param id ID du player
	 * @param pseudo pseudo du player
	 * @author /DM/
	 */
	public Player(int id, String pseudo)
	{
		super();
		init();
		setId(id);
		setPseudo(pseudo);
	}

	/**
	 * Retourne l'ID
	 * @return ID du Player
	 * @author /DM/
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Définit l'ID
	 * @param id ID du Player
	 * @author /DM/
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * Définit le score
	 * @return score du Player
	 * @author /DM/
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * Ajoute des points au score actuel
	 * @param point points à ajouter
	 * @author /DM/
	 */
	public void addScore(int point)
	{
		setScore(score + point);
	}
	
	/**
	 * Initialise le Player
	 * @author /DM/
	 */
	private void init()
	{
		setBackground(Color.WHITE);
		setMyPlayer(false);
		setHost(false);
		setPseudo("-");
		setId(-1);
		setTargetScore(100);
		reset();
	}
	
	/**
	 * Réinitialise le Player
	 * @author /DM/
	 */
	public void reset()
	{
		setScore(0);
		setRanking(0);
		setRankingModified(true);
		setAvatar(new Avatar());
	}
	
	/**
	 * Définit le score
	 * @param score score du Player
	 * @author /DM/
	 */
	public void setScore(int score)
	{	
		this.score = score;
		super.setScore(score);
		
		//DM/ Vérifie si le score cible est atteind 
		if(isFinish())
		{
			setBackground(Color.GREEN);
		}
	}
	
	/**
	 * Définit le classement
	 * @param ranking classement du Player
	 * @author /DM/
	 */
	public void setRanking(int ranking)
	{
		if(this.ranking != ranking)
		{
			this.ranking = ranking;
			this.rankingModified = true;
			super.setRanking(ranking);
		}
	}
	
	/**
	 * Retourne si le classement a été modifié
	 * @return état classement
	 * @author /DM/
	 */
	public boolean rankingModified()
	{
		return rankingModified;
	}
	
	/**
	 * Définit si le classement a été modifié
	 * @param rankingModified true si le classement a été modifié
	 * @author /DM/
	 */
	public void setRankingModified(boolean rankingModified)
	{
		this.rankingModified = rankingModified;
	}

	/**
	 * Définit si le Player est le mien
	 * @param isMyPlayer true si c'est mon Player
	 * @author /DM/
	 */
	public void setMyPlayer(boolean isMyPlayer)
	{
		if(isMyPlayer)
		{
			lblPseudo.setOpaque(true);
			lblPseudo.setBackground(Color.YELLOW);
		}
		
		this.isMyPlayer = isMyPlayer;
	}
	
	/** 
	 * Retourne si le Player est le mien
	 * @return true si c'est mon Player
	 * @author /DM/
	 */
	public boolean isMyPlayer()
	{
		return isMyPlayer;
	}

	/**
	 * Définit le pseudo
	 * @param pseudo Pseudo du Player
	 * @author /DM/
	 */
	public void setPseudo(String pseudo)
	{
		this.pseudo = pseudo;
		
		//DM/ Met a jour l'avatar
		if(avatar != null)
		{
			avatar.setText(pseudo);
		}
		
		super.setPseudo(pseudo);
	}
	
	/**
	 * Retourne le pseudo
	 * @return pseudo du Player
	 * @author /DM/
	 */
	public String getPseudo()
	{
		return pseudo;
	}
	
	/**
	 * Définit si le Player est l'hôte de la partie
	 * @param host true s'il est l'hôte
	 * @author /DM/
	 */
	public void setHost(boolean host)
	{
		this.host = host;
	}
	
	/**
	 * Retourne si le Player est l'hôte de la partie
	 * @return true s'il est l'hôte
	 * @author /DM/
	 */
	public boolean isHost()
	{
		return host;
	}
	
	/**
	 * Retourne si le player est valide
	 * @return true s'il est valide
	 * @author /DM/
	 */
	public boolean isValid()
	{
		boolean isValid = (id != -1);
		return isValid;
	}
	
	/**
	 * Définit l'Avatar du Player
	 * @param avatar Avatar du player
	 * @author /DM/
	 */
	public void setAvatar(Avatar avatar)
	{
		//DM/ Ancien avatar
		if(this.avatar != null)
		{
			this.avatar.reset();
		}

		//DM/ Nouvel avatar
		this.avatar = avatar;
		avatar.setUsed(true);
		avatar.setText(getPseudo());
		
		super.setAvatar(avatar);
	}
	
	/**
	 * Retourne l'Avatar 
	 * @return Avatar du Player
	 * @author /DM/
	 */
	public Avatar getAvatar()
	{
		return avatar;
	}
	
	/**
	 * Définit le score cible du Player
	 * @param targetScore score cible à atteindre par le Player
	 * @author /DM/
	 */
	public void setTargetScore(int targetScore)
	{
		this.targetScore = targetScore;
	}
	
	/**
	 * Retourne si le Player a terminé la partie
	 * @return true s'il a terminé
	 * @author /DM/
	 */
	public boolean isFinish()
	{
		boolean isFinish = score >= targetScore;
		return isFinish;
	}
}