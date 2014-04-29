package contact;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import smartphone.Application;
import smartphone.PageAppli;

import contact.Mail.MailType;
import contact.Number.NumberType;


public class PageAddContact extends PageAppli
{
	private ArrayList<JTextField> numbers = new ArrayList<>();
	private ArrayList<JComboBox> numbersTypes = new ArrayList<>();
	
	private ArrayList<JTextField> mails = new ArrayList<>();
	private ArrayList<JComboBox> mailsTypes = new ArrayList<>();	
	
	private ContactManager allContacts;
	
	//SB/ Pour la partie AddContact
	private Dimension dimCombobox;
	
	private JPanel pnlFirstname;
	private JLabel lblFirstname;
	private JTextField fldFirstname;
	
	private JPanel pnlLastname;
	private JLabel lblLastname;
	private JTextField fldLastname;
	
	private JPanel pnlNumber;
	private JComboBox cmbNumber;
	private JTextField fldNumber;
	private JButton btnAddNumber;
	
	private JPanel pnlMail;
	private JComboBox cmbMail;
	private JTextField fldMail;
	private JButton btnAddMail;
	
	private JButton btnApplyAddContact;
	private JButton btnCancel;
	
	/**
	 * Constructeur PageAddContact
	 * @param appli
	 * @param allContacts
	 * @author SB
	 */
	public PageAddContact(Application appli, ContactManager allContacts)
	{
		super(appli, "add");
		this.allContacts = allContacts;
		init();
		addFocusListener(new FocusAction());
	}

	/**
	 * Initialiser
	 * @author SB
	 */
	public void init()
	{
		setLayout(new FlowLayout());
		setBackground(Color.WHITE);
		
		GridLayout gl = new GridLayout(0, 1);
		gl.setVgap(3);
		
		dimCombobox = new Dimension(80, 20);
		
		pnlNumber = new JPanel(gl);
		pnlNumber.setBackground(Color.WHITE);
		pnlMail = new JPanel(gl);
		pnlMail.setBackground(Color.WHITE);
		
		
		pnlFirstname = new JPanel(new FlowLayout());
		pnlFirstname.setBackground(Color.WHITE);
		lblFirstname = new JLabel("Prénom");
		
		Dimension d = new Dimension(80, 10);
		lblFirstname.setPreferredSize(d);		
		
		fldFirstname = new JTextField("", 23);
		
		pnlLastname = new JPanel(new FlowLayout());
		pnlLastname.setBackground(Color.WHITE);
		lblLastname = new JLabel("Nom");
		fldLastname = new JTextField("", 23);
		lblLastname.setPreferredSize(d);
			
		
		cmbNumber = new JComboBox<>(NumberType.getNumberType());
		cmbNumber.setPreferredSize(dimCombobox);
		fldNumber = new JTextField("", 18);
		
		btnAddNumber = new JButton(new ImageIcon("Images/Plus.png"));
		JPanel pnlNumberFormat = addInPanel(cmbNumber, fldNumber);
		
		cmbMail = new JComboBox<>(MailType.getMailType());
		cmbMail.setPreferredSize(dimCombobox);
		fldMail = new JTextField("", 18);
		
		btnAddMail = new JButton(new ImageIcon("Images/Plus.png"));
		JPanel pnlMailFormat = addInPanel(cmbMail, fldMail);
		
		btnApplyAddContact = new JButton("Enregistrer", new ImageIcon("Images/add.png"));
		btnCancel = new JButton("Annuler");
		
		pnlFirstname.add(lblFirstname);
		pnlFirstname.add(fldFirstname);
		add(pnlFirstname);
		
		pnlLastname.add(lblLastname);
		pnlLastname.add(fldLastname);
		add(pnlLastname);
		
		add(pnlNumber);
		pnlNumberFormat.add(btnAddNumber);
		pnlNumber.add(pnlNumberFormat);
		pnlNumber.setBorder(new TitledBorder("Numéro"));
		
		add(pnlMail);
		pnlMailFormat.add(btnAddMail);
		pnlMail.add(pnlMailFormat);
		pnlMail.setBorder(new TitledBorder("Mail"));
		
		add(btnApplyAddContact);
		add(btnCancel);
		
		btnAddNumber.addMouseListener(new AddNumberAction());
	
		btnAddMail.addMouseListener(new AddMailAction());
		
		btnApplyAddContact.addMouseListener(new AddContactAction());
		btnCancel.addMouseListener(new CancelAction());
	}
	
