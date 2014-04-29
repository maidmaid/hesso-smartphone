package battlespacekey.pages;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JButton;
import javax.swing.JPanel;

import smartphone.Application;
import smartphone.PageAppli;

import battlespacekey.data.Data;
import battlespacekey.network.Client;
import battlespacekey.player.Avatar;
import battlespacekey.player.AvatarManager;
import battlespacekey.player.Player;
import battlespacekey.player.PlayerManager;


public class PageOption extends PageAppli
{
	private Client client;
	private Data data;
	private PlayerManager playerManager;
	private AvatarManager avatarManager;
	private AvatarChoice avatarChoice;
	private JButton btnStartGame;
	
	public PageOption(Application appli, Client client, Data data, PlayerManager playerManager, AvatarManager avatarManager)
	{
		super(appli, "option");
		this.client = client;
		this.data = data;
		this.playerManager = playerManager;
		this.avatarManager = avatarManager;
		init();
	}
	
	private void init()
	{
		//DM/ Focus
		addFocusListener(new FocusAction());
		
		//DM/ Cree le panel d'avatar
		avatarChoice = new AvatarChoice();
		add(avatarChoice);
		
		//DM/ Cree le bouton start
		btnStartGame = new JButton("Commencer la partie");
		btnStartGame.addActionListener(new StartPartyAction());
		add(btnStartGame);
	}
	
	private class AvatarChoice extends JPanel
	{
		public AvatarChoice()
		{
			super();
			setBackground(Color.WHITE);
			setLayout(new GridLayout(5, 6));
			
			for(Avatar avatar : avatarManager)
			{
				avatar.addMouseListener(new SelectAvatarAction());
				add(avatar);
			}
		}
		
		/**
		 * Classe gerant l'action qui selectionne un avatar
		 * @author /DM/
		 */
		private class SelectAvatarAction extends MouseAdapter
		{		
			public void mouseClicked(MouseEvent e)
			{
				Avatar avatar = (Avatar)e.getSource();
				
				if(!avatar.isUsed())
				{
					Player player = playerManager.getMyPlayer();
					player.setAvatar(avatar);
					client.send(data.encodeAvatar(player));					
				}
			}
		}
	}
	
	/**
	 * Classe gerant l'action qui commence une partie
	 * @author /DM/
	 */
	private class StartPartyAction implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			client.send(data.encodeStartParty());
		}
	}
	
	private class FocusAction implements FocusListener
	{
		public void focusGained(FocusEvent e)
		{
			boolean isHost = playerManager.getMyPlayer().isHost();
			btnStartGame.setVisible(isHost);
		}

		public void focusLost(FocusEvent e)
		{
			
		}
	}
}