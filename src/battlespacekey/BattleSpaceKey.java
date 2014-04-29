package battlespacekey;


import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;

import smartphone.Application;

import battlespacekey.data.Data;
import battlespacekey.network.Client;
import battlespacekey.network.Server;
import battlespacekey.pages.PageGame;
import battlespacekey.pages.PageMain;
import battlespacekey.pages.PageOption;
import battlespacekey.player.AvatarManager;
import battlespacekey.player.PlayerManager;


/**
 * La classe BattleSpaceKey est une application de jeu en multijoueur super fun
 * @author /DM/
 */
public class BattleSpaceKey extends Application
{
	private Server server;
	private Client client;
	private Data data;
	private PlayerManager playerManager;
	private AvatarManager avatarManager;
	
	private PageMain pagMain;
	private PageOption pagOption;
	private PageGame pagGame;
	
	/**
	 * Cré un BattleSpaceKey
	 * @author /DM/
	 */
	public BattleSpaceKey()
	{
		//DM/ Definit l'Application
		super();
		
		setName("Battle Space Key");
		setLayout(new GridLayout());
		setFocusable(true);
		
		try
		{
			server = new Server();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		client = new Client();
		data = new Data();
		playerManager = new PlayerManager();
		avatarManager = new AvatarManager();
		
		//DM/ Pages
		pagMain = new PageMain(this, server, client, data, playerManager, avatarManager);
		pagOption = new PageOption(this, client, data, playerManager, avatarManager);
		pagGame = new PageGame(this, server, client, data, playerManager);
		
		playerManager.setPageGame(pagGame);
		playerManager.createMyPlayer();
		
		setActivePage(pagMain);
	}
}