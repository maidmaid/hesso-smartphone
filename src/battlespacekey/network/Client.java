package battlespacekey.network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * La classe Client permet d'établir une relation réseau TCP avec un serveur
 * @author /DM/
 */
public class Client
{
	private int id;
	private ArrayList<ClientListener> listeners;
	private Socket socket;
	private PrintWriter out;
    private BufferedReader in;
    private Thread trdReception;
    
    /**
     * Crée un Client
     * @param host adresse IP du serveur
     * @throws UnknownHostException
     * @throws IOException
     * @author /DM/
     */
    public Client(String host) throws UnknownHostException, IOException
    {
    	init();
    	connect(host);
    }
    
    /**
     * Crée un Client en ajouter le listener avant la connexion
     * @param host adresse IP du serveur
     * @param listener écouteur du Client
     * @throws UnknownHostException
     * @throws IOException
     * @author /DM/
     */
    public Client(String host, ClientListener listener) throws UnknownHostException, IOException
    {
    	init();
    	addClientListener(listener);
    	connect(host);
    }
    
    /**
     * Crée un Client en lui passant une socket déjà prête à l'emploi
     * @param socket socket connecté au server
     * @author /DM/
     */
    public Client(Socket socket)
    {
    	init();
    	this.socket = socket;
    	initSocket();
    }
    
    /**
     * Crée un Client sans encore de connexion
     * @author /DM/
     */
    public Client()
    {
    	init();
    }
    
    /**
     * Se connecte au serveur
     * @param host adresse IP du serveur
     * @throws UnknownHostException
     * @throws IOException
     * @author /DM/
     */
    public void connect(String host) throws UnknownHostException, IOException
    {
    	socket = new Socket(host, 6023);
    	initSocket();
    }
    
    /**
     * Ferme la connexion du Client au serveur
     * @author /DM/
     */
    public void close()
    {
    	listeners.clear();
    	
    	trdReception.interrupt();
    	
    	try
		{
    		out.close();
        	in.close();
			socket.close();
		}
		catch(IOException e)
		{

		}
    }
    
    /**
     * Initialise le Client
     * @author /DM/
     */
    public void init()
    {
    	listeners = new ArrayList<>();
    	id = -1;
    }
    
    /**
     * Initialise la socket du Client
     * @author /DM/
     */
    private void initSocket()
    {    	
    	try
    	{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		}
    	catch (IOException e)
		{
			e.printStackTrace();
		}
    	
    	trdReception = new Thread(new Reception());
    	trdReception.start();
    }
    
    /**
     * Définit l'ID du Client
     * @author /DM/ 
     */
    public void setId(int id) 
    {
    	this.id = id;
    }
    
    /**
     * Retourne l'ID du client
     * @author /DM/
     */
    public int getId()
    {
    	return id;
    }
    
    /**
	 * Efface un ecouteur de client
	 * @param listener ClientListener Ecouteur de client
	 * @author /DM/
	 */
	public void removeClientListener(ClientListener listener)
	{
		listeners.remove(listener);
	}

	/**
	 * Ajoute un ecouteur de client
	 * @param listener ClientListener Ecouteur de client
	 * @author /DM/
	 */
	public void addClientListener(ClientListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Emet le signal qu'un nouveau message a ete recu
	 * @param message String message 
	 * @author /DM/
	 */
	private void messageReceivedSignal(String message)
	{
		for (ClientListener cl : listeners)
		{
			cl.messageReceived(message);
		}
	}
	
	/**
	 * Appelle la méthode disconnected des ClientListener
	 * @author /DM/
	 */
	private void disconnectedSignal()
	{
		for (ClientListener cl : listeners)
		{
			cl.disconnected(this);
		}
	}
	
	/**
	 * Envoie un message au server
	 * @param message message à envoyer au server
	 * @author /DM/
	 */
    public void send(String message)
    {
    	out.println(message);
        out.flush();
    }
    
    /**
     * La classe Reception gère les réceptions de message provenant du serveur.
     * Elle est est gérée dans un Thread
     * @author /DM/
     */
    class Reception implements Runnable
    {
    	/**
    	 * Méthode run du Thread
    	 * @author /DM/
    	 */
		public void run()
		{
			boolean connected = true;
			
			while(connected)
			{
				 try
		            {
					 	String line = in.readLine();
		            	messageReceivedSignal(line);
			        }
		            catch (IOException e)
		            {     
		            	connected = false;
		            	disconnectedSignal();
		                e.printStackTrace();
		            }
	        }
		}
    }
}