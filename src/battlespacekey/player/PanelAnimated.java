package battlespacekey.player;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * La classe PanelAnimated est un JPanel evolue qui permet de bouger
 * @author /DM/
 */
public class PanelAnimated extends JPanel
{
	private static int timeTick = 20;
	private Timer timer;
	private int ticks; 
	private double deltaX;
	private double deltaY;
	private double startX;
	private double startY;
	
	/**
	 * Constructeur du PanelAnimated
	 * @author /DM/
	 */
	public PanelAnimated()
	{
		ListenerTimer listener = new ListenerTimer();
		timer = new Timer(timeTick, listener);
	}
	
	/**
	 * Deplace le panel avec une animation
	 * @param x position en x
	 * @param y position en y 
	 * @param ms temps en miliseconde
	 * @author /DM/
	 */
	public void translate(int x, int y, int ms)
	{
		//DM/ Stope l'animation en cours
		timer.stop();
		
		//DM/ Sauvegarde la position
		startX = getX();
		startY = getY();
		
		//DM/ Calcule le nombre de passage
		ticks = ms / timeTick;
		
		//DM/ Calcule deltaX et deltaY
		deltaX = (double)(x - getX()) / ticks;
		deltaY = (double)(y - getY()) / ticks;
		
		//DM/ Starte l'animation
		timer.start();
	}
	
	/**
	 * Classe ListenerTimer
	 * @author /DM/
	 */
	private class ListenerTimer implements ActionListener
	{
		/**
		 * Surchage de la methode actionPerformed
		 * @author /DM/
		 */
		public void actionPerformed(ActionEvent e)
		{
			//DM/ Decremente ticks a chaque passage dans le timer
			if(--ticks == 0)
			{
				timer.stop();
			}
			
			//DM/ Calcule la nouvelle position
			startX += deltaX; 
			startY += deltaY;
			
			//DM/ Set la nouvelle position
			setLocation((int)startX, (int)startY);
		}
	}
}
