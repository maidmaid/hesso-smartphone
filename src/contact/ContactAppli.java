package contact;

import smartphone.Application;

public class ContactAppli extends Application
{	
	private ContactManager allContacts = new ContactManager();
	
	/**
	 * Constructeur ContactAppli
	 * @author SB
	 */
	public ContactAppli()
	{		
		setName("Contacts");
		setIcon("Images/Appli_Contacts.png");
		
		//SB/ Instencie les différentes pages de l'application
		PageMainContact pagMainContact = new PageMainContact(this, allContacts);
		PageAddContact pagAddContact = new PageAddContact(this, allContacts);
		PageShowContact pagShowContact = new PageShowContact(this, allContacts);
	
		//SB/ Affiche la page main de l'application
		setActivePage(pagMainContact);
	}
}
