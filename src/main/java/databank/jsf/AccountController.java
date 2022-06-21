package databank.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.annotation.SessionMap;
import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

import databank.dao.AccountDao;

import databank.model.AccountPojo;
@Named
@SessionScoped
public class AccountController implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	@SessionMap
	private Map< String, Object> sessionMap;

	@Inject
	private AccountDao accountDao;

	private List< AccountPojo> account;

 public String validateAccount(String account_name,String account_password) {
	 boolean valid=false;
	 String goWhere="index.xhtml";
	 
	if( accountDao.accountValidation(account_name,account_password)==true) {
		valid=true;
		goWhere="mainpage.xhtml?faces-redirect=true";
		
	}
	
	return goWhere;
	}
 
 public String goToAddForm() {
		//Pay attention to the name here, it will be used as the object name in add-person.xhtml
		//ex. <h:inputText value="#{newPerson.firstName}" id="firstName" />
		sessionMap.put( "newAccount", new AccountPojo());
		return "add-account.xhtml?faces-redirect=true";
	}

 public String submitAccount( AccountPojo account) {
		//TODO use DAO, also update the Person object with current date. you can use Instant::now
		accountDao.createAccount(account);
		return "index.xhtml?faces-redirect=true";
	}



}