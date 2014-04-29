package battlespacekey.network;

import battlespacekey.data.DataListener;

/**
 * Cette inferface contient les événements du serveur
 * @author /DM/
 */
public interface ServerListener
{
	/**
	 * Evenement lors de la connexion d'un nouveau Client
	 * @param client
	 * @author /DM/
	 */
	public void clientConnected(Client client);
}
