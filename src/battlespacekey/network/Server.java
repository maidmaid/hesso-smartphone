package battlespacekey.network;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * La classe Server crée un serveur TCP surlequel des clients peuvent se connecter
 * @author /DM/
 */
public class Server
{
	private ArrayList<ServerListener> listeners;
	private ServerSocket server;
	private ArrayList<Client> clients;
	private Thread trdConnexion;
	
	/**
	 * Crée un Serveur non démmaré
	 * @throws IOException
	 * @author /DM/
	 */
	public Server() throws IOException
	{
		listeners = new ArrayList<>();
		clients = new ArrayList<>();
	}
	
	/**
	 * Démmare le serveur
	 * @throws IOException
	 * @author /DM/
	 */
	public void start() throws IOException
	{
		server = new ServerSocket(6023);
		trdConnexion = new Thread(new Connection());
		trdConnexion.start();
	}
	
	/**
	 * Ferme la connexion du serveur
	 * @throws IOException
	 * @author /DM/
	 */
	public void close() throws IOException
	{
		for(Client c : clients)
		{
			c.close();
		}
		
		clients.clear();
		trdConnexion.interrupt();
		server.close();
	}
	
	/**
	 * Efface un ecouteur de client
	 * @param listener ClientListener Ecouteur de client
	 * @author /DM/
	 */
	public void removeServerListener(ServerListener listener)
	{
		listeners.remove(listener);
	}

	/**
	 * Ajoute un ecouteur de client
	 * @param listener ClientListener Ecouteur de client
	 * @author /DM/
	 */
	public void addServerListener(ServerListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Emet le signal qu'un nouveau message a ete recu
	 * @param message String message 
	 * @author /DM/
	 */
	private void clientConnectedSignal(Client client)
	{
		for (ServerListener sl : listeners)
		{
			sl.clientConnected(client);
		}
	}
	
	/**
	 * Retourne un Client en fonction de l'ID
	 * @param id ID du client à retourner
	 * @return retourne le Client. NULL si aucun ID correspondant
	 * @author /DM/
	 */
	public Client getClient(int id)
	{
		for(Client c : clients)
		{
			if(c.getId() == id)
			{
				return c;
			}
		}
		
		return null;
	}
	
	/**
	 * Envoie un message à tous les clients 
	 * @param message message à envoyer
	 * @author /DM/
	 */
	public void broadcast(String message)
	{
		broadcast(message, null);
	}
	
	/**
	 * Envoie un message à tous les clients, sauf à un client précis
	 * @param message message à envoyer
	 * @param exception Client à exclure de l'envoi
	 * @author /DM/
	 */
	public void broadcast(String message, Client exception)
	{
		for (Client c : clients)
		{
			if(!c.equals(exception))
			{
				c.send(message);
			}
		}
	}
	
	/**
	 * La classe Connection gère les nouvelles connections de Client.
	 * La classe est gérée par un Thread
	 * @author /DM/
	 */
	public class Connection implements Runnable
	{
		/**
		 * Méthode run pour le Thread
		 * @author /DM/
		 */
	    public void run()
	    {
	    	try
	    	{
	    		while(true)
	    		{
					Socket socket = server.accept();
					Client client = new Client(socket);
					clients.add(client);
					clientConnectedSignal(client);
	    		}
			}
	    	catch (IOException e)
			{
				e.printStackTrace();
			}
	    }
	}

	/**
	 * Retourne la liste des Clients
	 * @return liste des Clients
	 * @author /DM/
	 */
	public ArrayList<Client> getClients()
	{
		return clients;
	}
}