	/**
	 * Ajouter un JPanel pour une meilleure mise en forme 
	 * @param cb
	 * @param tf
	 * @author SB
	 */
	private JPanel addInPanel(JComboBox cb, JTextField tf)
	{
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(cb);
		p.add(tf);
		p.setBackground(Color.WHITE);
		return p;
	}
	
	/**
	 * Permet de valider l'ajout d'un contact (sauvegarde les infos dans l'objet Contact et dans le fichier)
	 * @author SB
	 */
	private class AddContactAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			Contact c = new Contact();
			c.setFirstname(fldFirstname.getText());
			c.setLastname(fldLastname.getText());
			
			//Ajout du numéro
			c.addNumber(new Number(NumberType.MOBILE, fldNumber.getText()));
			
			for (int i = 0; i < numbers.size(); i++)
			{
				int index = numbersTypes.get(i).getSelectedIndex();
				NumberType type = NumberType.values()[index];
				Number n = new Number(type, numbers.get(i).getText());
				c.addNumber(n);
			}
			
			//Ajout du mail
			c.addMail(new Mail(MailType.PRIVE, fldMail.getText()));
			
			for (int i = 0; i < mails.size(); i++)
			{
				int index = mailsTypes.get(i).getSelectedIndex();
				MailType type = MailType.values()[index];
				Mail m = new Mail(type, mails.get(i).getText());
				c.addMail(m);
			}
			
			//Ajout dans l'objet contact et sauvegarde dans le fichier
			allContacts.add(c);
			allContacts.saveContactList();
			appli.setActivePage("main");
		}
	}
	
	/**
	 * Permet de revenir à la page principale
	 * @author SB
	 */
	private class CancelAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			appli.setActivePage("main");
		}
	}
	
	/**
	 * Permet d'ajouter une nouvelle ligne (combobox et textfield) pour l'ajout d'un numéro supplémentaire
	 * @author SB
	 */
	private class AddNumberAction extends MouseAdapter
	{		
		public void mouseClicked(MouseEvent e)
		{
			JComboBox cb = new JComboBox<>(NumberType.getNumberType());
			cb.setPreferredSize(dimCombobox);
			JTextField tf = new JTextField("", 18);
			JButton bt = new JButton(new ImageIcon("Images/remove.png"));
			numbersTypes.add(cb);
			numbers.add(tf);

			JPanel p = addInPanel(cb, tf);
			p.add(bt);
			bt.addMouseListener(new RemoveNumberAction(p));
			pnlNumber.add(p);
		}
	}
	
	/**
	 * Permet d'enlever la ligne permettant la saisie d'un numéro supplémentaire
	 * @author SB
	 */
	private class RemoveNumberAction extends MouseAdapter
	{
		private JPanel panel;
		
		public RemoveNumberAction(JPanel p)
		{
			this.panel = p;
		}
		
		public void mouseClicked(MouseEvent e)
		{
			pnlNumber.remove(panel);
		}
	}
	
	/**
	 * Permet d'ajouter une nouvelle ligne (combobox et textfield) pour l'ajout d'un mail supplémentaire
	 * @author SB
	 */
	private class AddMailAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			JComboBox cb = new JComboBox<>(MailType.getMailType());
			cb.setPreferredSize(dimCombobox);
			JTextField tf = new JTextField("", 18);
			JButton bt = new JButton(new ImageIcon("Images/remove.png"));
			mailsTypes.add(cb);
			mails.add(tf);
			
			JPanel p = addInPanel(cb, tf);
			p.add(bt);
			bt.addMouseListener(new RemoveMailAction(p));
			pnlMail.add(p);
		}
	}
	
	/**
	 * Permet d'enlever la ligne permettant la saisie d'un mail supplémentaire
	 * @author SB
	 */
	private class RemoveMailAction extends MouseAdapter
	{
		private JPanel panel;
		
		public RemoveMailAction(JPanel p)
		{
			this.panel = p;
		}
		
		public void mouseClicked(MouseEvent e)
		{
			pnlMail.remove(panel);
		}
	}
	
	/**
	 * Permet de refraichir la page à chaque affichage
	 * @author SB
	 */
	private class FocusAction extends FocusAdapter
	{
		public void focusGained(FocusEvent e)
		{
			removeAll();
			numbers.clear();
			numbersTypes.clear();
			mails.clear();
			mailsTypes.clear();
			init();
			repaint();
		}
	}
	
}
