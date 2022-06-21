package com.armada.user.card;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;


public class CardPojo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int cardNumber;
	private Date issueDate;
	private int personnelID;
	private int ruleID;
	public int getRuleID() {
		return ruleID;
	}
	public void setRuleID(int ruleID) {
		this.ruleID = ruleID;
	}
	public int getPersonnelID() {
		return personnelID;
	}
	public void setPersonnelID(int personnelID) {
		this.personnelID = personnelID;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date isssueDate) {
		this.issueDate = isssueDate;
	}
	public int getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + Objects.hash(getCardNumber());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof CardPojo otherCardPojo) {
			// see comment (above) in hashCode(): compare using only member variables that are
			// truely part of an object's identity
			return Objects.equals(this.getCardNumber(), otherCardPojo.getCardNumber());
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( "Person [card_number=").append( getCardNumber()).append( ", ");
		if ( getIssueDate() != null) {
			builder.append( "issue_date=").append( getIssueDate()).append( ", ");
		}
		if ( getPersonnelID() != 0) {
			builder.append( "personnelID=").append( getPersonnelID()).append( ", ");
		}
		if ( getRuleID() != 0) {
			builder.append( "rule_ID=").append( getRuleID()).append( ", ");
		}
		
		
		builder.append( "]");
		return builder.toString();
		
	}

}
