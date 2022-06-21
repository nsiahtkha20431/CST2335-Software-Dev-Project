package databank.dao;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import databank.model.*;


@Named
@ApplicationScoped
public class AccountDao implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String DATABANK_DS_JNDI = "java:app/jdbc/armada";
	private static final String INSERT_ACCOUNT = "insert into account (account_name,account_password) values(?,?)";
	private static final String ACCOUNT_VALIDATION = "select * from account where account_name = ? and account_password = ?";
	
	@Inject
	protected ExternalContext externalContext;

	private void logMsg( String msg) {
		( (ServletContext) externalContext.getContext()).log( msg);
	}

	@Resource( lookup = DATABANK_DS_JNDI)
	protected DataSource databankDS;

	protected Connection conn;
	protected PreparedStatement validationPstmt;
	protected PreparedStatement createPstmt;
	
	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			logMsg( "building connection and stmts");
			conn = databankDS.getConnection();
			validationPstmt=conn.prepareStatement(ACCOUNT_VALIDATION);
			createPstmt=conn.prepareStatement(INSERT_ACCOUNT);
		} catch ( Exception e) {
			logMsg( "something went wrong getting connection from database: " + e.getLocalizedMessage());
		}
	}
	
	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			logMsg( "closing stmts and connection");
			validationPstmt.close();
			createPstmt.close();
			conn.close();
		} catch ( Exception e) {
			logMsg( "something went wrong closing stmts or connection: " + e.getLocalizedMessage());
		}
		}
	
	public boolean accountValidation(String account_name, String account_password) {
		AccountPojo account = new AccountPojo();
		Boolean valid = false;
		try   {
			validationPstmt.setString(1,account_name);
			validationPstmt.setString(2,account_password);
			ResultSet rs = validationPstmt.executeQuery();

			if ( rs.next()) {
				System.out.println("Loginned");
				valid = true;
			}
			else {
				System.out.println("wrong username or password");
				valid=false;}
			
			
		} catch ( SQLException e) {
			logMsg( "something went wrong accessing database: " + e.getLocalizedMessage());
		}
		
		return valid;
	}
	
	public AccountPojo createAccount(AccountPojo account) {
		logMsg( "creating an account");
		try {
    		createPstmt.setString(1, account.getUserName());
    		createPstmt.setString(2,account.getPassWord());
    				
    		createPstmt.execute();
    	}
    	catch (SQLException e) {
            logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
    	}
    
		
		
		
		return null;
		
	}

}
