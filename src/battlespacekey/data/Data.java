package battlespacekey.data;


import java.util.ArrayList;

import smartphone.Application;

import battlespacekey.network.Client;
import battlespacekey.player.Player;


/**
 * La classe Data gère l'encodage/décodage des messages à envoyer
 * @author /DM/
 */
public class Data
{
	private ArrayList<DataListener> listeners;
	
	/**
	 * Crée un Data
	 * @author /DM/
	 */
	public Data()
	{
		listeners = new ArrayList<>();
	}
	
	/**
	 * Efface un DataListener
	 * @param listener DataListener à effacer
	 * @author /DM/
	 */
	public void removeDataListener(DataListener listener)
	{
		listeners.remove(listener);
	}

	/**
	 * Ajoute un DataListener
	 * @param listener DataListener à ajouter
	 * @author /DM/
	 */
	public void addDataListener(DataListener listener)
	{
		listeners.add(listener);
	}

	/**
	 * Appelle l'évenement id() des DataListener
	 * @param id ID du Player
	 * @author /DM/
	 */
	private void idSignal(int id)
	{
		for (DataListener dl : listeners)
		{
			dl.id(id);
		}
	}
	
	/**
	 * Appelle l'évenement connection() des DataListener
	 * @param id ID du Player
	 * @author /DM/
	 */
	private void connectionSignal(int id)
	{
		for (DataListener dl : listeners)
		{
			dl.connection(id);
		}
	}
	
	/**
	 * Appelle l'évenement pseudo() des DataListener
	 * @param id ID du Player 
	 * @param pseudo pseudo du Player
	 * @author /DM/
	 */
	private void pseudoSignal(int id, String pseudo)
	{
		for (DataListener dl : listeners)
		{
			dl.pseudo(id, pseudo);
		}
	}
	
	/**
	 * Appelle l'évenement deconnection() des DataListener
	 * @param id ID du Player
	 * @author /DM/
	 */
	private void deconnectionSignal(int id)
	{
		for(DataListener dl : listeners)
		{
			dl.deconnection(id);
		}
	}
	
	/**
	 * Appelle l'évenement score() des DataListener
	 * @param id ID du Player
	 * @param score score du Player
	 * @author /DM/
	 */
	private void scoreSignal(int id, int score)
	{
		for(DataListener dl : listeners)
		{
			dl.score(id, score);
		}
	}
	
	/**
	 * Appelle l'évenement avatar() des DataListener
	 * @param id ID du Player
	 * @param avatar avatar du Player
	 * @author /DM/
	 */
	private void avatarSignal(int id, String avatar)
	{
		for(DataListener dl : listeners)
		{
			dl.avatar(id, avatar);
		}
	}
	
	/**
	 * Appelle l'évenement host() des DataListerner
	 * @param id ID du Player
	 * @author /DM/
	 */
	private void hostSignal(int id)
	{
		for(DataListener dl : listeners)
		{
			dl.host(id);
		}
	}
	
	/**
	 * Appelle l'évenement startParty() des DataListener
	 * @author /DM/
	 */
	private void startPartySignal()
	{
		for(DataListener dl : listeners)
		{
			dl.startParty();
		}
	}
	
	/**
	 * Decode le message et appelle les differents évenements des DataListener 
	 * @param message String message recu
	 * @author /DM/
	 */
	public void decode(String message)
	{
		String[] arguments = message.split(";");
		
		if(arguments[0].equals("id"))
		{
			int id = Integer.parseInt(arguments[1]);
			idSignal(id);
		}
		if(arguments[0].equals("connection"))
		{
			int id = Integer.parseInt(arguments[1]);
			connectionSignal(id);
		}
		else if(arguments[0].equals("deconnection"))
		{
			int id = Integer.parseInt(arguments[1]);
			deconnectionSignal(id);
		}
		else if(arguments[0].equals("pseudo"))
		{
			int id = Integer.parseInt(arguments[1]);
			String pseudo = arguments[2];
			pseudoSignal(id, pseudo);
		}
		else if(arguments[0].equals("score"))
		{
			int id = Integer.parseInt(arguments[1]);
			int score = Integer.parseInt(arguments[2]);
			scoreSignal(id, score);
		}
		else if(arguments[0].equals("avatar"))
		{
			int id = Integer.parseInt(arguments[1]);
			String avatar = arguments[2];
			avatarSignal(id, avatar);
		}
		else if(arguments[0].equals("start"))
		{
			startPartySignal();
		}
		else if(arguments[0].equals("host"))
		{
			int id = Integer.parseInt(arguments[1]);
			hostSignal(id);
		}
	}

