package battlespacekey.data;

/**
 * L'interface DataListener contient les évenements de Data
 * @author /DM/
 */
public interface DataListener
{
	/**
	 * Evenement lors de la réception d'un message de type id:
	 * le serveur attribue un ID au Player
	 * @param id ID que le serveur attribue au Player
	 * @author /DM/
	 */
	public void id(int id);
	
	/**
	 * Evenement lors de la réception d'un message de type connection:
	 * le serveur indique qu'un client s'est connecté
	 * @param id ID du Player qui s'est connecté
	 * @author /DM/
	 */
	public void connection(int id);
	
	/**
	 * Evenement lors de la réception d'un message de type deconnection:
	 * le serveur indique qu'un Player s'est déconnecté
	 * @param id ID du Player qui s'est déconnecté
	 * @author /DM/
	 */
	public void deconnection(int id);
	
	/**
	 * Evenement lors de la réception d'un message de type pseudo:
	 * le serveur indique le pseudo d'un Player
	 * @param id ID du Player
	 * @param pseudo pseudo du Player
	 * @author /DM/
	 */
	public void pseudo(int id, String pseudo);
	
	/**
	 * Evenement lors de la réception d'un message de type avatar:
	 * le serveur indique l'avatar d'un Player
	 * @param id ID du Player
	 * @param avatar Avatar du Player
	 * @author /DM/
	 */
	public void avatar(int id, String avatar);
	
	/**
	 * Evenement lors de la réception d'un message de type score:
	 * le serveur indique le score d'un Player
	 * @param id ID du Player
	 * @param score score du Player
	 * @author /DM/
	 */
	public void score(int id, int score);
	
	/**
	 * Evenement lors de la réception d'un message de type startParty:
	 * le serveur indique que la partie commence
	 * @author /DM/
	 */
	public void startParty();
	
	/**
	 * Evenement lors de la réception d'un message de type host:
	 * le serveur indique qui est l'hôte de la partie
	 * @param id ID du Player hôte
	 * @author /DM/
	 */
	public void host(int id);
}
