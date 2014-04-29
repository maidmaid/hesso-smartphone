package battlespacekey.pages;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import smartphone.Application;
import smartphone.PageAppli;

import battlespacekey.data.Data;
import battlespacekey.data.DataListener;
import battlespacekey.network.Client;
import battlespacekey.network.ClientListener;
import battlespacekey.network.Server;
import battlespacekey.network.ServerListener;
import battlespacekey.player.Avatar;
import battlespacekey.player.AvatarManager;
import battlespacekey.player.Player;
import battlespacekey.player.PlayerManager;


public class PageMain extends PageAppli
{
	private Server server;
	private Client client;
	private Data data;
	private PlayerManager playerManager;
	private AvatarManager avatarManager;
	private JTextField fldJoinParty;
	
	public PageMain(Application appli, Server server, Client client, Data data, PlayerManager playerManager, AvatarManager avatarManager)
	{
		super(appli, "main");
		this.server = server;
		this.client = client;
		this.data = data;
		this.playerManager = playerManager;
		this.avatarManager = avatarManager;
		init();
	}
	
	public void init()
	{
		setLayout(new FlowLayout());

		client.addClientListener(new ClientActions());
		data.addDataListener(new EnginActions());

		//DM/ Nouvelle partie
		JButton btnNewParty = new JButton("Nouvelle partie");
		btnNewParty.addActionListener(new NewPartyAction());
		add(btnNewParty);
		server.addServerListener(new ServerActions());

		//DM/ Rejoindre une partie
		JButton btnJoinParty = new JButton("Rejoindre une partie");
		btnJoinParty.addActionListener(new JoinPartyAction());
		fldJoinParty = new JTextField("127.0.0.1", 10);
		JPanel pnlJoinParty = new JPanel(new FlowLayout());
		pnlJoinParty.setBackground(Color.WHITE);
		pnlJoinParty.add(btnJoinParty);
		pnlJoinParty.add(fldJoinParty);
		add(pnlJoinParty);
	}
	
	/**
	 * Classe gerant l'action d'une nouvelle partie
	 * @author /DM/
	 */
	private class NewPartyAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//DM/ FIXME
			playerManager.reset();
			
			try
			{
				//DM/ Mon player est l'hote de la partie
				playerManager.getMyPlayer().setHost(true);
				
				//DM/ Cree le serveur
				server.start();
				
				try
				{
					client.connect("127.0.0.1");
					appli.setActivePage("option");
				}
				catch(UnknownHostException e1)
				{
					e1.printStackTrace();
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				}
				
			}
			catch(IOException e1)
			{
				e1.printStackTrace();
			}						
		}
	}
	
	/**
	 * Classe gerant l'action pour joindre une partie
	 * @author /DM/
	 */
	private class JoinPartyAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//DM/ FIXME
			playerManager.reset();
			
			try
			{
				String host = fldJoinParty.getText();
				client.connect(host);
				appli.setActivePage("option");
			}
			catch(UnknownHostException e1)
			{
				e1.printStackTrace();
			}
			catch(IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}
	
	class ClientActions implements ClientListener
	{
		public void messageReceived(String message)
		{
			data.decode(message);
		}

		public void disconnected(Client client)
		{
			reset();
		}
	}
	
	class ServerActions implements ServerListener
	{
		private int id;
		
		public ServerActions()
		{
			id = 0;
		}
		
		private int nextId()
		{
			return ++id;
		}
		
		public void clientConnected(Client client)
		{
			int id = nextId();
			
			client.setId(id);
			
			//DM/ Ajoute le listener au nouveau client
			client.addClientListener(new ClientActions());
			
			//DM/ Envoie un ID au nouveau client
			client.send(data.encodeId(id));
			
			//DM/ Envoie aux autres clients la connexion du nouveau client
			server.broadcast(data.encodeConnection(id), client);
			
			//DM/ Envoie au nouveau client la connexion des autres clients
			for(Player player : playerManager)
			{
				if(player.isValid())
				{
					client.send(data.encodeConnection(player));
					client.send(data.encodePseudo(player));
					client.send(data.encodeAvatar(player));
					client.send(data.encodeScore(player));
				}
			}
			
			//DM/ Envoie au nouveau client qui est l'hote de la partie
			client.send(data.encodeHost(playerManager.getMyPlayer().getId()));
			
			//DM/ Envoie un avatar aleatoire au nouveau client
			server.broadcast(data.encodeAvatar(id, avatarManager.getRandomAvailableAvatar().getIconFile()));
		}
		
		class ClientActions implements ClientListener
		{
			public void messageReceived(String message)
			{
				server.broadcast(message);
			}

			public void disconnected(Client client)
			{
				server.getClients().remove(client);
				server.broadcast(data.encodeDeconnection(client));
			}		
		}
	}
	
	class EnginActions implements DataListener
	{
		public void id(int id)
		{
			Player player = playerManager.getMyPlayer();
			player.setId(id);
			playerManager.readjust();
			
			client.send(data.encodePseudo(player));
		}
		
		public void connection(int id)
		{
			Player player = new Player(id);
			playerManager.add(player);
			playerManager.readjust();
		}
		
		public void deconnection(int id)
		{
			Player player = playerManager.getPlayer(id);
			
			if(player.isHost())
			{				
				if(player.isMyPlayer())
				{
					try
					{
						server.close();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
				
				reset();
			}
			else
			{
				if(player.isMyPlayer())
				{
					reset();
				}
				else
				{
					playerManager.removePlayer(id);
					playerManager.readjust();
				}
			}
		}
		
		public void pseudo(int id, String pseudo)
		{
			playerManager.getPlayer(id).setPseudo(pseudo);
		}		
		
		public void score(int id, int score)
		{
			playerManager.getPlayer(id).setScore(score);
			playerManager.readjust();
		}

		public void avatar(int id, String avatar)
		{
			Avatar a = avatarManager.getAvatar(avatar);
			playerManager.getPlayer(id).setAvatar(a);
		}

		public void startParty()
		{
			appli.setActivePage("game");
		}

		public void host(int id)
		{
			playerManager.getPlayer(id).setHost(true);
		}
	}
	
	public void reset()
	{
		appli.setActivePage("main");
		playerManager.reset();
		avatarManager.reset();
	}
}