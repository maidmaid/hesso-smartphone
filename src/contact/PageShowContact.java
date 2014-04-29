package contact;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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


public class PageShowContact extends PageAppli
{	
	private ContactManager allContacts;
	private Contact c;
	/*
	private ArrayList<JTextField> numbers = new ArrayList<>();
	private ArrayList<JComboBox> numbersTypes = new ArrayList<>();
	
	private ArrayList<JTextField> mails = new ArrayList<>();
	private ArrayList<JComboBox> mailsTypes = new ArrayList<>();
	*/
	
	private ArrayList<JComboBox> numberCmbList;
	private ArrayList<JTextField> numberFldList;
	
	private ArrayList<JComboBox> mailCmbList;
	private ArrayList<JTextField> mailFldList;
	
	
	private JPanel pnlFirstname;
	private JLabel lblFirstname;
	private JTextField fldFirstname;
	
	private JPanel pnlLastname;
	private JLabel lblLastname;
	private JTextField fldLastname;
	
	private JPanel pnlNumber;
	private JPanel pnlMail;

	private JButton btnApplyChanges;
	
	private Dimension dimCombobox;
	
	/**
	 * Constructeur PageShowContact
	 * @param appli
	 * @param allContacts
	 * @author SB
	 */
	public PageShowContact(Application appli, ContactManager allContacts)
	{
		super(appli, "show");
		this.allContacts = allContacts;
		
		numberCmbList = new ArrayList<JComboBox>();
		numberFldList = new ArrayList<JTextField>();
		mailCmbList = new ArrayList<JComboBox>();
		mailFldList = new ArrayList<JTextField>();
		
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
		pnlNumber.setBorder(new TitledBorder("Numero"));
		
		pnlMail = new JPanel(gl);
		pnlMail.setBackground(Color.WHITE);
		pnlMail.setBorder(new TitledBorder("Mail"));
		
		
		pnlFirstname = new JPanel(new FlowLayout());
		pnlFirstname.setBackground(Color.WHITE);
		lblFirstname = new JLabel("Prénom");
		
		Dimension d = new Dimension(80, 10);
		lblFirstname.setPreferredSize(d);		
		
		fldFirstname = new JTextField("Prenom", 23);
		
		pnlLastname = new JPanel(new FlowLayout());
		pnlLastname.setBackground(Color.WHITE);
		lblLastname = new JLabel("Nom");
		fldLastname = new JTextField("Nom", 23);
		lblLastname.setPreferredSize(d);
		
		btnApplyChanges = new JButton("Valider");
		
		pnlFirstname.add(lblFirstname);
		pnlFirstname.add(fldFirstname);
		add(pnlFirstname);
		
		pnlLastname.add(lblLastname);
		pnlLastname.add(fldLastname);
		add(pnlLastname);
		
		add(pnlNumber);
		add(pnlMail);
		
		add(btnApplyChanges);
		btnApplyChanges.addMouseListener(new ApplyChanges());
	}
	
	/** 
	 * Permet d'afficher les infos du contact dès le chargement de la page
	 * @author SB
	 *
	 */
	private class FocusAction extends FocusAdapter
	{
		public void focusGained(FocusEvent e)
		{	
			removeAll();
			init();
			numberCmbList.clear();
			numberFldList.clear();
			mailCmbList.clear();
			mailFldList.clear();
			
			c = allContacts.getSelectedContact();
			fldFirstname.setText(c.getFirstname());
			fldLastname.setText(c.getLastname());

			//SB/ Boucle pour récuperrer tous les numéros			
			for (int i = 0; i < c.listNumber.size(); i++)
			{
				JTextField fld = new JTextField(18);				
				JComboBox cmb = new JComboBox(NumberType.getNumberType());
				cmb.setPreferredSize(dimCombobox);
				fld.setText(c.listNumber.get(i).getNumber());
				NumberType type = c.listNumber.get(i).getType();
				cmb.setSelectedIndex(type.ordinal());
				
				numberFldList.add(fld);
				numberCmbList.add(cmb);
				
				if (i == 0)
				{
					JButton btn = new JButton(new ImageIcon("Images/Plus.png"));
					JPanel pnlNumberFormat = addInPanel(cmb, fld, btn);
					pnlNumber.add(pnlNumberFormat);
					btn.addMouseListener(new AddNumberAction());
				}
				else
				{
					JButton btn = new JButton(new ImageIcon("Images/Minus.png"));				
					JPanel pnlNumberFormat = addInPanel(cmb, fld, btn);
					pnlNumber.add(pnlNumberFormat);
					btn.addMouseListener(new RemoveNumber(pnlNumberFormat, c.listNumber.get(i), cmb, fld));
				}
			}
			
			//SB/ Boucle pour récuperrer tous les mails
			for (int i = 0; i < c.listMail.size(); i++)
			{
				JTextField fld = new JTextField(18);				
				JComboBox cmb = new JComboBox(MailType.getMailType());
				cmb.setPreferredSize(dimCombobox);
				fld.setText(c.listMail.get(i).getMail());
				MailType type = c.listMail.get(i).getType();
				cmb.setSelectedIndex(type.ordinal());
				
				mailFldList.add(fld);
				mailCmbList.add(cmb);
				
				if (i == 0)
				{
					JButton btn = new JButton(new ImageIcon("Images/Plus.png"));
					JPanel pnlMailFormat = addInPanel(cmb, fld, btn);
					pnlMail.add(pnlMailFormat);
					btn.addMouseListener(new AddMailAction(c.listMail.get(i)));
				}
				else
				{
					JButton btn = new JButton(new ImageIcon("Images/Minus.png"));
					JPanel pnlMailFormat = addInPanel(cmb, fld, btn);
					pnlMail.add(pnlMailFormat);
					btn.addMouseListener(new RemoveMail(pnlMailFormat, c.listMail.get(i), cmb, fld));
				}
			}
			
		}
	}
	
