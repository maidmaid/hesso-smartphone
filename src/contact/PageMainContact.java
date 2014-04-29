package contact;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import smartphone.Application;
import smartphone.PageAppli;


public class PageMainContact extends PageAppli
{
	private ContactManager allContacts;
	private Contact selectedContact;
	
	//SB/ Pannels pour mieux gérer l'affichage
	private JPanel pnlNorth = new JPanel(new GridLayout());
	private JPanel pnlCenter = new JPanel(new FlowLayout());
	private JPanel pnlSouth = new JPanel(new FlowLayout());
	
	//SB/ Liste
	private JList<Contact> listContact = new JList<Contact>();
	
	//SB/ Boutons
	private JButton btnAddContact = new JButton("Nouveau");
	private JButton btnShowContact = new JButton("Afficher");
	private JButton btnDeleteContact = new JButton("Supprimer");

	/**
	 * Constructeur PageMainContact
	 * @param appli
	 * @param allContacts
	 * @author SB
	 */
	public PageMainContact(Application appli, ContactManager allContacts)
	{
		super(appli, "main");
		this.allContacts = allContacts;
		init();
	}
	
	/**
	 * Fonction d'initialisation
	 * @author SB
	 */
	public void init()
	{
		setLayout(new BorderLayout());
		pnlCenter.setBackground(Color.WHITE);
		pnlSouth.setBackground(Color.WHITE);
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
		listContact.addMouseListener(new SelectionContact());
		JScrollPane scrollPane = new JScrollPane(listContact);
		scrollPane.setPreferredSize(new Dimension(400, 350));
		pnlNorth.add(scrollPane);
		
		//Ajout des boutons du menu avec les ecouteurs
		pnlSouth.add(btnAddContact);
		btnAddContact.addMouseListener(new AddContact());
		pnlCenter.add(btnShowContact);
		btnShowContact.addMouseListener(new ShowContact());
		pnlCenter.add(btnDeleteContact);
		btnDeleteContact.addMouseListener(new DeleteContact());
		
		addFocusListener(new FocusAction());
		
		refreshListContact();
	}
	
	/**
	 * Fonction qui rafraichit la liste des contacts
	 * @author DM
	 */
	private void refreshListContact()
	{
		DefaultListModel<Contact> model = new DefaultListModel<>();
		for (Contact c : allContacts)
		{
			model.addElement(c);
		}
		listContact.setModel(model);
	}
	
	/**
	 * Classe gere la selection d'un contact dans la JList
	 * @author SB / DM
	 */
	private class SelectionContact extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			int index = listContact.locationToIndex(e.getPoint());

			selectedContact = allContacts.get(index);
			allContacts.setSelectedContact(selectedContact);
		}
	}
	
	/**
	 * Classe qui gere l'affichage complet d'un contact
	 * @author SB
	 */
	private class ShowContact extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if(selectedContact != null)
			{
				appli.setActivePage("show");
				refreshListContact();
			}
		}
	}
	
	/**
	 * Classe qui gere l'ajout d'un contact
	 * @author SB
	 */
	private class AddContact extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			appli.setActivePage("add");
		}
	}
	
	/**
	 * Classe qui gere la suppression d'un contact
	 * @author SB
	 */
	private class DeleteContact extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			allContacts.remove(selectedContact);
			refreshListContact();
		}
	}
	
	/**
	 * Classe qui gere le focus de la page
	 * @author SB
	 */
	private class FocusAction extends FocusAdapter
	{
		public void focusGained(FocusEvent e)
		{
			refreshListContact();
		}
	}
}
