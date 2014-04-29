package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import smartphone.Application;
import smartphone.PageAppli;

import battlespacekey.data.Data;
import battlespacekey.network.Server;
import battlespacekey.player.Player;
import battlespacekey.player.PlayerManager;

/**
 * La classe TestBattleSpaceKey teste le jeu Battle Space Key
 * @author /DM/
 */
public class TestBattleSpaceKey
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	/**
	 * Teste la création de mon player
	 * @author /DM/
	 */
	@Test
	public void testCreateMyPlayer()
	{
		PageAppli pagGame = new PageAppli(new Application(), "game");
		PlayerManager playerManager = new PlayerManager();
		playerManager.setPageGame(pagGame);
		playerManager.createMyPlayer();
		Player myPlayer = playerManager.getMyPlayer();
		Assert.assertTrue(myPlayer.isMyPlayer());
	}
	
	/**
	 * Teste le redémarrage du server
	 * @throws IOException
	 * @author /DM/
	 */
	@Test
	public void testRestartServer() throws IOException
	{
		Server server = new Server();
		server.start();
		server.close();
		server.start();		
	}
	
	/**
	 * Teste l'encodate d'un pseudo
	 * @author /DM/
	 */
	@Test
	public void testEncodePseudo()
	{
		Player player = new Player(888, "Pseudo888");
		Data data = new Data();
		String encodePseudo = data.encodePseudo(player);
		String correctEncodePseudo = "pseudo;888;Pseudo888;";
		
		Assert.assertEquals(encodePseudo.toString(), correctEncodePseudo.toString());
	}
}