package contact;

import java.io.Serializable;

public class Number implements Serializable
{
	private NumberType type;
	private String number;
	
	/**
	 * Constructeur Number
	 * @param type
	 * @param number
	 * @author SB
	 */
	public Number(NumberType type, String number)
	{
		this.type = type;
		this.number = number;
	}
	
	/**
	 * Enum pour les types de numéro
	 * @author Meons
	 */
	public enum NumberType
	{
		MOBILE,
		DOMICILE,
		BUREAU,
		FAX,
		AUTRE;
		
		public static String[] getNumberType()
		{
			String nTypes[] = {"Mobile", "Domicile", "Bureau", "Fax", "Autre"};
			return nTypes;
		}
	}
	
	/**
	 * Modifier le type de numéro
	 * @param type
	 * @author SB
	 */
	public void setType(NumberType type)
	{
		this.type = type;
	}
	
	/**
	 * Modifier le numéro
	 * @param number
	 * @author SB
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}
	
	/**
	 * Obtenir le type de numéro
	 * @author SB
	 */
	public NumberType getType()
	{
		return this.type;
	}
	
	/**
	 * Obtenir le numéro
	 * @author SB
	 */
	public String getNumber()
	{
		return this.number;
	}
}