	/**
	 * Encode une liste d'arguments en String
	 * @param arguments arguments à encoder
	 * @return message encodé
	 * @author /DM/
	 */
	private String encode(ArrayList<String> arguments)
	{
		StringBuilder encode = new StringBuilder();
		
		for (String a : arguments)
		{
			encode.append(a);
			encode.append(";");
		}
		
		return encode.toString();
	}
	
	/**
	 * Encode l'ID du Player
	 * @param id ID du player
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodeId(int id)
	{
		ArrayList<String> arguments = new ArrayList<>();
		
		arguments.add("id");
		arguments.add(Integer.toString(id));
		
		return encode(arguments);
	}
	
	/**
	 * Encode la connexion d'un Player
	 * @param id ID du Player
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodeConnection(int id)
	{
		ArrayList<String> arguments = new ArrayList<>();
		
		arguments.add("connection");
		arguments.add(Integer.toString(id));
		
		return encode(arguments);
	}
	
	/**
	 * Envode le pseudo d'un Player
	 * @param id ID du Player
	 * @param pseudo pseudo du Player
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodePseudo(int id, String pseudo)
	{
		ArrayList<String> arguments = new ArrayList<>();
		
		arguments.add("pseudo");
		arguments.add(Integer.toString(id));
		arguments.add(pseudo);
		
		return encode(arguments);
	}
	
	/**
	 * Encode le pseudo d'un Player
	 * @param player Player dont le pseudo est à encoder
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodePseudo(Player player)
	{
		int id = player.getId();
		String pseudo = player.getPseudo();
		return encodePseudo(id, pseudo);
	}
	
	/**
	 * Encode la deconnection d'un Player
	 * @param id ID du Player
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodeDeconnection(int id)
	{
		ArrayList<String> arguments = new ArrayList<>();
		
		arguments.add("deconnection");
		arguments.add(Integer.toString(id));
		
		return encode(arguments);
	}
	
	/**
	 * Encode la déconnexion d'un client
	 * @param client Client deconnecté
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodeDeconnection(Client client)
	{
		int id = client.getId();
		return encodeDeconnection(id);
	}
	
	/**
	 * Encode le score
	 * @param id ID du Player
	 * @param score score du Player
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodeScore(int id, int score)
	{
		ArrayList<String> arguments = new ArrayList<>();
		
		arguments.add("score");
		arguments.add(Integer.toString(id));
		arguments.add(Integer.toString(score));
		
		return encode(arguments);
	}
	
	/**
	 * Encode un score
	 * @param player Player dont il faut encoder le score
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodeScore(Player player)
	{
		int id = player.getId();
		int score = player.getScore();
		return encodeScore(id, score);
	}

	/**
	 * Encode une connection
	 * @param player Player dont il faut encoder la connexion
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodeConnection(Player player)
	{
		int id = player.getId();
		return encodeConnection(id);
	}
	
	/**
	 * Encode l'avatar
	 * @param id ID du Player
	 * @param avatar Avatar du Player
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodeAvatar(int id, String avatar)
	{
		ArrayList<String> arguments = new ArrayList<>();
		
		arguments.add("avatar");
		arguments.add(Integer.toString(id));
		arguments.add(avatar);
		
		return encode(arguments);
	}
	
	/**
	 * Encode l'avatar
	 * @param player Player dont il faut encoder l'Avatar
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodeAvatar(Player player)
	{
		int id = player.getId();
		String avatar = player.getAvatar().getIconFile();
		return encodeAvatar(id, avatar);
	}

	/**
	 * Envode le commencement de la partie
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodeStartParty()
	{
		ArrayList<String> arguments = new ArrayList<>();
		
		arguments.add("start");
		
		return encode(arguments);
	}
	
	/**
	 * Encode l'hôte
	 * @param id ID du Player hôte
	 * @return message encodé
	 * @author /DM/
	 */
	public String encodeHost(int id)
	{
		ArrayList<String> arguments = new ArrayList<>();
		
		arguments.add("host");
		arguments.add(Integer.toString(id));
		
		return encode(arguments);
	}
}
