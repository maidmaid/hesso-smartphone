package contact;

import java.io.Serializable;

public class Mail implements Serializable
{
	
	private MailType type;
	private String mail;
	
	/**
	 * Constructeur Mail
	 * @param type
	 * @param mail
	 * @author SB
	 */
	public Mail(MailType type, String mail)
	{
		this.type = type;
		this.mail = mail;
	}
	
	/**
	 * Enum pour les types de mail
	 * @author Meons
	 */
	public enum MailType
	{
		PRIVE,
		PRO,
		AUTRE;
		
		public static String[] getMailType()
		{
			String mTypes[] = {"Privé", "Pro", "Autre"};
			return mTypes;
		}
	}

	/**
	 * Modifier le type de mail
	 * @param type
	 * @author SB
	 */
	public void setType(MailType type)
	{
		this.type = type;
	}
	
	/**
	 * Modifier le mail
	 * @param mail
	 * @author SB
	 */
	public void setMail(String mail)
	{
		this.mail = mail;
	}
	
	/**
	 * Obtenir le type de mail
	 * @author SB
	 */
	public MailType getType()
	{
		return this.type;
	}
	
	/**
	 * Obtenir le mail
	 * @author SB
	 */
	public String getMail()
	{
		return this.mail;
	}
}
