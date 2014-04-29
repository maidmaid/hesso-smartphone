package contact;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import contact.Mail.MailType;
import contact.Number.NumberType;

public class ContactManager extends ArrayList<Contact> implements Serializable
{
	//SB/ Pour la serialisation
	static final long serialVersionUID = 01062013L;
	
	private Contact selectedContact;
	
	/**
	 * Constructeur ContactManager
	 * @author SB
	 */
	public ContactManager()
	{
		super();
		loadContactList();
		selectedContact = new Contact();
		checkOwner();
		sort();
	}
	
	/**
	 * Second constructeur ContactManager
	 * @param contacts
	 * @author SB
	 */
	public ContactManager(ArrayList<Contact> contacts)
	{
		super(contacts);
		loadContactList();
		selectedContact = new Contact();
		checkOwner();
		sort();
	}
	
	/**
	 * Vérifier si un contact owner existe
	 * @author SB
	 */
	private void checkOwner()
	{
		Contact c = getOwner();
		
		if(c == null)
		{
			c = new Contact();
			c.setFirstname("Default");
			c.setLastname("Owner");
			c.addNumber(new Number(NumberType.AUTRE, ""));
			c.addMail(new Mail(MailType.AUTRE, ""));
			c.setOwner(true);
			add(c);
		}
	}
	
	/**
	 * Modifier le contact séléctionné
	 * @param selectedContact
	 * @author SB
	 */
	public void setSelectedContact(Contact selectedContact)
	{
		this.selectedContact = selectedContact;		
	}
	
	/**
	 * Obtenir le contact séléctionné
	 * @author SB
	 */
	public Contact getSelectedContact()
	{
		return selectedContact;
	}
	
	/**
	 * Sauvegarder la liste des contacts dans un fichier
	 * @author SB
	 */
	public void saveContactList()
	{
		try
		{
			//SB/ Dans le répertoire temp
			//SB/ Pour Win7 --> C:\Users\%username%\AppData\Local\Temp
			String temp = System.getProperty("java.io.tmpdir");
			String path = temp + "/contacts.txt";
			FileOutputStream fichier = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(this);
			oos.flush();
			oos.close();
		}
		catch (java.io.IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Charger la liste des contacts depuis le fichier
	 * @author SB
	 */
	public void loadContactList()
	{
		try
		{ 
			String temp = System.getProperty("java.io.tmpdir");
			String path = temp + "/contacts.txt";
			
			//DM/ Vérifie si le fichier existe
			File f = new File(path);
			if(!f.exists())
			{
				saveContactList();
			}
			
			FileInputStream fichier = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fichier);
			addAll((ArrayList<Contact>) ois.readObject());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Surcharger la méthode remove pour ajouter la sauvegarde des modifs dans le fichier
	 * @author SB
	 */
	public boolean remove(Object o)
	{
		boolean result = super.remove(o);
		saveContactList();
		return result;
	}
	
	/**
	 * Surcharger la methode add pour ajouter la sauvegarde des modifs dans le fichier
	 * @author SB
	 */
	public boolean add(Contact e)
	{
		boolean result = super.add(e);
		saveContactList();
		return result;
	}
	
	/**
	 * Obtenir le contact owner
	 * @author SB
	 */
	public Contact getOwner()
	{
		for (Contact c : this)
		{
			if(c.isOwner())
			{
				return c;
			}
		}

		return null;
	}
	
	/**
	 * Permet de comparer les contacts pour le tri
	 * @author DM
	 */
	private class ContactComparator implements Comparator<Contact>
	{
		public int compare(Contact c1, Contact c2)
		{
			return c1.isOwner() ? -1 : 1;	
		}
	}
	
	/**
	 * Trie la liste des contacts
	 * @author DM
	 */
	public void sort()
	{
		Collections.sort(this, new ContactComparator());
	}
}
