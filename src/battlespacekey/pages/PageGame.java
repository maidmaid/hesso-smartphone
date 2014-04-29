package battlespacekey.pages;


import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import smartphone.Application;
import smartphone.PageAppli;

import battlespacekey.data.Data;
import battlespacekey.network.Client;
import battlespacekey.network.Server;
import battlespacekey.player.AvatarManager;
import battlespacekey.player.Player;
import battlespacekey.player.PlayerManager;


public class PageGame extends PageAppli
{
	private JLabel lblPodium;
	private Server server;
	private Client client;
	private Data data;
	private PlayerManager playerManager;
	
	public PageGame(Application appli, Server server, Client client, Data data, PlayerManager playerManager)
	{
		super(appli, "game");
		this.server = server; 
		this.client = client;
		this.data = data;
		this.playerManager = playerManager;
		init();
	}

	private void init()
	{
		setLayout(null);
		addKeyListener(new KeySpaceAction());

		//DM/ Podium
		lblPodium = new JLabel();
		ImageIcon icon = new ImageIcon("src/battlespacekey/images/podium.png");
		lblPodium.setIcon(icon);
		lblPodium.setBounds((400 - icon.getIconWidth()) / 2, 20, icon.getIconWidth(), icon.getIconHeight());
		add(lblPodium);
		
		//DM/ Bouton quitter
		JLabel lblQuit = new JLabel("Quitter la partie");
		lblQuit.addMouseListener(new QuitParty());
		lblQuit.setFocusable(false);
		lblQuit.setBounds(5, 5, 100, 15);
		add(lblQuit);
	}
	
	private class KeySpaceAction extends KeyAdapter
	{
		public void keyReleased(KeyEvent e)
		{
			Player p = playerManager.getMyPlayer();
			
			if(e.getKeyCode() == KeyEvent.VK_SPACE && !p.isFinish())
			{
				p.addScore(1);
				client.send(data.encodeScore(p));
			}
		}
	}
	
	private class QuitParty extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			Player p = playerManager.getMyPlayer();
			client.send(data.encodeDeconnection(p.getId()));
		}
	}
}
