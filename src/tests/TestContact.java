package tests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import contact.Contact;
import contact.ContactManager;
import contact.Mail;
import contact.Number;
import contact.Mail.MailType;
import contact.Number.NumberType;

/**
 * La classe TestContact est une classe de qui teste différentes opérations sur les contacts
 * @author /DM/
 */
public class TestContact
{
	private static ContactManager contactManager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		contactManager = new ContactManager();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		contactManager.saveContactList();
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
	 * Teste la sauvegarde des contacts
	 * @author /DM/
	 */
	@Test
	public void testSaveContactList()
	{
		Contact c1 = new Contact("Simeon", "Bobylev", new Number(NumberType.MOBILE, "079 578 94 64"), new Mail(MailType.PRIVE, "@gmail.com"));
		c1.setOwner(true);
		c1.addNumber(new Number(NumberType.DOMICILE, "024 472 12 12"));

		Number number = new Number(NumberType.AUTRE, "");
		number.setType(NumberType.FAX);
		number.setNumber("024 555 25 25");
		c1.addNumber(number);
		
		ContactManager contactManager = new ContactManager();
		contactManager.clear();
		contactManager.add(c1);
		
		contactManager.saveContactList();
		contactManager.loadContactList();
		
		Contact c2 = contactManager.get(0);
		Assert.assertEquals(c1.getFirstname(), c2.getFirstname());
	}
	
	/**
	 * Teste la sauvegarde de deux contacts
	 * @author /DM/
	 */
	@Test
	public void testCreate2Contact()
	{
		Contact c1 = new Contact("Simeon", "Bobylev", new Number(NumberType.MOBILE, "079 578 94 64"), new Mail(MailType.PRIVE, "@gmail.com"));
		Contact c2 = new Contact("Dany", "Maillard", new Number(NumberType.MOBILE, "079 000 00 00"), new Mail(MailType.PRIVE, "@gmail.com"));
		c2.isOwner();
		ContactManager contactManager = new ContactManager();
		contactManager.add(c1);
		contactManager.add(c2);
		contactManager.saveContactList();
	}
}
