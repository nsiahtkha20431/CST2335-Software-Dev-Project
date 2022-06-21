package com.armada.user.card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

public class CardDaoImpl implements CardDao {

	private static final String ARMADA_DS_JNDI = "java:app/jdbc/armada";
	private static final String ADD_CARD = "insert into cards(card_number,issue_date,personnelID,ruleID) values(?,?,?,?)";
	private static final String READ_CARDS = "select * from cards";
	private static final String BAN_CARD = "delete from cards where card_number = ?";
	private static final String UPDATE_CARD_TYPE = "update cards set ruleID = ? where card_number = ?";
	private static final String ASSIGN_CARD = "update cards set personnelID = ? where card_number = ?";
	private static final String READ_CARD_BY_NUMBER = " select * from cards where card_number = ?";
	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	@Resource(lookup = ARMADA_DS_JNDI)
	protected DataSource armadaDS;

	protected Connection con;
	protected PreparedStatement addCardPstmt;
	protected PreparedStatement listCardsPstmt;
	protected PreparedStatement banCardPstmt;
	protected PreparedStatement updateCardTypePstmt;
	protected PreparedStatement assignCardPstmt;
	protected PreparedStatement readByIdPstmt;

	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			logMsg("building connection and stmts");
			con = armadaDS.getConnection();
			addCardPstmt = con.prepareStatement(ADD_CARD);
			listCardsPstmt = con.prepareStatement(READ_CARDS);
			banCardPstmt = con.prepareStatement(BAN_CARD);
			updateCardTypePstmt = con.prepareStatement(UPDATE_CARD_TYPE);
			assignCardPstmt = con.prepareStatement(ASSIGN_CARD);
			readByIdPstmt = con.prepareStatement(READ_CARD_BY_NUMBER);

		} catch (SQLException e) {
			logMsg("something went wrong while getting connection from database" + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			logMsg("closing statement connections");
			addCardPstmt.close();
			listCardsPstmt.close();
			banCardPstmt.close();
			updateCardTypePstmt.close();
			assignCardPstmt.close();
			readByIdPstmt.close();
		} catch (SQLException e) {
			logMsg("something went wrong while closeing statements or connection: " + e.getLocalizedMessage());

		}
	}
	@Override
	public CardPojo readCardById(int cardNumber) {
		logMsg("reading a specific card");
		CardPojo cardPojo = new CardPojo();
		try {
			readByIdPstmt.setInt(1, cardNumber);
			ResultSet rs = readByIdPstmt.executeQuery();
			if(rs.next()) {
				cardPojo.setCardNumber(cardNumber);
				cardPojo.setIssueDate(rs.getDate("issue_date"));
				cardPojo.setPersonnelID(rs.getInt("personnelID"));
				cardPojo.setRuleID(rs.getInt("ruleID"));
			}
		}catch(SQLException e) {
			logMsg("error while accessing database:" + e.getLocalizedMessage());
		}
		return null;
		
	}
	
	@Override
	public CardPojo addCard(CardPojo card) {
		logMsg("Adding new Card");
		try {
			addCardPstmt.setInt(1, card.getCardNumber());
			addCardPstmt.setDate(2, card.getIssueDate());
			addCardPstmt.setInt(3, card.getPersonnelID());
			addCardPstmt.setInt(4, card.getRuleID());
		} catch (SQLException e) {
			logMsg("error while accessing database:" + e.getLocalizedMessage());
		}
		return card;
	}
	@Override
	public void banCard(int cardNumber) {
		logMsg("banning card from accessing gates");
		try {
			banCardPstmt.setInt(1, cardNumber);
			banCardPstmt.execute();
		} catch (SQLException e) {
			logMsg("error while accessing database: " + e.getLocalizedMessage());
		}
	}
	@Override
	public void changeCardType(CardPojo card) {
		logMsg("updating card type for accessing gates");
		try {
			updateCardTypePstmt.setInt(1, card.getRuleID());
			updateCardTypePstmt.setInt(2, card.getCardNumber());
			updateCardTypePstmt.execute();
		}
		catch(SQLException e) {
			logMsg("error whiile accessing database: "+e.getLocalizedMessage());
		}
	}
	@Override
	public void assignCard(CardPojo card) {
		logMsg("assigning card to someone");
		try {
			assignCardPstmt.setInt(1, card.getPersonnelID());
			assignCardPstmt.setInt(2, card.getCardNumber());
			assignCardPstmt.execute();
		} catch (SQLException e) {
			logMsg("error while accessing database: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List<CardPojo> listCards() {
		logMsg("lsiting card details");
		List<CardPojo> cards = new ArrayList<>();
		try (ResultSet rs = listCardsPstmt.executeQuery();) {
			while (rs.next()) {
				CardPojo newCard = new CardPojo();
				// (card_number,issue_date,personnelID,ruleID)
				newCard.setCardNumber(rs.getInt("card_number"));
				newCard.setIssueDate(rs.getDate("issue_date"));
				newCard.setPersonnelID(rs.getInt("personnelID"));
				newCard.setRuleID(rs.getInt("ruleID"));
				cards.add(newCard);

			}

		} catch (SQLException e) {
			logMsg("something went wrong while accessing database: " + e.getLocalizedMessage());
		}
		return cards;
	}

}
