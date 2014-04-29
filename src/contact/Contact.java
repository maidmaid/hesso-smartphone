package contact;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable
{
	private String firstname;
	private String lastname;
	
	//SB/ ArrayList pour les mails et numéros (un contact peut en avoir plusieurs)
	ArrayList<Number> listNumber = new ArrayList<>();
	ArrayList<Mail> listMail = new ArrayList<>();
	
	//SB/ Boolean pour savoir si le contact est le propriétaire
	private boolean isOwner = false;
	
	/**
	 * Constructeur Contact
	 * @param firstname prenom du contact
	 * @param lastname nom du contact
	 * @param number numero du contact
	 * @param mail mail du contact
	 * @author SB
	 */
	public Contact(String firstname, String lastname, Number number, Mail mail)
	{
		this.firstname = firstname;
		this.lastname = lastname;
		this.listNumber.add(number);
		this.listMail.add(mail);
	}
	
	/**
	 * Second constructeur Contact vide (sans paramètres)
	 * @author SB
	 */
	public Contact()
	{
		
	}
	
	/**
	 * Modifier la propriété owner(proptiétaire) d'un contact
	 * @param isOwner
	 * @author SB
	 */
	public void setOwner(boolean isOwner)
	{
		this.isOwner = isOwner;
	}
	
	/**
	 * Savoir si contact owner ou pas
	 * @author SB
	 */
	public boolean isOwner()
	{
		return isOwner;
	}
	
	/**
	 * Modifier prenom
	 * @param firstname
	 * @author SB
	 */
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}
	
	/**
	 * Modifier nom
	 * @param lastname
	 * @author SB
	 */
	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}
	
	/**
	 * Obtenir prenom
	 * @author SB
	 */
	public String getFirstname()
	{
		return this.firstname;
	}
	
	/**
	 * Obtenir nom
	 * @author SB
	 */
	public String getLastname()
	{
		return this.lastname;
	}
	
	/**
	 * Obtenir TOUS les numéros du contact (retourne l'ArrayList)
	 * @author SB
	 */
	public ArrayList<Number> getNumber()
	{
		return this.listNumber;
	}
	
	/**
	 * Obtenir TOUS les mails du contact (retourne l'ArrayList)
	 * @author SB
	 */
	public ArrayList<Mail> getMail()
	{
		return this.listMail;
	}
	
	/**
	 * Ajouter un numero de téléphone au contact
	 * @param number
	 * @author SB
	 */
	public void addNumber(Number number)
	{
		this.listNumber.add(number);
	}
	
	/**
	 * Ajouter une adresse mail au contact
	 * @param mail
	 * @author SB
	 */
	public void addMail(Mail mail)
	{
		this.listMail.add(mail);
	}
	
	/**
	 * Afficher les contacts au format String
	 * @author DM / SB
	 */
	public String toString()
	{
		String show = firstname + " " + lastname;

		//SB/ Le contact owner est affiché en plus grand
		if(isOwner())
		{
			show = "<html><body><h3>" + show + "</h3></body></html>";
		}
		
		return show;
	}
}