	/**
	 * Ajouter un JPanel pour avoir la mise en forme voulue d'une JComboBox et d'un JTextField 
	 * @author SB
	 * @param cb
	 * @param tf
	 * @return
	 */
	private JPanel addInPanel(JComboBox cb, JTextField tf, JButton bt)
	{
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(cb);
		p.add(tf);
		p.add(bt);
		p.setBackground(Color.WHITE);
		return p;
	}
	
	/**
	 * Permet d'appliquer les changements (modifications du contact)
	 * @author SB
	 *
	 */
	private class ApplyChanges extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			c.setFirstname(fldFirstname.getText());
			c.setLastname(fldLastname.getText());
			
			for (int i = 0; i < numberFldList.size(); i ++)
			{				
				String number = numberFldList.get(i).getText(); 
				int index = numberCmbList.get(i).getSelectedIndex();
				NumberType type = NumberType.values()[index];
				
				if(i >= c.listNumber.size())
				{
					Number n = new Number(type, number);
					c.addNumber(n);
				}
				else
				{
					Number n = c.listNumber.get(i);
					n.setType(type);
					n.setNumber(number);
				}				
			}
			
			for (int i = 0; i < mailFldList.size(); i ++)
			{
				String mail = mailFldList.get(i).getText();
				int index = mailCmbList.get(i).getSelectedIndex();
				MailType type = MailType.values()[index];
				
				if(i >= c.listMail.size())
				{
					Mail m = new Mail(type, mail);
					c.addMail(m);
				}
				else
				{
					Mail m = c.listMail.get(i);
					m.setType(type);
					m.setMail(mail);
				}
			}
				
			allContacts.saveContactList();
			appli.setActivePage("main");
		}
	}
	
	/**
	 * Permet d'ajouter un numéro
	 * @author SB
	 */
	private class AddNumberAction extends MouseAdapter
	{	
		public void mouseClicked(MouseEvent e)
		{
			//!
			JComboBox cb = new JComboBox<>(NumberType.getNumberType());
			cb.setPreferredSize(dimCombobox);
			JTextField tf = new JTextField("Numero", 18);
			JButton bt = new JButton(new ImageIcon("Images/Minus.png"));
			numberCmbList.add(cb);
			numberFldList.add(tf);

			Number number = new Number(NumberType.AUTRE, "0");
			c.getNumber().add(number);
			
			JPanel p = addInPanel(cb, tf, bt);
			bt.addMouseListener(new RemoveNumber(p, number, cb, tf));
			pnlNumber.add(p);
		}
	}
	
	/**
	 * Permet de supprimer un numéro
	 * @author SB
	 */
	private class RemoveNumber extends MouseAdapter
	{
		private JPanel panel;
		private Number number;
		private JComboBox cmb;
		private JTextField fld;
		
		public RemoveNumber(JPanel p, Number n, JComboBox cb, JTextField tf)
		{
			this.panel = p;
			this.number = n;
			this.cmb = cb;
			this.fld = tf;
		}
		
		public void mouseClicked(MouseEvent e)
		{
			pnlNumber.remove(panel);
			c.listNumber.remove(number);
			numberCmbList.remove(cmb);
			numberFldList.remove(fld);
		}
	}
	
	/**
	 * Permet d'ajouter un mail
	 * @author SB
	 */
	private class AddMailAction extends MouseAdapter
	{
		private Mail mail;
		
		public AddMailAction(Mail m)
		{
			this.mail = m;
		}
		public void mouseClicked(MouseEvent e)
		{
			JComboBox cb = new JComboBox<>(MailType.getMailType());
			cb.setPreferredSize(dimCombobox);
			JTextField tf = new JTextField("Adresse e-mail", 18);
			JButton bt = new JButton(new ImageIcon("Images/Minus.png"));
			mailCmbList.add(cb);
			mailFldList.add(tf);
			
			JPanel p = addInPanel(cb, tf, bt);
			bt.addMouseListener(new RemoveMail(p, mail, cb, tf));
			pnlMail.add(p);
		}
	}
	
	/**
	 * Permet de supprimer un mail
	 * @author SB
	 */
	private class RemoveMail extends MouseAdapter
	{
		private JPanel panel;
		private Mail mail;
		private JComboBox cmb;
		private JTextField fld;
		
		public RemoveMail(JPanel p, Mail m, JComboBox cb, JTextField tf)
		{
			this.panel = p;
			this.mail = m;
			this.cmb = cb;
			this.fld = tf;
		}
		
		public void mouseClicked(MouseEvent e)
		{
			pnlMail.remove(panel);
			c.listMail.remove(mail);
			mailCmbList.remove(cmb);
			mailFldList.remove(fld);
		}
	}
}
