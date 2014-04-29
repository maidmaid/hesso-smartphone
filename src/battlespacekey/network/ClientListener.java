package battlespacekey.network;

/**
 * Cette interface contient les événements du Client
 * @see Client
 * @author /DM/
 */
public interface ClientListener
{
	/**
	 * Evenement lors de la deconnexion d'un client
	 * @param client Client deconnecté
	 * @author /DM/
	 */
	public void disconnected(Client client);
	
	/**
	 * Evenement lors de la reception d'un message du serveur
	 * @param message message du serveur
	 * @author /DM/
	 */
	public void messageReceived(String message);
}
