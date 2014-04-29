package battlespacekey.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

/**
 * La classe Box est un panel graphique avec tous les elements du joueurs
 * @author /DM/
 */
public class Box extends PanelAnimated
{
	protected JLabel lblPseudo;
	private JLabel lblScore;
	private JLabel lblRanking;
	private JProgressBar barAdvance;
	private JProgressBar barLate;
	private Avatar avatar;
	
	/**
	 * Crée une Box
	 * @author /DM/
	 */
	public Box()
	{
		super();
		init();
	}
	
	/**
	 * Ancienne initialisation de la Box
	 * @deprecated utiliser init
	 * @author /DM/
	 */
	private void initOld()
	{	
		//DM/ Definit les dimensions
		Dimension dBox = new Dimension(100, 60);
		double quota = 0.8;

		//DM/ Box
		setLayout(null);
		setBackground(Color.GRAY);
		setSize(dBox);
		
		//DM/ Bar Late
		barLate = new JProgressBar(0);
		barLate.setMaximum(10);
		barLate.setStringPainted(true);
		barLate.setBounds(0, 0, 100, 15);
		add(barLate);
		
		//DM/ Bar Advance
		barAdvance = new JProgressBar(0);
		barAdvance.setMaximum(10);
		barAdvance.setStringPainted(true);
		barAdvance.setBounds(0, 45, 100, 15);
		add(barAdvance);
		
		//DM/ Pseudo
		lblPseudo = new JLabel();
		lblPseudo.setBounds(30, 15, 70, 30);
		//lblPseudo.setBackground(Color.BLUE);
		//lblPseudo.setOpaque(true);
		add(lblPseudo);
		
		//DM/ Ranking
		lblRanking = new JLabel();
		lblRanking.setBounds(0, 15, 30, 15);
		//lblRanking.setBackground(Color.GREEN);
		//lblRanking.setOpaque(true);
		add(lblRanking);
		
		//DM/ Score
		lblScore = new JLabel();
		lblScore.setBounds(0, 30, 30, 15);
		//lblScore.setBackground(Color.YELLOW);
		//lblScore.setOpaque(true);
		add(lblScore);	
	}

	/**
	 * Initialise la Box
	 * @author /DM/
	 */
	private void init()
	{	
		//DM/ Definit les dimensions
		Dimension dBox = new Dimension(64, 64 + 10 + 10);
		
		//DM/ Box
		setLayout(new BorderLayout());
		setSize(dBox);
		
		//DM/ Bar Late
		barLate = new JProgressBar(0);
		barLate.setMaximum(10);
		barLate.setStringPainted(true);
		barLate.setBounds(0, 0, 20, 10);
		//add(barLate, BorderLayout.WEST);
		
		//DM/ Score
		lblScore = new JLabel();
		lblScore.setHorizontalAlignment(JLabel.CENTER);
		lblScore.setBounds(20, 0, 24, 10);
		add(lblScore, BorderLayout.NORTH);
		
		//DM/ Bar Advance
		barAdvance = new JProgressBar(0);
		barAdvance.setMaximum(10);
		barAdvance.setStringPainted(true);
		barAdvance.setBounds(44, 0, 20, 10);
		//add(barAdvance, BorderLayout.EAST);
		
		//DM/ Avatar
		avatar = new Avatar();
		avatar.setBounds(0, 10, 64, 64);
		add(avatar, BorderLayout.CENTER);
		
		//DM/ Pseudo
		lblPseudo = new JLabel();
		lblPseudo.setBounds(0, 74, 64, 10);
		lblPseudo.setHorizontalAlignment(JLabel.CENTER);
		add(lblPseudo, BorderLayout.SOUTH);
		
		//DM/ Ranking
		lblRanking = new JLabel();
	}
	
	/**
	 * Affiche le score
	 * @param score score du Player
	 * @author /DM/
	 */
	public void setScore(int score)
	{
		lblScore.setText(Integer.toString(score));
	}
	
	/**
	 * Affiche le classement
	 * @param ranking classement du Player
	 * @author /DM/
	 */
	public void setRanking(int ranking)
	{
		lblRanking.setText(Integer.toString(ranking));
	}
	
	/**
	 * Affiche l'avance
	 * @param advance avance du Player
	 * @author /DM/
	 */
	public void setAdvance(int advance)
	{
		if(advance >= 10)
		{
			barAdvance.setForeground(Color.GREEN);
		}
		else if(advance >= 5)
		{
			barAdvance.setForeground(Color.ORANGE);
		}
		else
		{
			barAdvance.setForeground(Color.RED);
		}
		barAdvance.setValue(advance);
		barAdvance.setString("+" + Integer.toString(advance));
	}
	
	/**
	 * Affiche le retard
	 * @param late retard du Player
	 * @author /DM/
	 */
	public void setLate(int late)
	{
		if(late >= 10)
		{
			barLate.setForeground(Color.RED);
		}
		else if(late >= 5)
		{
			barLate.setForeground(Color.ORANGE);
		}
		else
		{
			barLate.setForeground(Color.GREEN);
		}
		barLate.setValue(late);
		barLate.setString("-" + Integer.toString(late));
	}

	/**
	 * Affiche le pseudo
	 * @param pseudo pseudo du Player
	 * @author /DM/
	 */
	public void setPseudo(String pseudo)
	{
		lblPseudo.setText(pseudo);
	}

	/**
	 * Affiche l'avatar
	 * @param avatar Avatar du Player
	 * @author /DM/
	 */
	public void setAvatar(Avatar avatar)
	{
		this.avatar.setIconFile(avatar.getIconFile());
	}
}
